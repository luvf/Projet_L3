package circuit;

import geometrie.Vecteur;

import java.util.ArrayList;

import view.UpdateEventSender;
import view.UpdateEventListener;


public class CircuitEdit implements Circuit, UpdateEventSender{
        
        private ArrayList<modification> modifs;
        private int width;
        private int height;
        private Terrain terrain[][];
        private Circuit circ;
        
        private ArrayList<UpdateEventListener> listener;
        

        public CircuitEdit(Circuit circ){
                this.modifs = new ArrayList<modification>();
                this.listener = new ArrayList<UpdateEventListener>();
                this.width = circ.getWidth();
                this.height = circ.getHeight();
                this.circ = circ;
                

                this.terrain = new Terrain[this.height][this.width];
                for (int i=0; i< this.height; i++){
                        for (int j = 0; j< this.width; j++) {
                                this.terrain[i][j]= circ.getTerrain(i, j);
                        }
                }
                
        }
        public CircuitEdit(CircuitEdit circ){
                this((Circuit)circ);
                for (modification t : circ.modifs){
                        this.modifs.add(t);
                }
                
        }
        
        public void addModif(modification m){
                addModif(m.x, m.y, m.t, true);
        }

        public void addModif(int x, int y, Terrain t, boolean safe){
                if (x >= 0 && y >= 0 && y < this.width && x < this.height){
                        if (this.terrain[x][y] != t){
                        	if(safe){
                                if (this.terrain[x][y] != Terrain.Herbe &&
                                        this.terrain[x][y] != Terrain.EndLine && 
                                        this.terrain[x][y] != Terrain.StartPoint){
                                        this.modifs.add(new modification(x, y, this.terrain[x][y]));
                                        this.terrain[x][y] = t;
                                }
                        	}
                        	else{
                        		if (this.terrain[x][y] != Terrain.EndLine && 
                                    this.terrain[x][y] != Terrain.StartPoint){
                                    this.modifs.add(new modification(x, y, this.terrain[x][y]));
                                    this.terrain[x][y] = t;
                                }
                        	}
                                //if (! (this.terrain[x][y] == Terrain.Herbe &&(t == Terrain.BandeRouge ||t == Terrain.BandeBlanche )) ){
                                        
                                //}
                                                        
                        }
                }
        }

        public void unDo(){
                if(this.modifs.size()>0){
                        modification last = this.modifs.get(this.modifs.size()-1);
                        this.modifs.remove(this.modifs.size()-1);
                        this.terrain[last.x][last.y]= last.t;
                        update();
                }
        }

        public void clean(){//netoie les zone rouges et blanches
                for (int i=0; i< this.height; i++){
                        for (int j = 0; j< this.width; j++) {
                                if (this.terrain[i][j] == Terrain.BandeRouge ||  this.terrain[i][j] == Terrain.BandeBlanche){
                                        this.terrain[i][j]=Terrain.Route;
                                }
                        }
                }
        }

        public void add(UpdateEventListener listener){
                this.listener.add(listener);
        }
        public void update(){
                for (UpdateEventListener li : listener){
                        li.manageUpdate();
                }
        }

        public Circuit toCircuit(){
                return new CircuitImpl(this.terrain);
        }

        public Terrain getTerrain(int i, int j) {
                return terrain[i][j];
        }

        @Override
        public Terrain getTerrain(Vecteur v) {
                return getTerrain((int) v.x, (int)v.y);
        }

        @Override
        public Vecteur getPointDepart() {
                return this.circ.getPointDepart();
        }

        @Override
        public Vecteur getDirectionDepart() {
                return this.circ.getDirectionDepart();
        }

        @Override
        public Vecteur getDirectionArrivee() {
                return this.circ.getDirectionArrivee();
        }

        @Override
        public int getWidth() {
                return this.width;
        }

        @Override
        public int getHeight() {
                return this.height;
        }

        @Override
        public ArrayList<Vecteur> getArrivees() {
                return this.circ.getArrivees();
        }

        @Override
        public double getDist(int i, int j) {
                return 0;
        }

        @Override
        public double getDist(Vecteur v) {
                return 0;
        }

        @Override
        public Circuit clone() {
                return new CircuitEdit(this.circ);
        }

        
}

class modification {
        public final int x;
        public final int y;
        public final Terrain t;

        public modification(int x, int y, Terrain t){
                this.x = x;
                this.y = y;
                this.t = t;
        }

}