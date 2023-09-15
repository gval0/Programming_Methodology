/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	// private instance variables
	private GLabel label;
	private FacePamphletProfile pf;
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		label = new GLabel("");
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		label.setLabel(msg);
		label.setFont(MESSAGE_FONT);
		double labelX = getWidth()/2 - label.getWidth()/2;
		double labelY = getHeight() - BOTTOM_MESSAGE_MARGIN;
		add(label,labelX,labelY);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		pf = profile;
		addName();
		addPicture();
		addStatus();
		addFriends();
	}

	// private instance variable 
	private double marginY;
	
	// displays name of the profile
	private void addName() {
		GLabel nameLabel = new GLabel(pf.getName());
		nameLabel.setFont(PROFILE_NAME_FONT);
		nameLabel.setColor(Color.blue);
		marginY = TOP_MARGIN + nameLabel.getAscent();
		add(nameLabel,LEFT_MARGIN,marginY);
	}
	
	// displays picture or picture space of the profile
	private void addPicture() {
		marginY += IMAGE_MARGIN;
		if (pf.getImage() == null) {
			GRect rect = new GRect(IMAGE_WIDTH,IMAGE_HEIGHT);
			add(rect,LEFT_MARGIN, marginY);
			GLabel picLabel = new GLabel("No Image");
			picLabel.setFont(PROFILE_IMAGE_FONT);
			double picLabelX = LEFT_MARGIN + IMAGE_WIDTH/2 - picLabel.getWidth()/2;
			double picLabelY = marginY + IMAGE_HEIGHT/2 + picLabel.getAscent()/2;
			add(picLabel,picLabelX,picLabelY);
		} else {
			GImage pic = pf.getImage();
			pic.scale(IMAGE_WIDTH/pic.getWidth(),IMAGE_HEIGHT/pic.getHeight());
			add(pic,LEFT_MARGIN, marginY);
			
		}
	}
	
	// displays updated status of the profile
	private void addStatus() {
		GLabel statusLabel;
		if (pf.getStatus().equals(""))  statusLabel = new GLabel("No current status");
		else  statusLabel = new GLabel(pf.getName() + " is " + pf.getStatus());
		statusLabel.setFont(PROFILE_STATUS_FONT);
		marginY +=STATUS_MARGIN + IMAGE_HEIGHT ;
		add(statusLabel,LEFT_MARGIN,marginY);
	}
	
	// displays friends of the profile
	private void addFriends() {
		GLabel friendLabel = new GLabel("Friends");
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		marginY -=STATUS_MARGIN + IMAGE_HEIGHT; 
		add(friendLabel,getWidth()/2,marginY);
		
		double friendLabelY = marginY + friendLabel.getHeight() ;
		Iterator<String> iter = pf.getFriends();
		while (iter.hasNext()) {
			String name = iter.next();
			GLabel nameLabel = new GLabel(name);
			nameLabel.setFont(PROFILE_FRIEND_FONT);
			add(nameLabel,getWidth()/2,friendLabelY);
			friendLabelY += nameLabel.getHeight();
		}
	}
	
}
