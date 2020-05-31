package javasrc.ch04_1;

import javasrc.ch03_4.SeparateChainingHashST;
import lib.*;

/*
* Symbo Graph data type. P. 552
*
* In typical real-world applications, keeping the value of V and E in the graph 
* definition file is somewhat inconvenient. Therefore, we keep them in 2 additional
* data structures, a table and an array, for quick indexing.
*/

public class SymbolGraph {

    // * key (vertex name) : index
    private SeparateChainingHashST<String, Integer> st;
    // * index => key (vertex name)
    private String[] keys;
    // * graph
    private Graph G;

    public SymbolGraph(String filename, String delim) {
        st = new SeparateChainingHashST<String, Integer>();
        In in = new In(filename);

        // ! First pass, first loop
        // * build a table of key-value pairs: key (vertex name) : index
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delim);
            for (int i = 0; i < a.length; i++) {
                if (!this.st.contains(a[i])) {
                    this.st.put(a[i], this.st.size());
                }
            }
        }

        // ! second loop
        // * build an array: index => key (vertex name)
        this.keys = new String[st.size()];
        for (String name : this.st.keys()) {
            keys[st.get(name)] = name;
        }

        // ! Second pass, third loop
        // * build a graph, which connect all vertices in one line
        this.G = new Graph(this.st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(delim);
            int v = st.get(a[0]);
            for (int i = 1; i < a.length; i++) {
                this.G.addEdge(v, st.get(a[i]));
            }
        }
    }

    // * is key a vertex, by checking table
    public boolean contains(String key) {
        return this.st.contains(key);
    }

    // * index (of graph) associated with key (vertex name), by checking table
    public int index(String key) {
        Integer result = this.st.get(key);
        if(result != null){
            return result;
        }else{
            return -1;
        }

    }

    // * key (vertex name) associated with index v (of graph), by checking array
    public String name(int v) {
        return this.keys[v];
    }

    // * underlying graph
    public Graph G() {
        return this.G;
    };

    public static void main(String[] args) {
        String filename = args[0];
        String delim = args[1];
        StdOut.println(delim);
        SymbolGraph sg = new SymbolGraph(filename, delim);

        Graph G = sg.G();

        while (StdIn.hasNextLine()) {
            String source = StdIn.readLine();
            int sIndex = sg.index(source);
            if (sIndex >= 0) {
                for (int v : G.adj(sg.index(source))) {
                    StdOut.println("    " + sg.name(v));
                }
            }else{
                StdOut.println("Not found " + source);
            }
        }
    }
}