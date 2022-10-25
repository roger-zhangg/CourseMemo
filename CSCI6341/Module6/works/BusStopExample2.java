
public class BusStopExample2 {

    public static void main (String[] argv)
    {
        int tests = 10000;
        int counter = 0;
        int sucCounter = 0;
        while(counter < tests){
            counter++;
            double myArrivalTime = 10;
        BusStop busStop = new BusStop (true); 
        double arrivalTime = 0;
        double numBuses = -1;
        while (arrivalTime < myArrivalTime) {
                numBuses ++;
                busStop.nextBus ();
                arrivalTime = busStop.getArrivalTime ();
                //System.out.println ("arrs: " +numBuses + " " + arrivalTime);
            }
            if (numBuses >=5){
                sucCounter ++;
            }
        }
        
        System.out.println ("prob: " + (double)sucCounter/(double)counter);
    }
    
}
