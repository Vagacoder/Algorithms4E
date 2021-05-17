package javasrc.ch05_4;

/*
 * Ex 5.4.13.
 * Challenging REs. Construct an RE that describes each of the following sets of 
 * strings over the binary alphabet:
 * a. All strings except 11 or 111 
 * b. Strings with 1 in every odd-number bit position 
 * c. Strings with at least two 0s and at most one 1 
 * d. Strings with no two consecutive 1s
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import lib.*;

public class ex5_4_13 {
    
    public static void main(String[] args){
        StdOut.println("a. All string except 11 or 111");
        Pattern p1 = Pattern.compile("((0|10)(0|1)*)|(1)|(11111*)");
        Matcher m1 = p1.matcher("10111");
        StdOut.println(m1.matches());
        m1 = p1.matcher("00111");
        StdOut.println(m1.matches());
        m1 = p1.matcher("0111");
        StdOut.println(m1.matches());
        m1 = p1.matcher("110111");
        StdOut.println(m1.matches());
        m1 = p1.matcher("1111");
        StdOut.println(m1.matches());
        m1 = p1.matcher("111");
        StdOut.println(m1.matches());
        m1 = p1.matcher("11");
        StdOut.println(m1.matches());

        StdOut.println("b. String with 1 in every odd-numberbit position");
        Pattern p2 = Pattern.compile("(0|1)1((0|1)1)*");
        Matcher m2 = p2.matcher("010111");
        StdOut.println(m2.matches());
        m2 = p2.matcher("0");
        StdOut.println(m2.matches());
        m2 = p2.matcher("1");
        StdOut.println(m2.matches());
        m2 = p2.matcher("00");
        StdOut.println(m2.matches());
        m2 = p2.matcher("10");
        StdOut.println(m2.matches());

        StdOut.println("c. String with at least two 0s and at most one 1");
        Pattern p3 = Pattern.compile("(000*10*)|(0*1000*)|(00*100*)|000*");
        Matcher m3 = p3.matcher("0010");
        StdOut.println(m3.matches());
        m3 = p3.matcher("0");
        StdOut.println(m3.matches());
        m3 = p3.matcher("00");
        StdOut.println(m3.matches());
        m3 = p3.matcher("1");
        StdOut.println(m3.matches());
        m3 = p3.matcher("10");
        StdOut.println(m3.matches());
        m3 = p3.matcher("010");
        StdOut.println(m3.matches());

        StdOut.println("d. String with no two consecutive 1s");
        Pattern p4 = Pattern.compile("1|0*|((100*)|(00*1))*");
        Matcher m4 = p4.matcher("010101");
        StdOut.println(m4.matches());
        m4 = p4.matcher("1001101");
        StdOut.println(m4.matches());
        m4 = p4.matcher("0");
        StdOut.println(m4.matches());
        m4 = p4.matcher("1");
        StdOut.println(m4.matches());
        m4 = p4.matcher("01");
        StdOut.println(m4.matches());
        m4 = p4.matcher("10");
        StdOut.println(m4.matches());
    }
}
