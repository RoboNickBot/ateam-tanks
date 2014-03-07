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

class SimpleTank extends Sprite
{
    private ArrayList<SimpleTank> playerTanks;
    private double speed; //how far a frame of MoveOrder will move the tank
    private double handling; //how far a frame of TurnOrder will turn the tank
    private OrderQueue orders;
    private int health;
    private Color color;

    public SimpleTank ( SpriteList sprites, ArrayList<SimpleTank> playerTanks, Vector3D position, Direction direction, double speed, double handling , Color c)
    {
        super ( sprites, position, direction, 10 );
        this.speed = speed;
        this.handling = handling;
        OrderQueue orders = new OrderQueue ();
        this.playerTanks = playerTanks;
        this.health = 100;
        this.color = c;
    }
    
    public void giveOrders ( OrderQueue newOrders )
    {
        orders = newOrders;
    }

    /**
     * Simply adds a SimpleBullet to the arraylist for now, will have to 
     * be expanded to allow other types of projectiles
     */
    public void shoot ( Direction direction )
    {
        this.sprites.add(new SimpleBullet(this.sprites, new Vector3D(this.position, new Vector3D(25, direction)), direction ));
    }
    public void damage ( int intensity )
    {
        this . health -= intensity;
        if ( this . health <= 0 )
        {
            this . kill ();
        }
    }
    public double getSpeed ()
    {
        return speed;
    }
    public double getHandling ()
    {
        return handling;
    }

    public void kill ()
    {
        sprites.remove ( this );
        playerTanks.remove ( this );
    }

    public int update ()
    {
        orders.exec( this );
        return 0;
    }
    public void paint(Graphics2D g){
        double radius=this.hitboxRadius;
        g.setColor(this.color);
        g.fill(Sprite.getCircle(position.getX(),position.getY(),radius));
        g.setColor(Color.black);
        double direction=this.direction.getTheta()*Math.PI/180;
        g.draw(new Line2D.Double(position.getX(),position.getY(),position.getX()+Math.cos(direction)*radius,
                                                   position.getY()+Math.sin(direction)*radius));
    }
    
    // worried about the array list of player tanks i don't think it is cloned properly
    public SimpleTank clone() {
    	SimpleTank clone = new SimpleTank(this.sprites,this.playerTanks,this.position,this.direction,this.speed,this.handling,this.color);
    	return clone;
    }
}
