package com.xyd.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DeviceChannel {
	@Column(name="resource_code", table="eb_media_device")
	private String resourceCode;
	@Column(table="eb_media_device")
	private int channel;
	@Column(table="eb_media_device")
	private String name;
	@Column(table="eb_media_device")
	private String description;
	
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public int getChannel() {
		return channel;
	}
	public void setChannel(int channel) {
		this.channel = channel;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
