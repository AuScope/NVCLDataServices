package org.auscope.nvcl.server.vo;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ProfDataVo {

    private int sampleNo;
    private byte[] profdata;
    
	public int getSampleNo() {
		return sampleNo;
	}
	public void setSampleNo(int sampleNo) {
		this.sampleNo = sampleNo;
	}
	public byte[] getProfdata() {
		return profdata;
	}
	public void setProfdata(byte[] profdata) {
		this.profdata = profdata;
	}

	public float[] getFloatprofdata() {
		float[] specfloats = new float[profdata.length/4];
		for (int i=0;i<profdata.length;i+=4){
			ByteBuffer buffer = ByteBuffer.wrap(profdata,i,4).order(ByteOrder.LITTLE_ENDIAN);
			specfloats[i/4]= buffer.getFloat();
		}
		return specfloats;
	}

	public void setprofdata(byte[] profdata) {
		this.profdata = profdata;
	}
	@JsonIgnore
	public byte[] getprofdata() {
		return profdata;
	}

}
