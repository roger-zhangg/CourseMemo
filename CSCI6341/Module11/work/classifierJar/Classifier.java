
import java.util.*;

/**
 * A classification algorithm must implement interface <code>Classifier</code>.
 * In the first method, the algorithm is given training data. This where the
 * training part of the algorithm is written. In the second, the algorithm
 * must classify a given sample. The key data structures are important to
 * understand. First, a single sample is a point in n-dim space. We represent
 * that with a <code>Vector<Double></code> instance. A collection of such
 * n-dim points will be a collection of  <code>Vector<Double></code> instances.
 * This we store in an <code>ArrayList<Vector<Double>></code>. There is one
 * such collection for each class, which is why trainingData[c] is
 * the collection for class c.
 *
 */

public interface Classifier {

    /**
     * Method <code>train</code> is called with data.
     *
     * @param numClasses an <code>int</code> value - the number of classes in the data.
     * @param isFixedDimension a <code>boolean</code> value - is the data of fixed dimension (i.e., are all the training samples of the same size)?
     * @param trainingData an <code>ArrayList<Vector<Double>>[]</code> value - the data itself: trainingData[c] is a list of vectors (n-dim points).
     * @return a <code>String</code> value - an algorithm can return a message to be displayed on the screen. Typically this is either "successful" or some reason for failure.
     */

    public String train (int numClasses, boolean isFixedDimension, ArrayList<Vector<Double>>[] trainingData);



    /**
     * In method <code>classify</code>, the algorithm is given a particular sample
     * and asked to classify the sample.
     *
     * @param sample a <code>Vector<Double></code> value - each number is one dimension in the sample.
     * @return an <code>int</code> value - the class (from 0 onwards) to which this should belong.
     */

    public int classify (Vector<Double> sample);

}
