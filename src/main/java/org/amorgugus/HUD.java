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

    public void render(String[] vars) {
        this.graphics.setColor(Color.BLACK);
        this.graphics.fillRect(0, (panel.getHeight() * 3 / 4), panel.getWidth(), panel.getHeight());
        //turn each coord into a variable to fix issues\
        this.graphics.setColor(Color.ORANGE);
//        this.graphics.drawString( 20, (panel.getHeight()*3/4+10));

        int h = (panel.getHeight() * 3 / 4) + 10;
        for (int i = 0; i < vars.length; i++) {
            this.graphics.drawString(vars[i], 20, h+(i*12));
        }
    }
}
