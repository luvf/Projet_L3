package view;

import geometrie.Vecteur;
import algo.Dijkstra; 
import algo.DijkstraReal;

import circuit.Circuit; 
import circuit.CircuitTools;

import view.ObserverImage;


import java.awt.Color; 
import java.awt.image.BufferedImage; 
import java.io.File;
import javax.imageio.ImageIO;

import java.util.ArrayList;



public class CircuitAffichage implements UpdateEventListener{
	private BufferedImage circuit;
	//private BufferedImage circuitSansVoiture;

	private ArrayList<ObserverImage> obs;

	public CircuitAffichage(Circuit circ){
		this.obs = new ArrayList<ObserverImage>();
		//this.circuitSansVoiture = CircuitTools.circuitToImage(circ);
		this.circuit = CircuitTools.circuitToImage(circ);
	}

	public void updatePosition(Vecteur p){
		this.circuit.setRGB((int)p.x, (int)p.y, Color.black.getRGB());
	}
	
	public void drawDij(DijkstraReal dij){// a mettre dans un obs
		for (int  i =0; i< dij.height ; i++){
			for (int j = 0 ; j < dij.width ; j++){
				if (dij.getDist(i, j) != Double.POSITIVE_INFINITY){
					double v = (dij.getDist(i, j));
					Color c = new Color( ((int)v*10) %255, 0, 0);
					this.circuit.setRGB(i, j, c.getRGB());
				}
			}
		}
	}


	public void save(String filename){
		try {
				File outputfile = new File(filename);
				ImageIO.write(this.circuit, "png", outputfile);
			} catch (Exception e){	
				System.out.println("Erreur dans la sauvegarde de l'image :\n" + e.getMessage());
			}
	}

	public void manageUpdate(){
		for (ObserverImage ob: obs){
			ob.print(this.circuit);
		}
	}

	public void add(ObserverImage obs){
		this.obs.add(obs);
	}
	
}