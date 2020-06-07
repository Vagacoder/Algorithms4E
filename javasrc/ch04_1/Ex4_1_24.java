package javasrc.ch04_1;

import java.util.HashMap;

import javasrc.ch03_4.SeparateChainingHashST;

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
        int numberOfSizeLessThan10 = 0;
        for(int i = 0; i < componentNumber; i++){
            int size = cc.componentSize(i);
            if(size > maxComponentSize){
                maxComponentSize = size;
                maxComponentIndex = i;
            }
            if(size < 10){
                numberOfSizeLessThan10 ++;
            }
            StdOut.println(size);
        }

        StdOut.println("Max component id is: " + maxComponentIndex + ", size is: "
        + maxComponentSize );

        StdOut.println("Number of component whose size is less than 10 is: " +
            numberOfSizeLessThan10);
        
        int[] verticesOfLargestComponent = cc.getFirstVertexId(maxComponentIndex);

        for(int i : verticesOfLargestComponent){
            StdOut.print(i + ", ");
        }
        StdOut.println();

        SeparateChainingHashST<Integer, Integer> longestPaths = new SeparateChainingHashST<>();
        for(int i = 0; i < verticesOfLargestComponent.length; i++){
            int vertexId = verticesOfLargestComponent[i];
            BreadthFirstPaths bfp = new BreadthFirstPaths(graph, vertexId);
            int longestP = 0;
            for( int j = 0; j < verticesOfLargestComponent.length; j++){
                int currentP = bfp.distTo(verticesOfLargestComponent[j]);
                if(currentP > longestP){
                    longestP = currentP;
                }
            }
            longestPaths.put(vertexId, longestP);
        }

        int diameter = -1;
        int radius = Integer.MAX_VALUE;
        int center = -1;
        for(int i = 0; i < verticesOfLargestComponent.length; i++){
            int vertexId = verticesOfLargestComponent[i];
            int currentP = longestPaths.get(vertexId);
            if(currentP > diameter){
                diameter = currentP;
            }
            if(currentP < radius){
                radius = currentP;
                center = vertexId;
            }
        }
        StdOut.println("Diameter of largest component is: " + diameter);
        StdOut.println("Radius of largest component is: " + radius);
        StdOut.println("Center of largest component is: " + center);
    }
}