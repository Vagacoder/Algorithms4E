package javasrc.ch05_3;

/*
 * Brute-force substring search. P.760
 * 
 * 
 */

import lib.*;

public class BruteForce {

    public static int search(String txt, String pattern){
        int M = pattern.length();
        int N = txt.length();

        for(int i = 0; i < N-M; i++){
            int j;
            for(j =0; j < M;j++){
                if(txt.charAt(i+j) != pattern.charAt(j)){
                    break;
                }
            }
            if(j == M){
                return i;
            }
        }
        return N;
    }

    public static void main(String[] args){
        String text = "This is a good day to die.";
        String pattern = "is";
        int index = search(text, pattern);
        StdOut.printf("Index of pattern \"%s\" at: %d", pattern, index);
        StdOut.println();
    }

}
