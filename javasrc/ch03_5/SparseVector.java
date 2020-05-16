package javasrc.ch03_5;

import java.util.TreeMap;
import lib.*;

/*
* Sparse vector with dot product 
*/

public class SparseVector {

    private TreeMap<Integer, Double> st;

    public SparseVector(){
        st = new TreeMap<Integer, Double>();
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
    
}