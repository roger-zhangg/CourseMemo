
public class BallDropSimulatorExample {

    public static void main (String[] argv)
    {
        BallDropSimulator sim = new BallDropSimulator ();
        
        Function dist = new Function ("distance");
        double delta = 0.01;

        for (double t=1; t<=10; t+=1) {
            double d = sim.run (t, 1000);
            double dt = sim.run (t+delta, 1000);
            dist.add (t, (dt-d)/delta);
        }
        
        dist.show ();
    }
    
}
