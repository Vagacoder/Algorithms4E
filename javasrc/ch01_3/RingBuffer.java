package javasrc.ch01_3;

/*
1.3.39 Ring buffer. A ring buffer, or circular queue, is a FIFO data structure of 
a fixed size N. It is useful for transferring data between asynchronous processes 
or for storing log files. When the buffer is empty, the consumer waits until data 
is deposited; when the buffer is full, the producer waits to deposit data. Develop 
an API for a RingBuffer and an implementation that uses an array representation 
(with circular wrap-around).

API:
boolean isEmpty(): is ringbuffer empty
boolean isFull(): is ringbuffer full
int size(): the number of elements in ringbuffer
void enqueue(T newItem): add new item to the end of ringbuffer
T dequeue(): remove item from the beginning of ringbuffer
void print(): print all elements including nulls in ringbuffer
*/

public class RingBuffer<T>{

    private T[] arr;
    private int numberInBuffer;
    private int first;
    private int last;
    private int capacity;

    public RingBuffer(int capacity){
        this.capacity = capacity;
        @SuppressWarnings("unchecked")
        T[] a = (T[]) new Object[this.capacity];
        this.arr = a;
        this.numberInBuffer = 0;
        this.first = 0;
        this.last = 0;
    }

    private void relocate(){
        @SuppressWarnings("unchecked")
        T[] a = (T[]) new Object[this.capacity];
        for(int i= 0; i< this.numberInBuffer; i++){
            a[i] = this.arr[this.first + i];
        }
        this.first = 0;
        this.last = this.numberInBuffer;
        this.arr = a;
    }

    public boolean isEmpty(){
        return this.numberInBuffer == 0;
    }

    public boolean isFull(){
        return this.numberInBuffer == this.capacity;
    }

    public int size(){
        return this.numberInBuffer;
    }

    public void enqueue(T newItem){
        if (this.numberInBuffer == this.capacity){
            return;
        } else if (this.last >= this.capacity){
            this.relocate();
        }
        this.arr[last++] = newItem;
        this.numberInBuffer++;
    }

    public T dequeue(){
        if (this.numberInBuffer == 0){
            return null;
        }
        T result = this.arr[--this.last];
        this.numberInBuffer--;
        return result;
    }

}