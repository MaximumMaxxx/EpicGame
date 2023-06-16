package org.amorgugus;

import java.awt.*;

public interface Drawable {
    Point getIntersect(Line l);
    boolean doesIntersect(Line l);
    void draw(Graphics g);
    Color getColor();
    double getHeightMultiplier();
}
