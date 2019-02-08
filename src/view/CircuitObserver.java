package view;

import circuit.Circuit;
import circuit.CircuitTools;

import java.awt.Graphics;
import java.awt.image.BufferedImage; 



public class CircuitObserver implements ObserverSWING{
	private BufferedImage image;


	public CircuitObserver(Circuit circ) {
		this.image = CircuitTools.circuitToImage(circ);	
	}


	public void print(Graphics g) {
		g.drawImage(this.image,0, 0, null);
	}
}

