
import java.util.*;
import java.awt.geom.*;
import java.awt.*;


public class PlayerSimulator {

    boolean debug = false;

    double t;                        // Current time.
    double x, y, theta;              // Current x,y, orientation.
    int playerNum = -1;              // Which player are we?
    int team = -1;                   // Which team?
    
    double nextX, nextY, nextTheta;  // Temporary - until the step is applied.

    double phi;                      // Steering angle.
    double v;                        // Forward velocity for accel model.
    boolean isAccelModel;            // Is the first control an accelerator?

    boolean isUnicycle = true;

    boolean hasBall = false;         // Ball data when we have the ball.
    double ballX, ballY, ballRadius; // Main program sets ballRadius.

    // Save the controls (velocity, turning angle). Called by Soccer.java
    double control1, control2;

    public PlayerSimulator (boolean isAccelModel, boolean isUnicycle)
    {
        this.isAccelModel = isAccelModel;
        this.isUnicycle = isUnicycle;
    }
    
    
    public void init (double initX, double initY, double initTheta)
    {
        this.x = initX;
        this.y = initY;
        this.theta = initTheta;
        this.t = 0;
    }
    

    public double getX ()
    {
        return x;
    }
    

    public double getY ()
    {
        return y;
    }
    

    public double getTheta ()
    {
        return theta;
    }
    

    public double getV ()
    {
        return v;
    }


    public void applyNextStep ()
    {
	// While computeNextStep() computes the next values, this method
	// actually applies them, i.e., does the update.
        x = nextX;
        y = nextY;
        theta = nextTheta;
    }
    


    public void computeNextStep (double c1, double c2, double delT)
    {
	control1 = c1;
	control2 = c2;

        if (isAccelModel) {
            v += delT * c1;
        }
        else {
            v = c1;
        }
        
        double delX = delT * v * Math.cos(theta);
        nextX = x + delX;
        double delY = delT * v * Math.sin(theta);
        nextY = y + delY;

        phi = (Math.PI / 20.0) * c2;

        double delTheta = delT * phi;
        if (!isUnicycle) {
            delTheta = delT * v * Math.sin(phi);
        }
        nextTheta = theta + delTheta;

	if (debug)
	    Debug.println (">>>computeNextStep: team="+ team + " p=" + playerNum + " phi=" + phi + " th=" + theta + " nextTh=" + nextTheta + " fixedNextTh=" + angleFix(nextTheta));
	nextTheta = angleFix (nextTheta);

	// Compute the position where the player would hold the ball.
	// This is used when the player actually has the ball.
	ballX = x + 2*ballRadius*Math.cos(theta);
	ballY = y + 2*ballRadius*Math.sin(theta);

        t += delT;
    }
    

    double angleFix (double a)
    {
        // Make each angle an angle between 0 and 2*PI.
        //** Note: this code can be optimized.
        if (a < 0) {
            while (a < 0) {
                a = a + 2*Math.PI;
            }
        }
        else if (a > 2*Math.PI) {
            while (a > 2*Math.PI) {
                a = a - 2*Math.PI;
            }
        }
        return a;
    }


    public double getTime ()
    {
        return t;
    }

}
