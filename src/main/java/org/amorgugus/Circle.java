
package org.amorgugus;

import org.amorgugus.Utils.MathUtils;

import java.awt.*;

public class Circle implements Drawable {
    Point center;
    double radius;
    Color color;


    public Circle(Point center, double radius, Color color) {
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    public  Point getCenter() {
        return this.center;
    }
    
    public double getRadius() {
        return this.radius;
    }

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

    @Override
    public boolean doesIntersect(Line l) {
        Point closestPoint = getIntersect(l);

        if (closestPoint == null) {
            return false;
        }

        double dx = Math.abs(closestPoint.getX()-this.center.getX());
        double dy = Math.abs(closestPoint.getY()-this.center.getY());
        double r = this.radius;
        // Check if the point is in the circle
        return dx * dx + dy * dy <= r * r;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        double topLeftX = this.center.getX() - radius;
        double topLeftY = this.center.getY() - radius;

        g.drawOval((int) topLeftX, (int) topLeftY, (int) (this.radius * 2), (int) (this.radius * 2));
    }

    @Override
    public Color getColor() {
        return this.color;
    }

    @Override
    public double getHeightMultiplier() {
        return 1;
    }
}
