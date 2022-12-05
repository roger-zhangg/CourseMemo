// CharProblem.java. Also: ScribblePanel, DrawPanel.
//
// Author: Rahul Simha
// Mar, 2008.
//
// This file has three Java classes related to the character
// recognition problem:
//    CharProblem - this implements the ClassificationProblem interface.
//    DrawPanel - handles drawing for a single character. 
//    ScribblePanel - handles the GUI events related to multiple chars.
// The key data structure is:
//    Vector<LineSegmentd> segments;
// Each char is a collection (Vector) of tiny line segments (each of which is
// represented by a LineSegmentd instance. Such a collection is transformed
// into an n-dim point in the file CharFeatures.java. Note that we have
// no way of restricting the size of a particular char drawing. This is an
// example where each sample can have different dimension.
//
// Related: CharFeatures.java


import java.util.*;
import java.io.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;


public class CharProblem extends JPanel implements ClassificationProblem {

    // The current data has A's and B's. The code is written generally,
    // to support additional classes.
    static int numClasses = 2;

    // All the data is read from this directory. It's assumed that all
    // the training data will be under this directory under sub-directories
    // called "class0", "class1" etc. See the data to understand the (ugly)
    // structure.
    static String dirPath = "data/charProblem1/training/";

    // GUI stuff.
    JLabel status;
    ScribblePanel leftPanel, rightPanel;

    // Feature extractor.
    CharFeatures features = CharFeatures.getInstance ();
    


    public CharProblem (JLabel status)
    {
	this.status = status;
    }


    //////////////////////////////////////////////////////////////////////
    // ClassificationProblem interface

    public int getNumClasses ()
    {
        return numClasses;
    }
    

    public boolean isFixedDimension ()
    {
        return false;
    }
    

    public ArrayList<Vector<Double>>[] getTrainingData ()
    {
        if (! leftPanel.isComplete() ) {
            status.setText ("Data for all classes not loaded");
            return null;
        }
        
        // All the dirty work of feature extraction is done in CharFeatures.java.
        return features.extractSet (numClasses, leftPanel.classData);
    }
    
    
    public Vector<Double> getSample ()
    {
        if ( (rightPanel.drawPanel.segments == null) ||
             (rightPanel.drawPanel.segments.size() == 0) ) {
            return null;
        }
        
        return features.extractSingle (rightPanel.drawPanel.segments);
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
    }

    //////////////////////////////////////////////////////////////////////
    // GUI construction

    public JPanel getFullPanel ()
    {
	JPanel panel = new JPanel ();
	panel.setLayout (new GridLayout(1,2));
        leftPanel = new ScribblePanel (true, numClasses, status);
        panel.add (leftPanel);
        rightPanel = new ScribblePanel (false, numClasses, status);
        panel.add (rightPanel);
	return panel;
    }

} // end-CharProblem




class ScribblePanel extends JPanel {

    int numClasses;

    // The current class and sample within that class.
    int currentSample = 0;
    int currentClass = 0;

    // GUI stuff.
    JLabel status;
    boolean isFullPanel = false;
    JTextField classField = new JTextField (2);
    DrawPanel drawPanel;

    // The data.
    ArrayList<Vector<LineSegmentd>>[] classData;


    public ScribblePanel (boolean isFullPanel, int numClasses, JLabel status)
    {
        this.isFullPanel = isFullPanel;
        this.numClasses = numClasses;
        this.status = status;
        classData = new ArrayList [numClasses];
        for (int c=0; c<numClasses; c++) {
            classData[c] = new ArrayList<Vector<LineSegmentd>>();
        }
        
        this.setLayout (new BorderLayout());
        drawPanel = new DrawPanel ();
        drawPanel.setBorder (BorderFactory.createLineBorder (Color.black));
        this.add (drawPanel, BorderLayout.CENTER);
        if (isFullPanel) {
            this.add (makeFullControls(), BorderLayout.SOUTH);
        }
        else {
            this.add (makeSubControls(), BorderLayout.SOUTH);
        }
    }
    

    JPanel makeFullControls ()
    {
        JPanel panel = new JPanel ();
        panel.setLayout (new GridLayout (2,1));

        JPanel topPanel = new JPanel ();
        JButton clearB = new JButton ("Clear");
        clearB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    clear ();
                }
            }
        );
        topPanel.add (clearB);

        topPanel.add (new JLabel ("   "));
        JButton addB = new JButton ("Add-to-class");
        addB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    add ();
                }
            }
        );
        topPanel.add (addB);
        panel.add (topPanel);
        
        topPanel.add (new JLabel ("C:"));
        topPanel.add (classField);

        topPanel.add (new JLabel ("       "));
        JButton saveB = new JButton ("Save");
        saveB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    save ();
                }
            }
        );
        topPanel.add (saveB);


        JPanel botPanel = new JPanel ();
        botPanel.add (new JLabel ("   "));
        JButton loadB = new JButton ("Load");
        loadB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    load ();
                }
            }
        );
        botPanel.add (loadB);


        botPanel.add (new JLabel ("      "));
        JButton prevprevB = new JButton ("<<");
        prevprevB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    prevClass ();
                }
            }
        );
        botPanel.add (prevprevB);
        JButton prevB = new JButton ("<");
        prevB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    prev ();
                }
            }
        );
        botPanel.add (prevB);

        JButton nextB = new JButton (">");
        nextB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    next ();
                }
            }
        );
        botPanel.add (nextB);
        JButton nextnextB = new JButton (">>");
        nextnextB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    nextClass ();
                }
            }
        );
        botPanel.add (nextnextB);


        panel.add (botPanel);
        return panel;
    }
    

    JPanel makeSubControls ()
    {
        JPanel panel = new JPanel ();
        panel.setLayout (new GridLayout (2,1));

        JPanel topPanel = new JPanel ();
        JButton clearB = new JButton ("Clear");
        clearB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    clear ();
                }
            }
        );
        topPanel.add (clearB);

        panel.add (topPanel);
        return panel;
    }
    

    void clear ()
    {
        drawPanel.clear ();
    }
    

    void add ()
    {
        if ((drawPanel.segments == null) || (drawPanel.segments.size()==0)) {
            status.setText ("Nothing to add");
            return;
        }
        // Otherwise, add
        int c = getClassNum ();
        if (c < 0) {
            return;
        }
        // Now add.
        classData[c].add (drawPanel.segments);
        status.setText ("Added char to class " + c + ": total samples=" + classData[c].size());
    }
    
    void load ()
    {
        try {
            for (int c=0; c<numClasses; c++) {
                String dirPath = CharProblem.dirPath + "class" + c + "/";
                // First read metadata.
                Scanner scanner = new Scanner (new FileInputStream (dirPath+"metadata.txt"));
                if (! scanner.hasNextInt()) {
                    status.setText ("ERROR: cannot read metadata");
                    return;
                }
                int numSamples = scanner.nextInt ();
                classData[c] = new ArrayList<Vector<LineSegmentd>> ();
                for (int i=0; i<numSamples; i++) {
                    Vector<LineSegmentd> segments = readFromFile (dirPath+i+".txt");
                    if (segments != null) {
                        classData[c].add (segments);
                    }
                }
            }
            currentClass = 0;
            currentSample = 0;
            getFromClassAndSample (currentClass, currentSample);
        }
        catch (IOException e) {
            System.out.println (e);
        }
    }


    Vector<LineSegmentd> readFromFile (String fileName)
        throws IOException
    {
        Scanner scanner = new Scanner (new File(fileName));
        Vector<LineSegmentd> segments = new Vector<LineSegmentd>();
        while (scanner.hasNextDouble()) {
            double x1 = scanner.nextDouble();
            double y1 = scanner.nextDouble();
            double x2 = scanner.nextDouble();
            double y2 = scanner.nextDouble();
            LineSegmentd L = new LineSegmentd (x1,y1, x2,y2);
            segments.add (L);
        }
        return segments;
    }

    
    void save ()
    {
        try {
            if (! isComplete()) {
                status.setText ("Not every class has data: cannot save");
                return;
            }
            
            for (int c=0; c<numClasses; c++) {
                String dirPath = CharProblem.dirPath + "class" + c + "/";
                for (int i=0; i<classData[c].size(); i++) {
                    Vector<LineSegmentd> segments = classData[c].get (i);
                    writeToFile (dirPath+i+".txt",segments);
                }
                // Next, write # samples to a text file: meta.data
                PrintWriter pw = new PrintWriter (new FileWriter (dirPath+"metadata.txt"));
                pw.println (classData[c].size());
                pw.close ();
            }
            status.setText ("Wrote " + numClasses + "  char files to " + CharProblem.dirPath);
        }
        catch (IOException e) {
            System.out.println (e);
        }
    }
    

    void writeToFile (String fileName, Vector<LineSegmentd> segments)
        throws IOException
    {
        PrintWriter pw = new PrintWriter (new FileWriter (fileName));
        for (LineSegmentd L: segments) {
            pw.println (L.x1 + " " + L.y1 + " " + L.x2 + " " + L.y2);
        }
        pw.close();
    }


    void prev ()
    {
        if (currentSample >= 1) {
            currentSample--;
        }
        else {
            currentSample = classData[currentClass].size() - 1;
        }
        
        getFromClassAndSample (currentClass, currentSample);
    }
    

    void next ()
    {
        if (currentSample < classData[currentClass].size()-1) {
            currentSample++;
        }
        else {
            currentSample = 0;
        }
        
        getFromClassAndSample (currentClass, currentSample);
    }


    void getFromClassAndSample (int c, int s)
    {
        Vector<LineSegmentd> segments = classData[c].get (s);
        drawPanel.segments = segments;
        drawPanel.startOver = false;
        drawPanel.repaint ();
        status.setText ("Char segments from class " + c + ", file " + currentSample + ".txt");
    }
    

    int getClassNum ()
    {
        int c = -1;
        try {
            c = Integer.parseInt (classField.getText());
        }
        catch (NumberFormatException e) {
            status.setText ("Improper class number");
            System.out.println (e);
        }
        return c;
    }
    

    boolean isComplete ()
    {
        // Check that each class has something.
        boolean isComplete = true;
        for (int c=0; c<numClasses; c++) {
            if (classData[c].size() <= 0) {
                isComplete = false;
            }
        }
        return isComplete;
    }
    

    void prevClass ()
    {
        if (currentClass == 0) {
            currentClass = numClasses - 1;
        }
        else {
            currentClass --;
        }
        currentSample = 0;
        getFromClassAndSample (currentClass, currentSample);
    }
    
    void nextClass () 
    {
        if (currentClass == numClasses-1) {
            currentClass = 0;
        }
        else {
            currentClass ++;
        }
        currentSample = 0;
        getFromClassAndSample (currentClass, currentSample);
    }

} // end-ScribblePanel


class DrawPanel extends JPanel implements MouseInputListener {

    // The data: a single char.
    Vector<LineSegmentd> segments;

    double prevX = -1, prevY = -1;
    boolean startOver = true;
    boolean startNewSequence = true;

    public DrawPanel ()
    {
        this.addMouseListener (this);
        this.addMouseMotionListener (this);
    }


    public void clear ()
    {
        segments = null;
        startOver = true;
        startNewSequence = true;
        this.repaint ();
    }
    

    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        
	Dimension D = this.getSize ();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        if (startOver) {
            return;
        }

        g.setColor (Color.blue);
        for (LineSegmentd L: segments) {
            int x1 = (int) L.x1;
            int y1 = D.height - (int) L.y1;
            int x2 = (int) L.x2;
            int y2 = D.height - (int) L.y2;
            g.drawLine (x1,y1, x2,y2);
        }
        
    }

    //////////////////////////////////////////////////////////////////
    // MouseInputListener interface

    public void mouseClicked (MouseEvent e) {}
    public void mouseMoved (MouseEvent e) {}
    public void mouseEntered (MouseEvent e) {}
    public void mouseExited (MouseEvent e) {}
    public void mousePressed (MouseEvent e) {}

    public void mouseDragged (MouseEvent e) 
    {
	Dimension D = this.getSize ();
        double x = e.getX();
        double y = D.height - e.getY();
        if ((startOver) || (startNewSequence)) {
            prevX = x;
            prevY = y;
            if (startOver) {
                segments = new Vector<LineSegmentd>();
            }
            startOver = false;
            startNewSequence = false;
            return;
        }

        segments.add (new LineSegmentd(prevX,prevY,x,y));
        prevX = x;
        prevY = y;
        this.repaint ();
    }

    public void mouseReleased (MouseEvent e) 
    {
        startNewSequence = true;
    }
    
}


