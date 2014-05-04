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
import java.io.*;

public abstract class Sprite extends Object implements Serializable
{
    protected Vector3D position;
    protected Direction direction;
    protected boolean alive;
    protected Hitbox hitbox;
    protected int playerID; // identifies owning player (if any; 0 means none)
    protected int unitNum; // identifies self out of owning player's units

    public Sprite(Vector3D p, Direction d, double hr)
    {
        this.position = new Vector3D(p);
        this.direction = new Direction(d);
        this.hitbox = new Hitbox(hr,hr,hr);
        this.alive = true;
        this.playerID = 0; // indicating unowned
        this.unitNum = 0;
    }

    public abstract Sprite clone();

    public abstract int update(SpriteList sprites);

    public abstract void damage(SpriteList sprites, int intensity);

    public abstract void paint(Graphics2D g);

    public void giveOrders(OrderQueue newOrders)
    {
        // for most sprites this does nothing
    }

    public boolean checkCollision(Sprite other)
    {
        return Hitbox.ifHit(this.hitbox, this.position, this.direction.getTheta(),other.hitbox, other.position, other.direction.getTheta());
    }

    public ArrayList<Sprite> getAllCollisions (SpriteList sprites)
    {

        ArrayList<Sprite> collisions = new ArrayList<Sprite>();
        for ( Sprite sprite : sprites.getSprites() )
        {
            if(sprite != this && checkCollision(sprite)){
                collisions.add ( sprite );
            }
        }
        return collisions;
    }

    public Vector3D getPosition()
    {
        return position;
    }
    public void setPosition(Vector3D p)
    {
        this.position = p;
    }
    public Direction getDirection()
    {
        return direction;
    }
    public void setDirection(Direction d)
    {
        this.direction = d;
    }
    public int getPlayerID()
    {
        return this.playerID;
    }
    public int getUnitNum()
    {
        return this.unitNum;
    }
    public String uid()
    {
        return this.playerID + "<->" + this.unitNum;
    }

    public void kill (SpriteList sprites)
    {
        this.alive = false;
        sprites.remove(this);
    }
    public static Arc2D.Double getCircle(double x,double y,double radius){
        return new Arc2D.Double(x-radius,y-radius,radius*2,radius*2,0,360,Arc2D.CHORD);
    }
}
