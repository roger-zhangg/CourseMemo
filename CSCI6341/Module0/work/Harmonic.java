
public class Harmonic {

    public static void main (String[] argv)
    {
        int n = 10000;
        System.out.println (sumDouble(n));
        System.out.println (sumFloat(n));
    }

    static double sumDouble (int n)
    {
        Function F = new Function ("f");
        int counter = 1;
	double sum = 0.0;
	double curr = 1.0;
	while(counter <= n){
		sum+=curr/counter;
		counter++;
        F.add (counter, sum);
	}
    F.show ();
	return sum;
    }

    static float sumFloat (int n)
    {
        // INSERT YOUR CODE HERE.
	int counter = 1;
        float sum = 0.0f;
        float curr = 1.0f;
        while(counter <= n){
                sum+=curr/counter;
                counter++;
        }
        return sum;
    }
    
}
