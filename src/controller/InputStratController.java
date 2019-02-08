package controller;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import circuit.Circuit;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import strategy.ScorableStrategy;
import strategy.StrategyInput;
import voiture.Voiture;

public class InputStratController extends StratController{
	
	private JLabel name;
	private JComboBox manettesCombo;
	private Controller[] controllers;

	
	public InputStratController(StratCompositeController parent){
		super("InputStratController", parent);
		this.manettesCombo = new JComboBox();

		
		this.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
		
		for(int i = 0; i < controllers.length; i++){
            Controller controller = controllers[i];         
            this.manettesCombo.addItem(controller.getName());
        }
		
		this.add(manettesCombo);
        
	}
	
	public ScorableStrategy getStrategy(Circuit c, Voiture v) {

		Controller input = null;
		for(int i = 0; i < controllers.length; i++){
            Controller controller = controllers[i];
            System.out.println(manettesCombo.getSelectedItem());
            System.out.println(controller.getName());
            
            if( controller.getName().equals(manettesCombo.getSelectedItem()) ){
            	  input = controller;
            }
                
        }
		
		return new StrategyInput(0, input, v);
	}

}
