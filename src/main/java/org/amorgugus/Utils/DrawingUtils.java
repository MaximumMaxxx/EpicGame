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

    /**
     * Draw a Rectangle in the viewable area for ground and sky - by Max
     * @param panel The DrawingPanel
     * @param g The Graphics Object
     * @param middle The middle of the screen in pixels
     */
    public static void drawFloorAndCieling(DrawingPanel panel, Graphics g, int middle, int viewableArea) {
        g.setColor(new Color(80, 158, 204));
        g.fillRect(0, 0, panel.getWidth(), middle);
        g.setColor(new Color(45, 122, 39));
        g.fillRect(0, middle, panel.getWidth(), viewableArea-middle);
    }

    /**
     * Draws a floor and cieling that gets darker as it gets further away
     * Still needs in testing - by Max
     * @param panel The DrawingPanel
     * @param g The Graphics Object
     * @param middle The middle of the screen in pixels
     */
    public static void moodyFloorAndCieling(DrawingPanel panel, Graphics g, int middle) {
        for (int i = 0; i < middle; i++) {
            double percent = 1 - (double) Math.pow(i,2) / (double) Math.pow(middle,2);
            g.setColor(new Color((int) (80 * percent), (int) (158 * percent), (int) (204 * percent)));
            g.fillRect(0, i, panel.getWidth(), i);
        }

        for (int i = 0; i < middle; i++) {
            double percent = (double) Math.pow(i,2) / (double) Math.pow(middle,2);
            g.setColor(new Color((int) (45 * percent), (int) (122 * percent), (int) (39 * percent)));
            g.fillRect(0, i + middle, panel.getWidth(), i + middle);
        }
    }

    /**
     * Draw the walls - by Max
     * @param panel The DrawingPanel
     * @param g The Graphics Object
     * @param character The Player
     * @param walls An array of walls to render
     * @param midPoint The middle of the screen to draw from
     */
    public static void drawWalls(DrawingPanel panel, Graphics g, Player character, Drawable[] walls, double midPoint) {
        final double degreesPerPixel = Consts.FOV/panel.getWidth();
        final double viewAngleOffset =  character.getAngle() - Consts.FOV/2;

        for (int pixel = 0; pixel < panel.getWidth(); pixel++) {
            double viewAngle = degreesPerPixel * pixel;

            Line playerLine = character.getLine(viewAngle + viewAngleOffset);

            List<Point> intersections = new ArrayList<>();
            Dictionary<Point, Drawable> pointWallDictionary = new Hashtable<>();
            MathUtils.getIntersections(walls, playerLine, intersections, pointWallDictionary);

            // We only need to draw a wall if there is an intersection
            if (intersections.size() > 0) {
                intersections.sort((o1, o2) -> (int) (o1.distance(character.getPoint()) - o2.distance(character.getPoint())));

                Point closestIntersect = intersections.get(0);
                Drawable closestWall = pointWallDictionary.get(closestIntersect);


                // Construct a line that is perpendicular to the player's view
                // Consts.PLAYER_MAX_VIEW_DISTANCE is an arbitrary number, but
                // it is the maximum distance that the player can see, so it'll work
                double characterAngle = character.getAngle();
                Point characterPoint = character.getPoint();
                Point p1 = MathUtils.pointAlongAngle(characterAngle+90, Consts.PLAYER_MAX_VIEW_DISTANCE, character.getPoint());
                Point p2 = MathUtils.pointAlongAngle(characterAngle-90, Consts.PLAYER_MAX_VIEW_DISTANCE, character.getPoint());

                if (Consts.DEBUG_RENDERING) {
                    playerLine.draw(g);
                    Line playerLineForPerpIntersection = new Line(p1, p2);
                    g.setColor(Color.RED);
                    playerLineForPerpIntersection.draw(g);
                }


                // I have no idea how this equation works, but it does
                // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line#Line_defined_by_two_points
                double perpDistance = Math.abs((p2.getX()-p1.getX())*(p1.getY()-closestIntersect.getY())-(p1.getX()-closestIntersect.getX())*(p2.getY()-p1.getY()))/Math.sqrt(Math.pow((p2.getX()-p1.getX()),2)+Math.pow((p2.getY()-p1.getY()),2));

                // The theory behind this is that the height of the wall is proportional to the distance from the player
                // You just have to use perpDistance instead of distance because otherwise you get a fisheye effect
                // PerpDistance is the distance from the player to the wall, but it's perpendicular to the player's view instead of emanating from the player point
                double height = (closestWall.getHeightMultiplier() * Consts.BASE_WALL_HEIGHT) / perpDistance;



                Color wallColor = closestWall.getColor();

                double ambientOcculsionFactor;
                if (closestWall.getClass() == Wall.class) {
                    ambientOcculsionFactor = getAmbientOcclusionFactor(closestIntersect, (Wall) closestWall);
                } else {
                    ambientOcculsionFactor = 1;
                }

                // This line is massive, but it's basically a lerp between the wall color and black based on distance and ambient occlusion
                double colorScaleFactor = (1-( MathUtils.lerp(0, Consts.PLAYER_MAX_VIEW_DISTANCE, perpDistance/Consts.PLAYER_MAX_VIEW_DISTANCE)/Consts.PLAYER_MAX_VIEW_DISTANCE)) * ambientOcculsionFactor;

                Color displayColor = new Color((int) (wallColor.getRed() * colorScaleFactor), (int) (wallColor.getGreen() * colorScaleFactor), (int) (wallColor.getBlue()*colorScaleFactor));
                g.setColor(displayColor);
                g.drawLine(pixel, (int) ( midPoint+height/2), pixel, (int) ( midPoint-height/2));
            }

        }
    }


    /**
     * Gets the ambient occlusion factor for a wall
     * @param closestIntersect The closest intersection to the player
     * @param closestWall The closest wall to the player
     * @return The ambient occlusion factor, double between 0 and 1
     */
    private static double getAmbientOcclusionFactor(Point closestIntersect, Wall closestWall) {
        // Some surprisingly ok ambient occlusion
        // Basically defines an upside down parabola with the vertex at the middle of the wall
        // The parabola has an absurd height, and the ambient occlusion factor is the y value of the parabola at the intersection point
        // Then the value is lerped between 0 and the height of the parabola
        // This is then divided by the height of the parabola to get a value between 0 and 1
        // It works better than I would expect but is not as good as proper ssao or raytracing
        // - Max

        double wallLength = closestWall.getP1().distance(closestWall.getP2());
        double intersectionToP1 = closestWall.getP1().distance(closestIntersect);

        // y = a(x-h)^2+k
        double scaledParabolaHeight = Consts.WALL_CORNER_PARABOLA_HEIGHT*wallLength;
//        double a = (Consts.WALL_CORNER_PARABOLA_HEIGHT/2-scaledParabolaHeight)/Math.pow((wallLength/2),2);
        double a = -scaledParabolaHeight / Math.pow(wallLength, 2); // GPT-4 gave me this equation, it's not strictly better but I like the rendered look more

        double ambientOcclusionFactor = a*Math.pow((intersectionToP1-wallLength/2),2)+scaledParabolaHeight;
        ambientOcclusionFactor = MathUtils.lerp(0,scaledParabolaHeight,ambientOcclusionFactor/scaledParabolaHeight)/scaledParabolaHeight;
        return ambientOcclusionFactor;
    }
}
