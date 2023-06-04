package org.amorgugus;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;

    }
    public double getX(){
        return this.x;
    }
    public double getY(){
        return this.y;
    }

    public void setX(double value) {
        this.x = value;
    }

    public void setY(double value) {
        this.y = value;
    }

    /**
     * Returns the distance between this point and another point using pythagorean theorem
     * @param otherPoint the point you want to see the distance to
     * @return The distance or Double.POSITIVE_INFINITY if Point is null
     */
    public double distance(Point otherPoint) {
        // Prevent any null pointer errors from
        // intersect producing null points
        if (otherPoint == null) {
            return Double.POSITIVE_INFINITY;
        }

        //
        double yDiff = otherPoint.getY()-this.y;
        double xDiff = otherPoint.getX()-this.x;

        return Math.sqrt(Math.pow(xDiff,2) + Math.pow(yDiff,2));
    }

    public String toString() {
        return "(x: " + this.x + ", y: " + this.y + ")";
    }
}
