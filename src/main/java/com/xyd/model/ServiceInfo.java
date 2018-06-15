package com.xyd.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.xyd.utils.IPAddress;

import io.netty.buffer.ByteBuf;

//0x03：回传地址信息，回传包含两种方式：一是IP+端口（0x01），二是域名+端口（0x02），用内容中第一个字节标识，端口长度为 2 字节，unit16。
public class ServiceInfo {
	private String address = "";	//应用服务地址IP地址或域名
	private int port = 0;			// 应用服务端口

	public ServiceInfo(ByteBuf frame, int len) {
		int type = frame.readByte(); // 跳过域名/IP标识
		
		byte[] bAddr = new byte[len -3]; 
		frame.readBytes(bAddr);
		if(type==1) {
			try {
				address = InetAddress.getByAddress(bAddr).toString(); 
			} catch (UnknownHostException e) {}
		} else if(type==2) {
			address = new String(bAddr);
		} else {
			frame.skipBytes(len -3);
		}
		
		port = frame.readShort();
	}

	public ServiceInfo(String value) {
		String[] s = value.split("\n");
		if(s.length == 2) {
			address = s[0];
			port = Integer.parseInt(s[1]);
		}
	}
	
	public ServiceInfo(String address, int port) {
		this.address = address.toLowerCase();
		this.port = port;
	}
	
	public String getAddress() {
		return address;
	}
	public int getPort() {
		return port;
	}
	
	public int writeBytes(ByteBuf frame) throws Exception {
		if(IPAddress.isValid(address)) {
			frame.writeByte(1);
			frame.writeBytes(InetAddress.getByName(address).getAddress());
			frame.writeShort(port);
			return 7;
		} else {
			frame.writeByte(2);
			byte b[] = address.getBytes();
			frame.writeBytes(b);
			frame.writeShort(port);
			return b.length + 3;
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s\n%d", address, port);
	}
}
