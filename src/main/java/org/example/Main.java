package org.example;
import org.example.DrawingPanel;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        DrawingPanel panel = new DrawingPanel();
        Graphics graphics = panel.getGraphics();
    }
}
