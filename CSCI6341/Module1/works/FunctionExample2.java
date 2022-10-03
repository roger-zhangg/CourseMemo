
import java.util.*;

public class FunctionExample2 {

    public static void main (String[] argv)
    {
        System.out.println (" x   f(x)");         // Table header
        System.out.println ("---------");
        for (double x=-10; x<=1; x=x+1) {
            double f = 1/(x-2);                 // Compute function.
            System.out.println (x + "  " + f);    // Print result.
        }
    }

}
