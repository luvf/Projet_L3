package strategy;
import voiture.Commande;
import java.util.ArrayList;
import java.io.IOException;
import java.io.EOFException;
import java.io.DataInputStream;
import java.io.FileInputStream;
public class StrategyListeCommande implements Strategy{
	private ArrayList<Commande> commandes;
	private int compteur;
	public StrategyListeCommande(Commande com[]){
		this.compteur = 0;
		this.commandes = new ArrayList<Commande>();
		for (int i= 0 ; i< com.length; i++){
			commandes.add(com[i]);
		}
	}
	public StrategyListeCommande(ArrayList<Commande> com){
		this.compteur = 0;
		this.commandes = new ArrayList<Commande>(com);
			
	}
	public StrategyListeCommande(String filename) throws IOException{
		try {
			DataInputStream os = new DataInputStream(new FileInputStream(filename));
			this.commandes = new ArrayList<Commande>();
			double a,t;
			while(true){ // on attend la fin de fichier
				a = os.readDouble();
				t = os.readDouble();
				this.commandes.add(new Commande(a,t));
			}
		} catch (EOFException e){	}
}
	public Strategy clone(){
			return new StrategyListeCommande(this.commandes);
	}
	public Commande getCommande(){
		if (this.compteur < this.commandes.size()){
			this.compteur++;
			return commandes.get(compteur-1);				  
		}
		return null;
	}

	public int remain(){
		return this.commandes.size()-this.compteur;
	}
}