package main;
import javax.swing.JFrame;

import controller.CircuitEditControler;
import controller.MainController;
import controller.TimeController;
import algo.Dijkstra;
import algo.DijkstraReal;
import voiture.Voiture;
import voiture.VoitureFactory;
import circuit.Circuit;
import circuit.CircuitFactory;
import strategy.StrategyRadar;
import strategy.StrategyComposite;
import strategy.StrategyRecord;
import simulation.Simulation;
import view.CircuitObserver;
import view.Ihm;
import view.StatObserver;
import view.VoitureObserver;
import view.CircuitAffichage;

import java.awt.BorderLayout;
import java.awt.Dimension;


public class Main {
    public static void main(String[] args) {
            
        
    
    	MainController ctrl = new MainController();
		JFrame fen = new JFrame();
		
		fen.setSize(new Dimension( 1200,1000));
		
		
		
		fen.getContentPane().setLayout(new BorderLayout());
		fen.add(ctrl, BorderLayout.CENTER);
		//fen.pack();
		fen.setVisible(true);

		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

	}
}
