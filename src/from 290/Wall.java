/**
 * Imaoye Ekpelu
 * CSCI 290
 * Final Project
 * contains similar functions to floor but instead apply to the walls
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.Icon;

public class Wall implements Icon {
	private int size = 20;
	

	public Wall() {
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

		Rectangle2D.Double wallsquare = new Rectangle2D.Double(x, y, size, size);
		w2.setColor(Color.BLACK);
		w2.fillRect(x, y, size, size);
		w2.draw(wallsquare);

	}

}
