package javasrc.ch05_4;

/*
 * 5.4.14 Binary divisibility. 
 * Construct an RE that describes all binary strings that when interpreted as a b
 * inary number are
 *  a. Divisible by 2
 *  b. Divisible by 3
 *  c. Divisible by 123
 * 
 */

import lib.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ex5_4_14 {
    
    public static void main(String[] args){
        StdOut.println("a. Divisible by 2");
        Pattern p1 = Pattern.compile("(0|1)*0");
        Matcher m1 = p1.matcher("0");
        StdOut.println(m1.matches());
        m1 = p1.matcher("10");
        StdOut.println(m1.matches());
        m1 = p1.matcher("100");
        StdOut.println(m1.matches());
        m1 = p1.matcher("101");
        StdOut.println(m1.matches());

        StdOut.println("\nb. Divisible by 3");
        Pattern p2 = Pattern.compile("(0|1(01*0)*1)*"); // * P. 793
        Matcher m2 = p2.matcher("11110"); // ? bin for 30
        StdOut.println(m2.matches());
        m2 = p2.matcher("100001"); // ? bin for 33
        StdOut.println(m2.matches());
        m2 = p2.matcher("11111"); // ? bin for 31
        StdOut.println(m2.matches());
        m2 = p2.matcher("11"); // ? bin for 3
        StdOut.println(m2.matches());
        m2 = p2.matcher("110"); // ? bin for 6
        StdOut.println(m2.matches());
        m2 = p2.matcher("1001"); // ? bin for 9
        StdOut.println(m2.matches());
        m2 = p2.matcher("1100"); // ? bin for 12
        StdOut.println(m2.matches());
        m2 = p2.matcher("100"); // ? bin for 4
        StdOut.println(m2.matches());
        m2 = p2.matcher("101"); // ? bin for 5
        StdOut.println(m2.matches());
        m2 = p2.matcher("111"); // ? bin for 7
        StdOut.println(m2.matches());

        for (int i = 0; i< 10000; i++){
            m2 = p2.matcher(Integer.toBinaryString(i));
            if (i%3 == 0){
                if (!m2.matches()){
                    StdOut.println("Not match for " + i + "; " + Integer.toBinaryString(i));
                }
            } else {
                if (m2.matches()){
                    StdOut.println("Match for " + i + "; " + Integer.toBinaryString(i));
                }
            }
        }

        StdOut.println("test from 0 to 10000 is done");


        StdOut.println("\nb. Divisible by 3 -2");
        // * https://cs.stackexchange.com/questions/89626/regex-for-divisibility-by-123-in-binary-strings/89627
        // * https://cs.stackexchange.com/questions/2016/how-to-convert-finite-automata-to-regular-expressions
        Pattern p22 = Pattern.compile("(0|11|10(1|00)*01)*"); // * self, see links above
        Matcher m22 = p22.matcher("11110"); // ? bin for 30
        StdOut.println(m22.matches());
        m22 = p22.matcher("100001"); // ? bin for 33
        StdOut.println(m22.matches());
        m22 = p22.matcher("11111"); // ? bin for 31
        StdOut.println(m22.matches());
        m22 = p22.matcher("11"); // ? bin for 3
        StdOut.println(m22.matches());
        m22 = p22.matcher("110"); // ? bin for 6
        StdOut.println(m22.matches());
        m22 = p22.matcher("1001"); // ? bin for 9
        StdOut.println(m22.matches());
        m22 = p22.matcher("1100"); // ? bin for 12
        StdOut.println(m22.matches());
        m22 = p22.matcher("100"); // ? bin for 4
        StdOut.println(m22.matches());
        m22 = p22.matcher("101"); // ? bin for 5
        StdOut.println(m22.matches());
        m22 = p22.matcher("111"); // ? bin for 7
        StdOut.println(m22.matches());

        for (int i = 0; i< 10000; i++){
            m22 = p22.matcher(Integer.toBinaryString(i));
            if (i%3 == 0){
                if (!m22.matches()){
                    StdOut.println("Not match for " + i + "; " + Integer.toBinaryString(i));
                }
            } else {
                if (m22.matches()){
                    StdOut.println("Match for " + i + "; " + Integer.toBinaryString(i));
                }
            }
        }

        StdOut.println("test from 0 to 10000 is done");
        // * could be solved by self methods above, but too complicated.
        StdOut.println("\nc. Divisible by 123");
        Pattern p3 = Pattern.compile("");
        Matcher m3 = p3.matcher("1111011");
        StdOut.println(m3.matches());
        
    }
}
