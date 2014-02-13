/**
 * A player class for a human
 */

import java.awt.Color;
import java.util.ArrayList;

public class HumanPlayer extends Player
{
    private InterfaceWithGame display;

    public HumanPlayer ( InterfaceWithGame iwg, String playerName, ArrayList<SimpleTank> tanks , Color c)
    {
        super ( playerName, tanks , c);
        this.display = iwg;
    }

    public void giveOrders ( int frameLimit )
    {
        for ( SimpleTank tank : ownedTanks )
        {
            tank.giveOrders ( display.askForOrders ( frameLimit, tank ) );
        }
    }
}
