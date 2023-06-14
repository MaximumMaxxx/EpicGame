package org.amorgugus;

public class Consts {
    public static final double PLAYER_MAX_VIEW_DISTANCE = 1000; // Render distance basically, top down pixels
    public static final double FOV = 60; // Degrees
    public static final int FRAMERATE = 60;
    public static final double VIEW_SCALE_FACTOR = 1;
    public static final int BASE_WALL_HEIGHT = 4000; // This value isn't really in any units, it's just a constant that happens to makes the walls look good
    public static final boolean DEBUG_RENDERING = false;
    public static final int WALL_CORNER_PARABOLA_HEIGHT = Integer.MAX_VALUE;

    public static final double VIEW_BOBBING_CYCLE_DIVISOR = 6;
    public static final double VIEW_BOBBING_HEIGHT_MULTIPLIER = 2;
    public static final boolean VIEW_BOBBING_ENABLED = true;
}
