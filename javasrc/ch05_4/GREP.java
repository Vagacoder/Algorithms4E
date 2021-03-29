package javasrc.ch05_4;

import edu.princeton.cs.algs4.StdIn;

/*
 * P. 804 Gerneralized Regular Expression Pattern-matching NFA client
 */

import lib.StdOut;

public class GREP {
    
    public static void main(String[] args){

        StdOut.println("Please enter regex pattern: ");
        String input = StdIn.readLine();

        String regexp = "(.*" + input + ".*)";
        NFA nfa = new NFA(regexp);
        while (StdIn.hasNextLine()){
            String txt = StdIn.readLine();
            if (nfa.recognizes(txt)){
                StdOut.println("Found " + txt + "!");
            }
        }
    }

}
