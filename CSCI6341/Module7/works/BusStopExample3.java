
public class BusStopExample3 {

    public static void main (String[] argv)
    {
        double myArrivalTime = 2;
        int numTrial = 100000;
        int numSucc = 0;
        for (int i =0;i<numTrial;i++){
            BusStop busStop = new BusStop (true); 
        double arrivalTime = 0;
        double numBuses = -1;
        while (arrivalTime < myArrivalTime) {
            numBuses ++;
            busStop.nextBus ();
            arrivalTime = busStop.getArrivalTime ();
        }
        if (numBuses==3){
            numSucc++;
        }
        }
        
        System.out.println ("Prob: " + (double)numSucc/numTrial);
    }
    
}
