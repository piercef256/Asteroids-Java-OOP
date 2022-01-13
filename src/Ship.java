// package src;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Ship extends Sprite {
    private int dx;
    private int dy;
    private List<Projectile> projectiles;

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public Ship(int x, int y) {
        super(x, y);
        projectiles = new ArrayList<>();
        loadImage("../sprites/ship.png");
        getImageDimensions();
    }

    public void updatePosition() {
        x += dx;
        y += dy;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
        }
    }

    public void fire() {
        projectiles.add(new Projectile(x + width, y + height / 2));
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}