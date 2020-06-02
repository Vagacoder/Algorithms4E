package javasrc.ch04_1;

/*
* Search template API. P.528
*/

import lib.*;

public abstract class Search{

    // * find vertices connected to a source vertex s
    Search(Graph G, int s){

    }

    // * is v connected to s?
    public abstract boolean marked(int v);

    // * how many vertices are connected to s?
    public abstract int count();
}