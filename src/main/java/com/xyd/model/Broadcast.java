package com.xyd.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.xyd.model.DeviceChannel;
import com.xyd.model.Encode;
import com.xyd.model.MediaContent;
import com.xyd.model.MediaType;
import com.xyd.model.TVProgram;
import com.xyd.utils.RegionUtil;

/**
 * The persistent class for the eb_broadcast database table.
 * 
 */
@Entity
@Table(name = "eb_broadcast")
@NamedQuery(name = "Broadcast.findAll", query = "SELECT e FROM Broadcast e")
public class Broadcast implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "idGenerator")
	@GenericGenerator(name = "idGenerator", strategy = "com.xyd.model.IDGenerator")
	private String id; // 消息ID
	private String source; // 请求广播源资源地址
	// @Convert(converter = TargetsConverter.class)
	@org.hibernate.annotations.Type(type="com.xyd.model.StringArrayField")
	private String[] targets; // 广播目标地址
	@Enumerated
	private Type type; // 广播类型
	@Column(name = "event_level")
	@Enumerated
	private EventLevel eventLevel; // 事件等级
	@Column(name = "event_type")
	private String eventType; // 事件类型
	private int volume; // 音量

	@Column(name = "begin_time")
	private Timestamp beginTime; // 开始时间
	private int duration; // 持续时间(秒)
	private int loop;

	private String title;

	@ElementCollection(targetClass = Content.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "eb_broadcast_auxiliaries", joinColumns = @JoinColumn(name = "message_id"))
	private List<Content> contents;

	// 广播发起者信息
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "creator_id")
	private Integer creatorId;
	@Column(name = "creator_name")
	private String creatorName;
	@Column(name = "organ_name")
	private String organName;
	@Column(name = "organ_code")
	private String organCode;
	@Column(name = "region_name")
	private String regionName;
	@Column(name = "region_code")
	private String regionCode;

	// 广播发布情况
	@Enumerated(EnumType.ORDINAL)
	private Status status; // 广播发布状态

	@Column(name = "end_time")
	private Timestamp endTime; // 结束时间

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "media_source")
	private MediaContent.Source mediaSource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String[] getTargets() {
		return targets;
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public EventLevel getEventLevel() {
		return eventLevel;
	}

	public void setEventLevel(EventLevel eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getLoop() {
		return loop;
	}

	public void setLoop(int loop) {
		this.loop = loop;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<Content> getContents() {
		return contents;
	}

	public Content getDeviceContent() {
		for (Content content : contents) {
			if (content.getDeviceChannel() != null) {
				return content;
			}
		}

		return null;
	}
	
	public Content getProgramContent() {
		for (Content content : contents) {
			if (content.getProgram() != null) {
				return content;
			}
		}

		return null;
	}
	
	public Content getContent(MediaType type) {
		for (Content content : contents) {
			if (type.equals(content.getType())) {
				return content;
			}
		}

		return null;
	}

	public void setContents(List<Content> contents) {
		this.contents = contents;
	}

	public void addContent(Content content) {
		if (contents == null)
			contents = new ArrayList<>();

		contents.add(content);
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Transient
	public String getRegion() {
		return RegionUtil.removeZero(source.substring(4, 16));
	}

	public int getRegionLevel() {
		switch (getRegion().length()) {
		case 2:
			return 1;
		case 4:
			return 2;
		case 6:
			return 3;
		case 9:
			return 4;
		case 12:
			return 5;
		default:
			return 9;
		}
	}

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganCode() {
		return organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public Status getStatus() {
		if (status == null)
			return Status.NEW;
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public MediaContent.Source getMediaSource() {
		return mediaSource;
	}

	public void setMediaSource(MediaContent.Source mediaSource) {
		this.mediaSource = mediaSource;
	}

	public Broadcast clone() throws CloneNotSupportedException {
		Broadcast newBroadcast = (Broadcast) super.clone();
		List<Content> newContents = new ArrayList<>();
		for (Content content : contents) {
			newContents.add(content.clone());
		}
		newBroadcast.setContents(newContents);
		return newBroadcast;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Broadcast) {
			Broadcast v = (Broadcast) obj;
			return v.getId().equals(getId()) && v.getTargets().equals(getTargets());
		}

		return false;
	}

	/*public static class TargetsConverter implements AttributeConverter<String[], String> {
		@Override
		public String convertToDatabaseColumn(String[] attribute) {
			if (attribute != null && attribute.length > 0)
				return String.join(",", attribute);
			else
				return "";
		}

		@Override
		public String[] convertToEntityAttribute(String dbData) {
			if (dbData != null)
				return dbData.split(",");
			else
				return new String[0];
		}
	}*/

	public static interface BytesHandle {
		boolean open();

		void push(byte[] buf, int size);

		void stop();

		String getRtspURL();
	}

	@Embeddable
	public static class Content implements Cloneable {
		@Enumerated(EnumType.ORDINAL)
		private MediaType type; // 资源类型
		@Convert(converter = Encode.EncodeConvert.class)
		private Encode encode; // 内容编码
		@Column(name = "related_id")
		private Integer relatedID; // 关联资源编号(媒资库ID、数字电视网络资源ID、或通道号)
		private String value; // 内容值(文本则内容本身，其它类型则为文件获取路劲，如果是PUSH、BYTE则保存存储路径)

		@Transient
		private String publish; // 发布流媒体内容的RTSP路径
		@Transient
		private BytesHandle pusher;
		@Transient
		private DeviceChannel deviceChannel;
		@Transient
		private TVProgram program;
		
		public Content() {};

		public MediaType getType() {
			return type;
		}

		public void setType(MediaType type) {
			this.type = type;
		}

		public Encode getEncode() {
			return encode;
		}

		public void setEncode(Encode encode) {
			this.encode = encode;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Integer getRelatedID() {
			return relatedID;
		}

		public void setRelatedID(Integer relatedID) {
			this.relatedID = relatedID;
		}

		public String getPublish() {
			return publish;
		}

		public void setPublish(String publish) {
			this.publish = publish;
		}

		public BytesHandle getPusher() {
			return pusher;
		}

		public void setPusher(BytesHandle pusher) {
			this.publish = pusher.getRtspURL();
			this.pusher = pusher;
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

		public Content clone() throws CloneNotSupportedException {
			return (Content) super.clone();
		}
	}

	/**
	 * 1：应急演练-发布系统演练；2：应急演练-模拟演练；3：应急演练-实际演练；4：应急广播；5：日常广播。其他：预留
	 *
	 */
	public enum Type {
		NONE, RELEASE_MANOEUVRE, SIMULATE_MANOEUVRE, ACTUAL_MANOEUVRE, EMERGENT, DAILY;

		public static Type valueOf(int value) {
			switch (value) {
			case 1:
				return RELEASE_MANOEUVRE;
			case 2:
				return SIMULATE_MANOEUVRE;
			case 3:
				return ACTUAL_MANOEUVRE;
			case 4:
				return EMERGENT;
			case 5:
				return DAILY;
			default:
				return NONE;
			}
		}

		public boolean gt(Type type) {
			return ordinal() > type.ordinal();
		}

		public boolean lt(Type type) {
			return ordinal() < type.ordinal();
		}
	}

	/*public enum Supply {
		NONE, PUSH, EXTERNAL, BYTES, TTS;

		public static Supply valueOf(int value) {
			switch (value) {
			case 1:
				return PUSH;
			case 2:
				return EXTERNAL;
			case 3:
				return BYTES;
			case 4:
				return TTS;
			default:
				return NONE;
			}
		}
	}*/

	public enum EventLevel {
		NONE, MAJOR, GREAT, LARGER, DAILY;

		public static EventLevel valueOf(int value) {
			switch (value) {
			case 1:
				return MAJOR;
			case 2:
				return GREAT;
			case 3:
				return LARGER;
			case 4:
				return DAILY;
			default:
				return NONE;
			}
		}

		public boolean gt(EventLevel eventLevel) {
			return ordinal() > eventLevel.ordinal();
		}

		public boolean lt(EventLevel eventLevel) {
			return ordinal() < eventLevel.ordinal();
		}
	}

	// 广播状态 0-添加 1-播放中 2-播放结束 3-失败
	public enum Status {
		NEW, PLAYING, PLAYED, FAILURE
	}
}