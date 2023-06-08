package org.amorgugus;

// Max

import java.awt.*;

public class Wall extends Line {
    private double height;
    private Color color;
    public Wall(Point p1, Point p2, double height, Color color) {
        super(p1,p2);
        this.height = height;
        this.color = color;
    }

    public double getPixelHeight() {
        return this.height * Consts.BASE_WALL_HEIGHT;
    }

    public double getHeightMultiplier() {
        return this.height;
    }

    public Color getColor() {
        return this.color;
    }

}
