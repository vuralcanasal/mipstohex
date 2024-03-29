import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MipsToHex {
	
	MipsToHex(String lookupTablePath, String startPoint, String inputFile, String outputFile) {
		//taking path of absolute directory
		String mainPath = System.getProperty("user.dir");
		
		lookupTablePath = mainPath + lookupTablePath;
			
		
		// create the window items for main menu
		JFrame fr = new JFrame("Mips To Hex");
		 
		JButton Interactive = new JButton("Interactive");
		JButton Batch = new JButton("Batch");
		JButton exit = new JButton("Exit");
		Interactive.setBounds(200,100,95,30);
		Batch.setBounds(200,150,95,30);
		exit.setBounds(200,200,95,30);
		
		JLabel message = new JLabel();
        message.setFont(new Font("Arial", Font.PLAIN, 15)); 
        message.setSize(1000, 20);
        message.setLocation(50, 50); 
		// created the window items
				
		// add all items on the window
		fr.add(Interactive);
		fr.add(Batch);
		fr.add(exit);
		fr.add(message);
		
		fr.setSize(500,500);
		fr.setLayout(null);
		fr.setVisible(true);
		// end GUI
		
		// read lookup table from .txt file and create it
		LookupTable lt = null;
		try {
			
			lt = new LookupTable(lookupTablePath);
				
		}catch (Exception e) {
			e.printStackTrace();
			message.setText("The lookup-table cannot be read");
		}
		
		LookupTable lookupTable = lt;
		
		// exit button function
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				}
		});
		
		// interactive mode
		Interactive.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Interactive(lookupTable, startPoint);
				message.setText("");
			}
		});
		
		// batch mode
		Batch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputSourcePath = mainPath + inputFile;
				String outputObjPath = mainPath + outputFile;
				String msg = new Batch().createObjFile(lookupTable,inputSourcePath,outputObjPath,startPoint);
				message.setText(msg);
			}
		});
		
	}

}
