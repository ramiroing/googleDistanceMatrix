package distancias;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import com.mashape.unirest.http.Unirest;


public class Main {

	public static final int filaInicio = 31;
	public static final String archivoEntrada = "puntosSP";
	public static final String archivoSalida = "sanpablo3";
	
	//public static String[] keys = new String[]{};
	
	public static String[] keys = new String[]{	"AIzaSyD-2crJzfwoLh1h9ecpInD8Vqf4xLEDhn4",
												"AIzaSyDw0TZG6kGQ6r8Z10PXDGlyL9vrGyOQMyQ",
												"AIzaSyAWq5t5xZvXL-685Uhe1nYRFUlJPYClxfU",
												"AIzaSyAn7eYve_hnkW1EoyXsTdqdsv-0TWpZCVY",
												"AIzaSyBUWm1iuC0RfGku4iXnPMb9tyR3Wb8A3G8",
												"AIzaSyC5lM8f_soiIiMV-MPedqv6nrH-iJ4Keww",
												"AIzaSyBszn5RtUoiUIjd-RvrIi8V0qEhaFgXaCw",
												"AIzaSyC_VAiGMwj7DnxvumvNkX0ovm_MJwdexkg",
												"AIzaSyDK7I9I6oyMQ45D1bUKFSfZpy3imtp60hI"};
	
	/*
	public static String[] keys = new String[]{ "AIzaSyBzBGyQl_rpuGS0-WzYFuF5X-peF86WwvU" ,
												"AIzaSyAHhHALVhAjaD73WE-BIN8ZTfGjwmVqvo4" ,
												"AIzaSyBBs_h8HiPN-I1PiT4RpQQjkEzDfF7whYU",
												"AIzaSyBCS5alKonlivmyJQQiDWd7wqBIhjBGeuc",
												"AIzaSyDgsH_UT_e_GApok8YA_W9F31ZWZYTDR7k",
												"AIzaSyAI9PdDMnqS9rVX84sneonZmM1BX7p3krc"
												};
*/
	public static int keyIndex = 0;
	
	private static String getDistance(String data) throws Exception{
		
		while (true){
			try {
				String url = "https://maps.googleapis.com/maps/api/distancematrix/json?units=metric&"+data+"&key=" + keys[keyIndex];
				//System.out.println(url);
				Integer dist = Unirest.get(url)
							    		.header("accept",  "application/json")
							    		.asJson()
							    		.getBody()
							    		.getObject()
							    		.getJSONArray("rows")
							    		.getJSONObject(0)
							    		.getJSONArray("elements")
							    		.getJSONObject(0)
							    		.getJSONObject("distance")
							    		.getInt("value");
				
				Double distKm = Double.parseDouble(dist.toString())/1000;
			    return String.format("%.2f", distKm);
	
			} catch (Exception e) {
				if (keyIndex < keys.length -1 ){
					keyIndex++;
					System.out.println("CAMBIA DE KEY -> " + keyIndex);
				}
				else return "-1";
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		leerDeArchivo();
	}
	
	public static void leerDeArchivo() throws Exception {
		
		List<String> lines = null;
		String[] parsed = null;
	    try {
	      lines = Files.readAllLines(Paths.get(archivoEntrada), StandardCharsets.UTF_8 );
	      parsed = new String[lines.size()]; 
	      int n = 0;
	      for (String line : lines) {
	    	  parsed[n] = line.replace(" ", "");
	    	  n++;
	      }
	    } catch (IOException e) {
	      System.out.println(e);
	    }

	    System.out.println("\n");
	    boolean error = false;
	    String[][] distanceMatrix = new String[lines.size()][lines.size()];
	    
	    PrintWriter writer = new PrintWriter(archivoSalida, "UTF-8");
	    
	    for (int fila=filaInicio ; fila < lines.size() ; fila++ ){
	    	System.out.print(fila + ", ");
	    	if (fila%20==0)
	    		System.out.println("");
	    	for (int columna=0 ; columna < lines.size() ; columna++ ){
	    		
	    		if (fila==columna){
	    			distanceMatrix[fila][columna] = "0";
	    		}else{
	    			String distance = getDistance("origins="+parsed[fila]+"&destinations="+parsed[columna]);
	    			if (!error && distance.equals("-1")){
	    				error = true;
	    				System.out.println("Error en fila: " + fila + " columna: " + columna);
	    			}
	    			distanceMatrix[fila][columna] = distance.toString();
	    		}
	    	}
	    	
	    	String line = "";
			for (int columna=0 ; columna<lines.size() ; columna++ )
				line += distanceMatrix[fila][columna] + " ";
			writer.println(line);
	    	
			if (error) break;
	    }
		
	    writer.close();
	}
	
}