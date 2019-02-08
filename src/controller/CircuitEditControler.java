package controller;

import circuit.Circuit;
import circuit.CircuitEdit;
import circuit.CircuitTools;
import circuit.Terrain;

import view.Ihm;
import view.CircuitEditObserver;


import javax.swing.Box;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;


public class CircuitEditControler extends JPanel implements MouseListener, ActionListener, ChangeListener{
	
	private CircuitEdit edit;
	private Circuit circ;
	private JButton routeButton;
	private JButton eauButton;
	private JButton redButton;
	private JButton	whiteButton;
	private JButton	safeButton;
	
	private JButton reset;
	private JButton clean;

	private JButton saveButton;
	private JSlider slider;
	private JTextField nameSave;

	private Terrain state;
	private Ihm ihm;
	private boolean safe;

	
	private MainController main;
	
	public CircuitEditControler(MainController main, Circuit circ){


		
		this.circ = circ;
		this.state = Terrain.Route;
		this.safe = true;
	
	//	this.circ = new circuitEdit(circ); 
		this.setLayout(new BorderLayout());
		Box boite = Box.createVerticalBox();
		this.routeButton = new JButton("route");
		routeButton.addActionListener(this);
		
		this.safeButton = new JButton("safe");
		safeButton.addActionListener(this);

		this.eauButton = new JButton("eau");
		eauButton.addActionListener(this);

		this.redButton = new JButton("red");
		redButton.addActionListener(this);

		this.whiteButton = new JButton("white");
		whiteButton.addActionListener(this);
		
		this.saveButton = new JButton("Save");
		saveButton.addActionListener(this);


		this.reset = new JButton("reset");
		this.reset.addActionListener(this);

		this.clean = new JButton("clean");
		this.clean.addActionListener(this);


		this.slider = new JSlider(JSlider.HORIZONTAL, 1 , 100, 2);
		this.slider.setMajorTickSpacing(1);
		this.slider.setPaintTicks(true);
		//this.slider.setPaintLabels(true);
		
		this.slider.addChangeListener(this);
		
		this.nameSave = new JTextField("new.trk");
		this.nameSave.addActionListener(this);

		boite.add(this.safeButton);
		boite.add(routeButton);
		boite.add(this.eauButton);
		boite.add(this.redButton);
		boite.add(this.whiteButton);
		
		boite.add(this.reset);
		boite.add(this.clean);
		boite.add(this.slider);
		boite.add(this.saveButton);
		boite.add(this.nameSave);
		//addMouseListener(this);

		this.add(boite, BorderLayout.EAST);
		reset(this.circ);
		this.main = main;

	}

	public void reset(Circuit circ){
		this.edit = new CircuitEdit(circ);

		this.ihm = new Ihm();
		this.ihm.add((MouseListener)this);
		this.edit.add(this.ihm);
		this.ihm.add(new CircuitEditObserver(this.edit));
		//JScrollPane ihmScrollPane = new JScrollPane(ihm);
		//ihmScrollPane.getVerticalScrollBar().setUnitIncrement(5);

		this.add(ihm, BorderLayout.CENTER);

		this.revalidate();

	}
	
	public void mouseClicked(MouseEvent e){	}

	public void mouseExited(MouseEvent e){	}
	public void mouseEntered(MouseEvent e){	}
	public void mouseReleased(MouseEvent e){	}
	public void mousePressed(MouseEvent e){
		int x = e.getX();
		int y = e.getY();

		drawSquare(x, y);
		
	}
	public void drawSquare(int x, int y){//actuellemtncircle
		

		int size = this.slider.getValue();

		for (int i = -size + 1; i< size; i++){
			for ( int j = -size + 1 ; j < size ; j++ ) {
				if (i*i+j*j <= size*size){///circle
					this.edit.addModif(y + i , x + j, state, this.safe);
				}
			}
		}
		this.edit.update();
		this.ihm.manageUpdate();
	}

	
	public void actionPerformed(ActionEvent a){
		if (a.getActionCommand().equals("route")){
			this.state = Terrain.Route;
		}
		if (a.getActionCommand().equals("eau")){
			this.state = Terrain.Eau;
		}
		if (a.getActionCommand().equals("Save")){
			String filename;
			filename = nameSave.getText();
			CircuitTools.circuitToFile(this.edit.toCircuit(),("trk/" + filename));
			this.main.updateCircuits();
		}
		if (a.getActionCommand().equals("reset")){
			reset(this.circ);
		}
		if (a.getActionCommand().equals("clean")){
			this.edit.clean();
			this.edit.update();
		}
		if (a.getActionCommand().equals("red")){
			this.state = Terrain.BandeRouge;
		}
		if (a.getActionCommand().equals("white")){
			this.state = Terrain.BandeBlanche;
		}
		if (a.getActionCommand().equals("safe")){
			this.safe = !this.safe;
		}
	}
	public void stateChanged(ChangeEvent e){ }

	
}

	