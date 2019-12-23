package javasrc.ch01_3;

/*
1.3.38 Delete kth element. Implement a class that supports the following API:

boolean isEmpty(): is the queue empty?
void insert(Item x): add an item
Item delete(int k): delete and return the kth least recently inserted item

Note: this class is useing an array implementation
*/

import lib.StdOut;

public class GeneralizedQueue<T>{

    private T[] queue;
    private int numberInQueue;
    private int first;
    private int last;

    public GeneralizedQueue(){
        @SuppressWarnings("unchecked")
        T[] q = (T[]) new Object[4];
        this.queue = q;
        this.numberInQueue = 0;
        this.first = 0;
        this.last = 0;  // last exclusive
    }

    public boolean isEmpty(){
        return this.numberInQueue == 0;
    }

    public int size(){
        return this.numberInQueue;
    }

    private void resize(int newCapacity){
        assert newCapacity > this.numberInQueue;
        @SuppressWarnings("unchecked")
        T[] q = (T[]) new Object[newCapacity];
        for(int i = 0; i< this.numberInQueue; i++){
            q[i] = this.queue[this.first + i];
        }
        this.queue = q;
        this.first = 0;
        this.last = this.numberInQueue;
    }

    public void insert(T newItem){
        if(this.last >= this.queue.length){
            this.resize(this.numberInQueue * 2);
        }
        this.queue[this.last++] = newItem;
        this.numberInQueue++;
    }

    public T delete(int k){
        if(k >= this.numberInQueue){
            return null;
        }

        if (this.numberInQueue >= 2 &&this.numberInQueue <= this.queue.length / 4){
            this.resize(this.numberInQueue * 2);
        }
        T result = this.queue[this.first + k];
        for(int i = this.first + k; i< this.first + this.numberInQueue -1; i++){
            this.queue[i] = this.queue[i+1];
        }
        this.queue[--this.last ] = null;
        this.numberInQueue--;
        return result;
    }

    public void print(){
        int length = this.queue.length;
        for(int i = 0; i< length; i++){
            if(this.queue[i] == null){
                StdOut.print("null ");
            } else{
                StdOut.print(this.queue[i] + " ");
            }
        }
        StdOut.println();
    }

    public static void main(String[] args){
        StdOut.println("test");
        GeneralizedQueue<String> gq = new GeneralizedQueue<>();
        gq.insert("we");
        gq.print();
        StdOut.println("size: " + gq.size());
        gq.insert("are");
        gq.insert("good");
        gq.insert("at");
        gq.insert("study");
        gq.print();
        StdOut.println("size: " + gq.size());

        StdOut.println(gq.delete(0));
        gq.print();
        StdOut.println("size: " + gq.size());
        StdOut.println(gq.delete(3));
        gq.print();
        StdOut.println("size: " + gq.size());
        gq.delete(0);
        gq.print();
        gq.delete(0);
        gq.print();
        gq.delete(0);
        gq.print();
        gq.delete(0);
        gq.print();
    
    }
}