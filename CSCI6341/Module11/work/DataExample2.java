
public class DataExample2 {

    static DataGenerator dGen = new DataGenerator ();

    public static void main (String[] argv)
    {
        double numTrials = 100000;
        double numMistakes = 0;
        for (int n=0; n<numTrials; n++) {
            dGen.generateSample ();
            int x = dGen.getX ();
            int c = dGen.getClassNum ();    // The true class that x belongs to.
            int algResult = classify (x);   // What the algorithm classifies x as.
            if (algResult != c) {
                numMistakes ++;
            }
            
        }
        double probError = numMistakes / numTrials;
        System.out.println ("Pr[E]=" + probError);
    }

    static int classify (int x)
    {
        if (dGen.getProbXGivenC (x,0)<dGen.getProbXGivenC (x,1)){
            return 1;
        }
        return 0;
        // INSERT YOUR CODE HERE. Use the dGen.probXGivenC (x,c) method
	// in DataGenerator to get Pr[X=x | C=c], and then use those
	// two values in making the decision.
    }

}
