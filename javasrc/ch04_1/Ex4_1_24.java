package javasrc.ch04_1;

/*
 * 4.1.24 Compute the number of connected components in movies.txt, the size of 
 * the largest component, and the number of components of size less than 10. 
 * Find the eccentricity, diameter, radius, a center, and the girth of the largest 
 * component in the graph. Does it contain Kevin Bacon?

*/

import lib.*;

public class Ex4_1_24{

    public static void main(String[] args){
        SymbolGraph sg = new SymbolGraph("data/moviesSmall.txt", "/");
        Graph graph = sg.G();
        CC cc = new CC(graph);
        int componentNumber = cc.count();
        int maxComponentIndex = -1;
        int maxComponentSize = -1;
        for(int i = 0; i <= componentNumber; i++){
            int size = cc.componentSize(i);
            if(size > maxComponentSize){
                maxComponentSize = size;
                maxComponentIndex = i;
            }
            StdOut.println(size);
        }

        
        
    }
}