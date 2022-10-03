
public class SinMaximum {

    public static void main (String[] argv)
    {
        // Initial guess: max occurs at 0, and has value 0.
        double bestX = 0;
        double min = Math.cos (bestX);

        // Now try values in the range [0,2*pi]
        for (double x=0; x<=2*Math.PI; x+=0.1) {
            double f = Math.cos (x);
            if (f < min) {
                min = f;
                bestX = x;
            }
        }

        // Print result.
        System.out.println ("Max value " + min + " occurred at x=" + bestX);
    }

}
