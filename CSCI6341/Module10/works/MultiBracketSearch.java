
public class MultiBracketSearch {

    public static void main (String[] argv)
    {
        bracketSearch (0, 10, 0, 10);
    }
    

    static void bracketSearch (double a1, double b1, double a2, double b2)
    {
        int numEvals = 0;
        int M = 6;
        int N = 4;
        double bestx1=a1, bestx2=a2;
        double bestf = computef (bestx1,bestx2);
        for (int i=0; i<N; i++) {
            double delta1 = (b1-a1) / M;
            double delta2 = (b2-a2)/M;
            for (double x=a1+delta1; x<=b1; x+=delta1) {
                for (double y=a2+delta2; y<=b2; y+=delta2) {
                    double f = computef (x,y);
                    if (f < bestf) {
                        bestf = f;
                        bestx1 = x;
                        bestx2 = y;
                    }
                    numEvals++;
                }
                
            }
            a2 = bestx2 - delta2;
                b2 = bestx2 + delta2;
                a1 = bestx1 - delta1;
                b1 = bestx1 + delta1;

            
        }
        // INSERT YOUR CODE HERE

        System.out.println ("Bracketing search: x1=" + bestx1 + " x2=" + bestx2 + " numFuncEvals=" + numEvals);
    }


    static double computef (double x1, double x2)
    {
        return Math.pow((x1-4.71),2)+Math.pow((x2-3.2),2)+2*Math.pow((x1-4.71)*(x2-3.2),2);
        // INSERT YOUR CODE HERE
    }

}
