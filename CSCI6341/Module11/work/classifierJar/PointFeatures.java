// PointFeatures.java
//
// Author: Rahul Simha
// Mar, 2008
//
// Feature extraction for the Point problem.
// PointFeatures.java
//
// Author: Rahul Simha
// Mar 2008
// 
// We don't really do any feature extraction, merely convert from
// one data structure to another.


import java.util.*;


public class PointFeatures {


    public static PointFeatures getInstance ()
    {
        return new PointFeatures ();
    }
    

    public Vector<Double> extractSingle (PlotPoint p)
    {
        // Convert from PlotPoint to Vector<Double>
        Vector<Double> v = new Vector<Double>();
        v.add (p.x);
        v.add (p.y);
        return v;
    }


    public ArrayList<Vector<Double>>[] extractSet (int numClasses, ArrayList<PlotPoint>[] points)
    {
        // Build 2D points out the data. Recall that trainingSet[c] is
        // the data for class c. And since each class can have multiple
        // Vector's, trainingSet[c] is a list (ArrayList) of such vectors.

        ArrayList<Vector<Double>>[] trainingSet = new ArrayList [numClasses];
        for (int c=0; c<numClasses; c++) {
            trainingSet[c] = new ArrayList<Vector<Double>> ();
            for (PlotPoint p: points[c]) {
                Vector<Double> v  = extractSingle (p);
                trainingSet[p.classNum].add (v);
            }
        }

        return trainingSet;
    }

}
