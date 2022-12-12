
import java.util.*;
import java.text.*;

public class Gradient extends NullClassifier {

    int numIterations = 5000;

    ArrayList<Vector<Double>>[] trainingData;

    // We inherit the variables: numClasses, dim.

    // Note: w[dim] = the threshold.
    double[] w;

    double initWLeft = -0.5, initWRight = 0.5;


    public String train (int numClasses, boolean isFixedDimension, ArrayList<Vector<Double>>[] trainingData)
    {
	this.numClasses = numClasses;
	this.trainingData = trainingData;
	if (isFixedDimension) {
	    dim = trainingData[0].get(0).size();
	}
	else {
	    this.trainingData = makeFixedDimension (numClasses, trainingData);
	}

        // Next, add one more dimension with value 1.0 to multiply
        // with threshold w[dim]. We'll leave the for-loops as i<dim+1 to
        // remind us. This method is inherited.
        this.trainingData = addUnitX (numClasses, trainingData);

        // Set initial weights to small random values.
        initializeWeights ();
        printWeights ("Before");

        // To store temporary weights during line-search.
        double[] nextw = new double [dim+1];

        for (int n=0; n<numIterations; n++) {


            // First, compute gradients.
            double[] g = computeGradients ();

            // Line-search (bracket-search in alpha-space)
            double currentf = computef (w);
            double alphaStart = 0.0001, alphaEnd=1;
            int numIntervals = 10, numRounds=5;
            double bestf = Double.MAX_VALUE;
            double bestAlpha = alphaStart;
            for (int r=0; r<numRounds; r++) {
                double delta = (alphaEnd-alphaStart) / numIntervals;
                for (double alpha=alphaStart; alpha<alphaEnd; alpha+=delta) {
                    for (int i=0; i<dim+1; i++) {
                        nextw[i] = w[i] - alpha * g[i];
                    }
                    double f = computef (nextw);
                    if (f < bestf) {
                        bestf = f;   bestAlpha = alpha;
                    }
                }
                // Shrink bracket.
                alphaStart = bestAlpha - delta;
                alphaEnd = bestAlpha + delta;
            }
            

            // Now apply bestAlpha: the gradient update.
            for (int i=0; i<dim+1; i++) {
                w[i] = w[i] - bestAlpha * g[i];
            }

        } //end-for-n
        
        
        printWeights ("After");

        // Build the line and display.
        Function F = new Function ("weight line");
        for (double x=1; x<=10; x+=0.5) {
            // INSERT YOUR CODE HERE:
            // double y = ... (equation of the line for 2D case).
            double y = w[0]*x;
            F.add (x,y);
        }
        F.show ();
	return null;
    }


    void initializeWeights ()
    {
        // Use dim+1 for the threshold.
        w = new double [dim+1];
        for (int i=0; i<dim+1; i++) {
            w[i] = RandTool.uniform (initWLeft, initWRight);
        }
    }
    

    double[] computeGradients ()
    {
        // G[i] = partial derivative of error E wrt w_i.
        double[] G = new double [dim+1];

        for (int i=0; i<dim+1; i++) {

            // The double-for simply iterates through all training samples.
            for (int c=0; c<numClasses; c++) {
                for (int k=0; k<trainingData[c].size(); k++) {
                    // Get k-th sample.
                    Vector<Double> X = trainingData[c].get(k); 
                    double z = applyDirect (X);
                    double y = c;
                    if (c == 0) {
                        y = -1;
                    }
                    double g = -2 * (y-z) * X.get(i);
                    G[i] = G[i] + g;
                }
            }

        }
        
        return G;
    }
    

    double computef (double[] w)
    {
	double sum = 0;
        for (int c=0; c<numClasses; c++) {
            for (int k=0; k<trainingData[c].size(); k++) {
                Vector<Double> X = trainingData[c].get(k); 
                double y = c;
                if (c == 0) {
                    y = -1;
                }
		// Compute z using parameters.
                double z = 0;
                for (int i=0; i<dim+1; i++) {
                    z = z + w[i]*X.get(i);
                }
		sum += (y-z)*(y-z);
	    }
	}	
	return sum;
    }


    double computef (Vector<Double> v, double y) 
    {
        double z = applyDirect (v);
        return (z-y)*(z-y);
    }
    

    double applyDirect (Vector<Double> v)
    {
        // Compute sum_i w_i*w_i
        double sum = 0;
        for (int i=0; i<dim+1; i++) {
            sum += w[i] * v.get(i);
        }
        return sum;
    }


    double apply (Vector<Double> v)
    {
        double sum = 0;
        for (int i=0; i<dim+1; i++) {
            sum += w[i] * v.get(i);
        }
        if (sum > 0) {
            return 1;
        }
        else {
            return -1;
        }
    }
    

    public int classify (Vector<Double> sample)
    {
	sample = makeFixedDimension (sample);

        double z = apply (sample);
        if (z == -1) {
            return 0;
        }
        else {
            return 1;
        }
    }    


    void printWeights (String msg)
    {
        System.out.print (msg + ": w: ");
        for (int i=0; i<dim+1; i++) {
            System.out.print (" " + w[i]);
        }
        System.out.println ();
    }

}


