package javasrc.ch04_1;

import java.util.regex.Pattern;

import javasrc.ch01_3.BagX;
import javasrc.ch03_4.SeparateChainingHashST;
import lib.*;

/*
 * 4.1.23 Write a program BaconHistogram that prints a histogram of Kevin Bacon 
 * numbers, indicating how many performers from movies.txt have a Bacon number 
 * of 0, 1, 2, 3, ... . Include a category for those who have an infinite number 
 * (not connected to Kevin Bacon).
 * 
 * Test using data/moviesSmall.txt

*/

public class BaconHistogram {

    SymbolGraph sg;
    Graph graph;

    int highestBaconNumbner = 0;
    SeparateChainingHashST<Integer, BagX<String>> histogram = new SeparateChainingHashST<>();
    BagX<String> noConnections;

    public BaconHistogram() {
        this.sg = new SymbolGraph("data/moviesSmall.txt", "/");
        this.graph = sg.G();
        this.noConnections = new BagX<>();

        int baconIndex = sg.index("Bacon, Kevin");

        for (int i = 0; i < graph.V(); i++) {
            String vertexName = sg.name(i);
            if (!vertexName.matches(".*\\(\\d{4}\\)")) {
                BreadthFirstPaths bfs = new BreadthFirstPaths(this.graph, baconIndex);

                if (bfs.hasPathTo(i)) {
                    int currentPerformerBaconNumber = bfs.distTo(i);
                    BagX<String> bag = histogram.get(currentPerformerBaconNumber);
                    if (bag == null) {
                        bag = new BagX<>();
                    }
                    bag.add(vertexName);
                    histogram.put(currentPerformerBaconNumber, bag);
                    if (currentPerformerBaconNumber > highestBaconNumbner) {
                        highestBaconNumbner = currentPerformerBaconNumber;
                    }
                } else {
                    this.noConnections.add(vertexName);
                }
            }
        }
    }

    public void print() {
        for (int i = 0; i <= this.highestBaconNumbner; i++) {
            // ! i/2 since this graph is bipartite, odd dist is always movie, 
            // ! even dist is always actor.
            if(i != 0 && i % 2 == 1){
                continue;
            }
            StdOut.print("Bacon number " + (i/2) + ": ");

            BagX<String> bag = histogram.get(i);
            if (bag != null) {
                for (String name : bag) {
                    if (name != null) {
                        StdOut.print(name + ", ");
                    }
                }
            }
            StdOut.println();
        }
        StdOut.println();
        StdOut.print("No connectio: ");
        for (String name : noConnections) {
            StdOut.print(name + ", ");
        }
        StdOut.println();
    }

    public static void main(String[] args) {
        BaconHistogram his = new BaconHistogram();
        his.print();
    }
}