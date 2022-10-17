public class ProjectileSimulatorExample2 {

    public static void main (String[] argv)
    {
        // Make a new simulator object.
        ProjectileSimulator proSim = new ProjectileSimulator ();

        // We want to plot d vs. t along x-axis.
        Function dist = new Function ("distance");
        Function velo = new Function ("velo");
        double delta = 0.001;

        for (double t=0.1; t<=2.3; t+=0.1) {
            // mass=1, angle=37, initVel=20, s=0.01
            proSim.run (1, 37, 20, t, 0.01);
            // After the simulation is run, get the final x-value.
            double x = proSim.getY ();
            proSim.run (1, 37, 20, t+delta, 0.01);
            // After the simulation is run, get the final x-value.
            double xt = proSim.getY ();
            dist.add(t,x);
            velo.add (t, (xt-x)/delta);
        }

        // Display.
        Function.show (dist,velo);
    }

}
    