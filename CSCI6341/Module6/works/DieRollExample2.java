
public class DieRollExample2 {

    public static void main (String[] argv)
    {
	double numTrials = 1000000;
	double numSuccesses = 0;

	Die die = new Die ();

	for (int n=0; n<numTrials; n++) {

	    // WRITE YOUR CODE HERE
	    if (die.roll () %2 == 0){
	    	continue;
	    }
	    if (die.roll () %2 ==1){
	    	numSuccesses++;
	    }

	}

	double prob = numSuccesses / numTrials;

	System.out.println ("Pr[odd+even]=" + prob + "  theory=" + 0.25);
    }

}

