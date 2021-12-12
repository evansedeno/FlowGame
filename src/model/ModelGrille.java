package model;

import utilitaires.CaseType;
import utilitaires.Files;
import vuecontroleur.VueCase;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file model.ModelGrille.java
 * @brief Class Model de la grille d'un jeu
 * @details Contient toutes les méthodes et attributs pour la gestion d'une grille de jeu
 */
public class ModelGrille extends Observable {

    private final VueCase[][] tabCV;
    private final int size;
    private int level;
    private final boolean iscreation;
    private final List<Chemin> ways = new ArrayList<>();
    private boolean clicked;
    private final User user;

    /**
     * @param size:  [int] Taille du niveau
     * @param level: [int] Identifiant du niveau
     * @param user:  [model.User] Utilisateur du niveau
     * @author Evan SEDENO
     * @brief Constructeur de la class model.ModelGrille
     * @details - On assigne la valeur de chaque paramètre à chaque attribut
     * @details - On instancie une nouvelle matrice de vuecontroleur.VueCase avec comme taille la taille passée en paramètre
     */
    public ModelGrille(int size, int level, User user) {
        this.size = size;
        this.level = level;
        this.user = user;
        this.tabCV = new VueCase[size][size];
        this.iscreation = level == 0;
        if (this.iscreation) {
            this.setNewLevel();
            this.generateEmptyGrille();
        } else {
            this.generateExistingGrille();
        }
        setChanged();
        notifyObservers();
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
     * @return this.tabCV[y][x]: [vuecontroleur.VueCase] Objet vuecontroleur.VueCase récupéré en fonction de son emplacement x et y
     * @author Evan SEDENO
     * @brief Accesseur spécial de l'attribut tabCV en fonction d'une position
     * @details - On renvoie l'objet vuecontroleur.VueCase qui correspond à la position x et y dans la matrice tabCV
     */
    public VueCase getVueCase(int x, int y) {
        return this.tabCV[y][x];
    }

    /**
     * @author Evan SEDENO
     * @brief Mutateur special de l'attribut level
     * @details - On assigne à l'attribut level le prochain niveau disponible
     */
    public void setNewLevel() {
        Files file = new Files();
        this.level = file.getNextLevel(size);
    }

    /**
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut iscreation
     * @details - On retourne la valeur de l'attribut iscreation pour voir si on créer ou on joue un niveau
     */
    public boolean isCreation() {
        return this.iscreation;
    }

    /**
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut user
     * @details - On retourne la valeur de l'attribut user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut level
     * @details - On retourne la valeur de l'attribut level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * @author Evan SEDENO
     * @brief Genre un nouveau niveau par rapport à la grille
     * @details - On appelle la méthode writeLevelFile de la class utilitaires.Files pour générer un fichier avec le niveau de la grille actuel
     */
    public void generateNewLevel() {
        Files file = new Files();
        file.writeLevelFile(this);
    }

    /**
     * @author Evan SEDENO
     * @brief Remplis la grille en fonction du niveau demandé
     * @details - Pour chaque case de la matrice tabCV, on créer un objet vuecontroleur.VueCase
     * @details - On récupère le type de la case grâce à la méthode getTypeInFile de la class utilitaires.Files et on l'assigne à l'objet vuecontroleur.VueCase
     */
    public void generateExistingGrille() {
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
     * @details - Pour chaque case de la matrice tabCV, on créer un objet vuecontroleur.VueCase avec un type vide
     */
    public void generateEmptyGrille() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, CaseType.empty);
            }
        }
    }

    /**
     * @return true/false: [boolean] On retourne vrai si la grille n'a plus de case vide et que tous les chemins sont complets sinon on retourne faux
     * @author Evan SEDENO
     * @brief Vérifie s'il reste des cases vides et que les chemins sont complets
     * @details - Pour chaque case de la matrice tabCV, on vérifie si le type de la case est vide
     * @details - Si une case est vide on retourne faux
     * - Pour chaque chemin, on vérifie si tous les chemins sont complets
     * - Si un chemin est incomplet on retourne faux sinon on retourne vrai
     */
    public boolean isFinished() {
        //On vérifie que toutes les cases ne sont pas vide
        for (int i = 0; i < this.getSize(); ++i) {
            for (int j = 0; j < this.getSize(); ++j) {
                if (this.getVueCase(i, j).getCase().getType() == CaseType.empty) {
                    return false;
                }
            }
        }

        //Si toutes les cases sont pleines alors on vérifie si tous les chemins sont complets
        for (Chemin way : ways) {
            if (way.getStop() == null) {
                return false;
            }
        }

        //Si tous les chemins sont complets alors on rajoute les points au joueur et on termine le jeu
        this.user.addPoints(this.size);
        return true;
    }

    /**
     * @param mycase: [vuecontroleur.VueCase] Case où la souris est appuyé
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est appuyé
     * @details - On vérifie si on créer ou on joue
     * @details - Si c'est une creation alors on appelle la méthode setNextNumber sur la case
     * @details - Si c'est un jeu alors on vérifie si la case est bloqué, si oui on créer un nouveau chemin
     * @details - On notifie l'Observer
     */
    public void pressedEvent(VueCase mycase) {
        if (mycase.getCase().isLocked()) {

            //On récupère la liste de chaque debut et chaque fin de chaque chemin complet
            List<VueCase> lockedcases = new ArrayList<>();
            for (Chemin chemin : ways) {
                if (chemin.getStart() != null && chemin.getStart() != null) {
                    lockedcases.add(chemin.getStart());
                    lockedcases.add(chemin.getStop());
                }
            }

            //On vérifie si la case pressée n'appartient pas déjà à un chemin existant
            if (!lockedcases.contains(mycase)) {
                Chemin way = new Chemin(mycase);
                way.getStart().setStarter(way.getStart());
                ways.add(way);
                clicked = true;
            } else {
                clicked = false;
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * @param mycase: [vuecontroleur.VueCase] Case où la souris est cliqué
     * @author Evan SEDENO
     * @brief Effectue les vérifications pour supprimer un chemin ou changer de type de case lors d'une création
     * @details - On vérifie la grille est une création ou non, si oui on change de type la case cliqué
     * @details - On vérifie le chemin à supprimer, s'il est supprimable alors le supprime
     * @details - On notifie l'Observer
     */
    public void clickedEvent(VueCase mycase) {
        //On vérifie si la grille est une création
        if (this.iscreation) {
            //On choisit la prochaine case à placer
            mycase.getCase().setNextNumber();
        } else if (ways.size() > 0) {
            //On vérifie si on clique sur un chemin
            if (mycase.getCase().isWay()) {
                ArrayList<Chemin> listchemin = new ArrayList<>();
                // On ajoute le chemin qui contient la case cliqué à la liste des chemins
                for (int i = 0; i < ways.size(); ++i) {
                    if (ways.get(i).getWay().contains(mycase)) {
                        listchemin.add(ways.get(i));
                    }
                }
                // On supprime chaque chemin qui contient la case cliqué
                for (Chemin way : listchemin) {
                    way.deleteChemin(ways);
                    ways.remove(way);
                }
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * @param mycase: [vuecontroleur.VueCase] Case où la souris est entré
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est entré
     * @details - On vérifie si on a le bouton de la souris pressé et qu'il existe au moins un chemin
     * @details - On vérifie si la case est bloqué si oui on appelle la méthode pour relâcher la souris
     * @details - On vérifie si la case est un tournant ou que la case est déjà enregistré dans le dernier chemin, si oui alors on appelle la méthode pour relâcher la souris
     * @details - On vérifie si la case est vide ou que c'est un chemin, si non on appelle la méthode pour relâcher la souris
     * @details - On vérifie si la case est apte à être dessiné, si oui on appelle la méthode pour la dessiner, si non on appelle la méthode pour relâcher la souris
     * @details - On notifie l'Observer
     */
    public void enteredEvent(VueCase mycase) {
        if (clicked && ways.size() > 0) {
            //On vérifie si on traverse une case bloquée autre que celle appartenant au chemin
            if (!mycase.getCase().isLocked()) {
                if (mycase.getCase().isTurn() || ways.get(ways.size() - 1).getWayWithoutStartAndStop().contains(mycase)) {
                    releasedEvent(mycase);
                } else if (mycase.getCase().getType() == CaseType.empty || mycase.getCase().isWay()) {
                    if ((mycase.getCase().isWay()
                            && !mycase.getCase().isSameDirection(ways.get(ways.size() - 1).getWay().get(ways.get(ways.size() - 1).getWay().size() - 1).getCase().getType()))
                            || mycase.getCase().getType() == CaseType.empty
                            && ways.get(ways.size() - 1).checkGoodChemin()) {
                        //On ajoute la case au chemin
                        ways.get(ways.size() - 1).addCase(mycase);
                        //On ajoute à la case actuelle, la case du début du chemin
                        mycase.setStarter(ways.get(ways.size() - 1).getStart());
                        //On redessine les cases
                        if (ways.get(ways.size() - 1).checkGoodChemin()) {
                            ways.get(ways.size() - 1).assignType(ways);
                        }
                    } else {
                        releasedEvent(mycase);
                    }
                } else {
                    releasedEvent(mycase);
                }
            } else {
                releasedEvent(mycase);
            }
        }
        setChanged();
        notifyObservers();
    }

    /**
     * @param mycase: [vuecontroleur.VueCase] Case où la souris est relâché
     * @author Evan SEDENO
     * @brief Créer le listener quand la souris est relâché
     * @details - On vérifie s'il y a au moins un chemin, si la case est bloquée et que la souris est pressé
     * @details - On vérifie le type de la case, si c'est le même type que la case départ du chemin alors on termine le chemin, sinon on supprime le chemin
     * @details - On vérifie si l'on a fini le niveau
     * @details - On vérifie que le dernier chemin est valide
     * @details - On notifie l'Observer
     */
    public void releasedEvent(VueCase mycase) {

        if (ways.size() > 0 && mycase.getCase().isLocked() && clicked) {
            //On vérifie si la souris est bien lâché sur une case bloquée
            if (mycase.getCase().getType() == ways.get(ways.size() - 1).getStart().getCase().getType() && mycase != ways.get(ways.size() - 1).getStart()) {
                //On vérifie si la case STOP est valide
                ways.get(ways.size() - 1).setStop(mycase);
                //On vérifie si toutes les cases ont été remplis
            } else {
                ways.get(ways.size() - 1).deleteChemin(ways);
                ways.remove(ways.get(ways.size() - 1));
            }
        } else if (ways.size() > 0 && !mycase.getCase().isLocked() && clicked) {
            ways.get(ways.size() - 1).deleteChemin(ways);
            ways.remove(ways.get(ways.size() - 1));
        }
        //On vérifie que le dernier chemin est valide
        if (ways.size() > 0) {
            if (!ways.get(ways.size() - 1).checkGoodChemin()) {
                ways.get(ways.size() - 1).deleteChemin(ways);
                ways.remove(ways.get(ways.size() - 1));
            } else {
                ways.get(ways.size() - 1).assignType(ways);
            }
        }
        this.clicked = false;
        setChanged();
        notifyObservers();
    }
}