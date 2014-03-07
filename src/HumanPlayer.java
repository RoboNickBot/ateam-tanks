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
            tank.giveOrders ( display.askForOrders ( this . playerName, frameLimit, tank) );
        }
    }
    // need to do something about the array of tanks
    public HumanPlayer clone() {
    	HumanPlayer clone = new HumanPlayer(this.display ,this.getName() ,this.ownedTanks ,this.getColor());
    	return null;
    }
}
