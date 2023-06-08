package org.amorgugus;

import org.amorgugus.Utils.MathUtils;

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
    public Point getPoint(){
        return new Point(this.x, this.y);
    }

    // Added by max
    public double getAngle() {
        return this.angle;
    }

    // Added by Max
    public Line getLine() {
        return new Line(new Point(this.x + 5, this.y + 5), this.angle, Consts.PLAYER_MAX_VIEW_DISTANCE);
    }

    // Also added by Max
    public Line getLine(double angle) {
        return new Line(new Point(this.x + 5, this.y + 5), angle, Consts.PLAYER_MAX_VIEW_DISTANCE);

    }

    public void lookAt(Point point){
    this.angle = MathUtils.angleToPoint(this.x, this.y, point.getX(), point.getY());
    }

    public void move(int forwardDist, int sideDist) {
        // Max helped here
        double xcomp = MathUtils.degreeCos(this.angle)*forwardDist;
        double ycomp = MathUtils.degreeSin(this.angle)*forwardDist;

        xcomp += MathUtils.degreeCos(this.angle+90)*sideDist;
        ycomp += MathUtils.degreeSin(this.angle+90)*sideDist;

        this.x = this.x + xcomp;
        this.y = this.y + ycomp;
    }

    public void render() {
        this.graphics.setColor(Color.GREEN);
        this.graphics.fillOval((int) this.x, (int) this.y, 10, 10);
        // Line class code done my Max
        Line line = this.getLine();
        line.draw(this.graphics);
    }

}

