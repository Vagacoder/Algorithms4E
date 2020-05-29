package javasrc.ch04_1;

/*
* Search template API. P.528
*/

import lib.*;

public abstract class Search{

    Search(Graph G, int s){

    }

    public abstract boolean marked(int v);

    public abstract int count();
}