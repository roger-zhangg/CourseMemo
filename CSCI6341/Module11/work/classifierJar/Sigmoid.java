
public class Sigmoid {

    public static void main (String[] argv)
    {
        Function sigmoid = new Function ("sigmoid");
        for (double x=-10; x<=10; x+=1) {
            // INSERT YOUR CODE HERE:
            // double y = ...
            double y = 1/(1+Math.pow(Math.E,10*x));
            sigmoid.add (x,y);
        }
        sigmoid.show ();
    }

}
