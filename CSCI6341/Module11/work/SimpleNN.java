
import java.util.*;

public class SimpleNN {

    public static void main (String[] argv)
    {
	setUp ();

        DataGenerator dGen = new DataGenerator ();

	// Training phase:
        int M = 30;
        for (int m=0; m<M; m++) {
            dGen.generateSample ();
	    train (dGen.getX(), dGen.getClassNum());
        }
	
	// Testing phase.
        double L = 1000;
	double numFailures = 0;
        for (int k=0; k<L; k++) {
            dGen.generateSample ();
	    int x = dGen.getX(); 
	    int c = dGen.getClassNum();
	    int algClass = classify (x);
	    if (c != algClass) {
		numFailures ++;
	    }
        }
	double probError = numFailures / L;
	System.out.println ("Pr[Error]=" + probError);
    }

    static ArrayList<Integer>[] data;

    static void setUp ()
    {
	data = new ArrayList [2];
	data[0] = new ArrayList<Integer> ();
	data[1] = new ArrayList<Integer> ();
    }

    static void train (int x, int c)
    {
	data[c].add (x);
    }

    static int classify (int x)
    {
	// Find closest in each class.
	double[] closestDist = new double [2];
	for (int i=0; i<2; i++) {
	    closestDist[i] = Double.MAX_VALUE;
	    for (int d: data[i]) {
		double dist = Math.abs (x - d);
		if (dist < closestDist[i]) {
		    closestDist[i] = dist;
		}
	    }
	}

	// ALTERNATIVE: if (closestDist[0] <= closestDist[1]) {
	if (closestDist[0] <= closestDist[1]) {
	    return 0;
	}
	else {
	    return 1;
	}
    }

}
