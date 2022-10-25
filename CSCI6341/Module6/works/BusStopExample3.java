
public class BusStopExample3 {

    public static void main (String[] argv)
    {
        int tests = 10000;
        int counter = 0;
        int sucCounter = 0;
        double waitTime10 = 0;
        double waitTime20 = 0;
        while(counter < tests){
            counter++;
            double myArrivalTime = 10;
            double myArrivalTime2 = 30;
        BusStop busStop = new BusStop (true); 
        double arrivalTime = 0;
        double numBuses = -1;
            while (arrivalTime < myArrivalTime) {
                numBuses ++;
                busStop.nextBus ();
                arrivalTime = busStop.getArrivalTime ();
                //System.out.println ("arrs: " +numBuses + " " + arrivalTime);
            }
            waitTime10+=arrivalTime;
            while (arrivalTime < myArrivalTime2) {
                numBuses ++;
                busStop.nextBus ();
                arrivalTime = busStop.getArrivalTime ();
                //System.out.println ("arrs: " +numBuses + " " + arrivalTime);
            }
            waitTime20+=arrivalTime;

        }
        
        System.out.println ("avg wait 10 min: " + (double)waitTime10/(double)counter);
        System.out.println ("avg wait 20 min: " + (double)waitTime20/(double)counter);
    }
    
}
