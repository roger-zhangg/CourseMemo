import java.util.ArrayList;
import java.util.Iterator;

public class RogerTeam implements TeamController {
    static boolean debug = false;
    SensorPack sensors;
    int numPlayers;
    ArrayList<Player> players;
    int myteam = -1;
    int oppTeam = -1;
    boolean onLeft;
    int defer = -1;
    int atker = -1;
    int oppBallPlayer = -1;
    int status = 0;
    // 0 for free ball, 1 for I have ball, 2 for opp have ball
    double shotAngle = 0.0;

    public String getName() {
        return "RogerTeam";
    }

    public void init(SensorPack sensors, int numPlayers, int myteam, boolean onLeft) {
        this.sensors = sensors;
        this.numPlayers = numPlayers;
        this.myteam = myteam;
        this.oppTeam = 1 - myteam;
        this.onLeft = onLeft;

        this.players = new ArrayList();
    }

    public void init(int playerNum, double initX, double initY, double initTheta) {
        Player p = new Player();
        p.playerNum = playerNum;
        p.x = initX;
        p.y = initY;
        p.theta = initTheta;
        if (defer == -1){
            defer = playerNum;
        }else{
            atker = playerNum;
        }
        this.players.add(p);
    }
    // move a player to intersect an opp player
    public void moveIntersect(Player p,double x,double y){
        if (this.onLeft){
            moveTo(p,x/2,(y+20)/2);
            return;
        }
        moveTo(p,(x+100)/2,(y+20)/2);

    }
    // simple function for moving a player to location
    void moveTo(Player p, double x, double y) {
        //double var3 = this.sensors.getX(this.oppTeam, var2);
        // var5 = this.sensors.getY(this.oppTeam, var2);
        if (this.distance(p.x,p.y, x, y) <= 3.05) {
            p.vel = 0.0;
        } else {
//            double var7 = 20.0;
//            double var9 = 0.0;
//            if (this.onLeft) {
//                var9 = 100.0;
//            }

            double var15 = this.sensors.getAngle(p.x, p.y, x, y);
            if (Math.abs(p.theta - var15) > 0.1) {
                if (this.isClockwiseFrom(p.theta, var15)) {
                    p.phi = -10.0;
                } else {
                    p.phi = 10.0;
                }

                p.vel = 10.0;
            } else {
                p.phi = 0.0;
                p.vel = 10.0;
            }

        }
    }
    // move to standby point for quick grabbing ball.
    public void moveToStandby1(Player p){
        if (this.onLeft){
            if (this.distance(40,35,p.x,p.y)<3){
                this.turnToward(p,50,20);
                return;
            }
            moveTo(p,40,35);
            return;
        }
        if (this.distance(60,35,p.x,p.y)<3){
            this.turnToward(p,50,20);
            return;
        }
        moveTo(p,60,35);
    }
    // simple turning function to turn a player to a location.
    public void turnToward(Player p,double x,double y){

        double var2 = this.getAngle(p,x,y);
        if (this.isClockwiseFrom(p.theta, var2)) {
            p.phi = -10.0;
        } else {
            p.phi = 10.0;
        }

    }
    public void move() {
        // TODO add early give up
        this.updateLocations();
        // denfender actions
        Player def = this.getPlayer(this.defer);
        switch (this.status) {
            case 0:
                if (!overHalfLine(sensors.getBallX())) {
                    moveTo(def, sensors.getBallX(), sensors.getBallY());
                    break;
                }

            case 1:
                moveToStandby1(def);
                break;
            case 2:
                Player oppPlayer = this.getPlayer(this.sensors.ballHeldByPlayer());
                moveIntersect(def, oppPlayer.x, oppPlayer.y);
                break;
        }

        // atker actions
        Player atk = this.getPlayer(this.atker);
        atk.tryKick = false;

        if (atk.hasBall) {
            if (this.overHalfLine(atk.x)) {
                this.tryShotOrTurn(atk);
            } else {
                this.moveToGoal(atk);
            }
        } else {
            this.tryBallGrab(atk);
            this.towardsBall(atk);
        }


        if (this.oppBallPlayer >= 0) {
            // chase opp player
            this.moveTo(atk, this.sensors.getX(this.oppTeam, this.oppBallPlayer),this.sensors.getY(this.oppTeam, this.oppBallPlayer));
        }

    }

    void updateLocations() {
        Iterator var1 = this.players.iterator();


        while(var1.hasNext()) {
            Player var2 = (Player)var1.next();
            var2.tryKick = false;
            var2.x = this.sensors.getX(this.myteam, var2.playerNum);
            var2.y = this.sensors.getY(this.myteam, var2.playerNum);
            var2.theta = this.sensors.getTheta(this.myteam, var2.playerNum);
            var2.distToBall = this.distance(this.sensors.getBallX(), this.sensors.getBallY(), var2.x, var2.y);
            if (this.sensors.isBallHeld() && this.sensors.ballHeldByTeam() == this.myteam && var2.playerNum == this.sensors.ballHeldByPlayer()) {
                var2.hasBall = true;
                if (var2.playerNum == this.defer){
                    // switch atk,def if def has ball
                    this.defer = this.atker;
                    this.atker = var2.playerNum;
                }
            } else {
                var2.hasBall = false;
            }
        }
        this.oppBallPlayer = this.checkOppHasBall(this.getPlayer(this.atker));
        this.updateStatus();



    }

    void updateStatus(){
        if (!this.sensors.isBallHeld()){
            status = 0;
            return;
        }
        if (this.sensors.ballHeldByTeam() == this.myteam){
            status = 1;
            return;
        }
        status = 2;
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

    int checkOppHasBall(Player var1) {
        if (this.sensors.isBallHeld() && this.sensors.ballHeldByTeam() == this.oppTeam) {
            return this.sensors.ballHeldByPlayer();
        } else {
            int var2 = 0;
            double currdst = Double.MAX_VALUE;

            for(int var5 = 0; var5 < this.numPlayers; ++var5) {
                double var6 = this.sensors.getX(this.oppTeam, var5);
                double var8 = this.sensors.getY(this.oppTeam, var5);
                double var10 = this.distance(var1.x, var1.y, var6, var8);
                if (var10 < currdst) {
                    currdst = var10;
                    var2 = var5;
                }
            }

            return var2;
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

    public boolean triesKick(int var1) {
        Player var2 = this.getPlayer(var1);
        if (debug) {
            Debug.println("Roger.triesKick(): p=" + var1 + " p.tryK=" + var2.tryKick);
        }

        return var2.tryKick;
    }

    public void kickSuccessful(int p) {
        Player p2 = this.getPlayer(p);
        p2.hasBall = false;
        p2.tryKick = false;
    }

    public boolean triesGrab(int p) {
        Player p2 = this.getPlayer(p);
        if (debug) {
            Debug.println("Roger.triesGrab(): p=" + p + " p.tryG=" + p2.tryGrab);
        }

        return p2.tryGrab;
    }

    public void grabSuccessful(int p) {
        Player p2 = this.getPlayer(p);
        p2.hasBall = true;
    }

    boolean overHalfLine(double x) {
        if (this.onLeft) {
            return x > 60.0;
        } else {
            return x < 40.0;
        }
    }

    void tryShotOrTurn(Player p) {
        this.findShotAngle(p);
        if (debug) {
            Debug.println("Roger.tryShot(): shotAngle=" + this.shotAngle);
        }

        if (Math.abs(p.theta - this.shotAngle) < 0.2) {
            p.tryKick = true;
            p.phi = 0.0;
            p.vel = 2.0;
        } else {
            if (this.isClockwiseFrom(p.theta, this.shotAngle)) {
                p.phi = -10.0;
            } else {
                p.phi = 10.0;
            }

            p.vel = 2.0;
        }

    }

    void findShotAngle(Player p) {
        double angle = 0.0;
        if (this.onLeft) {
            angle = 100.0;
        }

        double tmp = 20.0;
        boolean clear = this.clearShot(p, angle, tmp);
        if (clear) {
            this.shotAngle = this.sensors.getAngle(p.x, p.y, angle, tmp);
        } else {
            angle = (angle + p.x) / 2.0;
            tmp = 40.0;
            clear = this.clearShot(p, angle, tmp);
            if (clear) {
                this.shotAngle = this.sensors.getAngle(p.x, p.y, angle, tmp);
            } else {
                tmp = 0.0;
                this.shotAngle = this.sensors.getAngle(p.x, p.y, angle, tmp);
            }
        }
    }

    boolean clearShot(Player var1, double var2, double var4) {
        boolean var6 = true;
        Iterator var7 = this.players.iterator();

        while(var7.hasNext()) {
            Player var8 = (Player)var7.next();
            if (var8 != var1) {
                double var9 = this.distToLine(var8.x, var8.y, var1.x, var1.y, var2, var4);
                if (var9 <= 3.0) {
                    return false;
                }
            }
        }

        return true;
    }

    double distToLine(double var1, double var3, double var5, double var7, double var9, double var11) {
        if (Math.abs(var5 - var9) < 0.001) {
            return Math.abs(var1 - var5);
        } else {
            double var13 = (var11 - var7) / (var9 - var5);
            double var17 = -1.0;
            double var19 = var7 - var13 * var5;
            double var21 = Math.abs(var13 * var1 + var17 * var3 + var19) / Math.sqrt(var13 * var13 + var17 * var17);
            return var21;
        }
    }

    boolean facingGoal(Player var1) {
        double var2 = this.sensors.getX(this.myteam, var1.playerNum);
        double var4 = this.sensors.getY(this.myteam, var1.playerNum);
        double var6 = this.sensors.getTheta(this.myteam, var1.playerNum);
        double var8 = 0.0;
        double var10 = 30.0;
        double var12 = 10.0;
        if (this.onLeft) {
            var8 = 100.0;
        } else {
            var8 = 0.0;
        }

        double var14 = this.angleFix(Math.atan2(var10 - var4, var8 - var2));
        double var16 = this.angleFix(Math.atan2(var12 - var4, var8 - var2));
        if (debug) {
            Debug.println("Roger.facing(): th=" + var6 + " a1=" + var14 + " a2=" + var16 + " isB=" + this.isBetween(var14, var6, var16));
        }

        return this.isBetween(var14, var6, var16);
    }

    boolean isBetween(double var1, double var3, double var5) {
        double var7 = Math.abs(var1 - var5);
        if (var7 <= Math.PI) {
            if (var1 <= var5) {
                return var1 <= var3 && var3 <= var5;
            } else {
                return var5 <= var3 && var3 <= var1;
            }
        } else if (var1 <= var5) {
            return var1 >= var3 || var3 >= var5;
        } else {
            return var5 >= var3 || var3 >= var1;
        }
    }

    void turnToGoal(Player var1) {
        if (this.facingGoal(var1)) {
            var1.phi = 0.0;
        } else {
            double var2 = this.getGoalAngleFrom(var1.x, var1.y);
            if (this.isClockwiseFrom(var1.theta, var2)) {
                var1.phi = -10.0;
            } else {
                var1.phi = 10.0;
            }
        }

    }

    void moveToGoal(Player var1) {
        this.turnToGoal(var1);
        var1.vel = 10.0;
    }

    double getGoalAngleFrom(double var1, double var3) {
        double var5 = 0.0;
        double var7 = 20.0;
        if (this.onLeft) {
            var5 = 100.0;
        } else {
            var5 = 0.0;
        }

        double var9 = this.angleFix(Math.atan2(var7 - var3, var5 - var1));
        return var9;
    }

    double getAngle(Player p, double x, double y) {
        return this.angleFix(Math.atan2(x - p.x, y - p.y));
    }

    void tryBallGrab(Player var1) {
        double var2 = this.sensors.getX(this.myteam, var1.playerNum);
        double var4 = this.sensors.getY(this.myteam, var1.playerNum);
        double var6 = this.distance(var2, var4, this.sensors.getBallX(), this.sensors.getBallY());
        if (debug) {
            Debug.println("Roger.tryBallGrab(): d=" + var6);
        }

        if (var6 < 2.0) {
            var1.tryGrab = true;
        } else {
            var1.tryGrab = false;
        }

    }

    void towardsBall(Player var1) {
        double var2 = this.sensors.getX(this.myteam, var1.playerNum);
        double var4 = this.sensors.getY(this.myteam, var1.playerNum);
        double var6 = this.sensors.getBallX();
        double var8 = this.sensors.getBallY();
        double var10 = this.angleFix(Math.atan2(var8 - var4, var6 - var2));
        double var12 = this.sensors.getTheta(this.myteam, var1.playerNum);
        if (Math.abs(var10 - var12) > 0.1) {
            if (this.isClockwiseFrom(var12, var10)) {
                var1.phi = -10.0;
            } else {
                var1.phi = 10.0;
            }

            var1.vel = 10.0;
        } else {
            var1.phi = 0.0;
            var1.vel = 10.0;
        }

        if (debug) {
            Debug.println("Roger.towards(): th=" + var12 + " a=" + var10 + " diff=" + Math.abs(var10 - var12) + " isClock=" + this.isClockwiseFrom(var12, var10) + " phi=" + var1.phi);
        }

    }

    boolean isClockwiseFrom(double var1, double var3) {
        double var5 = Math.abs(var1 - var3);
        if (var5 <= Math.PI) {
            return !(var3 >= var1);
        } else {
            return var3 >= var1;
        }
    }

    double angleFix(double var1) {
        if (var1 < 0.0) {
            while(var1 < 0.0) {
                var1 += 6.283185307179586;
            }
        } else if (var1 > 6.283185307179586) {
            while(var1 > 6.283185307179586) {
                var1 -= 6.283185307179586;
            }
        }

        return var1;
    }

    double distance(double var1, double var3, double var5, double var7) {
        return Math.sqrt((var1 - var5) * (var1 - var5) + (var3 - var7) * (var3 - var7));
    }

    public void startDebug() {
        debug = true;
        System.out.println("Roger: debug=" + debug);
    }
}
