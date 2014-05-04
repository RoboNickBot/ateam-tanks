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

import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.*;
import java.awt.geom.Area;

public class SimpleBullet extends Projectile implements Serializable
{
    public SimpleBullet(Vector3D position, Direction direction)
    {
        super(position, direction, 2, Color.black, new Vector3D ( 8, direction ), new Vector3D ( 0, 0, -0.015 ) );
        //System.out.println ( "Bullet fired!" );
    }

    public SimpleBullet clone()
    {
        return new SimpleBullet(new Vector3D(this.position), new Direction(this.direction));
    }
    
    public void reactToCollision(SpriteList sprites, ArrayList<Sprite> collisions)
    {
        if (this.alive)
        {
            if (collisions.size() > 0)
            {
                // only happens if it hits another sprite, not the ground
                sprites.add(new SimpleBulletExplosion(this.position));
            }
            //for ( Sprite hitSprite : collisions )
            //{
            //    hitSprite . damage ( 5 ); // inflicts 5 damage on sprites (or tries to, anyway)
            //}
            //System.out.println ( "Boom!" );
            this.kill(sprites); // deletes self from the game
        }
    }
    public void paint(Graphics2D g){
        Area area = Hitbox.getArea(this.hitbox,this.position,this.direction.getTheta());
        g.setColor(Color.white);
        g . fill (area);
    }
    public void damage ( SpriteList sprites, int intensity )
    {
        if ( this . alive )
        {
            //System.out.println ( "Boom!" );
            this . kill (sprites); // deletes self from game upon any sort of damage
        }
    }
}
