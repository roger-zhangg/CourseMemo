
import java.text.*;

public class ConcCalc {

    public static void main (String[] argv)
    {
	// For printing/formatting:
	DecimalFormat df = new DecimalFormat ("###.###");

	// Rate parameters:
	double K_ab = 1.0;
	double K_c = 0.5;

	// Initial values:
	double A = 3.0;  
	double B = 2.0;
	double C = 1.5;
	double A_pre = A;
	double B_pre = B;
	// Initialize time:
	double t = 0;
	
	// Set our time increment:
	double s = 0.01;

	// Set final time:
	double endTime = 1.0;
	Function FA = new Function ("a");
	Function FB = new Function ("b");
	Function FC = new Function ("c");
	// Compute.
	while (t < endTime) {
		A_pre = A;
		B_pre = B;
	    // Compute the new values at time t+s:
	    A = A + 0.01*(0.5*C-A*B);    // INSERT YOUR CODE HERE
	    B = B + 0.01*(0.5*C-A_pre*B);    // INSERT YOUR CODE HERE
	    C = C + 0.01*(A_pre*B_pre - 0.5*C);    // INSERT YOUR CODE HERE
	    FA.add(t,A);
	    FB.add(t,B);
	    FC.add(t,C);
	    t = t + s;
	    // Print out the values to screen:
	    System.out.println ("At time t=" + df.format(t) + ":  A(t)=" + df.format(A) + "   B=" + df.format(B) + "   C(t)=" + df.format(C));

	    // Change t, and repeat.
	    
	}
	Function.show(FA,FB,FC);

    }

}
