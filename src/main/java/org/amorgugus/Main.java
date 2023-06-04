package org.amorgugus;

import org.amorgugus.UW.DrawingPanel;
import org.amorgugus.UW.PanelInput;
import org.amorgugus.Utils.DrawingUtils;

import java.awt.*;

public class Main {
    private static final int FRAMERATE = 60;
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel(1280, 720);
        PanelInput input = new PanelInput(panel);
        Graphics g = panel.getGraphics();
        boolean running = true;

        Player character = new Player((double) panel.getWidth() / 2 - 5, (double) panel.getHeight() / 2 - 5, 0, g);
        Line tempWall = new Line(new Point(66,58), new Point(228,19));

        int mousex;
        int mousey;
        HUD hud = new HUD(g, panel);
        while (running) {

            // LEAVE THIS HERE {
            DrawingUtils.screenFlip(panel, g);
            // }

            // Variables to render on hud
            Point intersect = character.getLine().getIntersect(tempWall);

            double distance;
            if (intersect != null) {
                distance = intersect.distance(character.getPoint());
            } else {
                distance = Double.POSITIVE_INFINITY;
            }

            String[] hudVars = new String[] {
                "Intersect point" + intersect,
                "View Intersect " + character.getLine().doesIntersect(tempWall),
                "Intersect distance" + distance,
                "Player Position " + character.getPoint()
            };

            hud.render(hudVars);
            g.setColor(Color.green);

            mousex = input.getMouseLoc().x;
            mousey = input.getMouseLoc().y;

            character.lookAt(new Point(mousex, mousey));

            character.render();

            g.setColor(Color.orange);
            tempWall.draw(g);


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

            panel.sleep(1000/FRAMERATE);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
