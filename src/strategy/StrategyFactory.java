package strategy;

import voiture.Commande;

public class StrategyFactory{

        public Strategy liste;

        public StrategyFactory(Commande com[]){
                this.liste = new StrategyListeCommande(com);
        }


}