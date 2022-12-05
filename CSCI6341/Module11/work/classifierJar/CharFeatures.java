// CharFeatures.java
//
// Author: Rahul Simha
// Mar, 2008
//
// Feature extraction for the Char problem. The default implementation
// merely converts from the line-segment data structure into
// the desired Vector of double's. To understand how this works,
// see the extractSingle() method.
//
// To implement better feature extraction, replace "return new CharFeatures()"
// with something else. That something else should extend CharFeatures.

import java.util.*;
import java.awt.*;
import java.awt.geom.*;


public class CharFeatures {

    public static CharFeatures getInstance ()
    {
        return new CharFeatures ();
    }
    
    public Vector<Double> extractSingle (Vector<LineSegmentd> segments)
    {
        Vector<Double> v = new Vector<Double> ();
        for (LineSegmentd L: segments) {
            v.add (L.x1);
            v.add (L.y1);
            v.add (L.x2);
            v.add (L.y2);
        }
        return v;
    }



    public ArrayList<Vector<Double>>[] extractSet (int numClasses, ArrayList<Vector<LineSegmentd>>[] classData)
    {
        ArrayList<Vector<Double>>[] trainingSet = new ArrayList [numClasses];
        for (int c=0; c<numClasses; c++) {
            trainingSet[c] = new ArrayList<Vector<Double>> ();
            // Build the vectors for class c from leftPanel.classData[c].
            for (Vector<LineSegmentd> segments: classData[c]) {
                Vector<Double> v = extractSingle (segments);
                trainingSet[c].add (v);
            }
        }
        return trainingSet;
    }

}


