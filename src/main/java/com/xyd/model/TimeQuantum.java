package com.xyd.model;

public class TimeQuantum {
	private short month;// 从低到高（1-12月）按位记录是否选择
	private byte week;	// 从低到高（星期天-星期六）按位记录是否选择 
	private int begin;	// 记录任务的时分秒：从0点开始的秒数
	private int end;	// 记录任务的时分秒：从0点开始的秒数
	
	TimeQuantum(String quantum){
		String[] val = quantum.substring(1).split(":");
		for(int i =0; i < val.length; i ++) {
			switch (i) {
			case 0:
				month = Short.parseShort(val[0]);
				break;
			case 1:
				week = Byte.parseByte(val[1]);
				break;
			case 2:
				begin = Integer.parseInt(val[2]);
				break;
			case 3:
				end = Integer.parseInt(val[3]);
				break;
			default:
			}
		}
	}
	
	public static TimeQuantum parse(String quantum) {
		return new TimeQuantum(quantum);
	}
	
	@Override
	public String toString() {
		return "T" + month + ":" + week + ":" + begin + ":" + end;
	}

	public short getMonth() {
		return month;
	}
	public void setMonth(short month) {
		this.month = month;
	}
	public byte getWeek() {
		return week;
	}
	public void setWeek(byte week) {
		this.week = week;
	}
	public int getBegin() {
		return begin;
	}
	public void setBegin(int begin) {
		this.begin = begin;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
}
