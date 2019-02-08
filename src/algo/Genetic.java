package algo;


import voiture.Voiture;
import voiture.VoitureFactory;

import simulation.SimpleSim;

import circuit.Circuit;
import strategy.Param;
import tools.Callback;


import java.util.concurrent.PriorityBlockingQueue;

import java.util.ArrayList;
import java.util.Random;


import java.util.concurrent.Semaphore;


public class Genetic{

	private int size;

	private ArrayList<Gene> population;
	private ArrayList<Gene> children;

	private double mut;

	private int[] configGen;

	private SimpleSim sim;
	
	private boolean avalable;
	private Semaphore childrenMutex;
	private Semaphore processSem;
	private Semaphore running;

	public Genetic(int size, double mut, Circuit c ){
		this.size= size;
		this.population = new ArrayList<Gene>();
		this.children = new ArrayList<Gene>();
		this.mut= mut;
		VoitureFactory voitFact = new VoitureFactory(c);

		this.sim= new SimpleSim(c, voitFact.Build());

		configGen =new int[10];
		for (int i=0; i< 9; i++){
			configGen[i]=10;
		}
		configGen[9] = 1;

		this.childrenMutex = new Semaphore(1);
		this.processSem = new Semaphore(size);
		this.running = new Semaphore(1);
	}

	public void setSize(int s){
		try{
			this.running.acquire();
			this.size = s;
			processSem = new Semaphore(this.size);
		}catch(Exception e){	}
		this.running.release();
		

	}
	public int getSize(){
		return this.size;
	}
	public void genOneElement(){
		int len =0;
		for (int i=0; i< this.configGen.length; i++){
			len+= configGen[i];
		}
		
		double[] cur = new double[len];
		Gene curGene;
		
		do {
			for (int j = 0; j < len; j++){
				cur[j]= (Math.random());
			}
			curGene=new Gene(new Param(cur, configGen), sim);
				
		}while(curGene.score >= 1000000);
		try{
			childrenMutex.acquire();
			this.children.add(curGene);
		}
		catch(Exception a){}
		finally{
			childrenMutex.release();
		}
		
		processSem.release();
		
	}

	public Semaphore getChildMutex(){
		return this.childrenMutex;
	}

	public Semaphore procesRunning(){
		return this.processSem;
	}

	public void init(int n, Callback call){
		setSize(n);
		try{
			this.running.acquire();
			processSem.acquire(n);
		}
		catch(Exception e){	}
		
		Thread t = new Thread(){
			public void run() {	
				for (int  i = 0;i< n; i++){
					Thread u = new Thread(){
						public void run() {
							genOneElement();
							call.callback("1");
						}
					};
					u.start();
				}
				try{
					processSem.acquire(n);
					selection();
				}
				catch(Exception a){}
				finally{
					processSem.release(n);
					running.release();
					call.callback("update");
				}
			}
		};
		t.start();
		
	}

	public Param[] genTwoChildren(){
		Param[] ret = new Param[2];
		double[] a = this.population.get((int)Math.floor(Math.random()*size)).param.toSimpleTab();
		double[] b;
		do{
			b = this.population.get((int)Math.floor(Math.random()*size)).param.toSimpleTab();
		}while (b == a);
		double[][] childs = crossingOver(a, b);
		ret[0] = new Param(mutate(childs[0]), this.configGen);
		ret[1] = new Param(mutate(childs[1]), this.configGen);

		return ret;
	
	}

	public void genOneChildren(){
		
		Param cur;
		double[] a = this.population.get((int)Math.floor(Math.random()*size)).param.toSimpleTab();
		double[] b;
		do{
			b = this.population.get((int)Math.floor(Math.random()*size)).param.toSimpleTab();
		}while (b == a);
		double[] child = simpleCrossingOver(a, b);
		cur = new Param(mutate(child), this.configGen);

		try {
			childrenMutex.acquire();
			this.children.add(new Gene(cur, sim));
		}catch(Exception e){	}
		finally{
			childrenMutex.release();
		}
		processSem.release();
		
	
	}
	
	public double[][] crossingOver(double[] a, double[] b){
		double[][]ret = new double[2][];
		ret[0] = new double[a.length];
		ret[1] = new double[a.length];
		for (int i= 0; i < a.length; i++){
			if (Math.random()<0.5){
				ret[0][i]=a[i];
				ret[1][i]=b[i];
			}
			else{
				ret[0][i]=b[i];
				ret[1][i]=a[i];
			}

		}
		return ret;
	}

	public double[] simpleCrossingOver(double[] a, double[] b){
		double[]ret = new double[a.length];
		for (int i= 0; i < a.length; i++){
			if (Math.random()<0.5){
				ret[i]=a[i];
			}
			else{
				ret[i]=b[i];
			}

		}
		return ret;
	}
	
	public double[] mutate(double[] p){
		Random random = new Random();
		for (int i = 0; i< p.length; i++){
			if (Math.random()< this.mut){
				p[i] = (double)Math.min(1, 
						Math.max(0,
						p[random.nextInt(p.length)]+random.nextGaussian()));
			}
		}
		
		return p;
	}

	public Param get(int i){
		return this.population.get(i).param;
	}

	public void step2(){
		for (int i =0; i< this.size; i++){
			Param[] cur = genTwoChildren();
			Gene g1 = new Gene(cur[0], sim);
			Gene g2 = new Gene(cur[1], sim);
			this.children.add(g1);
			this.children.add(g2);

		}
		selection();
	}
	
	public void step(int n, Callback call){
		setSize(n);
		try{
			this.running.acquire();
			this.processSem.acquire(n);
		}
		catch(Exception e){	}
		for (int i = 0; i< n; i++) {
			Thread t = new Thread(){
				public void run() {
					genOneChildren();
					call.callback("1");
				}
			};
			t.start();
		}
		try{
			processSem.acquire(n);
			selection();

			call.callback("update");
		}
		catch(Exception a){}
		finally{
			processSem.release(n);
		}

		this.running.release();
			
	}




	public int bestScore(){
		if (this.population.size()>0){
			return this.population.get(0).score;
		}
		return -1;
	}

	public void selection(){
		PriorityBlockingQueue<Gene> heap = new PriorityBlockingQueue<Gene>(1, new Gene());
		for (Gene el : this.population){
			heap.add(el);
		}
		for (Gene el : this.children){
			heap.add(el);
			
		}
		System.out.println("lolo");
		this.population = new ArrayList<Gene>();
		for (int i=0 ; i < this.size; i++){
			Gene cur = heap.poll();
			population.add(cur);
		}
		this.children = new ArrayList<Gene>();
	}
}


