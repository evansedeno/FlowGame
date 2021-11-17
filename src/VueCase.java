import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Random;

public class VueCase extends JPanel {

    private ModelCase c;

    public VueCase(int x, int y, boolean empty) {
        this.c = new ModelCase(x, y, empty);
    }

    public void update() {
        repaint();
    }

    public ModelCase getCase() {
        return this.c;
    }

    private void drawNoon(Graphics g) {
        g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
    }

    private void drawNine(Graphics g) {
        g.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
    }

    private void drawSix(Graphics g) {
        g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight());
    }

    private void drawThree(Graphics g) {
        g.drawLine(getWidth() / 2, getHeight() / 2, getWidth(), getHeight() / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.drawRoundRect(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, 5, 5);
        Rectangle2D deltaText = g.getFont().getStringBounds("0", g.getFontMetrics().getFontRenderContext()); // "0" utilisé pour gabarit

        switch (this.c.getType()) {
            case S1:
                g.drawString("1", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                break;
            case S2:
                g.drawString("2", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                break;
            case S3:
                g.drawString("3", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                break;
            case S4:
                g.drawString("4", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                break;
            case S5:
                g.drawString("5", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                break;
            case h0v0:
                drawNine(g);
                drawNoon(g);
                break;
            case h0v1:
                drawNine(g);
                drawSix(g);
                break;
            case h1v0:
                drawNoon(g);
                drawThree(g);
                break;
            case h1v1:
                drawThree(g);
                drawSix(g);
                break;
            case h0h1:
                drawThree(g);
                drawNine(g);
                break;
            case v0v1:
                drawNoon(g);
                drawSix(g);
                break;
            case cross:
                drawNoon(g);
                drawSix(g);
                drawThree(g);
                drawNine(g);
                break;
        }
    }
}