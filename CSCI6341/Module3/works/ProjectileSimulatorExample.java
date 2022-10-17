
public class ProjectileSimulatorExample {

    public static void main (String[] argv)
    {
        // Make a new simulator object.
        ProjectileSimulator proSim = new ProjectileSimulator ();

        // We want to plot d vs. t
        Function dist = new Function ("distance");
        Function vel = new Function ("vel");
        double delta = 0.01;
        for (double t=0.1; t<=2.3; t+=0.1) {
            // mass=1, angle=37, initVel=20
            double d = proSim.run (1, 70, 20, t, 0.0001);
            double dt = proSim.run (1, 70, 20, t+delta, 0.0001);
            double dt2 = proSim.run (1, 70, 20, t+delta*2, 0.0001);
            vel.add(t,((dt2-dt)/delta - (dt-d)/delta)/delta);
            dist.add (t, (dt-d)/delta);

	    // Use s = 0.01 to get distance at t+s
	    
        }

        // Display.
        Function.show (vel,dist);
    }

}
