package com.xyd.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * The persistent class for the eb_media_content database table.
 * 
 */
@Entity
@Table(name="eb_media")
@SecondaryTables({
	@SecondaryTable(name="eb_media_addit"),
	@SecondaryTable(name="eb_media_text"),
	@SecondaryTable(name="eb_media_device"),
	@SecondaryTable(name="eb_media_tv")
})
public class MediaContent implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;
	@Enumerated(EnumType.ORDINAL)
	private MediaType type;			//资源类型 1：文本；2：音频；3：图片；4：视频。
	@Enumerated(EnumType.ORDINAL)
	private Source source;
	private BigDecimal duration;
	private String language;
	@Convert(converter = Encode.EncodeConvert.class)
	private Encode encode;
	@Column(name="create_time")
	private Timestamp createTime;
	private String category;		//应急广播消息类别

	private short audit;
	@Column(name="audit_time")
	private Timestamp auditTime;
	@Column(name="audit_user")
	private String auditUser;

	private String memo;

	@Column(name="org_id")
	private Integer orgId;

	@Column(name="region_Id")
	private String regionId;

	@Column(name="user_id")
	private int userId;
	
	@Enumerated
	private Additional additional;
	@Enumerated
	private Text text;
	@Enumerated
	private DeviceChannel deviceChannel;
	@Enumerated
	private TVProgram program;

	public MediaContent() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public short getAudit() {
		return this.audit;
	}

	public void setAudit(short audit) {
		this.audit = audit;
	}

	public Timestamp getAuditTime() {
		return this.auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditUser() {
		return this.auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@JSONField(serializeUsing = Encode.JSONSerializer.class)
	public Encode getEncode() {
		return encode;
	}

	@JSONField(deserializeUsing=Encode.JSONDeserializer.class)
	public void setEncode(Encode encode) {
		this.encode = encode;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getDuration() {
		return this.duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	public String getRegionId() {
		return this.regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public MediaType getType() {
		return this.type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public Additional getAdditional() {
		return additional;
	}

	public void setAdditional(Additional additional) {
		this.additional = additional;
	}

	public DeviceChannel getDeviceChannel() {
		return deviceChannel;
	}

	public void setDeviceChannel(DeviceChannel deviceChannel) {
		this.deviceChannel = deviceChannel;
	}

	public TVProgram getProgram() {
		return program;
	}

	public void setProgram(TVProgram program) {
		this.program = program;
	}

	public enum Source {
		NONE, TEXT, TEXT_TTS, FILE, DEVICE, DTV, INNER_BYTE, PUSH, RTSP, HTTP, FTP
	}
}