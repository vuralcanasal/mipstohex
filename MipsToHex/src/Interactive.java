import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Interactive {
	
	Interactive(LookupTable lookupTable, String startPoint){
		
		// create the window items for interactive mode
		JFrame fr = new JFrame("Mips To Hex");
		
		JLabel title = new JLabel("Interactive Mode");
		
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
        title.setSize(290, 30); 
        title.setLocation(100, 30); 
        
		JLabel input = new JLabel("Enter your command");
		input.setFont(new Font("Arial", Font.PLAIN, 20)); 
		input.setSize(190, 20); 
		input.setLocation(100, 100); 
        
        JTextField tinput = new JTextField(); 
        tinput.setFont(new Font("Arial", Font.PLAIN, 15)); 
        tinput.setSize(190, 20); 
        tinput.setLocation(100, 150); 
		
        JButton convert = new JButton("Convert");
		JButton exit = new JButton("Exit");
        convert.setBounds(100,200,95,30);
		exit.setBounds(200,200,95,30);
		
		JLabel message = new JLabel();
        message.setFont(new Font("Arial", Font.PLAIN, 15)); 
        message.setSize(1000, 20);
        message.setLocation(100, 250); 
        //	created the window items
       	
		// add all items on the window
		fr.add(title);
        fr.add(input);
        fr.add(tinput); 
		fr.add(convert);
		fr.add(exit);
		fr.add(message);
        fr.setSize(400,500);
		fr.setLayout(null);
		fr.setVisible(true);
		// end GUI
		
		 // exit button function
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fr.dispose();
				}
		});
		
		// interactive mode function
		convert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// clear the message
				message.setText("");
				// if the input is empty
				if ((tinput.getText()).isBlank())
					message.setText("Type a proper command");
				
				else {
					// tokens[0] is operation and tokens[1] is the rest of the command
					String[] tokens = tinput.getText().trim().split("\\s",2); // remove first white-spaces and divide the instruction into 2 tokens.
					if(tokens.length==2) {
						tokens[1] = tokens[1].replaceAll("\\s+",""); // remove all white spaces from the rest of the command
						// find the operation from the lookup table
						int instructionIndex = -1;
						for(int i = 0; i<lookupTable.instructions.size();i++)
    					{
    						if(tokens[0].equals(lookupTable.instructions.get(i)))
    						{
    							instructionIndex = i;
    							break;
    						}
    					}
    					
						// if the operand is found in the lookup table
						if (instructionIndex != -1)
    					{
							String[] rest = tokens[1].split("[,]"); // take all words (no operand) one by one according to the commas
							// if the instruction type is 'R'
							if (lookupTable.types.get(instructionIndex).equals("R")) {
								String msg = new Rtype().RtypeConverter(lookupTable, tokens[0], rest, instructionIndex); // tokens[0] means operand
								message.setText(msg);
							}
							// if the instruction type is 'I'
							else if (lookupTable.types.get(instructionIndex).equals("I")) {
								String msg = new Itype().ItypeConverter(lookupTable, tokens[0], rest, instructionIndex); // tokens[0] means operand
								message.setText(msg);
							}
							// if the instruction type is 'J'
							else if (lookupTable.types.get(instructionIndex).equals("J")) {
								String msg = new Jtype().JtypeInteractionConverter(lookupTable, tokens[0], rest, instructionIndex, startPoint); // tokens[0] means operand
								message.setText(msg);
							}
							else {
								message.setText("The type is unknown");
							}
    					}
						// if the operand is not in the lookup table
						else
							message.setText("The operand is unknown");	
					
					}
					// if the user input has wrong syntax
					else
						message.setText("Syntax error");
				}
			}

		});// interactive mode function end
		
	}

}
