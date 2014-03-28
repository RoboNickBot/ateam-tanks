/**
 * Imaoye Ekpelu
 * CSCI 290
 * Final Project
 * 
 */

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;

public class GameBoard extends JPanel {
	//private int playerx = 0;
	//private int playery = 1;
	
	private Floor me = null;
/**
 * The GameBoard class is designed to instantiate an array that represents the game board. 0's represents walls  and 1's represent floors 
 */
	private int[][] gamearray = new int[][] {
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1 },
			{ 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1 },
			{ 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1 },
			{ 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1 },
			{ 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1 },
			{ 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1 },
			{ 1, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1 },
			{ 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1 },
			{ 1, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1 },
			{ 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1 },
			{ 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1 },
			{ 1, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1 },
			{ 1, 1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1 },
			{ 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

	private ArrayList<Floor> floorarray = new ArrayList<Floor>();
/**
 * GameBoard() is the constructor that iterates through the array and assigns floor or wall squares depending on the whether the number is a 1 or a 0
 */
	public GameBoard() {
		// JLabel wall = new JLabel(new Wall());
		// JLabel floor = new JLabel(new Floor());
		setLayout(new GridLayout(21, 21));

		for (int j = 0; j < 21; j++) {
			for (int i = 0; i < 21; i++) {
				if (gamearray[i][j] == 0) {
					add(new JLabel(new Wall()));
				} else if (gamearray[i][j] == 1) {
					Floor f = new Floor(i, j);
					//me = new Floor(i,j);
					if (i == 1 && j ==0){
						me = f;
						me.updatePlayer(true);
					}
					else if (gamearray[i][j] == 2){
						add(new JLabel(new Obstacle()));
					add(new JLabel(f));
					floorarray.add(f);

				}
			}
		}
		}
	}
/**
 * updatePosition (string s) takes a string as an input and compares current location to desired move and if the move is valid the player square moves
 * @param play
 */
	public void updatePosition(String play) {
		int x = me.getposx(), y = me.getposy();
		if (play == "up") {
			for (Floor f: floorarray){
				if (f.getposy() == y-1 && f.getposx()==x){
					f.updatePlayer(true);
					me.updatePlayer(false);
					me = f;
					break;
				}
			}
		} else if (play == "down") {
			for (Floor f: floorarray){
				if (f.getposy() == y+1 && f.getposx()==x){
					f.updatePlayer(true);
					me.updatePlayer(false);
					me = f;
					break;
				}
			}
		} else if (play == "left") {
			for (Floor f: floorarray){
				if (f.getposy() == y && f.getposx()==x-1){
					f.updatePlayer(true);
					me.updatePlayer(false);
					me = f;
					//return;
				}
			}
		} else if (play == "right") {
			for (Floor f: floorarray){
				if (f.getposy() == y && f.getposx()==x+1){
					f.updatePlayer(true);
					me.updatePlayer(false);
					me = f;
					//return;
				}
			}
		}
		repaint();
	}
}
