package javasrc.ch05_3;

/*
 * Ex 5.3.4. Write an efficient method that takes a string txt and an integer M 
 * as arguments and returns the position of the first occurrence of M consecutive 
 * blanks in the string txt.length if there is no such occurrence. Estimate the 
 * number of character compares used by your method, on typical text and in the 
 * worst case.
 * 
 */

import lib.*;

public class FindMblanks {
    
    public static int search(String txt, int M){
        int N = txt.length();

        int i = 0;
        while (i < N){
            int j = 0;
            while (txt.charAt(i+j) == ' '){
                j++;
                if (j == M){
                    return i;
                }
            }
            i += (j+1);
        }
        return N;
    }

    public static void main(String[] args){
        String txt = "This is   a  test    string.";
        int index = search(txt, 4);
        StdOut.println(index);
    }
}
