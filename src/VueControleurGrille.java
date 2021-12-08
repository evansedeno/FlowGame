import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.util.List;

/**
 * @author Frédéric ARMETTA & Evan SEDENO
 * @version 2.0
 * @date 16/11/2021
 * @file VueCase.java
 * @brief Class Vue et Controleur pour les grilles
 * @details Contient toutes les méthodes et attributs pour la gestion et l'affichage d'une grille
 */
public class VueControleurGrille extends JFrame {

    private static final int PIXEL_PER_SQUARE = 60;
    private JComponent currentComponent;
    private final ModelGrille grille;
    private final JPanel contentPane;
    private final JPanel buttonPane;
    private final JPanel allPane;
    private int level;
    private final int size;
    private final User user;
    private final List<Chemin> ways = new ArrayList<>();
    private boolean clicked = false;
    private final boolean iscreation;

    /**
     * @param size:   [int] Taille du niveau
     * @param _level: [int] Identifiant du niveau
     * @param user:   [User] Utilisateur
     * @author Frédéric ARMETTA & Evan SEDENO
     * @brief Constructeur de la class VueControleurGrille
     * @details - On assigne la valeur des attributs size, _level et user avec les 3 paramètres
     * @details - On définit certains paramètres sur le JFrame (fermeture de la fenêtre, taille de la fenêtre et pas redimensionnable)
     * @details - On assigne aux 3 attributs JPanel une instanciation de JPanel
     * @details - On assigne à l'attribut grille une nouvelle grille
     * @details - On vérifie si l'on souhaite jouer ou créer un niveau et on appelle les méthodes requises
     * @details - On ajoute la grille à la vue et on ajoute les listeners
     * @details - On ajoute tous les panels à la vue
     */
    public VueControleurGrille(int size, int _level, User user) {

        this.level = _level;
        this.size = size;
        this.user = user;

        //On créer la vue
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE * 2, size * PIXEL_PER_SQUARE * 2 + ((this.level == 0) ? 50 : 0));
        setResizable(false);
        GridLayout gridLayout = new GridLayout(size, size);
        this.contentPane = new JPanel(gridLayout);
        this.buttonPane = new JPanel();
        this.allPane = new JPanel();

        //On ajoute génère la grille (vide ou pleine)
        this.grille = new ModelGrille(size, this.level);
        if (level == 0) {
            this.iscreation = true;
            Files file = new Files();
            setNewLevel(file.getNextLevel(size));
            displayValidateButton();
            grille.generateEmptyGrille();
        } else {
            this.iscreation = false;
            grille.generateGrille();
        }

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
     * @param level: [int] Identifiant du niveau
     * @author Evan SEDENO
     * @brief Mutateur de l'attribut _level
     * @details - On assigne la valeur du paramètre level à l'attribut _level
     */
    public void setNewLevel(int level) {
        this.level = level;
    }

    /**
     * @author Evan SEDENO
     * @brief Ajoute au JPanel principal les JPanel nécessaires
     * @details - On ajoute les JPanel nécessaire à la création ou au jeu dans le JPanel principal
     */
    public void addPaneOnVue() {
        //On ajoute les élements à la vue en fonction de si l'on créer ou on joue un niveau
        if (this.iscreation) {
            this.allPane.add(this.contentPane);
            this.allPane.add(this.buttonPane);
        } else {
            this.allPane.add(this.contentPane);
        }
    }

    /**
     * @author Evan SEDENO
     * @brief Ajoute à la vue chaque case de la grille
     * @details - On parcourt l'ensemble de la grille et on ajoute chaque VueCase à la vue
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
     * @details - On parcourt chaque case de la grille et on ajoute les 4 listeners avec la méthode appropriée
     */
    public void addGrilleListeners() {
        //Pour chaque case de la grille on ajoute les listeners
        for (int i = 0; i < grille.getSize(); i++) {
            for (int j = 0; j < grille.getSize(); j++) {
                this.grille.getVueCase(i, j).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        mousePressedListener((VueCase) e.getSource());
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        mouseClickedListener((VueCase) e.getSource());
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentComponent = (JComponent) e.getSource();
                        mouseEnteredListener((VueCase) currentComponent);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        mouseReleaseListener((VueCase) currentComponent);
                    }
                });
            }
        }
    }

    /**
     * @author Evan SEDENO
     * @brief Dessine les cases du dernier chemin
     * @details - On appelle la méthode assignType sur le dernier chemin
     * @details - On redessine les cases
     */
    public void drawCases() {
        //On réassigne chaque case
        ways.get(ways.size() - 1).assignType(this.ways);
        repaint();
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche l'écran de fin du jeu
     * @details - On créer un nouveau JPanel pour l'affichage de l'écran de fin
     * @details - On créer les différents text
     * @details - On Ajoute les points au joueur et on l'actualise dans le fichier "users.json"
     * @details - On créer un bouton retour avec un listener qui va créer une nouvelle instance de la classe VueControleurMenu
     * @details - On ferme la fenêtre de jeu si on appuie sur le bouton
     */
    public void endGame() {

        //On créer un nouveau panel en félicitant le joueur
        JPanel gameEnd = new JPanel(new GridLayout(3, 1));
        gameEnd.setBackground(Color.darkGray);
        gameEnd.setPreferredSize(new Dimension(getWidth() - 100, getHeight() / 2));

        JLabel text1 = new JLabel("Bravo " + this.user.getUsername() + " !");
        JLabel text2 = new JLabel("Vous avez réussi le niveau !");
        text1.setForeground(Color.white);
        text1.setHorizontalAlignment(JLabel.CENTER);
        text2.setForeground(Color.white);
        text2.setHorizontalAlignment(JLabel.CENTER);

        this.user.addPoints(this.size);
        Files file = new Files();
        ArrayList<User> users = file.getAllUsers();
        boolean userexist = false;
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId() == this.user.getId()) {
                users.set(i, this.user);
                userexist = true;
            }
        }
        if (!userexist) {
            users.add(this.user);
        }
        file.actualizeUsers(users);

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
            menu.setUser(user);
            menu.drawMainMenu();
            setVisible(false);
            dispose();
        });

        gameEnd.add(back);

        setContentPane(gameEnd);
        repaint();
        revalidate();
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche l'écran de fin de la création de niveau
     * @details - On créer un nouveau JPanel pour la fin de création de niveau
     * @details - On créer les différents text
     * @details - On créer un bouton retour avec un listener qui va créer une nouvelle instance de la classe VueControleurMenu
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
        Files file = new Files();
        JButton button = new JButton("VALIDER");
        button.setBackground(Color.white);
        button.setPreferredSize(new Dimension(getWidth() / 2, 50));

        button.addActionListener(e -> {
            //On créer un nouveau fichier si on valide la grille
            file.writeLevelFile(this.size, this.level, this.grille);
            displayCreationFinished();
        });

        //On ajoute le bouton valider
        this.buttonPane.add(button);
        this.buttonPane.setMinimumSize(new Dimension(PIXEL_PER_SQUARE * this.size, 50));
        this.buttonPane.setMaximumSize(new Dimension(PIXEL_PER_SQUARE * this.size, 50));
    }

    /**
     * @param mycase: [VueCase] Case où la souris est entré
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est entré
     * @details - On vérifie si on a le bouton de la souris pressé et qu'il existe au moins un chemin
     * @details - On vérifie si la case est bloqué si oui on appelle la méthode pour relâcher la souris
     * @details - On vérifie si la case est un tournant ou que la case est déjà enregistré dans le dernier chemin, si oui alors on appelle la méthode pour relâcher la souris
     * @details - On vérifie si la case est vide ou que c'est un chemin, si non on appelle la méthode pour relâcher la souris
     * @details - On vérifie si la case est apte à être dessiné, si oui on appelle la méthode pour la dessiner, si non on appelle la méthode pour relâcher la souris
     */
    public void mouseEnteredListener(VueCase mycase) {

        if (this.clicked && this.ways.size() > 0) {
            //On vérifie si on traverse une case bloquée autre que celle appartenant au chemin
            if (!mycase.getCase().isLocked()) {
                if (mycase.getCase().isTurn() || this.ways.get(this.ways.size() - 1).getWayWithoutStartAndStop().contains(mycase)) {
                    mouseReleaseListener(mycase);
                } else if (mycase.getCase().getType() == CaseType.empty || mycase.getCase().isWay()) {
                    Chemin lastway = this.ways.get(this.ways.size() - 1);
                    if ((mycase.getCase().isWay()
                            && !mycase.getCase().isSameDirection(lastway.getWay().get(lastway.getWay().size() - 1).getCase().getType()))
                            || mycase.getCase().getType() == CaseType.empty
                            && this.ways.get(this.ways.size() - 1).checkGoodChemin()) {
                        //On ajoute la case au chemin
                        this.ways.get(this.ways.size() - 1).addCase(mycase);
                        //On ajoute à la case actuelle, la case du début du chemin
                        mycase.setStarter(this.ways.get(this.ways.size() - 1).getStart());
                        //On redessine les cases
                        drawCases();
                    } else {
                        mouseReleaseListener(mycase);
                    }
                } else {
                    mouseReleaseListener(mycase);
                }
            } else {
                mouseReleaseListener(mycase);
            }
        }
    }

    /**
     * @param mycase: [VueCase] Case où la souris est appuyé
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est appuyé
     * @details - On vérifie si on créer ou on joue
     * @details - Si c'est une creation alors on appelle la méthode setNextNumber sur la case
     * @details - Si c'est un jeu alors on vérifie si la case est bloqué, si oui on créer un nouveau chemin
     */
    public void mousePressedListener(VueCase mycase) {
        //On vérifie si on créer la grille ou si on joue
        if (this.iscreation) {
            //On choisit la prochaine case à placer
            mycase.getCase().setNextNumber();
            mycase.update();
        } else {
            if (mycase.getCase().isLocked()) {

                //On récupère la liste de chaque debut et chaque fin de chaque chemin complet
                List<VueCase> lockedcases = new ArrayList<>();
                for (Chemin chemin : this.ways) {
                    if (chemin.getStart() != null && chemin.getStart() != null) {
                        lockedcases.add(chemin.getStart());
                        lockedcases.add(chemin.getStop());
                    }
                }

                //On vérifie si la case pressée n'appartient pas déjà à un chemin existant
                if (!lockedcases.contains(mycase)) {
                    Chemin way = new Chemin(mycase);
                    way.getStart().setStarter(way.getStart());
                    this.ways.add(way);
                    this.clicked = true;
                }
            }
        }
    }

    /**
     * @param mycase: [VueCase] Case où la souris est relâché
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est relâché
     * @details - On vérifie s'il y a au moins un chemin, si la case est bloquée et que la souris est pressé
     * @details - On vérifie le type de la case, si c'est le même type que la case départ du chemin alors on termine le chemin, sinon on supprime le chemin
     * @details - On vérifie si l'on a fini le niveau
     * @details - On vérifie que le dernier niveau est valide
     */
    public void mouseReleaseListener(VueCase mycase) {
        if (this.ways.size() > 0 && mycase.getCase().isLocked() && this.clicked) {
            //On vérifie si la souris est bien lâché sur une case bloquée
            if (mycase.getCase().getType() == this.ways.get(this.ways.size() - 1).getStart().getCase().getType() && this.clicked && mycase != this.ways.get(this.ways.size() - 1).getStart()) {
                //On vérifie si la case STOP est valide
                this.ways.get(this.ways.size() - 1).setStop(mycase);
                //On vérifie si toutes les cases ont été remplis
                if (this.grille.validateGrille()) {
                    endGame();
                }
            } else {
                this.ways.get(this.ways.size() - 1).deleteChemin(this.ways);
                this.ways.remove(this.ways.get(this.ways.size() - 1));
            }
        } else if (this.ways.size() > 0 && !mycase.getCase().isLocked() && this.clicked) {
            this.ways.get(this.ways.size() - 1).deleteChemin(this.ways);
            this.ways.remove(this.ways.get(this.ways.size() - 1));
        }
        this.clicked = false;
        //On vérifie que le dernier chemin est valide
        if (this.ways.size() > 0) {
            if (!this.ways.get(this.ways.size() - 1).checkGoodChemin()) {
                this.ways.get(this.ways.size() - 1).deleteChemin(this.ways);
                this.ways.remove(this.ways.get(this.ways.size() - 1));
            }
            if (this.ways.size() > 0) drawCases();
        }
        repaint();
    }

    /**
     * @param mycase: [VueCase] Case où la souris est cliqué
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est cliqué
     * @details - On vérifie si la case cliqué est un chemin, si oui on supprime ce chemin
     */
    public void mouseClickedListener(VueCase mycase) {
        //On vérifie si on clique sur un chemin
        if (mycase.getCase().isWay()) {
            ArrayList<Chemin> listchemin = new ArrayList<>();
            // On ajoute le chemin qui contient la case cliqué à la liste des chemins
            for (int i = 0; i < this.ways.size(); ++i) {
                if (this.ways.get(i).getWay().contains(mycase)) {
                    listchemin.add(this.ways.get(i));
                }
            }
            // On supprime chaque chemin qui contient la case cliqué
            for (Chemin way : listchemin) {
                way.deleteChemin(this.ways);
                this.ways.remove(way);
            }
        }
        repaint();
    }
}