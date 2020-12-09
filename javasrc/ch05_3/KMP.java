package javasrc.ch05_3;

/*
 * Algorithm 5.6 Knuth-Morris-Pratt substring search. P.768
 * 
 * Proposition N. Knuth-Morris-Pratt substring search accesses no more than M + N 
 * characters to search for a pattern of length M in a text of length N.
 * 
 * 5.3.8 Add to KMP a count() method to count occurrences and a searchAll() 
 * method to print all occurrences.
 * 
 * 
*/

 import lib.*;

public class KMP {

    private String pattern;
    private int[][] dfa;

    public KMP(String pattern){
        this.pattern = pattern;
        int M = pattern.length();
        int R = 256;
        this.dfa = new int[R][M];
        this.dfa[pattern.charAt(0)][0] = 1;

        int X = 0;
        for (int j = 1; j < M; j++){
            for (int c = 0; c < R; c++){
                dfa[c][j] = dfa[c][X];
            }
            dfa[pattern.charAt(j)][j] = j+1;
            X = dfa[pattern.charAt(j)][X];
        }
    }

    public int search(String txt){
        int i = 0;
        int j = 0;
        int N = txt.length();
        int M = this.pattern.length();

        for (; i < N && j < M; i++){
            j = dfa[txt.charAt(i)][j];
        }

        if (j == M){
            return i - M;
        }else{
            return N;
        }
    }

    public int count(String txt){
        int count = 0;
        int N = txt.length();
        int M = this.pattern.length();
        int i = 0;
        int j = 0;

        while (i < N){
            


            i++;
        }

        return count;
    }
    
    public static void main(String[] args){
        // String pattern = "AACAA";
        String pattern = "ABABAC";
        String txt = "AAVRAACADABRAACAADABRA";
        KMP kmp = new KMP(pattern);
        StdOut.println("text:    " + txt);
        int offset = kmp.search(txt);
        StdOut.print("pattern: ");
        for(int i = 0; i < offset; i++){
            StdOut.print(" ");
        }
        StdOut.println(pattern);
    }
}
