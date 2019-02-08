package tools;
import voiture.Commande;
import java.util.ArrayList;
import java.io.IOException;
import java.io.EOFException;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class ListeCommandeTools {
	
        public static void saveListeCommande(ArrayList<Commande> liste, String filename){
                try {
                        DataOutputStream os = new DataOutputStream(new FileOutputStream(filename));
                        for(Commande c:liste){
                                os.writeDouble(c.getAcc());
                                os.writeDouble(c.getTurn());
                        }
                        os.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
        public static ArrayList<Commande> loadListeCommande(  String filename) throws IOException{
                ArrayList<Commande> liste = null;
                try {
                        DataInputStream os = new DataInputStream(new FileInputStream(filename));
                        liste = new ArrayList<Commande>();
                        double a,t;
                        while(true){ // on attend la fin de fichier
                                a = os.readDouble();
                                t = os.readDouble();
                                liste.add(new Commande(a,t));
                        }
                } catch (EOFException e){
                        return liste;
                }
        }
}