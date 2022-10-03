
import java.util.*;
import java.lang.Math;

public class FunctionExample3 {

    public static void main (String[] argv)
    {
        // Make a Function object and give it a name.
        Function F1 = new Function ("3x+5");
        Function F2 = new Function ("x2-2");
        Function F3 = new Function ("5/x2");
        Function F4 = new Function ("e-2x");

        for (double x=0; x<=10; x=x+0.1) {
            double f = 3*x+5;      
            if (f>10000000){
                f=0;
                //plot doen't work on inf
            }        
            // Feed the x,f(x) combinations into the object.
            F1.add (x, f);

            f = x*x-2;      
            if (f>10000000){
                f=0;
                //plot doen't work on inf
            }        
            // Feed the x,f(x) combinations into the object.
            F2.add (x, f);

            f = 5/(x*x);      
            if (f>10000000){
                f=0;
                //plot doen't work on inf
            }        
            // Feed the x,f(x) combinations into the object.
            F3.add (x, f);

            f = Math.exp(-2*x);      
            if (f>10000000){
                f=0;
                //plot doen't work on inf
            }        
            // Feed the x,f(x) combinations into the object.
            F4.add (x, f);
        }

        // Write to screen.
        // System.out.println (F1,F2,F3,F4);

        // Display.
        Function.show(F1,F2,F3,F4);
    }

}
