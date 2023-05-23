package org.example;
import org.example.DrawingPanel;

import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        Graphics g = panel.getGraphics();

        Character Character = new Character((double) panel.getWidth() /2-5, (double) panel.getHeight() /2-5,0,g);
        Character.render();
    }
}
