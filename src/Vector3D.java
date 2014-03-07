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
    public Vector3D ( double magnitude, Direction direction)
    {
        double theta=Math.toRadians(direction.getTheta());
        double phi=Math.toRadians(direction.getPhi());
        x=magnitude*Math.cos(phi)*Math.cos(theta);
        y=magnitude*Math.cos(phi)*Math.sin(theta);
        z=magnitude*Math.sin(phi);
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
    public double distance(Vector3D v){
        return Math.sqrt((v.x-x)*(v.x-x)+(v.y-y)*(v.y-y)+(v.z-z)*(v.z-z));
    }
    
    public Vector3D clone() {
    	Vector3D clone= new Vector3D(this.x,this.y,this.z);
    	return clone;
    }
}
