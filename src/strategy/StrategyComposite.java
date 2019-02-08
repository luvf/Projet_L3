package strategy;
import voiture.Commande;

import circuit.Circuit;

import voiture.Voiture;

import java.util.ArrayList;



public class StrategyComposite implements Strategy{
	private ArrayList<ScorableStrategy> strats;
	private StrategyListeCommande listeInit;
	private Circuit circ;
	private Voiture voit;
	
	public StrategyComposite(Circuit circ, Voiture voit, StrategyListeCommande liste){
		this.strats = new ArrayList<ScorableStrategy>();
		this.listeInit = liste;
		this.voit = voit;
		this.circ = circ;
	}
	public StrategyComposite(Circuit circ, Voiture voit){
		this(circ, voit, new StrategyListeCommande(new ArrayList<Commande>()));
	}
	
	public StrategyComposite(Circuit circ, Voiture voit, ArrayList<Commande> liste){
		this(circ, voit, new StrategyListeCommande(liste));
	}

	public Strategy clone(){
		return new StrategyComposite(circ, voit);
	}
	public Commande getCommande(){
		if (listeInit != null && this.listeInit.remain()>=1){
			//System.out.println(this.listeInit.remain());
			return listeInit.getCommande();
		}
		int index=0, best=0;
		for (int i = 0 ; i< this.strats.size(); i++){
			if (this.strats.get(i).getScore() > best){
				best = this.strats.get(i).getScore();
				index = i;
			}
		}
		return this.strats.get(index).getCommande();
	}
	public void add(ScorableStrategy strat){
		this.strats.add(strat);
	}
	
}