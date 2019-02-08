package circuit;
import geometrie.Vecteur;
import algo.Dijkstra;

import java.util.ArrayList;

public class CircuitImpl implements Circuit {


	protected int width;
	protected int height;

	protected Terrain terrain[][]; 

	protected Vecteur pointDepart;
	public final Vecteur dirDepart = new Vecteur(0,1);
	public final Vecteur dirArrivee = new Vecteur(0,1);
	protected ArrayList<Vecteur> arrivees;
	protected Dijkstra dijk;


	

	public CircuitImpl(Terrain terrain[][]){
		this.arrivees = new ArrayList<Vecteur>();
		this.height = terrain.length;
		this.width = terrain[0].length;		//si le terrain n'a aucune ligne le monde n'a aucun sens et donc 6 * 9 = 42
		this.terrain = new Terrain[this.height][this.width];
		for (int i=0; i< this.height; i++){
			for (int j = 0; j< this.width; j++) {
				this.terrain[i][j]= terrain[i][j];
				if (terrain[i][j]== Terrain.StartPoint){
					pointDepart = new Vecteur(i,j);
				}
				if (terrain[i][j]== Terrain.StartPoint){
					this.arrivees.add( new Vecteur(i,j));
				}
			}
		}
		this.dijk = new Dijkstra(this);
	}
	public CircuitImpl(String filename){
		this(CircuitTools.fileToTerrain(filename));
	}

	public Terrain getTerrain(int i, int j)
	{
		return terrain[i][j];
	}
	
	public Terrain getTerrain(Vecteur v){
		return terrain[(int)v.x][(int)v.y];
	}

	public Vecteur getPointDepart(){
		return pointDepart;
	}
	public Vecteur getDirectionDepart(){
		return dirDepart;
	}
	public Vecteur getDirectionArrivee(){
		return dirArrivee;
	}
	

	public int getWidth(){
		return this.width;
	}

	public int getHeight(){
		return this.height;
	}

	public ArrayList<Vecteur> getArrivees(){
		return arrivees;
	}

	public double getDist(int i, int j){
		return this.dijk.getDist(i, j);
	}

	public double getDist(Vecteur v){
		return getDist((int)v.x, (int)v.y);
	}

	public boolean runableAt(int x, int y){
		return this.terrain[x][y].isRunable();
	}

	public Circuit clone(){
		return new CircuitImpl(this.terrain);
	}

	
}