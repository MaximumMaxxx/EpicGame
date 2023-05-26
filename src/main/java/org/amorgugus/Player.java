package org.amorgugus;

import java.awt.*;

public class Player {
    private double x;
    private double y;
    private double angle;
    //angle ur facing in degrees
    private int speed;
    private Item heldItem;
    private Graphics graphics;

    public Player(double x, double y, double angle, Graphics g) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.speed = 0;
        this.heldItem = null;
        this.graphics = g;
    }

    public void move(int forwardDist, int sideDist) {
        // Max helped here
        double xcomp = MathUtils.degreeCos(this.angle)*forwardDist;
        double ycomp = MathUtils.degreeSin(this.angle)*forwardDist;

        // TODO! Implement Side move! - Matthew's Job

        this.x = this.x + xcomp;
        this.y = this.y + ycomp;
    }

    public void render() {
        this.graphics.setColor(Color.GREEN);
        this.graphics.fillOval((int) this.x, (int) this.y, 10, 10);
        // Line along angle code added by Max
        DrawingUtils.lineAlongAngle(this.graphics, this.angle, 10, new Point((int) this.x + 5, (int) this.y + 5));
    }

}

