package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import algo.DijkstraReal;
import circuit.Circuit;
import circuit.CircuitTools;

public class DijkstraObserver implements ObserverSWING{
	
	private BufferedImage circuit;
	
	public DijkstraObserver(Circuit circ){
		this.circuit = CircuitTools.circuitToImage(circ);
		this.drawDij(circ);
	}
	
	public void drawDij(Circuit circ){
		for (int  i =0; i< circ.getHeight() ; i++){
			for (int j = 0 ; j < circ.getWidth() ; j++){
				if (circ.getDist(i, j) < 100000000){
					double v = (circ.getDist(i, j));
					
					Color c = new Color( ((int)v) %255, 0, 0);
					
					//Color c = new Color( 6, 0, 0);
					this.circuit.setRGB(j, i, c.getRGB());
				}
			}
		}
	}

	public void print(Graphics g) {
		g.drawImage(this.circuit,0, 0, null);
	}
}

