package com.xyd.model;

public enum MediaType {
	NONE, TEXT, AUDIO, PICTURE, VIDEO, TAR; // 数字电视网络资源

	public static MediaType valueOf(int value) {
		switch (value) {
		case 1:
			return TEXT;
		case 2:
			return AUDIO;
		case 3:
			return PICTURE;
		case 4:
			return VIDEO;
		case 5:
			return TAR;
		default:
			return NONE;
		}
	}
}
