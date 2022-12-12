
public class DataExample3 {

    public static void main (String[] argv)
    {
        DataGenerator dGen = new DataGenerator ();

        double numTrials = 100000;
        double numMistakes = 0;
        double threeOccurs = 0;
        for (int n=0; n<numTrials; n++) {
            dGen.generateSample ();
            int x = dGen.getX ();
            if (x == 3) {                       // Focus on x=3.
                int c = dGen.getClassNum ();    // The true class that x belongs to.
                int algResult = classify (x);   // What the algorithm classifies x as.
                if (algResult != c) {
                    numMistakes ++;
                }
                threeOccurs ++;
            }
            
        }
        double probError = numMistakes / threeOccurs;
        System.out.println ("Pr[E3]=" + probError);
    }

    static int classify (int x)
    {
        DataGenerator dGen = new DataGenerator ();
        if (dGen.getProbXGivenC (x,0)<dGen.getProbXGivenC (x,1)){
            return 1;
        }
        return 0;
        // INSERT YOUR CODE HERE.
    }

}
