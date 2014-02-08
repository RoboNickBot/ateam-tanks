import java.lang.Math;

/**
 * A class to represent vectors
 *
 * Will be used to represent positions, velocities,
 *+and offsets
 */
public class Vector3D
{
    private double x;
    private double y;
    private double z;

    public Vector3D ()
    {
        x = 0;
        y = 0;
        z = 0;
    }
    public Vector3D ( double x, double y, double z )
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector3D ( Vector3D other )
    {
        x = other.x;
        y = other.y;
        z = other.z;
    }
    public Vector3D ( Vector3D other, Vector3D offset )
    {
        x = other.getX () + offset.getX ();
        y = other.getY () + offset.getY ();
        z = other.getZ () + offset.getZ ();
    }
    public Vector3D ( double magnitude, Direction direction, Direction inclination )
    {
        x = ( Math.cos ( Math.toRadians ( direction.getValue() ) ) * magnitude ) ;
        y = ( Math.sin ( Math.toRadians ( direction.getValue() ) ) * magnitude ) ;
        z = ( Math.sin ( Math.toRadians ( inclination.getValue() ) ) * magnitude ) ;
    }

    public void add ( Vector3D other )
    {
        x += other.x;
        y += other.y;
        z += other.z;
    }
    public double getX ()
    {
        return x;
    }
    public double getY ()
    {
        return y;
    }
    public double getZ ()
    {
        return z;
    }

    public String toString ()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}