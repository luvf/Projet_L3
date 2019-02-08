package geometrie;



public class VecteurTest {

	public static void testVecteur(){
		// TODO Auto-generated method stub
		Vecteur u = new Vecteur(1,0);
		System.out.println(u);
		u = u.rotation(Math.PI/2);
		System.out.println(u);
		u = u.rotation(Math.PI/2);
		System.out.println(u);
		
	}

}
