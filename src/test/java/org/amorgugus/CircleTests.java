package org.amorgugus;

import org.amorgugus.Utils.MathUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.Arrays;

public class CircleTests {
    double ACCEPTABLE_DOUBLE_ERROR = 0.00000000001;

    @Test
    public void doesIntersectTest() {
        Circle c = new Circle(new Point(4,6), 4, Color.red);
        Line l = new Line(new Point(0,0), new Point(10,10));

        System.out.println(Arrays.toString(MathUtils.pointsOfCircleIntersect(l,c)));
        assertTrue(c.doesIntersect(l));

        l = new Line(new Point(0,0), new Point(10,0));
        assertFalse(c.doesIntersect(l));
    }
}
