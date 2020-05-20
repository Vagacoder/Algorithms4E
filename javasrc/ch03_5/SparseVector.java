package javasrc.ch03_5;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import lib.*;

/*
* Sparse vector with dot product. P.503

* 3.5.16 Add a method sum() to SparseVector that takes a SparseVector as argument
and returns a SparseVector that is the term-by-term sum of this vector and the argu-
ment vector. Note: You need delete() (and special attention to precision) to handle the
case where an entry becomes 0.

*/

public class SparseVector {

    private HashMap<Integer, Double> st;
    private int n;

    public SparseVector(){
        st = new HashMap<Integer, Double>();
    }
    public SparseVector(int n){
        st = new HashMap<Integer, Double>();
        this.n = n;
    }

    public int getDemision(){
        return this.n;
    }

    public int size(){
        return st.size();
    }

    public void put(int i, double x){
        st.put(i, x );
    }

    public double get(int i){
        if(!st.containsKey(i)){
            return 0.0;
        }else{
            return st.get(i);
        }
    }

    public double dot(double[] that){
        double sum = 0.0;
        for(int i: st.keySet()){
            sum += that[i]*this.get(i);
        }
        return sum;
    }
    
    public SparseVector sum(SparseVector that){
        SparseVector result = new SparseVector();
        Set<Integer> union = new HashSet<>();
        union.addAll(this.st.keySet());
        union.addAll(that.st.keySet());

        for(Integer i : union){
            double sum = this.get(i) + that.get(i);
            if(sum != 0){
                result.put(i, sum);
            }
        }
        return result;
    }

    public void print(){
        for(Integer i : this.st.keySet()){
            StdOut.println(i + ": " + get(i));
        }
    }

    public static void check(){
        // * Example matrix on P.502
        // * 5 rows for matrix a (5x5)
        SparseVector sva1 = new SparseVector();
        SparseVector sva2 = new SparseVector();
        SparseVector sva3 = new SparseVector();
        SparseVector sva4 = new SparseVector();
        SparseVector sva5 = new SparseVector();
        sva1.put(1, 0.90);

        sva2.put(2, 0.36);
        sva2.put(3, 0.36);
        sva2.put(4, 0.18);

        sva3.put(3, 0.90);

        sva4.put(0, 0.90);

        sva5.put(0, 0.47);
        sva5.put(2, 0.47);

        sva1.print();
        sva2.print();
        sva3.print();
        sva4.print();
        sva5.print();
        StdOut.println();

        // * one column for vertical vector x
        SparseVector svx = new SparseVector();
        svx.put(0, 0.05);
        svx.put(1, 0.04);
        svx.put(2, 0.36);
        svx.put(3, 0.37);
        svx.put(4, 0.19);

        svx.print();
        StdOut.println();

        // * x in array format for dot()
        double[] x = {0.05, 0.04, 0.36, 0.37, 0.19};

        StdOut.println("1. testing dot() ...");
        StdOut.println(sva1.dot(x));
        StdOut.println(sva2.dot(x));
        StdOut.println(sva3.dot(x));
        StdOut.println(sva4.dot(x));
        StdOut.println(sva5.dot(x));
        StdOut.println();

        StdOut.println("2. testing sum() ...");
        SparseVector svc = new SparseVector();
        svc.put(0, 0.05);
        svc.put(2, -0.36);
        svc.put(4, 0.19);
        svc.print();
        StdOut.println();
        sva2.print();
        StdOut.println();

        sva2.sum(svc).print();
    }

    public static void main(String[] args){
        check();
    }
}