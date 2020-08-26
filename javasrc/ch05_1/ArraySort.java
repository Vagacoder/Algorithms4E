package javasrc.ch05_1;

/* 
 * 5.1.14 Array sort. Develop a method that uses 3-way string quicksort for keys 
 * that are arrays of int values.
 * 
 */

import lib.*;

public class ArraySort {
    

    public static void sortByNumber(int[] a){
        sortByNumber(a, 0, a.length - 1);
    }

    private static void sortByNumber(int[] a, int low, int high){
        if(low >= high){
            return;
        }

        int lt = low;
        int gt = high;
        int partition = a[low];
        int i = low + 1;

        while(i <= gt){
            int current = a[i];
            if(current < partition){
                exch(a, lt++, i++);
            }else if ( current > partition){
                exch(a, i, gt--);
            }else{
                i++;
            }
        }

        sortByNumber(a, low, lt-1);
        sortByNumber(a, lt, gt);
        sortByNumber(a, gt+1, high);
    }

    private static void exch(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // * real solution
    public static void sortByString(int[] a){
        String[] newA = new String[a.length];
        for(int i = 0; i < a.length; i++){
            newA[i] = a[i] + "";
        }

        int W = 0;
        for(int i = 0; i < a.length; i++){
            int curLength = newA[i].length();
            if(curLength > W){
                W = curLength;
            }
        }

        String padding = "";
        for(int i = 0; i < W; i++){
            padding += "0";
        }

        for(int i = 0; i < a.length; i++){
            String temp = padding + newA[i];
            int tempLength = temp.length();
            newA[i] = temp.substring(tempLength - W, tempLength);
        }

        Quick3String.sort(newA);
        for(int i = 0; i < a.length; i++){
            a[i] = Integer.parseInt(newA[i]);
        }
    }

    public static void main(String[] args){

        int[] a = {3, 4, 8, 10, 2, 7, 5, 1, 9, 6};

        // * 1. sort by number
        StdOut.println("1. sort by number");
        sortByNumber(a);
        for(int i = 0; i < a.length; i++){
            StdOut.print(a[i] + ", ");
        }
        StdOut.println("\n");

        // * 2. sort by string
        int[] b = {3, 4, 8, 10, 2, 7, 5, 1, 9, 6};
        sortByString(b);
        for(int i = 0; i < b.length; i++){
            StdOut.print(b[i] + ", ");
        }
        StdOut.println("\n");
    }
}