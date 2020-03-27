package javasrc.ch01_5;

/*
* Algorithm 1.5 Union-find implementation, P.221

* Proposition F. The quick-find algorithm uses one array access for each call to
find(), two array accesses for each call to connected(), and between N + 3 and
2N + 1 array accesses for each call to union() that combines two components.



usage:
% java QuickFindUF < tinyUF.txt
4 3
3 8
6 5
9 4
2 1
5 0
7 2
6 1
2 components
*/

/******************************************************************************
 *  Compilation:  javac QuickFindUF.java
 *  Execution:  java QuickFindUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/15uf/tinyUF.txt
 *                https://algs4.cs.princeton.edu/15uf/mediumUF.txt
 *                https://algs4.cs.princeton.edu/15uf/largeUF.txt
 *
 *  Quick-find algorithm.
 *
 ******************************************************************************/

import lib.StdOut;
import lib.StdIn;

public class QuickFindUF {
    private int[] id; // id[i] = component identifier of i
    private int count; // number of components

    /**
     * Initializes an empty union-find data structure with {@code n} elements
     * {@code 0} through {@code n-1}. Initially, each elements is in its own set.
     *
     * @param n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public QuickFindUF(int n) {
        count = n;
        id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i;
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find(int p) {
        validate(p);
        return id[p];
    }

    // validate that p is a valid index
    private void validate(int p) {
        int n = id.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    /**
     * Returns true if the two elements are in the same set.
     * 
     * @param p one element
     * @param q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     *         {@code false} otherwise
     * @throws IllegalArgumentException unless both {@code 0 <= p < n} and
     *                                  {@code 0 <= q < n}
     * @deprecated Replace with two calls to {@link #find(int)}.
     */
    @Deprecated
    public boolean connected(int p, int q) {
        validate(p);
        validate(q);
        return id[p] == id[q];
    }

    /**
     * Merges the set containing element {@code p} with the the set containing
     * element {@code q}.
     *
     * @param p one element
     * @param q the other element
     * @throws IllegalArgumentException unless both {@code 0 <= p < n} and
     *                                  {@code 0 <= q < n}
     */
    public void union(int p, int q) {
        validate(p);
        validate(q);
        int pID = id[p]; // needed for correctness
        int qID = id[q]; // to reduce the number of array accesses

        // p and q are already in the same component
        if (pID == qID)
            return;

        for (int i = 0; i < id.length; i++)
            if (id[i] == pID)
                id[i] = qID;
        count--;
    }

    /**
     * Reads in a an integer {@code n} and a sequence of pairs of integers (between
     * {@code 0} and {@code n-1}) from standard input, where each integer in the
     * pair represents some element; if the elements are in different sets, merge
     * the two sets and print the pair to standard output.
     * 
     * @param args the command-line arguments
     */
    // public static void main(String[] args) {
    //     int n = StdIn.readInt();
    //     QuickFindUF uf = new QuickFindUF(n);
    //     while (!StdIn.isEmpty()) {
    //         int p = StdIn.readInt();
    //         int q = StdIn.readInt();
    //         uf.union(p, q);
    //         StdOut.println(p + " " + q);
    //     }
    //     StdOut.println(uf.count() + " components");
    // }

    public static void main(String[] args){
        int n = 10;
        QuickFindUF qf = new QuickFindUF(n);
        qf.union(4, 3);
        StdOut.println("add 4 and 3, get " + qf.count() + " sets." );
        qf.union(3, 8);
        StdOut.println("add 3 and 8, get " + qf.count() + " sets." );
        qf.union(6, 5);
        StdOut.println("add 6 and 5, get " + qf.count() + " sets." ); 
        qf.union(9, 4);
        StdOut.println("add 9 and 4, get " + qf.count() + " sets." ); 
        qf.union(2, 1);
        StdOut.println("add 2 and 1, get " + qf.count() + " sets." ); 
        qf.union(8, 9);
        StdOut.println("add 8 and 9, get " + qf.count() + " sets." ); 
        qf.union(5, 0);
        StdOut.println("add 5 and 0, get " + qf.count() + " sets." ); 
        qf.union(7, 2);
        StdOut.println("add 7 and 2, get " + qf.count() + " sets." ); 
        qf.union(6, 1);
        StdOut.println("add 6 and 1, get " + qf.count() + " sets." ); 
        qf.union(1, 0);
        StdOut.println("add 1 and 0, get " + qf.count() + " sets." ); 
        qf.union(6, 7);
        StdOut.println("add 6 and 7, get " + qf.count() + " sets." );
    }

}