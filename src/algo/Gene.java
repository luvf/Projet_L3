package algo;

import strategy.Param;
 
import simulation.SimpleSim;

import java.util.Comparator;





class Gene implements Comparator<Gene>{
	public final Param param;
	public final int score;
	
	public Gene(Param p, int score){
		this.param = p;
		this.score = score;
	}

	public Gene(Param p, SimpleSim sim){
		this.param = p;
		this.score = sim.play(p, 8000);
	}

	public Gene(){
		this.param = null;
		this.score = -1;
	}
	

	public int compare( Gene g1,  Gene g2) {
		if(g1.score > g2.score)
			return 1;
		else if (g1.score == g2.score)
			return 0;
		return -1;
	}


}