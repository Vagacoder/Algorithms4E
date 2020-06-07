package javasrc.ch04_1;

import lib.*;

/*
 * 4.1.26 Write a SymbolGraph client like DegreesOfSeparation that uses depth-first
 * search instead of breadth-first search to find paths connecting two performers, 
 * producing output like that shown on the facing page.
 * 
// ! this program has too many recursive calling resulting in stack overflow, it
   ! needs a stack size > 1500k to run it.
   ! use this in CLI: java -Xmx200m -Xss2000k -cp "./:./lib" javasrc/ch04_1/DegreesOfSeparationDFS data/movies.txt "/" "Bacon, Kevin" 
 
*/
public class DegreesOfSeparationDFS {
    
    public static void main(String[] args){
        // SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        SymbolGraph sg = new SymbolGraph("data/movies.txt", "/");
        
        Graph g = sg.G();

        // String source = args[2];
        String source = "Bacon, Kevin";

        if(!sg.contains(source)){
            StdOut.println(source + " not found in database.");
            return;
        }

        int s = sg.index(source);
        DepthFirstPaths dfp = new DepthFirstPaths(g, s);

        // while(!StdIn.isEmpty()){
            // String input = StdIn.readLine();
            String input = "Kidman, Nicole";

            if(sg.contains(input)){
                int inputIndex = sg.index(input);
                if(dfp.hasPathTo(inputIndex)){
                    for(int i: dfp.pathTo(inputIndex)){
                        StdOut.println("\t" + sg.name(i));
                    }

                }else{
                    StdOut.println(input + " is not connected.");
                }
            }else{
                StdOut.println(input + " not in database.");
            }
        // }
    }
}