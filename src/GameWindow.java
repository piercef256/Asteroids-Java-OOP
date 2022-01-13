// package src;

import java.awt.*;
import javax.swing.*;

public class GameWindow extends JFrame {
    SpacePanel spacePanel;

    GameWindow() {
        spacePanel = new SpacePanel();
        this.add(spacePanel);
        this.setTitle("Asteroids");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
