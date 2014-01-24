package experiment;
import java.awt.geom.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class GamePanel extends JPanel implements MouseListener,MouseMotionListener, KeyListener{
    private Game game;
    private AffineTransform transform=new AffineTransform();
    private Point2D.Double mouseP=new Point2D.Double();
    public GamePanel(Game _game){
        game=_game;
    }
    public void updateTransform(){
        Rectangle2D.Double rect1=game.getBound();
        Rectangle2D.Double rect2=new Rectangle2D.Double(0,0,getWidth(),getHeight());
        double rx=rect1.width/rect2.width;
        double ry=rect1.height/rect2.height;
        double rr=0;
        if(rx>ry){
            rr=rx; 
            rect2.y+=(rect2.height-rect1.height/rr)/2; 
            rect2.height=rect1.height/rr; 
        }else{ 
            rr=ry; 
            rect2.x+=(rect2.width-rect1.width/rr)/2; 
            rect2.width=rect1.width/rr; 
        } 
        double dx=-rect1.x/rr+rect2.x;
        double dy=(rect1.y+rect1.height)/rr+rect2.y;
        this.transform=new AffineTransform(1/rr,0,0,-1/rr,dx,dy);
    }
    public void paint(Graphics _g){
        game.update(System.currentTimeMillis());
        Graphics2D g=(Graphics2D) _g;
        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());
        updateTransform();
        g.transform(transform);
        g.setColor(Color.red);
        g.fill(game.bound);

        game.tank1.paint(g);
        game.tank2.paint(g);

        if(game.bullet!=null){
            game.bullet.paint(g);
            repaint();
        }else{
            g.setColor(Color.white);
            g.draw(new Line2D.Double(game.tank.getCannonP(),mouseP));
        }
    }
    public void mouseMoved(MouseEvent e){
        Point2D.Double p=new Point2D.Double(e.getX(),e.getY());
        try{
            transform.inverseTransform(p,mouseP);
        }catch(Exception ee){}
        repaint();
    }
    public void mouseDragged(MouseEvent e){

    }
    public void mouseClicked(MouseEvent e){

    }
    public void mouseExited(MouseEvent e){

    }
    public void mouseEntered(MouseEvent e){

    }
    public void mousePressed(MouseEvent e){
         Point2D.Double p=game.tank.getCannonP();
         game.shoot((mouseP.x-p.x)/1000,(mouseP.y-p.y)/1000);
         repaint();
    }
    public void mouseReleased(MouseEvent e){

    }
    public void keyPressed(KeyEvent e){

    }
    public void keyReleased(KeyEvent e){

    }
    public void keyTyped(KeyEvent e){

    }
    public static void main(String[] args){
        JFrame frame=new JFrame("Hello");
        frame.setSize(400,300);
        Game game=new Game();
        game.tank1=new Tank(-30,0);
        game.tank2=new Tank(30,0);
        game.tank=game.tank1;
        game.bound=new Rectangle2D.Double(-50,-50,100,100);
        GamePanel panel=new GamePanel(game);
        panel.addMouseMotionListener(panel);
        panel.addMouseListener(panel);
        panel.addKeyListener(panel);
        frame.setLayout(new BorderLayout());
        frame.add(panel,BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
