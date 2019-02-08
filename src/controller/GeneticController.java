
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
import javax.swing.JProgressBar;
import circuit.Circuit;
import strategy.ScorableStrategy;
import strategy.Strategy;
import strategy.StrategyParam;
import voiture.Voiture;

import algo.Genetic;

import tools.Callback;

import java.util.concurrent.Semaphore;




public class GeneticController extends StratController implements Callback {
	
	private JButton compute;
	private JButton init;

	private JFormattedTextField nbTurn;
	private JLabel meilleurTemps;

	private Circuit circ;

	private Genetic genetic;

	private JFormattedTextField taillePopF;

	private int progression;
	private int maxProg;
	private JProgressBar progressB;
	
	public GeneticController(StratCompositeController parent, Circuit c){
		super("Genetic Controller", parent);
		
		this.compute = new JButton("compute");
		this.init = new JButton("init");
		this.meilleurTemps = new JLabel("Meilleur temps : ------");
		this.nbTurn = new JFormattedTextField(NumberFormat.getIntegerInstance());
		this.taillePopF = new JFormattedTextField(NumberFormat.getIntegerInstance());
		
		this.circ = c;
		

		
		this.init.addActionListener(this);
		this.compute.addActionListener(this);

		

		this.nbTurn.setValue((long) 2);
		this.taillePopF.setValue((long) 10);
		this.progressB =new JProgressBar(0,100);

		Box tmp = Box.createHorizontalBox();
		this.mainBox.add(tmp);
		tmp.add(this.init);
		tmp.add(this.compute);
		tmp.add(meilleurTemps);
		this.mainBox.add(this.nbTurn);
		this.mainBox.add(this.taillePopF);


		this.mainBox.add(this.progressB);

	}

	private void init(){
		int n = getTaillePop();
		this.progressB.setValue(0);
		this.progression = 0;
		this.maxProg = n;
		this.genetic.init(n, this);
	}

	public void step(){

		GeneticController self = this;
		int n = getNbTurn();
		int p = getTaillePop();
		this.progressB.setValue(0);
		this.maxProg = n * p;
		this.progression = 0;
		for (int i=0; i < getNbTurn(); i++){
			Thread t = new Thread(){
				public void run() {
					genetic.step(p, self);
				}
			};
			t.start();
		}
	}

	public void updateScore(){
		this.meilleurTemps.setText( "Meilleur temps : " + Integer.toString(this.genetic.bestScore()));
	}

	public int getTaillePop(){
		return (int) (long)this.taillePopF.getValue();
	}
		public int getNbTurn(){
		return (int) (long)this.nbTurn.getValue();
	}


	public ScorableStrategy getStrategy(Circuit c, Voiture v) {
		return new StrategyParam(c, v, this.genetic.get(0));
	} 

	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("compute")){
			step();
		}
		if (a.getActionCommand().equals("init")){
			this.genetic = new Genetic(getTaillePop(), 0.1, this.circ);
			init();
		}
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
	}
	public void updateProgress(){
		this.progression++;
		this.progressB.setValue(this.progression*100/this.maxProg);
	}

	public void callback(String str){
		if ("update".equals(str)){
			updateScore();
		}
		if ("1".equals(str)){
			updateProgress();
		}
	}
	
}
