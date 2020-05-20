package javasrc.ch03_5;

import java.util.HashMap;

/*
* 3.5.23 Sparse matrices. Develop an API and an implementation for sparse 2D matri-
ces. Support matrix addition and matrix multiplication. Include constructors for row
and column vectors.

*/

import lib.*;

public class SparseMatrix{

    private HashMap<Integer, SparseVector> table;
    // * matrix demision: m x n; m rows and n columns
    private int m;
    private int n;

    public SparseMatrix(int rows, int columns){
        this.m = rows;
        this.n = columns;
        table = new HashMap<>();
    }

    public void put(int r, int c, double value){
        if(r < 0 || r > m){
            throw new IllegalArgumentException("row number out of range");
        }
        if(c < 0 || c > n){
            throw new IllegalArgumentException("column number out of range");
        }

        if(table.get(r)==null){
            table.put(r, new SparseVector(this.n));
        }
        table.get(r).put(c, value);
    }

    public void put(int r, SparseVector row){
        if(r < 0 || r > m){
            throw new IllegalArgumentException("row number out of range");
        }
        if(row.getDemision() != n){
            throw new IllegalArgumentException("column number does not match");
        }

        table.put(r, row);
    }

    public double get(int r, int c){
        if(r < 0 || r > m){
            throw new IllegalArgumentException("row number out of range");
        }
        if(c < 0 || c > n){
            throw new IllegalArgumentException("column number out of range");
        }
        SparseVector row = table.get(r);
        if(row != null){
            return table.get(r).get(c);
        }
        return 0.0;
    }

    public SparseMatrix add(SparseMatrix that){
        if(that.m != this.m || that.n != this.n){
            throw new IllegalArgumentException("Demision do not match");
        }

        SparseMatrix result = new SparseMatrix(this.m, this.n);

        for(Integer rowNumber: this.table.keySet()){
            SparseVector rowOfThis = this.table.get(rowNumber);
            SparseVector rowOfThat = that.table.get(rowNumber);
            if(rowOfThat != null){
                result.put(rowNumber, rowOfThis.sum(rowOfThat));
            }else{
                result.put(rowNumber, rowOfThis);
            }
        }

        for(Integer rowNumber: that.table.keySet()){
            if(!this.table.containsKey(rowNumber)){
                SparseVector rowOfThis = this.table.get(rowNumber);
                SparseVector rowOfThat = that.table.get(rowNumber);
                if(rowOfThis != null){
                    result.put(rowNumber, rowOfThat.sum(rowOfThis));
                }else{
                    result.put(rowNumber, rowOfThat);
                }
            }
        }

        return result;
    }

    public double[] multiply(double[] b){
        double[] result = new double[this.m];
        for(int i = 0; i < m; i++){
            SparseVector row = table.get(i);
            if(row != null){
                double data = row.dot(b);
                result[i] = data;
            }else{
                result[i] = 0.0;
            }
        }
        return result;
    }

    public static void main(String[] args){
    
    }
}