// FaceFeatures.java
//
// Author: Rahul Simha
// Mar, 2008
//
// Feature extraction for the face problem. The default version
// merely computes a greyscale image, and puts each pixel value
// into the Vector of double's. To implement a smarter set of
// features, replace "return new Features()" with something else
// that extends FaceFeatures.


import java.util.*;
import java.awt.*;


public class FaceFeatures {

    public static FaceFeatures getInstance ()
    {
        return new FaceFeatures ();
        //return new FaceFeatures2 ();
    }

    public Vector<Double> extractSingle (Image image)
    {
        Vector<Double> v = new Vector<Double> ();
        // First, convert to grayscale.
        int[][] greyPixels = toGreyPixels (image);
        // Now make into a single vector.
        for (int i=0; i<greyPixels.length; i++) {
            for (int j=0; j<greyPixels[0].length; j++) {
                v.add ((double)greyPixels[i][j]);
            }
        }
        System.out.println ("Image: " + greyPixels.length + " x " + greyPixels[0].length);
        return v;
    }


    public ArrayList<Vector<Double>>[] extractSet (int numClasses, ArrayList<Image>[] classData)
    {
        ArrayList<Vector<Double>>[] trainingSet = new ArrayList [numClasses];
        for (int c=0; c<numClasses; c++) {
            trainingSet[c] = new ArrayList<Vector<Double>> ();
            for (Image image: classData[c]) {
                Vector<Double> v = extractSingle (image);
                trainingSet[c].add (v);
            }
            // Build vector from image.
        }
        return trainingSet;
    }


    int[][] toGreyPixels (Image image)
    {
        ImageTool imTool = new ImageTool ();
        int[][][] pixels = imTool.imageToPixels (image);
        int[][] greyPixels = toGreyScale (pixels);
	return greyPixels;
    }

    int[][] toGreyScale (int[][][] pixels)
    {
        // Extract pixels and size.
        int numRows = pixels.length;
        int numCols = pixels[0].length;

        // Make array of exactly the same size.
        int[][] greyPixels = new int [numRows][numCols];

        // Greyscale conversion.
        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {

                // Compute total intensity:
                int totalIntensity = pixels[i][j][1] + pixels[i][j][2] + pixels[i][j][3];
                int avg = (int) (totalIntensity / 3.0);
                // Alpha value is the same.
                greyPixels[i][j] = avg;
            }
        }

        return greyPixels;
    }
    
}


class FaceFeatures2 extends FaceFeatures {

    // HOG: Histogram of Oriented Gradients. This is described in the paper
    // N.Dalal and B.Triggs, "Histograms of Oriented Gradients for Human
    // Detection", http://en.wikipedia.org/wiki/Histogram_of_oriented_gradients

    int numBlocks = 2;
    int numBins = 10;


    // We only need to override extractSingle() since extractSet() is
    // inherited and calls this method.

    public Vector<Double> extractSingle (Image image)
    {
        int[][] pixels = toGreyPixels (image);
        Vector<Double> v = new Vector<Double> ();
	
	// First, pick out blocks.
	int numBlockRows = pixels.length / numBlocks;
	int numBlockCols = pixels[0].length / numBlocks;
	for (int b=0; b<numBlocks; b++) {
	    for (int b2=0; b2<numBlocks; b2++) {
		// Note: the# blocks is numBlocks^2 so we can use b alone.
		int rowStart = b * numBlockRows;
		int colStart = b * numBlockCols;
		int rowEnd = (b+1) * numBlockRows;
		int colEnd = (b+1) * numBlockCols;
		// Get histogram for block.
		double[] hist = makeHOG (pixels, rowStart, rowEnd, colStart, colEnd);
		// Concatenate the histogram into the feature vector.
		for (int bin=0; bin<hist.length; bin++) {
		    v.add (hist[bin]);
		}
	    }
	}

	return v;
    }    


    double[] makeHOG (int[][] pixels, int rowStart, int rowEnd, int colStart, int colEnd)
    {
        double[] binCount = new double [numBins];
        double angleInterval = 2*Math.PI / numBins;

	// We'll ignore the pixels at the boundary, and hope that
	// other blocks pick up features there.
	double numInHistogram = 0;
	for (int row=rowStart+1; row<rowEnd-1; row++) {
	    for (int col=colStart+1; col<colEnd-1; col++) {
		// For pixel[row,col], compute gradient orientation.
		double angle = gradientOrientation (pixels, row, col);
		int bin = (int) Math.floor (angle / angleInterval);
		if (bin < numBins) {
		    binCount [bin] ++;
		    numInHistogram ++;
		}
	    }
	}

        // Now make the histogram a fractional one.
        for (int b=0; b<numBins; b++) {
            binCount[b] = binCount[b] / numInHistogram;
        }

	return binCount;
    }

    double gradientOrientation (int[][] pixels, int row, int col)
    {
	// Avg y-diff.
	double yAboveTotal = pixels[row+1][col-1] + pixels[row+1][col] + pixels[row+1][col+1];
	double yBelowTotal = pixels[row-1][col-1] + pixels[row-1][col] + pixels[row-1][col+1];
	double yDiff = (yAboveTotal - yBelowTotal) / 3;
	
	// Avg x-diff.
	double xLeftTotal = pixels[row-1][col-1] + pixels[row][col-1] + pixels[row+1][col-1];
	double xRightTotal = pixels[row-1][col+1] + pixels[row][col+1] + pixels[row+1][col+1];
	double xDiff = (xRightTotal - xLeftTotal) / 3;

	double angle = Math.atan2 (yDiff, xDiff);
	if (angle < 0) {
	    angle += 2 * Math.PI;
	}

	return angle;
    }

}



