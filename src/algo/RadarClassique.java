package algo;
import geometrie.Vecteur;
import circuit.Circuit;
import voiture.Voiture;
import circuit.Terrain;

public class RadarClassique implements Radar{
	public final Voiture voit;
	public final Circuit circ;
	public final int nbfaisceaux;
	private double epsilon = 0.1;
	private Vecteur lastPos;
	private int bestIndex;
	private double[] scores;
	private double[] thetas;

	public RadarClassique(Voiture v, Circuit c, int nbf){
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
	
	public RadarClassique(Voiture v, Circuit c, double[] thetas){
		this.voit = v;
		this.circ = c;
		this.nbfaisceaux = thetas.length;
		this.lastPos = new Vecteur(-1, -1);
		this.scores = new double[this.nbfaisceaux];
		this.thetas = thetas;
		scores();
		
	}
	
	public double[] scores() {
		//if ( ! this.lastPos.equals( this.voit.getPosition() ) ){
			this.lastPos = this.voit.getPosition();
			this.bestIndex = 0;
			for(int i = 0; i < this.nbfaisceaux; i++){
				this.scores[i] = calcScore(this.thetas[i]);
				if(this.scores[i] > this.scores[this.bestIndex]){
					this.bestIndex = i;
				}
		//	}
		}
		return scores;
	}
	private double calcScore(double angle){
		int c = 0;
		Vecteur pos = this.lastPos;
		Vecteur dir = this.voit.getDirection().rotation(angle).normaliser().multscalaire(epsilon);
		this.lastPos = pos;
		
		while(circ.getTerrain(pos).isRunable()){
			if (circ.getTerrain(pos) == Terrain.EndLine ){
				if (circ.getDirectionArrivee().produitScalaire(dir) >=0 ){
					return 3 * (circ.getWidth() + circ.getHeight()) - c;
				}
			}
			pos = pos.add(dir);
			c++;
		}
		return c;
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
