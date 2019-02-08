package controller;

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

public class RadarStratController extends StratController {
	
	
	
	private JSlider faisceauS;
	private JFormattedTextField faisceauF;

	private JSlider freinageS;
	private JFormattedTextField freinageF;



	
	
	
	public RadarStratController(StratCompositeController parent){
		
		
		
		super("RadarStratController", parent);
		
		Box faisceauBox = Box.createHorizontalBox();
		
		faisceauBox.add(new JLabel("Nombre Faisceau"));
		this.faisceauS = new JSlider(JSlider.HORIZONTAL, 1 , 100, 20);
		this.faisceauS.setMajorTickSpacing(1);
		this.faisceauS.setPaintTicks(true);
		this.faisceauS.addChangeListener(this);
		faisceauBox.add(this.faisceauS);
		
		this.faisceauF = new JFormattedTextField(NumberFormat.getIntegerInstance());
		faisceauBox.add(faisceauF);
		this.faisceauF.addActionListener(this);
		this.faisceauF.setValue(20);

		Box freinageBox = Box.createHorizontalBox();
		
		freinageBox.add(new JLabel("Force Freinage"));
		this.freinageS = new JSlider(JSlider.HORIZONTAL, -20 , 20, -10);
		this.freinageS.setMajorTickSpacing(1);
		this.freinageS.setPaintTicks(true);
		this.freinageS.addChangeListener(this);
		freinageBox.add(this.freinageS);
		
		this.freinageF = new JFormattedTextField(NumberFormat.getInstance());
		freinageBox.add(freinageF);
		this.freinageF.addActionListener(this);
		this.freinageF.setValue(-0.5);

		
		this.mainBox.add(faisceauBox);
		this.mainBox.add(freinageBox);
		//this.add(allBox);

	}

	public ScorableStrategy getStrategy(Circuit c, Voiture v) {
		return new StrategyRadar(c, v, this.faisceauS.getValue(), (double)this.freinageS.getValue()/20);
	} 
	

	@Override
	public void actionPerformed(ActionEvent a) {
		super.actionPerformed(a);
		this.faisceauS.setValue((int) this.faisceauF.getValue());
		this.freinageS.setValue((int) (double) this.freinageF.getValue());
		
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		this.faisceauF.setValue(this.faisceauS.getValue());
		this.freinageF.setValue((double)this.freinageS.getValue()/20);
		
	}


		
	
}
