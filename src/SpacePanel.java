// package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;
import java.util.Timer;

public class SpacePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    public List<Asteroid> asteroidList = new ArrayList<Asteroid>();
    public List<Projectile> projectiles = new ArrayList<Projectile>();

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;

    Asteroid asteroid;
    Asteroid asteroid2;
    int gameTicks = 0;
    Ship ship;

    SpacePanel() {
        addAsteroids(1);
        addShip();
        this.setFocusable(true);
        this.addKeyListener(new KeyboardListener());
        this.setPreferredSize(SCREEN_SIZE);
        asteroidCreationLoop();

        gameThread = new Thread(this);
        gameThread.start();

    }

    public Projectile generateProjectile() {
        int rotation = ship.getRotation();
        // int x = ship.getX() + (ship.getWidth() / 2)
        // + (int) Math.cos(Math.toRadians(rotation));

        // int y = ship.getY() + (ship.getHeight() / 2)
        // - (int) Math.sin(Math.toRadians(rotation));

        int x = ship.getX() + (ship.getWidth() / 2);
        int y = ship.getY();

        x += Math.cos(Math.toRadians(rotation - 90)) * 10;
        y += Math.abs(Math.sin(Math.toRadians(rotation - 180)) * 10);

        return new Projectile(x, y, rotation);
    }

    public void addProjectile() {
        Projectile projectile = generateProjectile();
        projectiles.add(projectile);
        // projectiles.add(new Projectile(ship.getX() + (ship.getWidth() / 2),
        // ship.getY(), ship.getRotation()));
    }

    public void addShip() {
        ship = new Ship(400, 400);
    }

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        for (Asteroid asteroid : asteroidList) {
            asteroid.draw(g);
        }

        for (Projectile projectile : projectiles) {
            projectile.draw(g);
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.rotate(Math.toRadians(ship.getRotation()), ship.getX() + ship.getWidth() / 2,
                ship.getY() + ship.getHeight() / 2);
        g2d.drawImage(ship.getImage(), ship.getX(),
                ship.getY(), this);
    }

    public void updatePosition() {
        for (int i = 0; i < asteroidList.size(); i++) {
            Asteroid asteroid = asteroidList.get(i);

            if (asteroid.getVisibility()) {
                asteroid.updatePosition();
            } else {
                asteroidList.remove(i);
            }
        }
        ship.updatePosition();
        updateProjectiles();
    }

    public void asteroidCreationLoop() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                int numAsteroids = ThreadLocalRandom.current().nextInt(1, 4);
                addAsteroids(numAsteroids);
            }
        }, 0, 1000);
    }

    public void fire() {
        // projectiles.add(new Projectile(x + width, 0, rotation));
        addProjectile();

    }

    private void updateProjectiles() {
        List<Projectile> projectiles = getProjectiles();

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);

            if (projectile.getVisibility()) {
                projectile.updatePosition();
            } else {
                projectiles.remove(i);
            }
        }
    }

    public Asteroid generateRandomAsteroid() {
        int x = ThreadLocalRandom.current().nextInt(0, GAME_WIDTH);
        int y = ThreadLocalRandom.current().nextInt(0, GAME_HEIGHT);
        int size = ThreadLocalRandom.current().nextInt(15, 75);

        return new Asteroid(x, y, size);
    }

    public void addAsteroids(int numAsteroids) {
        for (int i = 0; i < numAsteroids + 1; i++) {
            Asteroid asteroid = generateRandomAsteroid();
            asteroidList.add(asteroid);
        }
    }

    public void run() {
        while (true) {
            updatePosition();
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public class KeyboardListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            // int key = e.getKeyCode();
            // ship.addKeyPress(key);

            // HashMap<Integer, Boolean> pressedKeys = ship.getKeys();

            // if (pressedKeys.getOrDefault(KeyEvent.VK_SPACE, false)) {
            // fire();
            // }

            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                fire();
            }
            ship.keyPressed(e);

        }

        public void keyReleased(KeyEvent e) {
            ship.keyReleased(e);
        }
    }
}
