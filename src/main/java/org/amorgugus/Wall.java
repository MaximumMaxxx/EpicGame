package org.amorgugus;

// Max

import java.awt.*;

public class Wall extends Line {
    private final double height;
    private final Color color;
    public Wall(Point p1, Point p2, double height, Color color) {
        super(p1,p2);
        this.height = height;
        this.color = color;
    }

    /**
     * Get the height of the wall in pixels
     * @return The height of the wall in pixels
     */
    public double getPixelHeight() {
        return this.height * Consts.BASE_WALL_HEIGHT;
    }

    /**
     * Get the height of the wall in units of the base wall heights
     * @return The height of the wall in units
     */
    public double getHeightMultiplier() {
        return this.height;
    }

    /**
     * Get the color of the wall
     * @return The color of the wall
     */
    public Color getColor() {
        return this.color;
    }

}
