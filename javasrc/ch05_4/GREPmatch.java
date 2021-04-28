package javasrc.ch05_4;

/*
 * 5.4.7  Modify the GREP client on page 804 to be a client GREPmatch that encloses 
 * the pattern in parentheses but does not add .* before and after the pattern, 
 * so that it prints out only those lines that are strings in the language described 
 * by the given RE.
 */

import lib.StdOut;
import lib.In;
import lib.StdIn;
import java.io.File;



public class GREPmatch {
    
    public static void main(String[] args){

        // StdOut.println("Please enter regex pattern: ");
        // String input = StdIn.readLine();

        String input = "(A|B)(C|D)";
        // String input = "A(B|C)*D)";
        // String input = "(A*B|AC)D)";

        String regexp = "(" + input + ")";
        NFA nfa = new NFA(regexp);
        
        // while (StdIn.hasNextLine()){
        //     String txt = StdIn.readLine();
        //     if (nfa.recognizes(txt)){
        //         StdOut.println("Found" + txt + "!");
        //     }
        // }

        String filename = "";
        File file = new File(filename);
        In in = new In(file);
        while (!in.isEmpty()){
            String line = in.readLine();
            if (nfa.recognizes(line)){
            StdOut.println("Found " + input + " in " + line + "!");
            }
        }
    }

}
