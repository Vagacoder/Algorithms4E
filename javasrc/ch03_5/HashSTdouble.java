package javasrc.ch03_5;

import lib.*;

/*
* * 3.5.4 Develop classes HashSTint and HashSTdouble for maintaining sets of keys of
primitive int and double types, respectively. (Convert generics to primitive types in
the code of LinearProbingHashST.)

* NOTE: key cannot be Double.MIN_VALUE, which is used as empty sign.
*/

public class HashSTdouble<Value> {

    private double[] keys;
    private Value[] values;

    private int capacity = 31;
    private int size;

    public HashSTdouble(){
        keys = new double[capacity];
        for(int i = 0; i < capacity; i++){
            keys[i] = Double.MIN_VALUE;
        }
        values = (Value[]) new Object[capacity];
    }

    public HashSTdouble(int capacity){
        this.capacity = capacity;
        keys = new double[capacity];
        for(int i = 0; i < capacity; i++){
            keys[i] = Double.MIN_VALUE;
        }
        values = (Value[]) new Object[capacity];
    }

    private int hash(double key){
        long bits = Double.doubleToLongBits(key);
        // StdOut.println(Long.toHexString(bits));
        int hashCode = (int) (bits ^ (bits >>> 32));

        return (hashCode & 0x7fffffff) % capacity;
    }

    private void resize(int newCapacity){
        double[] newKeys = new double[newCapacity];
        for(int i = 0; i< newCapacity; i++){
            newKeys[i] = Double.MIN_VALUE;
        }
        Value[] newValues = (Value[]) new Object[newCapacity];

        int oldCapacity = this.capacity;
        this.capacity = newCapacity;

        for(int i=0; i < oldCapacity; i++){
            if(keys[i] != Double.MIN_VALUE){
                double key = keys[i];
                Value value = values[i];
                boolean found = false;
                int j;
                for(j = hash(key); newKeys[j] != Double.MIN_VALUE; j = (j+1) % capacity){
                    if(newKeys[j] == key){
                        newValues[j] = value;
                        found = true;
                        break;
                    }
                }
                if(!found){
                    newKeys[j] = key;
                    newValues[j] = value;
                }
            }
        }
        this.keys = newKeys;
        this.values = newValues;
    }

    public void put(double key, Value value){
        if(key == Double.MIN_VALUE){
            throw new IllegalArgumentException("Key out of range");
        }
        if(this.size >= this.capacity /2){
            resize(2 * capacity);
        }

        int i;
        for(i = hash(key); keys[i] != Double.MIN_VALUE; i = (i+1)%capacity){
            if(keys[i] == key){
                values[i] = value;
                return;
            }
        }
        this.keys[i] = key;
        this.values[i]= value;
        this.size++;
    }

    public Value get(double key){
        if(key == Double.MIN_VALUE){
            throw new IllegalArgumentException("Key out of range");
        }

        for(int i = hash(key); keys[i] != Double.MIN_VALUE; i = (i+1)%capacity){
            if(keys[i] == key){
                return values[i];
            }
        }
        return null;
    }

    public void delete(double key){
        int i;
        boolean found = false;
        for(i = hash(key); keys[i] != Double.MIN_VALUE; i =(i+1)%capacity){
            if(!found){
                if(keys[i] == key){
                    keys[i] = Double.MIN_VALUE;
                    values[i] = null;
                    this.size--;
                    found = true;
                }
            }else{
                double tempKey = keys[i];
                Value tempValue = values[i];
                keys[i] = Double.MIN_VALUE;
                values[i] = null;
                this.size--;
                put(tempKey, tempValue);
            }
        }
    }

    public void print(){
        for(int i = 0; i< capacity; i++){
            if(keys[i] != Double.MIN_VALUE){
                StdOut.println(keys[i] + ": " + values[i].toString());
            }
        }
        StdOut.println("size: " + this.size);
    }

    public static void main(String[] args){
        HashSTdouble<String> doubleSt = new HashSTdouble<>();
        // StdOut.println(st.hash(1.0f));

        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            doubleSt.put(i-5, keys[i]);
        }

        doubleSt.print();
        StdOut.println();
        
        doubleSt.delete(4.0f);
        doubleSt.print();
        StdOut.println();
    }
    
}