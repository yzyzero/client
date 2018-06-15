package com.xyd.transfer.ip.datapack;

import io.netty.buffer.ByteBuf;

/**
 * 序号 	语法 		长度（字节） 		编码规则 
 * 1 	故障恢复标识  1				1：发生故障；2：故障消除，即恢复正常。
 * 2 	故障类型 	1				1：电源电流过低；2：平均电源功耗过低；3：功放输出电压过低；4：锁定频率场强过低；5：无法获取监测信息；9：其它。
 * 3 	发生时间 	4 				发生故障或者故障恢复的UTC 时间。
 */
public class TerminalFailureReport  extends BasePack {
	private int sign;
	private int error;
	private int time;

	public TerminalFailureReport(RawPack pack) {
		this(pack.getSessionID(), pack.getSource(), pack.getTargets(), pack.getBuf());
	}

	public TerminalFailureReport(int sessionID, String source, String[] targets, ByteBuf frame) {
		this.setSessionID(sessionID);
		this.setSource(source);
		this.setTargets(targets);
		
		sign = frame.readByte();
		error = frame.readByte();
		time = frame.readInt();
	}

	public int getSign() {
		return sign;
	}

	public int getError() {
		return error;
	}

	public int getTime() {
		return time;
	}
	
	public String getDescription() {
		switch (error) {
		case 1: return "电源电流过低";
		case 2: return "平均电源功耗过低";
		case 3: return "功放输出电压过低";
		case 4: return "锁定频率场强过低";
		case 5: return "无法获取监测信息";
		default: return "其它故障";
		}
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_FAILURE_RECOVERY;
	}
}
