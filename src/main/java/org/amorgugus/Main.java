package org.amorgugus;

import java.awt.*;

public class Main {
    private static final int FRAMERATE = 240;
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        PanelInput input = new PanelInput(panel);
        Graphics g = panel.getGraphics();
        boolean running = true;

        Player character = new Player((double) panel.getWidth() / 2 - 5, (double) panel.getHeight() / 2 - 5, 0, g);

        int mousex;
        int mousey;
        while (running) {
            // LEAVE THIS HERE
            DrawingUtils.screenFlip(panel, g);
            g.setColor(Color.green);

            mousex = input.getMouseLoc().x;
            mousey = input.getMouseLoc().y;

            character.render();

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

            }

//            int width = 10;
//            g.drawRect(x-width/2, y-width/2, 10,10);

//            double angle = MathUtils.angleToPoint(x,y,mousex,mousey);
//            DrawingUtils.lineAlongAngle(g,angle,100,new Point(x,y));



            panel.sleep(1000/FRAMERATE);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
