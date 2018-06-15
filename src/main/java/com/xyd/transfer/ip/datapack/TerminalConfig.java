package com.xyd.transfer.ip.datapack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Hex;

import com.xyd.model.IPInfo;
import com.xyd.model.ServiceInfo;

import io.netty.buffer.ByteBuf;

/**
 * 序号 	语法 		长度（字节） 			编码规则
 * 1 	设置参数个数 1 					终端参数标识：
 * 2 	参数标识1 	1
 * 3 	参数标识1 	内容长度 1
 * 4 	参数标识1 	内容参数标识1 		内容长度
 *
 * 0x01：音量按百分比形式标识，其中：“0x00：静音；0xff：开播，音量不变；0x01～0x64：对应音量1%～100%”。
 * 0x02：终端本地IP 地址信息，IP 地址（4字节）、子网掩码（4 字节）、网关（4 字节），一共12 字节。要求预留IPV6 格式。
 * 0x03：回传地址信息，回传包含两种方式：一是IP+端口（0x01），二是域名+端口（0x02），用内容中第一个字节标识，端口长度为 2 字节，unit16。
 * 0x04：终端资源编码。
 */
public class TerminalConfig extends SendPack {
	private final Map<Parameter, Object> parameters;
	
	public TerminalConfig(String...targets) {
		parameters = new HashMap<>();
		this.setTargets(targets);
	}
	
	public void add(Parameter key, Object value) {
		parameters.put(key, value);
	}

	@Override
	protected PackType getType() {
		return PackType.REQUEST;
	}

	@Override
	protected int generateBuf(ByteBuf buf) throws Exception {
		int contentLen = 1;
		buf.writeByte(parameters.size());
		Iterator<Parameter> it = parameters.keySet().iterator();
		while (it.hasNext()) {
			Parameter key = it.next();
			buf.writeByte(key.ordinal());
			switch (key) {
			case VOLUME:
				buf.writeByte(1);
				buf.writeByte((int)parameters.get(key));
				contentLen += 3;
				break;
			case LOCAL_ADDRESS:
				buf.writeByte(12);
				IPInfo ipInfo = (IPInfo) parameters.get(key);
				ipInfo.writeBytes(buf);
				contentLen += 14;
				break;
			case ECHO_ADDRESS:
				int lenPos = buf.writerIndex();
				buf.writeByte(3);
				ServiceInfo sInfo = (ServiceInfo)parameters.get(key);
				int iLen = sInfo.writeBytes(buf);
				buf.setByte(lenPos, iLen);
				contentLen += iLen + 2;
				break;
			case RESOURCE_CODE:
				buf.writeByte(9);
				String resId = (String) parameters.get(key);
				buf.writeBytes(Hex.decodeHex(resId));
				contentLen += 11;
				break;
			default:
				break;
			}
		}
		
		return contentLen;
	}

	@Override
	public OperationType getOperation() {
		return OperationType.TERMINAL_CONFIG;
	}

}
