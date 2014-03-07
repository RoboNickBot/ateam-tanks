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
 * A main game class that holds all relevant
 * game data and the game interface and runs
 * the main game loop.
 *
 * The current idea is that this is spawned
 * from a higher up gui-centric MainMenu type
 * class, and this runs independantly until
 * the game ends and it returns control to
 * that menu
 */

import java.util.ArrayList;
import java.io.Console;

public class Game 
{
    private ArrayList<Player> players;
    private SpriteList sprites;
    private InterfaceWithGame display;  // dat alignment

    private int framesPerTurn;
    private int turnLimit;
    private int mapsize;
    private Console console;
	private ArrayList<Player> playerscopy;

    public Game ( ArrayList<Player> p, SpriteList s, InterfaceWithGame d, int frames, int turns, int mapsize )
    {
        players = p;
        sprites = s;
        sprites.update();
        display = d;

        framesPerTurn = frames;
        turnLimit = turns;

        this.mapsize = mapsize;

        console = System.console();
    }

    public int run ()
    {
        display.initializeDisplay ( sprites, mapsize );

        boolean running = true;

        for ( int t = 1; t <= turnLimit && running; t ++ )
        {
            GameTest.debug("starting turn");
            for ( Player player : players )
            {
                if ( player.stillAlive() )
                {
                    GameTest.debug("giving orders");
                    player.giveOrders ( framesPerTurn );
                    GameTest.debug("orders given");
                }
            }

            console . readLine ( "Press enter to run the turn: " );

            boolean unfinishedBusiness = false; // used to check if bullets are still flying
            for ( int f = 0; f < framesPerTurn || unfinishedBusiness; f ++ )
            {
                try{
                    Thread.sleep(60);
                //TODO
                unfinishedBusiness = false;
                for ( Sprite sprite : sprites . getSprites () )
                {
                    if ( sprite.update() == 1 )
                    {
                        unfinishedBusiness = true;
                    }
                }
                sprites.update();
                GameTest.debug("update! frame #" + f );
                
                }catch(Exception e){}
                display.updateDisplay ();
            }

            int alive = 0;
            String winner = "Nobody";
            for ( Player player : players )
            {
                if ( player.stillAlive() )
                {
                    alive ++;
                    winner = player.getName();
                }
            }
            if ( alive <= 1 )
            {
                display.announceWinner ( winner );
                running = false;
            }
        }

        display.cleanUpAndDestroyDisplay ();
        return 0;
    }
    
    public Game clone() {
      	Object[] copy=  players.toArray();
    		for(int i = 0; i> players.size(); i++) {
    			playerscopy.add(this.players.get(i));
        		}
    	Game clone = new Game(playerscopy,sprites.clone(), display,framesPerTurn,turnLimit, this.mapsize);
    	return clone;
    }
    
    
}
