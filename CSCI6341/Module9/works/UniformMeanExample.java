
public class UniformMeanExample {

    public static void main (String[] argv)
    {
        Function df = new Function("f");
        double numTrials = 10;                // n=10
        double total = 0;
        for (int i=1; i<=numTrials; i++) {
            double x = RandTool.uniform ();   // X_i = outcome of i-th call to uniform()
            total += x;
            df.add(i,total/i);
        }
        double avg = total / numTrials;       // S_n / n
                                              // = (X_1 + ... + X_n) / n
        System.out.println ("Avg: " + avg);   
        df.show();
    }

}
