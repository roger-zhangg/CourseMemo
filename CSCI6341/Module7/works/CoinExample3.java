// CoinExample3.java
//
// Estimate Pr[X=k] for 3-coin-flip example.

public class CoinExample3 {

    public static void main (String[] argv)
    {
        // "Large" # trials.
	double numTrials = 1000000;

	Coin coin = new Coin (0.6);           // Pr[heads]=0.6
    double[] counterArr = new double[4];
	for (int t=0; t<numTrials; t++) {

            // INSERT YOUR CODE HERE
        int counter =0;
        for (int i = 0;i<3;i++){
            if (coin.flip() ==1){
                counter++;
            }
        }
        counterArr[counter]++;


	}
    double sum = 0.0;
    for (int i =0;i<=3;i++){
        sum+= i*counterArr[i]/numTrials;
    }
    System.out.println("E= "+sum);

        // AND HERE

    }
 
}
