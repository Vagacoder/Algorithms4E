package javasrc.ch05_4;

/*
 * Algorithm 5.9 Regular Expression Pattern Matching (grep). P.802 and P.799
 * 
 * Proposition Q. Determining whether an N-character text string is recognized by 
 * the NFA corresponding to an M-character RE takes time proportional to NM in 
 * the worst case.
 * 
 * Proposition R. Building the NFA corresponding to an M-character RE takes time 
 * and space proportional to M in the worst case.
 * 
 */

import javasrc.ch01_3.LinkedListBag;
import javasrc.ch01_3.LinkedListStack;
import javasrc.ch04_2.Digraph;
import javasrc.ch04_2.DirectedDFS;
import lib.StdOut;


public class NFA {

    // * Match transistions
    private char[] regexp;
    // * Epsilon transitions
    private Digraph G;
    // * number of states
    private int M;

    public NFA(String regexp){
        LinkedListStack<Integer> ops = new LinkedListStack<>();
        this.regexp = regexp.toCharArray();
        this.M = regexp.length();
        this.G = new Digraph(M+1);

        for (int i = 0; i < M; i++){
            // * Or expression P. 801
            // * left pointer
            int lp = i;
            if (this.regexp[i] == '(' || this.regexp[i] == '|'){
                ops.push(i);
            } else if (this.regexp[i] == ')'){
                int or = ops.pop();
                // * 2-way or operator
                if (this.regexp[or] == '|'){
                    lp = ops.pop();
                    this.G.addEdge(lp, or + 1);
                    this.G.addEdge(or, i);
                } else if (this.regexp[or] == '('){
                    lp = or;
                } else {
                    assert false;
                }
            }

            // * Closure
            // * closure operator (uses 1-character lookahead)
            if (i < M-1 && this.regexp[i+1] == '*') {
                this.G.addEdge(lp, i+1);
                this.G.addEdge(i+1, lp);
            }
            if (this.regexp[i] == '(' || this.regexp[i] == '*' || this.regexp[i] == ')'){
                this.G.addEdge(i, i+1);
            }
        }
        if (ops.size() != 0){
            throw new IllegalArgumentException("Invalid regular expression");
        }
    }

    public boolean recognizes(String txt){
        LinkedListBag<Integer> pc = new LinkedListBag<>();
        // ! this.G contains edeges of empty-transition, not match transition
        DirectedDFS dfs = new DirectedDFS(this.G, 0);
        for (int v = 0; v < this.G.V(); v++){
            if (dfs.marked(v)){
                pc.add(v);
            }
        }

        // * Computer possible NFA states for txt[i+1]
        for (int i = 0; i < txt.length(); i++){
            
            // * assert input string
            if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || 
            txt.charAt(i) == '(' || txt.charAt(i) == ')'){
                throw new IllegalArgumentException("text contains the metacharacter'" +
                txt.charAt(i) + "'");
            }

            // * Compute possible NFA states for txt[i+1]
            LinkedListBag<Integer> match = new LinkedListBag<>();
            for (int v : pc){
                if (v == M){
                    continue;
                }
                if ((this.regexp[v] == txt.charAt(i)) || this.regexp[v] == '.'){
                    match.add(v+1);
                }
            }
            
            pc = new LinkedListBag<Integer>();
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.V(); v++){
                if (dfs.marked(v)){
                    pc.add(v);
                }
            }
            if (pc.size() == 0){
                return false;
            }
        }

        // * check for accept state
        for (int v : pc){
            if (v == M){
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args){
        // String regexp = "(" + args[0] + ")";
        String regexp = "((A*B|AC)D)";
        // String txt = args[1];
        String txt = "AABD";
        NFA nfa = new NFA(regexp);
        StdOut.println(nfa.recognizes(txt));
    }
}
