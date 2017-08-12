package org.rothmayer.AmbiForPC;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public class Display {
	
	private int width;
	private int height;
	private int realWidth;
	private int realHeight;
	private int offsetX;
	private int offsetY;
	private int realOffsetX;
	private int realOffsetY;
	private int number;
	private GraphicsDevice device;
	
	
	public Display(int number, GraphicsDevice device) {
		this.device = device;
		width = device.getDisplayMode().getWidth();;
		height = device.getDisplayMode().getHeight();
		realWidth = device.getDefaultConfiguration().getBounds().width;
		realHeight = device.getDefaultConfiguration().getBounds().height;
		realOffsetX = device.getDefaultConfiguration().getBounds().x;
		realOffsetY = device.getDefaultConfiguration().getBounds().y;
		double scale = ((double)realHeight)/((double) height);
		offsetX = (int) (realOffsetX/scale);
		offsetY = (int) (realOffsetY/scale);
		this.number = number;
		System.out.println("Device detected " + number + " X: " + offsetX + " Y: " + offsetY + " width: " + width + " height: " + height);
		
		//PColor color = new Capture(offsetX+1000, offsetY+1000, 200, 200).getColor();
		//System.out.println(offsetX+1000 + " " + offsetY+1000);
		if(2 == number){
			AmbiForPC.tManager = new TestManager(number, new Rectangle(offsetX, offsetY, width, height));
		}
		
		
		
		
		
		//long time = System.currentTimeMillis();
		//System.out.println("Red " + color.getRed() + " Green " + color.getGreen() + " Blue " + color .getBlue());
		//System.out.println("Time: " + (System.currentTimeMillis()-time) + "ms");
		
	}
	
	public byte[] getData(Integer inset, Integer vPixel, Integer hPixel){
		vPixel+=2;
		int heighSep = height/vPixel;
		int widthSep = width/hPixel;
		
		List<Byte> list = new ArrayList<>();
		
		int num = 0;

		Robot robot;
		try {
			robot = new Robot();
		
		BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(offsetX, offsetY, width, height));
		
		int x = width-inset;
		int y = height-2*heighSep;
		
		for(int i = 0; i < vPixel-2; i++){
			//System.out.println(num + " " + i+ " " + x + " " + y + " " + inset + " " + heighSep);
			Color color = new Capture(x ,y , inset, heighSep).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			if(AmbiForPC.test){
				AmbiForPC.tManager.addRec(new Rectangle(x, y, widthSep, heighSep), color);
			}
			//System.out.println("B: " + color.getBlue() + " R: " + color.getRed());
			y -= heighSep;
			num++;
		}
		
		x = width-widthSep;
		y = 0;
		
		for(int i = 0; i < hPixel; i++){
			//System.out.println(num + " " + i+ " " + x + " " + y + " " + widthSep + " " + inset);
			Color color = new Capture(x ,y , widthSep, inset).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			if(AmbiForPC.test){
				AmbiForPC.tManager.addRec(new Rectangle(x, y, widthSep, heighSep), color);
			}
			x -= widthSep;
			num++;
		}
		
		x = 0;
		y = heighSep;
		
		for(int i = 0; i < vPixel-2; i++){
			//System.out.println(num + " " + i +" " + x + " " + y + " " + inset + " " + heighSep);
			Color color = new Capture(x ,y , inset, heighSep).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			if(AmbiForPC.test){
				AmbiForPC.tManager.addRec(new Rectangle(x ,y , inset, heighSep), color);
			}
			y += heighSep;
			num++;
		}
		
		x = 0;
		y = height-inset;
		
		for(int i = 0; i < hPixel; i++){
			//System.out.println(num + " " + i+ " " + x + " " + y + " " + widthSep + " " + inset);
			Color color = new Capture(x ,y , widthSep, inset).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			if(AmbiForPC.test){
				AmbiForPC.tManager.addRec(new Rectangle(x ,y , widthSep, inset), color);
			}
			x += widthSep;
			num++;
		}
		
		/*for(int y = offsetY+height+heighSep; y >= offsetY+1; y -= heighSep){
			
			int tX = offsetX+width-inset;

			System.out.println(tX + " " + y  + " " + inset + " " + heighSep + "-" + num);
			PColor color = new Capture(tX ,y , inset, heighSep).getColor(bufferedImage);

			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			//AmbiForPC.tManager.addWindow(num, new Rectangle(offsetY+width-inset,y , heighSep, inset));
			num++;
		}

		int tn = 1;
		for(int x = offsetX+width; x >= offsetX+1; x -= widthSep){
			PColor color = new Capture(x, offsetY+height-inset, inset, widthSep).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			//AmbiForPC.tManager.addWindow(num, new Rectangle(offsetX+height-inset, y, inset, widthSep));
			System.out.println(x + " " + (offsetY+height-inset)  + " " + inset + " " + heighSep + "-" + tn);
			num++;
			tn++;
		}
		
		for(int y = offsetY; y <= offsetY+height+heighSep-1; y += heighSep){
			PColor color = new Capture(offsetX, y, heighSep, inset).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			//AmbiForPC.tManager.addWindow(num, new Rectangle(x, offsetY, heighSep, inset));
			num++;
		}
		
		for(int x = offsetX; x <= offsetX+width+widthSep-1; x += widthSep){
			PColor color = new Capture(x, offsetY, inset, widthSep).getColor(bufferedImage);
			list.add((byte)color.getRed());
			list.add((byte)color.getGreen());
			list.add((byte)color.getBlue());
			//AmbiForPC.tManager.addWindow(num, new Rectangle(offsetX, y, inset, widthSep));
			num++;
		}*/
		Byte[] data = new Byte[list.size()];
		data = list.toArray(data);
		return ArrayUtils.toPrimitive(data);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}


	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


	public int getRealWidth() {
		return realWidth;
	}


	public int getRealHeight() {
		return realHeight;
	}


	public int getOffsetX() {
		return offsetX;
	}


	public int getOffsetY() {
		return offsetY;
	}


	public int getNumber() {
		return number;
	}


	public GraphicsDevice getDevice() {
		return device;
	}
	
	public String toString(){
    	return "Device " + number + " X: " + offsetX + " Y: " + offsetY + " width: " + width + " height: " + height;
	}

	

}
