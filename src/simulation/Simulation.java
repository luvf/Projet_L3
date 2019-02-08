package simulation;

import strategy.StrategyComposite;
import strategy.StrategyListeCommande;
import strategy.StrategyRadar;
import strategy.StrategyRecord;
import circuit.Circuit;
import voiture.Voiture;
import voiture.Commande;
import view.CircuitAffichage;
import circuit.Terrain;
import view.UpdateEventSender;
import view.UpdateEventListener;

import java.util.ArrayList;

import controller.StratCompositeController;



public class Simulation implements UpdateEventSender{
	private Circuit circ;
	private Voiture voitInit;
	private ArrayList<StrategyRecord> strat;
	
	private ArrayList<Voiture> voitures;
	private ArrayList<ArrayList<Voiture>> voituresListe;
	private int compteur;
	private int curIter;
	private Boolean simEtat;
	private ArrayList<StratCompositeController> stratCtrl;
	

	private ArrayList<UpdateEventListener> listener;
	

	
	public ArrayList<Etat> etat;

	public Simulation(Circuit c, Voiture v, ArrayList<StratCompositeController> strctrl){
		this.circ = c;
		this.voitInit = v;

		this.etat = new ArrayList<Etat>();
		this.listener = new ArrayList<UpdateEventListener>();
		this.compteur = 0;
		this.curIter = 0;
		this.voitures = new ArrayList<Voiture>();
		this.voituresListe = new ArrayList<ArrayList<Voiture>>();
		this.stratCtrl = strctrl;
		this.simEtat = false;
		
		/*StrategyComposite comp = this.stratCtrl.getStrategy(this.circ, this.voit);
		comp.add(new StrategyRadar(this.circ, this.voit));*/
		//this.strat = new StrategyRecord(null);// il ne devrait pas trop geuler
	}

	public void play(){
		while (this.simEtat == true){
				compteur++;
				curIter++;
				oneStep();
				update();
		}
	}
	/*public void play(int n){

		for (int i=0; i < n; i++){
			
			oneStep();
			
			this.voitures.add(this.voit.clone());
		//	System.out.println(this.strat.getCommande());
		}
	}*/
	
	
	private void oneStep(){
		for (int i=0; i< voitures.size(); i++){
			if (etat.get(i) == Etat.RUN){
				Commande c = this.strat.get(i).getCommande();
				if (c == null){
					this.etat.set(i, Etat.LOSE); //ca veut dire que les commandes sont finies.
					return ;
				}
				voitures.get(i).drive(c);
				this.voituresListe.get(i).add(voitures.get(i).clone());
				
				
			}
			majEtat();
		
		}
		
		
	}

	private void majEtat(){
		for (int i=0; i< voitures.size(); i++){
			Voiture cur = this.voitures.get(i);
			if (this.circ.getTerrain(cur.getPosition()).isRunable()== true)
			{
				if (this.circ.getTerrain(cur.getPosition()) == Terrain.EndLine){
					if(cur.getDirection().produitScalaire(
						this.circ.getDirectionArrivee()			) > 0 ){
						this.etat.set(i, Etat.WIN);
					}
					else{
						this.etat.set(i, Etat.LOSE);
					}
				}
			}
			else {
				this.etat.set(i, Etat.LOSE);
			}
		}
	}

	public void kill(){
		this.simEtat = false;
	}
	

	public void add(UpdateEventListener listener){
		this.listener.add(listener);
	}

	public void update(){
		for (UpdateEventListener li : listener){
			li.manageUpdate();
		}
		
		for (int i=0; i< this.voituresListe.size(); i++) {
			if (this.voituresListe.get(i).size() > this.curIter-1){
				this.stratCtrl.get(i).update(this.voituresListe.get(i).get(this.curIter-1));
			}
		}
	
	}
	
	public int nbVoitures(){
		return this.voitures.size();
	}
	public int getCompteur(){
		return this.compteur;
	}

	public int getScore(int i){
		return this.strat.get(i).getRecord().size();
	}
	
	public int curIter(){
		return this.curIter-1;// verifier l'utilité de ce -1 (apres que ca ait compilé et marche (sinon on risque d'en chier))
	}

	
	public Voiture getVoiture(int i, int j){
		int s =this.voituresListe.get(i).size();
		if (s > j){
			return this.voituresListe.get(i).get(j);
		}else{
			return this.voituresListe.get(i).get(s-1);
		}
	}
	
	public Commande getCommande(int i, int j){
		int s =this.strat.get(i).getRecord().size();
		if (s > j){
			return this.strat.get(i).getRecord().get(j);
		}else{
			return this.strat.get(i).getRecord().get(s-1);
		}
		
	}
	
	
	public void rewind(int t){
		this.curIter = t;
		update();
	}
	
	public void pause(){
		this.simEtat = false;
	}
	
	/*public void start(){
		if (this.etat != Etat.RUN)
		{
			ArrayList<Commande> rec = this.strat.getRecord();
			while (rec.size() >  this.curIter){
				rec.remove(rec.size()-1);
			}
			//System.out.println(this.listeVoiture.size());
			this.voit = this.listeVoiture.get(0).clone();
			StrategyComposite comp = this.stratCtrl.getStrategy(this.circ, this.voit, rec);
			
			//comp.add(new StrategyRadar(this.circ, this.voit));
			
			this.strat = new StrategyRecord(comp);
			this.listeVoiture.clear();
			this.listeVoiture.add(this.voit.clone());
			//play(this.curIter);
			this.compteur = 0;
			this.curIter = 0;
			this.etat = Etat.RUN;

			Thread t = new Thread(){
			public void run() {
				play();
				}
			};
			t.start();
		}
	}*/

	public void start(){
			pause();
			this.voitures = new ArrayList<Voiture>();
			this.voituresListe = new ArrayList<ArrayList<Voiture>>();
			this.strat = new ArrayList<StrategyRecord>();
			this.etat = new ArrayList<Etat>();
			for (int i = 0; i< this.stratCtrl.size(); i++){
				this.voituresListe.add(new ArrayList<Voiture>());
				this.voitures.add(voitInit.clone());
				this.voituresListe.get(i).add(this.voitures.get(i).clone());
				StrategyComposite comp = this.stratCtrl.get(i).getStrategy(this.circ, this.voitures.get(i), new ArrayList<Commande>());
			
				//comp.add(new StrategyRadar(this.circ, this.voit));
				
				this.strat.add(new StrategyRecord(comp));

				this.compteur = 0;
				this.curIter = 0;
				this.etat.add(Etat.RUN);
			}
			this.simEtat= true;
			Thread t = new Thread(){
				public void run() {
					play();
				}
			};
			t.start();
	}
	
	public ArrayList<StrategyRecord> getStrategy(){
		return this.strat;
	}
	
}

	

enum Etat{
	RUN, WIN, LOSE, STANDBY;
}