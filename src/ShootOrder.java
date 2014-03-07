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
public class ShootOrder extends Order
{
    private Direction direction;

    public ShootOrder ( double theta )
    {
        super ( 3 );
        // this type of bullet does not allow for adjusting the inclination -- it can only shoot parallel to the ground
        this . direction = new Direction ( theta, 0 );
    }

    public void execSpecific ( SimpleTank tank )
    {
        if ( this.frames == 2 )
        {
            System.out.println ( "Shooting" );
            tank . shoot ( this . direction );
        }
    }
    public ShootOrder clone() {
    	ShootOrder clone= new ShootOrder(this.direction.getTheta());
    	return clone;
    }
}
