
public class Binomial {

    public static void main (String[] argv)
    {
        double prob = binomial (10, 0.6, 3);
        System.out.println ("Pr[X=3] = " + prob);
        Function F6 = new Function("p=0.6");
        Function F2 = new Function("p=0.2");
        for (double k = 0;k<=10;k++){
            F6.add(k,binomial (10, 0.6, (int)k));
            F2.add(k,binomial (10, 0.2, (int)k));
        }
        Function.show(F6,F2);
    }
    
    public static double binomial (int n, double p, int k)
    {
        // INSERT YOUR CODE HERE.
        double factoN = 1.0;
        double factoK = 1.0;
        double factoNK =1.0;
        for(int i=1;i<=n;i++){    
            factoN=factoN*i;    
         }    
        for(int i=1;i<=n-k;i++){    
            factoNK=factoNK*i;    
         }   
         for(int i=1;i<=k;i++){    
            factoK=factoK*i;    
         }   
        return factoN/(factoK*factoNK)*Math.pow(p, k)*Math.pow(1-p, n-k);


    }

}
