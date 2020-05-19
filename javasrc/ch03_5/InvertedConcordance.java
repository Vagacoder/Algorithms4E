package javasrc.ch03_5;

import java.util.HashMap;

/*
* 3.5.21 Inverted concordance. Write a program InvertedConcordance that takes a
concordance on standard input and puts the original string on standard output stream.
Note : This computation is associated with a famous story having to do with the Dead
Sea Scrolls. The team that discovered the original tablets enforced a secrecy rule that
essentially resulted in their making public only a concordance. After a while, other re-
searchers figured out how to invert the concordance, and the full text was eventually
made public.

*/

import lib.*;

public class InvertedConcordance{

    public static void main(String[] args){
       
        HashMap<Integer, String> table = new HashMap<>();

        while(!StdIn.isEmpty()){
            String input = StdIn.readLine();
            if(input.equals("Q")){
                break;
            }
            String regex = ",\\s?|: ";
            String[] tokens = input.split(regex);
            
            // for(String s: tokens){
            //     StdOut.println(s);
            // }

            String word = tokens[0];
            for(int i = 1; i<tokens.length; i++){
                table.put(Integer.parseInt(tokens[i]), word);
            }
        }
        StdOut.println("\nPlease enter index number:");
        while(!StdIn.isEmpty()){
            String input = StdIn.readLine();
            int index = Integer.parseInt(input);
            String word = table.get(index);
            if(word != null){
                StdOut.println(word);
            }else{
                StdOut.println("No this index number");
            }
            StdOut.println("Please enter index number:");
        }
    }
}