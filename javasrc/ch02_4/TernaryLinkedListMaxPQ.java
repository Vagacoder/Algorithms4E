package javasrc.ch02_4;

/* 
* 2.4.24 Priority queue with explicit links. 

Implement a priority queue using a heap-ordered binary tree, but use a triply 
linked structure instead of an array. You will need three links per node: two to 
traverse down the tree and one to traverse up the tree. Your implementation should 
guarantee logarithmic running time per operation, even if no maximum priority-queue 
size is known ahead of time.

*/

import lib.*;

public class TernaryLinkedListMaxPQ<Key extends Comparable<Key>> {

    private class Node {
        Key item;
        Node parent;
        Node left;
        Node right;
    }

    private Node first;
    private int size;

    public TernaryLinkedListMaxPQ() {
        this.first = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public void insert(Key newKey) {
        Node newNode = new Node();
        newNode.item = newKey;

        // * empty
        if (this.first == null) {
            this.first = newNode;
            this.size++;
            return;
        }

        // * find a leaf
        Node cur = this.first;
        while (cur.left != null) {
            if (cur.right != null) {
                if (cur.left.left != null && cur.left.right != null) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            } else {
                break;
            }
        }

        // * add new node to leaf
        if (cur.left == null) {
            cur.left = newNode;
        } else {
            cur.right = newNode;
        }
        newNode.parent = cur;
        this.size++;
        swim(newNode);
    }

    private void swim(Node cur) {
        while (cur.parent != null && less(cur.parent, cur)) {

            // * get tree structure information: parent, grandparent, left or right etc.
            Node parent = cur.parent;
            Node gParent = parent.parent;

            boolean isCurOnLeft = true;
            Node curSiblin = null;

            if (cur == parent.left) {
                isCurOnLeft = true;
                curSiblin = parent.right;
            } else {
                isCurOnLeft = false;
                curSiblin = parent.left;
            }

            boolean isParentOnLeft = true;
            if (gParent != null && parent == gParent.right) {
                isParentOnLeft = false;
            }

            // * move parent down to cur
            parent.parent = cur;
            parent.left = cur.left;
            parent.right = cur.right;
            if (cur.left != null) {
                cur.left.parent = parent;
            }
            if (cur.right != null) {
                cur.right.parent = parent;
            }

            // * lift cur up to parent
            if (isCurOnLeft) {
                cur.left = parent;
                cur.right = curSiblin;
            } else {
                cur.left = curSiblin;
                cur.right = parent;
            }
            if (curSiblin != null) {
                curSiblin.parent = cur;
            }

            cur.parent = gParent;

            // * parent is not first, set grandparent
            if (gParent != null) {
                if (isParentOnLeft) {
                    gParent.left = cur;
                } else {
                    gParent.right = cur;
                }
            }
            // * paren is first, set first
            else {
                this.first = cur;
            }
        }
    }

    public Key delMax() {
        // * empty queue
        if (this.first == null) {
            return null;
        }

        Node max = this.first;
        Node leaf = findLeaf();
        // StdOut.println(leaf.item);

        // * 1 element;
        if (leaf == this.first) {
            this.first = null;
            this.size--;
            return max.item;
        }

        // * 2 - 3 elements, tree height = 1;
        // ? this case is special, when leaf lift to first, leaf'children are 
        // ? set to first'children, one of which is leaf. So that results in a 
        // ? loop structure. 
        if (leaf == this.first.left) {
            leaf.left = this.first.right;
            if (leaf.left != null) {
                leaf.left.parent = leaf;
            }
            leaf.parent = null;
            this.first.left = null;
            this.first.right = null;
            this.first = leaf;
            this.size--;
            sink(this.first);
            return max.item;
        } else if (leaf == this.first.right) {
            leaf.left = this.first.left;
            if (leaf.left != null) {
                leaf.left.parent = leaf;
            }
            leaf.parent = null;
            this.first.left = null;
            this.first.right = null;
            this.first = leaf;
            this.size--;
            sink(this.first);
            return max.item;
        }

        // * other situations, elements # > 3, tree height > 1, leaf and first are
        // * not adjacent.

        // * move leaf to first
        // ? these 2 operations will cause loop structure if tree heigh = 1 ====
        leaf.left = this.first.left;
        leaf.right = this.first.right;
        // ? ====================================================================

        if (leaf.left != null) {
            leaf.left.parent = leaf;
        }
        if (leaf.right != null) {
            leaf.right.parent = leaf;
        }
        
        // * leaf parent's link to leaf => null
        if(leaf.parent.left == leaf){
            leaf.parent.left = null;
        }else {
            leaf.parent.right = null;
        }
        
        leaf.parent = null;

        // * remove first
        this.first.left = null;
        this.first.right = null;
        
        // * rebuild heap
        this.first = leaf;
        sink(this.first);
        this.size--;
        return max.item;
    }

    private Node findLeaf() {
        Node cur = this.first;
        while (cur.left != null) {
            if (cur.left.left == null && cur.right != null) {
                cur = cur.right;
            } else {
                cur = cur.left;
            }
        }
        return cur;
    }

    private void sink(Node cur) {
        while (!(cur.left == null && cur.right == null)) {

            // * get tree structure informations
            Node parent = cur.parent;
            boolean isCurOnLeft = true;

            if (parent != null) {
                if (cur == parent.left) {
                } else {
                    isCurOnLeft = false;
                }
            }

            Node largeChild = null;
            Node smallChild = null;
            boolean isLargeOnLeft = true;

            // * only one child
            if (cur.left == null || cur.right == null) {
                if (cur.left == null) {
                    largeChild = cur.right;
                    smallChild = cur.left;
                    isLargeOnLeft = false;
                } else {
                    largeChild = cur.left;
                    smallChild = cur.right;
                }
            } 
            // * two children, compare to find larger one
            else {
                if (less(cur.left, cur.right)) {
                    largeChild = cur.right;
                    smallChild = cur.left;
                    isLargeOnLeft = false;
                } else {
                    largeChild = cur.left;
                    smallChild = cur.right;
                }
            }

            if (less(largeChild, cur)) {
                break;
            }
            
            // * move cur down
            cur.left = largeChild.left;
            cur.right = largeChild.right;
            if (cur.left != null) {
                cur.left.parent = cur;
            }
            if (cur.right != null) {
                cur.right.parent = cur;
            }
            cur.parent = largeChild;

            // * lift large child up
            if (parent != null) {
                if (isCurOnLeft) {
                    parent.left = largeChild;
                } else {
                    parent.right = largeChild;
                }
                largeChild.parent = parent;
            } else {
                this.first = largeChild;
            }

            if (isLargeOnLeft) {
                largeChild.left = cur;
                largeChild.right = smallChild;
            } else {
                largeChild.right = cur;
                largeChild.left = smallChild;
            }
            if (smallChild != null) {
                smallChild.parent = largeChild;
            }


        }
    }

    private boolean less(Node a, Node b) {
        return a.item.compareTo(b.item) < 0;
    }

    public void print() {
        Node cur = this.first;
        if (cur == null) {
            StdOut.println("empty");
        } else {
            int level = 0;
            print(level, cur);
        }
    }

    private void print(int level, Node cur) {
        String leadingTabs = "";
        for (int i = 0; i < level; i++) {
            leadingTabs += "\t";
        }
        StdOut.println(leadingTabs + cur.item.toString());
        if (cur.left != null) {
            print(level + 1, cur.left);
        }
        if (cur.right != null) {
            print(level + 1, cur.right);
        }
    }

    public static void check() {
        TernaryLinkedListMaxPQ<String> pq = new TernaryLinkedListMaxPQ<>();
        pq.insert("this");
        pq.insert("is");
        pq.insert("a");
        pq.insert("unit");
        pq.insert("test");
        pq.insert("for");
        pq.insert("question");

        pq.print();
        StdOut.println(pq.size());

        StdOut.println(pq.delMax());
        pq.print();
        StdOut.println(pq.size());
    }

    public static void main(String[] args) {

        StdOut.println("1. testing ...");
        check();

    }
}