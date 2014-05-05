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

public class AITest {
	public static void main (String args[]) {
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();
		test9();
		test10();
		test11();
		test12();
	}
	public static void test1() {
		
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
		ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
		
		SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -50, -6, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
		SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 6, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
		Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
		
		sprites.add ( o1 );
		
		sprites2.add ( o1 );


		sprites.add ( t1 );
		p1tanks.add ( t1 );

		sprites.add ( t2 );
		CompTanks.add ( t2 );

		ArrayList<Player> players = new ArrayList<Player>();
		players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
		
		players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

		Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
		System.out.println("size = " + sprites2.size());
		System.out.println ( "//  Game running now  //\n" );

		int result = game.run ();

		System.out.println ( "/// Game finished with code " + result + " ///\n" );
	}
	public static void test2() {
			
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
		ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
			
		SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -50, -6, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
		SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 6, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
		Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
			
		sprites.add ( o1 );
			
		sprites2.add ( o1 );


		sprites.add ( t1 );
		p1tanks.add ( t1 );

		sprites.add ( t2 );
		CompTanks.add ( t2 );
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add ( new HumanPlayer (ui, "Player", p1tanks, Color.blue ) );
			
		players.add ( new AIPlayer3 ( sprites2, CompTanks, p1tanks.get(0), Color.red ) );

		Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
		System.out.println("size = " + sprites2.size());
		System.out.println ( "//  Game running now  //\n" );

		int result = game.run ();

		System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test3() {
				
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
				
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -6, -50, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 6, 100, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
				
			sprites.add ( o1 );
				
			sprites2.add ( o1 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
				
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test4() {
						
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
						
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( 6, 50, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( -6, -100, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
						
			sprites.add ( o1 );
						
			sprites2.add ( o1 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
						
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test5() {
						
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
						
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -90, -90, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 100, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
						
			sprites.add ( o1 );
						
			sprites2.add ( o1 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
						
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test6() {
								
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
								
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( 100, 100, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( -100, -100, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
								
			sprites.add ( o1 );
							
			sprites2.add ( o1 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
								
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();
					
			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test7() {
								
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
								
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( 100, -100, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( -100, 100, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
								
			sprites.add ( o1 );
								
			sprites2.add ( o1 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
								
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test8() {
								
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
								
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -100, 100, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, -100, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
								
			sprites.add ( o1 );
								
			sprites2.add ( o1 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
								
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test9() {
				
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
				
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -100, -6, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 6, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 70, 20 ), new Direction ( 0 ), 20 );
							
			Obstacle o2 = new Obstacle ( sprites, new Vector3D ( 0, -70, 20 ), new Direction ( 0 ), 20 );
						
			Obstacle o3 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
							
			sprites.add ( o1 );
			sprites.add ( o2 );
			sprites.add ( o3 );
									
									
			sprites2.add ( o1 );
			sprites2.add ( o2 );
			sprites2.add ( o3 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
				
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test10() {
			
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
				
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -100, -6, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 30, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 50, 20 ), new Direction ( 0 ), 20 );
			
			Obstacle o2 = new Obstacle ( sprites, new Vector3D ( 0, -50, 20 ), new Direction ( 0 ), 20 );
			
			Obstacle o3 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
				
			sprites.add ( o1 );
			sprites.add ( o2 );
			sprites.add ( o3 );
						
						
			sprites2.add ( o1 );
			sprites2.add ( o2 );
			sprites2.add ( o3 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
				
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test11() {
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
							
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -100, -6, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 100, 30, 10 ), new Direction ( 360 ), 2.9, 5.5, Color.blue );
			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
							
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();
			
			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
		public static void test12() {
					
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
			ArrayList<Obstacle> sprites2 = new ArrayList<Obstacle>();
						
			SimpleTank t1 = new SimpleTank ( sprites, p1tanks, new Vector3D ( -100, -6, 10 ), new Direction ( 90), 2.5, 3.5, Color.red );
			SimpleTank t2 = new SimpleTank ( sprites, CompTanks, new Vector3D ( 60, 30, 10 ), new Direction ( 270 ), 2.9, 5.5, Color.blue );
			//Obstacle o1 = new Obstacle ( sprites, new Vector3D ( 0, 50, 20 ), new Direction ( 0 ), 20 );
					
			//Obstacle o2 = new Obstacle ( sprites, new Vector3D ( 0, -50, 20 ), new Direction ( 0 ), 20 );
					
			Obstacle o3 = new Obstacle ( sprites, new Vector3D ( 0, 0, 20 ), new Direction ( 0 ), 20 );
						
			//sprites.add ( o1 );
			//sprites.add ( o2 );
			sprites.add ( o3 );
								
								
			//sprites2.add ( o1 );
			//sprites2.add ( o2 );
			sprites2.add ( o3 );


			sprites.add ( t1 );
			p1tanks.add ( t1 );

			sprites.add ( t2 );
			CompTanks.add ( t2 );

			ArrayList<Player> players = new ArrayList<Player>();
			players.add ( new HumanPlayer (ui, "Player", CompTanks, Color.red ) );
						
			players.add ( new AIPlayer3 ( sprites2, p1tanks, CompTanks.get(0), Color.blue ) );

			Game game = new Game ( players, sprites, ui, framesPerTurn, turnLimit, mapsize );
			System.out.println("size = " + sprites2.size());
			System.out.println ( "//  Game running now  //\n" );

			int result = game.run ();

			System.out.println ( "/// Game finished with code " + result + " ///\n" );
		}
}