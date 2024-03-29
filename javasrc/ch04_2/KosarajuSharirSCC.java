package javasrc.ch04_2;

import lib.*;

/*
 * Algorithm 4.6 Kosaraju-Sharir algorithm for Strong Component. P.587
 
 ! Detailed explaination is on P.588. Schema at P.589.
  
 * Postorder Lemma. Let C be a strong component in a digraph G and let v be any 
 * vertex not in C. If there is an edge e pointing from any vertex in C to v, 
 * then vertex v appears before every vertex in C in the reverse postorder of G^r.
 * 
 * Proposition H. The Kosaraju–Sharir algorithm identifies the strong components 
 * of a digraph G.
 * 
 * Proposition I. The Kosaraju–Sharir algorithm uses preprocessing time and space 
 * proportional to V + E to support constant-time strong connectivity queries in 
 * a digraph.
 * 
 * 
 * 4.2.22 True or false: If we modify the Kosaraju–Sharir algorithm to run the 
 * first depth-first search in the digraph G (instead of the reverse digraph GR) 
 * and the second depth-first search in GR (instead of G), then it will still 
 * find the strong components.
 * 
*/
public class KosarajuSharirSCC {
    
    private boolean[] marked;
    private int[] componentId;
    private int count;

    public KosarajuSharirSCC(Digraph G){
        this.marked = new boolean[G.V()];
        this.componentId = new int[G.V()];
        DepthFirstOrder order = new DepthFirstOrder(G.reverse());
        for(int s: order.reversePost()){
            if(!marked[s]){
                dfs(G, s);
                count++;
            }
        }
    }

    private void dfs(Digraph G, int v){
        marked[v] = true;
        componentId[v] = count;
        for(int w: G.adj(v)){
            if(!marked[w]){
                dfs(G, w);
            }
        }
    }

    public boolean stronglyConnected(int v, int w){
        return componentId[v] == componentId[w];
    }

    public int id(int v){
        return this.componentId[v];
    }

    public int count(){
        return this.count;
    }

    public static void main(String[] args){
        Digraph g = new Digraph(new In(args[0]));

        KosarajuSharirSCC scc = new KosarajuSharirSCC(g);

        StdOut.println("component #: " + scc.count());

        for(int i = 0; i < g.V(); i++){
            StdOut.println(i + ": " + scc.id(i));
        }

        // * 4.2.22
        StdOut.println();
        KosarajuSharirSCC sccr = new KosarajuSharirSCC(g.reverse());

        StdOut.println("component #: " + sccr.count());

        for(int i = 0; i < g.V(); i++){
            StdOut.println(i + ": " + sccr.id(i));
        }
    }
}