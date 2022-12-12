
public class QueueGradientDescent {

    static double mu1 = 1;
    static double mu2 = 1.5;
    static double lambda = 0.9;

    public static void main (String[] argv)
    {
        gradientDescent (0, 1);
    }
    
    static void gradientDescent (double a, double b)
    {
        double x = a;
        double alpha = 0.01;
        double epsilon = 0.0001;
        double deriv = 2*epsilon;
        while (Math.abs(deriv) > epsilon) {
            deriv = computeDeriv(x);
            x = x - alpha * deriv;
        }
        System.out.println ("Gradient descent: x*=" + x + " f(x*)=" + computef(x));
    }


    static double computef (double x) 
    {
        // INSERT YOUR CODE HERE.
        return x/(mu1-lambda*x) + (1-x)/(mu2-lambda*(1-x));
    }

    static double computeDeriv (double x)
    {   
        return (computef(x+0.01) -computef(x))/0.01;
        // INSERT YOUR CODE HERE.
    }

}
