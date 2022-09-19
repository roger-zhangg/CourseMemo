
import java.util.*;
import java.awt.geom.*;
import java.awt.*;


public class DubinCarController implements CarController {

    // Angular velocities.
    double mu1=10, mu2=10;
    ArrayList<Rectangle2D.Double> obstacles;
    SensorPack sensors;
    // Are the two controls accelerators?
    boolean isAccelModel;
    double start = 50;
    double b = 7.8;
    double interval = 75;

    public void init (double initX, double initY, double initTheta, double endX, double endY, double endTheta, ArrayList<Rectangle2D.Double> obstacles, SensorPack sensors)
    {
        this.obstacles = obstacles;
        this.sensors = sensors;
    }
    

    public double getControl (int i)
    {
        if (i == 1) {
            return mu1;
        }
        else if (i == 2) {
            return mu2;
        }
        else {
            System.out.println ("ERROR: DubinCarController.getControl(): incorrect input");
            return 0;
        }
    }


    public void move ()
    {
        
            double x = sensors.getX();
            // Pass the controls (v1=10, v2=10) to the simulator. DeltaT=0.1.
            if (x<=start+interval){
                mu1=b;
                mu2=10;
            }
            if (x>start+interval&&x<=start+3*interval){
                mu1=10;
                mu2=b;
            }
            if (x>start+3*interval&&x<start+4*interval){
                mu1=b;
                mu2=10;
            }
            double theta = sensors.getTheta();
            if (x>=start+4*interval){
                    if (theta > 0){
                        mu1=10;
                        mu2=9.95;
                    }else if (theta <0){
                        mu1=9.95;
                        mu2=10;
                    }else{
                        mu1=10;
                        mu2=10;
                    }
            }
    }
    
    public void draw (Graphics2D g2, Dimension D)
    {
    }
    

}
