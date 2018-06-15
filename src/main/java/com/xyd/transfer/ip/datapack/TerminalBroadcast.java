package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;

/**
 * 序号	语法 			长度（字节） 编码规则
 * 1 	资源编码 		9 			资源编码。压缩型BCD 编码，9 字节。
 * 2 	应急广播消息ID	15 			应急广播消息ID。
 * 3 	播发结果 		1 			0：播发失败；1：播发成功；2：播发中。
 * 4 	播发开始时间 		4 			播发开始时间。
 * 5 	播发截止时间 		4 			播发截止时间。
 *
 */
public class TerminalBroadcast extends BasePack {
	private String resourceCode;
	private String messageID;
	private int result;
	private int beginTime;
	private int endTime;

	public TerminalBroadcast(RawPack pack) {
		this(pack.getSessionID(), pack.getSource(), pack.getTargets(), pack.getBuf());
	}
	
	public TerminalBroadcast(int sessionID, String source, String[] targets, ByteBuf frame) {
		this.setSessionID(sessionID);
		this.setSource(source);
		this.setTargets(targets);
		
		byte bRes[] = new byte[9];
		frame.readBytes(bRes);
		resourceCode = Hex.encodeHexString(bRes);
		
		byte bMsg[] = new byte[15];
		frame.readBytes(bMsg);
		messageID = Hex.encodeHexString(bMsg);
		
		result = frame.readByte();
		beginTime = frame.readInt();
		endTime = frame.readInt();
	}
	
	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_BROADCAST_QUERY;
	}

	public String getResourceCode() {
		return resourceCode;
	}

	public String getMessageID() {
		return messageID;
	}

	public int getResult() {
		return result;
	}

	public int getBeginTime() {
		return beginTime;
	}

	public int getEndTime() {
		return endTime;
	}
}
