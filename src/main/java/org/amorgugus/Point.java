package org.amorgugus;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y){
        this.x = x;
        this.y = y;

    }

    /**
     * Get the x of the point
     * @return The x of the point
     */
    public double getX(){
        return this.x;
    }

    /**
     * Get the y of the point
     * @return The y of the point
     */
    public double getY(){
        return this.y;
    }

    /**
     * Set the x of the point
     * @param value The new x of the point
     */
    public void setX(double value) {
        this.x = value;
    }

    /**
     * Set the y of the point
     * @param value The new y of the point
     */
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

    /**
     * Prints the point in a readable format
     */
    public String toString() {
        return "(x: " + this.x + ", y: " + this.y + ")";
    }
}
