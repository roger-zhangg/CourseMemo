
public class FunctionComparison4 {

    public static void main (String[] argv)
    {
        // Initialize sum.
        double sum= 0;

        // Generate 50 values in the range [0,10]
        for (double x=0; x<=5; x+=0.05) {
            double f = 3*x + 5;
            double g = 3*x + 10;
            sum += Math.abs (f - g);
        }

        // Compute the average distance, multiplied by length of interval:
        double distance = (5-0) * sum / 100;

        System.out.println ("Distance f to g: " + distance);
    }

}
