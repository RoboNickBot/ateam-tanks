/**
 * An order for turning the tank
 *
 * each frame, it turns tank by <code>tank.getHandling()</code> angle.
 */

public class TurnOrder extends Order
{
    private int direction; // 1 is clockwise, -1 is counter-clockwise

    /**
     * @param frames number of frames to turn
     * @param direction either be 1 to turn clockwisely or -1 to turn counter-clockwisely
     */
    public TurnOrder ( int frames, int direction )
    {
        super ( frames );
        this.direction = direction;
    }

    public int getDirection(){
        return direction;
    }

    @Override
    protected void execSpecific ( SimpleTank tank )
    {
        tank.setDirection ( tank.getDirection () + direction * tank.getHandling () );
    }
}
