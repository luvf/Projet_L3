package circuit;


public class CircuitFactory{

    private Terrain terrain[][];
    
    public CircuitFactory(String filename){
        this.terrain = CircuitTools.fileToTerrain(filename);
    }

    public Circuit Build(){
        return new CircuitImpl(this.terrain);
    }
}