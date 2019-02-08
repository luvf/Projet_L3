package algo;

public interface Radar {
    public double[] scores(); // score de chaque branche
    public double[] distancesInPixels(); // pour l'observer
    public int getBestIndex(); // meilleur indice
    public double[] thetas(); // angles de chaque faisceau
}
