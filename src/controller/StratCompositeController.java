package controller;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeListener;

import circuit.Circuit;
import strategy.StrategyComposite;
import strategy.StrategyListeCommande;
import voiture.Commande;
import voiture.Voiture;

import java.util.ArrayList;
 

public class StratCompositeController extends JFrame implements ActionListener{

	private JButton addStr;
	private JComboBox<String> strats;

	private ArrayList<StratController> stratListe;

	private Box stratBox;

	private JLabel stats;

	
	public StratCompositeController(){
		this.setLayout(new BorderLayout());// peut etre chercher un autre layout
	
		
		//this.setPreferredSize(new Dimension( 400,75));
		//this.pack();

		this.getContentPane().setLayout(new BorderLayout());
		//fen.pack();
		setVisible(true);

		JPanel topPanel = new JPanel();

		this.addStr = new JButton("add");
		topPanel.add(addStr);
		addStr.addActionListener(this);
		

		this.strats = new JComboBox<String>(StratControllerFactory.NAMES);
		topPanel.add(strats);


		this.stats = new JLabel("");
		topPanel.add(this.stats);
		
		this.add(topPanel, BorderLayout.NORTH);


		this.stratBox = Box.createVerticalBox();
		this.add(this.stratBox, BorderLayout.CENTER);
		
		this.stratListe = new ArrayList<StratController>();
		this.pack();

	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("add")){
			add(StratControllerFactory.getStrategyController( (String) strats.getSelectedItem(), this));
		}
	}

	public void add(StratController elm){
		
		this.stratListe.add(elm);
		this.stratBox.add(elm);
		this.pack();
	}
	public void del(StratController elm){
		this.stratBox.remove(elm);//pas sur
		this.stratListe.remove(elm);// il me semble
		this.pack();
		
	}


	public StrategyComposite getStrategy(Circuit c, Voiture v, ArrayList<Commande> liste){// pas tres propre
		StrategyComposite ret = new StrategyComposite(c, v, liste);

		for (StratController strat : stratListe){
			ret.add(strat.getStrategy(c, v));
		}
		return ret;
	}
	


	public void update(Voiture voit) {
		
		String vit  = "Vitesse : " + Double.toString(voit.getVitesse());
		String pos  = "Position : " + voit.getPosition().toString();
		String dir  = "Direction : " + voit.getDirection().toString();
		String com  = "Commande : ";
		String out = vit + "\n"+ pos + "\n" +dir;

		this.stats.setText(out);//vit+ pos+ dir +com
		//this.revalidate();
	}
}