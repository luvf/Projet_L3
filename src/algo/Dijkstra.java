package algo;

import geometrie.Vecteur;
import circuit.Circuit;
import circuit.Terrain;

import java.util.concurrent.PriorityBlockingQueue;



public class Dijkstra {
	
	public final int width;
	public final int height;
	private int[][] dist;
	private Circuit circ;
	private PriorityBlockingQueue<Vecteur> heap;
	
	public Dijkstra(Circuit circ){
		this.width = circ.getWidth();
		this.height = circ.getHeight();
		
		this.circ = circ;
		
		initDist_Tas();
		algoDijkstra();
		
		
	}
	private void initDist_Tas(){
		this.dist = new int[this.height][this.width];
		this.heap = new PriorityBlockingQueue<Vecteur>(1, new ComparatorDijk(this.dist));
		
		for(int i = 0; i < this.height; i++){
			for(int j = 0; j < this.width; j++){
				if(this.circ.getTerrain(i, j) == Terrain.EndLine){
					this.dist[i][j] =  0;
					this.heap.add(new Vecteur(i, j));
				}
				else{
					this.dist[i][j] = Integer.MAX_VALUE;
				}
			}
		}
		
	}
	
	public int getDist(int i, int j){
		return this.dist[i][j];
	}
	public int getDist(Vecteur v){
		return this.dist[(int)v.x][(int)v.y];
	}
	
	public void setDist(int i, int j, int val){
		this.dist[i][j] = val;
	}
	public void setDist(Vecteur v, int val){
		this.dist[(int)v.x][(int)v.y] = val;
	}
	
	private void algoDijkstra (){
		while (this.heap.size() !=0 ){
			Vecteur cur = this.heap.poll();
			for (int i=-1; i<=1 ;i++){
				for (int j=-1; j<=1 ;j++){
					majVoisin(cur, i ,j);
				}
			}
			
		}
	}
	
	private void majVoisin(Vecteur cur, int i, int j){
		Vecteur tmp = new Vecteur(cur.x + i,cur.y + j);

		if ( 	i==0 && j==0 || 
				! circ.getTerrain(tmp).isRunable() ){
			return;
		}
		if( getDist(cur) == 0 && this.circ.getDirectionArrivee().produitScalaire(new Vecteur(i, j)) > 0){
			return;
		}
		
		int d;
		if (i * j == 0 ){
			d = getDist(cur) + 10;
		}else{
			d = getDist(cur) + 14;
		}
		
		
		if (getDist(tmp) > d){
			this.heap.remove(tmp);
			setDist(tmp, d);
			if (d< 0){
				System.out.println(d);
			}
			this.heap.add(tmp);
		}
	}
	
}
