package javasrc.ch05_3;

/*
 * Ex 5.3.15 Develop a brute-force substring search implementation BruteForceRL 
 * that processes the pattern from right to left (a simplified version of 
 * Algorithm 5.7).
 * 
 */

import lib.*;

public class BruteForceRL {
    
    public static int search (String pattern, String txt){
        int M = pattern.length();
        int N = txt.length();

        for (int i = 0; i <= N - M; i++){
            int j;
            for(j = M-1; j >= 0; j--){
                if (txt.charAt(i+j) != pattern.charAt(j)){
                    break;
                }
            }
            if (j < 0){
                return i;
            }
        }
        return N;
    }


    public static void main(String[] args){
        String text = "This is a good day to die, a day.";
        String pattern = "a";
        int index = search(pattern, text);
        StdOut.printf("Index of pattern \"%s\" at: %d", pattern, index);
        StdOut.println();
    }
}
