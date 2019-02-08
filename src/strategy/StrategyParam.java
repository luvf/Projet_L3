package strategy;

import algo.Radar;
import algo.RadarClassique;
import algo.RadarDijkstra;
import circuit.Circuit;
import voiture.Commande;
import voiture.Voiture;

import circuit.Terrain;

public class StrategyParam extends ScorableStrategy{

	private Param param; // [  
	private Radar radarDij;
	private Radar radarClassique;
	
	
	public StrategyParam(Circuit c, Voiture v, double[][] p){
		this(c, v, new Param(p));
	}
	
	public StrategyParam(Circuit c, Voiture v, Param param) {
		super(c, v);
		this.param = param;
		double[] t = {0};
		
		this.radarClassique = new RadarClassique(v, c, t);
		this.radarDij = new RadarDijkstra(v, c , (int) (param.nbfaisceau*100)+1);
		
		
	}


	public int getScore() {
		return 8;
	}


	public Commande getCommande() { 
		
		int indice = this.radarDij.getBestIndex();
		double braq = voit.getBraquage();
		double maxTurn = voit.getMaxTurn();
		
		double turn = this.radarDij.thetas()[indice]/braq;
		double acc = 1;
		
		double mur = this.radarClassique.scores()[this.radarClassique.getBestIndex()];
		
		int i= 0;

		if(this.circ.getTerrain(this.voit.getPosition()) == Terrain.Route){
			for(int j=0; j < param.distFreinMurR.length; j++){
				if (param.distFreinMurR[j] * 3000 < mur){
					i = j;
				}
			}
			acc = param.forceFreinMurR[i];
			if (Math.abs(turn) > maxTurn){
				acc = Math.min(acc, param.turnAccR[(int)(Math.abs(braq))*param.turnAccR.length]);
				turn = Math.signum(turn)*maxTurn;
				
			}
		acc = (acc - 0.5)*2;
		}
		if(this.circ.getTerrain(this.voit.getPosition()) == Terrain.BandeRouge){
			for(int j=0; j < param.distFreinMurBR.length; j++){
				if (param.distFreinMurBR[j] * 3000 < mur){
					i = j;
				}
			}
			acc = param.forceFreinMurBR[i];
			if (Math.abs(turn) > maxTurn){
				acc = Math.min(acc, param.turnAccBR[(int)(Math.abs(braq))*param.turnAccBR.length]);
				turn = Math.signum(turn)*maxTurn;
				
			}
			acc = (acc - 0.5)*2;
		}else{
			for(int j=0; j < param.distFreinMurBB.length; j++){
				if (param.distFreinMurBB[j] * 3000 < mur){
					i = j;
				}
			}
			acc = param.forceFreinMurBB[i];
			if (Math.abs(turn) > maxTurn){
				acc = Math.min(acc, param.turnAccBB[(int)(Math.abs(braq))*param.turnAccBB.length]);
				turn = Math.signum(turn)*maxTurn;
			
		}

		
		acc = (acc - 0.5)*2;
		}


		
		return new Commande(acc, turn);
	}

}

