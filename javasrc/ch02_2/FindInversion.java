package javasrc.ch02_2;

import java.util.Arrays;

/*
* 2.2.19 Inversions. 
Develop and implement a linearithmic algorithm for computing the number of inversions 
in a given array (the number of exchanges that would be performed by insertion sort 
for that array—see Section 2.1). 

This quantity is related to the Kendall tau distance; see Section 2.5.
*/

import lib.StdOut;

public class FindInversion{

    // O(N^2) brutal force
    public static int findNumberOfInversion(Comparable[] a){
        int result = 0;
        int N = a.length;
        for(int i = 0; i < N -1; i++){
            for(int j = i + 1; j < N; j++){
                if(less(a[j], a[i])){
                    result++;
                }
            }
        }
        return result;
    }

    // O(NlgN) 
    private static Comparable[] aux;

    public static int findNumberOfInversion1(Comparable[] a){
        int result = 0;
        int N = a.length;
        Comparable[] b = new Comparable[N];
        aux = new Comparable[N] ;

        for(int i = 0 ; i < N; i++){
            b[i] = a[i];
        }

        result = countInversion(b, 0, N-1);

        return result;
    }

    private static int countInversion(Comparable[]a, int low, int high){
        int inversionNumber = 0;
        if (high <= low) {
            return inversionNumber; 
        }

        int mid = (low + high) / 2;
        inversionNumber += countInversion(a, low, mid);
        inversionNumber += countInversion(a, mid + 1, high);
        inversionNumber += merge(a, low, mid, high);

        return inversionNumber;
    }

    private static int merge(Comparable[] a, int low, int mid, int high) {
        int i = low, j = mid + 1, inversionNumber = 0;

        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        for (int k = low; k <= high; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > high) {
                a[k] = aux[i++];
            } else if (less(aux[j], aux[i])) {
                a[k] = aux[j++];

                // if a[i] > a[j], then all element after i before mid + 1 > a[j] 
                inversionNumber += (mid + 1 - i);
            } else {
                a[k] = aux[i++];
            }
        }

        return inversionNumber;
    }

    private static boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;
    }


    public static void main(String[] args){
        String[] a = {"E", "X", "A", "M", "P", "L", "E"};
        StdOut.println(findNumberOfInversion(a));
        StdOut.println(findNumberOfInversion1(a));
    }
}