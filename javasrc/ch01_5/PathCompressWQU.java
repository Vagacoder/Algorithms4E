package javasrc.ch01_5;

/* 
1.5.13 Weighted quick-union with path compression. 
Modify weighted quick-union (Algorithm 1.5) to implement path compression, as 
described in Exercise 1.5.12. Give a sequence of input pairs that causes this 
method to produce a tree of height 4.

Note : The amortized cost per operation for this algorithm is known to be bounded
 by a function known as the inverse Ackermann function and is less than 5 for 
 any conceivable practical value of N.

*/

import lib.StdOut;

public class PathCompressWQU{

    private int[] parent;
    private int[] size;
    private int count;

    public PathCompressWQU(int n ){
        this.count = n;
        this.parent = new int[n];
        this.size = new int[n];
        for(int i = 0; i< n; i++){
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int count(){
        return this.count;
    }

    public void validate(int p){
        int n = this.parent.length;
        if ( p < 0 || p > n){
            throw new IllegalArgumentException("index must between 0 and "+(n-1));
        }
    }

    public boolean isConnected(int p, int q){
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    public int find(int p){
        validate(p);
        int root = p;
        while (root != parent[root]){
            root = parent[root];
        }
        while(p != this.parent[root]){
            int temp = p;
            p = this.parent[p];
            this.parent[temp] = root;
        }
        return root;
    }

    public void union(int p, int q){
        validate(p);
        validate(q);
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ){
            return;
        } else {
            if(this.size[rootP] < this.size[rootQ]){
                this.parent[rootP] = rootQ;
                this.size[rootQ] += this.size[rootP];
            }else {
                this.parent[rootQ] = rootP;
                this.size[rootP] += this.size[rootQ];
            }
        }
        this.count--;
    }

    public static void main(String[] args){
        int n = 10;
        PathCompressQU pc = new PathCompressQU(n);
        pc.union(4, 3);
        StdOut.println("add 4 and 3, get " + pc.count() + " sets." );
        pc.union(3, 8);
        StdOut.println("add 3 and 8, get " + pc.count() + " sets." );
        pc.union(6, 5);
        StdOut.println("add 6 and 5, get " + pc.count() + " sets." ); 
        pc.union(9, 4);
        StdOut.println("add 9 and 4, get " + pc.count() + " sets." ); 
        pc.union(2, 1);
        StdOut.println("add 2 and 1, get " + pc.count() + " sets." ); 
        pc.union(8, 9);
        StdOut.println("add 8 and 9, get " + pc.count() + " sets." ); 
        pc.union(5, 0);
        StdOut.println("add 5 and 0, get " + pc.count() + " sets." ); 
        pc.union(7, 2);
        StdOut.println("add 7 and 2, get " + pc.count() + " sets." ); 
        pc.union(6, 1);
        StdOut.println("add 6 and 1, get " + pc.count() + " sets." ); 
        pc.union(1, 0);
        StdOut.println("add 1 and 0, get " + pc.count() + " sets." ); 
        pc.union(6, 7);
        StdOut.println("add 6 and 7, get " + pc.count() + " sets." );
    }
}