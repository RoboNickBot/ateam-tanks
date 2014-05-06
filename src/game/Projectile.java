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
 * This is a superclass for all types of bullets and shells that tanks can shoot.
 *
 */

import java.util.ArrayList;
import java.awt.Color;
import java.io.*;

public abstract class Projectile extends Sprite implements Serializable
{
    protected Vector3D velocity;
    protected Vector3D gravity;

    public Projectile(Vector3D position, Direction direction, double hitboxRadius, Color color, Vector3D velocity, Vector3D gravity )
    {
        super(position, direction, hitboxRadius);
        this.velocity = velocity;
        this.gravity = gravity;
    }

    /**
     * A method for exploding or inflicting damage or splitting in two or whatever
     * this particular subclass of projectile is supposed to do upon hitting
     * another sprite. There is no default behavior.
     */
    public abstract void reactToCollision(SpriteList sprites, ArrayList<Sprite> collisions);

    public abstract Projectile clone();

    public abstract void damage(SpriteList sprites, int intensity); // inherited but still not implemented

    public int update(SpriteList sprites)
    {
        if (this.alive)
        {
            this.position = new Vector3D( this.position, this.velocity);
            this.velocity = new Vector3D( this.velocity, this.gravity);
            ArrayList<Sprite> coll = getAllCollisions(sprites);
            if (coll.size() > 0)
            {
                reactToCollision(sprites, coll);
            }
            else if (this.position.getZ() <= 0)
            {
                reactToCollision(sprites, new ArrayList<Sprite>());
            }
            return 1;
        }
        else
        {
            return 0;
        }
    }
    // method was added for giving bullets an arc -B
    public void changegravity() {
    	Vector3D biggravity = new Vector3D(0,0,-2);
    	this.gravity.add(biggravity); 
    	
    }
    
}
