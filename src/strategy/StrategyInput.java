package strategy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import voiture.Commande;
import voiture.Voiture;

public class StrategyInput extends ScorableStrategy{

	private double turnSensi;
	private Controller manette;
	private Component[] touches;
	
	public StrategyInput(double turnSensi, Controller manette, Voiture voit){
		super(null, voit);
		this.turnSensi = turnSensi;
		this.manette = manette;
		touches = manette.getComponents();
		}
	
	public Commande getCommande() {

		double acc = 0;
		double rotation = 0;
		manette.poll();
		for(int i=0;i<touches.length;i++) {
			if(touches[i].getName().equals("Axe Z")){
				float z = touches[i].getPollData();
				acc = (double) -z;
			}
			if(touches[i].getName().equals("Axe X")){
				float r = touches[i].getPollData();
				//if(r < 0.1){r =0;}
				System.out.println(Float.toString(r));
				rotation = (-1 * r)/10;
			}
		}
		
		double maxTurn = this.voit.getMaxTurn();
		if (Math.abs(rotation) > maxTurn){
			rotation = Math.signum(rotation)*maxTurn;
		}
		long start=System.nanoTime(); 
		while((System.nanoTime()-start)<600000); 
		return new Commande(acc, rotation);
	}



	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
