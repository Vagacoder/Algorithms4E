package javasrc.ch3_5;

import java.util.HashSet;

import lib.*;


public class WhiteFilter {
    
    public static void main(String[] args){
        HashSet<String> set = new HashSet<>();
        
        In in = new In(args[0]);
        while(!in.isEmpty()){
            set.add(in.readString());
        }
        in.close();

        while(!StdIn.isEmpty()){
            String word = StdIn.readString();
            if(set.contains(word)){
                StdOut.println(word);
            }
        }
    }
}