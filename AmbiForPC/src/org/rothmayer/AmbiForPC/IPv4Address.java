package org.rothmayer.AmbiForPC;

public class IPv4Address {
	
	int firstByte;
	int secondByte;
	int thirdByte;
	int fourthByte;
	
	public IPv4Address(byte firstByte, byte secondByte, byte thirdByte, byte fourthByte) {
		this.firstByte = firstByte;
		this.secondByte = secondByte;
		this.thirdByte = thirdByte;
		this.fourthByte = fourthByte;
	}
	
	public IPv4Address(String address) throws Exception{
		
		firstByte = Integer.parseInt(address.substring(address.indexOf(".")));
		if(firstByte > 255 || firstByte < 1){
			throw new Exception();
		}
		
		address = address.substring(address.indexOf("."), address.length());
		secondByte = Integer.parseInt(address.substring(address.indexOf(".")));
		if(secondByte > 255 || secondByte < 1){
			throw new Exception();
		}
		
		address = address.substring(address.indexOf("."), address.length());
		thirdByte = Integer.parseInt(address.substring(address.indexOf(".")));
		if(thirdByte > 255 || thirdByte < 1){
			throw new Exception();
		}
		
		address = address.substring(address.indexOf("."), address.length());
		fourthByte = Integer.parseInt(address.substring(address.indexOf(".")));
		if(fourthByte > 255 || fourthByte < 1){
			throw new Exception();
		}
	}

	public int getFirstByte() {
		return firstByte;
	}

	public int getSecondByte() {
		return secondByte;
	}

	public int getThirdByte() {
		return thirdByte;
	}

	public int getFourthByte() {
		return fourthByte;
	}
	
	public String getAddress(){
		return ""+ firstByte + "." + secondByte + "." + thirdByte + "." + fourthByte;
	}

}
