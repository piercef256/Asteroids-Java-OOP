// package src;

import java.awt.*;
import java.util.*;

public class Asteroid extends Rectangle {
    Random random;
    int xSpeed;
    int ySpeed;
    int VELOCITY = 1;
    int diameter;
    protected boolean visibility;

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

    public int getDiameter() {
        return diameter;
    }

    Asteroid(int randomX, int randomY, int randomDiameter) {
        super(randomX, randomY, randomDiameter, randomDiameter);
        diameter = randomDiameter;

        visibility = true;
        random = new Random();
        int randomXDirection = random.nextInt(2);
        if (randomXDirection == 0) {
            randomXDirection--;
        }

        int randomYDirection = random.nextInt(2);
        if (randomYDirection == 0) {
            randomYDirection--;
        }
        setTrajectory(randomXDirection * VELOCITY, randomYDirection * VELOCITY);
    }

    public void setTrajectory(int x, int y) {
        xSpeed = x;
        ySpeed = y;
    }

    public void updatePosition() {
        x += xSpeed;
        y += ySpeed;

    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }
}
