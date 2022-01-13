// package src;

import java.awt.*;
import java.util.*;

public class Projectile extends Rectangle {
    Random random;
    int xSpeed;
    int ySpeed;
    int VELOCITY = 1;
    protected boolean visibility;
    private final int PROJECTILE_SPEED = 2;

    Projectile(int x, int y) {
        super(x, y, 5, 5);
        visibility = true;
    }

    public boolean getVisibility() {
        return visibility;
    }

    public void setVisibility(boolean state) {
        visibility = state;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void updatePosition() {
        x += PROJECTILE_SPEED;

        if (x > SpacePanel.GAME_WIDTH) {
            visibility = false;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }
}
