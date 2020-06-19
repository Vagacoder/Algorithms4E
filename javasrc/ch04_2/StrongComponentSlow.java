package javasrc.ch04_2;

/*
* 4.2.31 Strong component. Describe a linear-time algorithm for computing the 
* strong component containing a given vertex v. On the basis of that algorithm, 
* describe a simple quadratic-time algorithm for computing the strong components 
* of a digraph.
* 
 
 ? Strong components of tinyDG.txt schema is at P.584

*/

import java.util.ArrayList;
import java.util.List;

import lib.*;

public class StrongComponentSlow {

    private Digraph dg;
    private int[] componentId;
    private boolean[] visited;
    private int count;

    public StrongComponentSlow(Digraph dg) {
        this.dg = dg;
        this.componentId = new int[dg.V()];
        this.visited = new boolean[dg.V()];
        groupComponent();
    }

    public List<Integer> getSC(int v) {
        ArrayList<Integer> components = new ArrayList<>();
        BreadthFirstPaths bfp = new BreadthFirstPaths(dg, v);
        for (int i = 0; i < dg.V(); i++) {
            if (bfp.hasPathTo(i)) {
                BreadthFirstPaths bfp1 = new BreadthFirstPaths(dg, i);
                if (bfp1.hasPathTo(v)) {
                    components.add(i);
                }
            }
        }
        return components;
    }

    private void groupComponent() {
        int count = 0;
        for (int v = 0; v < dg.V(); v++) {
            if(!visited[v]){
                List<Integer> component = getSC(v);
                for(int w : component){
                    visited[w] = true;
                    componentId[w] = count;
                }
                count++;
            }
        }
        this.count = count;
    }

    public boolean stronglyConnected(int v, int w) {
        return componentId[v] == componentId[w];
    }

    public int id(int v) {
        return this.componentId[v];
    }

    public int count() {
        return this.count;
    }

    public static void main(String[] args) {
        Digraph dg = new Digraph(new In("data/tinyDG.txt"));
        StrongComponentSlow scs = new StrongComponentSlow(dg);
        StdOut.println(scs.getSC(2));

        for(int i = 0; i < dg.V(); i++){
            StdOut.printf("ComponentId of %d is %d,\n", i, scs.id(i));
        }
    }
}