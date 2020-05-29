package javasrc.ch04_1;

/*
 * Paths template. P.535 
 */

public abstract class Paths {

    public abstract boolean hasPathTo(int v);

    public abstract Iterable<Integer> pathTo(int v);

}