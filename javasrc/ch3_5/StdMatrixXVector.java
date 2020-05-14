package javasrc.ch3_5;

import lib.*;

/*
* standard matrix-vector multiplication
*/

public class StdMatrixXVector {
    
    double[][] a;
    double[] x;
    double[] b;
    // * demension a is M x N
    // * x is vertical vector with N elements
    // * b is vertical vector with M elements
    int M;
    int N;

    public StdMatrixXVector(int m, int n){
        this.M = m;
        this.N = n;
        a = new double[M][N];
        x = new double[N];
        b = new double[M];
    }

    public boolean setA(double[][] a){
        if(a.length != M){
            return false;
        }
        if(a[0].length != N){
            return false;
        }

        this.a = a;
        return true;
    }

    public boolean setX(double[] x){
        if(x.length != N){
            return false;
        }
        this.x = x;
        return true;
    }

    public double[] getResult(){
        for(int i = 0; i < M; i++){
            double sum = 0.0;
            for(int j = 0; j < N; j++){
                sum += (a[i][j] * x[j]);
            }
            b[i] = sum;
        }
        return this.b;
    }

    public static void main(String[] args){
        StdMatrixXVector mv = new StdMatrixXVector(5, 6);
        double[][] a = {
            {0, 0.9, 0, 0, 0, 0},
            {0, 0, 0.36, 0.36, 0.18, 0},
            {0, 0, 0, 0.9, 0, 0},
            {0.9, 0, 0, 0, 0, 0},
            {0.47, 0, 0.47, 0, 0, 0},
        };
        double[] x = {0.05, 0.04, 0.36, 0.37, 0.19, 0};
        if(!mv.setA(a)){
            StdOut.println("A is not correct");
        }
        if(!mv.setX(x)){
            StdOut.println("X is not correct");
        }
        double[] b = mv.getResult();
        for(double n: b){
            StdOut.println(n);
        }
    }
}