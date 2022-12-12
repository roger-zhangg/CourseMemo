
public class BracketSearch2 {

    public static void main (String[] argv)
    {
        bracketSearch (0, 100);
    }
    

    static void bracketSearch (double a, double b)
    {
        int numEvals = 0;
        int M = 6;
        int N = 0;
        double stop = 0.001;
        double bestx = a;
        double bestf = computef (bestx);
        double prevBestf = 0;
        double epsilon = 0.1;

        // INSERT YOUR CODE HERE
        for (int i=0; i<1000; i++) {
            double delta = (b-a) / M;
            for (double x=a+delta; x<=b; x+=delta) {
                double f = computef (x);
                if (f < bestf) {
                    bestf = f;
                    bestx = x;
                }
            }
            if (Math.abs(bestf-prevBestf)<epsilon){
                System.out.println ("N=" + N + " bestx=" + bestx + " bestf=" + bestf + " prevBestf=" + prevBestf);
                System.exit(0);
            }
            a = bestx - delta;
            b = bestx + delta;
            prevBestf = bestf;
        }

    }


    static double computef (double x) 
    {
        double f = 2.5 + (x - 4.71) * (x - 4.71);
        return f;
    }

}
