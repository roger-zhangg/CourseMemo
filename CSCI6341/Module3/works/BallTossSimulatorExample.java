
public class BallTossSimulatorExample {

    public static void main (String[] argv)
    {
        BallTossSimulator sim = new BallTossSimulator ();
        
        Function dist = new Function ("distance");
        double delta = 0.01;
        for (double t=1; t<=10; t+=1) {
            double d = sim.run (t, 50);
            double dt = sim.run (t+delta, 50);
            dist.add (t, (dt-d)/delta);
        }

        dist.show ();
    }
    
}
