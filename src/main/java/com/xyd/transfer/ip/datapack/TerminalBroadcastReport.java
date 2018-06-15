package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;

/**
 * 序号	语法 			长度（字节）		 编码规则
 * 1 	应急广播消息ID 	15 				上报播发结果的应急广播消息ID。
 * 2 	结果代码 		1 				0：失败；1：成功。
 * 3 	结果描述长度 		2				结果描述的长度；如果无结果描述则长度为0。
 * 4 	结果描述内容 		结果描述长度 		可选，对应答结果进行描述。
 * 5 	播发开始时间 		4 				播发开始的UTC 时间。
 * 6 	播发结束时间 		4 				播发结束的UTC 时间。
 * 7 	播发次数 		1 				播发次数。
 * 8 	上报时间 		4 				上报播发结果的UTC 时间。
 */
public class TerminalBroadcastReport extends BasePack {
	private String messageID;
	private int resultCode;
	private String resultDesc;
	private int begin;
	private int end;
	private int times;
	private int reportTime;

	public TerminalBroadcastReport(RawPack pack) {
		this(pack.getSessionID(), pack.getSource(), pack.getTargets(), pack.getBuf());
	}
	
	public TerminalBroadcastReport(int sessionID, String source, String[] targets, ByteBuf frame) {
		this.setSessionID(sessionID);
		this.setSource(source);
		this.setTargets(targets);

		byte bmId[] = new byte[15];
		frame.readBytes(bmId);
		messageID = Hex.encodeHexString(bmId);
		resultCode = frame.readByte();
		int descLen = frame.readShort();
		if(descLen > 0 ) {
			byte bDesc[] = new byte[descLen];
			frame.readBytes(bDesc);
			resultDesc = new String(bDesc);
		}
		
		begin = frame.readInt();
		end = frame.readInt();
		times = frame.readByte();
		reportTime = frame.readInt();
	}

	public String getMessageID() {
		return messageID;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public int getBegin() {
		return begin;
	}

	public int getEnd() {
		return end;
	}

	public int getTimes() {
		return times;
	}

	public int getReportTime() {
		return reportTime;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_BROADCAST_REPORT;
	}
}
