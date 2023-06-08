package org.amorgugus;

// Line, Linus, Linus tech tip
// Max

import org.amorgugus.Utils.MathUtils;

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

    public Line(Point p1, double angle, double length) {
        this.p1 = p1;
        this.p2 = MathUtils.pointAlongAngle(angle, length, p1);
        this.slope = calculateSlope();
        this.intercept=calculateIntercept();
    }

    public Line(double slope, double yIntercept, double length) {
        this.slope = slope;
        this.intercept = yIntercept;
        this.p1 = new Point(-length,(slope*-length+yIntercept));
        this.p2 = new Point(length,(slope*length+yIntercept));
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }


    private double calculateIntercept() {
        // b = y1 -m * x1

        return this.p1.getY() - this.slope * this.p1.getX();
    }

    /**
     * Calculates the slope of the currents points p1 and p2
     * @return The slope from p1 to p2
     */
    public double calculateSlope() {
        // m=(y2-y1)/(x2-x1)
        return (p2.getY()-p1.getY())/(p2.getX()-p1.getX());
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
                MathUtils.isBetween(intersection.getX(), this.p1.getX(), this.p2.getX()) &&
                MathUtils.isBetween(intersection.getY(), this.p1.getY(), this.p2.getY()) &&
                MathUtils.isBetween(intersection.getX(), otherLine.getP1().getX(), otherLine.getP2().getX()) &&
                MathUtils.isBetween(intersection.getY(), otherLine.getP1().getY(), otherLine.getP2().getY())
        ) {
            return true;
        }

        return false;
    }


    /**
     * Draw yourself to the screen
     */
    public void draw(Graphics g) {
        g.drawLine((int) this.p1.getX(), (int) this.p1.getY(), (int) this.p2.getX(), (int) this.p2.getY());
    }

    @Override
    public String toString() {
        return "(" + this.p1.getX() + ", " + this.p1.getY() + ") <-> ("+this.p2.getX() + ", " + this.p2.getY() + ")";
    }
}
