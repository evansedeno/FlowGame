/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file ModelGrille.java
 * @brief Class Model de la grille d'un jeu
 * @details Contient toutes les méthodes et attributs pour la gestion d'une grille de jeu
 */
public class ModelGrille {

    private final VueCase[][] tabCV;
    private final int size;
    private final int level;

    /**
     * @param size:  [int] Taille du niveau
     * @param level: [int] Identifiant du niveau
     * @author Evan SEDENO
     * @brief Constructeur de la class ModelGrille
     * @details - On assigne la valeur de chaque paramètre à chaque attribut
     * @details - On instancie une nouvelle matrice de VueCase avec comme taille la taille passée en paramètre
     */
    public ModelGrille(int size, int level) {
        this.size = size;
        this.level = level;
        this.tabCV = new VueCase[size][size];
    }

    /**
     * @return this.size: [int] Taille du niveau
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut size
     * @details - On renvoie la valeur de la taille de la grille
     */
    public int getSize() {
        return this.size;
    }

    /**
     * @return this.tabCV[y][x]: [VueCase] Objet VueCase récupéré en fonction de son emplacement x et y
     * @author Evan SEDENO
     * @brief Accesseur spécial de l'attribut tabCV en fonction d'une position
     * @details - On renvoie l'objet VueCase qui correspond à la position x et y dans la matrice tabCV
     */
    public VueCase getVueCase(int x, int y) {
        return this.tabCV[y][x];
    }

    /**
     * @author Evan SEDENO
     * @brief Remplis la grille en fonction du niveau demandé
     * @details - Pour chaque case de la matrice tabCV, on créer un objet VueCase
     * @details - On récupère le type de la case grâce à la méthode getTypeInFile de la class Files et on l'assigne à l'objet VueCase
     */
    public void generateGrille() {
        Files file = new Files();
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, file.getTypeInFile(this.size, this.level, i, j));
            }
        }
    }

    /**
     * @author Evan SEDENO
     * @brief Remplis la grille de cases vide
     * @details - Pour chaque case de la matrice tabCV, on créer un objet VueCase avec un type vide
     */
    public void generateEmptyGrille() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, CaseType.empty);
            }
        }
    }

    /**
     * @return true/false: [boolean] On retourne vrai si la grille n'a plus de case vide sinon on retourne faux
     * @author Evan SEDENO
     * @brief Vérifie s'il reste des cases vides
     * @details - Pour chaque case de la matrice tabCV, on vérifie si le type de la case est vide
     * @details - Si une case est vide on retourne faux sinon on retourne vrai
     */
    public boolean validateGrille() {
        for (int i = 0; i < this.getSize(); ++i) {
            for (int j = 0; j < this.getSize(); ++j) {
                if (this.getVueCase(i, j).getCase().getType() == CaseType.empty) {
                    return false;
                }
            }
        }
        return true;
    }
}