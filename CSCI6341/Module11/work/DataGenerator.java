
public class DataGenerator {

    int x;                              // The data value.
    int c;                              // Which class it belongs to.

    public void generateSample ()
    {
        double z = RandTool.uniform ();
        if (z < 0.4) {
            c = 0;                      // Generate from class 0.
            x = generate0 ();
        }
        else {
            c = 1;                      // Generate from class 1.
            x = generate1 ();
        }
    }
    
    public int getX ()
    {
        return x;
    }
    
    public int getClassNum ()
    {
        return c;
    }

    int generate0 ()
    {
        double z = RandTool.uniform ();
        if (z < 0.4) {
            return 0;
        }
        else if (z < 0.7) {
            return 1;
        }
        else if (z < 0.9) {
            return 2;
        }
        else {
            return 3;
        }
    }


    int generate1 ()
    {
        double z = RandTool.uniform ();
        if (z < 0.05) {
            return 1;
        }
        else if (z < 0.15) {
            return 2;
        }
        else if (z < 0.75) {
            return 3;
        }
        else if (z < 0.85) {
            return 4;
        }
        else {
            return 5;
        }
    }
    
    double getProbXGivenC (int x, int c)
    {
	if (c == 0) {
	    if (x == 0) return 0.4;
	    else if (x == 1) return 0.3;
	    else if (x == 2) return 0.2;
	    else if (x == 3) return 0.1;
	    else return 0;
	}
	else {
	    if (x == 0) return 0;
	    else if (x == 1) return 0.05;
	    else if (x == 2) return 0.1;
	    else if (x == 3) return 0.6;
	    else if (x == 4) return 0.1;
	    else if (x == 5) return 0.15;
	    else return 0;
	}
    }

}
