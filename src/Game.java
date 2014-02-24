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

public class Game
{
    private ArrayList<Player> players;
    private SpriteList sprites;
    private InterfaceWithGame display;  // dat alignment

    private int framesPerTurn;
    private int turnLimit;
    private int mapsize;

    public Game ( ArrayList<Player> p, SpriteList s, InterfaceWithGame d, int frames, int turns, int mapsize )
    {
        players = p;
        sprites = s;
        sprites.update();
        display = d;

        framesPerTurn = frames;
        turnLimit = turns;

        this.mapsize = mapsize;
    }
    
    public int run ()
    {
        display.initializeDisplay ( sprites, mapsize );

        boolean running = true;

        for ( int t = 1; t <= turnLimit && running; t ++ )
        {
            for ( Player player : players )
            {
                if ( player.stillAlive() )
                {
                    player.giveOrders ( framesPerTurn );
                }
            }

            boolean unfinishedBusiness = false; // used to check if bullets are still flying
            for ( int f = 0; f < framesPerTurn || unfinishedBusiness; f ++ )
            {
                unfinishedBusiness = false;
                for ( Sprite sprite : sprites . getSprites () )
                {
                    if ( sprite.update() == 1 )
                    {
                        unfinishedBusiness = true;
                    }
                }
                sprites.update();
                
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
    
    public Game clone()
    {
        Game clone;
        try{
        	clone= (Game)super.clone();
        }
        catch (CloneNotSupportedException e) {
        	throw new Error();
        }
        return clone;
    }
    
    
}
