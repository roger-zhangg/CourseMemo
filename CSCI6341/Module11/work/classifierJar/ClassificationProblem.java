
import java.util.*;


/**
 * Interface <code>ClassificationProblem</code> is implemented by
 * classification problems so that they are all treated uniformly
 * by the GUI.
 *
 */

public interface ClassificationProblem {

    /**
     * How many classes?
     *
     * @return an <code>int</code> value
     */

    public int getNumClasses ();


    /**
     * Is the data of fixed dimension?
     *
     * @return a <code>boolean</code> value
     */

    public boolean isFixedDimension ();
    

    /**
     * Produce a set of training data. 
     *
     * @return an <code>ArrayList<Vector<Double>>[]</code> value
     */

    public ArrayList<Vector<Double>>[] getTrainingData ();
    

    /**
     * Produce a single sample to be classified. 
     *
     * @return a <code>Vector<Double></code> value
     */

    public Vector<Double> getSample ();

}
