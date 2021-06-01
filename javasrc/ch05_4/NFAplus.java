package javasrc.ch05_4;

/*
 ! This class is copied from NFA.java 
 * 
 * 5.4.16 Multiway or. Add multiway or to NFA. Your code should produce the machine
 * drawn below for the pattern ( . * A B ( ( C | D | E ) F ) * G ) .
 * 
 * 5.4.17 Wildcard. Add to NFA the capability to handle wildcards.
 * 
 * 5.4.18 One or more. Add to NFA the capability to handle the + closure operator.
 */

import javasrc.ch01_3.LinkedListBag;
import javasrc.ch01_3.LinkedListStack;
import javasrc.ch04_2.Digraph;
import javasrc.ch04_2.DirectedDFS;
import lib.StdOut;


public class NFAplus {

    // * Match transistions
    private char[] regexp;
    // * Epsilon transitions
    private Digraph G;
    // * number of states
    private int M;

    public NFAplus(String regexp){
        LinkedListStack<Integer> ops = new LinkedListStack<>();
        this.regexp = regexp.toCharArray();
        this.M = regexp.length();
        this.G = new Digraph(M+1);

        for (int i = 0; i < M; i++){
            // ! Ex 5.4.16 (P. 808), multiple ors
            // * left parenthesis
            int lp = i;
            if (this.regexp[i] == '(' || this.regexp[i] == '|'){
                ops.push(i);
            } else if (this.regexp[i] == ')'){
                LinkedListStack<Integer> ors = new LinkedListStack<>();
                int or = ops.pop();
                while (this.regexp[or] != '('){
                    if (this.regexp[or] == '|'){
                        ors.push(or);
                        or = ops.pop();
                    }else {
                        assert false;
                    }
                }
                lp = or;
                while (!ors.isEmpty()){
                    or = ors.pop();
                    this.G.addEdge(lp, or+1);
                    this.G.addEdge(or, i);
                }
            }

            // * Closure
            // * closure operator (uses 1-character lookahead)
            // * single-character closure: lp = i; closure expression: lp = or 
            if (i < M-1 && this.regexp[i+1] == '*') {
                this.G.addEdge(lp, i+1);
                this.G.addEdge(i+1, lp);
            }

            // * for '(', '*', ')', there is always red transition to next
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
        // ! this.G contains only edeges of empty-transition, no any match transitions
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
        // String regexp = "((A*B|AC)D)";

        // ! these below are using multiway or, for Ex. 5.4.16 (P.808)
        // String regexp = "(A|BC|D)";
        // String regexp = "(A|AB|D|AABD)";
        // String regexp = "((A|B|AA|AB)(B|E|F)(C|D|E|F|G))";
        // String regexp = "((A|B|AA|AB)(|E|F)(C|D|E|F|G))";
        // String regexp = "((((A|B)*|CD*|EFG)*)*)";
        String regexp = "(.*AB((C|D|E)F)*G)";

        // String txt = args[1];
        // String txt = "AABD";
        // String txt = "1123ABCFDFEFG";
        String txt = "1ABCFDEFG";
        NFAplus nfa = new NFAplus(regexp);
        StdOut.println(nfa.recognizes(txt));
    }
}
