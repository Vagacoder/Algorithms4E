package javasrc.ch05_3;


/*
* Ex 5.3.20 How would you modify the Rabin-Karp algorithm to determine whether 
* any of a subset of k patterns (say, all of the same length) is in the text?
* 
* 
* 
*/

import java.util.ArrayList;
import java.util.HashSet;
import lib.*;


public class RabinKarpPlus {
    
    private ArrayList<String> patterns;
    private HashSet<Long> patHashes;
    private int M;
    private long Q;
    private int R = 256;
    private long RM;

    public RabinKarpPlus(ArrayList<String> patterns){
        this.patterns = patterns;
        this.M = this.patterns.get(0).length();
        this.Q = longRandomPrime();
        this.RM = 1;
        for(int i = 1; i < M; i++){
            RM = (this.R * RM) % this.Q;
        }
        this.patHashes = new HashSet<>();
        for(String pattern : patterns){
            this.patHashes.add(hash(pattern, M));
        }
    }

    public boolean search(String txt){
        int N = txt.length();
        long txtHash = hash(txt, this.M);
        if(this.patHashes.contains(txtHash)){
            return true;
        }
        for (int i = this.M; i < N; i++){
            txtHash = (txtHash + this.Q - RM * txt.charAt(i-M) % this.Q) % this.Q;
            txtHash = (txtHash * R + txt.charAt(i)) % this.Q;
            if (this.patHashes.contains(txtHash)){
                return true;
            }
        }
        return false;
    }


    private long longRandomPrime(){
        return 277;
    }

    private long hash(String key, int M){
        long h = 0;
        for(int j = 0; j < M; j++){
            h = (this.R * h + key.charAt((j))) % this.Q;
        }
        return h;
    }

    public static void main(String[] args){
        ArrayList<String> patterns = new ArrayList<>();
        patterns.add("AACAA");
        patterns.add("ABCAA");
        patterns.add("ABCAB");
        String txt = "AACCABCABBCAD";
        RabinKarpPlus rkp = new RabinKarpPlus(patterns);
        StdOut.println(rkp.search(txt));

    }
}
