
package org.amorgugus;

import org.amorgugus.Utils.MathUtils;

import java.awt.*;

public class Circle implements Drawable {
    Point center;
    double radius;
    Color color;


    /**
     * Construct a circle from a center point, radius, and color
     * @param center The center point of the circle
     * @param radius The radius of the circle
     * @param color The color of the circle
     */
    public Circle(Point center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    /**
     * Get the center of the circle
     * @return The center of the circle
     */
    public  Point getCenter() {
        return this.center;
    }

    /**
     * Get the radius of the circle
     * @return The radius of the circle
     */
    public double getRadius() {
        return this.radius;
    }

    /**
     * Gets the closest point of intersection between the circle and the line (to the line's p1)
     * @param l The line to check
     * @return The closest point of intersection between the circle and the line
     */
    @Override
    public Point getIntersect(Line l) {
        Point[] points = MathUtils.pointsOfCircleIntersect(l,this);

        assert points != null;
        if (points.length == 0) {
            return null;
        }
        if (points.length == 1) {
            return points[0];
        }
        double closestDistance = Integer.MAX_VALUE;
        Point closetPoint = new Point(0,0);
        for (Point point : points) {
            double newDistance = l.getP1().distance(point);
            if (newDistance < closestDistance) {
                closestDistance = newDistance;
                closetPoint = point;
            }
        }

        return closetPoint;
    }

    /**
     * Check if the circle intersects the line
     * @param l The line to check
     * @return If the circle intersects the line
     */
    @Override
    public boolean doesIntersect(Line l) {
        Point closestPoint = getIntersect(l);

        if (closestPoint == null) {
            return false;
        }

        double dx = Math.abs(closestPoint.getX()-this.center.getX());
        double dy = Math.abs(closestPoint.getY()-this.center.getY());
        double r = this.radius;
        boolean ret = true;
        // Ret is kinda weird, it leads to double precision issues if it's on but it's probably not good to have it bypassed
//        boolean ret = (dx * dx + dy * dy <= r * r);
        // Check if the point is in the circle
        return ret && l.isPointWithinBounds(closestPoint);
    }

    /**
     * Draws the circle to the screen
     * @param g The graphics object to draw with
     */
    public void draw(Graphics g) {
        g.setColor(this.color);
        double topLeftX = this.center.getX() - radius;
        double topLeftY = this.center.getY() - radius;

        g.drawOval((int) topLeftX, (int) topLeftY, (int) (this.radius * 2), (int) (this.radius * 2));
    }

    /**
     * Gets the color of the circle
     * @return The color of the circle
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Gets the height multiplier of the circle
     * @return The height multiplier of the circle
     */
    @Override
    public double getHeightMultiplier() {
        return 1;
    }
}
