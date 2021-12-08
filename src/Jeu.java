import java.io.*;

/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file Jeu.java
 * @brief Class Jeu de FlowGame
 * @details Contient toutes les méthodes et attributs pour la gestion d'un jeu
 */
public class Jeu {
    private final int size;
    private final int level;
    private final User user;

    /**
     * @param size:  [int] Taille du niveau
     * @param level: [int] Identifiant du niveau
     * @param user:  [User] Utilisateur du niveau
     * @author Evan SEDENO
     * @brief Constructeur de la class Jeu
     * @details - On assigne la valeur de chaque paramètre à chaque attribut
     * @details - On vérifie si l'on créer ou on joue un niveau
     */
    public Jeu(int size, int level, User user) {
        this.size = size;
        this.level = level;
        this.user = user;

        //Si l'identifiant du niveau vaut 0 alors on lance une création de niveau sinon on joue le niveau avec le bon identifiant
        if (this.level == 0) {
            try {
                this.createGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            this.startGame();
        }
    }

    /**
     * @author Evan SEDENO
     * @brief Création d'une instance de la classe VueControleurGrille pour la création d'un niveau
     * @details - On créer une instance de la classe VueControleurGrille pour la création d'un niveau avec les paramètres nécessaires
     */
    public void createGame() throws IOException {
        new VueControleurGrille(this.size, 0, this.user);
    }

    /**
     * @author Evan SEDENO
     * @brief Création d'une instance de la classe VueControleurGrille pour jouer un niveau
     * @details - On créer une instance de la classe VueControleurGrille pour jouer un niveau avec les paramètres nécessaires
     */
    public void startGame() {
        new VueControleurGrille(this.size, this.level, this.user);
    }
}