package exam;

import circuit.Circuit;
import algo.Dijkstra;
import algo.Radar;
import algo.RadarDijkstra;
import algo.RadarClassique;

import voiture.Commande;
import voiture.Voiture;

import strategy.ScorableStrategy;
import strategy.Strategy;

public class StratExam extends ScorableStrategy{

	
	//private Radar radar;
	//private int nbFaisceau;
	//private double freinage;
	
	
	public StratExam(Circuit circ, Voiture v){
		super(circ, v);
		//this.nbFaisceau = nbFaisceau;
		//this.radar = new RadarDijkstra(v, circ, this.nbFaisceau);
		

	}
	
	public Commande getCommande() {
		return null;

	}

	public Strategy clone() {
		return new StratExam(this.circ, this.voit);
	}

	public int getScore() {
		return 5;
	} 

}
