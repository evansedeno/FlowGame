import javax.swing.*;
import java.awt.*;

public class VueCase extends JPanel {

    private final ModelCase c;
    private VueCase starter = null;
    private VueCase otherstarter = null;
    private CaseType firsttype = null;
    private CaseType lasttype = null;

    public VueCase(int x, int y, CaseType type) {
        this.c = new ModelCase(x, y, type);
    }

    public void update() {
        repaint();
    }

    public ModelCase getCase() {
        return this.c;
    }

    public boolean isLocked() {
        return this.c.isLocked();
    }

    public Color getColor() {
        if (this.starter != null) {
            switch (this.starter.getCase().getType()) {
                case S1:
                    return Color.green;
                case S2:
                    return Color.red;
                case S3:
                    return Color.cyan;
                case S4:
                    return Color.orange;
                case S5:
                    return Color.magenta;
                default:
                    return Color.white;
            }
        } else {
            return Color.white;
        }
    }

    private void drawNoon(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
        g.fillRect(getWidth() / 2 - 10, 0, 20, getHeight() / 2);
    }

    private void drawNine(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
        g.fillRect(0, getHeight() / 2 - 10, getWidth() / 2, 20);
    }

    private void drawSix(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight());
        g.fillRect(getWidth() / 2 - 10, getHeight() / 2, 20, getHeight());
    }

    private void drawThree(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(getWidth() / 2, getHeight() / 2, getWidth(), getHeight() / 2);
        g.fillRect(getWidth() / 2, getHeight() / 2 - 10, getWidth(), 20);
    }

    @Override
    public void paintComponent(Graphics g) {
        if (this.c.getType() != null) {
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.white);
            g.drawRoundRect(getWidth() / 4, getHeight() / 4, getWidth() / 2, getHeight() / 2, 5, 5);
            switch (this.c.getType()) {
                case S1:
                    //g.drawString("1", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                    g.setColor(Color.green);
                    g.fillOval(getWidth() / 2 - getWidth() / 3 / 2, getHeight() / 2 - getWidth() / 3 / 2, getWidth() / 3, getHeight() / 3);
                    break;
                case S2:
                    //g.drawString("2", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                    g.setColor(Color.red);
                    g.fillOval(getWidth() / 2 - getWidth() / 3 / 2, getHeight() / 2 - getWidth() / 3 / 2, getWidth() / 3, getHeight() / 3);
                    break;
                case S3:
                    //g.drawString("3", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                    g.setColor(Color.cyan);
                    g.fillOval(getWidth() / 2 - getWidth() / 3 / 2, getHeight() / 2 - getWidth() / 3 / 2, getWidth() / 3, getHeight() / 3);
                    break;
                case S4:
                    //g.drawString("4", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                    g.setColor(Color.orange);
                    g.fillOval(getWidth() / 2 - getWidth() / 3 / 2, getHeight() / 2 - getWidth() / 3 / 2, getWidth() / 3, getHeight() / 3);
                    break;
                case S5:
                    //g.drawString("5", getWidth() / 2 - (int) deltaText.getCenterX(), getHeight() / 2 - (int) deltaText.getCenterY());
                    g.setColor(Color.magenta);
                    g.fillOval(getWidth() / 2 - getWidth() / 3 / 2, getHeight() / 2 - getWidth() / 3 / 2, getWidth() / 3, getHeight() / 3);
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

                    if (this.otherstarter != null) {
                        VueCase firststarter = this.starter;
                        VueCase laststarter = this.otherstarter;
                        if (this.firsttype == CaseType.h0h1) {
                            this.setStarter(laststarter);
                            drawNoon(g);
                            drawSix(g);
                            this.setStarter(firststarter);
                            drawThree(g);
                            drawNine(g);
                        } else if (this.firsttype == CaseType.v0v1) {
                            this.setStarter(laststarter);
                            drawThree(g);
                            drawNine(g);
                            this.setStarter(firststarter);
                            drawNoon(g);
                            drawSix(g);
                        }
                    }
                    break;
            }
        }
    }

    public void setStarter(VueCase starter) {
        this.starter = starter;
    }

    public void sendCrossToDraw(Chemin chemin) {

        this.otherstarter = chemin.getStart();
        this.firsttype = this.c.getType();
        if (this.firsttype == CaseType.h0h1) {
            this.lasttype = CaseType.v0v1;
        } else if (this.firsttype == CaseType.v0v1) {
            this.lasttype = CaseType.h0h1;
        }
        this.c.setType(CaseType.cross);
        update();
    }

    public CaseType getFirstType() {
        return this.firsttype;
    }

    public CaseType getLastType() {
        return this.lasttype;
    }

    public VueCase getStarter(){
        return this.starter;
    }

    public VueCase getOtherStarter(){
        return this.otherstarter;
    }
}