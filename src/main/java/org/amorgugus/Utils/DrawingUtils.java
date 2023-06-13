package org.amorgugus.Utils;
import org.amorgugus.*;
import org.amorgugus.Point;
import org.amorgugus.UW.DrawingPanel;
import org.amorgugus.Consts;

import java.awt.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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

    public static void drawFloorAndCieling(DrawingPanel panel, Graphics g, int middle) {
        g.setColor(new Color(80, 158, 204));
        g.fillRect(0,0, panel.getWidth(), middle);
        g.setColor(new Color(45, 122, 39));
        g.fillRect(0, middle, panel.getWidth(), panel.getHeight());
    }

    public static void drawWalls(DrawingPanel panel, Graphics g, Player character, Wall[] walls, double degreesPerPixel, double viewAngleOffset) {
        for (int pixel = 0; pixel < panel.getWidth(); pixel++) {
            double viewAngle = degreesPerPixel * pixel;

            Line playerLine = character.getLine(viewAngle+ viewAngleOffset);

            List<Point> intersections = new ArrayList<>();
            Dictionary<Point, Wall> pointWallDictionary = new Hashtable<>();
            MathUtils.getIntersections(walls, playerLine, intersections, pointWallDictionary);

            // We only need to draw a wall if there is an intersection
            if (intersections.size() > 0) {
                intersections.sort((o1, o2) -> (int) (o1.distance(character.getPoint()) - o2.distance(character.getPoint())));

                Point closestIntersect = intersections.get(0);
                Wall closestWall = pointWallDictionary.get(closestIntersect);


                double characterAngle = character.getAngle();
                Point characterPoint = character.getPoint();
                Point shiftedPlayerpos = new Point(characterPoint.getX()+5,characterPoint.getY()+5);
                Point p1 = MathUtils.pointAlongAngle(characterAngle+90, Consts.PLAYER_MAX_VIEW_DISTANCE, shiftedPlayerpos);
                Point p2 = MathUtils.pointAlongAngle(characterAngle-90, Consts.PLAYER_MAX_VIEW_DISTANCE, shiftedPlayerpos);
                if (Consts.DEBUG_RENDERING) {
                    Line playerLineForPerpIntersection = new Line(p1, p2);
                    g.setColor(Color.RED);
                    playerLineForPerpIntersection.draw(g);
                }


                // I have no idea how this works
                // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line#Line_defined_by_two_points
                double perpDistance = Math.abs((p2.getX()-p1.getX())*(p1.getY()-closestIntersect.getY())-(p1.getX()-closestIntersect.getX())*(p2.getY()-p1.getY()))/Math.sqrt(Math.pow((p2.getX()-p1.getX()),2)+Math.pow((p2.getY()-p1.getY()),2));

                double height = (closestWall.getHeightMultiplier() * Consts.BASE_WALL_HEIGHT) / perpDistance;


                int midPoint = (panel.getHeight()*3/4)/2;

                Color wallColor = closestWall.getColor();

                // WALL CORNER SCALE FACTOR CODE
                double wallLength = closestWall.getP1().distance(closestWall.getP2());
                double intersectionToP1 = closestWall.getP1().distance(closestIntersect);

                double a = (-Consts.WALL_CORNER_PARABOLA_HEIGHT)/Math.pow((wallLength/2),2);

                double wallCornerScaleFactor = a*Math.pow((10-intersectionToP1-wallLength/2),2)+Consts.WALL_CORNER_PARABOLA_HEIGHT;
                wallCornerScaleFactor = MathUtils.lerp(0,Consts.WALL_CORNER_PARABOLA_HEIGHT,wallCornerScaleFactor/Consts.WALL_CORNER_PARABOLA_HEIGHT)/Consts.WALL_CORNER_PARABOLA_HEIGHT;
//                System.out.println(wallCornerScaleFactor);




                double colorScaleFactor = (1-( MathUtils.lerp(0, Consts.PLAYER_MAX_VIEW_DISTANCE, perpDistance/Consts.PLAYER_MAX_VIEW_DISTANCE)/Consts.PLAYER_MAX_VIEW_DISTANCE));
                Color displayColor = new Color((int) (wallColor.getRed() * colorScaleFactor), (int) (wallColor.getGreen() * colorScaleFactor), (int) (wallColor.getBlue()*colorScaleFactor));
                g.setColor(displayColor);
                g.drawLine(pixel, (int) ( midPoint+height/2), pixel, (int) ( midPoint-height/2));
            }

        }
    }
}
