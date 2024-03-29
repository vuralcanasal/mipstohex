import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LookupTable {
	
	String[] registersName = null;
	String[] registersValue = null;
	ArrayList<String> instructions = new ArrayList<String>() ;
	ArrayList<String> opcodes = new ArrayList<String>() ;
	ArrayList<String> types = new ArrayList<String>() ;
	ArrayList<String> functions = new ArrayList<String>() ;
	
	LookupTable(String lookupTablePath){

		// reading lookup table	start point
    	Scanner scanner = null;
		try {
			scanner = new Scanner(new File(lookupTablePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		}
				// read for registers
    	String I = scanner.nextLine();
    	I=I.replaceAll("\\s+","");					//removing spaces
    	String delims = "[,]+";			
    	registersName = I.split(delims.trim());		//Lookup table's first row has register's names. It supposed to separated with commas.
    	I = scanner.nextLine();
    	I=I.replaceAll("\\s+","");
    	registersValue = I.split(delims);		//Lookup table's second row has corresponding register's numbers. It supposed to separated with commas.
       			// read for instructions
    	while (scanner.hasNextLine()) {
    		
    		I = scanner.nextLine();
    		delims = "[ ]+";			//splitting instructions into tokens that we took from source file according to space.
    		String[] tokens = I.split(delims);
    		
    		instructions.add(tokens[0]);
    		types.add(tokens[1]);
    		opcodes.add(tokens[2]);
    		functions.add(tokens[3]);	
    	}
    	// reading lookup table end point
    	scanner.close();
	}

}
