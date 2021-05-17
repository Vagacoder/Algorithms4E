package javasrc.ch05_4;

/*
 * Ex 5.4.12
 * Write a Java regular expression for each of the following:
 * a. Phone numbers, such as (609) 555-1234
 * b. Social Security numbers, such as 123-45-6789
 * c. Dates, such as December 31, 1999
 * d. IP addresses of the form a.b.c.d where each letter can represent one, two,
 * or three digits, such as 196.26.155.241
 * e. License plates that start with four digits and end with two uppercase letters
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lib.*;

public class ex5_4_12 {

    public static void main(String[] args){
        // ! Note: java regex string need escape twice
        StdOut.println("a. Phone number");
        Pattern p1 = Pattern.compile("\\([0-9]{3}\\)\\ [0-9]{3}-[0-9]{4}", Pattern.DOTALL);
        Matcher m1 = p1.matcher("(609) 555-1234");
        StdOut.println(m1.matches());
        m1 = p1.matcher("(609 555-1234");
        StdOut.println(m1.matches());

        StdOut.println("b. Social Security Number");
        Pattern p2 = Pattern.compile("[0-9]{3}-[0-9]{2}-[0-9]{4}");
        Matcher m2 = p2.matcher("123-45-6789");
        StdOut.println(m2.matches());
        m2 = p2.matcher(("12-34-5678"));
        StdOut.println(m2.matches());

        StdOut.println("c. Date: December 31, 1999");
        // * 1. easy one, with many missed cases
        // Pattern p3 = Pattern.compile("[A-Z][a-z]{3,8} [0-9]{1,2}, [0-9]{1,4}");

        // * 2. better one, skip some month names.
        Pattern p3 = Pattern.compile("(January|Feburary|March|November|December) (30|31|([1-2]?[0-9])), [0-9]{1,4}");
        Matcher m3 = p3.matcher("December 31, 1999");
        StdOut.println(m3.matches());
        m3 = p3.matcher("December 32, 1999");
        StdOut.println(m3.matches());
        m3 = p3.matcher("December 02, 1999");
        StdOut.println(m3.matches());
        m3 = p3.matcher("Feburary 1, 18");
        StdOut.println(m3.matches());

        StdOut.println("d. IPv4 address");
        Pattern p4 = Pattern.compile("^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-9])(\\.([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-9])){3}$");
        Matcher m4 = p4.matcher("196.26.155.241");
        StdOut.println(m4.matches());
        m4 = p4.matcher("096.26.155.241");
        StdOut.println(m4.matches());
        m4 = p4.matcher("196.26.155.261");
        StdOut.println(m4.matches());

        StdOut.println("e. License plate: nnnn-LL");
        Pattern p5 = Pattern.compile("[0-9]{4}[A-Z]{2}");
        Matcher m5 = p5.matcher("9056HG");
        StdOut.println(m5.matches());
        m5 = p5.matcher("9056hG");
        StdOut.println(m5.matches());
        m5 = p5.matcher("a056HG");
        StdOut.println(m5.matches());
    }
    
}
