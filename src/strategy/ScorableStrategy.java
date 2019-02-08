package strategy;

import circuit.Circuit;
import voiture.Commande;
import voiture.Voiture;

public abstract class ScorableStrategy implements Strategy {
	
	protected Circuit circ;
	protected Voiture voit;
	
	public ScorableStrategy(Circuit c, Voiture v){
		this.circ = c;
		this.voit = v;
		
	}
	
	public abstract int getScore();

	@Override
	public abstract Commande getCommande();

	

}
