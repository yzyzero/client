package com.xyd.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import io.netty.buffer.ByteBuf;

public class IPInfo {
	private String address = "";
	private String netmask = "";
	private String gateway = "";
	
	public IPInfo(ByteBuf frame) {
		byte[] b = new byte[4];
		frame.readBytes(b);
		try { address = InetAddress.getByAddress(b).toString(); } catch (UnknownHostException e) {}
		frame.readBytes(b);
		try { netmask = InetAddress.getByAddress(b).toString(); } catch (UnknownHostException e) {}
		frame.readBytes(b);
		try { gateway = InetAddress.getByAddress(b).toString(); } catch (UnknownHostException e) {}
	}

	public IPInfo(String value) {
		String[] s = value.split("/");
		if(s.length == 3) {
			address = s[0];
			netmask = s[1];
			gateway = s[2];
		}
	}
	
	public IPInfo(String address, String netmask, String gateway) throws UnknownHostException {
		this.address = address;
		this.netmask = netmask;
		this.gateway = gateway;
	}
	
	
	public String getAddress() {
		return address;
	}

	public String getNetmask() {
		return netmask;
	}

	public String getGateway() {
		return gateway;
	}
	
	public void writeBytes(ByteBuf frame) throws Exception {
		frame.writeBytes(InetAddress.getByName(address).getAddress());
		frame.writeBytes(InetAddress.getByName(netmask).getAddress());
		frame.writeBytes(InetAddress.getByName(gateway).getAddress());
	}
	
	@Override
	public String toString() {
		return String.format("%s/%s/%s", address, netmask, gateway);
	}
}
