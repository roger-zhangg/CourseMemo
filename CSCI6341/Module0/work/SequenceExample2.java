import java.lang.Math;
public class SequenceExample2 {

    public static void main (String[] argv)
    {
        int n = 10;
	for (int i=1; i<=n; i++) {
	    System.out.println ("Cn, n=" + i + ": " + computeC(i));
	}
        
    }

    static double computeC (int n)
    {
	    return (Math.sin(n)/n);
        // INSERT YOUR CODE HERE.
    }
    
}
