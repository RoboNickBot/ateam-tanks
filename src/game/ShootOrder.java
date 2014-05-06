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

package game;

/**
 * A very simple order for shooting SimpleBullets
 */
import java.awt.*;
import java.awt.geom.*;
public class ShootOrder extends Order
{
    private Direction direction;

    public ShootOrder ( double theta )
    {
        super ( 3 );
        // this type of bullet does not allow for adjusting the inclination -- it can only shoot parallel to the ground
        this . direction = new Direction ( theta, 0 );
    }
    
    //method to adjust for inclination in bullets -B 
    public void adjust(double x) {
    	this.direction.changephi(x);
    }

    public ShootOrder clone()
    {
        ShootOrder output = new ShootOrder(0);
        output.frames = this.frames;
        output.direction = new Direction(this.direction);
        return output;
    }

    public void walk ( UnitModel model, Graphics2D g )
    {
        //The model is not altered in any way
        //A line is painted radiating from the model in the
        // direction that the bullet was fired
        Vector3D startP = new Vector3D ( model.getPosition(), new Vector3D(12, this.direction) );
        Vector3D endP = new Vector3D ( startP, new Vector3D(20, this.direction) );
        g . setColor ( Color . red );
        g . draw ( new Line2D . Double ( startP.getX(), startP.getY(), endP.getX(), endP.getY() ) );
    }

    public void execSpecific ( SpriteList sprites, SimpleTank tank )
    {
        if ( this.frames == 2 )
        {
            System.out.println ("Shooting");
            tank.shoot(sprites, this.direction);
        }
    }
}
