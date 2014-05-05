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

package game;

/**
 * hitbox's center is the actual position
 */
import java.awt.geom.*;
import java.io.*;

public class Hitbox implements Serializable {
    //x span
    private double width;
    //y span
    private double height;
    //z span
    private double altitude;
    public Hitbox(double a,double b,double c){
        width=a;
        height=b;
        altitude=c;
    }
    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }
    public double getAltitude(){
        return altitude;
    }
    public static boolean ifHit(Hitbox b1,Vector3D v1,double d1,Hitbox b2,Vector3D v2,double d2){
        double tmp=(b1.altitude+b2.altitude)/2;
        //check z direction
        if(v1.getZ()-v2.getZ()>tmp){
            return false;
        }
        if(v2.getZ()-v1.getZ()>tmp){
            return false;
        }
        Area area1=getArea(b1,v1,d1);
        Area area2=getArea(b2,v2,d2);
        area1.intersect(area2);
        return !area1.isEmpty();
    }
    public static Area getArea(Hitbox b1,Vector3D v1,double d1){
        Path2D.Double path=new Path2D.Double();
        double cos=Math.cos(d1);
        double sin=Math.sin(d1);
        double x=v1.getX();
        double y=v1.getY();
        path.moveTo(b1.width/2,b1.height/2);
        path.lineTo(b1.width/2,-b1.height/2);
        path.lineTo(-b1.width/2,-b1.height/2);
        path.lineTo(-b1.width/2,b1.height/2);
        path.closePath();
        AffineTransform trans=new AffineTransform();
        trans.scale(.5,.5);
        trans.rotate(d1);
        trans.translate(x,y);
        path.transform(trans);
        System.out.println(path.toString());
        return new Area(path);
    }
}
