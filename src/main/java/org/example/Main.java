package org.example;
import org.example.DrawingPanel;

import java.awt.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        Graphics g = panel.getGraphics();
        g.drawLine(10,20,30,40);


    }
}
