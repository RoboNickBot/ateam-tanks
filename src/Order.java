/*
 * A general order.
 *
 * Every order lasts a certain number of frames, and simply
 * defines how the tank will behave for that many frames
 * of the game (frames are the basic unit of game progression --
 * they represent one pass of the main game loop
 */

public abstract class Order
{
    private int frames;

    public Order ( int frames )
    {
        this.frames = frames;
    }

    public void exec ( SimpleTank tank )
    {
        this.execSpecific ( tank );
        frames --;
    }

    protected abstract void execSpecific ( SimpleTank tank );

    public int getFrames ()
    {
        return frames;
    }
}
