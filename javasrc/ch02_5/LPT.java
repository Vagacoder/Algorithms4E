package javasrc.ch02_5;

/*
* 2.5.13 Load balancing. 
Write a program LPT.java that takes an integer M as a command-line argument, reads 
job names and processing times from standard input and prints a schedule assigning 
the jobs to M processors that approximately minimizes the time when the last job 
completes using the longest processing time first rule, as described on page 349.

? Explain: To produce a good schedule is the longest processing time first rule, 
where we consider the jobs in descending order of processing time, assigning each 
job to the processor that becomes available first. 

1. To implement this algorithm, we first sort the jobs in reverse order. 
2. Then we maintain a priority queue of M processors, where the priority is the sum 
of the processing times of its jobs. 
3. At each step, we delete the processor with the minimum priority, 
4. Add the next job to the processor, and reinsert that processor into the priority 
queue.

*/

import javasrc.ch02_3.Quick;
import lib.*;

public class LPT{

    // * class of job
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

    // * class of CPU
    class Processor implements Comparable<Processor>{
        Job[] jobs;
        int jobNumber;
        int totalTime;

        Processor(int capacity){
            this.jobNumber = 0;
            this.totalTime =0;
            jobs = new Job[capacity];
        }

        void addJob(Job newJob){
            this.jobs[jobNumber++] = newJob;
            this.totalTime += newJob.time;
        }

        @Override
        public int compareTo(Processor that) {
            return this.totalTime - that.totalTime;
        }
    }

    // * LPT
    private Job[] allJobs;
    private Processor[] cpus; //* min priority queue
    private int jobNumber;
    private int cpuNumber;

    public LPT(int maxJobNumber, int cpuNumber){
        this.jobNumber = 0;
        this.allJobs = new Job[maxJobNumber];
        this.cpus = new Processor[cpuNumber+1];
        for(int i = 1; i <= cpuNumber; i++){
            this.cpus[i] = new Processor(maxJobNumber);
        }   
        this.cpuNumber = cpuNumber;
    }

    public boolean isJobsEmpty(){
        return this.jobNumber == 0;
    }

    public boolean isCpuEmpty(){
        return this.cpuNumber == 0;
    }

    public void addJob(Job newJob){
        this.allJobs[jobNumber++] = newJob;
    }
    
    public void dispatch(){
        Job[] jobsCopy = new Job[this.jobNumber];
        for (int i =0; i < jobNumber; i++){
            jobsCopy[i] = this.allJobs[i];
        }
        Quick.sort(jobsCopy);
        for(int i = this.jobNumber-1; i >= 0;i-- ){
            Processor minCpu = delMinCpu();
            minCpu.addJob(jobsCopy[i]);
            addCpu(minCpu);
        }
    }

    public void printAllCpuJobs(){
        for(int i = 1; i <= cpuNumber; i++ ){
            Processor cpu = this.cpus[i];
            StdOut.println("CPU#" + i);
            StdOut.println("Job list:");
            for(int j = 0; j<cpu.jobNumber; j++){
                Job job = cpu.jobs[j];
                StdOut.println("" + job.name + "\t" + job.time);
            }
            StdOut.println();
        }
    }

    private void addJobToCpu(Job newJob, Processor cpu){
        cpu.addJob(newJob);
    }

    private void addCpu(Processor cpu){
        if(this.cpuNumber < this.cpus.length - 1){
            this.cpus[++this.cpuNumber] = cpu;
            swim(this.cpuNumber);
        }else{
            StdOut.println("Reach CPU max limit");
        }
    }

    public Processor delMinCpu(){
        if(this.cpuNumber == 0){
            return null;
        }

        Processor min = this.cpus[1];
        exch(1, this.cpuNumber--);
        this.cpus[cpuNumber+1] = null;
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
        while(2*k <= this.cpuNumber){
            int j = 2*k;
            if(j< this.cpuNumber && larger(j, j+1)){
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
        return this.cpus[a].compareTo(this.cpus[b]) > 0;
    }

    private void exch(int a, int b){
        Processor temp = this.cpus[a];
        this.cpus[a] = this.cpus[b];
        this.cpus[b] = temp;
    }


    public static void main(String[] args){
        LPT lpt = new LPT(20, 4);
        lpt.addJob(lpt.new Job("a", 100));
        lpt.addJob(lpt.new Job("b", 90));
        lpt.addJob(lpt.new Job("c", 10));
        lpt.addJob(lpt.new Job("d", 200));
        lpt.addJob(lpt.new Job("e", 30));
        lpt.addJob(lpt.new Job("f", 120));
        lpt.addJob(lpt.new Job("g", 60));
        lpt.addJob(lpt.new Job("h", 170));
        lpt.addJob(lpt.new Job("i", 180));
        lpt.addJob(lpt.new Job("j", 150));
        lpt.addJob(lpt.new Job("k", 110));
        lpt.addJob(lpt.new Job("l", 20));
        lpt.addJob(lpt.new Job("m", 120));
        lpt.addJob(lpt.new Job("n", 200));
        lpt.addJob(lpt.new Job("o", 40));
        lpt.dispatch();
        lpt.printAllCpuJobs();
    }
}