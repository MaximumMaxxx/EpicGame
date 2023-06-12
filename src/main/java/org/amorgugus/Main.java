package org.amorgugus;

import org.amorgugus.UW.DrawingPanel;
import org.amorgugus.UW.PanelInput;
import org.amorgugus.Utils.DrawingUtils;
import org.amorgugus.Utils.MathUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws AWTException {

        DrawingPanel panel = new DrawingPanel(1280, 720);
        PanelInput input = new PanelInput(panel);
        Graphics gPanel = panel.getGraphics();
        BufferedImage offscreen = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = offscreen.getGraphics();
        Robot robot = new Robot();


        // Init the initial wall height based on the panel size
        Consts.BASE_WALL_HEIGHT = 20 * panel.getHeight();

        boolean running = true;

        Player character = new Player((double) panel.getWidth() / 2 - 5, (double) panel.getHeight() / 2 - 5, 0, g);

        Wall[] walls = new Wall[] {
                new Wall(new Point(66,58), new Point(228,19), 1, Color.orange),
                new Wall(new Point(228,19), new Point(182,97), 1, Color.orange),
                new Wall(new Point(182,97), new Point(42,90), 1, Color.orange),
                new Wall(new Point(42,90), new Point(66,58), 1, Color.orange),
        };


        int mousex;
        int mousey;


        HUD hud = new HUD(g, panel);

        panel.sleep(5000);

        while (running) {
            // LEAVE THIS HERE {
            DrawingUtils.screenFlip(panel, g);
            // }



            // remember to move code into player when done
            mousex = input.getMouseLoc().x;
            double angle = character.getAngle();
            int zeroPos = panel.getWidth()/2;
            character.setAngle(angle + (mousex - zeroPos+1) * 0.5);
            robot.mouseMove(zeroPos, 1000);
            //remember to move

            double degreesPerPixel = Consts.FOV/panel.getWidth();
            double viewAngleOffset =  character.getAngle() - Consts.FOV/2;

            g.setColor(Color.RED);
            if(Consts.DEBUG_RENDERING) {
                character.render();
                for (Wall wall :
                        walls) {
                    wall.draw(g);
                }

                Line viewcone = character.getLine(viewAngleOffset);
                viewcone.draw(g);
            }

            int middle = hud.getPlayerViewAbleArea()/2;

            DrawingUtils.drawFloorAndCieling(panel, g, middle);

            for (int pixel = 0; pixel < panel.getWidth(); pixel++) {
                double viewAngle = degreesPerPixel * pixel;

                Line playerLine = character.getLine(viewAngle+viewAngleOffset);

                List<Point> intersections = new ArrayList<>();
                Dictionary<Point, Wall> pointWallDictionary = new Hashtable<>();
                MathUtils.getIntersections(walls, playerLine, intersections, pointWallDictionary);

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

                    double height = Consts.BASE_WALL_HEIGHT / perpDistance;


                    int midPoint = (panel.getHeight()*3/4)/2;

//                    double d1 = closestWall.getP1().distance(closestIntersect);
//                    double d2 = closestWall.getP2().distance(closestIntersect);




//                    double wallLength = closestWall.getP1().distance(closestWall.getP2());
                    Color wallColor = closestWall.getColor();
//                    double wallCornerScaleFactor = MathUtils.clamp(0,1, -Math.abs(Math.min(d1,d2)-wallLength/2)+wallLength/2*.9);
                    double colorScaleFactor = (1-( MathUtils.lerp(0, Consts.PLAYER_MAX_VIEW_DISTANCE, perpDistance/Consts.PLAYER_MAX_VIEW_DISTANCE)/Consts.PLAYER_MAX_VIEW_DISTANCE));
                    Color displayColor = new Color((int) (wallColor.getRed() * colorScaleFactor), (int) (wallColor.getGreen() * colorScaleFactor), (int) (wallColor.getBlue()*colorScaleFactor));
                    g.setColor(displayColor);
                    g.drawLine(pixel, (int) ( midPoint+height/2), pixel, (int) ( midPoint-height/2));
                }

            }
            if (Consts.DEBUG_RENDERING) {
                Line viewcone = character.getLine(degreesPerPixel * panel.getWidth() + viewAngleOffset);
                viewcone.draw(g);
                g.setColor(Color.RED);
            }





            String[] hudVars = new String[] {
                    "Player Position " + character.getPoint(),
                    "Wall height: " + Consts.BASE_WALL_HEIGHT,
            };

            hud.render(hudVars, character.getHealth());
            g.setColor(Color.green);


            if (input.keyDown('w')) {
                character.move(1,0);
            }
            if (input.keyDown('s')) {
                character.move(-1,0);
            }
            if (input.keyDown('a')) {
                character.move(0,-1);
            }
            if (input.keyDown('d')){
                character.move(0,1);
            }



            gPanel.drawImage(offscreen,0,0,null);
            panel.sleep(1000/Consts.FRAMERATE);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
