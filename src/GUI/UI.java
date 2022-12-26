package GUI;
import javax.swing.*;

import model.BackEnd;

import java.awt.*;
import java.awt.event.*;

public class UI extends JFrame implements ItemListener, ActionListener{

	ButtonGroup eventGroup = new ButtonGroup();
	JRadioButton subTable0;
    JRadioButton subTable1;
    JRadioButton subTable2;
    JTextArea queryOutput;
	JLabel nameL;
	JLabel dateL;
	JLabel surnameL;
    JTextField nameT;
    JTextField dateT;
    JTextField surnameT;
    JButton insert;
    String eventSelected;
    BackEnd app;
    
	public UI() {
		initialize();
	}

	private void initialize() {
		app = new BackEnd();
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		subTable0 = new JRadioButton("SubTable0");
		eventGroup.add(subTable0);
		subTable1 = new JRadioButton("SubTable1");
		eventGroup.add(subTable1);
		subTable2 = new JRadioButton("SubTable2");
		eventGroup.add(subTable2);        
		subTable0.addItemListener(this);
		subTable1.addItemListener(this);
		subTable2.addItemListener(this);
		JPanel eventPanel = new JPanel(new GridLayout(0, 1));
		eventPanel.add(subTable0);
		eventPanel.add(subTable1);
		eventPanel.add(subTable2);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
        this.add(eventPanel, c);

        JLabel emptyLabel1 = new JLabel("                    \n          ");
        JPanel emptyPanel1 = new JPanel(new GridLayout(0, 1));
        emptyPanel1.add(emptyLabel1);
        c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
        this.add(emptyPanel1, c);

        
		JPanel datePanel = new JPanel(new GridLayout(0, 1));
		
        nameL = new JLabel("       Name  ");
        nameL.setEnabled(true);
        dateL = new JLabel("       Date  ");
        dateL.setEnabled(true);
        surnameL = new JLabel("       Surname  ");
        surnameL.setEnabled(true);
		nameT = new JTextField();
        nameT.setEnabled(true);
        dateT = new JTextField();
        dateT.setEnabled(true);
		surnameT = new JTextField();
        surnameT.setEnabled(true);
        JPanel dataPanel = new JPanel(new GridLayout(0, 2));
		dataPanel.add(nameL );
		dataPanel.add(nameT);
		dataPanel.add(dateL );
		dataPanel.add(dateT );
		dataPanel.add(surnameL );
		dataPanel.add(surnameT );
		datePanel.add(dataPanel);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
        this.add(datePanel, c);
        
        JLabel emptyLabel2 = new JLabel("                    \n          ");
        JPanel emptyPanel2 = new JPanel(new GridLayout(0, 1));
        emptyPanel2.add(emptyLabel2);
        c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 1;
        this.add(emptyPanel2, c);

        insert = new JButton("Insert");
        insert.addActionListener(this);
		JPanel submitPanel = new JPanel(new GridLayout(0, 1));
		submitPanel.add(insert);
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 2;
		c.gridy = 2;
        this.add(submitPanel, c);

        JLabel emptyLabel3 = new JLabel("                    \n          ");
        JPanel emptyPanel3 = new JPanel(new GridLayout(0, 1));
        emptyPanel3.add(emptyLabel3);
        c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 3;
        this.add(emptyPanel3, c);

        queryOutput = new JTextArea(18,50);
        queryOutput.setText("There is a MainTable that the subTables all reference"
        		+ "\nSubTable0 requires input params Name and Date"
        		+ "\nSubTable1 requires input params Name and Surname"
        		+ "\nSubTable2 requires input params Name"
        		+ "\n\nOUTPUT will appear here when insert is hit!"
        		+ "\n\n\t ENJOY!! :)");
		JScrollPane outputPanel = new JScrollPane(queryOutput);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 4;
        this.add(outputPanel, c);
        

		this.setBounds(50, 50, 600, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void itemStateChanged(ItemEvent e) {

	    Object source = e.getItemSelectable();

	    if (source == subTable0) {
	    	System.out.println("1");
	    	this.eventSelected="SubTable0";
	    } else if (source == subTable1) {
	    	System.out.println("2");
	    	this.eventSelected="SubTable1";
	    } else if (source == subTable2) {
	    	System.out.println("3");
	    	this.eventSelected="SubTable2";
	    }	    
	}
	
    public void actionPerformed(ActionEvent e) {
    	this.app.insert(this.eventSelected, this.nameT.getText(), this.dateT.getText(), this.surnameT.getText());
    	String result = this.app.Query(this.eventSelected);
    	if (this.app.getEventAdded()) {
    		String additionalInfo = this.app.Query("MainTable");
    		result += "\n\n" + additionalInfo;
    	}
    	queryOutput.setText(result);
    }
}
