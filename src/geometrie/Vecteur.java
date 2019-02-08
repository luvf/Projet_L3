package geometrie;

public class Vecteur {
	

	public final double x;
	public final double y;
	
	
	
	
	public Vecteur(Vecteur cp){
		this.x = cp.x;
		this.y = cp.y;
	}
	
	public Vecteur(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public Vecteur(){
		this.x = 0;
		this.y = 0;
	}
	public Vecteur(Vecteur u, Vecteur v){
		this.x = v.x - u.x;
		this.y = v.y - u.y;
	}
	
	public Vecteur clone(){
		return new Vecteur(this);
	}
	
	public double angle(Vecteur u){
		return  Math.signum(produitvectoriel(u)) * 
				Math.acos( produitScalaire(u) / (norme() * u.norme()));
	}
	
	public double produitScalaire(Vecteur u){
			return (this.x*u.x) + (this.y*u.y) ;
	}
	
	public Vecteur rotation(double angle){
		return new Vecteur(  this.x * Math.cos(angle) - this.y * Math.sin(angle) ,
							 this.y * Math.cos(angle) + this.x * Math.sin(angle));
	}
	
	public double produitvectoriel(Vecteur u){
		return this.x * u.y - this.y * u.x; 
	}
	
	public double norme(){
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public Vecteur multscalaire(double scalaire){
		return new Vecteur(this.x * scalaire, this.y * scalaire);
	}
	
	
	public Vecteur normaliser(){
		return multscalaire(1/norme());
	}
	
	public Vecteur add(Vecteur u){
		return new Vecteur( this.x + u.x, this.y + u.y );
	}
	
	public static Vecteur add(Vecteur v, Vecteur u){
			return v.add(u);
	}

	public Vecteur sub(Vecteur u){
		return new Vecteur(u, this);
	}
	
	public static Vecteur sub(Vecteur v, Vecteur u){
			return v.sub(u);
	}
	
	@Override
	public String toString() {
		return "Vecteur [x=" + this.x + ", y=" + this.y + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vecteur other = (Vecteur) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
		

	}
}


