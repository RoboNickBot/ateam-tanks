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
}
