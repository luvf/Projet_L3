package strategy;

import circuit.Circuit;
import algo.Dijkstra;
import algo.Radar;
import algo.RadarDijkstra;
import algo.RadarClassique;

import voiture.Commande;
import voiture.Voiture;

public class StrategyRadar extends ScorableStrategy{

	
	private Radar radar;
	private int nbFaisceau;
	private double freinage;
	
	
	
	public StrategyRadar(Circuit circ, Voiture v, int nbFaisceau){
		this(circ, v, nbFaisceau,  -.9);
		//this.radar = new RadarClassique(v, circ, 35);

	}
	public StrategyRadar(Circuit circ, Voiture v, int nbFaisceau, double freinage){
		super(circ, v);
		this.nbFaisceau = nbFaisceau;
		this.radar = new RadarDijkstra(v, circ, this.nbFaisceau);
		this.freinage = freinage;
		//this.radar = new RadarClassique(v, circ, 35);

	}
	
	public Commande getCommande() {
		int indice = this.radar.getBestIndex();
		double braq = voit.getBraquage();
		double maxTurn = voit.getMaxTurn();
		double angl = this.radar.thetas()[indice] / braq ;
		double acc = 1;

		if (Math.abs(angl) > maxTurn){
			angl = Math.signum(angl)*maxTurn;
			acc = freinage;
		}
		
		return new Commande(acc, angl);

	}

	public Strategy clone() {
		return new StrategyRadar(this.circ, this.voit, this.nbFaisceau);
	}

	public int getScore() {
		return 5;
	} 

}
