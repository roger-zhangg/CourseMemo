
public class UniformMeanExample2 {

    public static void main (String[] argv)
    {
        double numTrials = 1000;                // n=10
        Function d1 = new Function("first");
        // The first "run": set the seed to some value.
        RandTool.setSeed (1234);
        double total = 0;
        for (int i=1; i<=numTrials; i++) {
            double x = RandTool.uniform (); 
            total += x;
            System.out.println ("i=" + i + "  S_i/i=" + (total/i));
            d1.add(i,total/i);
        }
        double avg = total / numTrials; 
        System.out.println ("Avg: " + avg);   


        // The second "run": set the seed to some other value and repeat.
        RandTool.setSeed (4321);
        total = 0;
        Function d2 = new Function("second");
        for (int i=1; i<=numTrials; i++) {
            double x = RandTool.uniform (); 
            total += x;
            d2.add(i,total/i);
            System.out.println ("i=" + i + "  S_i/i=" + (total/i));
        }
        avg = total / numTrials; 
        System.out.println ("Avg: " + avg);   
        Function.show(d1,d2);
    }

}
