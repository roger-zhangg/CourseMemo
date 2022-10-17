
public class InclineSimulatorExample {

    public static void main (String[] argv)
    {
        // Make a new instance of the class.
        InclineSimulator sim = new InclineSimulator ();

        // Set mass and angle.
        sim.mass = 1;
        sim.angle = 30;
        double delta = 0.0001;
        
        // We'll measure distance travelled at t=1, t=2, ... and put
        // these values into a Function object.
        Function dist = new Function ("dist");

        for (double t=1; t<=10; t+=1) {
            double d = sim.run (t);
            double dt = sim.run(t+delta);
            dist.add (t, (dt-d)/delta);
            System.out.println ("t=" + t + "  d=" + d);
        }
        
        // Display result.
        dist.show ();
    }

}
