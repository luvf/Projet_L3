package voiture;


public class Commande {
	public final double acceleration;
	public final double rotation;
	
	public double getAcc(){
		return this.acceleration;
	}
	
	public double getTurn(){
		return this.rotation;
	}
	
	public Commande(double acc, double rot){
		this.acceleration = acc;
		this.rotation = rot;
	}
	
	public Commande(Commande c){
		this.acceleration = c.acceleration;
		this.rotation = c.rotation;
	}
@Override
	public String toString(){
		return "Acceleration, Rotation : " + this.acceleration + " , " + this.rotation + "\n";
	}
	}