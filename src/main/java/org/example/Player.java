package org.example;

import org.example.Item;

import java.awt.*;

public class Player {
    private double x;
    private double y;
    private double angle;
    //angle ur facing in degrees
    private int speed;
    private Item heldItem;
    private Graphics graphics;

    public Character(double x, double y, double angle, Graphics g) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 0;
        this.heldItem = null;
        this.graphics = g;
    }

    public void move(int forwardDist, int sideDist) {
        double xcomp = Math.cos(Math.toRadians(this.angle));
        double ycomp = Math.cos(Math.toRadians(this.angle));
        this.x = this.x + xcomp;
        this.y = this.y + ycomp;
    }

    public void render() {
        this.graphics.setColor(Color.GREEN);
        this.graphics.fillOval((int) this.x, (int) this.y, 10, 10);
        this.graphics.drawLine((int) this.x, (int) this.y, );
    }

}

