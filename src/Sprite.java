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

/*
 * This is a general class for anything that might
 * be placed, removed, or move or change in any way
 * on the field during play. The idea is that the main
 * game loop can iterate over a list of these and update
 * each one.
 * And then the graphical interface can iterate over the list
 * and display each one.
 *
 * So far only tanks use them, but the next obvious user would
 * be a bullet
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Color;

public abstract class Sprite extends Object
{
    protected SpriteList sprites;
    protected Vector3D position;
    protected Direction direction;
    protected boolean alive;
    protected double hitboxRadius;

    public Sprite ( SpriteList sprites, Vector3D p, Direction d, double hr )
    {
        this.sprites = sprites;

        this.position= new Vector3D ( p );
        this.direction = new Direction ( d );
        this.hitboxRadius = hr;
        this.alive = true;
    }

    public abstract int update ();

    public abstract void damage ( int intensity );

    public abstract void paint(Graphics2D g);

    public boolean checkCollision ( Sprite other )
    {
        return position.distance(other.position)<hitboxRadius+other.hitboxRadius;
    }
    public ArrayList<Sprite> getAllCollisions ()
    {

        ArrayList<Sprite> collisions = new ArrayList<Sprite>();
        for ( Sprite sprite : this.sprites.getSprites() )
        {
            if(sprite!=this&&checkCollision(sprite)){
                collisions.add ( sprite );
            }
        }
        return collisions;
    }

    public Vector3D getPosition ()
    {
        return position;
    }
    public void setPosition ( Vector3D p )
    {
        this.position = p;
    }
    public Direction getDirection ()
    {
        return direction;
    }
    public void setDirection ( Direction d )
    {
        this.direction = d;
    }

    public void kill ()
    {
        this . alive = false;
        sprites.remove ( this );
    }
    public static Arc2D.Double getCircle(double x,double y,double radius){
        return new Arc2D.Double(x-radius,y-radius,radius*2,radius*2,0,360,Arc2D.CHORD);
    }
    
    public  abstract Sprite clone(); 
    
}
