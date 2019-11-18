package javasrc.ch00;

import java.util.regex.*;
import lib.StdOut;

public class RexTester01{

public static void main(String[] args){
    Pattern p = Pattern.compile("a*b");
    Matcher m = p.matcher("aaaaaab");
    StdOut.println("Find a*b in aaaaaab: " + m.matches());
    StdOut.println(Pattern.matches("a*b", "aaaaab"));
}

}