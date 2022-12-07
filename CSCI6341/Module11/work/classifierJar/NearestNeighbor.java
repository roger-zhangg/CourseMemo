
import java.util.*;
import java.text.*;

public class NearestNeighbor extends NullClassifier {

    ArrayList<Vector<Double>>[] trainingData;

    public String train (int numClasses, boolean isFixedDimension, ArrayList<Vector<Double>>[] trainingData)
    {
	this.numClasses = numClasses;
	this.trainingData = trainingData;
	if (isFixedDimension) {
	    System.out.println ("NN: fixed");
	    dim = trainingData[0].get(0).size();
	}
	else {
	    this.trainingData = makeFixedDimension (numClasses, trainingData);
	}

	// No training at all.

	return null;
    }

    public int classify (Vector<Double> v)
    {
	v = makeFixedDimension (v);

	int bestClass = -1;
	double minDist = 100000.0;

	for (int c=0; c<numClasses; c++) {
		double avgDist = 0;
	    for (int m=0; m<trainingData[c].size(); m++) {

		// Get m-th training vector.
		Vector x = trainingData[c].get(m);

		double d = distance (x, v);
		//avgDist+=d/trainingData[c].size();
		// NOTE: the method distance (x,v) has been inherited from NullClassifier

		// INSERT YOUR CODE HERE
		if (d <minDist){
	    	bestClass = c;
	    	minDist = d;
	    }
	    }
	    
	}

	return bestClass;
    }    

}

