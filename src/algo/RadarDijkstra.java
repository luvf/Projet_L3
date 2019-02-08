package algo;
import geometrie.Vecteur;
import circuit.Circuit;
import voiture.Voiture;
import circuit.Terrain;


public class RadarDijkstra implements Radar{
	public final Voiture voit;
	public final Circuit circ;
	public final int nbfaisceaux;
	private double epsilon = 0.4;
	private Vecteur lastPos;
	private int bestIndex;
	private double[] scores;
	private double[] thetas;

	public RadarDijkstra(Voiture v, Circuit c, int nbf){
		this.voit = v;
		this.circ = c;
		this.nbfaisceaux = nbf;
		
		
		this.lastPos = new Vecteur(-1, -1);
		this.scores = new double[nbf];
		this.thetas = new double[nbf];	
		for(int i = (int)-this.nbfaisceaux/2, j = 0; i <= (int)(this.nbfaisceaux/2) - 1; i++, j++){
			this.thetas[j] = (Math.PI * i)/this.nbfaisceaux;
			
		}
		scores();
		
	}
	
	
	
	public double[] scores() {
		//if ( ! this.lastPos.equals( this.voit.getPosition() ) ){
			this.lastPos = this.voit.getPosition();
			this.bestIndex = 0;
			for(int i = 0; i < this.nbfaisceaux; i++){
				this.scores[i] = calcScore(this.thetas[i]);
				if(this.scores[i] < this.scores[this.bestIndex]){
					this.bestIndex = i;
				}
			//}
		}
		return scores;
	}
	private double calcScore(double angle){
		Double c = Double.POSITIVE_INFINITY;
		Vecteur pos = this.lastPos;
		Vecteur dir = this.voit.getDirection().rotation(angle).normaliser().multscalaire(epsilon);
		int i = 0;
		
		while(!endRayCondition(pos, dir)){
			if (c > circ.getDist(pos) + this.epsilon * i){
				c = circ.getDist(pos) + this.epsilon * i;
			}
			i++;
			pos = pos.add(dir);
			
		}
		return c;
	}

	private boolean endRayCondition(Vecteur pos, Vecteur dir){
		if (! circ.getTerrain(pos).isRunable()){
			return true;
		}
		if (circ.getTerrain(pos) == Terrain.EndLine ){
			if (circ.getDirectionArrivee().produitScalaire(dir) < 0 ){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public double[] distancesInPixels() {
		// TODO Auto-generated method stub
		return null;
	}
	public int getBestIndex() {
		scores();
		return this.bestIndex;
	}
	public double[] thetas() {
		return this.thetas;
	}
	
}
