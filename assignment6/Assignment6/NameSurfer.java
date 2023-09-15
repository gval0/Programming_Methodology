/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
	    JLabel name = new JLabel("Name");
	    add(name,SOUTH);
	    
	    nameField = new JTextField(15);
	    add(nameField,SOUTH);
	    nameField.addActionListener(this);
	    
	    JButton graphButton = new JButton("Graph");
	    add(graphButton,SOUTH);
	    
	    JButton clearButton = new JButton("Clear");
	    add(clearButton,SOUTH);
	    
	    
	    addActionListeners();
	    
	    graph = new NameSurferGraph();
	    add(graph);
	}
	
/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		
		NameSurferEntry entry = base.findEntry(nameField.getText());
		
		if (e.getActionCommand().equals("Graph")) {
			if (entry != null) graph.addEntry(entry);
		}
		if (e.getActionCommand().equals("Clear")) {
			graph.clear();
		}
		if (e.getActionCommand().equals(nameField.getText())) {
			if (entry != null) graph.addEntry(entry);
		}
		
		nameField.setText("");
	}
	
	// private instance variables
	private JTextField nameField;
	private NameSurferDataBase base = new NameSurferDataBase(NAMES_DATA_FILE); ;
	private NameSurferGraph graph;


}