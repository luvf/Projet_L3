package simulation;

import geometrie.Vecteur;
import algo.Dijkstra;
import circuit.Circuit;
import circuit.CircuitTools;




import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class CircuitAffichage{
	private BufferedImage circuit;
	private BufferedImage circuitSansVoiture;


	public CircuitAffichage(Circuit circ){
		this.circuitSansVoiture = CircuitTools.circuitToImage(circ);
		this.circuit = CircuitTools.circuitToImage(circ);
	}

	public void updatePosition(Vecteur p){
		this.circuit.setRGB((int)p.x, (int)p.y, Color.black.getRGB());
	}
	
	public void drawDij(Dijkstra dij){
		for (int  i =0; i< dij.height ; i++){
			for (int j = 0 ; j < dij.width ; j++){
				if (dij.getDist(i, j) != Integer.MAX_VALUE){
					int v = (dij.getDist(i, j));
					Color c = new Color( v %255, 0, 0);
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


}