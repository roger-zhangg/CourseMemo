
import java.util.*;

/**
 * Each instance of <code>MazeState</code> describes a single state
 * of the Maze problem.
 *
 * @see State
 */

public class CarState extends State {

    // Pointer to parent. This needs to be set by the appropriate problem,
    // in this case, in MazeProblem.
    CarState parent = null;

    // Size.


    // Location.
    int x=-1, y=-1;


    public CarState (CarState parent, int x, int y)
    {
        this.parent=parent;  this.x=x;  this.y=y;
    }


    public CarState getParent ()
    {
        return parent;
    }


    public boolean equals (Object obj)
    {
        if (! (obj instanceof CarState) ) {
            return false;
        }
        CarState m = (CarState) obj;
        if ( (m.x==x) && (m.y==y) ) {
            return true;
        }
        return false;
    }


    public String toString ()
    {
        String str = "CarState: [ x=" + x + ", y=" + y + ", cost="+ combinedCost + "]";
        return str;
    }

}
