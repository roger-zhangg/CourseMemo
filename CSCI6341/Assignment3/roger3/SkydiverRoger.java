import javax.swing.*;
import java.awt.*;


class DrawPanel2 extends JPanel {

    double t = 0;
    double d = 0;
    double v = 0;
    double g = 32.17;
    // feet/s
    double a = 0;
    double delT = 0.1;
    private Polygon poly;
    double openTime = 0.0;
    int x_diff = 0;
    int y_diff = 0;
    boolean deployed = false;
    double k1 = 0.268;
    double k2 = 1.342;



    public void paintComponent (Graphics g)
    {

        super.paintComponent (g);

        // Clear.
        Dimension D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        

        g.setColor(Color.BLUE);
        x_diff = D.width/2;
        y_diff = (int)d/4;

        if (deployed){
            // polygon represent chute opens
            int xPoly2[] = {x_diff, x_diff-1, x_diff-10, x_diff-2 ,x_diff, x_diff+2, x_diff+ 10, x_diff +1 ,x_diff, x_diff+ 20, x_diff - 20};
            int yPoly2[] = {y_diff, y_diff+5, y_diff+5, y_diff+7,  y_diff+15 ,y_diff+7 , y_diff+5, y_diff+5 , y_diff,y_diff-10,y_diff-10};
            poly = new Polygon(xPoly2, yPoly2, xPoly2.length);
        }else{
            // polygon represent sky diver
            int xPoly[] = {x_diff, x_diff-1, x_diff-10, x_diff-2 ,x_diff, x_diff+2, x_diff+ 10, x_diff +1 };
            int yPoly[] = {y_diff, y_diff+5, y_diff+5, y_diff+7,  y_diff+15 ,y_diff+7 , y_diff+5, y_diff+5};
            poly = new Polygon(xPoly, yPoly, xPoly.length);
        }
        g.drawPolygon(poly);

        g.fillRect (0,315, D.width,100);

    }


    void reset ()
    {
	t = 0;
	d = 0;
    a = 0;
    v = 0;
    openTime = 0.0;
    deployed = false;

        Thread t = new Thread (() -> animate());
        t.start();
    }

    //return distance needed for speed to reduce back to 40feet/sec for opentime T
    double calDistance(double T){
        double d = 0;
        double a = 0;
        double t = 0;
        double v = 0;
        while (t<=30){
            t = t + delT;
            if (t<T){
                a = g - k1 * v;
            }else{
                a = g - k2 * v;
            }

            v = v + delT * a;
            d = d + delT * v;
            if (t>T && v<=40){
                break;
            }
        }
        return d;
    }

    double bisectFind(double start,double end){
        double mid = (end+start)/2;
        System.out.println(mid+" "+calDistance(mid));
        if (calDistance(mid) < 1195){
            return bisectFind(mid,end);
        }else if (calDistance(mid) > 1205){
            return bisectFind(start,mid);
        }else{
            return mid;
        }
    }
    void animate ()
    {
	Function V = new Function ("trooper");
        // find the approx open time
        openTime = bisectFind(0,30);
        openTime = Math.floor(openTime);
        double tmpD = 0;
        // find the largest open time
        while (tmpD<1200){
            tmpD = calDistance(openTime+delT);
            if (tmpD>1200){
                break;
            }
            openTime+=delT;
        }

        System.out.println(openTime + " "+calDistance(openTime));
        while (t <= 30) {
            t = t + delT;
            if (t<openTime){
                a = g - k1 * v;
            }else{
                deployed = true;
                a = g - k2 * v;
            }

            v = v + delT * a;
            d = d + delT * v;
            if (t>openTime && v<=40){
                //hit ground
                break;
            }
	        V.add (t, v);
            this.repaint ();
//        if (d2>=d && t > 1){
//            System.out.println("catchup");
//            System.out.println("time used:" + t);
//            System.out.println("distance used:" + d2);
//            System.out.println("trooper's speed:" + v2);
//            break;
//        }

	    sleep ();
        } 
	Function.show (V,V);
    }

    void sleep ()
    {
            try {
                Thread.sleep (50);
            }
            catch (InterruptedException e){
                System.out.println (e);
            }
    }
    
}




public class SkydiverRoger extends JFrame {

    DrawPanel2 drawPanel;

    public SkydiverRoger ()
    {
        this.setSize (400,500);
        Container cPane = this.getContentPane();
        drawPanel = new DrawPanel2 ();

        JPanel panel = new JPanel ();
        JButton resetB = new JButton ("Go");
        resetB.addActionListener (a -> drawPanel.reset());
        panel.add (resetB);
        panel.add (new JLabel("    "));
        JButton quitB = new JButton ("Quit");
        quitB.addActionListener (a -> System.exit(0));
        panel.add (quitB);
        cPane.add (panel, BorderLayout.SOUTH);
        cPane.add (drawPanel, BorderLayout.CENTER);
        this.setVisible (true);
    }


    public static void main (String[] argv)
    {
        new SkydiverRoger ();
    }


}
