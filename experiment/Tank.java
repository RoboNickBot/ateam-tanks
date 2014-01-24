package experiment;
import java.awt.geom.*;
import java.awt.*;
public class Tank{
    public Rectangle2D.Double hitBox;
    public Color color=Color.blue;
    public Tank(double x,double y){
        hitBox=new Rectangle2D.Double(x-10,y-10,20,20);
    }
    public Bullet createBullet(double vx,double vy){
        Point2D.Double p=getCannonP();
        Bullet f=new Bullet(p.x,p.y,vx,vy);
        return f;
    }
    public void paint(Graphics2D g){
        g.setColor(color);
        g.fill(hitBox);
    }
    public boolean testBullet(Bullet bullet){
        if(hitBox.contains(bullet.x,bullet.y)){
            color=Color.black;
            return true;
        }
        return false;
    }
    public Point2D.Double getCannonP(){
        return new Point2D.Double(hitBox.x+10,hitBox.y+21);
    }
    
    
    
}
