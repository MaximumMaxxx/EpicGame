///2024256
package org.amorgugus;

import org.amorgugus.UW.DrawingPanel;

import java.awt.*;

public class HUD {
    private Item heldItem;
    private Graphics graphics;
    private DrawingPanel panel;

    public HUD(Graphics g, DrawingPanel panel) {
        this.heldItem = null;
        this.graphics = g;
        this.panel = panel;
    }

    public int getPlayerViewAbleArea() {
        return this.panel.getHeight() * 3/4;
    }


    /**
     * the render function will draw a box to the lower quarter of screen.
     * the box will contain information from vars
     * @param vars list of strings that get written to the screen.
     */
    public void render(String[] vars,int health) {
        this.graphics.setColor(Color.BLACK);
        this.graphics.fillRect(0, getPlayerViewAbleArea(), panel.getWidth(), panel.getHeight());
        this.graphics.setColor(Color.ORANGE);
        int h = (panel.getHeight() * 3 / 4) + 10;
        for (int i = 0; i < vars.length; i++) {
            this.graphics.drawString(vars[i], 20, h + (i * 12));
        }
            this.graphics.setColor(Color.RED);
            this.graphics.fillRect(400,(panel.getHeight() * 3 / 4 + 50),100,20);
            this.graphics.setColor(Color.GREEN);
            this.graphics.fillRect(400,(panel.getHeight() * 3 / 4 + 50),health,20);
        }
    }