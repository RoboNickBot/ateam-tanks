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
 * A very simple order for shooting SimpleBullets
 */
import java.awt.*;
public class WaitOrder extends Order
{
    public WaitOrder ( int frames )
    {
        super ( frames );
    }

    public WaitOrder clone()
    {
        return new WaitOrder(this.frames);
    }

    public void walk ( UnitModel model, Graphics2D g )
    {
        //there is no graphical representation necessary for a wait,
        //and the model is unchanged by a wait
    }

    public void execSpecific ( SpriteList sprites, SimpleTank tank )
    {
        // Whistles lazily
    }
}
