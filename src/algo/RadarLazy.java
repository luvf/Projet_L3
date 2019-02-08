package algo;

import geometrie.Vecteur;
import circuit.Circuit;
import voiture.Voiture;

import circuit.Terrain;

public class RadarLazy implements Radar{

	public final Voiture voit;
	public final Circuit circ;
	public final int nbfaisceaux;
	public final double radius;
	private double epsilon = 0.2;
	
	private int timeSleep;
	private int maxSleepingTime;

	private Vecteur lastPos;

	private int bestIndex;

	private double[] scores;
	private double[] thetas;

	public RadarLazy(Voiture v, Circuit c, int nbf, double radius, int maxSleep){
		this.voit = v;
		this.circ = c;
		this.nbfaisceaux = nbf;
		this.radius = radius;
		this.bestIndex = 0;
		this.maxSleepingTime = maxSleep;
		this.timeSleep = maxSleep+4;
		this.lastPos = new Vecteur(-1, -1);
		this.scores = new double[nbf];

		this.thetas = new double[nbf];	

		for(int i = (int)-this.nbfaisceaux/2, j = 0; i <= (int)(this.nbfaisceaux/2) - 1; i++, j++){
			this.thetas[j] = (Math.PI * i)/this.nbfaisceaux;
		}
		scores();
		
	}
	

	public double[] scores() {
		if ( ! this.lastPos.equals( this.voit.getPosition() ) ){
			this.lastPos = this.voit.getPosition();
			if (wakeUp())
			{

				this.bestIndex = 0;
				for(int i = 0; i < this.nbfaisceaux; i++){
					this.scores[i] = calcScore(this.thetas[i]);
					if(this.scores[i] > this.scores[this.bestIndex]){
						this.bestIndex = i;
					}
				}
			}
		}
		return scores;
	}


	private boolean wakeUp(){
		if(this.timeSleep > this.maxSleepingTime){
			this.timeSleep = 0;
			return true;
		}
		this.timeSleep++;

		for (double i= 0; i< (2 * Math.PI); i+=2 * Math.PI/this.radius){
			if (circ.getTerrain(
					(int)(this.radius * Math.cos(i)),
					(int)(this.radius * Math.sin(i))
					).isRunable() == false){
				return true;
			}
		}
		return false;
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
		return thetas;
	}
	
}
