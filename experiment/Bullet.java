package experiment;
import java.awt.geom.*;
import java.awt.*;
public class Bullet{
    public static final double G=0.00001;
    double x,y;
    double vx,vy;
    public Bullet(double _x,double _y,double _vx,double _vy){
        x=_x;y=_y;vx=_vx;vy=_vy;
    }
    public void update(long dt){
        x+=vx*dt;
        y+=vy*dt;
        vy-=G;
    }
    public void paint(Graphics2D g){
        g.setColor(Color.black);
        g.fill(new Ellipse2D.Double(x-5,y-5,10,10));
    }
}
