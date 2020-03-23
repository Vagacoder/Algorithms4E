package javasrc.ch02_5;

/*
* 2.5.6 Implement a recursive version of select().

*/

import lib.*;

public class SelectKthRecursive{

    public static Comparable select(Comparable[]a, int k){
        StdRandom.shuffle(a);
        int low = 0, high = a.length - 1;
        return select(a, low, high, k);
    }

    // * 2.5.6 recursive 
    private static Comparable select(Comparable[] a, int low, int high, int k){
        int j = partition(a, low, high);
        if(j < k){
            return select(a, j+1, high, k);
        }else if (j > k){
            return select(a, low, j -1, k);
        }else {
            return a[k];
        }
    }

    private static int partition(Comparable[] a, int low, int high){
        int i = low, j = high + 1;
        Comparable pivot = a[low];
        while(true){
            while(less(a[++i], pivot)){
                if(i == high) {
                    break;
                }
            }
            while(less(pivot, a[--j])){
                if(j == low){
                    break;
                }
            }
            if(i >= j){
                break;
            } 
            exch(a, i, j);
        }
        exch(a, low, j);
        return j;
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    public static void main(String[] args){
        Integer[] a = { 2, 4, 5, 0, 9, 1, 3, 8, 6, 7 };
        StdOut.println(select(a, 0));
        StdOut.println(select(a, 1));
        StdOut.println(select(a, 2));
        StdOut.println(select(a, 3));
        StdOut.println(select(a, 4));
        StdOut.println(select(a, 5));
        StdOut.println(select(a, 6));
        StdOut.println(select(a, 7));
        StdOut.println(select(a, 8));
    
    }
}