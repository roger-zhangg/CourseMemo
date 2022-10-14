
public class BallDropSimulatorExercise2 {

    public static void main (String[] argv)
    {
        BallDropSimulator2 sim = new BallDropSimulator2 ();

        // Drop the ball from a height of 1000.
        double height = 1000;
        sim.run (height);
        
        double finalVelocity = sim.getV ();
        double start = 100;
        double end = 900;
        double testVelo = 0.0;
        double mid = 0.0;


        while ( Math.abs (finalVelocity - 2*testVelo) > 1 ) {
            mid = (start + end)/2;
            sim.run (mid);
            testVelo = sim.getV ();
            if (testVelo * 2 > finalVelocity){
                start = mid;
            }else{
                end = mid;
            }
            System.out.println ("Height required: " + mid +" "+ finalVelocity +" "+ testVelo);
        }

        System.out.println ("Height required: " + mid + " time requried "+sim.getT());
    }
    
}
