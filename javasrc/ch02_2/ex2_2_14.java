package javasrc.ch02_2;

/*
* 2.2.14 Merging sorted queues. 
Develop a static method that takes two queues of sorted items as arguments and 
returns a queue that results from merging the queues into sorted order.

*/

import javasrc.ch01_3.LinkedListQueue;

public class ex2_2_14{

    public static void main(String[] args){
        LinkedListQueue<Integer> q1 = new LinkedListQueue<>();
        LinkedListQueue<Integer> q2 = new LinkedListQueue<>();

        q1.enqueue(1);
        q1.enqueue(5);
        q1.enqueue(19);
        q1.enqueue(27);
        q1.enqueue(33);

        q2.enqueue(8);
        q2.enqueue(10);
        q2.enqueue(12);
        q2.enqueue(36);
        q2.enqueue(49);

        LinkedListQueue<Integer> result = new LinkedListQueue<>();

        while(!q1.isEmpty() || !q2.isEmpty()){
            if(q1.isEmpty()){
                result.enqueue(q2.dequeue());
            } else if (q2.isEmpty()){
                result.enqueue(q1.dequeue());
            } else {
                int temp1 = q1.peekTop();
                int temp2 = q2.peekTop();
                if(temp1 < temp2){
                    result.enqueue(q1.dequeue());
                } else {
                    result.enqueue(q2.dequeue());
                }
            }
        }

        result.print();
    }
}