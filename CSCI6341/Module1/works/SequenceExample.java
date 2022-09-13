
public class SequenceExample {

    public static void main (String[] argv)
    {
        int n = 10;
        System.out.println ("An, n=" + n + ": " + computeA(n));
        n = 10000;
        System.out.println ("An, n=" + n + ": " + computeA(n));
        
    }

    static double computeA (int n)
    {
        // INSERT YOUR CODE HERE.
	int counter = 0;
	double result = 1.0;
	while (counter < n){
	    result *= (1.0+1.0/n);
	    counter ++;
	}
	return result;
    }
    
}
