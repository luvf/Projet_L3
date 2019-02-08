package exam;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import circuit.Circuit;
import strategy.ScorableStrategy;
import strategy.Strategy;
import strategy.StrategyRadar;
import voiture.Voiture;

import controller.StratController;

import controller.StratCompositeController;


public class ControllerExam extends StratController {
	
	
	public ControllerExam(StratCompositeController parent){
		super("RadarStratController", parent);
		

	}

	public ScorableStrategy getStrategy(Circuit c, Voiture v) {
		return null; //new StrategyExam(c, v);
	} 
	

	@Override
	public void actionPerformed(ActionEvent a) {
	}


	@Override
	public void stateChanged(ChangeEvent e) {
	}


		
	
}
