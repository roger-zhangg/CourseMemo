
public class RandomSequence2 {

    public static void main (String[] argv)
    {
        for (int n=1; n<=10; n++) {
            System.out.println ("Vn, n=" + n + ": " + computeV(n));
        }
    }

    static double computeV (int n)
    {
	    int counter = 0;
	    double sum = 0.0;
	    while(counter < n){
		    sum+=computeU(n);
		    counter++;
	    }
	    return sum/n;
	// INSERT YOUR CODE HERE
    }

    static double computeU (int n)
    {
        return RandTool.uniform ();
    }
    
}

