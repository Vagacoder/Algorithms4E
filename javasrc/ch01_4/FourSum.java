package javasrc.ch01_4;

/*
1.4.14 4-sum. Develop an algorithm for the 4-sum problem.
*/

import java.util.Arrays;
import lib.StdOut;
import lib.In;
import javasrc.ch01_1.BinarySearch;

public class FourSum{

    public static int countSlow(int[] a){
        int count = 0;
        int N = a.length;
        for (int i = 0; i< N; i++){
            for (int j =i+1; j<N; j++){
                for(int k=j+1; k<N; k++){
                    for(int l=k+1; l<N;l++)
                    if(((long)a[i]) + a[j] + a[k] + a[l] == 0){
                        StdOut.println(a[i] + " " + a[j] + " " + a[k] + " " + a[l]);
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public static int countFast(int[] a){
        Arrays.sort(a);
        int count = 0;
        int N = a.length;
        for (int i = 0; i< N; i++){
            for (int j =i+1; j<N; j++){
                for(int k=j+1; k<N; k++){
                    int index =BinarySearch.rank(-(a[i]+a[j]+a[k]), a);
                    if (index > k){
                    StdOut.printf("%d, %d, %d, %d\n", a[i], a[j], a[k], a[index]);
                    count++;
                }
                }
            }
        }
        return count;
    }
    

    public static void main(String[] args){
        In in = new In(args[0]);
        int[] a = in.readAllInts();

        Stopwatch timer = new Stopwatch();
        int count = countFast(a);
        StdOut.println("It takes time of " + timer.elapsedTime());
        StdOut.println(count);
    }

}