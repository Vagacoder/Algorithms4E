package javasrc.ch05_4;

/*
 * 5.4.15 One-level REs. Construct a Java RE that describes the set of strings 
 * that are legal REs over the binary alphabet, but with no occurrence of parentheses 
 * within parentheses. For example, (0.*1)* | (1.*0)* is in this language, but 
 * (1(0 | 1)1)* is not.
 */

import lib.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ex5_4_15 {
    
    public static void main(String[] args){
        Pattern p1 = Pattern.compile("(0|1)(0|1)*");
        Matcher m1 = p1.matcher("0");
        StdOut.println(m1.matches());
        m1 = p1.matcher("1");
        StdOut.println(m1.matches());
        m1 = p1.matcher("01");
        StdOut.println(m1.matches());
        m1 = p1.matcher("10");
        StdOut.println(m1.matches());
        m1 = p1.matcher("11");
        StdOut.println(m1.matches());
    }

}
