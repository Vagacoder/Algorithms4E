package javasrc.ch05_1;

/*
 * Key-indexed counting. P.703
 * 
 * 
 */

import lib.*;

public class KeyindexedCounting {
    
    private Item[] items;
    private int R = 0;

    public KeyindexedCounting (Item[] items){
        this.items = items;
        for (Item item : items){
            if (this.R < item.getKey()){
                this.R = item.getKey();
            }
        }
        // StdOut.println("R is: " + this.R);
    }

    public Item[] sort(){
        // * Step 1, compute frequency counts
        int N = this.items.length;
        int[] count = new int[R+2];
        for (int i = 0; i < N; i++){
            count[items[i].getKey() + 1]++;
        }

        // * Step 2, transform counts to indices
        for (int r = 0; r < this.R; r++){
            count[r+1] += count[r];
        }

        // * Step 3, Distribute the data
        Item[] aux = new Item[N];
        for (int i = 0; i < N; i++){
            aux[count[this.items[i].getKey()]++] = this.items[i];
        }

        // * Step 4, copy back, skipped
        return aux;
    }

    public static void main(String[] args){
        Item[] items = new Item[]{
            new Item(2, "Anderson"),
            new Item(3, "Brown"),
            new Item(3, "Davis"),
            new Item(4, "Garcia"),
            new Item(1, "Harris"),
            new Item(3, "Jackson"),
            new Item(4, "Johonson"),
            new Item(3, "Jones"),
            new Item(1, "Martin"),
            new Item(2, "Martinez"),
            new Item(2, "Miller"),
            new Item(1, "Moore"),
            new Item(2, "Robinson"),
            new Item(4, "Smith"),
            new Item(0, "TIGER!"),
            new Item(3, "Taylor"),
            new Item(4, "Thomas"),
            new Item(4, "Thompson"),
            new Item(2, "White"),
            new Item(3, "Williams"),
            new Item(4, "Wilson")
        };

        KeyindexedCounting kc = new KeyindexedCounting(items);
        Item[] result = kc.sort();
        for (Item item : result){
            StdOut.printf("%s : %d\n", item.getName(), item.getKey());
        }
    }

}
