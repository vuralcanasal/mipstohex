import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



public class Batch {
	ArrayList<String> labels = new ArrayList<String>();
	ArrayList<Integer> labelsLines = new ArrayList<Integer>();
	ArrayList<String> sourceCode = new ArrayList<String>();
	ArrayList<String> operand = new ArrayList<String>();
	ArrayList<String> restCommand = new ArrayList<String>();
	ArrayList<String> converted = new ArrayList<String>();
	
	public String createObjFile(LookupTable lookupTable, String inputSourcePath, String outputObjPath, String startPoint) {
		// if there is any error for converting, flag return true
		boolean Error = false;
		
		readSourceFile(inputSourcePath);
		if(operand.size() == 0)
			return "The source file cannot be opened";
		else {
			// create an obj file if it is not created
			createObjFile(outputObjPath);
			
			for(int i = 0; i < operand.size(); i ++) {
				
				// find the operand from the lookupTable
				int operandIndex = -1;
				for(int j = 0; j<lookupTable.instructions.size();j++)
				{
					if(operand.get(i).equals(lookupTable.instructions.get(j)))
					{
						operandIndex = j;
						break;
					}
				}
				
				// if the operand is found in the lookup table
				if (operandIndex != -1)
				{
					String[] rest = restCommand.get(i).split(",");
					// if the operand type is 'R'
					if (lookupTable.types.get(operandIndex).equals("R")) {
						converted.add(new Rtype().RtypeConverter(lookupTable, operand.get(i), rest, operandIndex)); // tokens[0] means operand
						
					}
					// if the operand type is 'I'
					else if (lookupTable.types.get(operandIndex).equals("I")) {
						// if operand is branch, calculate the label value
						if(operand.get(i).charAt(0) == 'b') {
							String branchValue;
							int branchindex = -1;
							for(int j = 0; j<labels.size();j++)
	    					{
		    					if(rest[2].equals(labels.get(j))){
		    						branchindex = j;
									break;
								}
	    					}
	    					
	    					if(branchindex > -1)
	    					{
	    						if ((labelsLines.get(branchindex) - i ) > 0)
	    							branchValue = Integer.toString((labelsLines.get(branchindex) - i - 1));
	    						else
	    							branchValue = Integer.toString((labelsLines.get(branchindex) - i));
	    					}
	    						
	    					else
	    						branchValue = "Error: Label Name";
								
	    					rest[2] = branchValue;
						}
						converted.add(new Itype().ItypeConverter(lookupTable, operand.get(i), rest, operandIndex)); // tokens[0] means operand
					}
					// if the operand type is 'J'
					else if (lookupTable.types.get(operandIndex).equals("J")) {
						// find label's address
						int labelindex = -1;
						for(int j = 0; j<labels.size();j++)
						{
	    					if(rest[0].equals(labels.get(j))){
	    						labelindex = j;
								break;
							}
						}
						
						if(labelindex > -1)
							rest[0] = Integer.toString(labelsLines.get(labelindex));
						else
							rest[0] = "-";
						
						converted.add(new Jtype().JtypeInteractionConverter(lookupTable, operand.get(i), rest, operandIndex, startPoint)); // tokens[0] means operand
					}
					else {
						converted.add("The type is unknown");
					}
				}
				// if the operand is not in the lookup table
				else
					converted.add("The operand is unknown");	
				
				// if there is any error for converted
				if(converted.get(i).charAt(0) != '0')
					Error = true;
				writeObjFile(outputObjPath);
			}

		}
				
		
		if(Error)
			return "The object is created with some ERROR";
		else
			return "The object is created successfully";
	}

	private void writeObjFile(String outputObjPath) {
		
		try {
			BufferedWriter myWriter = new BufferedWriter(new FileWriter(outputObjPath));
		      
		      for(int i = 0; i< converted.size(); i++) {
		    	  myWriter.write(converted.get(i));
		    	  myWriter.newLine();
		      }
		      myWriter.flush();
		      myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}

	private void createObjFile(String outputObjPath) {
		
		try {
		      File outputObj = new File(outputObjPath);
		      outputObj.createNewFile();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	private void readSourceFile(String inputSourcePath) {
		int indexLabel = -1;
		Scanner scanner = null;
		// read source file
		try {
			scanner = new Scanner(new File(inputSourcePath));
		
			//First read all the lines for detecting label names and corresponding index.
			while (scanner.hasNextLine()) {	
				indexLabel++;
				String line = scanner.nextLine().trim();
				boolean isLabel = false;
				int i = 1;
				for(; i <line.length(); i++) {
					if(line.charAt(line.length() - i) == ':') {
						isLabel = true;
						break;
					}
					else isLabel = false;
				}
				if(isLabel) {
					labels.add(line.substring(0,line.length()-i));
					sourceCode.add((line.substring(line.length()-i+1,line.length())));
					labelsLines.add(indexLabel);
				}
				else
					sourceCode.add(line);
				
			}
			scanner.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		
		// create operand and rest in the command
		for (int i = 0; i < sourceCode.size(); i++) {
			sourceCode.set(i, sourceCode.get(i).trim());
			String[] code = sourceCode.get(i).split("\\s",2);
			operand.add(code[0]);
			restCommand.add(code[1].replaceAll(" ", ""));
		} 
		
	}// end of the reading
		
	

}