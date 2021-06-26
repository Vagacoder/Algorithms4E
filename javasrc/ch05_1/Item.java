package javasrc.ch05_1;

public class Item {
    
    private int key;
    private String name;

    public Item(int key, String name){
        this.key = key;
        this.name = name;
    }

    public int getKey(){
        return this.key;
    }

    public String getName(){
        return this.name;
    }
}
