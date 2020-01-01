package javasrc.ch01_5;

/*
Algorithm 1.5 Union-find implementation, P.221

usage:
% java UF < tinyUF.txt
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

import lib.StdIn;
import lib.StdOut;

public class SimpleUF {
    private int[] id;
    private int count;

    public SimpleUF(int N) {
        this.count = N;
        this.id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int count() {
        return this.count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) {
        int pID = find(p);
        int qID = find(q);
        if (pID == qID) {
            return;
        }
        for (int i = 0; i < id.length; i++) {
            if (this.id[i] == pID) {
                this.id[i] = qID;
            }
        }
        this.count--;
    }

    public static void main(String[] args) {
        int N = StdIn.readInt();
        SimpleUF uf = new SimpleUF(N);
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) {
                continue;
            }
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }
        StdOut.println(uf.count() + " components");
    }
}
