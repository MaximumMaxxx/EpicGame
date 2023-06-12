package org.amorgugus;

import org.amorgugus.UW.DrawingPanel;
import org.amorgugus.UW.PanelInput;
import org.amorgugus.Utils.DrawingUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

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

            int middle = hud.getPlayerViewAbleArea()/2;

            DrawingUtils.drawFloorAndCieling(panel, g, middle);

            DrawingUtils.drawWalls(panel, g, character, walls, degreesPerPixel, viewAngleOffset);
            if (Consts.DEBUG_RENDERING) {
                character.render();
                for (Wall wall :
                        walls) {
                    wall.draw(g);
                }

                Line viewConeLine = character.getLine(viewAngleOffset);
                viewConeLine.draw(g);
                viewConeLine = character.getLine(degreesPerPixel * panel.getWidth() + viewAngleOffset);
                viewConeLine.draw(g);
                g.setColor(Color.RED);
            }





            String[] hudVars = new String[] {
                    "Player Position " + character.getPoint(),
                    "Wall height: " + Consts.BASE_WALL_HEIGHT,
            };

            hud.render(hudVars, character.getHealth());
            g.setColor(Color.green);


            if (input.keyDown('w')) {
                character.move(1,0, walls);
            }
            if (input.keyDown('s')) {
                character.move(-1,0, walls);
            }
            if (input.keyDown('a')) {
                character.move(0,-1, walls);
            }
            if (input.keyDown('d')){
                character.move(0,1, walls);
            }



            gPanel.drawImage(offscreen,0,0,null);
            panel.sleep(1000/Consts.FRAMERATE);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
