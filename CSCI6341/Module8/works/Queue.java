// Queue.java
//
// Author: Rahul Simha
// Mar, 2008
//
// A single-server queue.


import java.util.*;
import java.text.*;

public class Queue {
    
    // Avg time between arrivals = 1.0, avg time at server=1/0.75.
    double arrivalRate = 0.75;
    double serviceRate = 1.0;
    double totalM = 0.0;
    double avgArrTime;
    double totalZero = 0.0;
    //DensityHistogram darr = DensityHistogram(0.0,10.0,10);
    //DensityHistogram marr = DensityHistogram(0.0,10.0,20);
    DensityHistogram darr;
    DensityHistogram marr;

    // A data structure to store customers.
    LinkedList<Customer> queue;

    // A data structure for simulation: list of forthcoming events.
    PriorityQueue<Event> eventList;

    // The system clock, which we'll advance from event to event.
    double clock;
    double length = 0;
    // Statistics.
    int numArrivals = 0;                    // How many arrived?
    int numDepartures;                      // How many left?
    double totalWaitTime, avgWaitTime;      // For time spent in queue.
    double totalSystemTime, avgSystemTime;  // For time spent in system.


    void init ()
    {
         darr = new DensityHistogram(0.0,10.0,20);
         marr = new DensityHistogram(0.0,10.0,10);
        queue = new LinkedList<Customer> ();
        eventList = new PriorityQueue<Event> ();
        clock = 0.0;
        avgArrTime= 0.0;
        numArrivals = numDepartures = 0;
        totalWaitTime = totalSystemTime = 0.0;
        scheduleArrival ();
    }


    void simulate (int maxCustomers)
    {
        init ();

        while (numArrivals < maxCustomers) {
            Event e = eventList.poll ();
            clock = e.eventTime;
            if (e.type == Event.ARRIVAL) {
                handleArrival (e);
            }
            else {
                handleDeparture (e);
            }
        }

        stats ();
        System.out.println ("avg arrival time= "+avgArrTime/1000);
        System.out.println ("avg service rate= "+1/(avgSystemTime-avgWaitTime));
        System.out.println (" M= "+totalM/1000);
        System.out.println (" M/d= "+totalM/1000/avgSystemTime);
        System.out.println (" server free prob= "+totalZero/1000);
    }


    void handleArrival (Event e)
    {
    totalM+=length;
    marr.add(length);
	numArrivals ++;
    
    if (length ==0){
        totalZero++;
    }
    length++;
	queue.add (new Customer (clock));
	if (queue.size() == 1) {
	    // This is the only customer => schedule a departure.
	    scheduleDeparture ();
	}
	scheduleArrival ();
    }


    void handleDeparture (Event e)
    {
    Customer c = queue.removeFirst ();
    double timeInSystem = clock - c.arrivalTime;
    darr.add(timeInSystem);
	numDepartures ++;
	
    length--;

        // This is the time from start to finish for this customer:
        

        // Maintain total (for average, to be computed later).
	totalSystemTime += timeInSystem;

	if (queue.size() > 0) {
	    // There's a waiting customer => schedule departure.
	    Customer waitingCust = queue.get (0);
            // This is the time spent only in waiting:
            double waitTime = clock - waitingCust.arrivalTime; 
	    // Note where we are collecting stats for waiting time.
	    totalWaitTime += waitTime;
	    scheduleDeparture ();
	}
    }


    void scheduleArrival ()
    {
	// The next arrival occurs when we add an interrarrival to the the current time.
	double nextArrivalTime = clock + randomInterarrivalTime();
	eventList.add (new Event (nextArrivalTime, Event.ARRIVAL));
    }
    

    void scheduleDeparture ()
    {
	double nextDepartureTime = clock + randomServiceTime ();
	eventList.add (new Event (nextDepartureTime, Event.DEPARTURE));
    }


    double randomInterarrivalTime ()
    {
        double arrTime= exponential (arrivalRate);
        avgArrTime+=arrTime;
	return arrTime;
    }


    double randomServiceTime ()
    {
	return exponential (serviceRate);
    }


    double exponential (double gamma)
    {
        return (1.0 / gamma) * (-Math.log(1.0 - RandTool.uniform()));
    }

    void stats ()
    {
	if (numDepartures == 0) {
	    return;
	}
	avgWaitTime = totalWaitTime / numDepartures;
	avgSystemTime = totalSystemTime / numDepartures;
    marr.display();
        darr.display();
    }


    public String toString ()
    {
        String results = "Simulation results:";
        results += "\n  numArrivals:     " + numArrivals;
        results += "\n  numDepartures:   " + numDepartures;
        results += "\n  avg Wait:        " + avgWaitTime;
        results += "\n  avg System Time: " + avgSystemTime;
        return results;
    }
    
    

    ///////////////////////////////////////////////////////////////////////
    // main

    public static void main (String[] argv)
    {
        Queue queue = new Queue ();
        queue.simulate (1000);
        System.out.println (queue);
    } 

}


// Class Customer (one instance per customer) stores whatever we 
// need for each customer. Since we collect statistics on waiting 
// time at the time of departure, we need to record when a 
// customer arrives.

class Customer {
    double arrivalTime;
    public Customer (double arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }
}


// Class Event has everything we need for an event: the type of
// event, and when it occurs. To use Java's PriorityQueue, we need
// have this class implement the Comparable interface where
// one event is "less" if it occurs sooner.

class Event implements Comparable {
    public static int ARRIVAL = 1;
    public static int DEPARTURE = 2;
    int type = -1;                     // Arrival or departure.
    double eventTime;                  // When it occurs.

    public Event (double eventTime, int type)
    {
	this.eventTime = eventTime;
	this.type = type;
    }


    public int compareTo (Object obj)
    {
        Event e = (Event) obj;
        if (eventTime < e.eventTime) {
            return -1;
        }
        else if (eventTime > e.eventTime) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public boolean equals (Object obj)
    {
        return (compareTo(obj) == 0);
    }

}

