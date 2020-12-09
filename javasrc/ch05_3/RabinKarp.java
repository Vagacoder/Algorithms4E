package javasrc.ch05_3;

/*
 * Algorithm 5.8 Rabin-Karp fingerprint substring search. P.777
 * 
 * Property P. The Monte Carlo version of Rabin-Karp substring search is linear-
 * time and extremely likely to be correct, and the Las Vegas version of Rabin-
 * Karp sub-string search is correct and extremely likely to be linear-time.
 * 
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
        return 277;
    }

    // * Monte Carlo / Las Vegas
    public boolean check(int i, String txt){
        // * Monte Carlo
        // return true;

        // * Las Vegas
        for (int j = i; j < i+this.M+1; j++){
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

    private int search(String txt){
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

    public static void main(String[] args){

    }
}
