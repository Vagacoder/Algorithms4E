package javasrc.ch05_2;

import edu.princeton.cs.algs4.StdOut;
import javasrc.ch01_3.LinkedListQueue;

/*
 *  5.2.5 Develop nonrecursive versions of TrieST and TST.
 * 
 */

import lib.*;

public class TrieSTnoRecursive<Value> {

    private static int R = 256;

    private static class Node{
        private Object val;
        private Node[] next = new Node[R];
    }

    private Node root;

    public TrieSTnoRecursive(){
        this.root = new Node();
    }

    public void put(String key, Value val){
        Node cur = this.root;
        int length = key.length();
        int d = 0;

        while (d < length){
            // ? i for show the index of Trie.next
            // int i = key.charAt(d);
            char c = key.charAt(d);
            if (cur.next[c] == null){
                cur.next[c] = new Node();
            }
            cur = cur.next[c];

            d++;
        }
        cur.val = val;
    }

    public Value get(String key){
        Node cur = this.root;
        int length = key.length();
        int d = 0;

        while(cur != null){
            if(d > length){
                break;
            }
            if(d == length){
                return (Value) cur.val;
            }
            // ? i for show the index of Trie.next
            // int i = key.charAt(d);
            char c = key.charAt(d);
            cur = cur.next[c];
            d++;
        }

        return null;
    }

    private Node getNode(String key){
        Node cur = this.root;
        int length = key.length();
        int d = 0;

        while(cur != null){
            if(d > length){
                break;
            }
            if(d == length){
                return cur;
            }
            // ? i for show the index of Trie.next
            // int i = key.charAt(d);
            char c = key.charAt(d);
            cur = cur.next[c];
            d++;
        }

        return null;
    }

    public Iterable<String> keys(){
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre){
        LinkedListQueue<String> q = new LinkedListQueue<>();
        collect(getNode(pre), pre, q);
        return q;
    }

    private void collect(Node x, String pre, LinkedListQueue<String> q){
        if(x == null){
            return;
        }
        if(x.val != null){
            q.enqueue(pre);
        }
        for(char c = 0; c< R; c++){
            collect(x.next[c], pre + c, q);
        }
    }

    // TODO: longest prefix of , delete

    public static void main(String[] args){
        TrieSTnoRecursive<Integer> trie = new TrieSTnoRecursive<>();
        String[] strs = {"this", "is", "a", "good", "day", "to", "die"};
        
        for (int i = 0; i < strs.length; i++){
            trie.put(strs[i], i);
        }

        for (int i =0; i < strs.length; i++){
            StdOut.println(trie.get(strs[i]));
        }

        Integer s1 = trie.get("iss");
        if(s1 != null){
            StdOut.println(s1);
        }else{
            StdOut.println("Not found in trie!");
        }

        Integer s2 = trie.get("g");
        if(s2 != null){
            StdOut.println(s2);
        }else{
            StdOut.println("Not found in trie!");
        }

        Node node = trie.getNode("");
        if(node == trie.root){
            StdOut.println("It is root");
        }else{
            StdOut.println("It is NOT root");
        }
    }
}
