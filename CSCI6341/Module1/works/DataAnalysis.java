
public class DataAnalysis {

    public static void main (String[] argv)
    {
        // Make a Function object.
        Function F = new Function ("mystery");

        // Put the data in.
        F.add (1, 8);
        F.add (2, 11);
        F.add (3, 14);
        F.add (4, 17);
        F.add (5, 20);

        // Display it.
        F.show ();
    }

}
