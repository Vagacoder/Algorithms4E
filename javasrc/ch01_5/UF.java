package javasrc.ch01_5;

// Algorithm 1.5 Union-find implementation, P.221

/******************************************************************************
 *  Compilation:  javac UF.java
 *  Execution:    java UF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Weighted quick-union by rank with path compression by halving.
 *
 *  % java UF < tinyUF.txt
 *  4 3
 *  3 8
 *  6 5
 *  9 4
 *  2 1
 *  5 0
 *  7 2
 *  6 1
 *  2 components
 *
 ******************************************************************************/
import lib.StdIn;
import lib.StdOut;

 public class UF {

    private int[] parent;
    private byte[] rank;
    private int count;

    public UF(int n){
        if (n < 0) throw new IllegalArgumentException();
        this.count = n;
        this.parent = new int[n];
        this.rank = new byte[n];
        for(int i = 0; i < n; i++){
            this.parent[i] = i;
            this.rank[i] = 0;
        }
    }

    public int find(int p){
        this.validate(p);
        while (p != this.parent[p]){
            this.parent[p] = this.parent[this.parent[p]];
            p = this.parent[p];
        }
        return p;
    }

    public int count(){
        return count;
    }

    public boolean connected(int p, int q){
        return find(p) == find(q);
    }

    public void union(int p, int q){
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return ;

        if (this.rank[rootP] < this.rank[rootQ]){
            this.parent[rootP] = rootQ;
        } else if ( this.rank[rootP] > this.rank[rootQ]){
            this.parent[rootQ] = rootP;
        } else{
            this.parent[rootQ] = rootP;
            this.rank[rootP]++;
        }
        this.count--;
    }

    public void validate(int p){
        int n = parent.length;
        if (p < 0 || p >= n){
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n-1));
        }
    }

    public static void main(String[] args){
        int n = StdIn.readInt();
        UF uf = new UF(n);
        while(!StdIn.isEmpty()){
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.find(p) == uf.find(q)){
                continue;
            }
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }

 }
