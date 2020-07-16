package javasrc.ch04_4;

/*
 * 4.4.7 Develop a version of DijkstraSP that supports a client method that returns 
 * a second shortest path from s to t in an edge-weighted digraph (and returns 
 * null if there is only one shortest path from s to t).
  
 ? sample file:
 ? 1. tinyEWDG.txt (tinyEWD.txt in textbook), P.653

 ! Algorithm:
 ? 1. use Dijkstra find shortest path, p, in original g.
 ? 2. remove edges of p, one by one to generate a serial new graph newGs.
 ? 3. use Dijkstra calculate new shortest path newP for ech newG.
 ? 4. find the shortest one among all new shortes path newP.

 ! But this is not implemented in DijkstraSP. This is more like a client of 
 ! DijkstraSP.
 */

public class DijkstraSP2nd2 {
    
}