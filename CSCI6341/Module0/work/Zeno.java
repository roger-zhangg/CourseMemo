
public class Zeno {

    public static void main (String[] argv)
    {
        int n = 10;
        System.out.println (sum(n));
    }

    static double sum (int n)
    {
        Function F = new Function ("f");
        int counter = 0;
        double sum = 0.0;
        double current_elem = 1.0;
        while (counter<n){
            sum += current_elem;
            counter++;
            current_elem*=0.5;
            F.add (counter, sum);
        }
        F.show ();
        return sum;
    }

}
