
public class DiscreteGenExample {

    public static void main (String[] argv)
    {
        // INSERT YOUR CODE FOR TESTING (HISTOGRAM) HERE.
        DensityHistogram dh = new DensityHistogram(0.0,4.0,4);
        for (int i = 0; i< 10000;i++){
            dh.add(generateNext());
        }
        dh.display();
    }
    

    static int generateNext ()
    {
        // INSERT YOUR CODE HERE.
        double U = RandTool.uniform();
        if (U<0.064){
            return 0;
        }else if (U<0.064+0.288){
            return 1;
        }else if (U<0.064+0.288+0.432){
            return 2;
        }else{
            return 3;
        }
    }

}
