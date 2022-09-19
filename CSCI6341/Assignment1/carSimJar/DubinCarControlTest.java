public class DubinCarControlTest {
    public static void main (String[] argv) 
    {
        // Build an instance of the simulator. Note: no obstacles in this example.
        // The constructor: isAccel=false
        DubinCarSimulator simulator = new DubinCarSimulator (false);
        simulator.init (50, 50, 0, null);

        // This example moves the car from (50,50) to (550,50).
        double x = 50;
	double start = 50;
        while (x < 550) {
	    double b = 7.8;
	    double interval = 75;
            // Pass the controls (v1=10, v2=10) to the simulator. DeltaT=0.1.
            if (x<=start+interval){
		simulator.nextStep (b, 10, 0.1);
	    }
	    if (x>start+interval&&x<=start+3*interval){
		simulator.nextStep (10,b, 0.1);
	    }
	    if (x>start+3*interval&&x<start+4*interval){
		simulator.nextStep (b,10, 0.1);
	    }
	    double theta = simulator.getTheta();
	    if (x>=start+4*interval){
		    if (theta > 0){
			    simulator.nextStep (10,9.95, 0.1);
		    }else if (theta <0){
		    simulator.nextStep (9.95,10, 0.1);
		    }else{
		    simulator.nextStep (10,10, 0.1);
	            }
	    }
            // Now extract the new location and other info.
            double t = simulator.getTime ();
            x = simulator.getX ();
            double y = simulator.getY ();
	    if (x>=150 && x<=250 && y>=0 && y<=100){
		    System.out.println ("hit");
	    }

            // Display.
            System.out.println ("t=" + t + " x=" + x + " y=" + y+ " the="+theta);

        } //end-while
        
    }
}
