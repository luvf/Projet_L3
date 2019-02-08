package algo;

import geometrie.Vecteur;
import circuit.Circuit;
import circuit.Terrain;

import java.util.concurrent.PriorityBlockingQueue;

import java.util.ArrayList;


//


public class DijkstraReal {
	
	public final int width;
	public final int height;
	private double[][] dist;
	private Vecteur[][] gradient;
	private Circuit circ;
	private PriorityBlockingQueue<Vecteur> heap;
	private ArrayList<Vecteur> border;

	
	public DijkstraReal(Circuit circ){
		this.width = circ.getWidth();
		this.height = circ.getHeight();
		
		this.circ = circ;
		
		initDist_Tas();
		algoDijkstra();// il semble que cette etape prenne du temps 
		fill();
		secondLoop();
		
		
	}

	private void initDist_Tas(){
		this.dist = new double[this.height][this.width];
		this.gradient = new Vecteur[this.height][this.width];
		this.border = new ArrayList<Vecteur>();
		this.heap = new PriorityBlockingQueue<Vecteur>(1, new ComparatorDijkRe(this.dist));
		
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				Vecteur cur = new Vecteur (i, j);
				if(this.circ.getTerrain(i, j) == Terrain.EndLine){
					setDist(cur, 0, new Vecteur(0,0));
					this.heap.add(new Vecteur(i, j));	
					this.border.add(new Vecteur(i,j));		
				}
				else{
					if (inBorder(i, j)){ // on est sur un bord
						this.border.add(new Vecteur(i,j));
					}
					setDist(cur, Double.POSITIVE_INFINITY, new Vecteur(0,0));
				}
			}
		}
		
	}
	
	public boolean inBorder(int x, int y){
		if (! this.circ.getTerrain(x,y).isRunable()){
			return false;
		}
		
		for (int i=-1; i<=1 ;i++){
			for (int j=-1; j<=1 ;j++){
				if (!this.circ.getTerrain(	i + x,
											j + y).isRunable()){
					return true;
				}
			}
		}
		return false;
	}



	private void algoDijkstra (){
		while (this.heap.size() !=0 ){
			//System.out.println(this.heap.size());
			Vecteur cur = this.heap.poll();// modifier cette partie.... euh pk elle est bien nn ?
			for (Vecteur next : border){
				if (estVisible(cur, next)){
					
					majVoisin(cur, next);
					//System.out.println(getDist(next));
				}
			}	
		}
	}	
	public void fill(){
		for (Vecteur b : this.border){
			Vecteur dir = getGrad(b);
			
			if (dir != null){
			
				Vecteur end = b.add(dir);
				Vecteur cur = b;
				double l = dir.norme();
				double epsilon = 0.3;
				dir = dir.normaliser();
				dir = dir.multscalaire(epsilon);
				
				int i = 0;
				while ( (double)i *epsilon < l  ){//unn pezut sale/y
			
					cur = cur.add(dir);
					double d = cur.sub( end).norme() + getDist(end) ;
					setDist(cur, d , new Vecteur(cur, end));
					i++;

				}
			}
		}
	}


	private void secondLoop(){
		for (int i = 0; i < this.height ;i++){
			System.out.println(i);
			for (int j =0 ; j < this.width ; j++){
				Vecteur cur = new Vecteur(i, j);
				if (this.circ.getTerrain(i, j).isRunable() &&
					getDist(cur) == Double.POSITIVE_INFINITY){
					for (Vecteur b : this.border){
						Vecteur dir = new Vecteur(cur, b);
						double d = dir.norme() + getDist(b);
						if(getDist(cur) > d && estVisible(cur, b)){
							setDist(cur, d ,dir);	
						}	
					}
				}
			}
		}
	}
	
	private void majVoisin(Vecteur cur, Vecteur next){
		Vecteur dir = new Vecteur(next, cur);
		if (dir.norme() <0.1){ //on est pas son propre voisin
			return;
		}

		
		if( getDist(cur) == 0 && 
			this.circ.getDirectionArrivee().produitScalaire(dir) < 0){ //si on traverse la ligne dans le mauvais sens.
			return;
		}
		
		double d;
		d = getDist(cur) + dir.norme();
		
		if (getDist(next) > d){
			
			this.heap.remove(next);
			setDist(next, d, dir);
			this.heap.add(next);
		}
	}
	
	private boolean estVisible(Vecteur a, Vecteur b){// a essayer de faire de maniere psedo dichotomique   : ....... 4251637
	// mais surtout : tester chaque point une et une seule fois sur la droite formelle //a opti en vitesse
		Vecteur dir = new Vecteur(a, b);
		double l = dir.norme();
		dir = dir.normaliser();
		double epsilon = 1;
		dir = dir.multscalaire(epsilon);
		Vecteur cur = a;
		
		int i= 0;
		while ( (double)i *epsilon < l  ){//unn pezut sale/y
			cur = cur.add(dir);
			if ((!this.circ.getTerrain(cur).isRunable()) ||
				 this.circ.getTerrain(cur) == Terrain.EndLine){
				return false;
			}
			i++;
		}
		return true;
	
	}


	public double getDist(int i, int j){
		return this.dist[i][j];
	}
	public double getDist(Vecteur v){
		return this.dist[(int)v.x][(int)v.y];
	}

	public Vecteur getGrad(int i, int j){
		return this.gradient[i][j];
	}
	public Vecteur getGrad(Vecteur v){
		return this.gradient[(int)v.x][(int)v.y];
	}
	
	
	public void setDist(Vecteur v, double d, Vecteur g){
		this.dist[(int)v.x][(int)v.y] = d;
		this.gradient[(int) v.x][(int) v.y] = new Vecteur(g);
	}
	
}
