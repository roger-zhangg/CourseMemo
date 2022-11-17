// CoinExample2.java
//
// Author: Rahul Simha
// Mar, 2008.
//
// Estimate Pr[3 heads in 5 flips]

public class CoinExample2 {

    public static void main (String[] argv)
    {
        // "Large" # trials.
	double numTrials = 1000000;

        // Count # times desired outcome shows up.
	double numSuccesses = 0;

	Coin coin = new Coin (0.6);           // Pr[heads]=0.6

	for (int n=0; n<numTrials; n++) {
		int counter = 0;
		for (int c = 0;c<10;c++){
			if (coin.flip()==1){
				counter ++;
			}
            // INSERT YOUR CODE HERE
		}
		if (counter == 3){
			numSuccesses ++;
		}

	}

        // Estimate. (No need to cast into double's)
	double prob = numSuccesses / numTrials;

	System.out.println ("Pr[3 H in 10 flips]=" + prob);
    }
 
}
