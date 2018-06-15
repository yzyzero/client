package com.xyd.transfer.ip.datapack;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;

public class BroadcastClose extends SendPack {
	private String messageID;
	
	public BroadcastClose(String messageID, String source, String...targets) {
		this.messageID = messageID;
		this.setSource(source);
		this.setTargets(targets);
	}
	
	@Override
	protected int generateBuf(ByteBuf buf) throws DecoderException {
		buf.writeBytes(Hex.decodeHex(messageID));
		return 15;
	}
	
	@Override
	protected PackType getType() {
		return PackType.REQUEST;
	}
	
	@Override
	public OperationType getOperation() {
		return OperationType.BROADCAST_CLOSE;
	}

}
