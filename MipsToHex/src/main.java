
public class main {
	
	public static void main(String[] args) {
		
		final String startPoint="10000000000000000001000000000000";   // initial address 0x80001000 
		final String lookupTable="\\lookup_table.txt";	// lookup table's path
		final String input="\\input.src";	// .src file's path
		final String output="\\output.obj";		// .obj file's path
		
		new MipsToHex(lookupTable, startPoint, input, output);
		
	}
}
