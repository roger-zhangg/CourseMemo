
public class UniformExample {

    public static void main (String[] argv)
    {
        int numTrials = 100000;
        DensityHistogram hist = new DensityHistogram (0,2, 20);
        double sum = 0;
        double sumx = 0;
        for (int n=0; n<numTrials; n++) {
            // INSERT YOUR CODE HERE
            double x ;
            x = RandTool.uniform();
            sum+=x*x;
            sumx+=x;
            hist.add(x*x);
        }
        System.out.println ("Avg x^2: " + sum/numTrials);
        System.out.println ("Avg x: " + sumx/numTrials);
        hist.display ();
    }
    
}
