package javasrc.ch01_5;

/*
1.5.12 Quick-union with path compression. 

Modify quick-union (page 224) to include path compression, by adding a loop to 
find() that links every site on the path from p to the root. Give a sequence of 
input pairs that causes this method to produce a path of length 4. 

Note : The amortized cost per operation for this algorithm is known to be logarithmic.
*/

import lib.StdOut;

public class PathCompressQU {

    private int[] parent;
    private int count;

    public PathCompressQU(int n) {
        this.parent = new int[n];
        this.count = n;
        for (int i = 0; i < n; i++) {
            this.parent[i] = i;
        }
    }

    public int count() {
        return this.count;
    }

    public void validate(int p) {
        int n = this.parent.length;
        if (p < 0 || p > n) {
            throw new IllegalArgumentException("index must between 0 and " + (n - 1));
        }
    }

    public int find(int p) {
        validate(p);
        int root = p;
        while (root != this.parent[root]) {
            root = this.parent[root];
        }
        while(p != this.parent[root]){
            int temp = p;
            p = this.parent[p];
            this.parent[temp] = root;
        }
        return root;
    }

    public boolean isConnected(int p, int q) {
        return this.find(p) == this.find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) {
            return;
        } else {
            this.parent[rootP] = this.parent[rootQ];
            this.count--;
        }
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