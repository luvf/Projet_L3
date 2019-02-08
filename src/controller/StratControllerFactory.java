package controller;

import circuit.Circuit;
import view.Ihm;

import exam.ControllerExam;

public class StratControllerFactory{
	public final static String[] NAMES={"radar", "trigger", "genetic", "input","bezier", "exam"};
	public static Circuit circ;
	public static Ihm ihm;
	
	public static StratController getStrategyController(String name, StratCompositeController parent){
		if (name.equals("radar")){
			return new RadarStratController(parent);
		}
		if (name.equals("trigger")){
			return new StratTriggerController(parent);
		}
		if (name.equals("genetic")){
			return new GeneticController(parent, circ);
		}
		if (name.equals("input")){
			return new InputStratController(parent);
		}
		if (name.equals("bezier")){
			return new BezierController(parent, circ, ihm );
		}
		if (name.equals("exam")){
			return new ControllerExam(parent);
		}
		return null;
	}
	public static StratController getStrategyController(String name){
		return getStrategyController( name, null);
	
	}
	
	
}