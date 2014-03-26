/**
 * Copyright 2014 A-Team Games
 *
 * This file is part of ateam-tanks.
 *
 *    ateam-tanks is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    ateam-tanks is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with ateam-tanks.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * A new core test for the Game/Player structure,
 * as well as the dummy ui. This test is acting in
 * place of the main gui menu that will eventually
 * be written to launch the games.
 */

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.BorderLayout;

import java.io.Console;
import java.io.BufferedReader;
import java.io.FileReader;

public class CompTest {
	public static void main (String args[]) {
		start();
	}
	public static void start() {
		Console in = System.console();
		System.out.println();
		System.out.println();
		System.out.println( "    A-Team Games Presents: Some sort of tank game!" );
		System.out.println();
		System.out.println();
		System.out.println ( "Player 1, please enter your name:");
		String player1Name = in.readLine ( ">>> " );
		System.out.println();
		System.out.println ( "/// Game Demo 1 Starting ///\n" );
		
		int framesPerTurn = 100;
		int turnLimit = 20;
		int mapsize = 200;
		
		DemoPanel ui=new DemoPanel();
		JFrame frame=new JFrame("ateam-tanks");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setLayout(new BorderLayout());
		frame.add(ui,BorderLayout.CENTER);
		frame.setVisible(true);
		ArrayList<SimpleTank> p1tanks = new ArrayList<SimpleTank>();
		ArrayList<SimpleTank> CompTanks = new ArrayList<SimpleTank>();
		
		SpriteList sprites = new SpriteList();
		SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -100, -6, 10 ), new Direction ( 12), 2.5, 3.5, Color.red );
		SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 6, 10 ), new Direction ( 300 ), 2.9, 5.5, Color.blue );

		sprites.add ( t1 );
		p1tanks.add ( t1 );

		sprites.add ( t2 );
		CompTanks.add ( t2 );

		ArrayList<Player> players = new ArrayList<Player>();
		players.add ( new HumanPlayer ( ui, player1Name, p1tanks, Color.red ) );
		players.add ( new Comp ( new SpriteList(), CompTanks, p1tanks, Color.blue ) );

		Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );

		System.out.println ( "//  Game running now  //\n" );

		int result = game.run ();

		System.out.println ( "/// Game finished with code " + result + " ///\n" );
	}
}