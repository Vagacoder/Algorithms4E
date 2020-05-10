package javasrc.ch03_4;

/*
* 3.4.31 Cuckoo hashing. Develop a symbol-table implementation that maintains two
hash tables and two hash functions. Any given key is in one of the tables, but not both.
When inserting a new key, hash to one of the tables; if the table position is occupied,
replace that key with the new key and hash the old key into the other table (again kick-
ing out a key that might reside there). If this process cycles, restart. Keep the tables less
than half full. This method uses a constant number of equality tests in the worst case
for search (trivial) and amortized constant time for insert.

*/

import lib.*;

public class CuckooHashST<Key, Value>{

    // * inner class for key-value pair
    class Node{
        Key key;
        Value value;

        Node(){}

        Node(Key key, Value value){
            this.key = key;
            this.value = value;
        }
    }

    public static final int INITIAL_SIZE = 30;
    // * hash table a size
    private int mA;
    // * hash table b size
    private int mB;
    // * node number in table a
    private int nA;
    // * node number in table b
    private int nB;

    private Node[] tableA;
    private Node[] tableB;

    public CuckooHashST(){
        this(INITIAL_SIZE);
    }

    public CuckooHashST(int M){
        this.mA = M;
        this.mB = M;
        tableA = new CuckooHashST.Node[mA];
        tableB = new CuckooHashST.Node[mB];
    }

    private int hashA(Key key){
        return (key.hashCode() & 0x7fffffff) * 11 % mA;
    }

    private int hashB(Key key){
        return (key.hashCode() & 0x7fffffff) * 17 % mB;
    }

    public void put(Key key, Value value){
        putA(key, value);
    }

    private void putA(Key key, Value value){
        if(nA >= mA/2){
            resize(mA * 2, true);
        }

        int iA = hashA(key);
        if(tableA[iA] != null){
            if(tableA[iA].key.equals(key)){
                tableA[iA].value = value;
                return;
            }else {
                Node temp = tableA[iA];
                tableA[iA] = new Node(key, value);
                putB(temp.key, temp.value);
            }
        }else{
            tableA[iA] = new Node(key, value);
            nA++;
        }
    }

    private void putB(Key key, Value value){
        if(nB >= mB/2){
            resize(mB * 2, false);
        }

        int iB = hashB(key);
        if(tableB[iB] != null){
            if(tableB[iB].key.equals(key)){
                tableB[iB].value = value;
                return;
            }else{
                Node temp = tableB[iB];
                tableB[iB] = new Node(key, value);
                putA(temp.key, temp.value);
            }
        }else{
            tableB[iB] = new Node(key, value);
            nB++;
        }
    }

    public Value get(Key key){
        int iA = hashA(key);
        if(tableA[iA] != null && tableA[iA].key.equals(key)){
            return tableA[iA].value;
        }
        int iB = hashB(key);
        if(tableB[iB] != null && tableB[iB].key.equals(key)){
            return tableB[iB].value;
        }
        return null;
    }

    private void resize(int mNew, boolean isA){
        Node[] newTable = new CuckooHashST.Node[mNew];
        
        if(isA){
            this.mA = mNew;
            for(int i = 0; i < tableA.length; i++){
                Node temp = tableA[i];
                int hash = hashA(temp.key);
                newTable[hash] = temp;
            }
            this.tableA = newTable;
        }else {
            this.mB = mNew;
            for(int i = 0; i < tableB.length; i++){
                Node temp = tableB[i];
                int hash = hashB(temp.key);
                newTable[hash] = temp;
            }
            this.tableB = newTable;
        }
    }

    public static void check(){
        CuckooHashST<String, Integer> chTable = new CuckooHashST<>();
        String[] keys = {"S", "E", "A", "R", "C", "H", "E","X", "A", "M", "P", "L", "E"};
        for(int i =0; i < keys.length; i++){
            chTable.put(keys[i], i);
        }
        StdOut.println(chTable.get("S"));
        StdOut.println(chTable.get("C"));
        StdOut.println(chTable.get("E"));
    }
    public static void main(String[] args){
        check();
    }
}