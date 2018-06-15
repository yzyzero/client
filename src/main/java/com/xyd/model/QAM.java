package com.xyd.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the eb_digital_qams database table.
 * 
 */
@Entity
@Table(name="eb_digital_qams")
@NamedQuery(name="QAM.findAll", query="SELECT d FROM QAM d")
@IdClass(QAMPK.class)
public class QAM implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="resource_id")
	private String resourceId;
	@Id
	@Column(name="singal_mode")
	@Enumerated(EnumType.ORDINAL)
	private SingalMode singalMode;	// 信号模式
	@Id
	private double frequency;		// 调制频率
	@Id
	private Integer qam;			// QAM (16, 32, 64, 128, 256)
	private Integer bandwidth;		// 带宽
	private Integer fec;
	@Column(name="freq_flag")
	private Integer freqFlag;
	@Column(name="header_mode")
	private Integer headerMode;
	@Column(name="interleaving_mode")
	private Integer interleavingMode;
	private Integer modulation;
	private Integer subcarrier;

	@Transient
	private int occupiedBandwidth;//占用带宽
	
	public QAM() {
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public SingalMode getSingalMode() {
		return singalMode;
	}

	public void setSingalMode(SingalMode singalMode) {
		this.singalMode = singalMode;
	}

	public double getFrequency() {
		return frequency;
	}

	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}

	public Integer getQam() {
		return qam;
	}

	public void setQam(Integer qam) {
		this.qam = qam;
	}

	public Integer getBandwidth() {
		return this.bandwidth;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
	}

	public Integer getFec() {
		return this.fec;
	}

	public void setFec(Integer fec) {
		this.fec = fec;
	}

	public Integer getFreqFlag() {
		if(this.freqFlag==null)
		{
			return 0;
		}
		return this.freqFlag;
	}

	public void setFreqFlag(Integer freqFlag) {
		this.freqFlag = freqFlag;
	}

	public Integer getHeaderMode() {
		if(this.headerMode==null)
		{
			return 0;
		}
		return this.headerMode;
	}

	public void setHeaderMode(Integer headerMode) {
		this.headerMode = headerMode;
	}

	public Integer getInterleavingMode() {
		if(this.interleavingMode==null)
		{
			return 0;
		}
		return this.interleavingMode;
	}

	public void setInterleavingMode(Integer interleavingMode) {
		this.interleavingMode = interleavingMode;
	}

	public Integer getModulation() {
		if(this.modulation==null)
		{
			return 0;
		}
		return this.modulation;
	}

	public void setModulation(Integer modulation) {
		this.modulation = modulation;
	}

	public Integer getSubcarrier() {
		if(this.subcarrier==null)
		{
			return 0;
		}
		return this.subcarrier;
	}

	public void setSubcarrier(Integer subcarrier) {
		this.subcarrier = subcarrier;
	}

	public int getOccupiedBandwidth() {
		return occupiedBandwidth;
	}
	public void setOccupiedBandwidth(int occupiedBandwidth) {
		this.occupiedBandwidth = occupiedBandwidth;
	}
	

	public enum SingalMode {
		DVB_C, DTMB, DVB_S;
	}
}