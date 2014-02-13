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

public abstract class Sprite
{
    protected ArrayList<Sprite> sprites;
    protected Vector3D position;
    protected Direction direction;
    protected HitBox hitbox;
    protected double hitboxRadius=10;
    protected Color color;

    public Sprite ( ArrayList<Sprite> sprites, Vector3D p, Direction d, HitBox h, Color c )
    {
        this.sprites = sprites;
        this.sprites.add ( this );

        this.position= new Vector3D ( p );
        this.direction = new Direction ( d );
        this.hitbox = new HitBox ( h );
        this.color = c;
    }

    public abstract int update ();

    // public abstract void paint(Graphics2D g);

    public boolean checkCollision ( Sprite other )
    {
        return position.distance(other.position)<hitboxRadius+other.hitboxRadius;
    }
    public ArrayList<Sprite> getAllCollisions ()
    {
        ArrayList<Sprite> collisions = new ArrayList<Sprite>();
        for ( Sprite sprite : this.sprites )
        {
            collisions.add ( sprite );
        }
        
        return collisions;
    }

    public HitBox getHitBox ()
    {
        return hitbox;
    }
    public void setHitBox ( HitBox other )
    {
        this.hitbox = new HitBox ( other );
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
    public Color getColor ()
    {
        return color;
    }
    public void setColor (Color c) 
    {
        this.color = c;
    }

    public void kill ()
    {
        sprites.remove ( this );
    }
    public static Arc2D.Double getCircle(double x,double y,double radius){
        return new Arc2D.Double(x-radius,y-radius,radius*2,radius*2,0,360,Arc2D.CHORD);
    }
}
