/*
 * A position of an object on the field
 */

class Position
{
    private int x;
    private int y;
    private int z;

    public Position ()
    {
        x = 0;
        y = 0;
        z = 0;
    }
    public Position ( int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Position ( Position p )
    {
        x = p.getX ();
        y = p.getY ();
        z = p.getZ ();
    }
    
    //This is supposed to be a simple way of producing adjacent positions for movement
    public Position ( Position prev, Velocity v )
    {
        x = v.getX () + prev.getX ();
        y = v.getY () + prev.getY ();
        z = v.getZ () + prev.getZ ();
    }

    public int getX ()
    {
        return x;
    }
    public int getY ()
    {
        return y;
    }
    public int getZ ()
    {
        return z;
    }

    public String toString ()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
