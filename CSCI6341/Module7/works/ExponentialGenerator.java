

public class ExponentialGenerator {    

    public static void main (String[] argv)
    {
        int numTrials = 100000;

        // Exponential parameter.
        double gamma = 4.0;

        // Make a density histogram. NOTE: change code in PropHistogram accordingly.
        PropHistogram hist = new PropHistogram (0, 5, 20);
        for (int n=0; n<numTrials; n++) {
            double x = generateNext (gamma);
            hist.add (x);
        }

        hist.display ();
    }
    

    static double generateNext (double gamma)
    {
        // INSERT YOUR CODE HERE.
        double U = RandTool.uniform();
        //System.out.println(Math.log(U/gamma)/-gamma);
        return Math.log(U/gamma)/-gamma;
    }

}
