// PointProblem.java
//
// Author: Rahul Simha
// Mar, 2008.
//
// PointProblem produces random points in the 2D-plane, according
// to specified distributions (which can be changed in the GUI).
// The code below handles both the GUI and the data generation.
//
// Related code: see PointFeatures.java.

import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;



public class PointProblem extends JPanel implements ClassificationProblem {

    // Some of the code is written for > 2 classes, but the GUI is
    // hardcoded to 2 classes. Note: you can do multiple (> 2) classes with
    // 2D points.
    static int numClasses = 2;

    // Increase these if you want more overlap between the classes.
    static double uniformOverlap = 1;
    static double gaussianStdDev = 1;

    // GUI stuff.
    JLabel status;
    String[] models = {"Uniform", "Gaussian", "Split-Uniform", "Split-Gaussian"};
    JComboBox modelBox = new JComboBox (models);
    JTextField pointsField = new JTextField (5);
    JTextField xField = new JTextField (5);
    JTextField yField = new JTextField (5);
    JTextField cField = new JTextField (2);
    int inset=20;            
    DecimalFormat df = new DecimalFormat();
    double currentX=-1, currentY=-1;
    int currentClass = -1;

    // Data: points[c].get(k) = k-th point in c-th class.
    // We'll write this generally, even though we can only display 2D.
    int numPoints;
    ArrayList<PlotPoint>[] points;
    PlotPoint currentTestPoint;

    // Feature extractor.
    PointFeatures features = PointFeatures.getInstance ();


    public PointProblem (JLabel status)
    {
	this.status = status;
        points = new ArrayList [numClasses];
        for (int c=0; c<numClasses; c++) {
            points[c] = new ArrayList<PlotPoint>();
        }
    }


    //////////////////////////////////////////////////////////////////////
    // ClassificationProblem Interface

    public int getNumClasses ()
    {
        return numClasses;   // 2.
    }
    
    public boolean isFixedDimension ()
    {
        return true;
    }
    
    public ArrayList<Vector<Double>>[] getTrainingData ()
    {
        // We'll put all the feature-extraction code someplace else.
        return features.extractSet (numClasses, points);
    }
    
    
    public Vector<Double> getSample ()
    {
        if (currentTestPoint == null) {
            return null;
        }
        
        return features.extractSingle (currentTestPoint);
    }
    

    //////////////////////////////////////////////////////////////////////
    // Generation code


    void generateUniform ()
    {
        int k = numPoints / numClasses;
        double interval = 10 / numClasses;
        // Make k points in each class.
        for (int c=0; c<numClasses; c++) {
            points[c] = new ArrayList<PlotPoint>();
            double left = c * interval - uniformOverlap;
            double right = left + interval + uniformOverlap;
            int pointsGenerated = 0;
            while (pointsGenerated < k) {
                // X values have an overlap, y values are arbitrary.
                double y = RandTool.uniform (0.0, 10.0);
                double x = RandTool.uniform (left, right);
                if ( (x > 0) && (x < 10) ) {
                    points[c].add (new PlotPoint (x,y,c,false));
                    pointsGenerated ++;
                }
            }
        }
    }
    


    void generateSplitUniform ()
    {
        // Split uniform has points from one class on either side of
        // the other. It's impossible for a linear-classifer to learn.

        // Only for 2 classes.
        if (numClasses != 2) {
            System.out.println ("ERROR: cannot use split Uniform with > 2 classes");
            return;
        }
        
        // First class is from [0,4] and [7,10]. Second from [3,8].

        // Second one first.
        points[1] = new ArrayList<PlotPoint>();
        for (int i=0; i<numPoints/2; i++) {
            double y = RandTool.uniform (0.0, 10.0);
            double x = RandTool.uniform (4-uniformOverlap, 7+uniformOverlap);
            points[1].add (new PlotPoint (x,y,1,false));
        }
        
        // First.
        points[0] = new ArrayList<PlotPoint>();
        for (int i=0; i<numPoints/2; i++) {
            double x=0;
            double y = RandTool.uniform (0.0, 10.0);
            if (i % 2 == 0) {
                x = RandTool.uniform (0.0, 4.0+uniformOverlap);
            }
            else {
                x = RandTool.uniform (7.0-uniformOverlap,10.0);
            }
            points[0].add (new PlotPoint (x,y,0,false));
        }
    }
    

    void generateGaussian ()
    {
        int k = numPoints / numClasses;
        // First two centers at (3,6) and (6,3).
        generateGaussian (k, 0, 3, 6, true);
        generateGaussian (k, 1, 6, 3, true);
        for (int c=2; c<numClasses; c++) {
            // Select centers uniformly.
            double x = RandTool.uniform (1.0, 9.0);
            double y = RandTool.uniform (1.0, 9.0);
            generateGaussian (k, c, x, y, true);
        }
    }
    


    void generateSplitGaussian ()
    {
        // Only for 2 classes.
        if (numClasses != 2) {
            System.out.println ("ERROR: cannot use split Uniform with > 2 classes");
            return;
        }

        // First class is centered at (5,5)
        generateGaussian (numPoints/2, 0, 5, 5, true);
        // Second class is partly centered at (3,6) and partly at (6,3)
        int remaining = numPoints - numPoints/2;
        int firstGroup = remaining/2;
        int secondGroup = remaining - firstGroup;
        generateGaussian (firstGroup, 1, 2, 6, true);
        generateGaussian (secondGroup, 1, 6, 2, false);
    }
    
    
    void generateGaussian (int n, int c, double centerX, double centerY, boolean makeNew)
    {
        if (makeNew) {
            points[c] = new ArrayList<PlotPoint>();
        }
        int pointsGenerated = 0;
        while (pointsGenerated < n) {
            double x = centerX + RandTool.gaussian (0, gaussianStdDev);
            double y = centerY + RandTool.gaussian (0, gaussianStdDev);
            if ( (x > 0) && (x < 10) && (y > 0) && (y < 10) ) {
                points[c].add (new PlotPoint (x,y,c,false));
                pointsGenerated ++;
            }
        }
    }
    


    //////////////////////////////////////////////////////////////////////
    // Screen work

    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);
	Dimension D = this.getSize ();

	// Background.
	g.setColor (Color.white);
	g.fillRect (0,0, D.width, D.height);
	g.setColor (Color.black);

        // Axes, bounding box.
        g.drawLine (inset,D.height-inset, D.width-inset, D.height-inset);
        g.drawLine (inset,inset, inset,D.height-inset);
        g.drawLine (D.width-inset,inset, D.width-inset,D.height-inset);
        g.drawLine (inset,inset, D.width-inset, inset);

        double w = D.width-2*inset;
        double h = D.height-2*inset;
        double xInterval = w / 10;
        double yInterval = h / 10;

        // X-ticks: 0 to 10 and labels.
        for (int i=1; i<=10; i++) {
            int xTick = (int) (i*xInterval);
            g.drawLine (inset+xTick,D.height-inset-5, inset+xTick,D.height-inset+5);
            g.drawString (""+i, xTick+inset-5, D.height-inset+20);
        }

        // Y-ticks: 0 to 10 and labels.
        for (int i=1; i<=10; i++) {
            int yTick = (int) (i*yInterval);
            g.drawLine (inset-5, D.height-yTick-inset, inset+5, D.height-yTick-inset);
            g.drawString (""+i, 1, D.height-yTick-inset);
        }

        // Draw points.
        for (int c=0; c<numClasses; c++) {
            for (PlotPoint p: points[c]) {
                int x = inset + (int) (w*(p.x-0)/10.0);
                int y = D.height - inset - (int) (h*(p.y-0)/10.0);
                g.setColor (p.color);
                if (p.isTest) {
                    g.fillRect (x-3,y-3,6,6);
                }
                else {
                    g.fillOval (x-2,y-2,4,4);
                }
            }
        }
        
        // Draw testpoint.
        if (currentTestPoint != null) {
            int x = inset + (int) (w*currentTestPoint.x/10.0);
            int y = D.height - inset - (int) (h*currentTestPoint.y/10.0);
            g.setColor (currentTestPoint.color);
            g.fillRect (x-3,y-3,6,6);
        }
    }


    //////////////////////////////////////////////////////////////////////
    // GUI construction

    public JPanel getFullPanel ()
    {
	JPanel panel = new JPanel ();
	panel.setLayout (new BorderLayout());
	panel.add (this, BorderLayout.CENTER); 
	panel.add (makeBottomPanel(), BorderLayout.SOUTH);
	return panel;
    }

    JPanel makeBottomPanel ()
    {
	JPanel panel = new JPanel ();
        panel.add (modelBox);
        panel.add (new JLabel ("   #points:"));
        panel.add (pointsField);
        JButton genB = new JButton ("Generate");
        genB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a)
                {
                    generate ();
                }
            }
        );
        panel.add (genB);
        panel.add (new JLabel ("        X:"));
        panel.add (xField);
        panel.add (new JLabel (" Y:"));
        panel.add (yField);
        panel.add (new JLabel (" C:"));
        panel.add (cField);

        panel.add (new JLabel ("    "));

        JButton addB = new JButton ("Add-to-data");
        addB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a)
                {
                    addToTrainingSet ();
                }
            }
        );
        panel.add (addB);

        panel.add (new JLabel ("    "));

        JButton setB = new JButton ("Set-as-test");
        setB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a)
                {
                    setAsTest ();
                }
            }
        );
        panel.add (setB);

	return panel;
    }


    //////////////////////////////////////////////////////////////////////
    // GUI events

    
    void generate ()
    {
        setNumPoints ();
        if (numPoints <= 0) {
            return;
        }
        String str = (String) modelBox.getSelectedItem();
        if (str.equals ("Uniform")) {
            generateUniform ();
        }
        else if (str.equals ("Gaussian")) {
            generateGaussian ();
        }
        else if (str.equals ("Split-Uniform")) {
            generateSplitUniform ();
        }
        else if (str.equals ("Split-Gaussian")) {
            generateSplitGaussian ();
        }
        this.repaint ();
    }


    void setNumPoints ()
    {
        try {
            int n = Integer.parseInt (pointsField.getText());
            numPoints = n;
        }
        catch (NumberFormatException e){
            numPoints = 0;
            status.setText ("Incorrect entry in #points field");
            System.out.println (e);
        }
    }
    

    void addToTrainingSet ()
    {
        getXY ();
        if ( (currentX < 0) || (currentY < 0) || (currentClass < 0) ) {
            return;
        }
        points[currentClass].add (new PlotPoint (currentX, currentY, currentClass, false));
        this.repaint ();
    }
    

    void getXY ()
    {
        try {
            double x = Double.parseDouble (xField.getText());
            double y = Double.parseDouble (yField.getText());
            int c = Integer.parseInt (cField.getText());
            currentX = x;
            currentY = y;
            currentClass = c;
        }
        catch (NumberFormatException e){
            status.setText ("Incorrect x, y or c values");
            System.out.println (e);
            currentX = currentY = -1;
        }
        
    }
    

    void setAsTest ()
    {
        getXY ();
        if ( (currentX < 0) || (currentY < 0) || (currentClass < 0) ) {
            return;
        }
        currentTestPoint = new PlotPoint (currentX, currentY, currentClass, true);
        this.repaint ();
    }
    
} // end-PointProblem



