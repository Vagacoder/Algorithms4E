package javasrc.ch05_3;

/*
 * Ex 5.3.1 Develop a brute-force substring search implementation Brute, using 
 * the same API as Algorithm 5.6.
 * 
 * 
 */

import lib.*;

public class Brute {

    private String pattern;

    public Brute(String pattern){
        this.pattern = pattern;
    }

    public int search(String txt){
        int N = txt.length();
        int M = this.pattern.length();
        
        for (int i = 0; i < N-M+1; i++){
            int j;
            for (j = 0; j < M; j++ ){
                if (txt.charAt(i+j) != this.pattern.charAt(j)){
                    break;
                }
            }
            if (j == M){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        String text = "This is a good day";
        Brute b = new Brute("day");
        int index = b.search(text);
        StdOut.println(index);

    }

}
