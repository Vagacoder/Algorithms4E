package javasrc.ch01_5;

/*
1.5.14 Weighted quick-union by height. 

Develop a UF implementation that uses the same basic strategy as weighted quick-
union but keeps track of tree height and always links the shorter tree to the 
taller one. Prove a logarithmic upper bound on the height of the trees for N 
sites with your algorithm.

*/

import lib.StdOut;

public class treeHeightWQU{

    private int[] parent;
    private int[] height;
    private int count;

    public treeHeightWQU(int n){
        this.parent = new int[n];
        this.height = new int[n];
        for (int i =0 ; i< n; i++){
            this.parent[i] = i;
            this.height[i] = 1;
        }
        this.count = n;
    }
    
    public void validate(int p){
        int n = this.parent.length;
        if (p < 0 || p > n){
            throw new IllegalArgumentException("index must between 0 and " + (n-1));
        }
    }

    public int count(){
        return this.count;
    }

    public int find(int p){
        while(p!= this.parent[p]){
            p = this.parent[p];
        }
        return p;
    }

    public boolean isConnected(int p, int q){
        validate(p);
        validate(q);
        return find(p) == find(q);
    }

    public void union(int p, int q){
        validate(p);
        validate(q);
        int rootP = find(p);
        int rootQ = find(q);
        if( rootP == rootQ){return;}
        else{
            if(this.height[rootP] < this.height[rootQ]){
                this.parent[rootP] = rootQ;
            } else if (this.height[rootP] > this.height[rootQ]){
                this.parent[rootQ] = rootP;
            } else {
                this.parent[rootP] = rootQ;
                height[rootQ]++;
            }
        }
        count--;
    }

    public static void main(String[] args){
        int n = 10;
        treeHeightWQU pc = new treeHeightWQU(n);
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