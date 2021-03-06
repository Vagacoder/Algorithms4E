package javasrc.ch05_3;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

import javasrc.ch01_3.LinkedListQueue;

/*
 * Algorithm 5.8 Rabin-Karp fingerprint substring search. P.777
 * 
 * Property P. The Monte Carlo version of Rabin-Karp substring search is linear-
 * time and extremely likely to be correct, and the Las Vegas version of Rabin-
 * Karp sub-string search is correct and extremely likely to be linear-time.
 * 
 * Ex 5.3.10 Add to RabinKarp a count() method to count occurrences and a searchAll()
 * method to print all occurrences.
 *  
 * Ex 5.3.12 Add the code to check() in RabinKarp (Algorithm 5.8) that turns it 
 * into a Las Vegas algorithm (check that the pattern matches the text at the 
 * position given as argument).
 * 
 * Ex 5.3.24 Find all occurrences. Add a method findAll() to each of the four 
 * substring search algorithms given in the text that returns an Iterable<Integer> 
 * that allows clients to iterate through all offsets of the pattern in the text.
 * 
 * Ex 5.3.25 Streaming. Add a search() method to KMP that takes variable of type 
 * In as argument, and searches for the pattern in the specified input stream 
 * without using any extra instance variables. Then do the same for RabinKarp.
 * 
 */

 import lib.*;

public class RabinKarp {
 
    // * Pattern, only needed fro Las Vegas
    private String pattern;
    // * pattern hash value
    private long patHash;
    // * pattern length
    private int M;
    // * a large prime
    private long Q;
    // * alphabet size
    private int R = 256;
    // * R^(M-1) % Q
    private long RM;

    public RabinKarp(String pattern){
        this.pattern = pattern;
        this.M = pattern.length();
        this.Q = longRandomPrime();
        this.RM = 1;
        for(int i = 1; i <= M-1; i++){
            RM = (this.R * RM)%this.Q;
        }
        this.patHash = hash(pattern, M);
    }

    // * Ex 5.3.33
    private long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    // * Monte Carlo / Las Vegas
    private boolean check(int i, String txt){
        // * Monte Carlo
        // return true;

        // * Las Vegas
        for (int j = i; j < i+this.M; j++){
            if (txt.charAt(j) != this.pattern.charAt(j-i)){
                return false;
            }
        }

        return true;
    }

    // * Horner's method, P.775
    private long hash(String key, int M){
        long h = 0;
        for(int j = 0; j < M; j++){
            h = (this.R * h + key.charAt(j)) % this.Q;
        }
        return h;
    }

    public int search(String txt){
        int N = txt.length();
        long txtHash = hash(txt, this.M);
        if (this.patHash == txtHash && check(0, txt)){
            return 0;
        }
        for (int i = this.M; i < N; i++){
            txtHash = (txtHash + this.Q - RM * txt.charAt(i-M) % Q) % Q;
            txtHash = (txtHash*R + txt.charAt(i)) % Q;
            if (this.patHash == txtHash){
                if (check(i-M+1, txt)){
                    return i-M+1;
                }
            }
        }
        return N;
    }

    // * 5.3.25
    public int search(In in){
        int count = 0;
        int m = this.pattern.length();
        long txtHash = 0;
        LinkedListQueue<Character> buffer = new LinkedListQueue<>();

        while (!in.isEmpty()){
            char curChar = in.readChar();
            count ++;
            buffer.enqueue(curChar);

            if (count <= m){
                txtHash = (txtHash*R + curChar) % Q;
            }else{
                char preChar = buffer.dequeue();
                txtHash = (txtHash + this.Q - RM * preChar % Q) % Q;
                txtHash = (txtHash*R + curChar) % Q;
            }
            if (this.patHash == txtHash){
                return count-m+1;
            }
        }
        return -1;
    }

    // * 5.3.10
    public int count(String txt){
        int count = 0;
        int N = txt.length();
        long txtHash = hash(txt, this.M);
        if (this.patHash == txtHash && check(0, txt)){
            count ++;
        }
        for (int i = this.M; i < N; i++){
            txtHash = (txtHash + this.Q - this.RM * txt.charAt(i-M) % this.Q) % this.Q;
            txtHash = (txtHash * this.R + txt.charAt(i)) % this.Q;
            if (this.patHash == txtHash){
                if (check(i-M+1, txt)){
                    count++;
                }
            }
        }
        return count;
    }

    // * 5.3.10
    public ArrayList<Integer> searchAll(String txt){
        ArrayList<Integer> result = new ArrayList<>();
        int N = txt.length();
        long txtHash = hash(txt, this.M);
        if (this.patHash == txtHash && check(0, txt)){
            result.add(0);
        }
        for (int i= this.M; i < N; i++){
            txtHash = (txtHash + this.Q - this.RM * txt.charAt(i-M) % this.Q) % this.Q;
            txtHash = (txtHash * this.R + txt.charAt(i)) % this.Q;
            if (this.patHash == txtHash){
                if(check(i-M+1, txt)){
                    result.add(i-M+1);
                }
            }
        }
        return result;
    }

    // * 5.3.24
    public Iterable<Integer> findAll(String txt){
        LinkedListQueue<Integer> result = new LinkedListQueue<>();
        int n = txt.length();
        long txtHash = this.hash(txt, this.M);
        if (this.patHash == txtHash && check(0, txt)){
            result.enqueue(0);
        }

        for(int i = this.M; i < n; i++){
            txtHash = (txtHash + this.Q - this.RM * txt.charAt(i-M)% this.Q) % this.Q;
            txtHash = (txtHash * this.R + txt.charAt(i)) % this.Q;
            if (this.patHash == txtHash){
                if(check(i-M+1, txt)){
                    result.enqueue(i-M+1);
                }
            }
        }
        return result;
    }

    public static void main(String[] args){
        String pattern = "AACAA";
        // String pattern = "ABABAC";
        String txt = "AAVRAACADABRAACAADABRA";
        RabinKarp rk = new RabinKarp(pattern);
        StdOut.println("text:    " + txt);
        int offset = rk.search(txt);
        StdOut.print("pattern: ");
        for(int i = 0; i < offset; i++){
            StdOut.print(" ");
        }
        StdOut.println(pattern);
        StdOut.println();

        txt = "This is a good day to die, a day.";
        pattern = "a";
        rk = new RabinKarp(pattern);
        int occurency = rk.count(txt);
        StdOut.println(occurency);

        txt = "Nice weekend is joyful.";
        pattern = "a";
        rk = new RabinKarp(pattern);
        occurency = rk.count(txt);
        StdOut.println(occurency);

        txt = "Wonderful daaaay.";
        pattern = "a";
        rk = new RabinKarp(pattern);
        occurency = rk.count(txt);
        StdOut.println(occurency);
        StdOut.println(rk.searchAll(txt));

        pattern = "aa";
        rk = new RabinKarp(pattern);
        occurency = rk.count(txt);
        StdOut.println(occurency);

        pattern = "aaa";
        rk = new RabinKarp(pattern);
        occurency = rk.count(txt);
        StdOut.println(occurency);

        pattern = "aaaa";
        rk = new RabinKarp(pattern);
        occurency = rk.count(txt);
        StdOut.println(occurency);
        StdOut.println(rk.searchAll(txt));

        pattern = "aaaaa";
        rk = new RabinKarp(pattern);
        occurency = rk.count(txt);
        StdOut.println(occurency);
        StdOut.println(rk.searchAll(txt));
    }
}
