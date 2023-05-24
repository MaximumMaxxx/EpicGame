package org.example;
import java.awt.*;

public class DrawingUtils {
    public static void lineAlongAngle(Graphics g, double angle, double length, Point location) {
        // Trig crap
        int xOffset = (int) (MathUtils.degreeCos(angle)*length);
        int yOffset = (int) (MathUtils.degreeSin(angle)*length);


        g.setColor(Color.black);
        g.drawLine(location.x, location.y, location.x+xOffset, location.y+yOffset);
    }
}
