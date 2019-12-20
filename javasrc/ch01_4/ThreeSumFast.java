package javasrc.ch01_4;

import java.util.Arrays;

import javasrc.ch01_1.BinarySearch;
import lib.In;
import lib.StdOut;

public class ThreeSumFast{

    public static int count(int[] a) {
        Arrays.sort(a);
        int N = a.length;
        int cnt = 0;
        for (int i = 0; i< N; i++){
            for (int j = i ; j< N; j++){
                int index =BinarySearch.rank(-(a[i]+a[j]), a);
                if (index > j){
                    // StdOut.printf("%d, %d, %d\n", a[i], a[j], index);
                    cnt++;
                }
            }
        }
        return cnt;
    }

    public static void main(String[] args){
        In in = new In(args[0]);
        int[] a = in.readAllInts();
        StdOut.println(count(a));
    }
}