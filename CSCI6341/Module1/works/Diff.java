
public class Diff {

    public static void main (String[] argv)
    {
	double d = 0.0001;
        Function F = new Function ("f");
        Function G = new Function ("g");
        // Compute for x-values in [0,10]
        for (double x=0; x<=10; x+=1) {
            // Compute f.
            double f = 3*x*x + 5;

            // Compute (f(x+d) - f(x)) / d
            double f_xd = 3 * (x+d)*(x+d) + 5;
            double g = (f_xd - f) / d;
            G.add (x, g);
            F.add (x, f);
            // Print.
            System.out.println ("x=" + x + "  g(x)=" + g);

	}
    Function.show (F,G);
    }

}

