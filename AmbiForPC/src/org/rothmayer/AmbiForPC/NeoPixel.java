package org.rothmayer.AmbiForPC;

public class NeoPixel {

	byte pos;
	PColor color;
	
	public NeoPixel(byte pos, PColor color) {
		this.pos = pos;
		this.color = color;
	}

	public byte getPos() {
		return pos;
	}

	public PColor getColor() {
		return color;
	}
	
	public byte[] getBytes(){
		return new byte[]{(byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue()};
	}
}
