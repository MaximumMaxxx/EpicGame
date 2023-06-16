package org.amorgugus;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class CircleTests {
    double ACCEPTABLE_DOUBLE_ERROR = 0.00000000001;

    @Test
    public void doesIntersectTest() {
        Circle c = new Circle(new Point(4,6), 4, Color.red);
        Line l = new Line(new Point(0,0), new Point(10,10));

        assertTrue(c.doesIntersect(l));
    }
}
