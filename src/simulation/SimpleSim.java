package simulation;


import circuit.Circuit;
import voiture.Voiture;
import voiture.Commande;
import strategy.StrategyParam;
import strategy.Strategy;
import circuit.Terrain;
import strategy.Param;

public class SimpleSim{
	
	private Etat etat;
	private Circuit circ;
	private Voiture voitBase;

	public SimpleSim(Circuit c, Voiture voit){
		this.circ = c;
		this.voitBase = voit;

	}


	public int play(Param p, int max){
		Voiture voit = voitBase.clone();
		Strategy strat = new StrategyParam(circ, voit, p);
		int compteur = 0;
		etat= Etat.RUN;
		
		while (etat == Etat.RUN){
			compteur++;
			Commande c = strat.getCommande();
		
			voit.drive(c);
			etat = majEtat(voit);
			
			
			if (compteur > max){
				etat = Etat.LOSE;
				System.out.println("-1");
			}
		}
		

		if (etat == Etat.LOSE){
			return 1000000;// infini
		}
		return compteur;
	}


	private Etat majEtat(Voiture voit){
		if (this.circ.getTerrain(voit.getPosition()).isRunable()== true)
		{
			if (this.circ.getTerrain(voit.getPosition()) == Terrain.EndLine){
				if(voit.getDirection().produitScalaire(
					this.circ.getDirectionArrivee()			) > 0 ){
					System.out.println("Gagne");
					return Etat.WIN;
					
				}
				else{
					System.out.println("-1");
					return Etat.LOSE;
					
				}
			}
		}
		else {
			return Etat.LOSE;
		}
		return Etat.RUN;
	}

}