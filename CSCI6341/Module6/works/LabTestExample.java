
public class LabTestExample {

    public static void main (String[] argv)
    {
	double numTrials = 1000000;
	LabTest lab = new LabTest (0.05, 0.99, 0.03);
	double totalP = 0;
	double totalFP = 0;
	double totalTP = 0;
	for (int n=0; n<numTrials; n++) {
	    lab.nextPatient();
	    if (lab.testedPositive()) {
	    	totalP++;
	    	if (lab.isSick()){
	    		totalTP++;
                // INSERT YOUR CODE HERE
	    	}else{
	    		totalFP++;
	    	}

	    }
	}
	System.out.println("False p:"+totalFP/totalP);
	System.out.println("True p:"+totalTP/totalP);
		// True positive
		// false
        // AND HERE
    }
    
}
