package org.example;
import org.example.DrawingPanel;

import java.awt.*;
import java.awt.event.KeyListener;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        Graphics g = panel.getGraphics();

        int x = panel.getWidth() / 2;
        int y = panel.getWidth() / 2;

        int mousex = 0;
        int mousey = 0;

        panel.onMouseDrag( (mx,my) -> {
            mousex = mx;
            mousey = my;
        });

        g.drawLine(10,20,30,40);
    }
}
