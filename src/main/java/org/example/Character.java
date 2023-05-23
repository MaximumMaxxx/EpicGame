package org.example;

import java.awt.*;

public class Character {
    private double x;
    private double y;
    private double angle;
    //angle ur facing in degrees
    private int speed;
    private Item heldItem;
    private Graphics graphics;


    public void move(int forwardDist, int sideDist) {
        double xcomp = Math.cos(Math.toRadians(this.angle));
        double ycomp = Math.cos(Math.toRadians(this.angle));
        this.x = this.x + xcomp;
        this.y = this.y + ycomp;
    }
}

