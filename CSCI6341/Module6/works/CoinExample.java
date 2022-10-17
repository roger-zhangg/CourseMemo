
public class CoinExample {

    public static void main (String[] argv)
    {
        // Flip a coin 5 times.
        Coin coin = new Coin ();
        int[] counter = new int[10];
        for (int i=0; i<10000; i++) {
            int c = coin.flip ();
            counter[c]++;                           // Returns 1 (heads) or 0 (tails).
            
        }
        System.out.println ("Flip " + 0 + ": " + counter[0]);
        System.out.println ("Flip " + 1 + ": " + counter[1]);
    }
 
}
