
public class Jtype extends Instruction{
	
	private String target;		// instruction[24-0]

	public String JtypeInteractionConverter(LookupTable lookupTable, String operand, String[] target, int operandIndex, String startPoint) {
		if (target.length != 1)
			return "Error: Too many arguments";
		
		else {
			this.target = target[0];
			// find target value
			try {
				int intTarget=Integer.parseInt(this.target);
				if (intTarget > -1)
					this.target = Integer.toBinaryString(intTarget);
				else
					throw new Exception();
				
			}catch (Exception e) {
					return "Error: Target number";
			}
			// target number times 4
			int bits = Integer.parseInt(this.target, 2);
			this.target = Integer.toBinaryString(bits<<2);
			
			// calculate the final address
			this.target = addBinary(startPoint, this.target);
			// if the final address is 32 bits
			if(this.target.length() == 32) {
				char[] targ = new char[26];
				int addressStart = 4; // remove first four bits
				int addressEnd = 30; // remove last two bits
				int i = 0;
				for(; addressStart < addressEnd; addressStart++) {
					targ[i] = this.target.charAt(addressStart);
					i++;
				}
				// set opcode and target number
				this.target = new String(targ);
				this.setOpcode(lookupTable.opcodes.get(operandIndex));
				
				this.setmMachinen(convertHex(this.getOpcode() + this.getTarget()));
			}
			//	if the final address is not 32 bits
			else
				return "The address is too big";
		}
		return this.getMachine();
	}	
	
	String addBinary(String a, String b) {
        // Initialize result 
        String result = "";  
          
        // Initialize digit sum 
        int s = 0;          
  
        // Traverse both strings starting  
        // from last characters 
        int i = a.length() - 1, j = b.length() - 1; 
        while (i >= 0 || j >= 0 || s == 1) 
        { 
              
            // Compute sum of last  
            // digits and carry 
            s += ((i >= 0)? a.charAt(i) - '0': 0); 
            s += ((j >= 0)? b.charAt(j) - '0': 0); 
  
            // If current digit sum is  
            // 1 or 3, add 1 to result 
            result = (char)(s % 2 + '0') + result; 
  
            // Compute carry 
            s /= 2; 
  
            // Move to next digits 
            i--; j--; 
        } 
          
        return result; 
    }
	
	// set and get functions
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	
}
