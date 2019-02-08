package strategy;

import voiture.Commande;


public class StrategyForward implements Strategy{

		private Commande com;

		public StrategyForward(){
			this.com = new Commande(1, 0);
		}

		public Strategy clone(){
				return new StrategyForward();
		}

		public Commande getCommande(){
				return new Commande(com) ;
		}
}	