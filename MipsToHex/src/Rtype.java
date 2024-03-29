
public class Rtype extends Instruction{
	private String rs;				// instruction[24-20]
	private String rt;				// instruction[19-15]
	private String rd;				// instruction[14-10]
	private String shamt = "0"; // instruction[9-5] with default value
	private String functionCode;	// instruction[4-0]
	
	public String RtypeConverter(LookupTable lookupTable, String operand, String[] registers, int operandIndex) {
		if (registers.length == 3) {
			// if the 3. element of the register part is integer such as "sll $t0,$t1,5"
			if(Character.isDigit(registers[2].charAt(0))){
				this.rd = registers[0];
				this.rt = registers[1];
				this.rs = "$zero"; // for zero value
				this.shamt = registers[2];
			}
			else {
				this.rd = registers[0];
				this.rs = registers[1];
				this.rt = registers[2];
			}
			// find registers value
			int indexRs=-1;
			int indexRt=-1;
			int indexRd=-1;
			int found = 0;
			for(int i = 0; i<lookupTable.registersName.length;i++)
			{
				if(this.rs.equals(lookupTable.registersName[i])){
					found++;
					indexRs = i;
				}
					
				if(this.rt.equals(lookupTable.registersName[i])) {
					found++;
					indexRt = i;
				}
					
				if(this.rd.equals(lookupTable.registersName[i])){
					found++;
					indexRd = i;
				}
				
				if (found == 3) break;
			}
			// if any of the register is unknown
			if((indexRs == -1) || (indexRt == -1 ) || (indexRd == -1 ))				
				return "Unknown register";
			else {
				// set registers, opcode and function values
				this.setRt(lookupTable.registersValue[indexRt]);
				this.setRs(lookupTable.registersValue[indexRs]);
				this.setRd(lookupTable.registersValue[indexRd]);
				// convert values to binary numbers
				// convert shamt
				String binaryValue;
				int integerValue = Integer.parseInt(this.shamt);
				binaryValue = Integer.toBinaryString(integerValue);
				if (binaryValue.length() < 5){
					int max = 5-binaryValue.length();
					for(int i=0; i < max; i++)
						binaryValue = '0' + binaryValue;
				}
				this.setShamt(binaryValue);
				// convert rs
				integerValue = Integer.parseInt(this.rs);
				binaryValue = Integer.toBinaryString(integerValue);
				if (binaryValue.length() < 5){
					int max = 5-binaryValue.length();
					for(int i=0; i < max; i++)
						binaryValue = '0' + binaryValue;
				}
				this.setRs(binaryValue);
				// convert rt
				integerValue = Integer.parseInt(this.rt);
				binaryValue = Integer.toBinaryString(integerValue);
				if (binaryValue.length() < 5){
					int max = 5-binaryValue.length();
					for(int i=0; i < max; i++)
						binaryValue = '0' + binaryValue;
				}
				this.setRt(binaryValue);
				// convert rd
				integerValue = Integer.parseInt(this.rd);
				binaryValue = Integer.toBinaryString(integerValue);
				if (binaryValue.length() < 5){
					int max = 5-binaryValue.length();
					for(int i=0; i < max; i++)
						binaryValue = '0' + binaryValue;
				}
				this.setRd(binaryValue);
				// opcode and function code get binary numbers from the lookup table
				this.setOpcode(lookupTable.opcodes.get(operandIndex));
				this.setFunctionCode(lookupTable.functions.get(operandIndex));
			}
			// registers.length == 3
			this.setmMachinen(convertHex(this.getOpcode() + this.getRs() + this.getRt() + this.getRd() + this.getShamt() + this.getFunctionCode()));
		}
		// such as move instruction which is pseudo
		else if (registers.length == 2) {
			// also we need to add all the instruction which is same format by using 'else if'
			if (operand.equals("move")) {
				this.rd = registers[0];
				this.rt = "$zero";
				this.rs = registers[1];
				// find registers value
				int indexRs=-1;
				int indexRt=-1;
				int indexRd=-1;
				int found = 0;
				for(int i = 0; i<lookupTable.registersName.length;i++)
				{
					if(this.rs.equals(lookupTable.registersName[i])){
						found++;
						indexRs = i;
					}
						
					if(this.rt.equals(lookupTable.registersName[i])) {
						found++;
						indexRt = i;
					}
						
					if(this.rd.equals(lookupTable.registersName[i])){
						found++;
						indexRd = i;
					}
					
					if (found == 3) break;
				}
				// if any of the register is unknown
				if((indexRs == -1) || (indexRt == -1 ) || (indexRd == -1 ))				
					return "Unknown register";
				else {
					// set registers, opcode and function values
					this.setRt(lookupTable.registersValue[indexRt]);
					this.setRs(lookupTable.registersValue[indexRs]);
					this.setRd(lookupTable.registersValue[indexRd]);
					// convert values to binary numbers
					// convert shamt
					String binaryValue;
					int integerValue = Integer.parseInt(this.shamt);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setShamt(binaryValue);
					// convert rs
					integerValue = Integer.parseInt(this.rs);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setRs(binaryValue);
					// convert rt
					integerValue = Integer.parseInt(this.rt);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setRt(binaryValue);
					// convert rd
					integerValue = Integer.parseInt(this.rd);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setRd(binaryValue);
					// opcode and function code get binary numbers from the lookup table
					this.setOpcode(lookupTable.opcodes.get(operandIndex));
					this.setFunctionCode(lookupTable.functions.get(operandIndex));
				}	
			}
			else
				return "Reconsider instruction";
			
			// registers.length == 2
			this.setmMachinen(convertHex(this.getOpcode() + this.getRs() + this.getRt() + this.getRd() + this.getShamt() + this.getFunctionCode()));
		}
		// such as jr instruction
		else if (registers.length == 1){
			// also we need to add all the instruction which is same format by using 'else if'
			if (operand.equals("jr")) {
				// find registers value
				this.rs = registers[0];
				int indexRs=-1;
				for(int i = 0; i<lookupTable.registersName.length;i++)
				{
					if(this.rs.equals(lookupTable.registersName[i])) {
						indexRs = i;
						break;
					}
				}
				// if there is no rs register in the lookup table
				if((indexRs == -1))
					return "Unknown register";
				else {
					this.setRs(lookupTable.registersValue[indexRs]);
					this.setRt(lookupTable.registersValue[0]);
					this.setRd(lookupTable.registersValue[0]);
					// convert values to binary numbers
					// convert shamt
					String binaryValue;
					int integerValue = Integer.parseInt(this.shamt);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setShamt(binaryValue);
					// convert rs
					integerValue = Integer.parseInt(this.rs);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setRs(binaryValue);
					// convert rt
					integerValue = Integer.parseInt(this.rt);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setRt(binaryValue);
					// convert rd
					integerValue = Integer.parseInt(this.rd);
					binaryValue = Integer.toBinaryString(integerValue);
					if (binaryValue.length() < 5){
						int max = 5-binaryValue.length();
						for(int i=0; i < max; i++)
							binaryValue = '0' + binaryValue;
					}
					this.setRd(binaryValue);
					// opcode and function code get binary numbers from the lookup table
					this.setOpcode(lookupTable.opcodes.get(operandIndex));
					this.setFunctionCode(lookupTable.functions.get(operandIndex));
				}
			}
			else
				return "Reconsider instruction";
			
			// registers.length == 1
			this.setmMachinen(convertHex(this.getOpcode() + this.getRs() + this.getRt() + this.getRd() + this.getShamt() + this.getFunctionCode()));
		}
		else
			return "Reconsider instruction";
		
		return this.getMachine();
	}

	// set and get functions
	public String getRs() {
		return rs;
	}
	
	public void setRs(String rs) {
		this.rs = rs;
	}
	
	public String getRt() {
		return rt;
	}
	
	public void setRt(String rt) {
		this.rt = rt;
	}
	
	public String getRd() {
		return rd;
	}
	
	public void setRd(String rd) {
		this.rd = rd;
	}
	
	public String getShamt() {
		return shamt;
	}
	
	public void setShamt(String shamt) {
		this.shamt = shamt;
	}
	
	public String getFunctionCode() {
		return functionCode;
	}
	
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	
}
