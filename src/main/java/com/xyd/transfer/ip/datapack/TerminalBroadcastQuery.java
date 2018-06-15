package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
/**
 * 序号 	语法 			长度（字节） 		编码规则
 * 1 	资源编码 		9 				资源编码。压缩型BCD 编码，9 字节。
 * 2 	应急广播消息ID 	15				应急广播消息ID。采用压缩型BCD 编码，15 字节。
 * 3 	查询开始时间 		4 				查询开始时间。
 * 4 	查询截止时间 		4 				查询截止时间。
 *
 */
public class TerminalBroadcastQuery extends SendPack {
	private String resourceCode;
	private String messageID;
	private int beginTime;
	private int endTime;
	
	public TerminalBroadcastQuery(String resourceCode, String messageID, int beginTime, int endTime) {
		this.resourceCode = resourceCode;
		this.messageID = messageID;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}

	@Override
	protected int generateBuf(ByteBuf buf) throws DecoderException {
		buf.writeBytes(Hex.decodeHex(resourceCode));
		buf.writeBytes(Hex.decodeHex(messageID));
		buf.writeInt(beginTime);
		buf.writeInt(endTime);
		return 32;
	}
	
	@Override
	protected PackType getType() {
		return PackType.REQUEST;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_BROADCAST_QUERY;
	}

	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public void setBeginTime(int beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
