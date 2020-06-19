package javasrc.ch04_2;

import java.util.ArrayList;
import java.util.Collections;

import lib.*;

/*
 * 4.2.29 LCA in a DAG. Given a DAG and two vertices v and w, develop an algorithm 
 * to find a lowest common ancestor (LCA) of v and w. In a tree, the LCA of v and 
 * w is the (unique) vertex farthest from the root that is an ancestor of both v 
 * and w. In a DAG, an LCA of v and w is an ancestor of v and w that has no 
 * descendants that are also ancestors of v and w. Computing an LCA is useful in 
 * multiple inheritance in programming languages, analysis of genealogical data 
 * (find degree of inbreeding in a pedigree graph), and other applications. 
 * 
 * Hint : Define the height of a vertex v in a DAG to be the length of the longest 
 * direct path from a source (vertex with indegree 0) to v. Among vertices that 
 * are ancestors of both v and w, the one with the greatest height is an LCA of 
 * v and w.
 
 ? tinyDAG.txt schema is at P.583 

*/
public class LCA {

    private ArrayList<Integer> sources = new ArrayList<>();
    private Digraph dg;

    public LCA(Digraph dg) {
        int v = dg.V();
        this.dg = dg;

        for (int i = 0; i < v; i++) {
            if (dg.indegree(i) == 0) {
                sources.add(i);
            }
        }
    }

    public void printSources() {
        for (int s : sources) {
            StdOut.println(s);
        }
    }

    public ArrayList<LCAdata> commonAncesters(int v, int w) {
        ArrayList<LCAdata> results = new ArrayList<>();

        for (int i = 0; i < this.dg.V(); i++) {
            DepthFirstPaths dfp = new DepthFirstPaths(this.dg, i);

            if (dfp.hasPathTo(v) && dfp.hasPathTo(w)) {
                for (int s : sources) {
                    LCAdata result = new LCAdata();
                    result.source = -1;
                    result.lca = i;
                    result.distance = Integer.MAX_VALUE;
                    int distance = new DepthFirstPaths(this.dg, s).distanceTo(i);
                    if (distance > 0 && distance < result.distance) {
                        result.source = s;
                        result.distance = distance;
                        results.add(result);
                    }
                }
            }
        }
        return results;
    }

    class LCAdata implements Comparable<LCAdata> {
        int source;
        int lca;
        int distance;

        @Override
        public int compareTo(LCAdata that) {
            return this.distance - that.distance;
        }
    }

    public static void main(String[] args) {
        Digraph dg = new Digraph(new In("data/tinyDAG.txt"));
        LCA lca = new LCA(dg);
        lca.printSources();
        ArrayList<LCAdata> res = lca.commonAncesters(4, 9);
        for (LCAdata data : res) {
            StdOut.printf("From %d to %d, distance: %d\n", data.source, data.lca, data.distance);
        }
        StdOut.println();

        Collections.sort(res);
        for (LCAdata data : res) {
            StdOut.printf("From %d to %d, distance: %d\n", data.source, data.lca, data.distance);
        }
    }
}