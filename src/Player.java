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
    private String playerName;
    private Color color;

    /* This is a list to allow for multiple owned units
     * per player if we wish to later implement that
     */
    protected ArrayList<SimpleTank> ownedTanks;

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
}
