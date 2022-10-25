
public class BusStopExample4 {

    public static void main (String[] argv)
    {
        int tests = 10000;
        int counter = 0;
        int sucCounter = 0;
        double b2count = 0;
        double b2givenb1count = 0;
        while(counter < tests){
            counter++;
            int bus0Count = 0;
            int bus05Count = 0;
            double arrivalTime = 0;
            BusStop busStop = new BusStop (false); 
            while (arrivalTime < 1) {
                busStop.nextBus ();
                arrivalTime = busStop.getArrivalTime ();
                if (arrivalTime <= 0.5){
                    bus0Count++;
                }else if (arrivalTime <=1){
                    bus05Count++;
                }
                //System.out.println ("arrs: " +numBuses + " " + arrivalTime);
            }
            if(bus05Count == 1){
                b2count++;
                if (bus0Count == 1){
                    b2givenb1count ++;
                }
            }

        }
        
        System.out.println ("Pr[B2]: " + (double)b2count/(double)counter);
        System.out.println ("Pr[B2|B1] " + (double)b2givenb1count/(double)counter);
    }
    
}
