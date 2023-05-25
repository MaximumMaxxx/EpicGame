package org.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the MathUtils class
 */
public class MathTests {
    // Due to the imprecision of doubles, we need to allow for a small error, this number is tiny tho so if something is actually broken it will get caught
    double ACCEPTABLE_DOUBLE_ERROR = 0.00000000001;
    @Test
    public void testDegreeSin() {
        assertEquals(0, MathUtils.degreeSin(0), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(1, MathUtils.degreeSin(90), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(0, MathUtils.degreeSin(180), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(-1, MathUtils.degreeSin(270), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(0, MathUtils.degreeSin(360), ACCEPTABLE_DOUBLE_ERROR);
    }

    @Test
    public void testDegreeCos() {
        assertEquals(1, MathUtils.degreeCos(0), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(0, MathUtils.degreeCos(90), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(-1, MathUtils.degreeCos(180), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(0, MathUtils.degreeCos(270), ACCEPTABLE_DOUBLE_ERROR);
        assertEquals(1, MathUtils.degreeCos(360), ACCEPTABLE_DOUBLE_ERROR);
    }

    @Test
    public void testAngleToPoint() {
        assertEquals(0, MathUtils.angleToPoint(0, 0, 1, 0));
        assertEquals(90, MathUtils.angleToPoint(0, 0, 0, 1));
        assertEquals(180, MathUtils.angleToPoint(0, 0, -1, 0));
        assertEquals(270, MathUtils.angleToPoint(0, 0, 0, -1));
        assertEquals(45, MathUtils.angleToPoint(0, 0, 1, 1));
        assertEquals(135, MathUtils.angleToPoint(0, 0, -1, 1));
        assertEquals(225, MathUtils.angleToPoint(0, 0, -1, -1));
        assertEquals(315, MathUtils.angleToPoint(0, 0, 1, -1));
    }
}
