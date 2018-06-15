package com.xyd.transfer.ip.datapack;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

import com.xyd.model.IPInfo;
import com.xyd.model.ServiceInfo;

import io.netty.buffer.ByteBuf;

/**
 * 0x01(W)：终端音量，1 字节，终端输出音量，效果为终端可输出最大音量的百分比，取值范围：0 ~ 100。
 * 0x02(W)：终端本地IP 地址信息，IP 地址（4字节）、子网掩码（4 字节）、网关（4 字节），一共12 字节。要求预留IPV6 格式。
 * 0x03(W)：回传地址信息，回传包含两种方式：1、IP+端口（0x01），2、域名+端口（0x02），用内容中第一个字节标识，端口长度为 2 字节，unit16。
 * 0x04(W)：终端资源编码。
 * 0x05：物理地址编码，第1 个字节标识物理地址编码长度；后面（长度‐1）字节，终端唯一标识，出厂时生成，固定不变。
 * 0x06：工作状态，1 个字节，终端当前所处工作状态。
 * 0x07：故障代码，1 个字节，由厂家定义。
 */
public class TerminalStatus extends BasePack {
	private int resultCode;
	private String resultDesc;
	private final Map<Parameter, Object> parameters = new HashMap<>();

	public TerminalStatus(RawPack pack) {
		this(pack.getSessionID(), pack.getSource(), pack.getTargets(), pack.getBuf());
	}
	
	public TerminalStatus(int sessionID, String source, String[] targets, ByteBuf frame) {
		this.setSessionID(sessionID);
		this.setSource(source);
		this.setTargets(targets);

		resultCode = frame.readByte();
		int descLen = frame.readShort();
		if(descLen > 0 ) {
			byte bDesc[] = new byte[descLen];
			frame.readBytes(bDesc);
			resultDesc = new String(bDesc);
		}
		
		int pNumber = frame.readByte();
		byte[] value;
		while(pNumber > 0) {
			Parameter identifer = Parameter.valueOf(frame.readByte());
			int vLen = frame.readByte();
			
			switch (identifer) {
			case RESOURCE_CODE: 
				value = new byte[vLen];
				frame.readBytes(value);
				parameters.put(identifer, Hex.encodeHexString(value));
				break;
			case ECHO_ADDRESS: 
				parameters.put(identifer, new ServiceInfo(frame, vLen)); break;
			case PHYSICALADDRESS:
				int phyLen = frame.readByte();
				value = new byte[phyLen];
				frame.readBytes(value);
				parameters.put(identifer, Hex.encodeHexString(value)); 
				break;
			case LOCAL_ADDRESS:
				parameters.put(identifer, new IPInfo(frame)); break;
			case VOLUME:
			case STATUS:
			case FAULT_CODE:
				parameters.put(identifer, frame.readByte()); break;
			default:
				frame.skipBytes(vLen);
			}
			
			pNumber --;
		}
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_STATUS_QUERY;
	}

	public int getResultCode() {
		return resultCode;
	}

	public String getResultDesc() {
		return resultDesc;
	}

	public Map<Parameter, Object> getParameters() {
		return parameters;
	}
	
	public Object getParameter(Parameter identifer) {
		return parameters.get(identifer);
	}
}
