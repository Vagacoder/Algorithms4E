package javasrc.ch3_5;

import java.util.HashSet;
import lib.*;

public class DeDup {
    
    public static void main(String[] args){
        HashSet<String> set = new HashSet<>();
        while(!StdIn.isEmpty()){
            String key = StdIn.readString();
            if(!set.contains(key)){
                set.add(key);
                StdOut.println(key);
            }
        }
    }
}