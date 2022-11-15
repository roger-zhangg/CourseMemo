
public class UniformExample4 {

    public static void main (String[] argv)
    {
        int numTrials = 100000;
        DensityHistogram hist = new DensityHistogram (0,2, 20);
        double sum = 0;
        double sumx = 0;
        for (int n=0; n<numTrials; n++) {
            // INSERT YOUR CODE HERE
            double x ;
            double g;
            x = RandTool.uniform();
            g = (x-0.5)*(x-0.5);
            sum+=g;
            sumx+=x;
            hist.add(g);
        }
        System.out.println ("Avg x^2: " + sum/numTrials);
        System.out.println ("Avg x: " + sumx/numTrials);
        hist.display ();
    }
    
}
