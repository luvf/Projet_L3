
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JProgressBar;
import circuit.Circuit;
import strategy.ScorableStrategy;
import strategy.Strategy;
import strategy.StrategyParam;
import voiture.Voiture;
import geometrie.Bezier;

import geometrie.Vecteur;
import view.BezierObserver;
import view.Ihm;

import view.UpdateEventSender;
import view.UpdateEventListener;
import java.util.ArrayList;

import strategy.StrategyBezier;
import algo.Genetic;






public class BezierController extends StratController implements UpdateEventSender, MouseListener {
	
	private JButton compute;
	private JButton reset;
	private JButton pop;

	private ArrayList<Vecteur> points;

	private int drag;
	private Circuit circ;
	private ArrayList<UpdateEventListener> listener;
	
	public BezierController(StratCompositeController parent, Circuit c, Ihm ihm){
		super("Bezier Controller", parent);

		this.circ = c;
		this.listener = new ArrayList<UpdateEventListener>();

		Box tmp = Box.createVerticalBox();
		this.compute = new JButton("compute");
		this.reset = new JButton("reset");
		this.pop = new JButton("pop");
		
		this.reset.addActionListener(this);
		this.compute.addActionListener(this);
		this.pop.addActionListener(this);

		tmp.add(this.reset);
		tmp.add(this.compute);
		tmp.add(this.pop);
		
		this.add(tmp);
		addMouseListener(ihm);
		add((UpdateEventListener)ihm);
		ihm.add(new BezierObserver(this, new Vecteur(c.getWidth(), c.getHeight()))); 
		ihm.add((MouseListener)this);
		drag = -1;

		reset();
	}

	private void reset(){
		this.points = new ArrayList<Vecteur>();
		Vecteur c = this.circ.getPointDepart();
		Vecteur d = this.circ.getDirectionDepart().multscalaire(50);

		this.points.add(c.sub(d));
		this.points.add(c);
		this.points.add(c.add(d));
		update();
	}
	public void pop(){
		int s = this.points.size();
		if (s>3){
			this.points.remove(s-1);
			this.points.remove(s-2);
			this.points.remove(s-3);
			update();
		}
	}
	

	public ScorableStrategy getStrategy(Circuit circ, Voiture voit) {
		if (points.size()>=3){
			Bezier bez[] = new Bezier[(int)(points.size()/3)-1];
			int k =0;
			for (int i=1; i < points.size()-2; i+= 3){
					Vecteur a = points.get(i);
					Vecteur b = points.get(i+1);
					Vecteur c = points.get(i+2);
					Vecteur d = points.get(i+3);
					bez[k] = new Bezier(a,b,c,d);
					k++;
				}
			return new StrategyBezier(circ,voit, bez);
		}
		else{
			return null;
		}
		
	} 

	

	@Override
	public void actionPerformed(ActionEvent a) {
		
		if (a.getActionCommand().equals("reset")){
			reset();	
		}
		if (a.getActionCommand().equals("pop")){
			pop();	
		}
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
	}


	public void mouseClicked(MouseEvent e){	}

	public void mouseExited(MouseEvent e){	}
	public void mouseEntered(MouseEvent e){	}
	public void mouseReleased(MouseEvent e){//prions pour que ca marche
		if (this.drag != -1){
			Vecteur curP = new Vecteur(e.getY(), e.getX());
			Vecteur oldP = this.points.get(this.drag);
			this.points.set(this.drag, curP);
			
			if (this.drag%3==1){// a remodif
				
				Vecteur prec = new Vecteur(oldP, this.points.get(this.drag-1));
				this.points.set(this.drag-1, curP.add(prec));
				Vecteur suiv = new Vecteur(oldP, this.points.get(this.drag+1));
				this.points.set(this.drag+1, curP.add(suiv));
				
				
			}
			if (this.drag%3==2){
				Vecteur pivot = this.points.get(this.drag-1);
				Vecteur autre = this.points.get(this.drag-2);
				double d = pivot.sub(autre).norme();
				autre = pivot.add((new Vecteur(curP, pivot)).normaliser().multscalaire(d));
				this.points.set(this.drag-2, autre);
			}
			if (this.drag%3==0){
				Vecteur pivot = this.points.get(this.drag+1);
				Vecteur autre = this.points.get(this.drag+2);
				double d = pivot.sub(autre).norme();
				autre = pivot.add((new Vecteur(curP, pivot)).normaliser().multscalaire(d));
				this.points.set(this.drag+2, autre);
			}
		
		}
		this.drag = -1;
		update();
	}


	public void mousePressed(MouseEvent e){
		double minDist = 8;
		Vecteur posmouse = new Vecteur(e.getY(), e.getX());// a mod
		for (int i = 0 ; i< this.points.size(); i++){
			
			double d = posmouse.sub(points.get(i)).norme();
			if (d<minDist){
				minDist = d;
				this.drag = i;
			}
		}
		if (drag == -1){
			addPoint(posmouse);
		}
		update();
	}

	public void addPoint(Vecteur pos){
		this.points.add(pos.add(new Vecteur(-20, 0)));
		this.points.add(pos);
		this.points.add(pos.add(new Vecteur(20, 0)));
	}
	public ArrayList<Vecteur> getPoints(){
		return this.points;
	}

	public void add(UpdateEventListener listener){
		this.listener.add(listener);
	}

	public void update(){
		for (UpdateEventListener li : listener){
			li.manageUpdate();
		}
	}
}


