package javasrc.ch03_5;

import javasrc.ch03_4.SeparateChainingHashST;
import lib.*;

/*
* Dictionary lookup P.495

* 3.5.12 Modify LookupCSV to associate with each key all values that appear in a key-
value pair with that key in the input (not just the most recent, as in the associative-array
abstraction).

* 3.5.13 Modify LookupCSV to make a program RangeLookupCSV that takes two key val-
ues from the standard input and prints all key-value pairs in the .csv file such that the
key falls within the range specified.
*/

public class LookupCSV2 {
    
    public static void main(String[] args){
        In in = new In(args[0]);
        int keyField1 = Integer.parseInt(args[1]);
        int keyField2 = Integer.parseInt(args[2]);

        SeparateChainingHashST<String, String[]> set = new SeparateChainingHashST<>();

        while(in.hasNextLine()){
            String line = in.readLine();
            String[] tokens = line.split(",");
            String key1 = tokens[keyField1];
            String key2 = tokens[keyField2];
            set.put(key1, tokens);
            set.put(key2, tokens);
        }

        while(!StdIn.isEmpty()){
            String query = StdIn.readString();
            if(set.get(query) != null){
                StdOut.println(set.get(query));
            }
        }
    }


}