package distancias;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main2 {

	public static void main(String[] args) throws IOException {


	    PrintWriter writer;
		try {
			List<String> lines = Files.readAllLines(Paths.get("montevideom"), StandardCharsets.UTF_8 );
			   
			  writer = new PrintWriter("montevideoKM", "UTF-8");
			  int i = 0;
		      for (String line : lines) {
		    	  if ( line.startsWith("#") || line.startsWith("CANT"))
		    		  continue;
		    	  String outputLine = "";
		    	  String[] numbers = line.split(" ");
		    	  for (String number : numbers){
		    		  
		    		  Double km = Double.parseDouble(number)/1000;
		    		  outputLine += String.format("%.2f", km) + " ";
		    	  }

		    	  writer.println(new String(outputLine));
		      }
		      writer.close();
		      
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
