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
    public boolean ingame;
    public ScoreDisplay scoreDisplay;

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
        this.setFocusable(true);
        this.addKeyListener(new KeyboardListener());
        this.setPreferredSize(SCREEN_SIZE);

        addShip();
        addAsteroids(1);
        startAsteroidCreationThread();

        scoreDisplay = new ScoreDisplay(GAME_WIDTH, GAME_HEIGHT);
        ingame = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public Projectile generateProjectile() {
        int shipRotation = ship.getRotation();

        int x = ship.getX() + (ship.getWidth() / 2);
        int y = ship.getY();

        x += Math.cos(Math.toRadians(shipRotation - 90)) * ship.getWidth() / 2;
        y += Math.abs(Math.sin(Math.toRadians(shipRotation - 90)) * ship.getHeight() / 2);

        return new Projectile(x, y, shipRotation);
    }

    public void addProjectile() {
        Projectile projectile = generateProjectile();
        projectiles.add(projectile);

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
        scoreDisplay.draw(g, ingame);
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

    public void startAsteroidCreationThread() {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                int numAsteroids = ThreadLocalRandom.current().nextInt(1, 3);
                addAsteroids(numAsteroids);
            }
        }, 0, 2000);
    }

    public void fire() {
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

            if (ingame) {
                updatePosition();
                checkCollisions();
                repaint();
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        // if (ingame == false) {
        // System.exit(0);
        // }
    }

    public void checkCollisions() {

        Rectangle r3 = ship.getBounds();

        for (Asteroid asteroid : asteroidList) {

            Rectangle r2 = asteroid.getBounds();

            if (r3.intersects(r2)) {

                ship.setVisibility(false);
                // asteroid.setVisibility(false);
                ingame = false;
            }
        }

        for (Projectile m : projectiles) {

            Rectangle r1 = m.getBounds();

            for (Asteroid asteroid : asteroidList) {

                Rectangle r2 = asteroid.getBounds();

                if (r1.intersects(r2)) {

                    m.setVisibility(false);
                    asteroid.setVisibility(false);
                    scoreDisplay.addScore(asteroid.getDiameter());
                }
            }
        }
    }

    public class KeyboardListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
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
