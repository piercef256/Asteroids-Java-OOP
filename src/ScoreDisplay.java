
import java.awt.*;

public class ScoreDisplay extends Rectangle {
    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int score;

    ScoreDisplay(int GAME_WIDTH, int GAME_HEIGHT) {
        ScoreDisplay.GAME_WIDTH = GAME_WIDTH;
        ScoreDisplay.GAME_HEIGHT = GAME_HEIGHT;
    }

    public void draw(Graphics g, boolean ingame) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 50));

        g.drawString("Score: " + String.valueOf(score), (GAME_WIDTH / 2) - 85, 50);

        if (!ingame) {
            g.setFont(new Font("Arial", Font.BOLD, 100));
            g.drawString("Game Over", 10, GAME_HEIGHT / 2);
        }

    }

    public int getScore() {
        return score;
    }

    public void addScore(int size) {
        score += size / 10;

    }

    public void renderGameOver(Graphics g, boolean gameover) {
        g.drawString("GAME OVER: " + String.valueOf(score), (GAME_WIDTH / 2) - 85, 50);
    }

}
