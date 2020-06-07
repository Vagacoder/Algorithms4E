package javasrc.ch04_1;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lib.*;

/*
 * Degeree of Separation. P.555
 * 
 * 4.1.25 Modify DegreesOfSeparation to take an int value y as a command-line 
 * argument and ignore movies that are more than y years old.
 * 
*/

public class DegreesOfSeparation {
  
    public static void main(String[] args){
        // * 4.1.25 get year
        int yearDifference = Integer.parseInt(args[3]);

        SymbolGraph sg = new SymbolGraph(args[0], args[1]);
        Graph G = sg.G();

        String source = args[2];
        if(!sg.contains(source)){
            StdOut.println(source + " not in database.");
            return;
        }

        int s = sg.index(source);
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

        while(!StdIn.isEmpty()){
            String sink = StdIn.readLine();

            // * 4.1.25 ========================================================
            Pattern p = Pattern.compile(".*\\((\\d{4})\\)$");
            Matcher m = p.matcher(sink);
            if(m.matches()){
                int year = Integer.parseInt(m.group(1));
                int thisYear = new Date().getYear() + 1900;
                if (thisYear - year > yearDifference){
                    continue;
                }
            }
            // * another year checking should add for loop below
            // * ===============================================================

            if(sg.contains(sink)){
                int t = sg.index(sink);
                if(bfs.hasPathTo(t)){
                    for(int v: bfs.pathTo(t)){
                        StdOut.println("   " + sg.name(v));
                    }
                }else{
                    StdOut.println("Not connected.");
                }
            }else{
                StdOut.println("Not in database.");
            }
        }
    }
}