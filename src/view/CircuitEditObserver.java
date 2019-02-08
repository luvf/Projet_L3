package view;

import circuit.CircuitEdit;
import circuit.CircuitTools;

import java.awt.Graphics;
import java.awt.image.BufferedImage; 



public class CircuitEditObserver implements ObserverSWING{
	private BufferedImage image;
	private CircuitEdit circ;

	public CircuitEditObserver(CircuitEdit circ) {
		this.circ = circ;
		
	}

	public void print(Graphics g) {
		g.drawImage(CircuitTools.circuitToImage(circ),0, 0, null);

	}
}

