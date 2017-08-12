package org.rothmayer.AmbiForPC;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Window;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestManager {
	
	JFrame frame;
	JPanel w;
	int num;
	Rectangle bound;
	HashMap<Rectangle, Color> map;
	boolean test = false;
	
	public TestManager(int num, Rectangle bound) {
		frame = new JFrame();
		frame.setBounds(bound);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		map = new HashMap<>();
		w = new JPanel(null)
		
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 2920785172532900449L;
			@Override
			  public void paint(Graphics g)
			  {
				super.paint(g);
				if(!AmbiForPC.test){
					return;
				}
				//System.out.println("paint" + map.size());
			    final Font font = getFont().deriveFont(48f);
			    g.setFont(font);
			    g.setColor(Color.RED);
			    final String message = ""+num;
			    FontMetrics metrics = g.getFontMetrics();
			    g.drawString(message, (bound.x-bound.width-metrics.stringWidth(message))/2,(bound.y-bound.height-metrics.getHeight())/2);
			    for (Rectangle rec : map.keySet()) {
			    	Color c = map.get(rec);
			    	g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue()));
			    	System.out.println(rec.x+ " " + rec.y+ " " +  rec.width+ " " +  rec.height);
			    	g.fillRect(rec.x, rec.y, rec.width, rec.height);
				}
			    
			    g.fillRect(-1920, 0, 100, 100);
			  }
			  @Override
			  public void update(Graphics g)
			  {
			    paint(g);
			  }
			};
		
		this.num = num;
		this.bound = bound;
		//w.setAlwaysOnTop(true);
		w.setOpaque(false);
		w.setLocation(bound.x, bound.y);
		System.out.println(w.getX() + " " + w.getY());
		w.setBounds(bound);
		//w.setBounds(0, 0, 1920, 1080);
		//w.setBackground(new Color(0, true));
		w.setBackground(Color.GRAY);
		//w.setBackground(new Color(0,0,0,0));
		w.setVisible(true);
		frame.getContentPane().add(w, BorderLayout.CENTER);
		frame.setVisible(true);
	}
	
	public void addRec(Rectangle r, Color color){
		map.put(r, color);
	}
	
	public void clear(){
		map.clear();
	}
	
	public void update(){
		frame.repaint();
	}

	public boolean isTest() {
		return test;
	}

	public void setTest(boolean test) {
		this.test = test;
	}

	public int getNum() {
		return num;
	}
	
	

}
