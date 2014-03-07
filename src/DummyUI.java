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

/**
 * A do-nothing standin for testing the Player
 * and Game classes ( well, it does print position information )
 */

class DummyUI implements InterfaceWithGame
{
    private SpriteList sprites;
    
    public DummyUI ()
    {
        sprites = new SpriteList();
    }

    public boolean initializeDisplay ( SpriteList sprites, int mapsize )
    {
        this.sprites = sprites;
        System.out.println ( "All initialized with a mapsize of " + mapsize + "!" );

        return true;
    }
    public void cleanUpAndDestroyDisplay ()
    {
        System.out.println ( "Everything is cleaned up\n" );
    }
    public void updateDisplay ()
    {
        for ( Sprite sprite : sprites . getSprites () )
        {
            System.out.println ( sprite.getClass().getName() + " -- " + sprite.getPosition().toString() );
        }
    }
    public OrderQueue askForOrders ( String playerName, int frameLimit, SimpleTank sprite )
    {
        System.out.println ( "There are only " + frameLimit + " frames in this turn." );
        System.out.println ( "You wanted to move forward for " + frameLimit/2 + " frames, right?" );

        OrderQueue q = new OrderQueue();
        q.add ( new MoveOrder ( frameLimit/2, 1 ) );
        q.add ( new ShootOrder ( 90 ) );

        return q;
    }
    public void announceWinner ( String winnerName )
    {
        System.out.println ( "The winner appears to be " + winnerName + "." );
    }
    
    
    // ask about the implements interface with game part
    //public DummyUI clone {
    	//DummyUI clone= new 
    //}
}

