
public class ExponentialCDF {


    public static void main (String[] argv)
    {
        Function F = makeExponentialCDF ();
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

    static Function makeExponentialCDF ()
    {
    	int M = 50;   
        double[] cdf = new double [M];
        double a = 0, b = 3;
	                // Number of intervals.
	double delta = (b-a) / M;     // Interval size.

	double[] intervalCounts = new double [M];
	double numTrials = 1000000;
	double non_trail = 0;

	for (int t=0; t<numTrials; t++) {
            // Random sample:
	    double y = RandTool.exponential (0.5);
            // Find the right interval:
	    if (y>3){
	    			non_trail++;
            		continue;
            	}
            int k = (int) Math.floor ((y-a) / delta);
            // Increment the count for every interval above and including k.
            for (int i=k; i<M; i++) {
            	
                intervalCounts[i] ++;
            }

	}

	// Now compute probabilities for each interval.
	
	for (int k=0; k<M; k++) {
	    cdf[k] = intervalCounts[k] / (numTrials-non_trail);
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

