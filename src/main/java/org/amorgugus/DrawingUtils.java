package org.amorgugus;
import java.awt.*;

public class DrawingUtils {
    /**
     * Draws a line along an angle - by Max
     * @param g A Graphics Object
     * @param angle The angle to draw the line in degrees
     * @param length The length of the line
     * @param location The location of the line
     */
    public static void lineAlongAngle(Graphics g, double angle, double length, Point location) {
        // Trig crap
        int xOffset = (int) (MathUtils.degreeCos(angle)*length);
        int yOffset = (int) (MathUtils.degreeSin(angle)*length);


        g.drawLine((int) location.getx(), (int) location.gety(), (int) (location.getx()+xOffset), (int) (location.gety()+yOffset));
    }

    /**
     * "Flip" the screen by drawing a white rectangle over the whole screen. Sets color to White - by Max
     * @param p The DrawingPanel
     * @param g The Graphics Object
     */
    public static void screenFlip(DrawingPanel p, Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,p.getWidth(),p.getHeight());
    }
}
