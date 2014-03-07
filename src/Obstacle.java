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
 * This is a sprite representing a non-moving object
 * on the playing field.
 *
 * These will be the main pieces that give the map
 * variance
 *
 */

import java.awt.Color;
import java.util.ArrayList;
import java.awt.Graphics2D;

public class Obstacle extends Sprite
{
    public Obstacle ( SpriteList sprites, Vector3D position, Direction direction, double hitboxRadius )
    {
        super ( sprites, position, direction, hitboxRadius );
    }

    /**
     * An obstacle does nothing and is affected by nothing
     */
    public int update ()
    {
        return 0;
    }
    public void damage ( int intensity )
    {
        // damage does not affect it, does nothing
    }
    public void paint(Graphics2D g){
        double radius=this.hitboxRadius;
        g.setColor(Color.white);
        g.fill(Sprite.getCircle(position.getX(),position.getY(),radius));
    }
    
    public Obstacle clone() {
    	Obstacle clone = new Obstacle(this.sprites,this.position,this.direction,this.hitboxRadius);
    	return clone;
    }
}
