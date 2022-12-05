// FaceProblem.java. Also: ImageBrowsePanel, ImageDisplayPanel.
//
// Author: Rahul Simha
// Mar 2008.
//
// This file has most of what's needed for the face recognition
// part of the problem: reading in the image files etc.
// The feature extraction part is in FaceFeatures.java.
// We are using data sets from: http://www.face-rec.org/.
// In particular, data/faceProblem1/ has data from 
//    http://cswww.essex.ac.uk/mv/allfaces/index.html
//
// The class ImageDisplayPanel actually displays images, while ImageBrowsePanel
// handles GUI events.
//
// Related: FaceFeatures.java, ImageTool.java

import java.util.*;
import java.io.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.*;


public class FaceProblem extends JPanel implements ClassificationProblem {

    // The code is written generally, to support more than 2 classes.
    static int numClasses = 2;

    // We assume that the training data is here:
    static String trainPath = "data/faceProblem1/training/";
    // And that the test data is here:
    static String testPath = "data/faceProblem1/test/";
    // To see the format needed, read the code in load().

    // GUI stuff.
    JLabel status;
    ImageBrowsePanel leftPanel, rightPanel;

    // Feature extractor.
    FaceFeatures features = FaceFeatures.getInstance ();


    public FaceProblem (JLabel status)
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
        return true;
    }
    

    public ArrayList<Vector<Double>>[] getTrainingData ()
    {
        if (! leftPanel.isComplete() ) {
            status.setText ("Data for all classes not loaded");
            return null;
        }

        return features.extractSet (numClasses, leftPanel.classData);
    }
    
    
    public Vector<Double> getSample ()
    {
        Image image = rightPanel.getCurrent ();
        return features.extractSingle (image);
    }
    

    //////////////////////////////////////////////////////////////////////
    // GUI construction

    public JPanel getFullPanel ()
    {
	JPanel panel = new JPanel ();
	panel.setLayout (new GridLayout(1,2));
        // The left side is the training data.
        leftPanel = new ImageBrowsePanel (numClasses, status, trainPath);
        panel.add (leftPanel);
        // Right side is test data.
        rightPanel = new ImageBrowsePanel (numClasses, status, testPath);
        panel.add (rightPanel);
	return panel;
    }
    
}



class ImageBrowsePanel extends JPanel {

    // GUI stuff.
    JLabel status;
    JTextField classField = new JTextField (2);

    // Each individual image is displayed inside this panel:
    ImageDisplayPanel displayPanel;

    // The data: a collection (ArrayList) of images for each class.
    ArrayList<Image>[] classData;

    int numClasses;
    int currentClass = 0;
    int currentSample = 0;

    // Where the data is stored:
    String dirPath;

    public ImageBrowsePanel (int numClasses, JLabel status, String dirPath)
    {
        this.numClasses = numClasses;
        this.status = status;
        this.dirPath = dirPath;

        classData = new ArrayList [numClasses];
        for (int c=0; c<numClasses; c++) {
            classData[c] = new ArrayList<Image> ();
        }
        
        this.setLayout (new BorderLayout());
        displayPanel = new ImageDisplayPanel ();
        displayPanel.setBorder (BorderFactory.createLineBorder (Color.black));
        this.add (displayPanel, BorderLayout.CENTER);
        this.add (makeControls(), BorderLayout.SOUTH);
    }


    //////////////////////////////////////////////////////////////////////
    // GUI construction

    JPanel makeControls ()
    {
        JPanel panel = new JPanel ();

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
        panel.add (loadB);

        panel.add (new JLabel ("        "));
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
        panel.add (prevprevB);
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
        panel.add (prevB);

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
        panel.add (nextB);
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
        panel.add (nextnextB);

        return panel;
    }
    

    //////////////////////////////////////////////////////////////////////
    // GUI events


    void load ()
    {
        // Load all the images.

        try {
            int numImages = 0;
            for (int c=0; c<numClasses; c++) {
                String path = dirPath + "class" + c + "/";
                // First read metadata.
                Scanner scanner = new Scanner (new FileInputStream (path+"metadata.txt"));
                if (! scanner.hasNextInt()) {
                    status.setText ("ERROR: cannot read metadata");
                    return;
                }
                int numSamples = scanner.nextInt ();
                classData[c] = new ArrayList<Image> ();
                for (int i=0; i<numSamples; i++) {
                    FileInputStream fis = new FileInputStream (path + "/" + i + ".jpg");
                    Image image = ImageIO.read (fis);
                    classData[c].add (image);
                    numImages ++;
                }
            }
            currentClass =  0;
            currentSample = 0;
            status.setText ("Read " + numImages + " images from " + numClasses + " classes");
        }
        catch (IOException e) {
            System.out.println (e);
        }
    }
    

    void getFromClassAndSample (int c, int s)
    {
        Image image = classData[c].get (s);
        displayPanel.image = image;
        displayPanel.repaint ();
        status.setText ("Image# " + s + " from class " + c);
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


    Image getCurrent ()
    {
        Image image = classData[currentClass].get (currentSample);
        return image;
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
}



class ImageDisplayPanel extends JPanel {

    Image image;
    
    public void paintComponent (Graphics g)
    {
        super.paintComponent (g);
        
	Dimension D = this.getSize ();
        g.setColor (Color.white);
        g.fillRect (0,0, D.width,D.height);
        if (image == null) {
            return;
        }
        // Note: image is scaled to panel size. But this does not
        // change the number of pixels in the Image instance.
        g.drawImage (image, 0,0, D.width, D.height, this);
    }
    
}

