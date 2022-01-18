// package src;

import java.awt.event.KeyEvent;
import java.util.HashMap;

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

    public void addKeyPress(int key) {
        pressedKeys.put(key, Boolean.TRUE);

    }

    public HashMap<Integer, Boolean> getKeys() {
        return pressedKeys;
    }

    HashMap<Integer, Boolean> pressedKeys = new HashMap<>();

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
        pressedKeys.put(key, Boolean.TRUE);

        if (pressedKeys.getOrDefault(KeyEvent.VK_LEFT, false)) {
            rotation -= 15;
        }

        if (pressedKeys.getOrDefault(KeyEvent.VK_RIGHT, false)) {
            rotation += 15;
        }

        if (pressedKeys.getOrDefault(KeyEvent.VK_UP, false)) {

            dx += Math.cos(Math.toRadians(rotation - 90)) * 10;
            dy += Math.sin(Math.toRadians(rotation - 90)) * 10;

        }

        // if (key == KeyEvent.VK_LEFT) {
        // rotation -= 15;
        // }

        // if (key == KeyEvent.VK_RIGHT) {
        // rotation += 15;
        // }

        // if (key == KeyEvent.VK_UP) {

        // dx += Math.cos(Math.toRadians(rotation - 90)) * 10;
        // dy += Math.sin(Math.toRadians(rotation - 90)) * 10;

        // }

        if (key == KeyEvent.VK_DOWN) {

        }

    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys.put(key, Boolean.FALSE);

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