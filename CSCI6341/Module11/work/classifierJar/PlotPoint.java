// PlotPoint.java
//
// Used in the PointProblem to store info about a single 2D point.

import java.awt.*;

public class PlotPoint {

    double x,y;
    Color color;
    boolean isTest = false;
    int classNum = -1;
    
    public PlotPoint (double x, double y, int classNum, boolean isTest)
    {
        this.x = x;
        this.y = y;
        this.classNum = classNum;
        this.isTest = isTest;
        if (isTest) {
            color = Color.red;
        }
        else {
            setColor (classNum);
        }
    }

    void setColor (int c)
    {
        if (c == 0) {
            color = Color.blue;
        }
        else if (c == 1) {
            color =  Color.green;
        }
        else if (c == 2) {
            color =  Color.magenta;
        }
        else if (c == 3) {
            color =  Color.cyan;
        }
        else {
            color =  Color.yellow;
        }
    }
    
}
