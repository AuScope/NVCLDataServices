package org.auscope.nvcl.server.vo;

public class ClassificationVo {
	
	private int index;
    private String classText;
    private int colour;
    
	public ClassificationVo() {
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getClassText() {
		return classText;
	}

	public void setClassText(String classText) {
		this.classText = classText;
	}

	public int getColour() {
		return colour;
	}

	public void setColour(int colour) {
		this.colour = colour;
	}
    
}
