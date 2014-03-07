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

import java.util.ArrayList;

/**
 * This class is abstracts the workings of the sprite list.
 *
 * This was necessary because sprites can only be added to or removed
 * from the main sprite list at the beginning or end of each pass-through
 *
 * If the list is changed in any way during a 
 *
 *   for ( Sprite sprite : sprites )
 *
 * type block, the program crashes with an exception.
 *
 * This class handles postponing list alterations until they
 * are safe to perform.
 */

public class SpriteList
{
    private ArrayList<Sprite> sprites;
    private ArrayList<Sprite> toBeAdded;
    private ArrayList<Sprite> toBeRemoved;

    public SpriteList ()
    {
        this . sprites = new ArrayList<Sprite>();
        this . toBeAdded = new ArrayList<Sprite>();
        this . toBeRemoved = new ArrayList<Sprite>();
    }
    public SpriteList ( SpriteList other )
    {
        this . sprites = new ArrayList<Sprite>( other.sprites );
        this . toBeAdded = new ArrayList<Sprite>( other.toBeAdded );
        this . toBeRemoved = new ArrayList<Sprite>( other.toBeRemoved );
    }
    public SpriteList ( ArrayList<Sprite> initial )
    {
        this . sprites = initial;
        this . toBeAdded = new ArrayList<Sprite>();
        this . toBeRemoved = new ArrayList<Sprite>();
    }

    public ArrayList<Sprite> getSprites ()
    {
        return sprites;
    }
    public void add ( Sprite newSprite )
    {
        this . toBeAdded . add ( newSprite );
    }
    public void remove ( Sprite deadSprite )
    {
        this . toBeRemoved . add ( deadSprite );
    }
    public void update ()
    {
        for ( Sprite deadSprite : this . toBeRemoved )
        {
            boolean success = false;
            success = this . sprites . remove ( deadSprite );
            if ( success )
            {
                System.out.println ( deadSprite . getClass () . getName () + " removed" );
            }
            else
            {
                System.out.println ( "REMOVE FAILED !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" );
            }
        }
        this . toBeRemoved = new ArrayList<Sprite>();

        for ( Sprite newSprite : this . toBeAdded )
        {
            this . sprites . add ( newSprite );
            System.out.println ( newSprite . getClass () . getName () + " added" );
        }
        this . toBeAdded = new ArrayList<Sprite>();
    }
    // not sure how to clone this one
    public SpriteList clone() {
    	SpriteList clone = new SpriteList();
    	if(!this.sprites.isEmpty()) {
    		for(int i = 0; i> this.sprites.size(); i++) {
    			clone.sprites.add(this.sprites.get(i));
    		}
    	}
    	if(!this.toBeAdded.isEmpty()) {
    		for(int i = 0; i> this.toBeAdded.size(); i++) {
    			clone.toBeAdded.add(this.toBeAdded.get(i));
    		}
    	}
    	if(!this.toBeRemoved.isEmpty()) {
    		for(int i = 0; i> this.toBeRemoved.size(); i++) {
    			clone.toBeRemoved.add(this.toBeRemoved.get(i));
    		}
    	}
    	return clone;
    	
    }
}
