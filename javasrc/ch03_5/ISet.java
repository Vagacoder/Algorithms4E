package javasrc.ch03_5;

public interface ISet<Key> {
    
    void add(Key key);
    void delete(Key key);
    boolean contains(Key key);
    boolean isEmpty();
    int size();
    String toString();
    void print();
}