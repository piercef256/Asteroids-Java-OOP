// package src;

import java.awt.event.KeyEvent;
import java.util.HashMap;

public class Ship extends Sprite {
    private int rotation;
    private int rotationSpeed;
    private int groundSpeed;

    public Ship(int x, int y) {
        super(x, y);
        rotation = 0;
        rotationSpeed = 5;
        groundSpeed = 2;

        loadImage("../sprites/ship.png");
        getImageDimensions();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRotation() {
        return rotation;
    }

    public void updatePosition() {
        if (pressedKeys.getOrDefault(KeyEvent.VK_LEFT, false)) {
            rotation -= rotationSpeed;
        }

        if (pressedKeys.getOrDefault(KeyEvent.VK_RIGHT, false)) {
            rotation += rotationSpeed;
        }

        if (pressedKeys.getOrDefault(KeyEvent.VK_UP, false)) {
            x += Math.cos(Math.toRadians(rotation - 90)) * groundSpeed;
            y += Math.sin(Math.toRadians(rotation - 90)) * groundSpeed;

        }

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
    }

    public void addToKeysPresses(int key) {
        pressedKeys.put(key, Boolean.TRUE);
    }

    public HashMap<Integer, Boolean> getPressedKeys() {
        return pressedKeys;
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