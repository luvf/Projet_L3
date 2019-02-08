package controller;


import java.io.File;
import java.io.FilenameFilter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import circuit.Circuit;
import circuit.CircuitFactory;
import circuit.CircuitImpl;
import simulation.Simulation;
import view.CircuitAffichage;
import view.CircuitObserver;
import view.Ihm;
import view.ObserverSWING;
import view.StatObserver;
import view.VoitureObserver;
import voiture.Voiture;
import voiture.VoitureFactory;

public class MainController  extends JPanel implements ActionListener{
	
	private JButton simButton;
	private JButton editButton;

	private JComboBox<String> circuits;

	private Box buttonBox;
	
	private TimeController simController;
	
	private CircuitEditControler circEdit;
	
	public MainController(){
		
		this.setLayout(new BorderLayout());
		this.buttonBox = Box.createHorizontalBox();
		
		this.simButton = new JButton("Simulation");
		this.buttonBox.add(simButton);
		simButton.addActionListener(this);
		
		this.editButton = new JButton("Edition");
		this.buttonBox.add(editButton);
		editButton.addActionListener(this);
		
		updateCircuits();
		this.add(this.buttonBox, BorderLayout.NORTH);
		
		
	}
	public void updateCircuits(){
		if (this.circuits != null){
			this.buttonBox.remove(this.circuits);
		}


		File trk = new File("trk/");
		FilenameFilter trkFilter = new FilenameFilter() {
			public boolean accept(File dir, String name) {
				String lowercaseName = name.toLowerCase();
				return lowercaseName.endsWith(".trk");
			}};
		this.circuits = new JComboBox<String>(trk.list(trkFilter));
		this.buttonBox.add(this.circuits);
		revalidate();
	}

	public void actionPerformed(ActionEvent a){
		
		if (a.getActionCommand().equals("Simulation")){
			this.startSimulation();
		}
		else if(a.getActionCommand().equals("Edition")){
			this.startEdition();
		}
	}
	
	
	public void startSimulation(){
		clearControler();
		
		this.simController = new TimeController(new CircuitImpl((String)"trk/" + this.circuits.getSelectedItem()));
		this.add(this.simController);
		
		simController.revalidate();
//		this.setVisible(true);
	}
	
	public void startEdition(){
		this.clearControler();
		
		
		
		CircuitImpl gotham = new CircuitImpl( (String)"trk/" + this.circuits.getSelectedItem());
		this.circEdit = new CircuitEditControler(this, gotham);
		
		this.add(circEdit);
		this.circEdit.revalidate();
		
	}
	public void clearControler(){
	
		if (this.simController!=null){
			remove(this.simController);
			this.simController.exit();
			this.simController= null;
		}
					
		if (this.circEdit!=null){
			remove(this.circEdit);
			this.circEdit = null;
		}
		//updateCircuits();
	}
	
	
	
}


