
public class Itype extends Instruction{
	private String rs;				// instruction[24-20]
	private String rt;				// instruction[19-15]
	private String offset;			// instruction[14-0]

	public String ItypeConverter(LookupTable lookupTable, String operand, String[] registers, int operandIndex) {
		if(registers.length==3) {
			// if the operand is a branch type that means its first character is 'b'
			if(operand.charAt(0) == 'b'){
				this.rs = registers[0];
				this.rt = registers[1];
				this.offset= registers[2];
			}
			else{
				this.rt = registers[0];
				this.rs = registers[1];
				this.offset= registers[2];
			}
		}
		
		//For lw&sw type instructions
		else if(registers.length==2){
			// take first part for rt
			this.rt = registers[0];
			// Separate second part. before '(' for immediate and between two paranteces for rs
			String[] rest = registers[1].split("[(]");
			this.offset = rest[0];
			this.rs = rest[1].substring(0, rest[1].length() - 1);
		}
		
		// find registers value
		int indexRt=-1;
		int indexRs=-1;
		int found = 0;
		for(int i = 0; i<lookupTable.registersName.length;i++)
		{
			if(this.rs.equals(lookupTable.registersName[i])){
				found++;
				indexRs = i;
			}
			if(this.rt.equals(lookupTable.registersName[i])){
				found++;
				indexRt = i;
			}
			if (found == 2) break;	
		}
		// if any of the register is unknown
		if((indexRt == -1) || (indexRs == -1 ) )
			return "Unknown register";
		// find immediate value
		boolean sign;
		//checking if immediate is negative or positive
		if(this.offset.charAt(0) == '-'){
			this.offset = this.offset.substring(1);
			sign = false;
		}
		else
			sign = true;
		
		try {
			int intImmediate=Integer.parseInt(this.offset);
			this.offset = Integer.toBinaryString(intImmediate);	
    	
		}catch (Exception e) {
				return "Error: Immediate number";
		}
		// check Positive immediate
		if((this.offset.length()>16) && (sign))
			return "Posivite immediates cannot be bigger than 16 bits";			
		// check Negative immediate
		else if((this.offset.length()>15) && (!sign))
			return "Negative immediates cannot be bigger than 15 bits";
		// if immediate number is valid
		else {
			char[] immAr = new char[16];
			
			int j=1;
			for(int i = 15; i > -1 ; i--)
			{
				int len = this.offset.length()-j;
				//Fulfilling positive immediate to 16bits
				if(sign){
					if(len > -1) {
						immAr[i] = this.offset.charAt(len);
					}
					else
						immAr[i]='0';
					j++;
				}
				//Fulfilling negative immediate to 16bits
				else{
					if(len > -1) {
							immAr[i] = this.offset.charAt(len);
					}
					else{
						if (i == 0)
							immAr[i]='1';
						else
							immAr[i]='0';
					}
					j++;
				}
			}
			// 2's complement
			if(!sign) {
				boolean meetone = false;
				for(int i=immAr.length-1; i > 0; i--) {
					if(immAr[i] == '1'){
						if(meetone == false)
							meetone =true;
						else
							immAr[i] = '0';
					}
					else {
						if(meetone == true)
							immAr[i] = '1';
					}
						
				}
			}
			// set rs, rt, offset and opcode vales
			this.offset = new String(immAr); // binary
			this.setRt(lookupTable.registersValue[indexRt]);
			this.setRs(lookupTable.registersValue[indexRs]);
			// convert rs
			int integerValue = Integer.parseInt(this.rs);
			String binaryValue = Integer.toBinaryString(integerValue);
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
			this.setOpcode(lookupTable.opcodes.get(operandIndex));
			
			this.setmMachinen(convertHex(this.getOpcode() + this.getRs() + this.getRt() + this.getOffset()));
		}
		
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
	
	public String getOffset() {
		return offset;
	}
	
	public void setOffset(String offset) {
		this.offset = offset;
	}

	

}
