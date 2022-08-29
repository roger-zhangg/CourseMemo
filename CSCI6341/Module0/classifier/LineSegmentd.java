
public class LineSegmentd {

    // This is a line segment from (x1,y1) to (x2,y2).
    double x1,y1, x2,y2;

    public LineSegmentd (double x1, double y1, double x2, double y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    

    public String toString ()
    {
        return "Line Segment: [(" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")]";
    }

}
