/**
 * Imaoye Ekpelu
 * CSCI 290
 * Final Project
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;


/**
 * Floor class implements Icon and makes the squares icon instances
 * @author Imaoye Ekpelu
 *
 */
public class Floor implements Icon {
	private int size = 20;
	private boolean player = false;
	private int posx=0, posy = 0;

	/**
	 * Constructor function that takes in i and j as inputs which represent the x and y coordinates
	 * @param i
	 * @param j
	 */
	public Floor(int i, int j ) {
		posx = i;
		posy =j;
	}
	
	/**
	 * returns the x position
	 * @return
	 */
	public int getposx(){
		return posx;
	}
	
	/**
	 * returns the y position
	 * @return
	 */
	public int getposy(){
		return posy;
	}
	
	/**
	 * Returns the player
	 * @return
	 */
	public boolean getPlayer(){
		return player;
	}
	
	/**
	 * Boolean to check whether or not the player is true or false
	 * @param p
	 */
	public void updatePlayer(boolean p){
		player = p;
		
	}
	@Override
	
	/**
	 * Returns the height of the icon
	 */
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return size;
	}
/**	
 * Returns the width of the icon
 */
	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return size;
	}
/**
 * Determins what color to paint a given icon
 */
	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		Graphics2D f2 = (Graphics2D) g;

		Rectangle2D.Double floorsquare = new Rectangle2D.Double(x, y, size,
				size);
		//f2.setColor(Color.WHITE);
		//f2.fill(floorsquare);
		// f2.draw(floorsquare);
		if (player == true){
			f2.setColor(Color.CYAN);
			//f2.fill(floorsquare);
		}
		else{
			f2.setColor(Color.WHITE);
			//f2.fill(floorsquare);
		}
		f2.fill(floorsquare);
	}

}
