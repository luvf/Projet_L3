package strategy;

import voiture.Commande;

public class StrategyConst implements Strategy{

	public final Commande command;
	
	public StrategyConst(Commande command){
		this.command = command;
		
	}
	
	public Commande getCommande() {
		return this.command;
	}

	public Strategy clone() {
		return new StrategyConst(this.command);
	}

}
