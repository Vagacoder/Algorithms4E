package javasrc.ch03_5;

/*
* 3.5.11 Develop a MultiSET class that is like SET, but allows equal keys and 
thus implements a mathematical multiset.

*/


public class MultiSET<Key> {
    

    private LinearProbingHashSTdup<Key, Integer> table;

    public MultiSET(){
        table = new LinearProbingHashSTdup<>();
    }

    public void put(Key key){
        table.put(key, 1);
    }

    public int size(){
        return table.size();
    }

    public boolean contains(Key key){
        return table.contains(key);
    }

    public static void main(String[] args){

    }
}