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

    /**
     * Construct a line from a point, angle, and length
     * @param p1 The first point of the line
     * @param angle The angle of the line in degrees where 0 is horizontal right and 90 is vertical
     * @param length The length of the line
     */
    public Line(Point p1, double angle, double length) {
        this.p1 = p1;
        this.p2 = MathUtils.pointAlongAngle(angle, length, p1);
        this.slope = calculateSlope();
        this.intercept=calculateIntercept();
    }

    /**
     * Construct a line from a slope, y intercept, and length
     * @param slope The slope of the line
     * @param yIntercept The y intercept of the line
     * @param length The length of the line
     */
    public Line(double slope, double yIntercept, double length) {
        this.slope = slope;
        this.intercept = yIntercept;
        this.p1 = new Point(-length,(slope*-length+yIntercept));
        this.p2 = new Point(length,(slope*length+yIntercept));
    }

    /**
     * Get the first point of the line
     * @return The first point of the line
     */
    public Point getP1() {
        return p1;
    }

    /**
     * Get the second point of the line
     * @return The second point of the line
     */
    public Point getP2() {
        return p2;
    }


    /**
     * Calculates the y intercept of the current line
     * @return The y intercept of the current line
     */
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
     * Get the intersection point of the current line and another line.
     * Does not do bounds.
     * @param otherLine The line you want to check intersection with
     */
    public Point getIntersect(Line otherLine) {
        if (this.slope == otherLine.slope) {
            return null;
        }

        double x;
        double y;
        final double largeNumber = Integer.MAX_VALUE;
        final double smallNumber = 0.0001;
        if (Math.abs(this.slope) > largeNumber){
            x = this.getP1().getX();
            y = otherLine.slope * this.getP1().getX() + otherLine.intercept;
        }
        else if (Math.abs(otherLine.slope) > largeNumber) {
            x = otherLine.getP1().getX();
            y = this.slope * otherLine.getP1().getX() + this.intercept;
//        } else if (Math.abs(otherLine.slope) < smallNumber) {
//            y = otherLine.intercept;
//            x = (y+this.intercept)/this.slope;
//            System.out.println("Using third formula");
//        } else if (Math.abs(this.slope) < smallNumber) {
//            y = this.intercept;
//            x = (y+otherLine.intercept)/otherLine.slope;
//            System.out.println("Using fourth formula");
        } else {
            // https://en.wikipedia.org/wiki/Line%E2%80%93line_intersection#Given_two_points_on_each_line_segment
            x = (otherLine.intercept - this.intercept) / (this.slope - otherLine.slope);
            y = this.slope * x + this.intercept;
        }



        return new Point(x, y);
    }

    public boolean doesIntersect(Line otherLine) {
        Point intersection = getIntersect(otherLine);

        if (intersection == null) {
            // If the line is parallel then it is null and does not intersect.
            return false;
        }

        // The point only intersects if it's within the bounds of both lines
        // Need to check both ways otherwise you get some goofy fun issues
        return (
                        this.isPointWithinBounds(intersection) &&
                        otherLine.isPointWithinBounds(intersection)
                );
    }


    /**
     * Checks whether some Point p is within the "bounding box"
     * or rectangle that could be formed from the two points that make up the line
     * @param p The point used for checking if it's within the bounds
     * @return true if the bounds contain the point
     */
    public boolean isPointWithinBounds(Point p) {
        // Parallel to the x axis
        if (Math.abs(this.slope) < 0.0001) {
            return (
                    MathUtils.isBetween(p.getX(), this.p1.getX(), this.p2.getX())
            );
        }

        // Parallel to the y axis
        if (Math.abs(this.slope) > Integer.MAX_VALUE) {
            return (
                    MathUtils.isBetween(p.getY(), this.p1.getY(), this.p2.getY())
            );
        }

        return (
                MathUtils.isBetween(p.getX(), this.p1.getX(), this.p2.getX()) &&
                MathUtils.isBetween(p.getY(), this.p1.getY(), this.p2.getY())
        );

    }

    /**
     * Draw yourself to the screen
     */
    public void draw(Graphics g) {
        g.drawLine((int) this.p1.getX(), (int) this.p1.getY(), (int) this.p2.getX(), (int) this.p2.getY());
    }

    /**
     * Pretty print the line
     */
    @Override
    public String toString() {
        return "(" + this.p1.getX() + ", " + this.p1.getY() + ") <-> ("+this.p2.getX() + ", " + this.p2.getY() + ")";
    }
}
