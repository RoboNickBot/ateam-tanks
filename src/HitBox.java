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
 * A rectangular prism in which a sprite resides.
 *
 * If two sprites' HitBoxes intersect, they are
 * considered to be colliding.
 *
 */

public class HitBox
{
    private double length;
    private double width;
    private double height;

    public HitBox ()
    {
        length = 0;
        width = 0;
        height = 0;
    }
    public HitBox ( double l, double w, double h )
    {
        length = l;
        width = w;
        height = h;
    }
    public HitBox ( HitBox other )
    {
        length = other.length;
        width = other.width;
        height = other.height;
    }

    public double getLength ()
    {
        return length;
    }
    public double getWidth ()
    {
        return width;
    }
    public double getHeight ()
    {
        return height;
    }
    
    public HitBox clone() {
    	HitBox clone= new HitBox(this.length,this.width,this.height);
    	return clone;
    }
}
