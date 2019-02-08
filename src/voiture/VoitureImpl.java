package voiture;
import geometrie.Vecteur;
public class VoitureImpl implements Voiture{
	private double[] tabVitesse;	
	private double[] tabTurn;  
  
	private final double vmax, braquage;
	private final double alpha_c, alpha_f, beta_f;
	private double vitesse;
	private Vecteur position;
	private Vecteur direction;
	
	
	public VoitureImpl(double[] tabVitesse, double[] tabTurn, double vmax, double 
	alpha_c, double braquage, double alpha_f, double beta_f, double vitesse, Vecteur position, Vecteur orientation)
	{
		this.tabVitesse = tabVitesse;
		this.tabTurn = tabTurn;
		this.vmax = vmax;
		this.braquage = braquage;
		this.alpha_c = alpha_c;
		this.alpha_f = alpha_f;
		this.beta_f = beta_f;
		this.vitesse = vitesse;
		this.position =  position;
		this.direction =  orientation;
		
	}
	
	public VoitureImpl(VoitureImpl v){
		this.tabVitesse = v.tabVitesse;
		this.tabTurn = v.tabTurn;
		this.vmax = v.vmax;
		this.braquage = v.braquage;
		this.alpha_c = v.alpha_c;
		this.alpha_f = v.alpha_f;
		this.beta_f = v.beta_f;
		this.vitesse = v.vitesse;
		this.position = v.position;
		this.direction = v.direction;
		
	}
	
	
	
	public void drive(Commande c){
		// VERIFICATIONS !!!
		// 1) Est ce que la rotation et l'accélération sont entre -1 et 1, sinon, throw new RuntimeException
		if(c.getTurn() < -1 || c.getTurn() > 1 || c.getAcc() < -1 ||
			c.getAcc() > 1){
			throw new RuntimeException(
				"rotation ou acceleration pas bonne\nAcceleration : " 
				+ c.getAcc() + "\nTurn : " + c.getTurn() + "\n");
		}
		// 2) Est ce que la rotation demandée est compatible avec la vitesse actuelle, sinon, throw new RuntimeException
		if(Math.abs(c.getTurn()) > this.getMaxTurn()){
			throw new RuntimeException("rotation trop importante pour la vitesse\n"
		+ "Rotation : " + c.getTurn() + "\n Rotation max : " + this.getMaxTurn() + "\n");
		}
		// approche normale
		// 1.1) gestion du volant
		direction = direction.rotation(c.getTurn() * braquage); // modif de direction
		// Note: on remarque bien que l'on tourne d'un pourcentage des capacités de la voiture
		// 1.2) garanties, bornes...
		direction = direction.normaliser(); // renormalisation pour éviter les approximations
		// 2.1) gestion des frottements
		vitesse -= alpha_f;
		vitesse -= beta_f*vitesse;
		// 2.2) gestion de l'acceleration/freinage
		vitesse += c.getAcc() * alpha_c;
		vitesse = Math.max(0., vitesse); // pas de vitesse négative (= pas de marche arriere)
		vitesse = Math.min(vmax, vitesse); // pas de dépassement des capacités
		// 3) mise à jour de la position
		position = position.add(direction.multscalaire(vitesse));
}
	public double getMaxTurn() {
		for(int i = 0; i < 10; i++){
			if(this.vitesse <= this.tabVitesse[i] * this.vmax){
				return this.tabTurn[i];
			}
		}
		return -1;
	}
	public double getVitesse() {
		return this.vitesse;
	}
	public Vecteur getPosition() {
		return this.position;
	}
	public Vecteur getDirection() {
		return this.direction;
	}
	public double getBraquage() {
		return this.braquage;
	}
	
	public String toString(){
	return	 " Vitesse : " + Double.toString(this.vitesse) + 
			" \nPosition : " + this.position.toString() + 
			"\nDirection : " + this.direction.toString();
	}

    public Voiture clone(){
        return new VoitureImpl(this);
    }
    
    public void replace(int x, int y){
    	this.position = new Vecteur(x,y);
    }
 
}
 