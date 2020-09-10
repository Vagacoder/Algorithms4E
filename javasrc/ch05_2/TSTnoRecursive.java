package javasrc.ch05_2;

/*
 * 5.2.5 Develop nonrecursive versions of TrieST and TST.
 */

import javasrc.ch01_3.LinkedListQueue;
import lib.*;

public class TSTnoRecursive<Value> {
    
    private class Node{
        char c;
        Node left, mid, right;
        Value val;
    }

    private Node root;

    public TSTnoRecursive(){
        this.root = new Node();
    }

    public void put(String key, Value val){
        Node cur = this.root;
        int length = key.length();
        int d = 0;

        while ( d < length){
            char c = key.charAt(d);

            if(cur.c == 0){
                cur.c = c;
            }

            if (c < cur.c){
                if(cur.left == null){
                    cur.left = new Node();
                }
                cur = cur.left;
            } else if ( c > cur.c){
                if(cur.right == null){
                    cur.right = new Node();
                }
                cur = cur.right;
            } else if (d < length - 1){
                if(cur.mid == null){
                    cur.mid = new Node();
                }
                cur = cur.mid;
                d++;
            } else {
                cur.val = val;
                d++;
            }
        }
    }

    public Value get(String key){
        Node cur = this.root;
        int length = key.length();
        int d = 0;

        while (cur != null){
            if(d > length){
                break;
            }
            
            char c = key.charAt(d);
            
            if(d == length - 1 && c == cur.c){
                return cur.val;
            }

            if(c < cur.c){
                cur = cur.left;
            }else if (c > cur.c){
                cur = cur.right;
            }else {
                cur = cur.mid;
                d++;
            }

        }

        return null;
    }


    public static void main(String[] args){
        TSTnoRecursive<Integer> tst = new TSTnoRecursive<>();
        String[] strs = {"this", "is", "a", "good", "day", "to", "die"};
        
        for (int i = 0; i < strs.length; i++){
            tst.put(strs[i], i);
        }

        for (int i =0; i < strs.length; i++){
            StdOut.println(tst.get(strs[i]));
        }

        Integer s1 = tst.get("iss");
        if(s1 != null){
            StdOut.println(s1);
        }else{
            StdOut.println("Not found in trie!");
        }

        Integer s2 = tst.get("g");
        if(s2 != null){
            StdOut.println(s2);
        }else{
            StdOut.println("Not found in trie!");
        }

    }
}
