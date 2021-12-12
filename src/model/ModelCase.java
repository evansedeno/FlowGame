package model;

import utilitaires.CaseType;

import static utilitaires.CaseType.*;

/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file model.ModelCase.java
 * @brief Class Model pour la gestion des cases du jeu
 * @details Contient toutes les méthodes et attributs pour la gestion des cases du jeu
 */
public class ModelCase {

    private final int x, y;
    private CaseType type;
    private final boolean locked;

    /**
     * @param x:    [int] Position x de la case
     * @param y:    [int] Position y de la case
     * @param type: [utilitaires.CaseType] Type de la casse
     * @author Evan SEDENO
     * @brief Constructeur de la class model.ModelCase
     * @details - On définit les valeurs des attributs x, y et type avec les trois paramètres
     * @details - Si le type de la case est n'est pas vide alors on regarde si son type correspond à une couleur, si oui on l'a bloque
     */
    public ModelCase(int x, int y, CaseType type) {
        this.x = x;
        this.y = y;
        this.type = type;

        //Si la case n'est pas vide alors on l'a bloque
        this.locked = type != empty;
    }

    /**
     * @return this.locked: [boolean] Retourne vrai si la case est bloqué, faux si non
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut locked
     * @details - On renvoie la valeur de l'attribut locked
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @param ct: [utilitaires.CaseType] Type de la case à définir
     * @author Evan SEDENO
     * @brief Mutateur de l'attribut type
     * @details - On assigne à l'attribut type la valeur de la variable ct en paramètre
     */
    public void setType(CaseType ct) {
        this.type = ct;
    }

    /**
     * @author Evan SEDENO
     * @brief Change le type de la case lors de la création d'un niveau
     * @details - On vérifie le type de la case et on assigne à l'attribut type la prochaine valeur avec un switch case
     */
    public void setNextNumber() {
        //On change le type de la case en fonction de sa valeur actuelle
        switch (this.type) {
            case empty:
                this.type = S1;
                break;
            case S1:
                this.type = S2;
                break;
            case S2:
                this.type = S3;
                break;
            case S3:
                this.type = S4;
                break;
            case S4:
                this.type = S5;
                break;
            case S5:
                this.type = empty;
                break;
        }
    }

    /**
     * @return this.type: [utilitaires.CaseType] Retourne le type de la case
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut type
     * @details - On renvoie le type de la case
     */
    public CaseType getType() {
        return this.type;
    }

    /**
     * @return [boolean] Retourne vrai si le type de la case est un tournant
     * @author Evan SEDENO
     * @brief Vérifie si la case est un tournant
     * @details - On renvoie vrai si le type de la case est un tournant
     */
    public boolean isTurn() {
        return this.type == h0v0
                || this.type == h0v1
                || this.type == h1v0
                || this.type == h1v1
                || this.type == cross;
    }

    /**
     * @return [boolean] Retourne vrai si le type de la case est un chemin
     * @author Evan SEDENO
     * @brief Vérifie si la case est un chemin
     * @details - On renvoie vrai si le type de la case est un chemin
     */
    public boolean isWay() {
        return this.type == h0v0
                || this.type == h0h1
                || this.type == h1v0
                || this.type == h1v1
                || this.type == h0v1
                || this.type == v0v1
                || this.type == cross;
    }

    /**
     * @return [boolean] Retourne vrai si le type en paramètre est dans la même direction que la case actuelle
     * @author Evan SEDENO
     * @brief Vérifie si le type en paramètre est dans la même direction que la case actuelle
     * @details - On renvoie vrai si le type de la case actuelle est une direction et que le type en paramètre est un croisement
     */
    public boolean isSameDirection(CaseType type) {
        return (type == cross && this.type == v0v1) || (type == cross && this.type == h0h1);
    }

    /**
     * @return this.x: [int] Retourne la position x de la case
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut x
     * @details - On renvoie la position x de la case
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return this.y: [int] Retourne la position y de la case
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut y
     * @details - On renvoie la position y de la case
     */
    public int getY() {
        return this.y;
    }

    /**
     * @return "X: " + this.x + ", Y: " + this.y: [String] Chaine de caractère avec la position de la case
     * @author Evan SEDENO
     * @brief Retourne en chaine de caractère la position de la case
     * @details - On retourne en chaine de caractère la position de la case en fonction de x et y
     */
    public String toString() {
        return "X: " + this.x + ", Y: " + this.y;
    }

    /**
     * @return x == modelCase.x && y == modelCase.y && type == modelCase.type [boolean] Valeur booléenne de la différence avec un autre objet de la classe model.ModelCase
     * @author Evan SEDENO
     * @brief Compare deux objets de type model.ModelCase
     * @details - On retourne vrai si l'adresse mémoire est la même pour les deux objets
     * @details - On retourne faux si l'objet n'est pas de la même classe
     * @details - On retourne vrais si les attributs des deux objets correspondent
     * @see "https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html"
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelCase modelCase = (ModelCase) o;
        return x == modelCase.x && y == modelCase.y && type == modelCase.type;
    }
}