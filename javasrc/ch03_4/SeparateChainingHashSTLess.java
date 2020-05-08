package javasrc.ch03_4;

/*
* 3.4.2 Develop an alternate implementation of SeparateChainingHashST that directly
uses the linked-list code from SequentialSearchST.

* 3.4.3 Modify your implementation of the previous exercise to include an integer field
for each key-value pair that is set to the number of entries in the table at the time that
pair is inserted. Then implement a method that deletes all keys (and associated values)
for which the field is greater than a given integer k. 

! Note : This extra functionality is useful in implementing the symbol table for a compiler.



*/

import lib.*;

public class SeparateChainingHashSTLess<Key, Value>{

    class Node{
        Key key;
        Value value;
        int numberAtInsertion;
        Node next;
    }

    public static final int DEFAULT_SIZE = 11;
    private Node[] table;
    // * hash table size
    private int tableSize;
    private int number;
    
    public SeparateChainingHashSTLess(){
        this(DEFAULT_SIZE);
    }

    public SeparateChainingHashSTLess(int size){
        this.tableSize = size;
        this.table = new SeparateChainingHashSTLess.Node[tableSize];
        this.number = 0;
    }

    private int hash(Key key){
        return (key.hashCode() & 0x7fffffff) % this.tableSize;
    }

    public Value get(Key key){
        int index = hash(key);
        Node cur = this.table[index];
        while(cur != null){
            if(cur.key.equals(key)){
                return cur.value;
            }else{
                cur = cur.next;
            }
        }
        return null;
    }

    public void put(Key key, Value value){
        int index = hash(key);
        Node cur = this.table[index];
        while(cur != null){
            if(cur.key.equals(key)){
                cur.value = value;
                return;
            }
            cur = cur.next;
        }
        Node newNode = new Node();
        newNode.key = key;
        newNode.value = value;
        newNode.next =this.table[index];
        newNode.numberAtInsertion = this.number;
        this.table[index] = newNode;
        this.number++;
    }
    
    // * 3.4.3
    public void deleteAbove(int k){
        for(int i = 0; i < this.tableSize; i++){
            Node parent = null;
            Node cur = this.table[i];
            while(cur != null){
                if(cur.numberAtInsertion > k){
                    if(parent != null){
                        parent.next = cur.next;
                        cur.next = null;
                        cur = parent;
                    }else{
                        this.table[i] = cur.next;
                        cur.next = null;
                        cur = this.table[i];
                        continue;       // ! important
                    }
                }
                parent = cur;
                cur = cur.next;
            }
        }
    }

    public void print(){
        for(int i = 0; i < this.tableSize; i++){
            StdOut.print("table #" + i + ": ");
            Node cur = this.table[i];
            while(cur !=null){
                StdOut.print("(" +cur.key + ": " + cur.value + ") => ");
                cur = cur.next;
            }
            StdOut.println("null");
        }
    }

    public static void check(){
        SeparateChainingHashSTLess<String, Integer> table = new SeparateChainingHashSTLess<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            table.put(keys[i], i);
        }
        table.print();
        StdOut.println();
        table.deleteAbove(5);
        table.print();
        
    }

    public static void main(String[] args){
        check();
    }
}