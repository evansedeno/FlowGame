import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file VueControleurMenu.java
 * @brief Class Vue et Controleur du Menu de FlowGame
 * @details Contient toutes les méthodes et attributs pour l'affichage et la gestion des menus
 */
public class VueControleurMenu extends JFrame {

    private static final int PIXEL_PER_SQUARE = 60;
    private static final int SIZE_WINDOWS = 5;
    private final JPanel menuPane = new JPanel();
    private final Font buttonfont = new Font("buttonfont", Font.BOLD, 20);
    private User user;

    /**
     * @author Evan SEDENO
     * @brief Constructeur de la class VueControleurMenu
     * @details - On définit certains paramètres sur le JFrame (fermeture de la fenêtre, taille de la fenêtre et pas redimensionnable)
     * - On appelle la méthode drawUsernameMenu()
     * - On rend le JFrame visible
     */
    public VueControleurMenu() {
        //On définit certains réglages pour le JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE_WINDOWS * PIXEL_PER_SQUARE * 2, SIZE_WINDOWS * PIXEL_PER_SQUARE * 2);
        setResizable(false);

        //On affiche le menu pour demander le nom n'utilisateur
        this.drawUsernameMenu();
        setVisible(true);
    }

    /**
     * @param user: [User] Utilisateur à attribuer à l'attribut user
     * @author Evan SEDENO
     * @brief Mutateur de l'attribut user
     * @details On donne à l'attribut user la valeur de l'User passé en paramètre
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @author Evan SEDENO
     * @brief Envoie dans le JPanel du menu le text avec le nom du projet
     * @details - On ajoute un espace
     * - On créer un nouveau JLabel avec comme text "FLOWGAME"
     * - On définit l'alignement du text en centré
     * - On créer une nouvelle police
     * - On définit la couleur du text en blanc
     * - On définit la police du text avec la police précédemment créée
     * - On ajoute le nouveau text au JPanel du Menu
     */
    public void drawTitle() {
        //On ajoute au JPanel du menu le nom du jeu
        this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel title = new JLabel("FLOWGAME");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Font titlefont = new Font("titlefont", Font.BOLD, 40);
        title.setForeground(Color.white);
        title.setFont(titlefont);
        this.menuPane.add(title);
    }

    /**
     * @param text: [String] Text à afficher dans le JPanel
     * @param size: [int] Taille du text
     * @param pane: [JPanel] JPanel auquel on va ajouter le text
     * @author Evan SEDENO
     * @brief Envoie dans le JPanel du menu le text avec le nom du projet
     * @details - On créer un nouveau JLabel avec comme text passé en paramètre
     * - On définit l'alignement du text en centré
     * - On créer une nouvelle police
     * - On définit la couleur du text en blanc
     * - On définit la police du text avec la police précédemment créée
     * - On ajoute le nouveau text au JPanel passé en paramètre
     */
    public void drawText(String text, int size, JPanel pane) {
        //On créer et on ajoute un JLabel au JPanel passé en paramètre
        JLabel str = new JLabel(text);
        str.setHorizontalAlignment(SwingConstants.CENTER);
        Font textfont = new Font("textfont", Font.BOLD, size);
        str.setForeground(Color.white);
        str.setFont(textfont);
        pane.add(str);
    }

    /**
     * @author Evan SEDENO
     * @brief Définie certains paramètres du JPanel Menu
     * @details - On supprime tous les éléments présents dans le JPanel Menu
     * - On ajoute un espace
     * - On ajoute le titre du jeu
     * - On ajoute un espace
     * - On définit l'alignement en centré
     * - On définit la couleur du fond en gris foncé
     * - On définit la taille du JPanel
     */
    public void setMenuPaneConf() {
        //On définit plusieurs paramètres pour le JPanel du menu
        this.menuPane.removeAll();
        this.menuPane.add(Box.createRigidArea(new Dimension(0, 10)));
        this.drawTitle();
        this.menuPane.add(Box.createRigidArea(new Dimension(0, getHeight() / 4)));
        this.menuPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.menuPane.setBackground(Color.darkGray);
        this.menuPane.setPreferredSize(new Dimension(getWidth() - 30, getHeight() - 30));
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche le menu principal
     * @details - On appelle la méthode setMenuPaneConf pour définir certains paramètres du JPanel
     * - On créer un JPanel et on lui donne certains paramètres (Couleur du fond, taille, disposition, alignement)
     * - On créer 3 boutons (Création d'un niveau, jouer un niveau et afficher les points)
     * - On définit des paramètres aux boutons (Taille, couleur et police)
     * - On créer 3 listeners pour les 3 boutons
     * - On ajoute tout le contenu au JPanel du Menu
     */
    public void drawMainMenu() {
        //On définit les paramètres du JPanel Menu
        this.setMenuPaneConf();

        //On créer le JPanel du menu principal
        JPanel game = new JPanel();
        game.setBackground(Color.darkGray);
        game.setPreferredSize(new Dimension(getWidth() - 80, 100));
        game.setLayout(new GridLayout(1, 2, 50, 50));
        game.setAlignmentX(Component.CENTER_ALIGNMENT);

        //On créer les trois boutons
        JButton creategame = new JButton("CREER");
        JButton playgame = new JButton("JOUER");
        JButton seepoints = new JButton("VOIR LES POINTS");
        playgame.setPreferredSize(new Dimension(getWidth() / 2, 50));
        creategame.setPreferredSize(new Dimension(getWidth() / 2, 50));
        seepoints.setPreferredSize(new Dimension(getWidth() - 80, 50));
        playgame.setBackground(Color.white);
        creategame.setBackground(Color.white);
        seepoints.setBackground(Color.white);
        playgame.setFont(this.buttonfont);
        creategame.setFont(this.buttonfont);
        seepoints.setFont(this.buttonfont);

        //On créer le listener pour la création d'un niveau
        creategame.addActionListener(e -> {
            //On affiche le menu de selection de la taille du niveau
            drawSizesMenu(true);
        });

        //On créer le listener pour jouer un niveau
        playgame.addActionListener(e -> {
            //On affiche le menu de selection de la taille du niveau
            drawSizesMenu(false);
        });

        //On créer le listener pour afficher les points des utilisateurs
        seepoints.addActionListener(e -> {
            //On affiche le menu avec le classement des utilisateurs
            drawPointsMenu();
        });

        //On créer le JPanel avec le bouton pour afficher les points
        JPanel points = new JPanel();
        points.setBackground(Color.darkGray);
        points.add(seepoints);

        //On ajoute chaque élement au JPanel principal
        game.add(creategame);
        game.add(playgame);
        this.menuPane.add(game);
        this.menuPane.add(points);
        setContentPane(this.menuPane);
        repaint();
    }

    /**
     * @param iscreation: [boolean] Variable qui définit si oui ou non on souhaite créer ou jouer un niveau
     * @author Evan SEDENO
     * @brief Affiche le menu de selection de la taille du niveau
     * @details - On appelle la méthode setMenuPaneConf pour définir certains paramètres du JPanel
     * - On créer un JPanel et on lui donne certains paramètres (Couleur du fond, taille, disposition, alignement)
     * - On créer un text pour demander de choisir la taille du niveau
     * - On récupère la liste de toutes les tailles disponible
     * - On affiche un bouton avec un listener pour chaque taille disponible (avec un appel de la méthode appropriée)
     * - On créer un bouton retour
     * - On ajoute tout le contenu au JPanel du Menu
     */
    public void drawSizesMenu(boolean iscreation) {
        //On définit les paramètres du JPanel Menu
        this.setMenuPaneConf();

        //On créer le JPanel du menu de la selection de la taille du niveau
        JPanel sizePane = new JPanel(new GridLayout(1, 1, 30, 30));
        sizePane.setPreferredSize(new Dimension(getWidth() - 100, 50));
        sizePane.setBackground(Color.darkGray);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 0)));
        this.drawText("Choisissez la taille du niveau:", 20, this.menuPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 10)));

        //On récupère toutes les tailles disponibles
        Files file = new Files();
        int[] sizes = file.getAllSizes();

        //On créer un bouton pour chaque taille de niveau
        for (int i : sizes) {
            JButton button = new JButton(String.valueOf(i));
            button.setPreferredSize(new Dimension(30, 10));
            button.setBackground(Color.white);
            button.setBorderPainted(false);
            button.setFont(this.buttonfont);

            //On créer le listener pour la taille du niveau
            button.addActionListener(e -> {
                //En fonction de si on créer un niveau ou non, on affiche le bon menu
                if (iscreation) {
                    //On créer une instanciation de jeu avec les paramètres voulus (création de niveau)
                    new Jeu(i, 0, user);
                    //On supprime la fenêtre actuelle
                    menuPane.removeAll();
                    setVisible(false);
                    dispose();
                } else {
                    drawLevelsMenu(i);
                }
            });
            sizePane.add(button);
        }

        //On ajoute le bouton retour et chaque élement
        JButton back = this.drawReturnButton();
        back.addActionListener(e -> drawMainMenu());
        this.menuPane.add(sizePane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 50)));
        this.menuPane.add(back);
        setContentPane(this.menuPane);
        repaint();
    }

    /**
     * @param size: [int] Taille des niveaux
     * @author Evan SEDENO
     * @brief Affiche le menu de selection du niveau
     * @details - On appelle la méthode setMenuPaneConf pour définir certains paramètres du JPanel
     * - On créer un JPanel et on lui donne certains paramètres (Couleur du fond, taille, disposition, alignement)
     * - On créer un text pour demander de choisir la taille du niveau
     * - On récupère la liste de tous les niveaux disponible
     * - On affiche un bouton avec un listener pour chaque niveau disponible (avec une instanciation de la class Jeu)
     * - On créer un bouton retour
     * - On ajoute tout le contenu au JPanel du Menu
     */
    public void drawLevelsMenu(int size) {
        //On définit les paramètres du JPanel Menu
        this.setMenuPaneConf();

        //On créer le JPanel du menu de la selection du niveau
        JPanel levelPane = new JPanel(new GridLayout(1, 1, 30, 30));
        levelPane.setPreferredSize(new Dimension(getWidth() - 100, 50));
        levelPane.setBackground(Color.darkGray);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 0)));
        this.drawText("Choisisez votre niveau:", 20, this.menuPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 10)));

        //On récupère tout les niveaux disponibles
        Files file = new Files();
        int[] levels = file.getAllLevels(size);

        //On créer un bouton pour chaque niveau
        for (int i : levels) {
            JButton button = new JButton(String.valueOf(i));
            button.setPreferredSize(new Dimension(30, 10));
            button.setBackground(Color.white);
            button.setBorderPainted(false);
            button.setFont(this.buttonfont);

            //On créer le listener pour le niveau
            button.addActionListener(e -> {
                //On créer une instanciation de jeu avec les paramètres voulus
                new Jeu(size, i, user);

                //On supprime la fenêtre actuelle
                menuPane.removeAll();
                setVisible(false);
                dispose();
            });
            levelPane.add(button);
        }

        //On ajoute le bouton retour et chaque élement
        JButton back = this.drawReturnButton();
        back.addActionListener(e -> drawSizesMenu(false));
        this.menuPane.add(levelPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 50)));
        this.menuPane.add(back);
        setContentPane(this.menuPane);
        repaint();
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche le menu du classement des utilisateurs
     * @details - On appelle la méthode setMenuPaneConf pour définir certains paramètres du JPanel
     * - On récupère le nombre d'utilisateurs
     * - On créer un JPanel et on lui donne certains paramètres (Couleur du fond, taille, disposition, alignement)
     * - On créer un text pour montrer les points de joueurs
     * - On récupère la liste de tous les joueurs et on l'a tri par ordre décroissant des points
     * - On créer un JPanel pour chaque joueur avec son nom et son nombre de points
     * - On créer un bouton retour
     * - On ajoute tout le contenu au JPanel du Menu
     */
    public void drawPointsMenu() {
        //On définit les paramètres du JPanel Menu
        this.setMenuPaneConf();

        //On récupère le nombre d'utilisateurs
        Files files = new Files();
        int nbuser = files.getMaxId();

        //On créer le JPanel du menu de l'affichage du classement des utilisateurs
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 0)));
        this.drawText("Points des joueurs:", 20, this.menuPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 10)));
        JPanel pointsPane = new JPanel(new GridLayout(nbuser / 4, 4, 30, 30));
        pointsPane.setPreferredSize(new Dimension(getWidth() - 100, 50));
        pointsPane.setBackground(Color.darkGray);
        pointsPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        Font userfont = new Font("textfont", Font.PLAIN, 15);
        Font pointfont = new Font("textfont", Font.BOLD, 20);

        //On récupère la liste des joueurs
        ArrayList<User> users = files.getAllUsers();
        Collections.sort(users);
        Collections.reverse(users);

        //On créer un JPanel a chaque utilisateur et on y met leurs informations
        for (User value : users) {
            JPanel user = new JPanel(new GridLayout(2, 1));
            user.setBackground(Color.white);
            JLabel usernametext = new JLabel(value.getUsername());
            JLabel pointstext = new JLabel(String.valueOf(value.getPoints()));
            usernametext.setHorizontalAlignment(JLabel.CENTER);
            pointstext.setHorizontalAlignment(JLabel.CENTER);
            usernametext.setFont(userfont);
            pointstext.setFont(pointfont);
            user.add(usernametext);
            user.add(pointstext);
            pointsPane.add(user);
        }

        //On ajoute le bouton retour et chaque élement
        JButton back = this.drawReturnButton();
        back.addActionListener(e -> drawMainMenu());
        this.menuPane.add(pointsPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 50)));
        this.menuPane.add(back);
        repaint();
        setContentPane(this.menuPane);
    }

    /**
     * @author Evan SEDENO
     * @brief Affiche le menu de selection du nom d'utilisateur
     * @details - On appelle la méthode setMenuPaneConf pour définir certains paramètres du JPanel
     * - On créer un JPanel et on lui donne certains paramètres (Couleur du fond et disposition)
     * - On créer un text pour demander de choisir le nom d'utilisateur
     * - On créer un champ de text et lui donne certains paramètres (Couleur du fond taille et police)
     * - On créer un bouton valider
     * - On créer un listener pour le bouton valider (avec l'attribution de l'attribut user par le nom d'utilisateur et un appel de la méthode drawMainMenu())
     * - On ajoute tout le contenu au JPanel du Menu
     */
    public void drawUsernameMenu() {
        //On définit les paramètres du JPanel Menu
        this.setMenuPaneConf();

        //On créer le JPanel username
        JPanel username = new JPanel();
        BoxLayout boxlayout = new BoxLayout(username, BoxLayout.Y_AXIS);
        username.setLayout(boxlayout);
        username.setBackground(Color.darkGray);
        this.menuPane.add(Box.createRigidArea(new Dimension(0, getHeight() / 4)));

        //On affiche le text suivant
        this.drawText("Entrez votre nom d'utilisteur:", 20, username);

        //On créer un input JTextField pour le nom d'utilisateur
        JTextField usernamefield = new JTextField();
        usernamefield.setFont(this.buttonfont);
        usernamefield.setPreferredSize(new Dimension(getWidth() / 2, 30));
        usernamefield.setBackground(Color.WHITE);

        //On affiche les differents elements
        username.add(Box.createRigidArea(new Dimension(0, 10)));
        username.add(usernamefield);
        username.add(Box.createRigidArea(new Dimension(0, 20)));

        //On créer le bouton valider
        JButton validate = new JButton("VALIDER");
        validate.setBackground(Color.white);
        validate.setBorderPainted(false);
        validate.setFont(this.buttonfont);

        //On créer le listener sur le bouton valider
        Files files = new Files();
        validate.addActionListener(e -> {
            String userstr = usernamefield.getText();

            //On oblige l'utilisateur à entrer des caractères
            if (!userstr.equals("")) {
                //On récupère l'utilisateur par son nom dans le fichier des utilisateurs
                setUser(files.getUserByUsername(userstr));
                menuPane.removeAll();

                //On affiche le menu principal
                drawMainMenu();
            }
        });

        //On ajoute le bouton valider et on affiche le JPanel username
        username.add(validate);
        this.menuPane.add(username);
        setContentPane(this.menuPane);
        repaint();
    }

    /**
     * @return back: [JButton] On retourne le JButton retour
     * @author Evan SEDENO
     * @brief Affiche le menu de selection du nom d'utilisateur
     * @details - On créer un JButton avec le text "RETOUR"
     * - On donne certains paramètres au bouton (Taille, couleur du fond, bordures et police)
     * - On retourne le JButton
     */
    public JButton drawReturnButton() {
        //On renvoie un bouton retour
        JButton back = new JButton("RETOUR");
        back.setPreferredSize(new Dimension(getWidth() / 4, 30));
        back.setBackground(Color.white);
        back.setBorderPainted(false);
        back.setFont(this.buttonfont);
        return back;
    }
}
