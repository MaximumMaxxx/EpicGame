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
    private static final int FRAMERATE = 30;
    public static void main(String[] args) {

        DrawingPanel panel = new DrawingPanel(1280, 720);
        PanelInput input = new PanelInput(panel);
        Graphics gPanel = panel.getGraphics();
        BufferedImage offscreen = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = offscreen.getGraphics();


        // Init the initial wall height based on the panel size
        Consts.BASE_WALL_HEIGHT = (int)(.8 * panel.getHeight());

        boolean running = true;

        Player character = new Player((double) panel.getWidth() / 2 - 5, (double) panel.getHeight() / 2 - 5, 0, g);

        Wall[] walls = new Wall[] {
                new Wall(new Point(66,58), new Point(228,19), 1, Color.orange),
                new Wall(new Point(66,58), new Point(67,228), 1, Color.lightGray),
        };


        int mousex;
        int mousey;

        double dynamicWallHeight = 10;

        HUD hud = new HUD(g, panel);
        while (running) {
            // LEAVE THIS HERE {
            DrawingUtils.screenFlip(panel, g);
            // }

            mousex = input.getMouseLoc().x;
            mousey = input.getMouseLoc().y;

            character.lookAt(new Point(mousex, mousey));

            character.render();

            g.setColor(Color.orange);
            for (Wall wall :
                    walls) {
                wall.draw(g);
            }

            // TODO: REmove this
            double wallHeight = 0;


            double degreesPerPixel = Consts.FOV/panel.getWidth();
            double viewAngleOffset =  character.getAngle() - Consts.FOV/2;
            Point p1 = new Point(0,0);
            Point p2 = new Point(0,0);

            g.setColor(Color.RED);
            Line viewcone = character.getLine(viewAngleOffset);
            viewcone.draw(g);
            for (int pixel = 0; pixel < panel.getWidth(); pixel++) {
                double viewAngle = degreesPerPixel * pixel;

                Line playerLine = character.getLine(viewAngle+viewAngleOffset);

                List<Point> intersections = new ArrayList<>();
                Dictionary<Point, Wall> pointWallDictionary = new Hashtable<>();
                for (Wall wall :
                        walls) {
                    if (playerLine.doesIntersect(wall)) {
                        Point intersect = playerLine.getIntersect(wall);
                        intersections.add(intersect);
                        pointWallDictionary.put(intersect, wall);
                    }
                }

                if (intersections.size() > 0) {
                    intersections.sort((o1, o2) -> (int) (o1.distance(character.getPoint()) - o2.distance(character.getPoint())));

                    Point closestIntersect = intersections.get(0);
                    Wall closestWall = pointWallDictionary.get(closestIntersect);
                    double dist = closestIntersect.distance(character.getPoint());


                    double characterAngle = character.getAngle();
                    Point characterPoint = character.getPoint();
                    Point shiftedPlayerpos = new Point(characterPoint.getX()+5,characterPoint.getY()+5);
                    p1 = MathUtils.pointAlongAngle(characterAngle+90, Consts.PLAYER_MAX_VIEW_DISTANCE, shiftedPlayerpos);
                    p2 = MathUtils.pointAlongAngle(characterAngle-90, Consts.PLAYER_MAX_VIEW_DISTANCE, shiftedPlayerpos);
                    Line playerLineForPerpIntersection = new Line(p1,p2);
                    g.setColor(Color.RED);
                    playerLineForPerpIntersection.draw(g);


                    Point P1 = playerLineForPerpIntersection.getP1();
                    Point P2 = playerLineForPerpIntersection.getP2();
                    // I have no idea how this works
                    // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line#Line_defined_by_two_points
                    double perpDistance = Math.abs((P2.getX()-P1.getX())*(P1.getY()-closestIntersect.getY())-(P1.getX()-closestIntersect.getX())*(P2.getY()-P1.getY()))/Math.sqrt(Math.pow((P2.getX()-P1.getX()),2)+Math.pow((P2.getY()-P1.getY()),2));

                    double height = dynamicWallHeight / perpDistance;


                    int midPoint = (panel.getHeight()*3/4)/2;

                    if(height<0) {
                        g.setColor(Color.pink);
                    } else{
                        g.setColor(closestWall.getColor());
                    }
                    g.drawLine(pixel, (int) ( midPoint+height/2), pixel, (int) ( midPoint-height/2));
                }

            }
            viewcone = character.getLine(degreesPerPixel * panel.getWidth() + viewAngleOffset);
            g.setColor(Color.RED);
            viewcone.draw(g);




            String[] hudVars = new String[] {
                    "Wall height at last line" + wallHeight,
                    "Player Position " + character.getPoint(),
                    "Wall height: " + dynamicWallHeight,
                    "P1: " + p1,
                    "P2: " + p2,
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
            if (input.keyDown('-')) {
                dynamicWallHeight /= 2;
            }
            if (input.keyDown('=')) {
                dynamicWallHeight *= 2;
            }



            gPanel.drawImage(offscreen,0,0,null);
            panel.sleep(1000/FRAMERATE);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
