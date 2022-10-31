
public class GaussianCDF {


    public static void main (String[] argv)
    {
        Function F = makeGaussianCDF ();
        System.out.println("Pr[0<X≤2]:"+(F.get(2)-F.get(0)));
        System.out.println("Pr[X>0]:"+(F.get(F.maxX())-F.get(0)));

        double delta = 0.01;
        int interval = 100;
        Function D = new Function("derivative");
        double isize = (F.maxX - F.minX)/interval;
        for (int i=0;i<interval;i++){
            double current_p = F.minX+isize * i;
            double d = (F.get(current_p+delta)-F.get(current_p))/(delta);
            D.add(current_p,d);

        }

        F.show ();
        D.show();
    }

    static Function makeGaussianCDF ()
    {
	double a = -2, b = 2;
	int M = 50;                   // Number of intervals.
	double delta = (b-a) / M;     // Interval size.

	double[] intervalCounts = new double [M];
	double numTrials = 1000000;

	for (int t=0; t<numTrials; t++) {
            // Random sample:
	    double y = RandTool.gaussian ();
            // Truncate:
            if (y < a) {
                y = a;
            }
            if (y > b) {
                y = b;
            }
            
            // Find the right interval:
            int k = (int) Math.floor ((y-a) / delta);
            // Increment the count for every interval above and including k.
            if (k < 0) {
                System.out.println ("k=" + k + " y=" + y + " (y-a)=" + (y-a));
            }
            
            for (int i=k; i<M; i++) {
                intervalCounts[i] ++;
            }
	}

	// Now compute probabilities for each interval.
	double[] cdf = new double [M];
	for (int k=0; k<M; k++) {
	    cdf[k] = intervalCounts[k] / numTrials;
	}

        // Build the CDF. Use mid-point of each interval.
        Function F = new Function ("Uniform cdf");
	for (int k=0; k<M; k++) {
	    double midPoint = a + k * delta + delta/2;
            F.add (midPoint, cdf[k]);
	}

        return F;
    }

}


