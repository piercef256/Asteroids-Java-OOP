// package src;

import java.awt.event.KeyEvent;

public class Ship extends Sprite {
    private double dx = 200;
    private double dy = 200;
    private int rotation;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getRotation() {
        return this.rotation;
    }

    public Ship(int x, int y) {
        super(x, y);
        rotation = 0;

        loadImage("../sprites/ship.png");
        getImageDimensions();
    }

    public void updatePosition() {
        x = (int) Math.ceil(dx);
        y = (int) Math.ceil(dy);

    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            rotation -= 15;
        }

        if (key == KeyEvent.VK_RIGHT) {
            rotation += 15;
        }

        if (key == KeyEvent.VK_UP) {

            dx += Math.cos(Math.toRadians(rotation - 90)) * 10;
            dy += Math.sin(Math.toRadians(rotation - 90)) * 10;

        }

        if (key == KeyEvent.VK_DOWN) {

        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
        }

        if (key == KeyEvent.VK_RIGHT) {
        }

        if (key == KeyEvent.VK_UP) {

        }

        if (key == KeyEvent.VK_DOWN) {
        }
    }
}