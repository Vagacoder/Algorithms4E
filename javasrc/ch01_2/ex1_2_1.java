package javasrc.ch01_2;

import lib.Point2D;
import lib.StdOut;
import lib.StdRandom;
import lib.StdDraw;

public class ex1_2_1{

    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        if(N<2){
            StdOut.println("Please enter at least 2 points.");
        }

        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.ORANGE);

        Point2D[] points = new Point2D[N];
        for(int i = 0; i< N; i++){
            double x = StdRandom.uniform();
            double y = StdRandom.uniform();
            Point2D p = new Point2D(x, y);
            points[i] = p;
            // StdOut.println("X is : " + x + " Y is: " + y);
            // StdDraw.point(x, y);
            p.draw();
        }

        double minDistance = points[0].distanceTo(points[1]);;
        for(int i = 0; i < N; i++){
            for (int j = i+1; j< N; j++){
                double distance = points[i].distanceTo(points[j]);
                if (distance < minDistance){
                    minDistance = distance;
                }
            }
        }

        StdOut.printf("Min distance is: %.10f", minDistance);

    }
}