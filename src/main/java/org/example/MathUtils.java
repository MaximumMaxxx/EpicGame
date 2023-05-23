package org.example;

public class MathUtils {
    /**
     * @param x1 The x of the first point
     * @param y1 The y of the first point
     * @param x2 the x of the second point
     * @param y2 the y of the second point
     * @return The angle in degrees that would point towards point 2 from point 1
     */
    public static double angleToPoint(double x1, double y1, double x2, double y2) {
        return Math.atan((x1-x2)/(y1-y2));
    }
}
