package org.amorgugus;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;

    }
    public double getx(){
        return this.x;
    }
    public double gety(){
        return this.y;
    }

    public double distance(Point otherPoint) {
        double yDiff = otherPoint.gety()-this.y;
        double xDiff = otherPoint.getx()-this.x;

        return Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2));
    }

    public String toString() {
        return "(x: " + this.x + ", y: " + this.y + ")";
    }
}
