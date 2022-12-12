
import java.util.*;
import java.awt.*;
import java.awt.geom.*;


/**
 * Interface <code>TeamController</code> must be implemented by every
 * team controller.
 *
 */

public interface TeamController {

    /**
     * Return your team name.
     *
     */
    public String getName ();


    /**
     * This <code>init()</code> method is called by the GUI once for each team.
     * This is how you know your team number (0 or 1), whether you are on
     * the left side, and how many players you have. You are also passed
     * a sensor pack, which will be useful in learning about the current
     * state of the field.
     *
     */
    public void init (SensorPack sensor, int numPlayers, int myteam, boolean onLeft);

    /**
     * This <code>init()</code> method is called by the GUI once for each player.
     * This is how you know each player is placed on the field. No, you don't get
     * to decide where your players are placed.
     *
     */
    public void init (int playerNum, double initX, double initY, double initTheta);

    /**
     * Method <code>move()</code> is called by the GUI at each step so
     * that you can plan your next move for each player. Essentially,
     * all the "intelligence" should be here. You implement code to 
     * adjust the controls. Note that the controls themselves are 
     * not returned by this method. Instead, the GUI gets them by 
     * calling getControl() once for each control.
     *
     */
    public void move ();


    /**
     * Method <code>getControl()</code> is called by the GUI to extract
     * the current values of the control for player p. There are only two now
     * and so i=1 or 2 the only values of <code>i</code>.
     *
     */
    public double getControl  (int p, int i);


    /**
     * Method <code>triesGrab()</code> is called by the GUI to ask
     * you if you want to hold (grab) the ball right now, since
     * the GUI has determined that it's close enough for a grab.
     * Return <code>true</code> if you decide you want to grab the ball. 
     * Note: if a player holds the ball too long, it will be forced 
     * out the player.
     *
     */
    public boolean triesGrab (int p);


    /**
     * Method <code>grabSuccessful()</code> is called by the GUI to let
     * you know that the grab you sought was successful. This allows you to update
     * your knowledge of the "state" of the system.
     *
     */
    public void grabSuccessful (int p);


    /**
     * Method <code>triesKick()</code> is called by the GUI to ask
     * you if you want to try and kick the ball right now, provided
     * player p is in possession of the ball. Return <code>true</code>
     * if you decide you want to kick the ball. Note: if a player holds the
     * ball too long, it will be forced out the player.
     *
     */
    public boolean triesKick (int p);


    /**
     * Method <code>kickSuccessful()</code> is called by the GUI to let
     * you know that the kick was successful. This allows you to update
     * your knowledge of the "state" of the system.
     *
     */
    public void kickSuccessful (int p);


    /**
     * Method <code>startDebug()</code> is called by the GUI when
     * the debug button is clicked. Then it's up to you to decide
     * what you want to do. You can write your debug to the file
     * <code>debug.data</code> by calling <code>Debug.println()</code>
     * from your code. It is useful to be able to delay writing
     * to this file because otherwise, it would be filled with useless
     * write's. When you see something going wrong on the screen,
     * click debug, and let that start the debugging log.
     *
     */
    public void startDebug ();
    
}
