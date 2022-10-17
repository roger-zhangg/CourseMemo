
public class InclineSimulatorExample2 {

    public static void main (String[] argv)
    {
        // Make a new instance of the class.
        InclineSimulator sim = new InclineSimulator ();

        // Set mass and angle.
        sim.mass = 1;
        sim.angle = 30;
        double delta = 0.01;
        // Measure x(t) = distance moved along x-axis.
        Function dist = new Function ("dist");

        for (double t=1; t<=10; t+=1) {
            sim.run (t);
            double x = sim.getX ();
            sim.run (t+delta);
            double xt = sim.getX ();
            dist.add (t, (xt-x)/delta);
        }
        
        // Display result.
        dist.show ();
    }

}
