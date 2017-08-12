package org.rothmayer.AmbiForPC;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

public class PixelSender {
	public AmbiForPC pc;
	Timer timer;
	Timer rainbowTimer;
	Queue<byte[]> queue;
	boolean stopped =false;
	
	

	public PixelSender(AmbiForPC pc) {
		this.pc = pc;
		
		
	}
	
	public void startTask(){
		stopped = false;
		queue = new LinkedList<>();
		TimerTask UDPtask = new TimerTask(){

			 public void run() {
					 byte[] value = pc.dis.getData(Integer.parseInt(pc.textFieldInSet.getText()), 16, 28);
					 
					 if(queue.size() > 7500){
						 byte[] ref = queue.poll();
						 boolean same = true;
						 for (byte[] bs : queue) {
							 //System.out.println(Arrays.equals(ref, queue.peek()));
							 if(!Arrays.equals(ref, bs)){
								 same = false;
								 break;
							 }
							
						}
						 if(same || stopped){
							 blackScreen();
						 }else{
							 if(!stopped){
								 pc.test3(value);
							 }
						 }
					 }else{
						 if(!stopped){
							 pc.test3(value);
						 }
					 }
					 queue.offer(value);
					 AmbiForPC.tManager.update();
				 }
				 
				 
			 
			};
			timer = new Timer();
			timer.schedule(UDPtask, 0, 40);
			
	}
	
	public void stopTimer(){
		stopped = true;
		timer.cancel();
		blackScreen();
	}
	
	public void blackScreen(){
		byte[] blackscreen = new byte[264];
		 for(int i = 0; i < blackscreen.length; i++){
			 blackscreen[i] = 0;
		 }
		 pc.test3(blackscreen);
	}

}
