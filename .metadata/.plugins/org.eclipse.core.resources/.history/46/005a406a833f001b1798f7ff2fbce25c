
public abstract class Instruction {
	private String machine;			// machine code in hex
	private String opcode;				// instruction[31-25]
	
	// the function convertHex take the 32 bit binary number and convert it to hexadecimal number
	String convertHex(String binary) {
		int digitNumber = 1;
	    int sum = 0;
	    String hex="0x";
	    for(int i = 0; i < binary.length(); i++){
	        
	    	if(digitNumber == 1)
	            sum += Integer.parseInt(binary.charAt(i) + "")*8;
	        
	        else if(digitNumber == 2)
	            sum += Integer.parseInt(binary.charAt(i) + "")*4;
	        
	        else if(digitNumber == 3)									
	            sum += Integer.parseInt(binary.charAt(i) + "")*2;
	        
	        else if(digitNumber == 4 || i < binary.length()+1){
	            sum += Integer.parseInt(binary.charAt(i) + "")*1;
	            digitNumber = 0;
	            if(sum < 10)
	            	hex = hex + sum;
	            else if(sum == 10)
	            	hex = hex + "A";
	            else if(sum == 11)
	            	hex = hex + 'B';
	            else if(sum == 12)
	            	hex = hex + "C";
	            else if(sum == 13)
	            	hex = hex + "D";
	            else if(sum == 14)
	            	hex = hex + "E";
	            else if(sum == 15)
	            	hex = hex + "F";
	            sum = 0;
	        }
	    	
	        digitNumber ++;  
	    }
	    
		return hex;
	}
	
	// set and get functions
	public String getMachine() {
		return machine;
	}
	
	public void setmMachinen(String machine) {
		this.machine = machine;
	}
	
	public String getOpcode() {
		return opcode;
	}
	
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}
}
