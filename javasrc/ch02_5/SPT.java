package javasrc.ch02_5;

/*
* 2.5.12 Scheduling. 
Write a program SPT.java that reads job names and processing times from standard 
input and prints a schedule that minimizes average completion time using the 
shortest processing time first rule, as described on page 349.

*/

import lib.*;

public class SPT{

    class Job implements Comparable<Job>{
        String name;
        int time;

        Job(String name, int time){
            this.name = name;
            this.time = time;
        }

        String getJobInfo(){
            return "" + this.name + "\t" + this.time;
        }

        @Override
        public int compareTo(Job that) {
            return this.time - that.time;
        }
    }

    private Job[] jobsMinPQ;
    private int size;

    public SPT(int capacity){
        this.size = 0;
        this.jobsMinPQ = new Job[capacity+1];
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public void add(Job newJob){
        if (this.size < this.jobsMinPQ.length - 1){
            this.jobsMinPQ[++this.size] = newJob;
            swim(this.size);
        }else{
            StdOut.println("Reach max capacity!");
        }
    }

    public Job delMin(){
        if(this.size == 0){
            return null;
        }

        Job min = this.jobsMinPQ[1];
        exch(1, this.size--);
        this.jobsMinPQ[size+1] = null;
        sink(1);
        return min;
    }

    private void swim(int k){
        while (k > 1 && larger(k/2, k)){
            exch(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k){
        while(2*k <= this.size){
            int j = 2*k;
            if(j< this.size&& larger(j, j+1)){
                j++;
            }
            if(!larger(k, j)){
                break;
            }
            exch(j, k);
            k = j;
        }
    }

    private boolean larger (int a, int b){
        return this.jobsMinPQ[a].compareTo(this.jobsMinPQ[b]) > 0;
    }

    private void exch(int a, int b){
        Job temp = this.jobsMinPQ[a];
        this.jobsMinPQ[a] = this.jobsMinPQ[b];
        this.jobsMinPQ[b] = temp;
    }

    public static void check(){
        StdOut.println("1. test ...");
        SPT sp = new SPT(10);
        StdOut.println("1.1. test empty schedule ...");
        Job min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        
        StdOut.println("1.2. test 1 element schedule ...");
        sp.add(sp.new Job("a", 10));
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());

        StdOut.println("1.3. test multiple elements schedule ...");
        sp.add(sp.new Job("a", 10));
        sp.add(sp.new Job("b", 2));
        sp.add(sp.new Job("c", 4));
        sp.add(sp.new Job("d", 1));
        sp.add(sp.new Job("e", 15));
        sp.add(sp.new Job("f", 9));
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
        min = sp.delMin();
        StdOut.println(min == null ? null : min.getJobInfo());
    }

    public static void main(String[] args){
            check();
    }
}