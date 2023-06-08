package org.amorgugus;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * This file handles all the tests for the Line class
 * @see org.amorgugus.Line
 */

public class LineTest {
    double ACCEPTABLE_DOUBLE_ERROR = 0.00000000001;
    @Test
    public void getIntersectionTest() {
        Line l1 = new Line(new Point(-1,1), new Point(1,-1));
        Line l2 = new Line(new Point(-1,-1), new Point(1,1));

        Point intersect = l1.getIntersect(l2);

        assertEquals(0.0, intersect.getX(), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(0.0, intersect.getY(), ACCEPTABLE_DOUBLE_ERROR);
    }

    @Test
    public void doesIntersectionTest() {
        Line l1 = new Line(new Point(-1,1), new Point(1,-1));
        Line l2 = new Line(new Point(-1,-1), new Point(1,1));

        assertTrue(l1.doesIntersect(l2));

    }

    @Test
    public void pointSlopeFormTest() {
        Line line = new Line(1,10, 10);
        assertEquals(-10, line.getP1().getX());
        assertEquals(0, line.getP1().getY());
    }
}