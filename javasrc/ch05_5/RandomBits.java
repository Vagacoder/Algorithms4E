package javasrc.ch05_5;

/*
 * Random Bits. P.817 
 */

import lib.*;

public class RandomBits {
    
    public static void main(String[] args){
        int x = 11111;
        for (int i = 0; i < 1000000; i++){
            x = x * 314159 + 218281;
            BinaryStdOut.write(x > 0);
        }
        BinaryStdOut.close();
    }
}
