package geometrie;





public class Bezier{
	public Vecteur[] points;
	public Vecteur[] polCoefs;

	public Bezier(Vecteur a, Vecteur b, Vecteur c, Vecteur d){
		this.points = new Vecteur[4];
		this.polCoefs = new Vecteur[4];
		this.points[0] = a;
		this.points[1] = b;
		this.points[2] = c;
		this.points[3] = d;	

		this.polCoefs[0] = new Vecteur( 		this.points[0].x,
												this.points[0].y);
		this.polCoefs[1] = new Vecteur( -3	*	this.points[0].x + 3 * 	this.points[1].x , 
										-3	*	this.points[0].y + 3 * 	this.points[1].y );
		this.polCoefs[2] = new Vecteur(  3 	* (	this.points[0].x - 2 * 	this.points[1].x +		this.points[2].x),
										 3 	* (	this.points[0].y - 2 * 	this.points[1].y +		this.points[2].y) );
		this.polCoefs[3] = new Vecteur( -		this.points[0].x + 3 *	this.points[1].x -	3 * this.points[2].x + this.points[3].x,
										-		this.points[0].y + 3 *	this.points[1].y -	3 *	this.points[2].y + this.points[3].y);
		

	}

	public Bezier(Vecteur[] p){
		this(p[0],p[1],p[2],p[3]);
	}
	public Vecteur val(double t){
		double a = (polCoefs[0].x + polCoefs[1].x * t + polCoefs[2].x * t * t  + polCoefs[3].x * t * t * t);
		double b = (polCoefs[0].y + polCoefs[1].y * t + polCoefs[2].y * t * t  + polCoefs[3].y * t * t * t);
		return new Vecteur(a,b);
	}
	public Vecteur derive(double t){
		double a = (polCoefs[1].x + 2 * polCoefs[2].x * t  + 3 * polCoefs[3].x * t * t);
		double b = (polCoefs[1].y + 2 * polCoefs[2].y * t  + 3 * polCoefs[3].y * t * t);
		return new Vecteur(a,b);
	}

	public Bezier clone(){
		return new Bezier(this.points);
	}

}
