package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.DecoderException;

import io.netty.buffer.ByteBuf;

public class TerminalStatusQuery extends SendPack {
	/* 1：终端音量；
	 * 2：本地地址；
	 * 3：回传地址；
	 * 4：终	端资源编码
	 * 5：物理地址编码
	 * 6：工作状态；
	 * 7：故障代码。
	*/
	private Parameter parameters[];
	
	public TerminalStatusQuery(String source, Parameter...parameters) {
		this.setSource(source);
		this.parameters = parameters;
	}

	@Override
	protected int generateBuf(ByteBuf buf) throws DecoderException {
		buf.writeByte(parameters.length);
		for (Parameter b : parameters) {
			buf.writeByte(b.ordinal());
		}
		return parameters.length + 1;
	}

	public void setParameters(Parameter...parameters) {
		this.parameters = parameters;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_STATUS_QUERY;
	}
	
	@Override
	protected PackType getType() {
		return PackType.REQUEST;
	}
}
