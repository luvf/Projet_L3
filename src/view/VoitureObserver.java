package view;

import simulation.Simulation;
import view.ObserverImage;
import voiture.Voiture;
import geometrie.Vecteur;

import java.awt.Color; 
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage; 
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;


public class VoitureObserver implements ObserverImage, ObserverSWING{
	private Simulation sim;
	private Voiture voit;
	private BufferedImage car;

	public VoitureObserver(Simulation sim) {
		super();
		this.sim = sim;
		

		try { 
			this.car = ImageIO.read(new FileInputStream("batmobile.png" )); 
		} 
		catch (IOException e) { }
	}

	public int getX(int i){ // a inverser pour l'affichage horizontal
		return (int) this.voit.getPosition().x;
	}
	public int getY(int j){
		return (int) this.voit.getPosition().y;
	}

	public Color getColor(int i) {
		int k = (int)(255*i)/sim.nbVoitures();
		return new Color(k,255, 255-k);
	}

	public void print(BufferedImage im) {
		if (this.voit != null){
			im.setRGB(getX(0), getY(0), getColor(0).getRGB());
		}
	}
	
	public void print(Graphics g){
		int curIter = sim.curIter();
		if (curIter >=0 ){
			for (int i =0; i< sim.nbVoitures();i++){
				String name = "Voiture" + Integer.toString(i);
				this.voit = sim.getVoiture(i,curIter);
				Color col = getColor(i);
				//System.out.println(sim.curIter());
				// Attention a l'inversion eventuelle des coordonnees
				g.setColor(col);
				g.drawRect( getY(i), getX(i), 1, 1);
				g.setColor(Color.black);
				//g.drawString(String.format("v: %.2f d: (%6.2f, %6.2f) derap: ", this.voit.getVitesse(),
			   // 		getX(), getY()) 10, 10);
			 // calcul de l'angle à appliquer sur l'image de la this.voit pour la rendre
				// cohérente avec la simulation à chaque instant
				// -> dépend de l'image et de l'affichage de l'interface (horizontale ou vertical)
				double angle = this.voit.getDirection().angle( new Vecteur(0,1) );

				// construction d'une transformation
				AffineTransform transform = new AffineTransform();
				// rotation par rapport à un centre à définir (cf javadoc)
				transform.rotate(angle, (car.getWidth() / 2), (car.getHeight() / 2));
				// transformation => transformation d'image
				AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BICUBIC);

				// image finale
				g.drawString(name, getY(i), (getX(i)-50));
				BufferedImage carMod = op.filter(car, null);
				g.drawImage(carMod, getY(i), getX(i), null);
			}	
		}	
	}
}