package org.amorgugus.Utils;
import org.amorgugus.Point;
import org.amorgugus.UW.DrawingPanel;
import org.amorgugus.Utils.MathUtils;

import java.awt.*;

public class DrawingUtils {
    /**
     * Draws a line along an angle - by Max
     * @param g A Graphics Object
     * @param angle The angle to draw the line in degrees
     * @param length The length of the line
     * @param location The location of the line
     */
    public static void lineAlongAngle(Graphics g, double angle, double length, org.amorgugus.Point location) {
        // Trig crap
        Point gottenPoint = MathUtils.pointAlongAngle(angle,length,location);

        g.drawLine((int) location.getX(), (int) location.getY(), (int) gottenPoint.getX(), (int) gottenPoint.getY());
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
