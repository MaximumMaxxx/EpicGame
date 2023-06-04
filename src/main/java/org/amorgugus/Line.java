package org.amorgugus;

// Line, Linus, Linus tech tip

import java.awt.*;

public class Line {
    private Point p1;
    private Point p2;
    private double slope;
    private double intercept;

    /**
     * Construct a Line from 2 points
     * @param p1 The first point of the line
     * @param p2 The second point of the line
     */
    public Line(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.slope = calculateSlope();
        this.intercept = calculateIntercept();
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }


    private double calculateIntercept() {
        // b = y1 -m * x1

        return this.p1.gety() - this.slope * this.p1.getx();
    }

    /**
     * Calculates the slope of the currents points p1 and p2
     * @return The slope from p1 to p2
     */
    public double calculateSlope() {
        // m=(y2-y1)/(x2-x1)
        return (p2.gety()-p1.gety())/(p2.getx()-p1.getx());
    }

    public Line(Point p1, double angle, double length) {
        this.p1 = p1;
        this.p2 = MathUtils.pointAlongAngle(angle, length, p1);
        this.slope = calculateSlope();
        this.intercept=calculateIntercept();
    }

    /**
     * Checks if this line intersects with another line
     * @param otherLine The line you want to check intersection with
     */
    public Point getIntersect(Line otherLine) {
        if (this.slope == otherLine.slope) {
            return null;
        }

        double x = (otherLine.intercept - this.intercept) / (this.slope - otherLine.slope);
        double y = this.slope * x + this.intercept;

        return new Point(x, y);
    }

    public boolean doesIntersect(Line otherLine) {
        Point intersection = getIntersect(otherLine);

        if (intersection == null) {
            // If the line is parallel then it is null and does not intersect.
            return false;
        }

        if (
            // If the point is not in the range between p1 and p2 in both x and y return false
                MathUtils.isBetween(intersection.getx(), this.p1.getx(), this.p2.getx()) &&
                MathUtils.isBetween(intersection.gety(), this.p1.gety(), this.p2.gety()) &&
                MathUtils.isBetween(intersection.getx(), otherLine.getP1().getx(), otherLine.getP2().getx()) &&
                MathUtils.isBetween(intersection.gety(), otherLine.getP1().gety(), otherLine.getP2().gety())
        ) {
            return true;
        }

        return false;
    }

    /**
     * Draw yourself to the screen
     */
    public void draw(Graphics g) {
        g.drawLine((int) this.p1.getx(), (int) this.p1.gety(), (int) this.p2.getx(), (int) this.p2.gety());
    }
}
