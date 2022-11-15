
public class Raindrop {

    public static void main (String[] argv)
    {
        double numTrials = 100000;
        
        double s = 1;
        double p = 0.5;
        DensityHistogram dd = new DensityHistogram(-10.0,10.0,20);
        DensityHistogram tt = new DensityHistogram(8,38.0,30);
        for (int n=0; n<numTrials; n++) {
        	double h = 30;
        	double x = 0;
        	double t = 0;
        	while(h>0){
        		t++;
        		if(RandTool.uniform()<p){
        			h-=s;
        		}else if (RandTool.uniform()<0.5){
        			x-=s;
        		}else{
        			x+=s;
        		}
        		dd.add(x);
        	}
        	tt.add(t);

            // INSERT YOUR CODE HERE.

        }
        dd.display();
        tt.display();

    }

}
