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
    {
        this . sprites = new ArrayList<Sprite>();
        this . toBeAdded = new ArrayList<Sprite>();
        this . toBeRemoved = new ArrayList<Sprite>();
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
            this . sprites . remove ( deadSprite );
        }
        this . toBeRemoved = new ArrayList<Sprite>();

        for ( Sprite newSprite : this . toBeAdded )
        {
            this . sprites . add ( newSprite );
        }
        this . toBeAdded = new ArrayList<Sprite>();
    }
    
    public SpriteList clone()
    { 	
    	SpriteList clone;
    	try {
			clone =(SpriteList)super.clone();
		} 
    	catch (CloneNotSupportedException e) {
			throw new Error();
		}
    	return clone; 
    }
    
}
