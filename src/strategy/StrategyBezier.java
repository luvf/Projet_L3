package strategy;

import algo.Radar;
import algo.RadarClassique;
import algo.RadarDijkstra;
import circuit.Circuit;
import voiture.Commande;
import voiture.Voiture;
import geometrie.Vecteur;
import geometrie.Bezier;


import java.util.ArrayList;


public class StrategyBezier extends ScorableStrategy{

	private Bezier[] segments;

	private int curSegment;
	private double abs;//absyce curviligne
	private double maxAbs;

	private ArrayList<Voiture> voitures;
	private ArrayList<Commande> listeCommande;

	private Vecteur curPos;
	private int curIter;

	public StrategyBezier(Circuit c, Voiture v, Bezier[] seg){
		super(c, v);
		this.segments = new Bezier[seg.length];
		for (int i=0; i<seg.length; i++){
			this.segments[i] = seg[i].clone();
		}
		this.curSegment=0;
		this.abs = 0;
		this.maxAbs = seg.length;
		this.listeCommande = new ArrayList<Commande>();
		this.voitures = new ArrayList<Voiture>();
		this.curIter =0;
		fourtout();
		System.out.println("lolo");
		System.out.println(this.voitures.size());
	}
	
	
	
	public double newAbs(double a, double b, double vitesse){
		curSegment = (int)abs;
		double epsilon = .00001;
		a= Math.max(0,a);
		Vecteur va = this.segments[curSegment].val(a);
		Vecteur init = va;
		b= Math.min(1, b);
		Vecteur vb = this.segments[curSegment].val(b);
		double c=0;
		while (va.sub(vb).norme() > epsilon){
			 c = (b + a)/2;
			Vecteur vc = this.segments[curSegment].val(c);
			if (vc.sub(init).norme() > vitesse ){
				b = c;
				vb = vc;
			}
			else{
				a = c;
				va = vc;
			}
		}
		return c;
	}

	public void updateAbs(){
		this.curPos = this.voit.getPosition();
		double a = Math.max(0, (double)(abs-.1));
		double b = Math.min(maxAbs, (double)(abs+0.1));
		double epsilon = 0.00001;

		Vecteur va = this.segments[(int)abs].val(a-(int)a);
		Vecteur init = va;
		Vecteur vb = this.segments[(int)abs].val(b-(int)b);
		double c=0;
		while (va.sub(vb).norme() > epsilon){
			c = (b+a)/2;
			Vecteur vc = this.segments[(int)abs].val(c);

			if ((new Vecteur(va, this.curPos)).norme() < (new Vecteur(vb, this.curPos)).norme() ){
				b = c;
				vb = vc;
			}
			else{
				a = c;
				va = vc;
			}
		}
		this.abs = c;

	}
	public Commande calcCommande(){
		updateAbs();			
			Vecteur tangente = this.segments[(int)abs].derive(this.abs-(int)this.abs);
			double vitesse = (double)this.voit.getVitesse();
			double newAbs = newAbs(abs, (double)(tangente.norme()*2), vitesse);
			Vecteur nextPos= this.segments[(int)newAbs].val(newAbs-(int)newAbs);
			Vecteur dir = new Vecteur(curPos, nextPos);

			double angle = this.voit.getDirection().angle(dir)/voit.getBraquage();
			System.out.println(angle);
			System.out.println(this.voit.getDirection().angle(dir));
			System.out.println(voit.getBraquage());
			return new Commande(1, angle);
	}

	public void fourtout(){
		while (maxAbs-abs > .01){
			Commande com;

			do {
				com = calcCommande();
				if(Math.abs(com.getTurn()) > this.voit.getMaxTurn()){
					rewind(this.voitures.size());
				}
			}while (Math.abs(com.getTurn()) > this.voit.getMaxTurn());
			
			String s="";
			for (Commande c : listeCommande){
				s+=" "+Double.toString(c.getAcc());
			}
			System.out.println(s);
			
			this.listeCommande.add(com);
			this.voit.drive(com);
			
			this.voitures.add(this.voit.clone());
		}
	}

	public void rewind(int k){
		int i = this.listeCommande.size();
		System.out.println(i);
		while(i>1 && this.listeCommande.get(i-1).getAcc() == -1){
			i--;
			this.listeCommande.remove(i);
			this.voitures.remove(i);
			this.voit = voitures.get(i-1).clone();
			updateAbs();

		}
		
		i--;
		this.listeCommande.remove(i);
		this.voitures.remove(i);
		this.voit = voitures.get(i-1).clone();
		updateAbs();
		ripley(k);
		
	}

	public void ripley(int k){
		for (int i=this.listeCommande.size(); i< k; i++){
			Commande com = calcCommande();
			
			com = new Commande(-1, com.getTurn());
			this.listeCommande.add(com);
			this.voit.drive(com);
			this.voitures.add(this.voit.clone());
		}
	}


	public int getScore() {
		
		if (curIter< this.listeCommande.size()){
			return 8;
		}else{
			 return 0;
		}
	}


	public Commande getCommande() {
		
		if (curIter< this.listeCommande.size()){
			curIter+=1;
			return this.listeCommande.get(curIter);
		}
		return null;
	}

}
