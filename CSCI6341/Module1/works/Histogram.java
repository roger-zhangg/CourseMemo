import java.lang.Math;
public class Histogram {

    static int numBins = 10;

    public static void main (String[] argv)
    {
        int numSamples = 10000;
        int n = 10000;
        histogramForU (n, numSamples);
        histogramForV (n, numSamples);
        histogramForW (n, numSamples);
    }
    
    static double computeU (int n)
    {
        return RandTool.uniform ();
    }
    
    static double computeV (int n)
    {
	double sum = 0;
	double Vn = 0.0;
        for (int i=1; i<=n; i++) {
            double Un = computeU (i);
            sum = sum + Un;
            Vn = (1.0 / i) * sum;
           
        }
	return Vn;
        // INSERT YOUR CODE HERE.
    }

    static double computeW (int n)
    {

	return (Math.sqrt(n)*(computeV(n)-0.5));	
        // INSERT YOUR CODE HERE.
    }


    // You don't need to modify any code below this but it's worth reading.


    static void histogramForU (int n, int numSamples)
    {
        // Generate the data set
	double[] data = new double [numSamples];
        for (int k=0; k<numSamples; k++) {
            double u = computeU (n);
	    data[k] = u;
        }

        // Do histogram.
        System.out.println ("Histogram for U");
	makeHistogram (data);
    }

    static void histogramForV (int n, int numSamples)
    {
        // Generate the data set
	double[] data = new double [numSamples];
        for (int k=0; k<numSamples; k++) {
            double v = computeV (n);
	    data[k] = v;
        }

        // Do histogram.
        System.out.println ("Histogram for V");
	makeHistogram (data);

    }


    static void histogramForW (int n, int numSamples)
    {
        // Generate the data set
	double[] data = new double [numSamples];
        for (int k=0; k<numSamples; k++) {
            double w = computeW (n);
	    data[k] = w;
        }

        // Do histogram.
        System.out.println ("Histogram for W");
	makeHistogram (data);
    }
    
    static void makeHistogram (double[] data)
    {
        // Create space: one counter per interval.
        int[] bins = new int [numBins];

	// Find min/max of data.
	double min = Double.MAX_VALUE,  max = Double.MIN_VALUE;
	for (int i=0; i<data.length; i++) {
	    if (data[i] < min) min = data[i];
	    if (data[i] > max) max = data[i];
	}

	// Interval size.
	double interval = (max-min) / numBins;

	// Now count.
	for (int i=0; i<data.length; i++) {
	    double distFromMin = (data[i] - min);
	    int whichBin = (int) Math.floor (distFromMin / interval);
	    if ( (whichBin >= 0) && (whichBin < numBins) ) {
		bins[whichBin] ++;
	    }
	}

        // Print histogram.
        for (int b=0; b<numBins; b++) {
	    // Interval:
	    double leftEnd = min + b*interval;
	    double rightEnd = leftEnd + interval;
            System.out.printf ("  b=%4d: [%5.3f, %5.3f] %6d\n", b, leftEnd, rightEnd, bins[b]);
        }

    }
    
}
