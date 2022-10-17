
public class CardExample3 {

    public static void main (String[] argv)
    {
	double numTrials = 100000;
	double numSuccesses = 0;
	double numTotal = 0;

	for (int n=0; n<numTrials; n++) {

	    CardDeck deck = new CardDeck ();
	    int c1 = deck.drawWithoutReplacement ();
	    int c2 = deck.drawWithoutReplacement ();
	    if (!deck.isClub(c2)){
	    	continue;
	    }
	    numTotal++;
	    if (deck.isDiamond(c1)){
	    	numSuccesses++;
	    }
	    // INSERT YOUR CODE HERE
	}

	System.out.println ("Pr[c1=diamond given c2=club]=" + numSuccesses/numTotal + "  theory=" + (13.0/51.0));

    }

}
