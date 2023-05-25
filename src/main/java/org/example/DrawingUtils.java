package org.example;
import java.awt.*;

public class DrawingUtils {
    public static void lineAlongAngle(Graphics g, double angle, double length, Point location) {
        // Trig crap
        int xOffset = (int) (MathUtils.degreeCos(angle)*length);
        int yOffset = (int) (MathUtils.degreeSin(angle)*length);


        g.drawLine((int) location.getx(), (int) location.gety(), (int) (location.getx()+xOffset), (int) (location.gety()+yOffset));
    }
}
