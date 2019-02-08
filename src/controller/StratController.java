package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import circuit.Circuit;
import strategy.ScorableStrategy;
import strategy.Strategy;
import voiture.Voiture;

public abstract class StratController extends JPanel implements ChangeListener, ActionListener{
	
	
	protected JLabel name;
	protected JButton del;
	protected Box mainBox;
	protected StratCompositeController parent;
	
	public StratController(String n, StratCompositeController parent){
		this.parent = parent;
		this.name = new JLabel(n);

		this.mainBox = Box.createVerticalBox();
		Box titleBox = Box.createHorizontalBox();
		if (parent !=null){
			this.del = new JButton("delete");
			titleBox.add(del);
			del.addActionListener(this);
		}
		titleBox.add(name);
		this.mainBox.add(titleBox);
		this.add(mainBox);
		this.revalidate();
	}
	
	
	public abstract ScorableStrategy getStrategy(Circuit c, Voiture v);

	public void actionPerformed(ActionEvent a) {
		if (a.getActionCommand().equals("delete")){
			if (this.parent != null){
				parent.del(this);
			}
		}
	}


	@Override
	public void stateChanged(ChangeEvent e) {
		
	}
	
}
