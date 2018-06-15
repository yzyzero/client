package com.xyd.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the eb_digital_qams database table.
 * 
 */
@Embeddable
public class QAMPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String resourceId;

	private QAM.SingalMode singalMode;

	private double frequency;

	private Integer qam;

	public QAMPK() {
	}
	public String getResourceId() {
		return this.resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public QAM.SingalMode getSingalMode() {
		return this.singalMode;
	}
	public void setSingalMode(QAM.SingalMode singalMode) {
		this.singalMode = singalMode;
	}
	public double getFrequency() {
		return this.frequency;
	}
	public void setFrequency(double frequency) {
		this.frequency = frequency;
	}
	public Integer getQam() {
		return this.qam;
	}
	public void setQam(Integer qam) {
		this.qam = qam;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof QAMPK)) {
			return false;
		}
		QAMPK castOther = (QAMPK)other;
		return 
			this.resourceId.equals(castOther.resourceId)
			&& this.singalMode.equals(castOther.singalMode)
			&& (this.frequency == castOther.frequency)
			&& this.qam.equals(castOther.qam);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.resourceId.hashCode();
		hash = hash * prime + this.singalMode.hashCode();
		hash = hash * prime + ((int) (java.lang.Double.doubleToLongBits(this.frequency) ^ (java.lang.Double.doubleToLongBits(this.frequency) >>> 32)));
		hash = hash * prime + this.qam.hashCode();
		
		return hash;
	}
}