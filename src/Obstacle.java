/**
 * This is a sprite representing a non-moving object
 * on the playing field.
 *
 * These will be the main pieces that give the map
 * variance
 *
 */

import java.util.ArrayList;

public class Obstacle extends Sprite
{
    public Obstacle ( ArrayList<Sprite> sprites, Vector3D position, Direction direction, HitBox box )
    {
        super ( sprites, position, direction, box );
    }

    /**
     * An obstacle does nothing and is affected by nothing
     */
    public int update ()
    {
        return 0;
    }
}
