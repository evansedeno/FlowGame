package vuecontroleur;

import model.ModelGrille;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Frédéric ARMETTA & Evan SEDENO
 * @version 2.0
 * @date 16/11/2021
 * @file vuecontroleur.VueCase.java
 * @brief Class Vue et Controleur pour les grilles
 * @details Contient toutes les méthodes et attributs pour la gestion et l'affichage d'une grille
 */
public class VueControleurGrille extends JFrame implements Observer {

    private static final int PIXEL_PER_SQUARE = 60;
    private JComponent currentComponent;
    private final ModelGrille grille;
    private final JPanel contentPane;
    private final JPanel buttonPane;
    private final JPanel allPane;

    /**
     * @param model: [model.ModelGrille] Modèle de la grille
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Constructeur de la class vuecontroleur.VueControleurGrille
     * @details - On assigne la valeur du paramètre model à l'attribut grille
     * @details - On définit certains paramètres sur le JFrame (fermeture de la fenêtre, taille de la fenêtre et pas redimensionnable)
     * @details - On assigne aux 3 attributs JPanel une instanciation de JPanel
     * @details - On ajoute la grille à la vue et on ajoute les listeners
     * @details - On ajoute tous les panels à la vue
     */
    public VueControleurGrille(ModelGrille model) {
        this.grille = model;

        //On créer la vue
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(this.grille.getSize() * PIXEL_PER_SQUARE * 2, this.grille.getSize() * PIXEL_PER_SQUARE * 2 + ((this.grille.getLevel() == 0) ? 50 : 0));
        setResizable(false);
        GridLayout gridLayout = new GridLayout(this.grille.getSize(), this.grille.getSize());
        this.contentPane = new JPanel(gridLayout);
        this.buttonPane = new JPanel();
        this.allPane = new JPanel();

        //On ajoute la grille au panel
        this.addGrilleOnVue();
        this.addGrilleListeners();

        //On ajoute les panels à la vue
        this.addPaneOnVue();
        this.allPane.setLayout(new BoxLayout(this.allPane, BoxLayout.Y_AXIS));
        setContentPane(this.allPane);
        setVisible(true);
    }

    /**
     * @author Evan SEDENO
     * @brief Ajoute au JPanel principal les JPanel nécessaires
     * @details - On ajoute les JPanel nécessaire à la création ou au jeu dans le JPanel principal
     */
    public void addPaneOnVue() {
        //On ajoute les élements à la vue en fonction de si l'on créer ou on joue un niveau
        if (grille.isCreation()) {
            this.allPane.add(this.contentPane);
            this.displayValidateButton();
            this.allPane.add(this.buttonPane);
        } else {
            this.allPane.add(this.contentPane);
        }
    }

    /**
     * @author Evan SEDENO
     * @brief Ajoute à la vue chaque case de la grille
     * @details - On parcourt l'ensemble de la grille et on ajoute chaque vuecontroleur.VueCase à la vue
     */
    public void addGrilleOnVue() {
        //On ajoute chaque case à la vue de la grille
        for (int i = 0; i < grille.getSize(); i++) {
            for (int j = 0; j < grille.getSize(); j++) {
                this.contentPane.add(grille.getVueCase(i, j));
            }
        }
    }

    /**
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Ajoute sur chaque case les 4 listeners (Pressé, Cliqué, Entré et Relâché)
     * @details - On parcourt chaque case de la grille et on ajoute les 4 listeners avec la méthode de la grille appropriée
     */
    public void addGrilleListeners() {
        //Pour chaque case de la grille on ajoute les listeners
        for (int i = 0; i < grille.getSize(); i++) {
            for (int j = 0; j < grille.getSize(); j++) {
                this.grille.getVueCase(i, j).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        grille.pressedEvent((VueCase) e.getSource());
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        grille.clickedEvent((VueCase) e.getSource());
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentComponent = (JComponent) e.getSource();
                        grille.enteredEvent((VueCase) currentComponent);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        grille.releasedEvent((VueCase) currentComponent);
                    }
                });
            }
        }
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche l'écran de fin du jeu
     * @details - On créer un nouveau JPanel pour l'affichage de l'écran de fin
     * @details - On créer les différents text
     * @details - On créer un bouton retour avec un listener qui va créer une nouvelle instance de la classe vuecontroleur.VueControleurMenu
     * @details - On ferme la fenêtre de jeu si on appuie sur le bouton
     */
    public void endGame() {

        //On créer un nouveau panel en félicitant le joueur
        JPanel gameEnd = new JPanel(new GridLayout(3, 1));
        gameEnd.setBackground(Color.darkGray);
        gameEnd.setPreferredSize(new Dimension(getWidth() - 100, getHeight() / 2));

        JLabel text1 = new JLabel("Bravo " + grille.getUser().getUsername() + " !");
        JLabel text2 = new JLabel("Vous avez réussi le niveau !");
        text1.setForeground(Color.white);
        text1.setHorizontalAlignment(JLabel.CENTER);
        text2.setForeground(Color.white);
        text2.setHorizontalAlignment(JLabel.CENTER);

        text1.setVerticalAlignment(SwingConstants.CENTER);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setVerticalAlignment(SwingConstants.CENTER);
        text2.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("myfont", Font.BOLD, 15);
        text1.setFont(font);
        text2.setFont(font);
        gameEnd.add(text1);
        gameEnd.add(text2);

        //On propose au joueur de retourner au menu
        JButton back = new JButton("RETOUR");
        back.setPreferredSize(new Dimension(30, 30));
        back.setBackground(Color.white);
        back.setBorderPainted(false);
        back.setFont(new Font("buttonfont", Font.BOLD, 20));

        back.addActionListener(e -> {
            VueControleurMenu menu = new VueControleurMenu();
            menu.setUser(this.grille.getUser());
            menu.drawMainMenu();
            setVisible(false);
            dispose();
        });

        gameEnd.add(back);
        setContentPane(gameEnd);
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche l'écran de fin de la création de niveau
     * @details - On créer un nouveau JPanel pour la fin de création de niveau
     * @details - On créer les différents text
     * @details - On créer un bouton retour avec un listener qui va créer une nouvelle instance de la classe vuecontroleur.VueControleurMenu
     * @details - On ferme la fenêtre de jeu si on appuie sur le bouton
     */
    public void displayCreationFinished() {

        //On affiche un nouveau panel en disant que le niveau a bien été enregistré
        JPanel gameReady = new JPanel();
        gameReady.setBackground(Color.darkGray);
        JLabel text = new JLabel("Le niveau a bien été enregisté !");
        text.setForeground(Color.white);
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("myfont", Font.BOLD, 20);
        text.setFont(font);
        gameReady.add(text);

        //On propose au joueur de retourner au menu
        JButton button = new JButton("RETOUR");
        button.setBackground(Color.white);
        button.setPreferredSize(new Dimension(getWidth() / 2, 50));

        button.addActionListener(e -> {
            new VueControleurMenu();
            removeAll();
            setVisible(false);
            dispose();
        });

        gameReady.add(button);
        setContentPane(gameReady);
        repaint();
        revalidate();
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche le bouton de validation de la création du niveau
     * @details - On créer un bouton valider avec un listener qui sauvegarde le niveau et qui appelle la méthode displayCreationFinished
     * @details - On ajoute le bouton à la vue
     */
    public void displayValidateButton() {
        JButton button = new JButton("VALIDER");
        button.setBackground(Color.white);
        button.setPreferredSize(new Dimension(getWidth() / 2, 50));

        button.addActionListener(e -> {
            //On créer un nouveau fichier si on valide la grille
            grille.generateNewLevel();
            displayCreationFinished();
        });

        //On ajoute le bouton valider
        this.buttonPane.add(button);
        this.buttonPane.setMinimumSize(new Dimension(PIXEL_PER_SQUARE * this.grille.getSize(), 50));
        this.buttonPane.setMaximumSize(new Dimension(PIXEL_PER_SQUARE * this.grille.getSize(), 50));
    }

    /**
     * @param o:   [Observable] Objet Observable relié à l'Observer
     * @param arg: [Object] Arguments à passé à la fonction
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Actualise l'Observer
     * @details - On vérifie si le jeu est terminé, si oui on affiche le panneau de fin
     * @details - On actualise la vue
     * @see "https://docs.oracle.com/javase/7/docs/api/java/util/Observer.html"
     */
    @Override
    public void update(Observable o, Object arg) {
        if (!this.grille.isCreation() && this.grille.isFinished()) {
            endGame();
        }
        repaint();
        revalidate();
    }
}





























