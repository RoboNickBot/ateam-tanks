package experiment;
import java.awt.geom.*;
public class Game{
    public Rectangle2D.Double bound;
    public Tank tank1,tank2;
    public Tank tank;
    public Bullet bullet;
    public long currentTime;
    public Game(){
        currentTime=System.currentTimeMillis();
    }
    public Rectangle2D.Double getBound(){
        return bound;
    }
    
    public void update(long time){
        time-=currentTime;
        currentTime+=time;
        if(bullet!=null){
            bullet.update(time);
        }
        if(bullet!=null&&tank1.testBullet(bullet)){
            bullet=null;
        }
        if(bullet!=null&&tank2.testBullet(bullet)){
            bullet=null;
        }
        if(bullet!=null&&!bound.contains(bullet.x,bullet.y)){
            bullet=null;
        }
    }
    public void shoot(double vx,double vy){
         bullet=tank.createBullet(vx,vy);
         if(tank==tank1){
             tank=tank2;
         }else{
             tank=tank1;
         }
    }
}
