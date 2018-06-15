package com.xyd.exception;

import com.xyd.model.Broadcast;

public class BroadcastException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private Broadcast broadcast = null;

	public BroadcastException() {
		super();
	}
	
	public BroadcastException(String message, Throwable cause) {
		super(message, cause);
	}

	public BroadcastException(String message) {
		super(message);
	}

	public BroadcastException(Throwable cause) {
		super(cause);
	}
	
	public BroadcastException(Broadcast broadcast) {
		super();
		this.broadcast = broadcast;
	}
	
	public BroadcastException(Broadcast broadcast, String message, Throwable cause) {
		super(message, cause);
		this.broadcast = broadcast;
	}

	public BroadcastException(Broadcast broadcast, String message) {
		super(message);
		this.broadcast = broadcast;
	}

	public BroadcastException(Broadcast broadcast, Throwable cause) {
		super(cause);
		this.broadcast = broadcast;
	}

	public Broadcast getBroadcast() {
		return broadcast;
	}
}
