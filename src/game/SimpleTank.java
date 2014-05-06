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

/*
 * A tank.
 *
 * Not sure why I called it SimpleTank -- I did not
 * have a more complicated form in mind
 * (it might get renamed to Tank)
 */

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import java.awt.Color;
import java.io.*;

public class SimpleTank extends Sprite implements Serializable
{
    private ArrayList<SimpleTank> playerTanks;
    private double speed; //how far a frame of MoveOrder will move the tank
    private double handling; //how far a frame of TurnOrder will turn the tank
    private OrderQueue orders;
    private int health;
    private Color color;

    public SimpleTank(Vector3D position, Direction direction, int pl, int un)
    {
        super(position, direction, 10);
        this.speed = 5;
        this.handling = 5;
        this.orders = new OrderQueue();
        this.health = 100;
        switch (pl)
        {
            case 1:
                this.color = Color.red;
                break;
            case 2:
                this.color = Color.blue;
                break;
            case 3:
                this.color = Color.yellow;
                break;
            case 4:
                this.color = Color.cyan;
                break;
            case 5:
                this.color = Color.magenta;
                break;
            default:
                this.color = Color.white;
                break;
        }
        this.playerID = pl;
        this.unitNum = un;
    }

    public SimpleTank clone()
    {
        SimpleTank output = new SimpleTank(new Vector3D(this.position), new Direction(this.direction), this.playerID, this.unitNum);
        output.orders = this.orders.clone();
        output.health = this.health;
        return output;
    }
    
    public void giveOrders(OrderQueue newOrders)
    {
        orders = newOrders;
    }

    /**
     * Simply adds a SimpleBullet to the arraylist for now, will have to 
     * be expanded to allow other types of projectiles
     */
    public void shoot(SpriteList sprites, Direction direction)
    {
        sprites.add(new SimpleBullet(new Vector3D(this.position, new Vector3D(25, direction)), direction));
    }
    
    public void damage(SpriteList sprites, int intensity)
    {
        this.health -= intensity;
        if (this.health <= 0)
        {
            this.kill(sprites);
        }
    }

    public int getPlayerID()
    {
        return this.playerID;
    }

    public double getSpeed()
    {
        return speed;
    }

    public double getHandling()
    {
        return handling;
    }

    public Color getColor()
    {
        return color;
    }

    public int update (SpriteList sprites)
    {
        orders.exec(sprites, this);
        return 0;
    }

    public void paint(Graphics2D g)
    {
        double radius=this.hitboxRadius;
        g.setColor(this.color);
        g.fill(Sprite.getRectangle(position.getX(),position.getY(),6, 6));
        g.fill(Sprite.getRectangle(position.getX(),position.getY(),  10, 2));
        //g.fill(Sprite.getCircle(position.getX(),position.getY(),radius));
        g.setColor(Color.black);
        double direction=this.direction.getTheta()*Math.PI/180;
        g.draw(new Line2D.Double(position.getX(),position.getY(),position.getX()+Math.cos(direction)*radius,
                                                   position.getY()+Math.sin(direction)*radius));
    }

}
