// Soccer.java
//
// Author: Rahul Simha
// March 2012
//
// To execute:
//   java Soccer gui
// or
//   java Soccer competition
//
// Based on the car simulator in earlier modules.
//
// NOTE ABOUT COORDINATES: All the control code will use standard
// Cartesian coordinates with the origin in the lower-left corner.
// The GUI code converts to Java's coordinates where necessary.
// We'll use a fixed size soccer field so that even if the GUI is
// resized, the soccer field coordinates stay the same.


import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;



public class Soccer extends JPanel implements SensorPack {

    static final boolean debug = false;

    // Dimensions of the field. Many are hardcoded, so don't change them.
    double minX=0, maxX=100, minY=0, maxY=40;    

    Dimension D;                 // Size of drawing area.
    int numIntervalsX = 10;      // # tick marks.
    int numIntervalsY = 4;       // # tick marks.
    int inset=60;                // Inset of axes and bounding box.

    int numTeams = 2;            // Only two teams.
    int numPlayers = 2;          // The test strategies have 2 players, but 
                                 // this can be increased.

    double 
	control1,                // control1 is velocity
	control2;                // control2 is turning angle
    
    // Current team, player for GUI, when playing in manual mode.
    int team = 0;
    int playerNum = 0;                

    // Ball variables
    double                       // Center of field.
	initBallX=maxX/2, 
	initBallY=maxY/2;

    double                       // Ball position, orientation, and velocity
	ballX=initBallX, 
	ballY=initBallY, 
	ballTheta=2*Math.PI - 0.1,
	ballV=0;

    double kickVelocity = 10;    // When kicked.
    double ballAcc = -1.2;       // Slows down when kicked.

    // Is the ball being held now, by whom, for how long etc?
    boolean isBallHeld = false;
    PlayerSimulator ballHoldingPlayer = null;
    double ballHeldStartTime = 0;
    double maxBallHoldTime = 8;  // Takes 10 seconds to cross the field.
    int ballCornerCount = 0;

    // Sizes: these should be fixed.
    double ballRadius=1, playerRadius=3, goalRadius=10;

    // Two teams.
    TeamController[] teamControl = new TeamController[2];
    
    // All players in a single structure - for collision dynamics.
    ArrayList<PlayerSimulator> allPlayers = new ArrayList<PlayerSimulator>();

    // Scoring.
    int score0=0, score1=0;
    int goalPoints0, goalPoints1, penaltyPoints0, penaltyPoints1, foulPoints0, foulPoints1;

    int goal=100, penalty=1, foul=2;

    // The time step. 0.1 is a large value. We might reduce it
    // later and also reduce the sleeptime.
    double delT = 0.1;
    double time;
    

    // Animation stuff.
    Thread currentThread;
    boolean isPaused = false;

    // GUI stuff.
    String[] cars = {"Unicycle", "Unicycle-acc", "Simple", "Simple-acc"};
    String[] gameTypes = {"team0 vs team1","team0 vs manual","both manual"};
    Color gridColor = Color.lightGray;
    boolean isAccurate = false;
    
    JComboBox carBox, gameBox;
    JSlider slider1, slider2;
    String topMessage = "";
    Font msgFont = new Font ("Serif", Font.PLAIN, 15);
    Font goalFont = new Font ("Monospaced", Font.PLAIN, 15);
    Font scoreFont = new Font ("Monospaced", Font.BOLD, 15);
    DecimalFormat df = new DecimalFormat ("##.##");

    JTextField[] controllerField = new JTextField [2];
    JToggleButton teamToggle = new JToggleButton ("Change to team 1");
    JComboBox playerBox;
    boolean kickPressed=false, grabPressed=false;
    boolean[] teamIsManual= new boolean [2];

    // For user drawing/debugging.
    LinkedList<Line2D.Double> lineSegments = new LinkedList<Line2D.Double> ();
    
    // GUI or competition
    boolean isGUI = true;
    int sleepTime = 200;


    ////////////////////////////////////////////////////////////////////////
    // Constructors

    public Soccer ()
    {
	// Empty when using GUI.
	isGUI = true;
	sleepTime = 200;
    }

    public Soccer (TeamController team0, TeamController team1)
    {
	teamControl[0] = team0;
	teamControl[1] = team1;
	isGUI = false;
	sleepTime = 0;
    }


    ////////////////////////////////////////////////////////////////////////
    // Drawing

    void repaintGUI ()
    {
	if (isGUI) {
	    this.repaint ();
	}
    }

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        Graphics2D g2 = (Graphics2D) g;
	RenderingHints rh = g2.getRenderingHints();
	rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	g2.setRenderingHints(rh);

        // Clear.
        D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        
        // Axes, bounding box.
	g.setColor (Color.gray);
        g.drawLine (inset,D.height-inset, D.width-inset, D.height-inset);
        g.drawLine (inset,inset, inset,D.height-inset);
        g.drawLine (D.width-inset,inset, D.width-inset,D.height-inset);
        g.drawLine (inset,inset, D.width-inset, inset);


        // X-ticks and labels.
        double xDelta = (maxX-minX) / numIntervalsX;
        for (int i=1; i<=numIntervalsX; i++) {
            double xTickd = i*xDelta;
            int xTick = realToJavaX (xTickd);
            g.drawLine (xTick,D.height-inset-5, xTick,D.height-inset+5);
            double x = minX + i*xDelta;
            g.drawString (df.format(x), xTick-5, D.height-inset+20);
        }

        // Y-ticks
        double yDelta = (maxY-minY) / numIntervalsY;
        for (int i=0; i<numIntervalsY; i++) {
            int yTick = (i+1) * (int) ((D.height-2*inset) / (double)numIntervalsY);
            g.drawLine (inset-5, D.height-yTick-inset, inset+5, D.height-yTick-inset);
            double y = minY + (i+1)  * yDelta;
            g.drawString (df.format(y), 1, D.height-yTick-inset);
        }

	// User drawing.
	if (lineSegments != null) {
	    g2.setColor (Color.yellow);
	    for (Line2D.Double L: lineSegments) {
		drawLine (g2, L);
	    }
	}


        // Center circle, penalty boxes, half-line
	g.setColor (Color.gray);
        drawOval (g2, maxX/2, maxY/2, 3, 0);
        drawOval (g2, 0, maxY/2, goalRadius, 1);
        drawOval (g2, maxX, maxY/2, goalRadius, -1);
        drawLine (g2, maxX/2, 0, maxX/2, maxY);
        
	// Players.
        for (PlayerSimulator p: allPlayers) {
            Color color = Color.green;
            if (p.team == 1) {
                color = Color.cyan;
            }
            drawOval (g2, p.x, p.y, p.theta, p.playerNum, color);
        }

        // Ball (which might overlay a player when the player holds ball).
	if (! isBallHeld) {
	    drawOval (g2, ballX, ballY, 0, -1, Color.red);
	}
	else {
	    drawOval (g2, ballHoldingPlayer.ballX, ballHoldingPlayer.ballY, 0, -1, Color.red);
	}

        messages (g);
    }
    

    int realToJavaX (double x)
    {
        int scaledX = (int) ((x-minX) / (maxX-minX) * (D.width-2*inset));
	return (inset + scaledX);
    }


    int realToJavaY (double y)
    {
	int scaledY = (int) ((y-minY) / (maxY-minY) * (D.height-2.0*inset) );
	return (D.height - inset - scaledY);
    }


    double javaToRealX (int jX)
    {
        int scaledX = jX - inset;
        double x = minX + scaledX * (maxX-minX)/ (D.width-2.0*inset);
        return x;
    }


    double javaToRealY (int jY)
    {
        int scaledY = D.height - inset - jY;
        double y = minY + scaledY * (maxY-minY)/ (D.height-2.0*inset);
        return y;
    }
    

    void drawLine (Graphics g, Line2D.Double L)
    {
	if (L == null) {
	    return;
	}
        drawLine (g, L.x1, L.y1, L.x2, L.y2);
    }
    

    void drawLine (Graphics g, double Lx1, double Ly1, double Lx2, double Ly2)
    {
	int x1 = realToJavaX (Lx1);
        int y1 = realToJavaY (Ly1);
	int x2 = realToJavaX (Lx2);
	int y2 = realToJavaY (Ly2);
	g.drawLine (x1, y1, x2, y2);
    }


    void drawRectangle (Graphics g, Rectangle2D.Double R)
    {
	if (R == null) {
	    return;
	}
	int x1 = realToJavaX (R.x);
	int y1 = realToJavaY (R.y);
	double x = R.x + R.width;
	double y = R.y - R.height;
	int x2 = realToJavaX (x);
	int y2 = realToJavaY (y);
        g.fillRect (x1, y1, x2-x1, y2-y1);
    }


    void drawOval (Graphics g, double cx, double cy, double radius, int arcInfo)
    {
        g.setColor (Color.lightGray);
	int x = realToJavaX (cx);
	int y = realToJavaY (cy);
        int r = realToJavaY(0) - realToJavaY (radius);
        if (arcInfo == 0) { // Full circle
            g.drawOval (x-r, y-r, 2*r, 2*r);
        }
        else if (arcInfo < 0) { // Left arc
            g.drawArc (x-r, y-r, 2*r, 2*r, 90, 180);
        }
        else { // Right arc
            g.drawArc (x-r, y-r, 2*r, 2*r, -90, 180);
        }
        
    }
    

    void drawOval (Graphics g, double cx, double cy, double theta, int num, Color color)
    {
        g.setColor (color);
	int x = realToJavaX (cx);
	int y = realToJavaY (cy);
        if (num < 0) {  // Ball
	    int r = realToJavaX (ballRadius) - realToJavaX(0);
            g.fillOval (x-r, y-r, 2*r, 2*r);
        }
        else {
	    int r = realToJavaX (playerRadius) - realToJavaX(0);
            g.fillOval (x-r, y-r, 2*r, 2*r);
            g.setColor (Color.white);
            g.fillArc (x-r,y-r,2*r,2*r, (int)toDegrees(theta)-30, 60);
            g.setColor (Color.darkGray);
            g.drawString (""+num,x-5, y+5);
        }
    }
    

    double toDegrees (double r)
    {
        // Convert radians to degrees.
        return 360*r / (2*Math.PI);
    }


    void messages (Graphics g)
    {
        // Top msg, time, scores etc.
        g.setFont (msgFont);
        g.setColor (Color.black);
        String timeStr = "Time: " + df.format(time);
        g.drawString (timeStr, 10, 30);
        g.drawString (topMessage, 80, 30);

        String penStr  = "Penalties:team0=" + penaltyPoints0 + "  team1=" + penaltyPoints1;
        String foulStr = "Fouls:    team0=" + foulPoints0 + "  team1=" + foulPoints1;
        String goalStr = "Goals:    team0=" + goalPoints0 + "  team1=" + goalPoints1;

        score0 = goalPoints0 + penaltyPoints1 + foulPoints1;
        score1 = goalPoints1 + penaltyPoints0 + foulPoints0;
        String scoreStr= "Score: team0=" + score0 + "  team1=" + score1;

        g.setFont (goalFont);
        g.drawString (penStr, D.width-600, 15);
        g.drawString (foulStr, D.width-600, 30);
        g.drawString (goalStr, D.width-600, 45);
        g.setFont (scoreFont);
        g.drawString (scoreStr, D.width-280, 30);
    }
    


    ////////////////////////////////////////////////////////////////////////
    // SensorPack interface

    public double getX (int team, int player)
    {
        PlayerSimulator p = getPlayer (team,player);
	// Technically, p==null means the team/player info is wrong. We'll force
	// the error so that it's visible on the screen.
        if (p == null) {
	    System.out.println ("NULL player: error in player-team number");
            return -1;
        }
        return p.x;
    }
    
    
    public double getY (int team, int player)
    {
        PlayerSimulator p = getPlayer (team,player);
        if (p == null) {
	    System.out.println ("NULL player: error in player-team number");
            return -1;
        }
        return p.y;
    }
    
    
    public double getTheta (int team, int player)
    {
        PlayerSimulator p = getPlayer (team,player);
        if (p == null) {
	    System.out.println ("NULL player: error in player-team number");
            return -1;
        }
        return p.theta;
    }
    

    public double getBallX ()
    {
        return ballX;
    }
    
    
    public double getBallY ()
    {
        return ballY;
    }
    
    
    public double getBallTheta ()
    {
        return ballTheta;
    }
    
    
    public boolean isBallHeld ()
    {
        return isBallHeld;
    }
    

    public int ballHeldByPlayer ()
    {
	if ( (isBallHeld) && (ballHoldingPlayer != null) ) {
	    return ballHoldingPlayer.playerNum;
	}
	return -1;
    }

    public int ballHeldByTeam ()
    {
	if ( (isBallHeld) && (ballHoldingPlayer != null) ) {
	    return ballHoldingPlayer.team;
	}
	return -1;
    }


    PlayerSimulator getPlayer (int team, int playerNum)
    {
        for (PlayerSimulator p: allPlayers) {
            if ((p.team == team) && (p.playerNum == playerNum)) {
                return p;
            }
        }
        return null;
    }
    

    public void drawLine (double x1, double y1, double x2, double y2)
    {
	lineSegments.add (new Line2D.Double (x1,y1, x2,y2));
    }


    public double getAngle (double x1, double y1, double x2, double y2)
    {
	return angleFix ( Math.atan2 (y2-y1, x2-x1) );
    }


    public double getCurrentTime ()
    {
        return getTime ();
    }
    


    ////////////////////////////////////////////////////////////////////////
    // Animation and simulation

    void reset ()
    {
	if (isGUI) {
	    int index = gameBox.getSelectedIndex ();
	    if (index == 0) {
		teamIsManual[0] = teamIsManual[1] = false;
		loadController (0);
		loadController (1);
		teamControl[0].init (this, numPlayers, 0, true);
		teamControl[1].init (this, numPlayers, 1, false);
	    }
	    else if (index == 1) {
		loadController (0);
		teamIsManual[0] = false;  teamIsManual[1] = true;
		teamControl[0].init (this, numPlayers, 0, true);
	    }
	    else {
		teamIsManual[0] = teamIsManual[1] = true;
	    }
	}
	else {
	    teamIsManual[0] = teamIsManual[1] = false;
	    teamControl[0].init (this, numPlayers, 0, true);
	    teamControl[1].init (this, numPlayers, 1, false);
	}

	resetGame ();
    }
        

    void resetGame ()
    {
        team = 0;
	playerNum = 0;
        setPlayers ();
	ballX = initBallX;  ballY = initBallY;  
	ballV=0;  ballTheta = 0;
	isBallHeld = false;
	ballHoldingPlayer = null;
	ballCornerCount = 0;
        goalPoints0 = goalPoints1 = 0;
        penaltyPoints0 = penaltyPoints1 = 0;
        foulPoints0 = foulPoints1 = 0;
	score0 = score1 = 0;
	kickPressed=false;   grabPressed=false;

	// Start the game.
        time = 0;
	if (isGUI) {
	    lineSegments = new LinkedList<Line2D.Double> ();
	    isPaused = false;
	    stopAnimationThread ();
	    this.repaintGUI ();
	}
    }
    

    void stopAnimationThread ()
    {
        if (currentThread != null) {
            currentThread.interrupt ();
            currentThread = null;
        }
    }
    

    double getTime ()
    {
        return allPlayers.get(0).t;
    }
    

    void setPlayers ()
    {
        allPlayers = new ArrayList<PlayerSimulator> ();

	// Space players equally.
        double vertSpacing = maxY / (numPlayers+1);
        
        for (int team=0; team<2; team++) {
            for (int i=0; i<numPlayers; i++) {
		PlayerSimulator playerSim = new PlayerSimulator (false, true);
		if (isGUI) {
		    String carStr = (String) carBox.getSelectedItem ();
		    playerSim.isAccelModel = carStr.equals ("Unicycle-acc") || carStr.equals ("Simple-acc");
		    playerSim.isUnicycle = carStr.equals ("Unicycle") || carStr.equals ("Unicycle-acc");
		}
                playerSim.team = team;
                playerSim.playerNum = i;
                if (team == 0) {
                    playerSim.x = 20;
                }
                else {
                    playerSim.x = 80;
                    playerSim.theta = Math.PI;
                }
                playerSim.y = (i+1) * vertSpacing;
		playerSim.t = 0;
		playerSim.ballRadius = ballRadius;
                
                if (! teamIsManual[team]) {
                    teamControl[team].init (i, playerSim.x, playerSim.y, playerSim.theta);
                }
                allPlayers.add (playerSim);
            }
        }
    }
    

    // The run() method should only be used in non-GUI (competition) mode.
    void run (double maxTime)
    {
	reset ();
	while (time <= maxTime) {
	    nextStep ();
            time = getTime ();
	}
    }

    
    // The go() method should only be used in GUI mode.
    void go ()
    {
        if (isPaused) {
            isPaused = false;
            return;
        }
        
        stopAnimationThread ();    // To ensure only one thread.

	if (isGUI) {
	    currentThread = new Thread () {
		    public void run () 
		    {
			animate ();
		    }
		};
	    currentThread.start();
	}
	else {
	    animate ();
	}
    }
    


    void pause () 
    {
        isPaused = true;
    }
    


    void animate ()
    {
        while (true) {

            if (! isPaused) {
		boolean done = nextStep ();
		if (done) {
		    System.out.println ("DONE!");
		    break;
		}
            }

            time = getTime ();
            this.repaintGUI ();

            try {
                Thread.sleep (sleepTime);
            }
            catch (InterruptedException e){
                break;
            }
        } //endwhile

        this.repaintGUI ();
    }


    boolean nextStep ()
    {
        // Apply all interactions: these only change orientations
        //    Ball with any wall
        //    Ball with any player: bounce or capture
        //    Any player with wall
        //    Any player with any other player - ball may detach
        //      player who had ball, spins randomly, kicks it out.
        //      May need to have ball move to a random spot close by
        //      if space just beyond player is not free.

        //** Shuffle player order. Not implemented now.

	// Player-to-player collisions.
	for (int i=0; i<allPlayers.size()-1; i++) {
	    PlayerSimulator p = allPlayers.get(i);
	    for (int j=i+1; j<allPlayers.size(); j++) {
		PlayerSimulator q = allPlayers.get(j);
		applyCollision (p, q);
	    }
	}

	// Player-to-wall collisions.
	for (PlayerSimulator p: allPlayers) {
	    applyWallCollision (p);
	}

        // Move ball.
	if (! isBallHeld) {
	    moveBall ();
	}

        // For each player in order:
        //    Get controls from team or from GUI and compute next step.
	getControls ();

        // For each player in order:
        //    Apply next step.
	applyNextStep ();

        // Apply penalties - players in their own goal area.
        applyPenalties ();

        return false;
    }


    void applyPenalties ()
    {
        // numInLeftBox[0] = # team0 players in left box etc.
        int[] numInLeftBox = new int [2];
        int[] numInRightBox = new int [2];
        
	for (PlayerSimulator p: allPlayers) {
            if ( distance(p.x,p.y, 0,maxY/2) < goalRadius ) {
                numInLeftBox[p.team] ++;
            }
            if ( distance(p.x,p.y, maxX,maxY/2) < goalRadius ) {
                numInRightBox[p.team] ++;
            }
        }
        
        // Penalties are assessed if you are in your own box, but
        // no opposing player is in the box and ball is not in box.
        if ((numInLeftBox[0] > 0) && (numInLeftBox[1] == 0)) {
            // Penalty points against team 0
            if ( distance(ballX,ballY, 0,maxY/2) > goalRadius ) {
                penaltyPoints0 += penalty;
            }
        }

        if ((numInRightBox[1] > 0) && (numInRightBox[0] == 0)) {
            // Penalty points against team 1
            if ( distance(ballX,ballY, 0,maxY/2) > goalRadius ) {
                penaltyPoints1 += penalty;
            }
        }

    }
    

    void applyWallCollision (PlayerSimulator p)
    {
	if (debug) Debug.println ("applyWallCollision(): ");
	if (p.y < playerRadius) {
	    // if players's direction is towards wall, reflect.
	    if ((p.theta >= Math.PI) && (p.theta <= 2*Math.PI)) {
		p.theta = -p.theta;
		p.theta = angleFix (p.theta);
		if (debug) Debug.println (" >> player " + p.playerNum + " reflection below");
	    }
	}
	else if (p.y > maxY-playerRadius) {
	    // if players's direction is towards wall, reflect.
	    if ((p.theta >= 0) && (p.theta <= Math.PI)) {
		p.theta = -p.theta;
		p.theta = angleFix (p.theta);
		if (debug) Debug.println (" >> player " + p.playerNum + " reflection above");
	    }
	}
	else if (p.x < playerRadius) {
	    if ((p.theta >= Math.PI/2) && (p.theta <= 3*Math.PI/2)) {
		p.theta = Math.PI - p.theta;
		p.theta = angleFix (p.theta);
		if (debug) Debug.println (" >> player " + p.playerNum + " reflection left");
	    }
	}
	else if (p.x > maxX-playerRadius) {
	    if (((p.theta >= 0) && (p.theta <= Math.PI/2))
		|| ((p.theta >= 3*Math.PI/2) && (p.theta <= 2*Math.PI))) {
		p.theta = Math.PI - p.theta;
		p.theta = angleFix (p.theta);
		if (debug) Debug.println (" >> player " +p.playerNum + " reflection right");
	    }
	}

    }


    void applyCollision (PlayerSimulator p, PlayerSimulator q)
    {
	if (distance(p.x,p.y, q.x,q.y) > 2*playerRadius) {
	    return;
	}

	// Here, we apply p's collision on q and vice-versa.
	// This is only an approximation - we aren't computing 
	// the resolvent vector.
	if (debug)
	    Debug.println ("Soccer.applyCollision: p.t=" + p.team + "p.p=" + p.playerNum + " q.t=" + q.team + " q.p=" + q.playerNum);

	double pTheta=p.theta, qTheta=q.theta;

	if ((p.v == 0) && (q.v == 0)) {
	    // Both are still. Nothing to be done.
	    return;
	}
	else if ((p.v == 0) && (q.v > 0)) {
	    // p stays still, q bounces
	    qTheta = getCollisionAngle (p, q, qTheta);
	    if (p.hasBall) {
		tryKick (p);
	    }
            else if ((qTheta != q.theta) && (p.team != q.team)){
                // foul on q.
                foulPoints (q);
            }
	}
	else if ((p.v > 0) && (q.v == 0)) {
	    // q stays still, p bounces
	    pTheta = getCollisionAngle (q, p, pTheta);
	    if (q.hasBall) {
		tryKick (q);
	    }
            else if ((pTheta != p.theta) && (p.team != q.team)) {
                // foul on p.
                foulPoints (p);
            }
	}
	else {
	    // Both bounce
	    qTheta = getCollisionAngle (p, q, qTheta);
	    pTheta = getCollisionAngle (q, p, pTheta);
            // If either has ball, it's lost.
	    if (p.hasBall) {
		tryKick (p);
	    }
	    if (q.hasBall) {
		tryKick (q);
	    }
	}

	p.theta = pTheta;
	q.theta = qTheta;
	if (debug)
	    Debug.println (" >>>> changed p.t=" + p.theta + " q.t=" + q.theta);
    }


    void foulPoints (PlayerSimulator p)
    {
        if (p.team == 0) {
            foulPoints0 += foul;
        }
        else {
            foulPoints1 += foul;
        }
    }


    double getCollisionAngle (PlayerSimulator p, PlayerSimulator q, double qTheta)
    {
	// Here, we apply p's collision on q and get q's change.
	// q is assumed moving, and p is still.

	// If the current direction does not have a radial component towards 
	// the center of p, this is not a collision (q is moving away).
	double radialAngle = Math.atan2 (p.y-q.y, p.x-q.x);
	double angleDist = angleDistance(radialAngle,qTheta);
	if (angleDist > Math.PI/2) {
	    // Keep current direction.
	    return qTheta;
	}

	double tangentAngle = angleFix (radialAngle + Math.PI/2);
	double tangentOpp = angleFix (tangentAngle + Math.PI);

	double tempTheta = 2*tangentAngle - q.theta;
	double qThetaNew = angleFix (tempTheta);

	if (debug)
	    Debug.println ("getCollAngle():   p.theta=" + toDegrees(p.theta) + " q.theta=" + toDegrees(q.theta) + ", rad=" + toDegrees(radialAngle) + " tan=" + toDegrees(tangentAngle) + " tanOpp=" + toDegrees(tangentOpp)+ ", temp=" + toDegrees(tempTheta) + " fixT=" + toDegrees(qTheta) + "\n");

	return qThetaNew;
    }


    void applyNextStep ()
    {
	if (debug) Debug.println ("applyNextStep() ");
	for (PlayerSimulator p: allPlayers) {
	    p.applyNextStep ();
	}
    }


    void getControls ()
    {
	if (debug) Debug.println ("getControls() ");
	if ( (!teamIsManual[0]) && (!teamIsManual[1]) ) {
	    // Both are programs: get controls from teamControl[0],[1]
	    teamControl[0].move ();
	    teamControl[1].move ();
	}
        else if (!teamIsManual[0]) {
	    teamControl[0].move ();
        }
        

	for (PlayerSimulator p: allPlayers) {
	    double c1 = getControl (p, 1);
	    c1 = boundControl1 (c1);
	    double c2 = getControl (p, 2);
	    c2 = boundControl2 (c2);
	    p.computeNextStep (c1, c2, delT);
	    if (debug) Debug.println (" >> p.t=" + p.team + " p.p=" + p.playerNum + " c1=" + c1 + " c2=" + c2);
	    if (p.hasBall) {
		if ( ballHeldTooLong(p) || triesKick(p) ) {
		    if (tryKick(p)) {
			if (teamControl[p.team] != null) {
			    teamControl[p.team].kickSuccessful (p.playerNum);
			}
			if (debug)
			    Debug.println (" >>>> kick success: p.p=" + p.playerNum);
		    }
		}
	    }
	    else if ( (!p.hasBall) && (!isBallHeld) ) {
		if ( (nearBall(p)) && (triesGrab(p)) ) {
		    if (tryGrab(p)) {
			if (teamControl[p.team] != null) {
			    teamControl[p.team].grabSuccessful (p.playerNum);
			}
			if (debug)
			    Debug.println (" >>>> grab success p.p=" + p.playerNum);
		    }
		}
	    }
	}
    }


    double getControl (PlayerSimulator p, int i)
    {
	if (! teamIsManual[p.team]) {
	    return teamControl[p.team].getControl (p.playerNum, i);
	}
	// If this is the current player, get it from the GUI.
	if ((p.team == team) && (p.playerNum == playerNum)) {
	    if (i == 1) {
		return control1;
	    }
	    else {
		return control2;
	    }
	}
	// Otherwise, it's already in the player.
	if (i == 1) {
	    return p.control1;
	}
	else {
	    return p.control2;
	}
    }

    
    double boundControl1 (double c)
    {
	if (c < 0) {
	    return 0;
	}
	if (c > 10) {
	    return 10;
	}
	return c;
    }

    double boundControl2 (double c)
    {
	if (c < -10) {
	    return -10;
	}
	if (c > 10) {
	    return 10;
	}
	return c;
    }


    boolean triesKick (PlayerSimulator p)
    {
	if (! teamIsManual[p.team]) {
	    return teamControl[p.team].triesKick(p.playerNum);
	}
	// Otherwise see if GUI has pressed kick.
	if (kickPressed) {
	    if ((p.team == team) && (p.playerNum == playerNum)) {
		kickPressed = false;
		return true;
	    }
	}
	return false;
    }


    boolean triesGrab (PlayerSimulator p)
    {
	if (! teamIsManual[p.team]) {
	    return teamControl[p.team].triesGrab(p.playerNum);
	}
	// Otherwise see if GUI has pressed grab.
	if (grabPressed) {
	    if ((p.team == team) && (p.playerNum == playerNum)) {
		grabPressed = false;
		return true;
	    }
	}
	return false;
    }


    boolean tryKick (PlayerSimulator p)
    {
        // Add error.
        double maxError = 2*Math.PI/36;  // 10 degrees.
        double error = UniformRandom.uniform (-maxError, maxError);
        double tempTheta = angleFix (p.theta + error);

	double dist = 2;
	int numTries = 1;
	double bx = p.x,  by = p.y;
	while (true) {
	    // We need to try reducing the distance because the ball
	    // might be very close to the wall.
	    bx = p.x + dist*playerRadius*Math.cos(tempTheta);
	    by = p.y + dist*playerRadius*Math.sin(tempTheta);
	    if (withinBounds (bx,by)) {
		break;
	    }
	    dist = dist/2;
	    numTries ++;
	    if (numTries > 10) {
		// This is not ideal. We should probably move the ball randomly.
		bx = p.x;
		by = p.y;
		break;
	    }
	}

	// Success.
	ballX = bx;  ballY = by;  ballTheta=tempTheta;  
	ballV = kickVelocity;
	p.hasBall = false;
	isBallHeld = false;
	ballHoldingPlayer = null;
	return true;
    }


    boolean withinBounds (double x, double y)
    {
	if ((x < ballRadius) || (x > maxX-ballRadius)) {
	    return false;
	}
	if ((y < ballRadius) || (y > maxY-ballRadius)) {
	    // Out of bounds.
	    return false;
	}
	return true;
    }


    boolean tryGrab (PlayerSimulator p)
    {
	p.hasBall = true;
	ballHoldingPlayer = p;
	isBallHeld = true;
	ballHeldStartTime = time;
	ballX = p.ballX;  ballY = p.ballY;
	return true;
    }


    boolean nearBall (PlayerSimulator p)
    {
	// First check distance.
	double dist = distance (p.x,p.y, ballX,ballY);
	if (dist > 2*ballRadius) {
	    return false;
	}

	// Now check angle to make sure player is approx facing the ball.
	double angle = Math.atan2 (ballY-p.y, ballX-p.x);
	angle = angleFix (angle);
	if (angleDistance(p.theta,angle) > 30) {
	    return false;
	}
	return true;
    }


    boolean ballHeldTooLong (PlayerSimulator p)
    {
	return ( (time-ballHeldStartTime) > maxBallHoldTime );
    }


    double angleDistance (double alpha, double beta)
    {
	//if (debug) Debug.println ("angleDist: al=" + alpha + " be=" + beta);
	if (alpha >= beta) {
	    double diff = alpha - beta;
	    if (diff < Math.PI) {
		return diff;
	    }
	    return 2*Math.PI - diff;
	}
	else return angleDistance (beta, alpha);
    }


    void moveBall ()
    {
	if (debug) Debug.println ("MoveBall: ");

	// If in contact with goal, apply goal rules.
	if ((ballX < ballRadius) && (ballY > maxY/2-goalRadius)
	    && (ballY < maxY/2+goalRadius)) {
	    // Goal scored by team 1.
            goalPoints1 += goal;
	    randomBall ();
	    if (debug) Debug.println (" >> goal by team1");
	    return;
	}
	else if ((ballX > maxX-ballRadius) && (ballY > maxY/2-goalRadius)
	    && (ballY < maxY/2+goalRadius)) {
	    // Goal scored by team 0.
            goalPoints0 += goal;
	    randomBall ();
	    if (debug) Debug.println (" >> goal by team0");
	    return;
	}

	// Check if the ball is stuck in a corner: in the same corner, 3 x 3,
	// for 10 consecutive seconds.
	if (ballInCorner()) {
	    ballCornerCount ++;
	    if (ballCornerCount > (int)(10.0/delT)) {
		// Kick it out. This assumes that it stays in a single corner.
		// It's unlikely that a ball will be kicked across two corners.
		randomBall ();
		ballCornerCount = 0;
	    }
	}
	else {
	    ballCornerCount = 0;
	}

	// If in contact with wall, apply wall rules.
	//   y value within r of top (maxY, or bottom)
	//   x value within r of 0 or maxX
	//   or both.
	if (ballY < ballRadius) {
	    // if ball's direction is towards wall, reflect.
	    if ((ballTheta >= Math.PI) && (ballTheta <= 2*Math.PI)) {
		ballTheta = -ballTheta;
		ballTheta = angleFix (ballTheta);
		if (debug) Debug.println (" >> ball reflection below");
	    }
	    else {
		// This is a hack, to prevent the ball from being stuck.
		ballTheta = Math.PI/2;
	    }
	}
	else if (ballY > maxY-ballRadius) {
	    // if ball's direction is towards wall, reflect.
	    if ((ballTheta >= 0) && (ballTheta <= Math.PI)) {
		ballTheta = -ballTheta;
		ballTheta = angleFix (ballTheta);
		if (debug) Debug.println (" >> ball reflection above");
	    }
	    else {
		// This is a hack, to prevent the ball from being stuck.
		ballTheta = 3*Math.PI/2;
	    }
	}
	else if (ballX < ballRadius) {
	    if ((ballTheta >= Math.PI/2) && (ballTheta <= 3*Math.PI/2)) {
		ballTheta = Math.PI - ballTheta;
		ballTheta = angleFix (ballTheta);
		if (debug) Debug.println (" >> ball reflection left");
	    }
	    else {
		// This is a hack, to prevent the ball from being stuck.
		ballTheta = 0;
	    }
	}
	else if (ballX > maxX-ballRadius) {
	    if (((ballTheta >= 0) && (ballTheta <= Math.PI/2))
		|| ((ballTheta >= 3*Math.PI/2) && (ballTheta <= 2*Math.PI))) {
		ballTheta = Math.PI - ballTheta;
		ballTheta = angleFix (ballTheta);
		if (debug) Debug.println (" >> ball reflection right");
	    }
	    else {
		// This is a hack, to prevent the ball from being stuck.
		ballTheta = Math.PI;
	    }
	}

	// If in contact with player, apply player rules.
	for (PlayerSimulator p: allPlayers) {
	    if (distance(p.x,p.y, ballX,ballY) <= ballRadius+playerRadius) {
		double radialAngle = Math.atan2 (p.y-ballY, p.x-ballX);
		double tangentAngle = angleFix (radialAngle + Math.PI/2);
		double tangentOpp = angleFix (tangentAngle + Math.PI);
		double diff = tangentAngle - ballTheta;
		double tempTheta = tangentAngle + diff;
		double fixedTheta = angleFix (tempTheta);
		if (debug) Debug.println ("px=" + p.x + " py=" + p.y + " bx=" + ballX + " by=" + ballY + " tan=" + toDegrees(tangentAngle) + " tanOpp=" + toDegrees(tangentOpp) + " diff=" + toDegrees(diff));
		if (debug) Debug.println ("btheta=" + toDegrees(ballTheta) + ", rad=" + toDegrees(radialAngle) + " tan=" + toDegrees(tangentAngle) + ", temp=" + toDegrees(tempTheta) + " fixT=" + toDegrees(fixedTheta) + "\n");
		// If the ball direction isn't towards the player, we don't bounce.
		if ((tangentAngle < tangentOpp) && (ballTheta > tangentAngle) && (ballTheta < tangentOpp)) {
		    continue;
		}
		else if ((tangentOpp < tangentAngle) && (ballTheta > tangentOpp) && (ballTheta < tangentAngle)) {
		    continue;
		}
		ballTheta = fixedTheta;
		//System.exit (0);
		break;
	    }
	}

	ballV += delT * ballAcc;
        if (ballV < 0) {
            ballV = 0;
        }

	// If ball is out of bounds, it will need some non-zero velocity
	// to get within bounds. Another hack.
	if ((ballX < 0) || (ballX > maxX) || (ballY < 0) || (ballY > maxY)) {
	    ballV = 5;
	}

        double delX = delT * ballV * Math.cos(ballTheta);
        ballX += delX;
        double delY = delT * ballV * Math.sin(ballTheta);
        ballY += delY;
    }

    
    boolean ballInCorner ()
    {
	if ((ballX < 3) && (ballY < 3)) {
	    return true;
	}
	if ((ballX < 3) && (ballY > maxY-3)) {
	    return true;
	}
	if ((ballX > maxX-3) && (ballY > maxY-3)) {
	    return true;
	}
	if ((ballX > maxX-3) && (ballY < 3)) {
	    return true;
	}
	return false;
    }


    void randomBall ()
    {
	// Find a random place near the center to place the ball.
	ArrayList<Double> possibleY = new ArrayList<Double> ();
	for (double y=2*ballRadius; y<=maxY-2*ballRadius; y+=ballRadius) {
	    if (isFree(maxX/2, y, ballRadius)) {
		possibleY.add (y);
	    }
	}

	// Now pick a random spot. 
	ballX = maxX/2;
        int k = UniformRandom.uniform ((int) 0, (int) possibleY.size()-1);
	ballY = possibleY.get(k);
	ballV = 0;
    }


    boolean isFree (double x, double y, double r) 
    {
	// See if the disk centered at (x,y) w/ radius r is free of objects.
	// See if ball is there.
	if ( distance(ballX,ballY, x,y) <= r ) {
	    return false;
	}
	for (PlayerSimulator p: allPlayers) {
	    if ( distance(p.x,p.y, x,y) <= r) {
		return false;
	    }
	}
	return true;
    }


    double angleFix (double a)
    {
        // Make each angle an angle between 0 and 2*PI.
        //** Note: this code can be optimized.
        if (a < 0) {
            while (a < 0) {
                a = a + 2*Math.PI;
            }
        }
        else if (a > 2*Math.PI) {
            while (a > 2*Math.PI) {
                a = a - 2*Math.PI;
            }
        }
        return a;
    }


    double distance (double x1, double y1, double x2, double y2)
    {
	return Math.sqrt ( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
    }



    ////////////////////////////////////////////////////////////////////////
    // GUI events

    void loadController (int i)
    {
        String className = "";
        try {
            if (! teamIsManual[i]) {
                className = controllerField[i].getText().trim();
                if (className.length() > 0) {
                    TeamController c = (TeamController)(Class.forName(className)).newInstance();
                    teamControl[i] = c;
                }
            }
            
            topMessage = " Controller loaded";
            this.repaintGUI ();
        }
        catch (Exception e) {
            System.out.println (e);
            topMessage = "ERROR: Could not load or instantiate controller: " + className;
        }
    }
    

    void toggleTeam ()
    {
        playerNum = 0;
        if (team == 0) {
            team = 1;
            teamToggle.setText ("Change to team 0");
        }
        else {
            team = 0;
            teamToggle.setText ("Change to team 1");
        }
        topMessage = "Changed focus to team " + team + ", player" + playerNum;
    }
    

    void playerChanged ()
    {
        playerNum = playerBox.getSelectedIndex ();
        topMessage = "Changed focus to player " + playerNum + " (team=" + team + ")";
    }

    
    void grab ()
    {
	grabPressed = true;
    }


    void kick ()
    {
	kickPressed = true;
    }
    

    void startDebug ()
    {
        for (int i=0; i<2; i++) {
            if (! teamIsManual[i]) {
                teamControl[i].startDebug ();
            }
        }
    }
    

    ////////////////////////////////////////////////////////////////////////
    // GUI construction

    JPanel makeBottomPanel ()
    {
        JPanel panel = new JPanel ();
        
        panel.setLayout (new GridLayout (2,1));
        JPanel sPanel = makeSetupPanel ();
        sPanel.setBorder (BorderFactory.createTitledBorder ("  Simulation  "));
        panel.add (sPanel);
        JPanel cPanel = makeControlPanel ();
        cPanel.setBorder (BorderFactory.createTitledBorder ("  Play  "));
        panel.add (cPanel);

        return panel;
    }
    

    JPanel makeSetupPanel ()
    {
        JPanel panel = new JPanel ();

	panel.setLayout (new GridLayout(2,1));

	JPanel topPart = new JPanel ();
	JPanel bottomPart = new JPanel ();
	
        carBox = new JComboBox (cars);
        topPart.add (carBox);
        gameBox = new JComboBox (gameTypes);
        topPart.add (gameBox);

        topPart.add (new JLabel ("  team0 controller:"));

        controllerField = new JTextField [2];
        controllerField[0] = new JTextField (10);
        controllerField[0].setText ("Schlimazels");
        topPart.add (controllerField[0]);

        topPart.add (new JLabel ("  team1 controller:"));
        controllerField[1] = new JTextField (10);
        controllerField[1].setText ("Schlemiels");
        topPart.add (controllerField[1]);

	JButton resetB = new JButton ("Reset");
	resetB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       reset ();
		   }
           }
        );
	bottomPart.add (resetB);

        bottomPart.add (new JLabel ("  "));
	JButton goB = new JButton ("Go");
	goB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       go ();
		   }
           }
        );
	bottomPart.add (goB);

        bottomPart.add (new JLabel ("  "));
	JButton pauseB = new JButton ("Pause");
	pauseB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       pause ();
		   }
           }
        );
	bottomPart.add (pauseB);

        bottomPart.add (new JLabel ("  "));
	JButton debugB = new JButton ("Debug");
	debugB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       startDebug ();
		   }
           }
        );
	bottomPart.add (debugB);


        bottomPart.add (new JLabel ("  "));
	JButton quitB = new JButton ("Quit");
	quitB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       System.exit(0);
		   }
           }
        );
	bottomPart.add (quitB);



	panel.add (topPart);
	panel.add (bottomPart);

        return panel;
    }


    JPanel makeControlPanel ()
    {
        JPanel panel = new JPanel ();
        
        panel.add (teamToggle);
        teamToggle.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    toggleTeam ();
                }
            }
        );

        panel.add (new JLabel ("Player#:"));
        String[] playerNum = new String [numPlayers];
        for (int i=0; i<numPlayers; i++) {
            playerNum[i] = "" + i;
        }

        playerBox = new JComboBox (playerNum);
        playerBox.addItemListener (
            new ItemListener () 
            {
                public void itemStateChanged (ItemEvent a) 
                {
                    playerChanged ();
                }
            }
        );
        panel.add (playerBox);

        panel.add (new JLabel("     "));

        panel.add (new JLabel ("C1: "));
	slider1 = new JSlider (0, 10, 0);
	slider1.setMajorTickSpacing(5);
	slider1.setMinorTickSpacing(1);
	slider1.setPaintTicks(true);
	slider1.setPaintLabels(true);
	slider1.addChangeListener (
	   new ChangeListener ()
	   {
	       public void stateChanged (ChangeEvent c)
	       {
		   control1 = slider1.getValue ();
	       }
	   }
        );
	panel.add (slider1);

        panel.add (new JLabel ("  C2: "));
	slider2 = new JSlider (-10, 10, 0);
	slider2.setMajorTickSpacing(5);
	slider2.setMinorTickSpacing(1);
	slider2.setPaintTicks(true);
	slider2.setPaintLabels(true);
	slider2.addChangeListener (
	   new ChangeListener ()
	   {
	       public void stateChanged (ChangeEvent c)
	       {
		   control2 = slider2.getValue ();
	       }
	   }
        );
	panel.add (slider2);

	panel.add (new JLabel ("     "));
	JButton grabB = new JButton ("Grab");
	grabB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       grab ();
		   }
           }
        );
	panel.add (grabB);

	JButton kickB = new JButton ("Kick");
	kickB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       kick ();
		   }
           }
        );
	panel.add (kickB);

        return panel;
    }
    


    void makeFrame ()
    {
        JFrame frame = new JFrame ();
        frame.setSize (1000, 700);
        frame.setTitle ("Soccer GUI and Simulator");
        Container cPane = frame.getContentPane();
        cPane.add (makeBottomPanel(), BorderLayout.SOUTH);
        cPane.add (this, BorderLayout.CENTER);
        frame.setVisible (true);
    }


    ////////////////////////////////////////////////////////////////////////
    // Main

    public static void main (String[] argv)
    {
	if ((argv == null) || (argv.length != 1)) {
            System.out.println ("Usage: java Soccer gui\nOr: java Soccer competition");
            System.exit (0);
        }

        if (argv[0].equals ("gui")) {
	    Soccer gui = new Soccer ();
	    gui.makeFrame ();
        }
	else {
	    competition ();
	}
    }
    

    static void competition ()
    {
	// Make instances of all teams and play them off.
	LinkedList<TeamController> teams = new LinkedList<TeamController> ();
	//teams.add (new TestTeam0());
	//teams.add (new TestTeam1());
	UniformRandom.set_seed (321);
	teams.add (new Schlimazels());
	teams.add (new RogerTeam());
	
	for (int i=0; i<teams.size()-1; i++) {
	    for (int j=i+1; j<teams.size(); j++) {
		playOff (teams.get(i), teams.get(j));
	    }
	}
    }

    static void playOff (TeamController teamA, TeamController teamB)
    {
	System.out.println ("MATCH: playing " + teamA.getName() + " vs. " + teamB.getName());
	// Play n games each side and report average score.
	// Call run() with maxTime.
	int numGames = 6;
	double maxTime = 1000;
	double[] totalPenalties = new double[2];
	double[] totalFouls = new double[2];
	double[] totalGoals = new double[2];
	double[] totalScores = new double[2];

	// Half the games with teamA on left.
	for (int n=0; n<numGames/2; n++) {
	    Soccer soccer = new Soccer (teamA, teamB);
	    soccer.run (maxTime);
	    totalPenalties[0] += soccer.penaltyPoints0;
	    totalFouls[0] += soccer.foulPoints0;
	    totalGoals[0] += soccer.goalPoints0;

	    totalPenalties[1] += soccer.penaltyPoints1;
	    totalFouls[1] += soccer.foulPoints1;
	    totalGoals[1] += soccer.goalPoints1;
	    System.out.println ("  game # " + n + " completed");
	}

	// Half the games with teamA on right.
	for (int n=numGames/2; n<numGames; n++) {
	    Soccer soccer = new Soccer (teamB, teamA);
	    soccer.run (maxTime);
	    totalPenalties[0] += soccer.penaltyPoints1;
	    totalFouls[0] += soccer.foulPoints1;
	    totalGoals[0] += soccer.goalPoints1;

	    totalPenalties[1] += soccer.penaltyPoints0;
	    totalFouls[1] += soccer.foulPoints0;
	    totalGoals[1] += soccer.goalPoints0;
	    System.out.println ("  game # " + n + " completed");
	}

	for (int i=0; i<2; i++) {
	    //** Note the careful use of 1-i. 
	    totalScores[i] = totalGoals[i] + totalFouls[1-i] + totalPenalties[1-i];
	}

	DecimalFormat df = new DecimalFormat ("##.##");

	System.out.println ("  Scores:");
	System.out.println ("    " + teamA.getName());
	System.out.println ("       goals    : " + df.format(totalGoals[0]/numGames));
	System.out.println ("       penalties: " + df.format(totalPenalties[0]/numGames));
	System.out.println ("       fouls    : " + df.format(totalFouls[0]/numGames));
	System.out.println ("       score    : " + df.format(totalScores[0]/numGames));

	System.out.println ("    " + teamB.getName());
	System.out.println ("       goals    : " + df.format(totalGoals[1]/numGames));
	System.out.println ("       penalties: " + df.format(totalPenalties[1]/numGames));
	System.out.println ("       fouls    : " + df.format(totalFouls[1]/numGames));
	System.out.println ("       score    : " + df.format(totalScores[1]/numGames));
    }

}
