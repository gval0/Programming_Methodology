/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program 
					implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the 
	 * interactors in the application, and taking care of any other 
	 * initialization that needs to be performed.
	 */
	public void init() {
		initialize();
		addActionListeners();
    }
    
	// adds all the buttons, text fields and the canvas
  private void initialize() {
	  JLabel label  = new JLabel("Name");
		add(label, NORTH);
		
		NAME_FIELD = new JTextField(TEXT_FIELD_SIZE);
		add(NAME_FIELD,NORTH);
		
		ADD_BUTTON = new JButton("Add");
		add(ADD_BUTTON,NORTH);
		DELETE_BUTTON = new JButton("Delete");
		add(DELETE_BUTTON,NORTH);
		LOOKUP_BUTTON = new JButton("Lookup");
		add(LOOKUP_BUTTON,NORTH);
		
		STATUS_FIELD = new JTextField(TEXT_FIELD_SIZE);
		add(STATUS_FIELD,WEST);
		STATUS_FIELD.addActionListener(this);
		STATUS_BUTTON = new JButton("Change Status");
		add(STATUS_BUTTON,WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		
		PICTURE_FIELD = new JTextField(TEXT_FIELD_SIZE);
		add(PICTURE_FIELD,WEST);
		PICTURE_FIELD.addActionListener(this);
		PICTURE_BUTTON = new JButton("Change Picture");
		add(PICTURE_BUTTON,WEST);
		
		add(new JLabel(EMPTY_LABEL_TEXT),WEST);
		
		ADD_FRIEND__FIELD = new JTextField(TEXT_FIELD_SIZE);
		add(ADD_FRIEND__FIELD,WEST);
		ADD_FRIEND__FIELD.addActionListener(this);
		ADD_FRIEND_BUTTON = new JButton("Add Friend");
		add(ADD_FRIEND_BUTTON,WEST);
		
		canvas  = new FacePamphletCanvas();
		add(canvas);
  }
    /**
     * This class is responsible for detecting when the buttons are
     * clicked or interactors are used, so you will have to add code
     * to respond to these actions.
     */
    public void actionPerformed(ActionEvent e) {
    	
    	// adds profile and displays it
		if (e.getSource() == ADD_BUTTON &&  !NAME_FIELD.getText().equals("")) {
			canvas.removeAll();
			if (!data.containsProfile(NAME_FIELD.getText())) {
				profile = new FacePamphletProfile(NAME_FIELD.getText());
				data.addProfile(profile);
				canvas.showMessage("New profile created");
			} else {
				profile = data.getProfile(NAME_FIELD.getText());
				canvas.showMessage("A profile with the name " + profile.getName() + " already exists");
			}
			
			canvas.displayProfile(profile);
		}
		
		// deletes profile if there is one
		if (e.getSource() == DELETE_BUTTON &&  !NAME_FIELD.getText().equals("")) {
			canvas.removeAll();
			if (data.containsProfile(NAME_FIELD.getText())) {
				data.deleteProfile(NAME_FIELD.getText());
				canvas.showMessage("Profile of " + NAME_FIELD.getText() +" deleted");
				profile = null;
			} else {
				canvas.showMessage("A profile with the name "+  NAME_FIELD.getText() +" does not exist");
			}
		}
		
		// looks up profile if there is one
		if (e.getSource() == LOOKUP_BUTTON &&  !NAME_FIELD.getText().equals("")) {
			if (data.containsProfile(NAME_FIELD.getText())) {
				profile = data.getProfile(NAME_FIELD.getText());
				canvas.removeAll();
				canvas.showMessage("Displaying " + profile.getName() ); 
				canvas.displayProfile(profile);
			} else {
				canvas.removeAll();
				canvas.showMessage("A profile with the name "+  NAME_FIELD.getText() +" does not exist");
				profile = null;
			}
		}
		
		//changes status and displays updated profile
		if ((e.getSource() == STATUS_BUTTON || e.getSource() == STATUS_FIELD )&& !STATUS_FIELD.getText().equals("")) {
			if(profile != null) {
			profile.setStatus(STATUS_FIELD.getText());
			canvas.removeAll();
			canvas.showMessage("Status updated to " + STATUS_FIELD.getText());
			canvas.displayProfile(profile);
			} else {
				canvas.showMessage("Please select a profile to change status");
			}
		}
		
		// changes profile picture and displays updated profile
		if ((e.getSource() == PICTURE_BUTTON || e.getSource() == PICTURE_FIELD) && !PICTURE_FIELD.getText().equals("")) {
			if(profile != null) {
			GImage image = null; 
			try { 
			image = new GImage(PICTURE_FIELD.getText()); 
			profile.setImage(image);
			canvas.removeAll();
			canvas.showMessage("Picture update");
			
			} catch (ErrorException ex) { 
				canvas.showMessage("Unable to open image file: " + PICTURE_FIELD.getText());  
			}
			} else {
				canvas.showMessage("Please select a profile to change picture");
			}
			canvas.displayProfile(profile);
		}
		
		// adds friends to profile and displays updated profile
		if ((e.getSource() == ADD_FRIEND_BUTTON || e.getSource() == ADD_FRIEND__FIELD) &&  !ADD_FRIEND__FIELD.getText().equals("")) {
			if(!profile.getName().equals(ADD_FRIEND__FIELD.getText())) {
			canvas.removeAll();
			if(profile != null) {
				if (data.containsProfile(ADD_FRIEND__FIELD.getText())) {
					if (profile.addFriend(ADD_FRIEND__FIELD.getText())) {
					FacePamphletProfile friend = data.getProfile(ADD_FRIEND__FIELD.getText());
					friend.addFriend(profile.getName());
					canvas.showMessage(ADD_FRIEND__FIELD.getText() + " added as a friend");
					} else {
						canvas.showMessage(profile.getName() + "  already has " + ADD_FRIEND__FIELD.getText() + " as a friend");
					}
				} else {
					canvas.showMessage( ADD_FRIEND__FIELD.getText() + " does not exist");
				}
					
			}else {
				canvas.showMessage("Please select a profile to add friend");
			}
			canvas.displayProfile(profile);
		} else canvas.showMessage("You can not add yourself as a friend");
			}
	}
    
    // private instance variables
    private FacePamphletDatabase data = new FacePamphletDatabase();
    private FacePamphletProfile profile;
    private FacePamphletCanvas canvas;
    
    private JButton ADD_BUTTON;
    private JButton DELETE_BUTTON;
    private JButton LOOKUP_BUTTON;
    private JButton STATUS_BUTTON;
    private JButton PICTURE_BUTTON;
    private JButton ADD_FRIEND_BUTTON;
    
    private JTextField NAME_FIELD;
    private JTextField STATUS_FIELD;
    private JTextField PICTURE_FIELD;
    private JTextField ADD_FRIEND__FIELD;
}
