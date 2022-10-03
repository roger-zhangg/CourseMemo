
public class RandomSequence5 {

    public static void main (String[] argv)
    {
        for (int n=1; n<=10000; n+=1) {
	    double Wn = computeW (n);
	    System.out.println ("Wn, n=" + n + ": " + Wn);
	}
    }

    static double computeW (int n)
    {
	// INSERT YOUR CODE HERE

    }

    static double computeV (int n)
    {
	// INSERT YOUR CODE HERE

    }

    static double computeU (int n)
    {
        return RandTool.uniform ();
    }
    
}
