/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		update();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		nameList.clear();
		update();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		nameList.add(entry);
		update();
	}
	
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		add(new GLine (0,GRAPH_MARGIN_SIZE,getWidth(),GRAPH_MARGIN_SIZE));
		add(new GLine (0,getHeight() - GRAPH_MARGIN_SIZE,getWidth(),getHeight() - GRAPH_MARGIN_SIZE));
		decadeWidth = getWidth()/NDECADES;
		for (int i=1; i<NDECADES; i++){
			add(new GLine (i*decadeWidth,0,i*decadeWidth,getHeight()));
			}
		int decade = 1900;
		for (int i=0; i <NDECADES; i++) {
			String str = "" + (decade + i*10);
			GLabel label = new GLabel(str);
			add(label,i*decadeWidth,getHeight());
		}
		addGraph();
		}
	
	
	private void addGraph() {
		double size = (double)(getHeight()-2*GRAPH_MARGIN_SIZE)/MAX_RANK;
		
		for (int i=0; i<nameList.size();i++) {
			Color color = getColor(i); // determine color of graph
			
			for (int j=0; j<NDECADES; j++) {
				
				GLabel label;
				int rank = nameList.get(i).getRank(j); // rank in the decade
				
				// if decade is not the last decade
				if (j != NDECADES-1) {
					
				int rankNextDecade = nameList.get(i).getRank(j+1);
				// if unraked should appear in the bottom
				if(rank == 0) {
					rank = 1000;
					label = new GLabel (nameList.get(i).getName() + "*");
				} else {
					label = new GLabel (nameList.get(i).getName() + " " + rank);
				}
				
				if (rankNextDecade == 0) rankNextDecade = 1000;
				
				GLine line = new GLine(decadeWidth * j, GRAPH_MARGIN_SIZE + (rank * size), 
									   decadeWidth * (j + 1),GRAPH_MARGIN_SIZE + rankNextDecade * size);
				
				label.setColor(color);
				line.setColor(color);
				
				add(line);	
				add(label,decadeWidth * j,GRAPH_MARGIN_SIZE + (rank * size));
				
			} else { // if decade is the last decade
				if(rank == 0) {
					rank = 1000;
					label = new GLabel (nameList.get(i).getName() + "*");
				} else {
					label = new GLabel (nameList.get(i).getName() + " " + rank);
				}
				
				label.setColor(color);
				add(label,decadeWidth * (NDECADES-1),GRAPH_MARGIN_SIZE + (rank * size));
			}	
			}
				
		}
	}
	
	// returns color of the graph
	private Color getColor (int num) {
		int rem = num % COLOR_N;
		switch(rem) {
		case 0: return Color.black;
		case 1: return Color.blue;
		case 2: return Color.red;
		default: return Color.yellow;
		}
		
	}
	
	// number of different colors of the graph
	private final static int COLOR_N = 4;
	
	// private instance variables
	private double decadeWidth;
	private ArrayList<NameSurferEntry> nameList = new ArrayList<NameSurferEntry>(); 
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
