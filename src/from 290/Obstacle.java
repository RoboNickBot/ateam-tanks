/**
 * Imaoye Ekpelu
 * CSCI 290
 * Final Project
 * contains similar functions to floor but instead apply to the Obstacles
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class Obstacle implements Icon {
	private int size = 20;
	
	private int obstacle_height=4;
	

	public Obstacle() {
		// private int size = 20;
	}

	@Override
	public int getIconHeight() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public int getIconWidth() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		Graphics2D w2 = (Graphics2D) g;

		Rectangle2D.Double Obstaclesquare = new Rectangle2D.Double(x, y, size, size);
		w2.setColor(Color.RED);
		w2.fillRect(x, y, size, size);
		w2.draw(Obstaclesquare);

	}

}
