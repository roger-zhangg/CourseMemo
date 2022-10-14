public class SPTest {
    public static void main (String[] argv)
    {
        // Build an instance of the simulator. Note: no obstacles in this example.
        // The constructor: isAccel=false
        SimpleCarSimulator simulator = new SimpleCarSimulator (false,false);
        simulator.init (50, 50, 0, null);
        double v = 10;
        double theta = 1;
        double x = 50;
        double y =50;

        double t = 0;
        while (t<10) {
            t+=0.1;
            simulator.nextStep (v, theta, 0.1);
            x = simulator.getX();
            y = simulator.getY();

            // Display.
            System.out.println ("t=" + t + " x=" + x + " y=" + y+ " the="+theta);

        } //end-while

    }
}
