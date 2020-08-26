package javasrc.ch05_1;

/*
 * 5.1.13 Hybrid sort. Investigate the idea of using standard MSD string sort for 
 * large arrays, in order to get the advantage of multiway partitioning, and 3-way 
 * string quicksort for smaller arrays, in order to avoid the negative effects of 
 * large numbers of empty bins.
 * 
 
 */

import lib.*;

public class HybridString {
    
    private static int R = 256;
    private static final int M = 3;
    private static String[] aux;

    public static void sort(String[] a){
        int N = a.length;
        aux = new String[N];
        sort(a, 0, N-1, 0);
    }

    private static void sort(String[] a, int low, int high, int d){
        if(low + M >= high){
            quick3WaySort(a, low, high, d);
            return;
        }

        int[] count = new int[R+2];

        for(int i = low; i <= high; i++){
            count[charAt(a[i], d) + 2]++;
        }

        for(int r = 0; r < R+1; r++){
            count[r + 1] += count[r];
        }

        for(int i = low; i <=high; i++){
            aux[count[charAt(a[i], d) + 1]++] = a[i];
        }

        for(int i = low; i <= high; i++){
            a[i] = aux[i -low];
        }

        for(int r= 0; r < R; r++){
            sort(a, low+count[r], low+count[r+1]-1, d+1);
        }
    }

    private static void quick3WaySort(String[] a, int low, int high, int d){
        if(high <= low){
            return;
        }

        int lt = low;
        int gt = high;
        int v = charAt(a[low], d);
        int i = low + 1;

        while(i <= gt){
            int t = charAt(a[i], d);
            if(t < v){
                exch(a, lt++, i++);
            }else if( t > v){
                exch(a, i, gt--);
            }else{
                i++;
            }
        }

        // ! here, a[low ... lt-1] < v = a[lt ... gt] < a[gt+1 ... high]
        // * sort these 3 parts
        sort(a, low, lt-1, d);
        if(v >= 0){
            sort(a, lt, gt, d+1);
        }
        sort(a, gt+1, high, d);
    }

    private static int charAt(String s, int d){
        if(d >= s.length()){
            return -1;
        }else{
            return s.charAt(d);
        }
    }

    private static void exch(String[] a, int i, int j) {
        String t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void main(String[] args){
        // * test #1
        StdOut.println("1. test shells.txt");
        String filename = "data/shells.txt";
        String[] input = new In(filename).readAllStrings();
        
        // ? test empty string, charAt() return -1
        // input[0] = "";

        StdOut.println("Before sort:");
        for (String s : input) {
            StdOut.println(s);
        }

        HybridString.sort(input);
        StdOut.println("\nAfter sort:");
        for (String s : input) {
            StdOut.println(s);
        }
        
    }
}