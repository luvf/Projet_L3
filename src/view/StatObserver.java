package view;

import java.awt.Color;
import java.awt.Graphics;

import simulation.Simulation;
import voiture.Voiture;
import java.text.DecimalFormat;
import voiture.Commande;
import geometrie.Vecteur;

public class StatObserver implements ObserverSWING{
	private Simulation sim;
	
	
	public StatObserver(Simulation sim){
		this.sim = sim;
		//eventuellemnt le poiint ou on dessine
	}

	private static String VectToString(Vecteur v) {
		return "[ " +new DecimalFormat("#.0###").format(v.x) + " : " + new DecimalFormat("#.0###").format(v.y)+" ]";
	}
	public void print(Graphics g) {
		int x = 700;
		int y = 50 ;
		int cur = this.sim.curIter();
		g.drawString("Tour " + Integer.toString(cur),  x , 20);
		
		if (cur >= 0){
			for (int i =0; i< sim.nbVoitures();i++){
				g.setColor(Color.black);
				Voiture voit = this.sim.getVoiture(0, cur);
				String score = "score : " + Integer.toString(sim.getScore(i));
				String nbVoit = "Voiture : " + Integer.toString(i);
				String vit  = "Vitesse : " + new DecimalFormat("#.0###").format(voit.getVitesse());
				String pos  = "Position : " + VectToString(voit.getPosition());
				String dir  = "Direction : " + VectToString(voit.getDirection());
				String com  = "Commande : ";
		
			if (cur > 0){
				Commande co= this.sim.getCommande(0 ,cur-1);
				com += "[ "+ new DecimalFormat("#.0###").format(co.acceleration) + " : ";
				com += new DecimalFormat("#.0###").format(co.rotation)+" ]";

			}
							
			g.drawString(score,  x + 0, y +10);
			g.drawString(nbVoit,  x + 0, y + 25);
			g.drawString(vit,  x + 0, y + 40);
			g.drawString(pos,  x + 0, y + 55);
			g.drawString(dir,  x + 0, y + 70);
			g.drawString(com,  x + 0, y + 85);
			y+=100;
			
			}
		}	
	}
	

}
