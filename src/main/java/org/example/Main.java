package org.example;
import org.example.DrawingPanel;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        PanelInput input = new PanelInput(panel);
        Graphics g = panel.getGraphics();
        boolean running = true;

        Player character = new Player((double) panel.getWidth() / 2 - 5, (double) panel.getHeight() / 2 - 5, 0, g);
        character.render();


        int x = panel.getWidth() / 2;
        int y = panel.getWidth() / 2;

        int mousex;
        int mousey;
        while (running) {
            mousex = input.getMouseLoc().x;
            mousey = input.getMouseLoc().y;


            int width = 10;
            g.drawRect(x-width/2, y-width/2, 10,10);

            double angle = MathUtils.angleToPoint(x,y,mousex,mousey);
            DrawingUtils.lineAlongAngle(g,angle,100,new Point(x,y));



            panel.sleep(10);

            if (DrawingPanel.instances == 0) {
                running = false;
            }
        }

    }
}
