package javasrc.ch3_5;

import javasrc.ch03_4.SeparateChainingHashST;
import lib.*;

/*
* Dictionary lookup P.495
*/

public class LookupCSV {
    
    public static void main(String[] args){
        In in = new In(args[0]);
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);

        SeparateChainingHashST<String, String> set = new SeparateChainingHashST<>();

        while(in.hasNextLine()){
            String line = in.readLine();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            String value = tokens[valField];
            set.put(key, value);
        }

        while(!StdIn.isEmpty()){
            String query = StdIn.readString();
            if(set.get(query) != null){
                StdOut.println(set.get(query));
            }
        }
    }


}