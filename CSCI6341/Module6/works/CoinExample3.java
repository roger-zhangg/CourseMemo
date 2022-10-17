
public class CoinExample3 {

    public static void main (String[] argv)
    {
        // "Large" # trials.
	double numTrials = 1000000;

        // Count # times desired outcome shows up.
	double numSuccesses = 0;

	Coin coin = new Coin (0.6);           // Pr[heads]=0.6

	// INSERT YOUR CODE HERE, similar to CoinExample2.java,
	// but to solve this problem.
	for (int n=0; n<numTrials; n++) {
            // Notice what we're repeating for #trials: the two flips.
		int counter = 0;
	    for (int i = 0;i<3;i++){
	    	if (coin.flip() == 1){
	    		counter++;
	    	}
	    }

	    if ( counter == 2) {
                // If either resulted in heads, that's a successful outcome.
		numSuccesses ++;
	    }
	}


        // Estimate. (No need to cast into double's)
	double prob = numSuccesses / numTrials;

	System.out.println ("Pr[Exactly 2 h in 3 flips]=" + prob + "  theory=" + 0.432);
    }
 
}
