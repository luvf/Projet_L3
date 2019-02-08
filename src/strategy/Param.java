package strategy;


public class Param {
	public double[] distFreinMurR;// distance a la quelle on entre en freinage
	public double[] forceFreinMurR;// force de frenage en face d'un mur
	public double[] turnAccR;

	public double[] distFreinMurBR;// distance a la quelle on entre en freinage
	public double[] forceFreinMurBR;// force de frenage en face d'un mur
	public double[] turnAccBR;

	public double[] distFreinMurBB;// distance a la quelle on entre en freinage
	public double[] forceFreinMurBB;// force de frenage en face d'un mur
	public double[] turnAccBB;
	
	public double nbfaisceau;
	
	public Param(double[][] p){
		this.distFreinMurR= new double[p[0].length];
		for (int i =0 ; i< p[0].length; i++ ) {
			distFreinMurR[i]=p[0][i];
		}
		this.forceFreinMurR = new double[p[1].length];
		for (int i =0 ; i< p[1].length; i++ ) {
			forceFreinMurR[i]=p[1][i];
		}
		this.turnAccR = new double[p[2].length];
		for (int i =0 ; i< p[2].length; i++ ) {
			turnAccR[i]=p[2][i];
		}


		this.distFreinMurBR= new double[p[3].length];
		for (int i =0 ; i< p[3].length; i++ ) {
			distFreinMurBR[i]=p[3][i];
		}
		this.forceFreinMurBR = new double[p[4].length];
		for (int i =0 ; i< p[4].length; i++ ) {
			forceFreinMurBR[i]=p[4][i];
		}
		this.turnAccBR = new double[p[5].length];
		for (int i =0 ; i< p[5].length; i++ ) {
			turnAccBR[i]=p[5][i];
		}

		this.distFreinMurBB= new double[p[6].length];
		for (int i =0 ; i< p[6].length; i++ ) {
			distFreinMurBB[i]=p[6][i];
		}
		this.forceFreinMurBB = new double[p[7].length];
		for (int i =0 ; i< p[7].length; i++ ) {
			forceFreinMurBB[i]=p[7][i];
		}
		this.turnAccBB = new double[p[8].length];
		for (int i =0 ; i< p[8].length; i++ ) {
			turnAccBB[i]=p[8][i];
		}
		this.nbfaisceau = p[9][0];
	}
	
	public Param(double[] p, int[] config){
		this.distFreinMurR= new double[config[0]];
		int cur = 0;
		for (int i =0 ; i< config[0]; i++ ) {
			distFreinMurR[i]=p[i + cur];
		}
		this.forceFreinMurR = new double[config[1]];
		cur += config[0];
		for (int i =0 ; i< config[1]; i++ ) {
			forceFreinMurR[i]=p[i + cur];
		}
		cur += config[1];
		this.turnAccR = new double[config[2]];
		for (int i =0 ; i< config[2]; i++ ) {
			turnAccR[i]=p[i + cur];
		}
		cur += config[2];
		//---------------
		this.distFreinMurBR= new double[config[3]];
	
		for (int i =0 ; i< config[3]; i++ ) {
			distFreinMurBR[i]=p[i + cur];
		}
		this.forceFreinMurBR = new double[config[4]];
		cur += config[3];
		for (int i =0 ; i< config[4]; i++ ) {
			forceFreinMurBR[i]=p[i + cur];
		}
		cur += config[4];
		this.turnAccBR = new double[config[5]];
		for (int i =0 ; i< config[5]; i++ ) {
			turnAccBR[i]=p[i + cur];
		}
		cur += config[5];
		//---------------
		this.distFreinMurBB= new double[config[6]];
		
		for (int i =0 ; i< config[6]; i++ ) {
			distFreinMurBB[i]=p[i + cur];
		}
		this.forceFreinMurBB = new double[config[7]];
		cur += config[6];
		for (int i =0 ; i< config[7]; i++ ) {
			forceFreinMurBB[i]=p[i + cur];
		}
		cur += config[7];
		this.turnAccBB = new double[config[8]];
		for (int i =0 ; i< config[8]; i++ ) {
			turnAccBB[i]=p[i + cur];
		}
		cur += config[8];

		this.nbfaisceau =p[cur];
	}

	public int[] getConfig(){
		int[] ret= new int[4];
		ret[0] = this.distFreinMurR.length;
		ret[1] = this.forceFreinMurR.length;
		ret[2] = this.turnAccR.length; 

		ret[3] = this.distFreinMurBR.length;
		ret[4] = this.forceFreinMurBR.length;
		ret[5] = this.turnAccBR.length; 

		ret[6] = this.distFreinMurBB.length;
		ret[7] = this.forceFreinMurBB.length;
		ret[8] = this.turnAccBB.length; 
		ret[9] = 1;
		return ret;
	}


	
	/*public double[][] toTab(){
		double[][] ret = new double[4][];
		ret[0]=new double[distFreinMur.length];
		for (int i =0 ; i< distFreinMur.length; i++ ) {
			ret[0][i]=distFreinMur[i];
		}
		ret[1]=new double[forceFreinMur.length];
		for (int i =0 ; i< forceFreinMur.length; i++ ) {
			ret[0][i]=forceFreinMur[i];
		}
		ret[2]=new double[turnAcc.length];
		for (int i =0 ; i< turnAcc.length; i++ ) {
			ret[2][i]=turnAcc[i];
		}
		ret[3]=new double[1];
		ret[3][0]= this.nbfaisceau;
		return ret;
	}*/
	public double[] toSimpleTab(){
		
		int size = 	this.distFreinMurR.length + this.forceFreinMurR.length +this.turnAccR.length +
					this.distFreinMurBR.length + this.forceFreinMurBR.length +this.turnAccBR.length +
					this.distFreinMurBB.length + this.forceFreinMurBB.length +this.turnAccBB.length + 1;
		int cur = 0;
		double[] ret = new double[size];

		for (int i =0 ; i< distFreinMurR.length; i++ ) {
			ret[i+cur]=distFreinMurR[i];
		}
		cur += distFreinMurR.length;
		for (int i =0 ; i< forceFreinMurR.length; i++ ) {
			
			ret[i + cur]=forceFreinMurR[i];
		}
		cur+= this.forceFreinMurR.length;
		for (int i =0 ; i< turnAccR.length; i++ ) {
			ret[i + cur]=turnAccR[i];
		}
		cur+= this.turnAccR.length;
		//----------
		for (int i =0 ; i< distFreinMurBR.length; i++ ) {
			ret[i+cur]=distFreinMurBR[i];
		}
		cur += distFreinMurBR.length;
		for (int i =0 ; i< forceFreinMurBR.length; i++ ) {
			
			ret[i + cur]=forceFreinMurBR[i];
		}
		cur+= this.forceFreinMurBR.length;
		for (int i =0 ; i< turnAccBR.length; i++ ) {
			ret[i + cur]=turnAccBR[i];
		}
		cur+= this.turnAccBR.length;
		//----------
		for (int i =0 ; i< distFreinMurBB.length; i++ ) {
			ret[i+cur]=distFreinMurBB[i];
		}
		cur += distFreinMurBB.length;
		for (int i =0 ; i< forceFreinMurBB.length; i++ ) {
			
			ret[i + cur]=forceFreinMurBB[i];
		}
		cur+= this.forceFreinMurBB.length;
		for (int i =0 ; i< turnAccBB.length; i++ ) {
			ret[i + cur]=turnAccBB[i];
		}
		cur+= this.turnAccBB.length;
		ret[cur] = this.nbfaisceau;
		return ret;
	}
}