package algo;
import java.util.Comparator;

import geometrie.Vecteur;

public class ComparatorDijkRe implements Comparator<Vecteur> {// a templatiser
	private double[][] dist;

	public ComparatorDijkRe(double[][] dist) {
		this.dist = dist;
	}

	public int compare( Vecteur o1,  Vecteur o2) {
		if(dist[(int) o1.x][(int) o1.y]>dist[(int) o2.x][(int) o2.y])
			return 1;
		else if (dist[(int) o1.x][(int) o1.y] == dist[(int) o2.x][(int) o2.y])
			return 0;
		return -1;
	}
}
