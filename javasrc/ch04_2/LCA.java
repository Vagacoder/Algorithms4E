package javasrc.ch04_2;

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

*/
public class LCA {

    
    public LCA(Digraph dg){

    }

    public static void main(String[] args){

    }
}