package javasrc.ch03_5;

/*
* 3.5.4 Develop classes HashSTint and HashSTdouble for maintaining sets of keys of
primitive int and double types, respectively. (Convert generics to primitive types in
the code of LinearProbingHashST.)

* NOTE: key cannot be Integer.MIN_VALUE, which is used as empty sign.
*/

import lib.*;

public class HashSTint<Value>{

    
    // * default table size is 31
    private int tableSize = 31;
    private int size;
    private int[] keys;
    private Value[] values;

    public HashSTint(){
        keys = new int[tableSize];

        // ! using Integer.MIN_VALUE as sign of empty
        for(int i = 0; i < tableSize; i++){
            keys[i] = Integer.MIN_VALUE;
        }
        values = (Value[]) new Object[tableSize];
    }

    public HashSTint(int tableSize){
        this.tableSize = tableSize;
        keys = new int[tableSize];
        for(int i = 0; i < tableSize; i++){
            keys[i] = Integer.MIN_VALUE;
        }
        values = (Value[]) new Object[tableSize];
    }

    private int hash(int key) {
        return (key & 0x7fffffff) % tableSize;
    }

    private void resize(int newTableSize){
        int[] tempKeys = new int[newTableSize];
        for(int i = 0; i < newTableSize; i++){
            tempKeys[i] = Integer.MIN_VALUE;
        }

        Value[] tempValues = (Value[]) new Object[newTableSize];
        
        // ! hash() below must use new table size
        int oldTableSize = this.tableSize;
        this.tableSize = newTableSize;

        for(int i = 0; i< oldTableSize; i++){
            if(keys[i]!= Integer.MIN_VALUE){
                int key = keys[i];
                Value value = values[i];
                boolean found = false;
                int j;
                for(j = hash(key); tempKeys[j] != Integer.MIN_VALUE; j = (j + 1)% tableSize){
                    if(tempKeys[j] == key){
                        tempValues[j] = value;
                        found = true;
                        break;
                    }
                }
                if(!found){
                    tempKeys[j] = key;
                    tempValues[j] = value;
                }
            }
        }
        this.keys = tempKeys;
        this.values = tempValues;
    }

    public void put(int key, Value value){
        // ! using Integer.MIN_VALUE as sign of empty
        if(key == Integer.MIN_VALUE){
            throw new IllegalArgumentException("Key out of range");
        }
        if(this.size >= this.tableSize /2){
            resize(2 * tableSize);
        }

        int i;
        for(i = hash(key); keys[i] != Integer.MIN_VALUE; i = (i + 1)% tableSize){
            if(keys[i] ==key){
                values[i] = value;
                return;
            }
        }
        this.keys[i] = key;
        this.values[i] = value;
        this.size++;
    }

    public Value get(int key){
        // ! using Integer.MIN_VALUE as sign of empty
        if(key == Integer.MIN_VALUE){
            throw new IllegalArgumentException("Key out of range");
        }

        for(int i = hash(key); keys[i] != Integer.MIN_VALUE; i = (i+1)% tableSize){
            if(keys[i] == key){
                return values[i];
            }
        }

        return null;
    }

    public void delete(int key){
        int hash = hash(key);
        int i;
        boolean found = false;
        for (i = hash; keys[i] != Integer.MIN_VALUE; i = (i + 1) % tableSize) {
            if (!found) {
                if (keys[i] == key) {
                    keys[i] = Integer.MIN_VALUE;
                    values[i] = null;
                    this.size--;
                    found = true;
                }
            }else{
                int tempKey = keys[i];
                Value tempValue = values[i];
                keys[i] = Integer.MIN_VALUE;
                values[i] = null;
                this.size--;
                put(tempKey, tempValue);
            }
        }
    }

    public void print() {
        for(int i = 0; i < this.tableSize; i++){
            if(keys[i]!= Integer.MIN_VALUE){
                StdOut.println(keys[i] + ": " + values[i].toString());
            }
        }
        StdOut.println("size: " + this.size);
    }

    public static void main(String[] args){
        HashSTint<String> intSt = new HashSTint<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            intSt.put(i-5, keys[i]);
        }

        intSt.print();

        StdOut.println();
        intSt.delete(4);
        intSt.print();

        StdOut.println();
        StdOut.println(intSt.get(0));

        StdOut.println();
        StdOut.println(intSt.get(7));

        StdOut.println();
        StdOut.println(intSt.get(12));

        StdOut.println();
        StdOut.println(intSt.get(-1));

        StdOut.println();
        intSt.delete(-4);
        intSt.print();
    }

}