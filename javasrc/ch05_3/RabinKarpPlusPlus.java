package javasrc.ch05_3;

/*
* Ex 5.3.21 How would you modify the Rabin-Karp algorithm to search for a given 
* pattern with the additional proviso that the middle character is a “wildcard” 
* (any text character at all can match it).
* 
* 
*/

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Random;

import lib.*;

public class RabinKarpPlusPlus {
    
    private String pattern;
    private int R = 256;
    private int M;
    private long Q;
    private long RM;
    private HashSet<Long> patHashs;

    public RabinKarpPlusPlus(String pattern, char wildcard){
        this.pattern = pattern;
        this.M = this.pattern.length();
        this.Q = longRandomPrime();
        this.RM = 1;
        for(int i = 1; i<=M-1;i++){
            RM = (this.R * RM)%this.Q;
        }
        this.patHashs = new HashSet<>();
        for (int i = 0; i < R; i++){
            String newPattern = pattern.replace(wildcard, (char)i);
            
            StdOut.println(newPattern);
            StdOut.println(pattern);

            this.patHashs.add(hash(newPattern, this.M));
        }

    }

    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    private long hash(String key, int M){
        long h = 0;
        for(int j = 0; j < M; j++){
            h = (this.R * h + key.charAt(j)) % this.Q;
        }
        return h;
    }

    private boolean check(int i, String txt){
        return true;
    }

    public int search(String txt){
        int N = txt.length();
        long txtHash = hash(txt, this.M);
        if (this.patHashs.contains(txtHash) && check(0, txt)){
            return 0;
        }
        for (int i = this.M; i < N; i++){
            txtHash = (txtHash + this.Q - RM * txt.charAt(i-M) % Q) % Q;
            txtHash = (txtHash*R + txt.charAt(i)) % Q;
            if (this.patHashs.contains(txtHash)){
                if (check(i-M+1, txt)){
                    return i-M+1;
                }
            }
        }
        return N;
    }

    public static void main(String args[]){
        String pattern = "AA*A";
        // String pattern = "ABABAC";
        String txt = "AAVRAACADABRAACAADABRA";
        RabinKarpPlusPlus rkpp = new RabinKarpPlusPlus(pattern, '*');
        StdOut.println("text:    " + txt);
        int offset = rkpp.search(txt);
        StdOut.print("pattern: ");
        for(int i = 0; i < offset; i++){
            StdOut.print(" ");
        }
        StdOut.println(pattern);
        StdOut.println();


    }

}
