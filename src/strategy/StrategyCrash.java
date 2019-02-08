package strategy;

import algo.Dijkstra;
import algo.Radar;
import algo.RadarDijkstra;
import circuit.Circuit;
import voiture.Commande;
import voiture.Voiture;

public class StrategyCrash implements Strategy{
	private Voiture voit;
	private Circuit circ;
	
	
	public StrategyCrash(Circuit circ, Voiture v){
		this.voit = v;
		this.circ = circ;
		//this.radar = new RadarClassique(v, circ, 35);

	}
	
	public Commande getCommande() {
		/*int indice = this.radar.getBestIndex();
		double braq = voit.getBraquage();
		double maxTurn = voit.getMaxTurn();
		double angl = this.radar.thetas()[indice] / braq ;
		double acc = 1;

		if (Math.abs(angl) > maxTurn){
			angl = Math.signum(angl)*maxTurn;
			acc = -0.55;
		
		}
		*/
		return new Commande(0, 0);
	}
	public Strategy clone() {
		return new StrategyCrash(this.circ, this.voit);
	}

}
