
/**
 * Class <code>Player</code> is a utility class that can be
 * used by any team to record current info about its players. Its use
 * is optional. The instances of this class are filled out in 
 * the sample code provided in <code>TestTeam0.java</code>.
 *
 */

public class Player {

    /**
     * Player number.
     */
    public int playerNum = -1;           // My player number.

    /**
     * Current x value
     */
    public double x;                     // Current location.

    /**
     * Current y value
     */
    public double y;                     

    /**
     * Current orientation
     */
    public double theta;                 // Current orientation.

    /**
     * Velocity.
     */
    public double vel = 0;               // Velocity.

    /**
     * Turn angle
     */
    public double phi = 0;               // Turn angle.

    /**
     * Does this player have the ball?
     */
    public boolean hasBall = false;      // Do I have the ball?

    /**
     * Will this player kick the ball?
     */
    public boolean tryKick = false;      // Should I kick?

    /**
     * Will this player grab the ball?
     */
    public boolean tryGrab = false;      // Should I grab?

    /**
     * Distance from this player to the ball.
     */
    public double distToBall = -1;       // My distance to the ball.


    // For debugging.
    public String toString ()
    {
        return "[num=" + playerNum + " x=" + x + " y=" + y + " theta=" + theta + " v=" + vel + " phi=" + phi + " hasBall=" + hasBall + "]";
    }
    
}
