package javasrc.ch05_2;

import javasrc.ch01_3.LinkedListQueue;

/*
 * Algorithm 5.5 TST (Ternary Search Trie) Symbol Table. P.747 
 * 
 * Proposition J. The number of links in a TST built from N string keys of average
 * length w is between 3N and 3Nw.
 * 
 * Proposition K. A search miss in a TST built from N random string keys requires
 * ~ln N character compares, on the average. A search hit or an insertion in a 
 * TST uses ~ln N + L character compares, where L is the length of the search key.
 * 
 * Proposition L. A search or an insertion in a TST built from N random string 
 * keys with no external one-way branching and R t-way branching at the root 
 * requires roughly lnN - t*lnR character compares, on the average.
 * 
 * 5.2.7 Empty string in TSTs. The code in TST does not handle the empty string 
 * properly. Explain the problem and suggest a correction.
 * 
 * 5.2.9 Extended operations for TSTs. Implement keys() and the extended operations 
 * introduced in this section—longestPrefixOf(), keysWithPrefix(), and keysThatMatch()
 * —for TST.
 * 
 * 5.2.10 Size. Implement very eager size() (that keeps in each node the number 
 * of keys in its subtree) for TrieST and TST.
 * 
 */

import lib.*;

public class TST<Value> {

    private class Node {
        char c;
        Node left, mid, right;
        Value val;
    }

    private Node root;

    // * 5.2.10
    private int n;

    public Value get(String key) {
        Node x = get(this.root, key, 0);
        if (x == null) {
            return null;
        }
        return (Value) x.val;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }

        // * 5.2.7 Dealing with empty string
        if (d == key.length()) {
            return x;
        }

        char c = key.charAt(d);
        if (c < x.c) {
            return get(x.left, key, d);
        } else if (c > x.c) {
            return get(x.right, key, d);
        } else if (d < key.length() - 1) {
            return get(x.mid, key, d + 1);
        } else {
            return x;
        }
    }

    public void put(String key, Value val) {
        this.root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d) {

        // * 5.2.7 Dealing with empty string
        if (d == key.length()) {
            this.n++;
            x.val = val;
            return x;
        }

        char c = key.charAt(d);
        if (x == null) {
            x = new Node();
            x.c = c;
        }
        if (c < x.c) {
            x.left = put(x.left, key, val, d);
        } else if (c > x.c) {
            x.right = put(x.right, key, val, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, val, d + 1);
        } else {
            x.val = val;
            this.n++;
        }
        return x;
    }

    // * 5.2.10
    public int size(){
        return this.n;
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        LinkedListQueue<String> q = new LinkedListQueue<>();
        collect(get(this.root, prefix, 0), prefix, q);
        return q;
    }

    private void collect(Node x, String prefix, LinkedListQueue<String> q) {
        if (x == null) {
            return;
        }
        if (x.val != null) {
            q.enqueue(prefix + x.c);
        }
        collect(x.left, prefix, q);
        collect(x.right, prefix, q);
        collect(x.mid, prefix + x.c, q);
    }

    public String longestPrefixOf(String s) {
        int length = search(root, s, 0, 0);
        return s.substring(0, length);
    }

    private int search(Node x, String s, int d, int length) {
        if (x == null) {
            return length;
        }
        if (x.val != null) {
            length = d;
        }
        if (d == s.length()) {
            return length;
        }
        char c = s.charAt(d);
        if (c < x.c) {
            return search(x.left, s, d, length);
        } else if (c > x.c) {
            return search(x.right, s, d, length);
        } else {
            length++;
            return search(x.mid, s, d + 1, length);
        }
    }

    public Iterable<String> keysThatMatch(String pattern) {
        LinkedListQueue<String> q = new LinkedListQueue<>();
        collect(root, "", pattern, q);
        return q;
    }

    private void collect(Node x, String prefix, String pattern, LinkedListQueue<String> q) {
        if (x == null) {
            return;
        }
        int d = prefix.length();
        // if(d == pattern.length() && x.val != null){
        // q.enqueue(prefix);
        // }

        if (d == pattern.length()) {
            return;
        }

        char c = pattern.charAt(d);
        if (c == '.' || c < x.c) {
            collect(x.left, prefix, pattern, q);
        }
        if (c == '.' || c > x.c) {
            collect(x.right, prefix, pattern, q);
        }
        if (c == '.' || c == x.c) {
            prefix = prefix + x.c;
            if (d == pattern.length()-1 && x.val != null) {
                q.enqueue(prefix);
            }
            collect(x.mid, prefix, pattern, q);
        }
    }

    public static void main(String[] args) {
        TST<Integer> tst = new TST<>();
        String[] strs = { "this", "is", "a", "good", "day", "to", "die", "" };

        // * test put and get
        StdOut.println("1. test put() and get()");
        for (int i = 0; i < strs.length; i++) {
            tst.put(strs[i], i);
        }

        for (int i = 0; i < strs.length; i++) {
            StdOut.println(tst.get(strs[i]));
        }

        // * test keys()
        StdOut.println("\n2. test keys()");
        for (String key : tst.keys()) {
            StdOut.println(key);
        }

        // * test longestPrefixOf()
        StdOut.println("\n3. test longestPrefixOf()");
        StdOut.println(tst.longestPrefixOf(""));
        StdOut.println(tst.longestPrefixOf("a"));
        StdOut.println(tst.longestPrefixOf("about"));
        StdOut.println(tst.longestPrefixOf("d"));
        StdOut.println(tst.longestPrefixOf("dayofgodd"));
        StdOut.println(tst.longestPrefixOf("g"));
        StdOut.println(tst.longestPrefixOf("goodman"));
        StdOut.println(tst.longestPrefixOf("i"));
        StdOut.println(tst.longestPrefixOf("is"));
        StdOut.println(tst.longestPrefixOf("isthisok"));

        // * test keysThatMatch
        StdOut.println("\n4. test keysThatMatch");
        Iterable<String> keys = tst.keysThatMatch("day");
        for (String key : keys) {
            StdOut.println(key);
        }
        keys = tst.keysThatMatch("d..");
        for (String key : keys) {
            StdOut.println(key);
        }
    }
}
