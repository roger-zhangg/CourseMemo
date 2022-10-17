
public class InclineVelocityExample {

    public static void main (String[] argv)
    {
        // Make a new instance of the class.
        InclineSimulator sim = new InclineSimulator ();

        // Set mass and angle.
        sim.mass = 1;
        sim.angle = 80;
        double delta = 0.01;
        
        // We'll make a Function object to store "velocity" readings.
        Function velocity = new Function ("velocity");

        for (double t=1; t<=10; t+=1) {
            // Run simulator up to time t.
            sim.run (t);
            // Get the velocity and put that in our function object.
            double v = sim.getV ();
            sim.run(t+delta);
            double vd = sim.getV();
            velocity.add (t, (vd-v)/delta);
        }
        
        // Display result.
        velocity.show ();
    }

}
