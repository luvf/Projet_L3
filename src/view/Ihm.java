package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.Dimension;
import tools.MouseEventSender;


public class Ihm extends JPanel implements UpdateEventListener, MouseListener, MouseEventSender{

	private ArrayList<ObserverSWING> obs;
	//private Graphics g;
	private ArrayList<MouseListener> mouseListen;
	public Ihm(){
		this.obs = new ArrayList<ObserverSWING>();
		this.mouseListen = new ArrayList<MouseListener>();
		addMouseListener(this);

	}
	
	public void paint(Graphics g){
		super.paint(g);
		for (ObserverSWING ob: obs){
			ob.print(g);
		}
	}
	
	public void manageUpdate() {

		try{
			Thread.sleep(3);//5
			
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				repaint();
			}
		});
		
	}
	
	
	public void add(ObserverSWING obs){
		this.obs.add(obs);
	}
	


	public void mouseClicked(MouseEvent e){
		for (MouseListener el : this.mouseListen){
			el.mouseClicked(e);
		}
	}

	public void mouseExited(MouseEvent e){
		for (MouseListener el : this.mouseListen){
			el.mouseExited(e);
		}	
	}
	public void mouseEntered(MouseEvent e){
		for (MouseListener el : this.mouseListen){
			el.mouseEntered(e);
		}
	}
	public void mouseReleased(MouseEvent e){
		for (MouseListener el : this.mouseListen){
			el.mouseReleased(e);
		}
	}
	public void mousePressed(MouseEvent e){
		for (MouseListener el : this.mouseListen){
			el.mousePressed(e);
		}
	}

	public void add(MouseListener el){
		this.mouseListen.add(el);
	}
	public void remove(MouseListener el){
		this.mouseListen.remove(el);
	}

	public void saveImage(String filename){
	 //BufferedImage saveImage = ScreenImage.createImage(this);
	 
		BufferedImage image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		paint(g2d);
		g2d.dispose();
		
		try {
			File outputfile = new File(filename);
			ImageIO.write(image, "png", outputfile);
		} catch (Exception e){	
			System.out.println("Erreur dans la sauvegarde de l'image :\n" + e.getMessage());
		}
}
}