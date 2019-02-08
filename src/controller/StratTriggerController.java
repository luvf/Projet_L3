package controller;

import java.awt.event.ActionEvent;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import circuit.Circuit;
import circuit.Terrain;
import strategy.ScorableStrategy;
import strategy.StrategyTrigger;
import voiture.Voiture;


public class StratTriggerController extends StratController {

	private StratController stratCtrl;
	
	private JButton SetButton;
	
	private JComboBox<String> strats;
	private JComboBox<Terrain> trigger;

	
	private JSlider vMaxS;
	private JFormattedTextField vMaxF;


	
	
	public StratTriggerController(StratCompositeController parent){
		super("TriggerStratController", parent);
		
		Box setup = Box.createHorizontalBox();
		this.SetButton = new JButton("set");
		setup.add(SetButton);
		SetButton.addActionListener(this);
		
		this.strats = new JComboBox<String>(StratControllerFactory.NAMES);
		setup.add(strats);
		this.mainBox.add(setup);
		
		Box vMaxBox = Box.createHorizontalBox();
		this.vMaxS = new JSlider(JSlider.HORIZONTAL, 0 , 90, 90);
		this.vMaxS.setMajorTickSpacing(1);
		this.vMaxS.setPaintTicks(true);
		this.vMaxS.addChangeListener(this);
		vMaxBox.add(this.vMaxS);
		
		this.vMaxF = new JFormattedTextField(NumberFormat.getInstance());
		vMaxBox.add(vMaxF);
		this.vMaxF.addActionListener(this);
		this.vMaxF.setValue(.9);

		this.mainBox.add(vMaxBox);
		
		Terrain[] triggs = {Terrain.Route, Terrain.BandeRouge, Terrain.BandeBlanche};
		this.trigger = new JComboBox<Terrain>(triggs);
		this.mainBox.add(this.trigger);

		this.stratCtrl = null;
	}
	
	
	public ScorableStrategy getStrategy(Circuit c, Voiture v) {
		
		return new StrategyTrigger(c, v,  (float) (double) this.vMaxF.getValue(), (Terrain)this.trigger.getSelectedItem(), this.stratCtrl.getStrategy(c, v));
	} 
		
	
	@Override
	public void actionPerformed(ActionEvent a) {
		super.actionPerformed(a);
		this.vMaxS.setValue((int) ((double) this.vMaxF.getValue()) * 100);

		if (a.getActionCommand().equals("set")){
			if (stratCtrl != null){
				this.remove(stratCtrl);
			}
			stratCtrl = StratControllerFactory.getStrategyController( (String) this.strats.getSelectedItem());
			this.add(stratCtrl);
			this.revalidate();
			this.parent.pack();

		}
			
	}
	
	
	public void stateChanged(ChangeEvent e) {
		this.vMaxF.setValue((double)this.vMaxS.getValue()/100);
		
	}

}
