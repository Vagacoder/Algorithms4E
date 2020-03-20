package javasrc.ch02_4;

/*
* Example from lecture slides
* Simulate the motion of n moving particles that behaveaccording to the laws of elastic collision

* class of ball
*/

import lib.*;

public class Ball{

    // position
    private double rx, ry;
    // velocity
    private double vx, vy;
    private final double radius;
    
    public Ball(double rx, double ry, double vx, double vy, double radius){
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
    }

    public void move(double dt){
        if ((rx + vx*dt < radius)||(rx + vx*dt > 1.0 - radius)){
            vx = -vx;
        }
        if ((ry + vy*dt < radius)||(ry + vy*dt > 1.0 - radius)){
            vy = -vy;
        }
        rx += vx*dt;
        ry += vy*dt;
    }

    public void draw(){
        StdDraw.filledCircle(rx, ry, radius);
    }

    public static void main(String[] args){
    
    }
}