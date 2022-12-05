// ClassifierGUI.java
//
// Author: Rahul Simha
// Mar, 2008
//
// This is the starting point for the three classification problems.
// The basic structure is this: a "problem" instance knows how to
// generate (and display) data. The data is passed to a classifier
// algorithm, which trains on the data. Then, one can submit "queries"
// to the classifier, challenging it to classify into the correct class.

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;


public class ClassifierGUI extends JFrame {

    // Write text feedback at the top of the frame.
    JLabel status = new JLabel (" ");

    // Other GUI stuff.
    JTabbedPane tabbedPane;
    JTextField algField = new JTextField (20);
    JButton nextB = new JButton ("Classify");

    // Three problems currently supported.
    PointProblem pointProblem;
    CharProblem charProblem;
    FaceProblem faceProblem;

    // Which classifer, which problem etc. These are interfaces.
    Classifier classifier;
    ClassificationProblem problem;



    //////////////////////////////////////////////////////////////////////
    // main

    public static void main (String[] argv)
    {
	new ClassifierGUI ();
    }



    //////////////////////////////////////////////////////////////////////
    // GUI construction

    public ClassifierGUI () 
    {
	this.setSize (950, 700);
	this.setResizable (true);
	this.setBackground (Color.gray);

	pointProblem = new PointProblem (status);
	charProblem = new CharProblem (status);
	faceProblem = new FaceProblem (status);

	Container cPane  = this.getContentPane();
	cPane.add (status, BorderLayout.NORTH);
	tabbedPane = new JTabbedPane ();
	tabbedPane.addChangeListener (
	   new ChangeListener () 
	   {
	       public void stateChanged (ChangeEvent e) 
	       {
		   tabChange ();
	       }
	   }
	);

	// Add tab apps.
	tabbedPane.add ("2D-points", pointProblem.getFullPanel());
	tabbedPane.add ("Chars", charProblem.getFullPanel());
	tabbedPane.add ("Faces", faceProblem.getFullPanel());

        cPane.add (tabbedPane, BorderLayout.CENTER);
        cPane.add (makeBottomPanel(), BorderLayout.SOUTH);

	this.setVisible (true);
	problem = pointProblem;
    }



    JPanel makeBottomPanel ()
    {
        JPanel panel = new JPanel ();
        
        panel.add (new JLabel ("Algorithm: "));
        panel.add (algField);
        JButton loadB = new JButton ("Load-Alg");
        loadB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    loadAlgorithm ();
                }
            }
        );
        panel.add (loadB);

        panel.add (new JLabel ("    "));
        JButton planB = new JButton ("Train");
        panel.add (planB);
        planB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    train ();
                }
            }
        );

        panel.add (new JLabel ("    "));
        panel.add (nextB);
        nextB.addActionListener (
            new ActionListener () 
            {
                public void actionPerformed (ActionEvent a) 
                {
                    next ();
                }
            }
        );
	nextB.setEnabled (false);

        panel.add (new JLabel ("    "));
	JButton quitB = new JButton ("Quit");
	quitB.addActionListener (
	   new ActionListener () {
		   public void actionPerformed (ActionEvent a)
		   {
		       System.exit(0);
		   }
           }
        );
	panel.add (quitB);
        
        return panel;
    }
    


    //////////////////////////////////////////////////////////////////////
    // GUI event handling

    void tabChange ()
    {
	int index = tabbedPane.getSelectedIndex ();
	if (index == 0) {
	    problem = pointProblem;
	    nextB.setEnabled (false);
	}
	else if (index == 1) {
	    problem = charProblem;
	    nextB.setEnabled (false);
	}
	else if (index == 2) {
	    problem = faceProblem;
	    nextB.setEnabled (false);
	}
    }


    void loadAlgorithm ()
    {
	String className = algField.getText();
	try {
	    classifier = (Classifier) (Class.forName(className)).newInstance();
	    status.setText (className + " loaded");
	    nextB.setEnabled (false);
	}
	catch (Exception e) {
	    System.out.println (e);
	    status.setText ("Failed to load " + className);
	}
    }
    

    void train ()
    {
        // Assume that a classifier has been loaded. Get the training
        // data from the "problem" and pass it on to the algorithm.

	if (classifier != null) {
	    status.setText ("Starting training ...");
            String errorMsg = classifier.train (problem.getNumClasses(),
                                                problem.isFixedDimension(),
                                                problem.getTrainingData());
            if (errorMsg == null) {
                status.setText ("Training complete");
                nextB.setEnabled (true);
            }
            else {
                status.setText (errorMsg);
            }
	}
	else {
	    status.setText ("Error: No classifier loaded");
	}
    }
    

    void next ()
    {
        // Get a sample test point from the problem and give it
        // to the classifer. The classifer returns a classification
        // (which class), which we display.
        if (classifier == null) {
            status.setText ("No classifer loaded");
            return;
        }
        Vector<Double> sample = problem.getSample ();
        int c = classifier.classify (sample);
        status.setText ("Classfied into class " + c);
    }

}

