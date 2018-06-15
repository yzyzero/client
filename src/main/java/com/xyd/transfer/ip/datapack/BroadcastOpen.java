package com.xyd.transfer.ip.datapack;

import java.util.List;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.xyd.model.Broadcast;
import com.xyd.model.MediaType;

import io.netty.buffer.ByteBuf;

public class BroadcastOpen extends SendPack {

	/*-------------------------------开广播指令---------------------------------------------------
	序号		语法			长度（字节）	编码规则
	1	应急广播消息ID	15	30位数字码，采用压缩型BCD编码，15字节。通过应急广播消息ID区别其他的应急广播消息。编码规则：应急广播平台ID(18位)+日期（8位）+顺序码（4位），日期格式为YYYYMMDD，YYYY表示年，MM表示月，DD表示日。
	2	广播类型			1	1：应急演练-发布系统演练；2：应急演练-模拟演练；3：应急演练-实际演练；4：应急广播；5：日常广播。其他：预留
	3	事件级别			1	取值范围1～4，本字段参照了国务院颁发的《国家突发公共事件总体应急预案》（2015）中的级别定义。1：1级（特别重大）2：2级（重大）3：3级（较大）4：4级（一般）
	4	事件类型			5	5个字节（40位比特），应急广播消息的类别，按照《国家突发公共事件总体应急预案》和《国家应急平台体系信息资源分类与编码规范》中对突发事件的分类和编码要求，该字段的取值范围和对应类别描述见附录B。每个字符都按照GB/T 15273.1-1994编码为8位。默认为“11111”。
	5	音量				1	音量按百分比形式标识，其中：0x00：静音 0xff：开播，音量不变  0x01～0x64：对应音量1%～100%
	6	开始时间			4	UTC时间
	7	持续时间			4	循环播放持续时间，单位：秒。
	8	辅助数据数量		1	应急事件的辅助数据数量，包含文本、音频、图片、视频等，取值1～4。
	---------------------------------------------------------------------------------------*/
	private final Broadcast content;
	
	public BroadcastOpen(Broadcast content) {
		this.content = content;
		this.setSource(content.getSource());
		this.setTargets(content.getTargets());
	}

	@Override
	protected int generateBuf(ByteBuf buf) throws DecoderException {
		int optLen = 32;
		buf.writeBytes(Hex.decodeHex(content.getId())); //消息编号
		buf.writeByte(content.getType().ordinal());
		buf.writeByte(content.getEventLevel().ordinal());
		buf.writeBytes(content.getEventType().getBytes());
		buf.writeByte(content.getVolume());
		buf.writeInt((int) ((content.getBeginTime() == null ? System.currentTimeMillis() : content.getBeginTime().getTime())/1000));
		buf.writeInt(content.getDuration());
		List<Broadcast.Content> contents = content.getContents();
		
		int conLen = 0;
		int conLenPos = buf.writerIndex();
		buf.writeByte(contents.size());
		for (Broadcast.Content con : contents) {
			if(MediaType.AUDIO.equals(con.getType()) ||
					MediaType.TEXT.equals(con.getType()) ||
					MediaType.PICTURE.equals(con.getType()) ||
					MediaType.VIDEO.equals(con.getType())) {
				
				byte bContent[];
				if(MediaType.AUDIO.equals(con.getType())) {
					bContent = con.getPublish().getBytes();
				} else {
					bContent = con.getValue().getBytes();
				}
				
				buf.writeByte(con.getType().ordinal());
				int acLen = bContent.length;
				
				if (MediaType.AUDIO.equals(con.getType())) {
					acLen += 2;
					buf.writeShort(acLen);
					buf.writeByte(1);	// 协议固定为RTSP
					buf.writeByte(con.getEncode().getValue());	// 音频类型
				} else {
					buf.writeShort(acLen);
				}
				buf.writeBytes(bContent);
				
				optLen += 3 + acLen;
				conLen ++;
			}
		}
		buf.setByte(conLenPos, conLen);
		
		
		return optLen;
	}
	
	@Override
	public PackType getType() {
		return PackType.REQUEST;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.BROADCAST_OPEN;
	}
}
