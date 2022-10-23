
import java.awt.*;
import javax.swing.*;


class DrawPanel extends JPanel {

    double t = 0;
    double d = 0;
    double d2 = 0;
    double v = 0;
    double v2 = 0;
    double a = 12;
    double delT = 0.1;

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);

        // Clear.
        Dimension D = this.getSize();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        
        g.setColor (Color.red);
        int x = (int) d/10;
        int y = D.height/2;
        g.fillOval (x,y, 10, 10);

        g.setColor (Color.blue);
        int x2 = (int) d2/10;
        int y2 = D.height/2;
        g.fillOval (x2,y2, 10, 10);
    }


    void reset ()
    {
	t = 0;
	d = 0;
    d2 = 0;
	v = 100;
    v2 = 0;
        Thread t = new Thread (() -> animate());
        t.start();
    }


    void animate ()
    {
	Function V = new Function ("trooper");
	Function D = new Function ("speeding car");
        while (t <= 30) {
	        t = t + delT;
	        v2 = v2 + delT * a;
            d = d + delT * v;
            d2 = d2 + delT * v2;
	        D.add (t, d);
	        V.add (t, d2);
            this.repaint ();
        if (d2>=d && t > 1){
            System.out.println("catchup");
            System.out.println("time used:" + t);
            System.out.println("distance used:" + d2);
            System.out.println("trooper's speed:" + v2);
            break;
        }

	    sleep ();
        } 
	Function.show (V,D);
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




public class StateTrooperRoger extends JFrame {

    DrawPanel drawPanel;

    public StateTrooperRoger ()
    {
        this.setSize (500,200);
        Container cPane = this.getContentPane();
        drawPanel = new DrawPanel ();

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
        new StateTrooperRoger ();
    }


}
