package strategy;
import voiture.Commande;
import java.util.ArrayList;
import java.io.IOException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;


public class StrategyRecord implements Strategy{
	private Strategy strat;
	private ArrayList<Commande> record;
	
	public StrategyRecord(Strategy strat){//A MODIFIER TRES SALE 
		this.strat = strat;
		this.record = new ArrayList<Commande>();
	}
	
	public Strategy clone(){
		return new StrategyRecord(this.strat);
	}
	public Commande getCommande(){
		Commande ret = this.strat.getCommande();
		this.record.add(ret);
		return ret;
	}
	public ArrayList<Commande> getRecord(){
		return this.record;
	}
	public void save(String filename){
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream(filename));
			for(Commande c:this.record){
				os.writeDouble(c.getAcc());
				os.writeDouble(c.getTurn());
			}
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Commande getLastCommand(){
		return this.record.get(this.record.size()-1);
	}


	
}