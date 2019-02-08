package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import javax.swing.JPanel;
import javax.swing.JSlider;


import circuit.Circuit;

import java.awt.event.ActionListener;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;

import simulation.Simulation;
import view.CircuitObserver;
import view.DijkstraObserver;
import view.Ihm;
import view.ObserverSWING;
import view.StatObserver;
import view.UpdateEventListener;
import view.VoitureObserver;
import voiture.Voiture;
import voiture.VoitureFactory;
import strategy.StrategyRecord;

import java.awt.Graphics;

import java.util.ArrayList;


public class TimeController extends JPanel implements ActionListener, ChangeListener{
	
	private JButton StartButton;
	private JButton PauseButton;
	private JButton RewindButton;
	private JButton addVoitButton;
	private JButton remVoitButton;
	private JButton saveImButton;
	private JTextField imageName;
	private JSlider slider;


	private JFormattedTextField Time;
	private JButton Save;
	private JTextField FileName;

	private ArrayList<StratCompositeController> stratCtrl;

	private Simulation sim;
	private Ihm ihm;
	int lrg;
	
	public TimeController(Circuit gotham){
		
		this.setLayout(new BorderLayout());
		Box boite = Box.createVerticalBox();
		
		this.addVoitButton = new JButton("add Voit");
		boite.add(addVoitButton);
		this.addVoitButton.addActionListener(this);
		
		this.remVoitButton = new JButton("rem Voit");
		boite.add(this.remVoitButton);
		this.remVoitButton.addActionListener(this);

		this.StartButton = new JButton("start");
		boite.add(StartButton);
		StartButton.addActionListener(this);

		
		
		this.PauseButton = new JButton("pause");
		boite.add(PauseButton);
		PauseButton.addActionListener(this);

		this.RewindButton = new JButton("rewind");
		boite.add(RewindButton);
		RewindButton.addActionListener(this);

		this.Time = new JFormattedTextField(NumberFormat.getIntegerInstance());
		boite.add(Time);
		this.Time.addActionListener(this);
		this.Time.setValue(0);

		this.Save = new JButton("save");
		boite.add(Save);
		Save.addActionListener(this);

		this.FileName = new JTextField();
		boite.add(FileName);
		FileName.addActionListener(this);

		this.saveImButton = new JButton("Capture Image");
		boite.add(this.saveImButton);
		this.saveImButton.addActionListener(this);

		this.imageName = new JTextField("Nom_image.png");
		boite.add(this.imageName);

		boite.add(Box.createGlue());


		this.slider = new JSlider(JSlider.HORIZONTAL, 0, gotham.getWidth(), 0);
		this.slider.setMajorTickSpacing(50);
		this.slider.setMinorTickSpacing(10);
		this.slider.setPaintTicks(true);
		//this.slider.setPaintLabels(true);
		this.add(this.slider, BorderLayout.NORTH);
		this.slider.addChangeListener(this);

		StratControllerFactory.circ = gotham;


		this.stratCtrl = new ArrayList<StratCompositeController>(); //est ce que c'est bien utile de le faire ici ? OUI

		//this.add(CurrentTime);
		this.add(boite, BorderLayout.EAST);
		
		VoitureFactory batCave = new VoitureFactory(gotham);
		Voiture batmobile = batCave.Build();
        
		this.sim = new Simulation(gotham, batmobile, this.stratCtrl);
		
		
		this.ihm = new Ihm();
		StratControllerFactory.ihm = this.ihm;
		ihm.setPreferredSize(new Dimension(gotham.getWidth(), gotham.getHeight()));

		
		this.ihm.add(new CircuitObserver(gotham));
		//this.ihm.add(new DijkstraObserver(gotham));          //affiche le Dijkstra sur le circuit
		this.ihm.add(new VoitureObserver(this.sim));
		this.ihm.add(new StatObserver(this.sim));
		this.sim.add(ihm);
		//this.sim.add(this);
		//this.add(ihm, BorderLayout.CENTER);
		
		JScrollPane ihmScrollPane =  new JScrollPane(ihm);
		ihmScrollPane.getVerticalScrollBar().setUnitIncrement(5);

		this.add(ihmScrollPane, BorderLayout.CENTER);
		
		//arkham.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

	public void exit(){
		for (StratCompositeController str: stratCtrl){
			str.dispose();
		}
		this.sim.kill();
	}
	
	public void actionPerformed(ActionEvent a){
		//System.out.println("sssss");
		if (a.getActionCommand().equals("start")){
			this.sim.start();
		}
		else if(a.getActionCommand().equals("pause")){
			this.sim.pause();
		}
		else if(a.getActionCommand().equals("rewind")){
			this.sim.rewind((int) (long)this.Time.getValue());
		}
		else if(a.getActionCommand().equals("save")){
			StrategyRecord str = this.sim.getStrategy().get(0);//a modi
			str.save(this.FileName.getText());
		}
		else if(a.getActionCommand().equals("add Voit")){
			this.stratCtrl.add(new StratCompositeController());
		}
		else if(a.getActionCommand().equals("Capture Image")){
			this.ihm.saveImage(this.imageName.getText());
		}
		else if(a.getActionCommand().equals("rem Voit")){
			if (this.stratCtrl.size() > 0){
				this.stratCtrl.get(this.stratCtrl.size()-1).dispose();
				this.stratCtrl.remove(this.stratCtrl.size()-1);
			}
			
			
		}
	
	}

	public void stateChanged(ChangeEvent e){
		this.sim.pause();
		int value = this.sim.getCompteur() * this.slider.getValue()/this.slider.getMaximum();
		this.Time.setValue(value);
		this.sim.rewind(value);
	}

	
}
