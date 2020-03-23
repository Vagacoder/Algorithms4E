package javasrc.ch02_5;


/*
* 2.5.4 Implement a method String[] dedup(String[] a) that returns the objects in
a[] in sorted order, with duplicates removed.

*/

import lib.*;
import javasrc.ch02_3.Quick;

public class Dedup {

    public static String[] dedup(String[] a) {
        int length = a.length;
        a = a.clone();
        if(length <= 1){
            return a;
        }

        Quick.sort(a);
        String[] temp = new String[length];
        int curA = 1, curT = 1;
        String previousString = a[0];
        temp[0] = a[0];
        while (curA < length) {
            if(a[curA].equals(previousString)){
                curA++;
            }else{
                previousString = a[curA];
                temp[curT++] = a[curA++];
            }
        }

        String[] result = new String[curT];
        for (int i = 0; i < curT; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    public static void check(){
        StdOut.println("1. test empty array ...");
        String[] a = {};
        String[] result = dedup(a);
        for(String str: result){
            StdOut.print(str + ", ");
        }
        StdOut.println("\n");

        StdOut.println("2. test 1 element array ...");
        String[] a1 = {"a"};
        result = dedup(a1);
        for(String str: result){
            StdOut.print(str + ", ");
        }
        StdOut.println("\n");
    
        StdOut.println("3. test same element array ...");
        String[] a2 = {"a", "a", "a"};
        result = dedup(a2);
        for(String str: result){
            StdOut.print(str + ", ");
        }
        StdOut.println("\n");


        StdOut.println("4. test 1 element array ...");
        String[] a3 = {"b", "a", "c", "h", "d", "b", "e", "f", "b", "a", "a", "f", "g"};
        result = dedup(a3);
        for(String str: result){
            StdOut.print(str + ", ");
        }
        StdOut.println("\n");
    }

    public static void main(String[] args) {
        check();
    }
}