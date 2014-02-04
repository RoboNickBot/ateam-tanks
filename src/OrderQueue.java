
import java.util.*;

/**
 * A containing class for a list of orders that
 * each tank recieves at the beginning of each turn.
 *
 * All it does is take care of discarding orders when they
 * are out of frames
 *
 * The orderqueue is filled during the player's ordering
 * phase before each turn.
 *
 * @author Nick Lewchenko, Jiaqi Li
 *
 */
class OrderQueue
{
    private int framesLeft;
    private Queue<Order> orders;

    public OrderQueue ()
    {
        this(100);
    }
    public OrderQueue(int framesAllowed){
        this.framesLeft=framesAllowed;
        orders = new ArrayDeque<Order> ();
    }

    public int getFramesLeft(){
        return framesLeft;
    }

    /**
     * @return 0 when success, 1 when no more frames left.
     */
    public int add ( Order o )
    {
        if(o.getFrames()>framesLeft){
            return 1;
        }else{
            orders.add ( o );
            framesLeft-=o.getFrames();
            return 0;
        }
    }
    /**
     * @return 0 when success, 1 when queue is empty.
     */
    public int exec ( SimpleTank tank )
    {
        Order o=orders.peek();
        while(o!=null&&o.getFrames()==0){
            orders.remove();
            o=orders.peek();
        }
        if ( o !=null )
        {
            o.exec( tank );
            return 0;
        }else{
            return 1;
        }
    }
}
