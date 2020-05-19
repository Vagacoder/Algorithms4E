package javasrc.ch03_5;

import javasrc.ch03_1.SequentialSearchST;
import javasrc.ch03_4.SeparateChainingHashST;

/*
* 3.5.20 Concordance. Write an ST client Concordance that puts on standard output a
concordance of the strings in the standard input stream (see page 498).

*/

import lib.*;

public class Concordance{

    public static void main(String[] args){

        In in = new In("data/tale.txt");
        SeparateChainingHashST<String, SequentialSearchST<Integer, String>> table =
         new SeparateChainingHashST<>();

        int index = 0;
        while(in.hasNextLine()){
            String[] temp = in.readLine().split(" ");
            for(int i = 0; i< temp.length;i++){
                String key = temp[i];
                if(table.get(key) == null){
                    table.put(key, new SequentialSearchST<Integer, String>());
                }
                table.get(key).put(index, key);
                index++;
            }
        }
        in.close();

        int i = 0;
        for(String key : table.keys()){
            StdOut.println(key);
            i++;
        }
        StdOut.println("Has " + i + " words");

        
        StdOut.println("\nPlease enter a word for query:");
        while(!StdIn.isEmpty()){
            String input = StdIn.readString();
            SequentialSearchST<Integer, String> st = table.get(input);
            if(st != null){
                StdOut.print(input+ ": ");
                for(int key: st.keys()){
                    StdOut.print(key + ", ");
                }
                StdOut.println("\n");
            }else{
                StdOut.println("No such word.\n");
            }
        StdOut.println("Please enter a word for query:");
        }
        

    }
}