package org.amorgugus.Utils;

import org.amorgugus.*;

import java.util.Dictionary;
import java.util.List;

public class MathUtils {
    /**
     * @param x1 The x of the first point
     * @param y1 The y of the first point
     * @param x2 the x of the second point
     * @param y2 the y of the second point
     * @return The angle in degrees that would point towards point 2 from point 1
     */
    public static double angleToPoint(double x1, double y1, double x2, double y2) {
        // Thanks Issac for suggestion atan2 instead of normal atan
        double angle = Math.toDegrees(Math.atan2((y2-y1),(x2-x1)));

        // Atan2 sometimes returns negative angles, this makes sure it is always positive, not technically required but makes the numbers look better
        if (angle < 0) {
            angle += 360;
        }
        return angle;
    }


    /**
     *
     * Returns the point that's some distance and angle from a location
     * @param angle The angle in degrees
     * @param length The distance from the location
     * @param location The location to start from
     * @return The point that's some distance and angle from a location
     */
    public static Point pointAlongAngle(double angle, double length, Point location) {
        // Trig crap
        int xOffset = (int) (MathUtils.degreeCos(angle)*length);
        int yOffset = (int) (MathUtils.degreeSin(angle)*length);

        return new Point(location.getX()+xOffset, location.getY()+yOffset);
    }

    public static double degreeSin(double a) {
        return Math.sin(Math.toRadians(a));
    }

    public static double degreeCos(double a) {
        return Math.cos(Math.toRadians(a));
    }

    /**
     * Checks if a number is between two other numbers even if the two other numbers are in the wrong order
     * @param a The number you're checking
     * @param b Bound 1
     * @param c Bound 2
     * @return True if `a` is between the bounds
     */
    public static boolean isBetween(double a, double b, double c) {
        return (
                (c>=a && b<=a) ||
                (c<=a && b>=a)
                );
    }

    /**
     * Linearly interpolates between two points
     * @param bound1 The first bound
     * @param bound2 The second bound
     * @param percentage A value between 0 and 1 which represents how far between the bounds you are
     * @return the lerped result
     */
    public static double lerp(double bound1, double bound2, double percentage) {
        return (bound2 - bound1) * percentage;
    }

    /**
     * Gets a list of all the intersections between a line and a list of walls and puts them in a dictionary with the wall they intersect
     * @param walls The walls to check
     * @param playerLine The view line to check
     * @param intersections The list to add the intersections to
     * @param pointWallDictionary The dictionary to add the intersections to
     */
    public static void getIntersections(Drawable[] walls, Line playerLine, List<Point> intersections, Dictionary<Point, Drawable> pointWallDictionary) {
        for (Drawable wall :
                walls) {
            if (wall.doesIntersect(playerLine)) {
                Point intersect = wall.getIntersect(playerLine);
                intersections.add(intersect);
                pointWallDictionary.put(intersect, wall);
            }
        }
    }

    /**
     * Checks if a line intersects any of the walls, returns the point of intersection or null if there is none
     * @param walls The walls to check
     * @param l The line to check
     * @return The point of intersection or null if there is none
     */
    public static Point doesIntersectWall(Drawable[] walls, Line l) {
        for (Drawable wall :
                walls) {
            if (wall.doesIntersect(l)){
                return wall.getIntersect(l);
            }
        }
        return null;
    }

    public static Point[] pointsOfCircleIntersect(Line line, Circle circle) {
        // God bless this man https://www.desmos.com/calculator/lxaq38n0et
        // Actually pretty much saved this endevour
        double b = line.getP1().getY() - line.calculateSlope()*(line.getP1().getX()-circle.getCenter().getX()) - circle.getCenter().getY();


        double A = Math.pow(line.calculateSlope(),2)+1;
        double B = 2 * line.calculateSlope() * b;
        double C = (Math.pow(b,2)-Math.pow(circle.getRadius(),2));

        double[] roots = findRealRoots(A,B,C);

        if (roots.length == 0) {
            return new Point[] {};
        } if (roots.length == 1) {
            return new Point[] {
                new Point(roots[0] + circle.getCenter().getX(), line.calculateSlope()*roots[0]+line.calculateIntercept() + circle.getCenter().getY())
            };
        } if (roots.length == 2) {
            return new Point[] {
                new Point(roots[0] + circle.getCenter().getX(), line.calculateSlope()*roots[0]+ b + circle.getCenter().getY()),
                new Point(roots[1] + circle.getCenter().getX(), line.calculateSlope()*roots[1]+ b + circle.getCenter().getY())
            };
        }
        // If we get here something went very wrong
        return null;
    }

    /**
     * Finds the real roots of a quadratic equation
     * @param a The a value
     * @param b The b value
     * @param c The c value
     * @return An array of the real roots, 0-2 elements
     */
    public static double[] findRealRoots(double a, double b, double c) {
        // Chatgpt ahh code
        // I could have written this myself but that would be dumb - Max
        double discriminant = b * b - 4 * a * c;

        if (discriminant < 0) {
            // No real roots
            return new double[0];
        } else if (discriminant == 0) {
            // One real root
            double root = -b / (2 * a);
            return new double[] { root };
        } else {
            // Two real roots
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return new double[] { root1, root2 };
        }
    }
}
