package view;

import circuit.CircuitEdit;
import circuit.CircuitTools;
import geometrie.Vecteur;
import geometrie.Bezier;
import controller.BezierController;


import java.awt.Graphics;
import java.awt.image.BufferedImage; 
import geometrie.Bezier;
import java.util.ArrayList;
import java.awt.Color;


public class BezierObserver implements ObserverSWING{
	private Vecteur size;
	private BezierController bezier;

	public BezierObserver(BezierController ctrl, Vecteur size) {
		this.bezier = ctrl;
		this.size = size;
		
	}

	public void print(Graphics g) {
		ArrayList<Vecteur> points = this.bezier.getPoints();
		ArrayList<Bezier> beziers = new ArrayList<Bezier>();
		BufferedImage ret = new BufferedImage((int)size.x, (int)size.y, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < (int)size.y; i++){
			for (int j = 0; j < (int)size.y; j++) {
				ret.setRGB(i,j, (new Color(0,0,0,0)).getRGB());
			}
		}

		if (points.size()<=3){
			for (int i=1; i< points.size(); i++){
				Vecteur curPoint = points.get(i);
				g.fillOval((int)curPoint.y-8, (int)curPoint.x-8, 16, 16);
			}
		}else{
			for (int i=1; i < points.size()-2; i+= 3){
				Vecteur a = points.get(i);
				g.fillOval((int)a.y-8, (int)a.x-8, 16, 16);
				Vecteur b = points.get(i+1);
				g.fillOval((int)b.y-8, (int)b.x-8, 16, 16);
				Vecteur c = points.get(i+2);
				g.fillOval((int)c.y-8, (int)c.x-8, 16, 16);
				Vecteur d = points.get(i+3);
				g.fillOval((int)d.y-8, (int)d.x-8, 16, 16);
				beziers.add(new Bezier(a,b,c,d));
			}
			float x = 0;
			while (x < beziers.size()){
				Vecteur curPoint = beziers.get((int)x).val(x-(int)x);
				x+= 0.0002;
				if (curPoint.x>=0 && curPoint.y <= size.x&&curPoint.y>=0 && curPoint.y <= size.x  ){
					ret.setRGB((int)curPoint.y, (int)curPoint.x, (new Color(0,0,0)).getRGB());
				}
			}

		}
		
		g.drawImage(ret, 0,0, null);

	}
}

