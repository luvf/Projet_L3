package circuit;

import java.awt.Color;

public enum Terrain {
    Route,             //.
    Herbe,             //g
    Eau,             //b
    Obstacle,         //o
    BandeRouge,        //r
    BandeBlanche,    //w
    StartPoint,     //*
    EndLine,        //! 
    Boue;            //m

    public boolean isRunable(){
        return (this.ordinal() < 1 ||  this.ordinal() > 3) ; //pas (herbe eau obstacle)
    }

    
}


class TerrainTools{
    public static char[] conversion =
        {'.', 'g', 'b', 'o', 'r', 'w', '*', '!', 'm'};
    
    

    public static Color[] convColor = {
        Color.gray, 
        Color.green,
        Color.blue, 
        Color.black, 
        Color.red, 
        Color.white,
        Color.cyan, 
        Color.cyan, 
        new Color(200, 150, 128)
    };

    public static Terrain fromChar(char c){
        for (int i = 0; i < 9; i++){
            if (TerrainTools.conversion[i] == c){

                return Terrain.values()[i];
            }
        }
        throw new RuntimeException("terrain inconu" + c);
    }
    public static char toChar(Terrain t){
        return TerrainTools.conversion[t.ordinal()];
    }

    public static Color toRGB(Terrain t){
        return TerrainTools.convColor[t.ordinal()];
    }

}

