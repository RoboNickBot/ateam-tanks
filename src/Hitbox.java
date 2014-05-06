
/**
 * hitbox's center is the actual position
 */
import java.awt.geom.*;
public class Hitbox{
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
