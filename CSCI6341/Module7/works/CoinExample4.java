// CoinExample4.java
//
// #flips needed for first heads

public class CoinExample4 {

    public static void main (String[] argv)
    {
        // "Large" # trials.
	double numTrials = 1000000;

	Coin coin = new Coin (0.1);           // Pr[heads]=0.1
    double[] counterArr = new double[1000];
    for (int t=0; t<numTrials; t++) {

            // INSERT YOUR CODE HERE
        int counter =1;
        while(coin.flip()==0){
            counter++;
        }
        counterArr[counter]++;


    }
    double sum = 0.0;
    for (int i =0;i<=999;i++){
        sum+= i*counterArr[i]/numTrials;
    }
    System.out.println("E= "+sum);

        // AND HERE
    }
 
}
