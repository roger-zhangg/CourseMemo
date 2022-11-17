
public class UniformCDF2 {


    public static void main (String[] argv)
    {
        Function F = makeUniformCDF ();
        double a = 0, b = 1;
			int M = 50;                   // Number of intervals.
			double delta = (b-a) / M;     // Interval size.
		double x = 0;
		double x1 = delta;
		double sum = 0;
		for(int i =0;i<M;i++){
			sum+=(x+x1)*0.5*(F.get(x1)-F.get(x));
			x1+=delta;
			x+=delta;
		}
        System.out.println (" ex: " + sum);
        // INSERT YOUR CODE HERE
    }

    static Function makeUniformCDF ()
    {
	double a = 0, b = 1;
	int M = 50;                   // Number of intervals.
	double delta = (b-a) / M;     // Interval size.

	double[] intervalCounts = new double [M];
	double numTrials = 1000000;

	for (int t=0; t<numTrials; t++) {
            // Random sample:
	    double y = RandTool.uniform ();
            // Find the right interval:
            int k = (int) Math.floor ((y-a) / delta);
            // Increment the count for every interval above and including k.
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

