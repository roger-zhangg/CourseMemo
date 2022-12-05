
import java.util.*;
import java.text.*;


public class NullClassifier implements Classifier {

    boolean printAll = true;

    // For use by derived classes:
    ArrayList<Vector<Double>>[] trainingData;
    int numClasses = -1;
    int dim = -1;
    int component = -1;


    public String train (int numClasses, boolean isFixedDimension, ArrayList<Vector<Double>>[] trainingData)
    {
        System.out.println ("Null: #classes=" + numClasses + " isFixed=" + isFixedDimension);

        if (trainingData == null) {
            return "Training data not complete";
        }

	this.numClasses = numClasses;
	this.trainingData = trainingData;
	if (isFixedDimension) {
	    dim = trainingData[0].get(0).size();
	}
        
        // Print training data to screen.
        for (int c=0; c<numClasses; c++) {
            System.out.println ("  Class c=" + c + ": " + trainingData[c].size() + " vectors");
                for (Vector<Double> v: trainingData[c]) {
                    System.out.print ("  Vector w/len=" + v.size() + "\n   ");
                    if (printAll) {
                        for (Double d: v) {
                            System.out.print (" " + d);
                    }
                    System.out.println ("");
                }
            }
        }

        return null;
    }


    public int classify (Vector<Double> sample)
    {
        System.out.println ("Test Alg: sample");
        if (sample == null) {
            System.out.println ("  => null");
            return -1;
        }

        System.out.print ("  => len=" + sample.size() + "\n  ");
        if (printAll) {
            for (Double d: sample) {
                System.out.print (" " + d);
            }
        }
        System.out.println ("");
        return -1;
    }


    ////////////////////////////////////////////////////////////////////
    // Utility methods for use by other classifiers.

    // A useful method for classifiers to use, that will be accessible
    // if this class is extended. The method clips vectors so that they
    // are all of the same dimension.

    public ArrayList<Vector<Double>>[] makeFixedDimension (int numClasses, ArrayList<Vector<Double>>[] data)
    {
	// First find smallest and largest.
	int smallestDim = Integer.MAX_VALUE;
	int largestDim = Integer.MIN_VALUE;
	for (int c=0; c<numClasses; c++) {
	    for (Vector<Double> v: data[c]) {
		if (v.size() < smallestDim) {
		    smallestDim = v.size ();
		}
		if (v.size() > largestDim) {
		    largestDim = v.size ();
		}
	    }
	}

	this.dim = smallestDim;

	if (smallestDim == largestDim) {
	    return data;
	}


	// Otherwise, clip everything to smallest.
	ArrayList<Vector<Double>>[] clippedData	= new ArrayList [numClasses];
	for (int c=0; c<numClasses; c++) {
	    clippedData[c] = new ArrayList<Vector<Double>> ();
	    for (Vector<Double> v: data[c]) {
		if (v.size() > smallestDim) {
		    Vector<Double> v2 = clip (v, smallestDim);
		    clippedData[c].add (v2);
		}
		else {
		    clippedData[c].add (v);
		}
	    }
	}
	
	return clippedData;
    }


    Vector<Double> makeFixedDimension (Vector<Double> v)
    {
	if (v.size() == dim) {
	    return v;
	}
	else if (v.size() > dim) {
	    return clip (v, dim);
	}
	else {
	    return pad (v, dim);
	}
    }



    Vector<Double> clip (Vector<Double> v, int n)
    {
	Vector<Double> v2 = new Vector<Double> ();
	for (int i=0; i<n; i++) {
	    v2.add (v.get(i));
	}
	return v2;
    }

    Vector<Double> pad (Vector<Double> v, int n)
    {
	Vector<Double> v2 = new Vector<Double> ();
	for (int i=0; i<v.size(); i++) {
	    v2.add (v.get(i));
	}
	for (int i=v.size(); i<n; i++) {
	    v2.add (0.0);
	}
	return v2;
    }


    double distance (Vector<Double> v1, Vector<Double> v2) 
    {
	double sum = 0;
	int m = v1.size();
	if (v2.size() < m) {
	    m = v2.size ();
	}
	for (int i=0; i<m; i++) {
	    double xi = v1.get(i);
	    double yi = v2.get(i);
	    sum += (xi-yi) * (xi-yi);
	}
	return Math.sqrt (sum);
    }


    public ArrayList<Vector<Double>>[] addUnitX (int numClasses, ArrayList<Vector<Double>>[] data)
    {
	ArrayList<Vector<Double>>[] extendedData = new ArrayList [numClasses];
	for (int c=0; c<numClasses; c++) {
	    extendedData[c] = new ArrayList<Vector<Double>> ();
	    for (Vector<Double> v: data[c]) {
                Vector<Double> v2 = copy (v);
                v2.add (1.0);
                extendedData[c].add (v2);
            }
        }
        return extendedData;
    }
    
    Vector<Double> copy (Vector<Double> v)
    {
	Vector<Double> v2 = new Vector<Double> ();
        for (Double D: v) {
            v2.add (D);
        }
        return v2;
    }
    


    double distance (Vector<Double> v1, Vector<Double> v2, int component) 
    {
	if (v1.size() > v2.size()) {
	    return v1.get(component);
	}
	else if (v1.size() < v2.size()) {
	    return v2.get(component);
	}
	double xc = v1.get(component);
	double yc = v2.get(component);
	return Math.sqrt ( (xc-yc) * (xc-yc) );
    }


    double classDistance (int c, Vector<Double> v, int k) 
    {
	// Find k (or less) closest points in class and the avg dist to them.

	//** TO DO.
	return -1;
    }

}
