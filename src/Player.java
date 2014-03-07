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
 * This class represents a player, for the purposes
 * of sprite ownership and orders allocation.
 *
 * It can be instantiated itself as a dummy player
 * that gives no orders, for testing purposes
 */

import java.awt.Color;
import java.util.ArrayList;

public class Player
{
    protected String playerName;
    private Color color;

    /* This is a list to allow for multiple owned units
     * per player if we wish to later implement that
     */
    protected ArrayList<SimpleTank> ownedTanks;
	private ArrayList<SimpleTank> tankcopy;

    public Player ( String name, ArrayList<SimpleTank> tanks , Color c)
    {
        playerName = name;
        ownedTanks = tanks;
        color = c;
    }

    /**
     * The method called during the "give orders" phase of the
     * game.
     *
     * This method asks the player for the list of orders (for
     * a human player, most likely useing the gui interface)
     * for each of the player's units and then gives the orders
     * to those units.
     *
     * In this superclass, the units are given empty orderlists.
     *
     * frameLimit is the number of frames that can be allocated
     * among the orders
     */
    public void giveOrders ( int frameLimit )
    {
        for ( SimpleTank tank : ownedTanks )
        {
            tank.giveOrders ( new OrderQueue () );
        }
    }

    public boolean stillAlive ()
    {
        return ( ownedTanks.size() != 0 );
    }

    public String getName ()
    {
        return playerName;
    }
    public Color getColor ()
    {
        return color;
    }
    // worried ownedTanks will cause problems because it is an array
    public Player clone() {
    	Object[] copy=  ownedTanks.toArray();
		for(int i = 0; i> ownedTanks.size(); i++) {
			tankcopy.add(this.ownedTanks.get(i));
    		}
    	Player clone= new Player(playerName,tankcopy,color );
    	return clone;
    }
    
    
}
