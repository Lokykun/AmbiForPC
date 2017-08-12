package org.rothmayer.AmbiForPC;

public class PColor {
	
	private int red;
	private int green;
	private int blue;
	
	public PColor(int color) {
		
		red = (color & 0x00ff0000) >> 16;
	    green = (color & 0x0000ff00) >> 8;
	    blue = color & 0x000000ff;
	}

	public PColor(int red, int green, int blue) {
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}
	
	public byte[] getBytes(){
		return new byte[]{(byte)getRed(),(byte)getGreen(),(byte)getBlue()};
	}
	
	

}
