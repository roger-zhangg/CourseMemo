
/**
 * Interface <code>SensorPack</code> is given to you so that you can
 * call this to learn about the current status of the soccer field.
 *
 */

public interface SensorPack {

    /**
     * Get the x-coordinate of any player's location, in whichever team.
     *
     */
    public double getX (int team, int player);
    

    /**
     * Get the y-coordinate of any player's location, in whichever team.
     *
     */
    public double getY (int team, int player);
    

    /**
     * Get the orientation of any player, in whichever team.
     *
     */
    public double getTheta (int team, int player);


    /**
     * Get the ball's x location.
     *
     */
    public double getBallX ();
    

    /**
     * Get the ball's y location.
     *
     */
    public double getBallY ();
    

    /**
     * Get the ball's orientation.
     *
     */
    public double getBallTheta ();
    

    /**
     * Get the current time.
     *
     */
    public double getCurrentTime ();


    /**
     * Has the ball been grabbed and is being held by someone?
     *
     */
    public boolean isBallHeld ();


    /**
     * Which team is holding the ball?
     *
     */
    public int ballHeldByTeam ();


    /**
     * Which player is holding the ball?
     *
     */
    public int ballHeldByPlayer ();

    /**
     * A utility method: what is the angle of the line segment going from
     * from (x1,y1) to (x2,y2)?
     *
     */
    public double getAngle (double x1, double y1, double x2, double y2);


    /**
     * A utility/debugging method: draw a line from (x1,y1) to (x2,y2).
     *
     */
    public void drawLine (double x1, double y1, double x2, double y2);

}
