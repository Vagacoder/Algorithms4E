package javasrc.ch05_3;

/*
 * Ex 5.3.1 Develop a brute-force substring search implementation Brute, using 
 * the same API as Algorithm 5.6.
 * 
 * Ex 5.3.14 Develop versions of the substring search implementations in this 
 * section that use char[] instead of String to represent the pattern and the text.
 * 
 */

import lib.*;

public class Brute {

    private String pattern;
    private char[] cPattern;

    public Brute(String pattern){
        this.pattern = pattern;
    }

    public Brute(char[] pattern){
        this.cPattern = pattern;
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

    public int search(char[] txt){
        int N = txt.length;
        int M = this.cPattern.length;

        for (int i = 0; i < N-M+1; i++){
            int j;
            for(j=0; j < M;j++){
                if (txt[i+j] != this.cPattern[j]){
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

        char[] txt = text.toCharArray();
        Brute b1 = new Brute("day".toCharArray());
        index = b1.search(txt);
        StdOut.println(index);

    }

}
