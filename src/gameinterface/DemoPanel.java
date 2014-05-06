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

package gameinterface;

import game.*;
import event.*;
import network.*;

import java.util.*;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.geom.*;
import java.awt.Color;
import java.awt.event.*;

import java.io.Console;

public class DemoPanel extends JPanel implements DisplaysGame, GetsOrders/*, KeyListener*/{
    /**
     * 0: display
     * 1: give order
     * 2: announce winner
     */


    Rectangle2D.Double gameViewRect;

    int state;
    SpriteList sprites;

    SimpleTank mainTank=null;
    OrderQueue orderQueue=null;

    String winnerName=null;

    ArrayList<OrderQueue> orders = new ArrayList<OrderQueue>();
    ArrayList<UnitModel> models = new ArrayList<UnitModel>();

    @Override
    public boolean initializeDisplay ( int mapSize ){
        gameViewRect=new Rectangle2D.Double(-mapSize,-mapSize,mapSize*2,mapSize*2);
        state=0;
        sprites = new SpriteList();
        //this.addKeyListener(this);
        return true;
    }
    @Override
    public void cleanUpAndDestroyDisplay (){
        return;
    }
    @Override
    public void show (SpriteList s){
        this.sprites = s;
        repaint();
        try {
        Thread.sleep(60);
        } catch (InterruptedException e) {
            // ..
        }
        //for ( Sprite sprite : sprites.getSprites() )
        //{
        //    System.out.println ( sprite.getPosition().toString() );
        //}
    }

    @Override
    public ArrayList<OrderQueue> askForOrders (SpriteList s, int id, String playerName){
        this.sprites = s;
        this.models = new ArrayList<UnitModel>();
        this.orders = new ArrayList<OrderQueue>();
        this.repaint();
        for (Sprite sprite : sprites.getOwnedBy(id))
        {
            OrderQueue q = new OrderQueue(sprites.getFramesPerTurn(), sprite.uid());
            this.models.add(new UnitModel(sprite));
            this.orders.add(q);
            boolean keepgoing = true;
            int frames = 0;
            int frameLimit = sprites.getFramesPerTurn();
            Console in = System.console();
            System.out.println ();
            System.out.println ();
            System.out.println ( playerName + ", please allocate orders to your tank." );
            while ( keepgoing && frames < sprites.getFramesPerTurn() )
            {
                String type = "f";
                System.out.println ( "You have " + ( frameLimit - frames ) + " frames left for this turn." );
                System.out.println ( "Please choose an order type: [w]ait, [m]ove, [t]urn, [s]hoot, [f]inished" );
                type = in.readLine ( ">>> " );
                if ( type.equals ( "w" ) || type.equals ( "wait" ) )
                {
                    System.out.println ( "Wait for how many frames?" );
                    int f = Integer.parseInt ( in.readLine ( ">>> " ) );
                    if ( frames+f <= frameLimit )
                    {
                        frames += f;
                    }
                    else
                    {
                        f = frameLimit - frames;
                        frames = frameLimit;
                    }
                    q.add ( new WaitOrder ( f ) );
                    System.out.println ( "Ordered wait for " + f + " frames." );
                }
                if ( type.equals ( "m" ) || type.equals ( "move" ) )
                {
                    System.out.println ( "Move for how many frames?" );
                    int f = Integer.parseInt ( in.readLine ( ">>> " ) );
                    if ( frames+f <= frameLimit )
                    {
                        frames += f;
                    }
                    else
                    {
                        f = frameLimit - frames;
                        frames = frameLimit;
                    }
                    System.out.println ( "And in what direction? ( 1 = forwards, -1 = backwards )" );
                    int d = Integer.parseInt ( in.readLine ( ">>> " ) );
                    q.add ( new MoveOrder ( f, d ) );
                    System.out.println ( "Ordered move for " + f + " frames." );
                }
                if ( type.equals ( "t" ) || type.equals ( "turn" ) )
                {
                    System.out.println ( "Turn for how many frames?" );
                    int f = Integer.parseInt ( in.readLine ( ">>> " ) );
                    if ( frames+f <= frameLimit )
                    {
                        frames += f;
                    }
                    else
                    {
                        f = frameLimit - frames;
                        frames = frameLimit;
                    }
                    System.out.println ( "And in what direction? ( -1 = clockwise, 1 = counter-clockwise )" );
                    int d = Integer.parseInt ( in.readLine ( ">>> " ) );
                    q.add ( new TurnOrder ( f, d ) );
                    System.out.println ( "Ordered move for " + f + " frames." );
                }
                if ( type.equals ( "s" ) || type.equals ( "shoot" ) )
                {
                    int f = 15;
                    if ( frames+f <= frameLimit )
                    {
                        frames += f;
                        System.out.println ( "In what direction? ( 0 - 359 )" );
                        int d = Integer.parseInt ( in.readLine ( ">>> " ) );
                        q.add ( new ShootOrder ( ( double ) (d+1)  ) );
                        q.add ( new ShootOrder ( ( double ) d  ) );
                        q.add ( new ShootOrder ( ( double ) (d-5)  ) );
                        q.add ( new ShootOrder ( ( double ) (d-3)  ) );
                        q.add ( new ShootOrder ( ( double ) (d+4)  ) );
                        System.out.println ( "Ordered shoot (requires " + f + " frames." );
                    }
                    else
                    {
                        System.out.println ( "You don't have enough frames left for a shoot (requires " + f + " frames)" );
                    }
                }
                if ( type.equals ( "f" ) || type.equals ( "finished" ) )
                {
                    System.out.println ( "Excellent" );
                    keepgoing = false;
                }
                if ( frames >= frameLimit )
                {
                    System.out.println ( "Frames filled for this turn" );
                }
                this.repaint ();
            }
            System.out.println();
            System.out.println();
            System.out.println();
            this.repaint ();
        }
        ArrayList<OrderQueue> output = new ArrayList<OrderQueue>(this.orders);
        this.orders = new ArrayList<OrderQueue>();
        this.models = new ArrayList<UnitModel>();
        this.repaint();
        return output;
    }

    public void updateTransform(Graphics2D g2){
        Rectangle2D.Double rect1=gameViewRect;
        Rectangle2D.Double rect2=new Rectangle2D.Double(0,0,getWidth(),getHeight());
        double rr=0;
        double rx=rect1.width/rect2.width;
        double ry=rect1.height/rect2.height;
        if(rx>ry){
            rr=rx; 
            rect2.y+=(rect2.height-rect1.height/rr)/2; 
            rect2.height=rect1.height/rr; 
        }else{ 
            rr=ry; 
            rect2.x+=(rect2.width-rect1.width/rr)/2; 
            rect2.width=rect1.width/rr; 
        } 
        double dx=-rect1.x/rr+rect2.x;
        double dy=(rect1.y+rect1.height)/rr+rect2.y;
        g2.setTransform(new AffineTransform(1/rr,0,0,-1/rr,dx,dy));
    }
    @Override
    public void paint(Graphics g){
        Graphics2D g2=(Graphics2D) g;
        if(state==2){
            g2.drawString(winnerName,100,100);
            return;
        }
        updateTransform(g2);
        //fill background
        g2.setColor(Color.black);
        g2.fill(gameViewRect);
        for(Sprite sprite:sprites.getSprites()){
            sprite.paint(g2);
            //System.out.println ( sprite.getClass().getName() + " painted!!!" );
        }
        if(state==1){
            //mainTank.paint(g2);
        }
        for (int x = 0; x < orders.size(); x++)
        {
            UnitModel m = new UnitModel(models.get(x));
            orders.get(x).walkModel ( m, g2 );
        }
    }
    /*
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyPressed(KeyEvent e){
        if(state!=1){
            return;
        }
        Order order=null;
        switch(e.getKeyCode()){
        case KeyEvent.VK_UP:
            order=new MoveOrder(1, 1);
        break;
        case KeyEvent.VK_DOWN:
            order=new MoveOrder(1,-1);
            
        break;
        case KeyEvent.VK_RIGHT:
            order=new TurnOrder(1,-1);
        break;
        case KeyEvent.VK_LEFT:
            order=new TurnOrder(1, 1);
        break;
        case KeyEvent.VK_SPACE:
            mainTank.notify();
            return;
        default:
        break;
        }
        //no valid order
        if(order==null){
            return;
        }
        //too many frames
        if(orderQueue.add(order)==1){
            return;
        }
        order=(Order)order.clone();
        order.exec(mainTank);
    }
    @Override
    public void keyReleased(KeyEvent e){

    }
    */
}
