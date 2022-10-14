
import java.text.*;

public class ConcCalc2 {
	
    public static void main (String[] argv)
    {
    
	// For printing/formatting:
	

	// Rate parameters:
	double K_ab = 1.0;
	double K_c = 0.5;

	// Initial values:
	double A = 3.0;  
	double B = 2.0;
	double C = 1.5;
	// Initialize time:
	double t = 0;
	
	// Set our time increment:
	double s = 0.01;

	// Set final time:
	double endTime = 1.0;
	
	// Compute.
	recurCal(A,B,C,endTime,0.0,s);

    }

    public static void recurCal (double A,double B,double C,double endTime,double t, double s){
    	if (t>=endTime){
    		return;
    	}
    	DecimalFormat df = new DecimalFormat ("###.###");
    	System.out.println ("At time t=" + df.format(t) + ":  A(t)=" + df.format(A) + "   B=" + df.format(B) + "   C(t)=" + df.format(C));
    	recurCal(A+s*(0.5*C-A*B),B+s*(0.5*C-A*B),C+s*(A*B-0.5*C),endTime,t+s,s);
    }

}
