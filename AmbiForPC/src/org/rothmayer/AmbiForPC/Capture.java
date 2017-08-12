package org.rothmayer.AmbiForPC;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class Capture {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Capture(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		
	}
	
	public Color getColor(BufferedImage bufferedImage){
		
		int red = 0;
		int blue = 0;
		int green = 0;
		int count = 0;
		
		//Rectangle captureSize = new Rectangle(x, y, width, height);

		//System.out.println(" x: " + x + " y: " + y );
		for(int y2 = y ; y2 < y+ height; y2++){
			for(int x2 = x ; x2 < x+ width; x2++){
				Color color = new Color(bufferedImage.getRGB(x2, y2));
				
				red += color.getRed();
				blue += color.getBlue();
				green += color.getGreen();
				count++;
			}
		}

		
		
		Color c = new Color(red/count, green/count, blue/count);
		//System.out.println("Red: " + c.getRed() + " Blue: " + c.getBlue() + " Green: " + c.getGreen() + " x: " + x + " y: " + y );
		return c;
	}
	
	

}
