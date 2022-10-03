
public class FunctionComparison2 {

    public static void main (String[] argv)
    {
        // Initialize sum.
        double distance = 0;
        
        // Generate 50 values in the range [0,10]
        for (double x=0; x<=10; x+=0.1) {
            distance = distance + (-(3*x + 5) + (20));
        }

        System.out.println ("Distance: " + distance);
    }

}
