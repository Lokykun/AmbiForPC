package org.rothmayer.AmbiForPC;

import java.net.InetAddress;

public class Configuration {
	int ledHorizontal;
	int ledVertical;
	int displayNumber;
	int inset;
	StartPosition startPosition;
	boolean clockwise;
	boolean useCom;
	int comPort;
	int bautrate;
	int frames;
	boolean useUDP;
	int port;
	
	public Configuration(int ledHorizontal, int ledVertical, int displayNumber, int inset, StartPosition startPosition,
			boolean clockwise, boolean useCom, int comPort, int bautrate, int frames, boolean useUDP) {
		super();
		this.ledHorizontal = ledHorizontal;
		this.ledVertical = ledVertical;
		this.displayNumber = displayNumber;
		this.inset = inset;
		this.startPosition = startPosition;
		this.clockwise = clockwise;
		this.useCom = useCom;
		this.comPort = comPort;
		this.bautrate = bautrate;
		this.frames = frames;
		this.useUDP = useUDP;
	}

}
