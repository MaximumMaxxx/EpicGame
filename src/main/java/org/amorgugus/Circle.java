
package org.amorgugus;

import java.awt.*;

public class Circle {
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

    public void draw(Graphics g) {
        g.setColor(this.color);
        double topLeftX = this.center.getX() - radius;
        double topLeftY = this.center.getY() - radius;

        g.drawOval((int) topLeftX, (int) topLeftY, (int) (this.radius * 2), (int) (this.radius * 2));
    }
}
