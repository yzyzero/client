package com.xyd.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Text {

	@Column(table="eb_media_text")
	private String details;
	@Column(name="voice_name", table="eb_media_text")
	private String voiceName;
	@Column(table="eb_media_text")
	private Integer pitch;
	@Column(table="eb_media_text")
	private Integer speed;
	@Column(table="eb_media_text")
	private Integer volume;

	public String getDetails() {
		return this.details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Integer getPitch() {
		return this.pitch;
	}

	public void setPitch(Integer pitch) {
		this.pitch = pitch;
	}
	
	public Integer getSpeed() {
		return this.speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getVoiceName() {
		return this.voiceName;
	}

	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

	public Integer getVolume() {
		return this.volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}
}
