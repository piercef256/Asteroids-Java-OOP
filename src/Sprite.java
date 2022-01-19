// package src;
// source: https://zetcode.com/javagames/movingsprites/ 

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class Sprite {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean visible;
    protected Image image;

    public Sprite(int x, int y) {
        this.x = x;
        this.y = y;
        visible = true;
    }

    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    protected void getImageDimensions() {
        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getVisibility() {
        return visible;
    }

    public void setVisibility(Boolean visible) {
        this.visible = visible;
    }
}