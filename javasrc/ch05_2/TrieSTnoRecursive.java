package javasrc.ch05_2;

/*
*  5.2.5 Develop nonrecursive versions of TrieST and TST.
* 
*/

import javasrc.ch01_3.LinkedListQueue;
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

    public Iterable<String> keysThatMatch(String pattern){
        LinkedListQueue<String> q = new LinkedListQueue<>();
        collect(root, "", pattern, q);
        return q;
    }

    private void collect(Node x, String pre, String pattern, LinkedListQueue<String>q){
        int d = pre.length();
        if(x == null){
            return;
        }
        if(d == pattern.length() && x.val != null){
            q.enqueue(pre);
        }
        if(d == pattern.length()){
            return;
        }

        char next = pre.charAt(d);
        for(char c = 0; c <R; c++){
            if(next == '.' || next ==c){
                collect(x.next[c], pre + c, pattern, q);
            }
        }
    }

    public String longestPrefixOf(String s){
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length){
        if(x == null){
            return length;
        }
        if(x.val != null){
            length = d;
        }
        if(d == s.length()){
            return length;
        }
        char c = s.charAt(d);
        return search(x.next[c], s, d+1, length);

    }

    public void delete(String key){
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d){
        if(x == null){
            return null;
        }

        if(d == key.length()){
            x.val = null;
        }else{
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c], key, d+1);
        }

        if(x.val != null){
            return x;
        }

        for(char c=0; c < R; c++){
            if(x.next[c] != null){
                return x;
            }
        }

        return null;
    }

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
