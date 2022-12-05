
public class DataExample {

    public static void main (String[] argv)
    {
        DataGenerator dGen = new DataGenerator ();
        int K = 3000;
        int[] total = new int[2];
        int[] totalc = new int[2];
        System.out.printf ("Data  Class\n");
        for (int k=0; k<K; k++) {
            dGen.generateSample ();
            //System.out.printf ("%d     %d\n", dGen.getX(), dGen.getClassNum());
            if (dGen.getX()==3){
                totalc[dGen.getClassNum()]++;
            }
            total[dGen.getClassNum()]++;
        }
        System.out.printf(""+(double)totalc[0]/total[0]+" "+ (double)totalc[1]/total[1]);
    }

}
