package org.auscope.nvcl.server.vo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.auscope.nvcl.server.util.Utility;

@XmlRootElement(name = "SpectralLog")
@XmlType(propOrder={"logID", "logName","wavelengthUnits","sampleCount","script","wavelengthsasString","fwhmsString","tirqsString"})
public class SpectralLogVo {
	

	private String logID;
	private String logName;
	private int sampleCount;
	private float[] wavelengths;
	private String wavelengthUnits;
	private String script;
	private float[] fwhm;
	private float[] tirq;


	public SpectralLogVo() {
	}

	public String getLogID() {
		return logID;
	}

	public void setLogID(String logID) {
		this.logID = logID;
	}

	public String getLogName() {
		return logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	@XmlElement(name="wavelengths")
	public String getWavelengthsasString() {
		return Utility.floatArrayToString(this.wavelengths);
	}
	
	@XmlTransient
	public float[] getWavelengths() {
		return wavelengths;
	}

	public void setWavelengths(float[] wavelengths) {
		this.wavelengths = wavelengths;
	}

	public String getWavelengthUnits() {
		return wavelengthUnits;
	}

	public void setWavelengthUnits(String wavelengthUnits) {
		this.wavelengthUnits = wavelengthUnits;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}
	@XmlElement(name="fwhm")
	public String getfwhmsString() {
		return Utility.floatArrayToString(this.fwhm);
	}
	
	@XmlTransient
	public float[] getFwhm() {
		return fwhm;
	}

	public void setFwhm(float[] fwhm) {
		this.fwhm = fwhm;
	}

	@XmlElement(name="tirq")
	public String gettirqsString() {
		return Utility.floatArrayToString(this.tirq);
	}
	
	@XmlTransient
	public float[] getTirq() {
		return tirq;
	}

	public void setTirq(float[] tirq) {
		this.tirq = tirq;
	}
}
