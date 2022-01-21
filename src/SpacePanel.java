// package src;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.List;
import java.util.ArrayList;

public class SpacePanel extends JPanel implements Runnable {
    static final int GAME_WIDTH = 600;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    public List<Asteroid> asteroidList;
    public List<Projectile> projectiles;
    public boolean ingame;
    public ScoreDisplay scoreDisplay;
    public Ship ship;
    public Thread gameThread;
    public Thread asteroidThread;
    public Image image;
    public Graphics graphics;
    public Random random;
    public KeyboardListener keyboardListener;

    SpacePanel() {
        gameThread = new Thread(this);

        gameThread.start();
    }

    public void initilize() {

        this.setFocusable(true);
        keyboardListener = new KeyboardListener();
        this.addKeyListener(keyboardListener);
        this.setPreferredSize(SCREEN_SIZE);
        asteroidList = Collections.synchronizedList(new ArrayList<Asteroid>());
        projectiles = Collections.synchronizedList(new ArrayList<Projectile>());

        ship = new Ship(400, 400);
        scoreDisplay = new ScoreDisplay(GAME_WIDTH, GAME_HEIGHT);
        ingame = true;
        startAsteroidCreationThread();
    }

    public void checkFire() {
        if (ship.getPressedKeys().getOrDefault(KeyEvent.VK_SPACE, false)) {
            fire();
        }
    }

    public void run() {
        initilize();
        while (true) {

            while (ingame) {
                checkFire();
                updatePosition();
                checkCollisions();
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // reset game after 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            reset();
        }
    }

    public void reset() {
        removeKeyListener();
        removeAll();
        initilize();
    }

    private void removeKeyListener() {
        this.removeKeyListener(keyboardListener);
    }

    public void startAsteroidCreationThread() {
        asteroidThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (ingame) {
                    addAsteroids(1);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        asteroidThread.start();
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

    public static int getGameWidth() {
        return GAME_WIDTH;
    }

    public void fire() {
        addProjectile();
    }

    private void updateProjectiles() {

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
        int x = 0;
        int y = 0;
        int size = 0;
        boolean isValid = false;
        Rectangle r1 = ship.getBounds();

        while (!isValid) {
            x = ThreadLocalRandom.current().nextInt(0, GAME_WIDTH);
            y = ThreadLocalRandom.current().nextInt(0, GAME_HEIGHT);
            size = ThreadLocalRandom.current().nextInt(15, 75);
            Rectangle r2 = new Rectangle(x, y, size, size);
            if (!r1.intersects(r2)) {
                isValid = true;
            }
        }

        return new Asteroid(x, y, size);
    }

    public void addAsteroids(int numAsteroids) {
        for (int i = 0; i < numAsteroids + 1; i++) {
            Asteroid asteroid = generateRandomAsteroid();
            asteroidList.add(asteroid);
        }
    }

    public boolean checkCollision(Rectangle r1, Rectangle r2) {
        return r1.intersects(r2);
    }

    public void checkCollisions() {

        Rectangle r0 = ship.getBounds();

        for (Asteroid asteroid : asteroidList) {

            Rectangle r2 = asteroid.getBounds();

            if (r0.intersects(r2)) {

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

                    // m.setVisibility(false);
                    asteroid.setVisibility(false);
                    scoreDisplay.addScore(asteroid.getDiameter());
                }
            }
        }
    }

    public class KeyboardListener extends KeyAdapter {
        public void keyPressed(KeyEvent e) {

            ship.keyPressed(e);
            // if (ship.getPressedKeys().getOrDefault(KeyEvent.VK_SPACE, false)) {
            // // fire every 100 ms
            // Timer timer = new Timer();
            // timer.schedule(new TimerTask() {
            // @Override
            // public void run() {
            // fire();
            // }
            // }, 0, 1000);
            // // fire();
            // // timer.cancel();

            // }

        }

        public void keyReleased(KeyEvent e) {
            ship.keyReleased(e);
        }
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

}
