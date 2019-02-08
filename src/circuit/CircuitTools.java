package circuit;


import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import java.awt.image.BufferedImage;

public class CircuitTools{

	public static Terrain[][] fileToTerrain(String filename){
		InputStream file=null;
		
		try {
			file = new FileInputStream(filename);
			int col, line;
			Terrain ret[][];
			InputStreamReader fr = new InputStreamReader(file);
			BufferedReader in = new BufferedReader(fr);
			
			String buf;
			buf = in.readLine();
			col = Integer.parseInt(buf);
			
			buf = in.readLine();
			line = Integer.parseInt(buf);

			ret = new Terrain[line][col];

			for (int i = 0; i< line; i++){
				buf = in.readLine();
				for (int j = 0 ; j < col; j++){
					
					ret[i][j] = TerrainTools.fromChar(buf.charAt(j));
				}
			}

		in.close(); 
		return ret;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Invalid Format : " + file + "... Loading aborted\n" + e.getMessage());
			return null;
		}
	}

	public static Terrain[][] imageToTerrain(String filename){//TODO
		InputStream file=null;
		
		try {
			file = new FileInputStream(filename);
			int col, line;
			Terrain ret[][];
			InputStreamReader fr = new InputStreamReader(file);
			BufferedReader in = new BufferedReader(fr);
			
			String buf;
			buf = in.readLine();
			col = Integer.parseInt(buf);
			
			buf = in.readLine();
			line = Integer.parseInt(buf);

			ret = new Terrain[line][col];

			for (int i = 0; i< line; i++){
				buf = in.readLine();
				for (int j = 0 ; j < col; j++){
					
					ret[i][j] = TerrainTools.fromChar(buf.charAt(j));
				}
			}

		in.close(); 
		return ret;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Invalid Format : " + file + "... Loading aborted\n" + e.getMessage());
			return null;
		}
	}
	public static BufferedImage circuitToImage(Circuit circ){
		int w = circ.getHeight();
		int h = circ.getWidth();
		BufferedImage ret = new BufferedImage(h, w, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < h; i++){
			for (int j = 0; j < w; j++) {
				ret.setRGB(i,j, TerrainTools.toRGB(circ.getTerrain(j, i)).getRGB());
			}
		}
		return ret;
	}

	public static void circuitToFile(Circuit circ, String filename){
		File file=null;
		file = new File(filename);
		try{
		FileWriter fw = new FileWriter(file);
		
		
		int height = circ.getHeight();
		int width = circ.getWidth();
		
		  fw.write (String.valueOf (width));
		  fw.write ("\r\n");
		  fw.write (String.valueOf (height));
		  fw.write ("\r\n");
		
		
		for(int i = 0; i < height; i++){
			
			for(int j = 0; j < width ; j++ ){
				fw.write(TerrainTools.toChar(circ.getTerrain(i, j)));
			}
			fw.write ("\r\n");
		}
		
		
		fw.close();
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Invalid Format : " + file + "...Saving aborted\n" + e.getMessage());
	}

	}

	
}
