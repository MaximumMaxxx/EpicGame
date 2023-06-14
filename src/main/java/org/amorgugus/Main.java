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
        int frameCount = 0;


        // Init the initial wall height based on the panel size
//        Consts.BASE_WALL_HEIGHT = 20 * panel.getHeight();

        boolean running = true;

        Player character = new Player(625, 85, -90, g);

        Wall[] walls = new Wall[] {
                new Wall(new Point(0,1), new Point(0,720), 1, Color.orange),
                new Wall(new Point(0,0), new Point(1280,0), 1, Color.orange),
                new Wall(new Point(0,720), new Point(1280,720), 1, Color.orange),
                new Wall(new Point(1280,1), new Point(1280,720), 1, Color.orange),


                new Wall(new Point(0,100), new Point(100,0), 1, Color.orange),
                new Wall(new Point(200,100), new Point(200,150), 1, Color.orange),
                new Wall(new Point(200,100), new Point(500,101), 1, Color.orange),
                new Wall(new Point(200,150), new Point(200,100), 1, Color.orange),
                new Wall(new Point(42,90), new Point(66,58), 1, Color.orange),
        };


        int mousex;
        int mousey;
        boolean moved = false;


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

            g.setColor(Color.RED);

            int middle = hud.getPlayerViewAbleArea()/2;

//            DrawingUtils.moodyFloorAndCieling(panel, g, middle);
            DrawingUtils.drawFloorAndCieling(panel, g, middle);

            DrawingUtils.drawWalls(panel, g, character, walls, frameCount, moved);
            if (Consts.DEBUG_RENDERING) {
                character.render();
                for (Wall wall :
                        walls) {
                    wall.draw(g);
                }

                double degreesPerPixel = Consts.FOV/panel.getWidth();
                double viewAngleOffset =  character.getAngle() - Consts.FOV/2;
                Line viewConeLine = character.getLine(viewAngleOffset);
                viewConeLine.draw(g);
                viewConeLine = character.getLine(degreesPerPixel * panel.getWidth() + viewAngleOffset);
                viewConeLine.draw(g);
                g.setColor(Color.RED);
            }





            String[] hudVars = new String[] {
                    "Player Position " + character.getPoint(),
                    "Wall height: " + Consts.BASE_WALL_HEIGHT,
                    "Character Angle: " + character.getAngle(),
            };

            hud.render(hudVars, character.getHealth());
            g.setColor(Color.green);


            int forwardDist = 0;
            int sideDist = 0;
            moved = false;
            if (input.keyDown('w')) {
                moved = true;
                forwardDist += 1;
            }
            if (input.keyDown('s')) {
                moved = true;
                forwardDist -=1;
            }
            if (input.keyDown('a')) {
                moved = true;
                sideDist -= 1;
            }
            if (input.keyDown('d')){
                sideDist += 1;
                moved = true;
            }
            if (input.keyDown('q')) {
                // Exit
                running = false;
                System.exit(0);
            }
            character.move(forwardDist,sideDist, walls);



            gPanel.drawImage(offscreen,0,0,null);
            panel.sleep(1000/Consts.FRAMERATE);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
