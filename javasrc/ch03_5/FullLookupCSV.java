package javasrc.ch03_5;

import edu.princeton.cs.algs4.SeparateChainingHashST;

/*
* 3.5.22 Fully indexed CSV. Implement an ST client FullLookupCSV that builds an ar-
ray of ST objects (one for each field), with a test client that allows the user to specify the
key and value fields in each query.

*/

import lib.*;

public class FullLookupCSV{
    

    public static void main(String[] args){

        In in = new In(args[0]);
        int keyField = Integer.parseInt(args[1]);

        SeparateChainingHashST<String, String[]> table = new SeparateChainingHashST<>();

        while(in.hasNextLine()){
            String line = in.readLine();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            table.put(key, tokens);
        }

        while(!StdIn.isEmpty()){
            String query = StdIn.readString();
            String[] strings = table.get(query);
            if(strings!=null){
                for(String s: strings){
                    StdOut.print(s + ", ");
                }
                StdOut.println();
            }
        }
    
    }
}