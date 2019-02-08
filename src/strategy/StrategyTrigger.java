package strategy;

import circuit.Circuit;
import circuit.Terrain;
import algo.Dijkstra;
import algo.Radar;
import algo.RadarDijkstra;
import algo.RadarClassique;
import voiture.Commande;
import voiture.Voiture;

public class StrategyTrigger extends ScorableStrategy {
	
	private ScorableStrategy strat;
	private float vMax;
	private Terrain trigger;

	
	
	public StrategyTrigger(Circuit circ, Voiture v,  float vMax, Terrain trig, ScorableStrategy strat){
		super(circ, v);
		this.strat = strat;
		this.vMax =  vMax;
		this.trigger = trig;
		//this.radar = new RadarClassique(v, circ, 35);

	}
	
	public Commande getCommande() {
		Commande com = strat.getCommande();
		

		if(	this.circ.getTerrain(this.voit.getPosition()) == trigger &&
			this.voit.getVitesse() > vMax){
			return new Commande(-1, com.rotation);
		}
		
		return com;
	}

	

	public int getScore() {
		if (this.circ.getTerrain(this.voit.getPosition()) == trigger){
			return 7;
		}
		return 3;
	} 

}
