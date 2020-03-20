package javasrc.ch02_4;

/*
* Example from lecture slides
* Simulate the motion of n moving particles that behaveaccording to the laws of elastic collision

* class of client
*/

import lib.*;

public class BouncingBalls{

    public static void main(String[] args){

        // * parameters setting
        // int n = Integer.parseInt(args[0]);
        int n = 100;
        double radius = 0.01;

        Ball[] balls = new Ball[n];

        for (int i = 0; i < n ; i++){
            // * parameters setting
            double vx = StdRandom.uniform(-0.05, 0.05);
            double vy = StdRandom.uniform(-0.05, 0.05);
            double rx = StdRandom.uniform(0, 1.0);
            double ry = StdRandom.uniform(0, 1.0);
            balls[i] = new Ball(rx,ry, vx, vy, radius);
        }

        while (true){
            StdDraw.clear();
            for(int i = 0; i< n; i++){
                balls[i].move(0.5);
                balls[i].draw();
            }
            StdDraw.show(50);
        }
    }
}