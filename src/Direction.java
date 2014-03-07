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
 * A class to abstract sprite directions
 *
 */
public class Direction
{
    private double theta;
    private double phi;

    public Direction ()
    {
        theta=0;
        phi=0;
    }
    public Direction ( double dir )
    {
        theta=dir;
        phi=0;
    }
    public Direction ( double dir, double phi)
    {
        this.theta = dir;
        this.phi = phi;
    }
    public Direction ( Direction other )
    {
        this.theta=other.theta;
        this.phi=other.phi;
    }
    public Direction ( Direction other, Direction offset )
    {
        this.theta=round(other.theta+offset.theta);
        this.phi=bound(other.phi+offset.phi);
    }
    public double getTheta(){
        return theta;
    }
    public double getPhi(){
        return phi;
    }

    public String toString ()
    {
        return "[" + theta+","+phi + "]";
    }

    private static double round ( double dir )
    {
        return ((dir % 360.0)+360.)%360.;
    }
    private static double bound(double dir){
        if(dir>180){
            return 180;
        }else if(dir<-180){
            return -180;
        }else{
            return dir;
        }
    }
    public Direction clond() {
    	Direction clone= new Direction(this.theta);
    	return clone;
    }
    
}
