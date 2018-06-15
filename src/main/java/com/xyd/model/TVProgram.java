package com.xyd.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class TVProgram {
	@Column(table="eb_media_tv")
    private Integer code; //节目编码
    
	@Column(table="eb_media_tv", name="audio_pid")
	private Integer audioPid;		// 音频PID 0x0021 - 0x1FFF
	@Column(table="eb_media_tv", name="video_pid")
	private Integer videoPid;		// 视频PID 0x0021 - 0x1FFF
	@Column(table="eb_media_tv", name="pcr_pid")
	private Integer pcrPid;			// PCRPID 0x0021 - 0x1FFF

	@Enumerated(EnumType.ORDINAL)
	@Column(table="eb_media_tv", name="singal_mode")
    private QAM.SingalMode singalMode; //信号模式
	@Column(table="eb_media_tv")
    private Integer freq;	// 调制频率
	@Column(table="eb_media_tv")
    private Integer qam;
	@Column(table="eb_media_tv", name="rate_speed")
    private Integer rateSpeed;  //音频带宽
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Integer getAudioPid() {
		return audioPid;
	}
	public void setAudioPid(Integer audioPid) {
		this.audioPid = audioPid;
	}
	public Integer getVideoPid() {
		return videoPid;
	}
	public void setVideoPid(Integer videoPid) {
		this.videoPid = videoPid;
	}
	public Integer getPcrPid() {
		return pcrPid;
	}
	public void setPcrPid(Integer pcrPid) {
		this.pcrPid = pcrPid;
	}
	public QAM.SingalMode getSingalMode() {
		return singalMode;
	}
	public void setSingalMode(QAM.SingalMode singalMode) {
		this.singalMode = singalMode;
	}
	public Integer getFreq() {
		return freq;
	}
	public void setFreq(Integer freq) {
		this.freq = freq;
	}
	public Integer getQam() {
		return qam;
	}
	public void setQam(Integer qam) {
		this.qam = qam;
	}
	public Integer getRateSpeed() {
		return rateSpeed;
	}
	public void setRateSpeed(Integer rateSpeed) {
		this.rateSpeed = rateSpeed;
	}
}
