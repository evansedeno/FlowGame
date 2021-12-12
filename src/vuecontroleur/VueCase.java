package vuecontroleur;

import model.Chemin;
import model.ModelCase;
import utilitaires.CaseType;

import javax.swing.*;
import java.awt.*;

/**
 * @author Frédéric ARMETTA & Evan SEDENO
 * @version 2.0
 * @date 16/11/2021
 * @file vuecontroleur.VueCase.java
 * @brief Class Vue de l'affichage d'une case
 * @details Contient toutes les méthodes et attributs pour l'affichage d'une case
 */
public class VueCase extends JPanel {

    private final ModelCase c;
    private VueCase starter = null;
    private VueCase otherstarter = null;
    private CaseType firsttype = null;
    private CaseType lasttype = null;

    /**
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Constructeur de la class vuecontroleur.VueCase
     * @details - On assigne à l'attribut "c" une instanciation de la classe model.ModelCase avec en paramètre les paramètres donnés au constructeur
     */
    public VueCase(int x, int y, CaseType type) {
        this.c = new ModelCase(x, y, type);
    }

    /**
     * @author Evan SEDENO
     * @brief Actualise les elements graphiques
     * @details - On rejoue la méthode paintComponent pour actualiser les differents elements
     */
    public void update() {
        repaint();
    }

    /**
     * @return this.c: [vuecontroleur.VueCase] Retourne l'attribut "c"
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut "c"
     * @details - On retourne la valeur de l'attribut "c"
     */
    public ModelCase getCase() {
        return this.c;
    }

    /**
     * @return [Color] Retourne une couleur
     * @author Evan SEDENO
     * @brief Retourne une couleur en fonction du type de starter
     * @details - On regarde avec un switch case le type de starter et on retourne la couleur correspondante
     */
    public Color getColor() {
        //On regarde avec le switch case quelle couleur renvoyer
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

    /**
     * @param g: [Graphics] Composante graphique du JFrame
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Dessine une part de chemin (Nord)
     * @details - On assigne la couleur en fonction de la couleur du starter
     * @details - (Le code en commentaire est une alternative d'affichage)
     */
    private void drawNoon(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, 0);
        g.fillRect(getWidth() / 2 - 10, 0, 20, getHeight() / 2);
    }

    /**
     * @param g: [Graphics] Composante graphique du JFrame
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Dessine une part de chemin (Ouest)
     * @details - On assigne la couleur en fonction de la couleur du starter
     * @details - (Le code en commentaire est une alternative d'affichage)
     */
    private void drawNine(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(0, getHeight() / 2, getWidth() / 2, getHeight() / 2);
        g.fillRect(0, getHeight() / 2 - 10, getWidth() / 2, 20);
    }

    /**
     * @param g: [Graphics] Composante graphique du JFrame
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Dessine une part de chemin (Sud)
     * @details - On assigne la couleur en fonction de la couleur du starter
     * @details - (Le code en commentaire est une alternative d'affichage)
     */
    private void drawSix(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(getWidth() / 2, getHeight() / 2, getWidth() / 2, getHeight());
        g.fillRect(getWidth() / 2 - 10, getHeight() / 2, 20, getHeight());
    }

    /**
     * @param g: [Graphics] Composante graphique du JFrame
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Dessine une part de chemin (Est)
     * @details - On assigne la couleur en fonction de la couleur du starter
     * @details - (Le code en commentaire est une alternative d'affichage)
     */
    private void drawThree(Graphics g) {
        g.setColor(this.getColor());
        //g.drawLine(getWidth() / 2, getHeight() / 2, getWidth(), getHeight() / 2);
        g.fillRect(getWidth() / 2, getHeight() / 2 - 10, getWidth(), 20);
    }

    /**
     * @param g: [Graphics] Composante graphique du JFrame
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Dessine l'essemble des composants
     * @details - Dessine un rectangle vide
     * @details - En fonction du type de la case, on dessine soit un rond de couleur, soit un chemin ou un croisement
     * @details - (Le code en commentaire est une alternative d'affichage)
     * @see "https://docs.oracle.com/javase/7/docs/api/javax/swing/JComponent.html"
     */
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

    /**
     * @param starter: [vuecontroleur.VueCase] Objet vuecontroleur.VueCase à assigner
     * @author Evan SEDENO
     * @brief Mutateur de l'attribut starter
     * @details - On assigne à l'attribut la valeur de l'objet vuecontroleur.VueCase en paramètre
     */
    public void setStarter(VueCase starter) {
        this.starter = starter;
    }

    /**
     * @param chemin: [model.Chemin] model.Chemin envoyé pour le croisement
     * @author Evan SEDENO
     * @brief Dessine un croisement en fonction des deux chemins
     * @details - On assigne le starter du deuxième chemin à l'attribut otherstarter
     * @details - On assigne l'attribut firsttype au type actuel et lasttype à la direction inverse
     * @details - On assigne le nouveau type croisement à l'attribut "c"
     * @details - On redessine la case
     */
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

    /**
     * @return this.firsttype: [vuecontroleur.VueCase] Retourne l'attribut firsttype
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut firsttype
     * @details - On retourne la valeur de l'attribut firsttype
     */
    public CaseType getFirstType() {
        return this.firsttype;
    }

    /**
     * @return this.lasttype: [vuecontroleur.VueCase] Retourne l'attribut lasttype
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut lasttype
     * @details - On retourne la valeur de l'attribut lasttype
     */
    public CaseType getLastType() {
        return this.lasttype;
    }

    /**
     * @return this.otherstarter: [vuecontroleur.VueCase] Retourne l'attribut otherstarter
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut otherstarter
     * @details - On retourne la valeur de l'attribut otherstarter
     */
    public VueCase getOtherStarter() {
        return this.otherstarter;
    }

}