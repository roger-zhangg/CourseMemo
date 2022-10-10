
import java.util.*;
import java.awt.geom.*;
import java.awt.*;


public class SimpleCarController implements CarController {

    // The two controls: either (vel,phi) or (acc,phi)
    double acc;       // Acceleration.
    double vel;       // Velocity.
    double phi;       // Steering angle.
    double max_x=-10000.0,max_y=-10000.0,min_x=10000.0,min_y=10000.0;
    // use to determine the grid density
    int splits = 100;
    double canvas_x = 600.0;
    double canvas_y = 600.0;
    double block_w = canvas_x/splits;
    double block_h = canvas_y/splits;
    // safe route is 2 block away from obstacles
    int margin = 3;
    // type 1 for unicycle, type 2 for dubin
    int carType = 2;
    int[][] grid = new int[splits][splits];
    LinkedList<CarState> plan = new LinkedList<CarState>();
    LinkedList<CarState> planSimple = new LinkedList<CarState>();
    ArrayList<Rectangle2D.Double> obstacles;
    SensorPack sensors;
    int currentBX = 0;
    int currentBY = 0;
    int targetBX = -1;
    int targetBY = -1;
    // Is the first control an accelerator?
    boolean isAccelModel = false;
    CarState next = null;

    public void init (double initX, double initY, double initTheta, double endX, double endY, double endTheta, ArrayList<Rectangle2D.Double> obstacles, SensorPack sensors)
    {
        planSimple = new LinkedList<CarState>();
        plan = new LinkedList<CarState>();
        next = null;
        this.obstacles = obstacles;
        this.sensors = sensors;
        targetBX = -1;
        targetBY = -1;
        //fill the grid with obstacle
        for(int i=0;i<splits;i++){
            for(int j=0;j<splits;j++){
                grid[i][j] = 0;
                double center_y = (i+0.5)*block_w;
                double center_x = (j+0.5)*block_h;
                for (Rectangle2D.Double R: obstacles) {
                    //System.out.println (R);
                    // R.x, R.y is the upright point of rect
                    if (grid[i][j]!=0){
                        continue;
                    }
                    if ((R.x - 0.5 * block_w > center_x) || (R.x+R.width + 0.5 * block_w < center_x)){
                        continue;
                    }
                    if ((R.y - R.height - 0.5 * block_h > center_y) || !(R.y+ 0.5 * block_h > center_y)){
                        continue;
                    }
                    // here means inside obstacle
                    grid[i][j]= margin;
                }
            }
        }
        //fill the grid with margin
        for(int refreshes = margin-1;refreshes>0;refreshes--){
            for(int i=0;i<splits;i++) {
                for (int j = 0; j < splits; j++) {
                    if (grid[i][j] != 0){
                        continue;
                    }
                    if (i > 0){
                        if (grid[i-1][j]>grid[i][j]){
                            grid[i][j] = grid[i-1][j]-1;
                        }
                    }
                    if (i < splits -1){
                        if (grid[i+1][j]>grid[i][j]){
                            grid[i][j] = grid[i+1][j]-1;
                        }
                    }
                    if (j > 0){
                        if (grid[i][j-1]>grid[i][j]){
                            grid[i][j] = grid[i][j-1]-1;
                        }
                    }
                    if (j < splits -1){
                        if (grid[i][j+1]>grid[i][j]){
                            grid[i][j] = grid[i][j+1]-1;
                        }
                    }
                }
            }
        }
        //get start and goal here
        int initBY = (int) (initY/block_h);
        int initBX = (int) (initX/block_w);
        int endBY = (int) (endY/block_h);
        int endBX = (int) (endX/block_w);
        grid[initBY][initBX]= -6;
        grid[endBY][endBX]= -7;
        //get cost matrix
        int[][] costMatrix = getCostMatrix(splits,initBX,initBY,endBX,endBY);
        //start A* with memory here


//        for (Rectangle2D.Double R: obstacles) {
//            System.out.println (R);
//            if (R.x < min_x){
//                min_x = R.x;
//            }
//            if (R.y > max_y){
//                max_y = R.y;
//            }
//            if (R.x+R.width > max_x){
//                max_x = R.x + R.width;
//            }
//            if (R.y - R.height < min_y){
//                min_y = R.y - R.height;
//            }
//        }
        previewGrid();
        CarState start = new CarState(null,initBX,initBY);
        CarState goal = new CarState(null,endBX,endBY);
        System.out.println("start planner");
        CBPlannerAStar Planner = new CBPlannerAStar();
        System.out.println("start planning");
        plan = Planner.makePlan(splits,costMatrix,grid,start,goal);
        CarState last = null;
        for(CarState s:plan){
            System.out.println(s.x+","+s.y);
            last = s;
            if (s.parent == null){
                continue;
            }
            if (s.parent.parent == null){
                continue;
            }
            if ((s.x - s.parent.x) == (s.parent.x - s.parent.parent.x)&&(s.x!=s.parent.x)){
                continue;
            }
            if ((s.y - s.parent.y) == (s.parent.y - s.parent.parent.y)&&(s.y!=s.parent.y)){
                continue;
            }
            planSimple.add(s.parent);
        }
        planSimple.addLast(last);
        System.out.println("Simplified plan");
        for(CarState s:planSimple) {
            System.out.println(s.x+","+s.y);
        }

    }

    public int[][] getCostMatrix(int splits,int initBX,int initBY,int endBX,int endBY){
        int[][] costMatrix = new int[splits][splits];
        for (int i =0;i<splits;i++){
            for (int j =0;j<splits;j++){
                int costFromStart = Math.abs(i - initBY) + Math.abs(j - initBX);
                int costToEnd = Math.abs(i - endBY) + Math.abs(j - endBX);
                costMatrix[i][j]=costFromStart+costToEnd;
            }
        }
        for(int i=splits-1;i>=0;i--){
            System.out.println (Arrays.toString(costMatrix[i]));
        }
        System.out.println ("----------------------------------------");
        return costMatrix;

    }
    public void previewGrid(){
        for(int i=splits-1;i>=0;i--){
            System.out.println (Arrays.toString(grid[i]));
        }
    }

    public double getControl (int i)
    {
	if (i == 1) {
	    if (isAccelModel) {
		return acc;
	    }
	    else {
		return vel;
	    }
	}
	else if (i == 2) {
	    return phi;
	}
	return 0;
    }


    public void move ()
    {
        currentBX = (int)(sensors.getX()/block_w);
        currentBY = (int)(sensors.getY()/block_h);
        if (carType==2){
            currentBY-=1;
        }
        if ((targetBY == -1 && targetBX == -1)||Math.abs(targetBY - currentBY)<=3&&Math.abs(targetBX - currentBX)<=3){
            if (plan.isEmpty()){
                targetBX = next.x * 2 -next.parent.x;
                targetBY = next.y * 2 - next.parent.y;
            }else{
                next = planSimple.getFirst();
                planSimple.removeFirst();
                targetBX = next.x;
                targetBY = next.y;
            }
        }
        //check plan and current position
        // if up, theta  = 1/2 Math.PI
        //
//        double[][] mapDiffTheta = new double[][]{new double[]{Math.PI/4*5,Math.PI/4*6,Math.PI/4*7},new double[]{Math.PI,0,0},new double[]{Math.PI/4*3,Math.PI/2,Math.PI/4*1}};
//        int Y = targetBY-currentBY+1;
//        int X = targetBX-currentBX+1;
//        if (Y<0){Y=0;}
//        if (Y>2){Y=2;}
//        if (X<0){X=0;}
//        if (X>2){X=2;}
//        double targetTheta = mapDiffTheta[Y][X];
//        //arctan(y/x)
        double targetTheta = Math.atan2((double)(targetBY-currentBY),(double)(targetBX-currentBX));

//        if (targetBX-currentBX<0){
//            targetTheta = Math.PI - targetTheta;
//        }
//
        if (targetTheta<0){
            targetTheta+=Math.PI*2;
        }
        double thetaDiff = targetTheta - sensors.getTheta();
        if (thetaDiff>Math.PI){
            thetaDiff-=2*Math.PI;
        }
        if (thetaDiff<-Math.PI){
            thetaDiff+=2*Math.PI;
        }
        System.out.println("X "+currentBX+" Y "+currentBY+" TX "+targetBX+" TY "+targetBY);
        System.out.println("current "+sensors.getTheta()+" target "+targetTheta);
        if (carType == 1){
            if (thetaDiff>0.1){
                //turn left
                phi = 10;
                vel = 0;
            }else if (thetaDiff<-0.1) {
                //turn right
                phi = -10;
                vel = 0;
            }else{
                //move forward
                phi = thetaDiff/2;
                vel = 10;
            }
        }else if(carType == 2){
            if (thetaDiff>0.3) {
                //turn left
                phi = 10;
                vel = 1;
            }else if (thetaDiff<-0.3) {
                    //turn right
                    phi = 1;
                    vel = 10;
                }else{
                    //move forward
                    phi = 10+thetaDiff*10;
                    vel = 10-thetaDiff*10;
                }
        }


    }
        // This is where you adjust the control values.


    public void draw (Graphics2D g2, Dimension D)
    {
        // If you want do draw something on the screen (e.g., a path)
        // this is where you do it. Remember to convert to Java coordinates:
        //    yJava = D.height - y;
    }

}
