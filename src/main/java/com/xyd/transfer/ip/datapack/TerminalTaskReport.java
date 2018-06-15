package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;

/**
 * 序号 	语法 			长度（字节） 编码规则
 * 1 	切换标识 		1 			1：进入任务；2：离开任务。
 * 2 	应急广播消息ID 	15 			终端任务切换对应的应急广播消息ID。
 * 3 	发生时间 		4 			终端播出任务切换时的UTC 时间。
 */
public class TerminalTaskReport extends BasePack {
	private int sign;
	private String messageID;
	private int time;

	public TerminalTaskReport(RawPack pack) {
		this(pack.getSessionID(), pack.getSource(), pack.getTargets(), pack.getBuf());
	}
	
	public TerminalTaskReport(int sessionID, String source, String[] targets, ByteBuf frame) {
		this.setSessionID(sessionID);
		this.setSource(source);
		this.setTargets(targets);
		
		sign = frame.readByte();
		byte bmId[] = new byte[15];
		frame.readBytes(bmId);
		messageID = Hex.encodeHexString(bmId);
		time = frame.readInt();
	}

	public int getSign() {
		return sign;
	}

	public String getMessageID() {
		return messageID;
	}

	public int getTime() {
		return time;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_TASK_SWITCH;
	}
}
