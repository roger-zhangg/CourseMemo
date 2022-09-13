
public class Zeno {

    public static void main (String[] argv)
    {
        int n = 100;
        System.out.println (sum(n));
    }

    static double sum (int n)
    {
	int counter = 0;
	double sum = 0.0;
	double init = 0.5;
        while (counter < n) {
		counter++;
		sum+=init;
		init = init/2;// Write your code here.
	}
	return sum;
    }

}
