import java.util.*;

public class TestTeam1 implements TeamController {

    SensorPack sensors;           // The sensor pack handed to us.
    int numPlayers;               // How many players/team.
    ArrayList<Player> players;    // List of our players.
    int myteam = -1;              // Our team number (0 or 1).
    boolean onLeft;               // Are we on the left or right?

    boolean debug = false;        // If we want to turn on debugging.


    public String getName ()
    {
	return "TestTeam1";
    }


    public void init (SensorPack sensors, int numPlayers, int myteam, boolean onLeft) 
    {
        this.sensors = sensors;
        this.numPlayers = numPlayers;
        this.myteam = myteam;
        this.onLeft = onLeft;
        players = new ArrayList<Player> ();
    }
    
    public void init (int playerNum, double initX, double initY, double initTheta)
    {
        Player p = new Player ();
        p.playerNum = playerNum;
        p.x = initX;
        p.y = initY;
        p.theta = initTheta;
        players.add (p);
    }


    public void move ()
    {
        updateLocations ();
        int t = (int) sensors.getCurrentTime ();
        for (Player p: players) {
	    // Random velocity each time, and a random direction every 5 seconds.
            p.vel = UniformRandom.uniform (5.0, 10.0);
            if (t % 5 == 0) {
                p.phi = UniformRandom.uniform (-10.0, 10.0);
            }
        }
    }
    

    public double getControl  (int p, int i)
    {
	Player player = getPlayer (p);
	if (i == 1) {
	    return player.vel;
	}
	else {
	    return player.phi;
	}
    }
    

    public boolean triesKick (int p)
    {
	// If we have the ball, we're going to kick.
	Player player = getPlayer (p);
	if (player.hasBall) {
	    player.hasBall = false;
	    return true;
	}
        return false;
    }
    

    public void kickSuccessful (int p)
    {
    }
    

    public boolean triesGrab (int p)
    {
	// If we can grab the ball, we will.
	Player player = getPlayer (p);
	// See if player is close enough to the ball.
	double ballX = sensors.getBallX ();
	double ballY = sensors.getBallY ();
	if ( distance(player.x,player.y, ballX,ballY) < 2 ) {
	    return true;
	}
        return false;
    }
    

    public void grabSuccessful (int p)
    {
	Player player = getPlayer (p);
	player.hasBall = true;
    }
    

    void updateLocations ()
    {
	for (Player p: players) {
	    p.tryKick = false;
	    p.x = sensors.getX (myteam, p.playerNum);
	    p.y = sensors.getY (myteam, p.playerNum);
	    p.theta = sensors.getTheta (myteam, p.playerNum);
            p.distToBall = distance (sensors.getBallX(), sensors.getBallY(), p.x,p.y);
            if ( (sensors.isBallHeld()) && (sensors.ballHeldByTeam() == myteam) && p.playerNum == sensors.ballHeldByPlayer()) {
                p.hasBall = true;
            }
            else {
                p.hasBall = false;
            }
	}
    }

    Player getPlayer (int k)
    {
	for (Player player: players) {
	    if (player.playerNum == k) {
		return player;
	    }
	}
	return null;
    }

    double distance (double x1, double y1, double x2, double y2)
    {
	return Math.sqrt ( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
    }

    public void startDebug ()
    {
        debug = true;
    }
    
}
