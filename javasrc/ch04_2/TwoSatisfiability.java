package javasrc.ch04_2;

/*
 * 4.2.34 2-satisfiability. Given a boolean formula in conjunctive normal form 
 * with M clauses and N variables such that each clause has exactly two literals 
 * (where a literal is either a variable or its negation), find a satisfying 
 * assignment (if one exists). 
 * Hint : Form the implication digraph with 2N vertices (one per literal). For 
 * each clause x + y, include edges from y' to x and from x' to y. Claim: The 
 * formula is satisfiable if and only if no literal x is in the same strong 
 * component as its negation x'. Moreover, a topological sort of the kernel DAG 
 * (contract each strong component to a single vertex) yields a satisfying assignment.
 * 

 ! 1) problem description is a little confusing for me. 
 ! 2) no input for test.

*/

public class TwoSatisfiability {
    
    // * for N variables, digraph contains 2N vertices, 0 to N-1 for x, N to 2N-1 for x'
    private Digraph dg;
    private int N;

    public TwoSatisfiability(int N){
        this.N = N;
        this.dg = new Digraph(2 * N);
    }

    // * For each clause x + y, include edges from y' to x and from x' to y.
    public void addOrClause(int x, int y){
        this.dg.addEdge(x+N, y);
        this.dg.addEdge(y+N, x);
    }

    public boolean isSatisfy(){
        KosarajuSharirSCC kscc = new KosarajuSharirSCC(this.dg);
        for(int i = 0; i < N; i++){
            if(kscc.stronglyConnected(i, i + N)){
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args){

    }
}