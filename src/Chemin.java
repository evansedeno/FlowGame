import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file Chemin.java
 * @brief Class
 * @details Contient toutes les méthodes et attributs pour la gestion et l'affichage d'une grille
 */
public class Chemin {

    private VueCase start = null;
    private VueCase stop = null;
    private final List<VueCase> way = new ArrayList<>();

    /**
     * @param start: [VueCase] Case départ du chemin
     * @author Evan SEDENO
     * @brief Constructeur de la classe Chemin
     * @details - On assigne la VueCase passé en paramètre à l'attribut start
     */
    public Chemin(VueCase start) {
        this.setStart(start);
    }

    /**
     * @return this.start: [VueCase] Attribut start
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut start
     * @details - On renvoie la valeur de l'attribut start
     */
    public VueCase getStart() {
        return this.start;
    }

    /**
     * @return this.stop: [VueCase] Attribut stop
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut stop
     * @details - On renvoie la valeur de l'attribut stop
     */
    public VueCase getStop() {
        return this.stop;
    }

    /**
     * @return this.way: [List<VueCase>] Attribut way
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut way
     * @details - On renvoie la valeur de l'attribut way
     */
    public List<VueCase> getWay() {
        return this.way;
    }

    /**
     * @return this.way: [List<VueCase>] Attribut way
     * @author Evan SEDENO
     * @brief Accesseur spécifique de l'attribut way
     * @details - On renvoie la liste de VueCase de l'attribut way sans le depart et l'arrivée
     */
    public List<VueCase> getWayWithoutStartAndStop() {
        //On créer une nouvelle liste de VueCase
        List<VueCase> chemin = new ArrayList<>(this.way);
        //On retire le départ
        chemin.remove(0);
        //Si le chemin est terminé alors on retire l'arrivée
        if (this.getStop() != null) chemin.remove(chemin.size() - 1);
        return chemin;
    }

    /**
     * @param stop: [VueCase] Valeur de la case d'arrêt du chemin
     * @author Evan SEDENO
     * @brief Mutateur de l'attribut stop
     * @details - On assigne la valeur du paramètre stop à l'attribut stop
     * @details - On rajoute à la liste des VueCase du chemin la VueCase stop en paramètre
     */
    public void setStop(VueCase stop) {
        this.stop = stop;
        this.addCase(stop);
    }

    /**
     * @param start: [VueCase] Valeur de la case de depart du chemin
     * @author Evan SEDENO
     * @brief Mutateur de l'attribut start
     * @details - On assigne la valeur du paramètre stop à l'attribut start
     * @details - On ajoute la case à la liste des cases du chemin
     * @details - On appelle la methode de la VueCase pour assigner un depart avec la VueCase passé en paramètre le start
     */
    public void setStart(VueCase start) {
        this.start = start;
        this.addCase(this.start);
        start.setStarter(start);
    }

    /**
     * @param newcase: [VueCase] Valeur d'une case du chemin
     * @author Evan SEDENO
     * @brief Mutateur spécifique de l'attribut way
     * @details - On rajoute à l'attribut way la nouvelle VueCase passé en paramètre
     */
    public void addCase(VueCase newcase) {
        this.way.add(newcase);
    }

    /**
     * @param chemins: [List<Chemin>] Liste de l'ensemble des chemins
     * @author Evan SEDENO
     * @brief Appel des 3 méthodes pour la construction du chemin
     * @details - On appelle la méthode pour faire les directions
     * @details - On appelle la méthode pour faire les tournants
     * @details - On appelle la méthode pour faire les croisements
     */
    public void assignType(List<Chemin> chemins) {
        this.makeDirections();
        this.makeTurns();
        this.makeCross(chemins);
    }

    /**
     * @author Evan SEDENO
     * @brief Création des tournants par appel des 4 méthodes pour les 4 directions
     * @details - Pour chaque case du chemin on récupère la case précédente, la case actuelle et la suivante
     * @details - On appelle les 4 méthodes pour faire les 4 types tournants
     * @details - On redessine la case actuelle
     */
    public void makeTurns() {
        //Pour chaque case du chemin on dessine le bon tournant avec l'appelle des 4 méthodes ci-dessous
        for (int i = 2; i < this.getWay().size(); ++i) {
            VueCase firstcase = this.getWay().get(i - 2);
            VueCase drawcase = this.getWay().get(i - 1);
            VueCase lastcase = this.getWay().get(i);
            this.makeH0V0(firstcase, drawcase, lastcase);
            this.makeH0V1(firstcase, drawcase, lastcase);
            this.makeH1V0(firstcase, drawcase, lastcase);
            this.makeH1V1(firstcase, drawcase, lastcase);
            //On actualise la case i
            drawcase.update();
        }
    }

    /**
     * @param chemins: [List<Chemin>] Liste de tous les chemins
     * @author Evan SEDENO
     * @brief Création des croisements
     * @details - On retire le chemin actuel de la liste des chemins
     * @details - Pour chaque case de chaque chemin de la liste passé en paramètre on vérifie si le chemin actuel contient une des cases
     * @details - On appelle la méthode pour dessiner le croisement
     * @details - On rajoute le chemin actuel dans la liste des chemins
     */
    public void makeCross(List<Chemin> chemins) {
        //On retire le chemin actuel de la liste des chemins
        chemins.remove(this);
        //On vérifie qu'il y ait plus de 1 chemin
        if (chemins.size() > 0) {
            //On parcourt chaque chemin
            for (Chemin way : chemins) {
                //On parcourt chaque case du chemin
                for (VueCase commoncase : way.getWay()) {
                    //Si le chemin actuel contient une case du chemin parcourut alors on appelle la méthode pour dessiner le croisement
                    if (this.getWay().contains(commoncase) && !commoncase.getCase().isLocked()) {
                        commoncase.sendCrossToDraw(way);
                    }
                }
            }
        }
        //On ajoute le chemin actuel de la liste des chemins
        chemins.add(this);
    }

    /**
     * @author Evan SEDENO
     * @brief Création des directions
     * @details - Pour chaque case du chemin, on récupère la case précédente et celle à dessiner
     * @details - On vérifie les conditions en fonction de la position de la case précédente pour dessiner la direction
     * @details - On actualise la case
     */
    public void makeDirections() {
        //On parcourt l'ensemble des cases du chemin
        for (int i = 1; i < this.getWay().size(); ++i) {

            //On récupère la case actuelle et la précédente
            VueCase firstcase = this.getWay().get(i - 1);
            VueCase drawcase = this.getWay().get(i);

            //On vérifie la position de la case actuelle par rapport à la précédente et on assigne le bon type de direction
            if (drawcase != this.getStop()) {
                if (firstcase.getCase().getX() > drawcase.getCase().getX()
                        && firstcase.getCase().getY() == drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.h0h1);
                } else if (firstcase.getCase().getX() < drawcase.getCase().getX()
                        && firstcase.getCase().getY() == drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.h0h1);
                } else if (firstcase.getCase().getX() == drawcase.getCase().getX()
                        && firstcase.getCase().getY() > drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.v0v1);
                } else if (firstcase.getCase().getX() == drawcase.getCase().getX()
                        && firstcase.getCase().getY() < drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.v0v1);
                }
            }
            //On redessine la case actuelle
            drawcase.update();
        }
    }

    /**
     * @param firstcase: [VueCase] Case avant la case à dessiner
     * @param drawcase:  [VueCase] Case à dessiner
     * @param lastcase:  [VueCase] Case après la case à dessiner
     * @author Evan SEDENO
     * @brief Création du tournant (Nord-Ouest)
     * @details - On fonction de la position et du type des 3 cases passé en paramètre on assigne le nouveau type à la case à dessiner
     */
    public void makeH0V0(VueCase firstcase, VueCase drawcase, VueCase lastcase) {
        //On vérifie la position et le type des 3 cases passé en paramètre on assigne le nouveau type à la case à dessiner
        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);

        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)) {
            drawcase.getCase().setType(CaseType.h0v0);
        }

        //Turns on normals
        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        }
        drawcase.update();
    }

    /**
     * /**
     *
     * @param firstcase: [VueCase] Case avant la case à dessiner
     * @param drawcase:  [VueCase] Case à dessiner
     * @param lastcase:  [VueCase] Case après la case à dessiner
     * @author Evan SEDENO
     * @brief Création du tournant (Sud-Ouest)
     * @details - On fonction de la position et du type des 3 cases passé en paramètre on assigne le nouveau type à la case à dessiner
     */
    public void makeH0V1(VueCase firstcase, VueCase drawcase, VueCase lastcase) {
        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)) {
            drawcase.getCase().setType(CaseType.h0v1);
        }

        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h1v0
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        }
        drawcase.update();
    }

    /**
     * @param firstcase: [VueCase] Case avant la case à dessiner
     * @param drawcase:  [VueCase] Case à dessiner
     * @param lastcase:  [VueCase] Case après la case à dessiner
     * @author Evan SEDENO
     * @brief Création du tournant (Nord-Est)
     * @details - On fonction de la position et du type des 3 cases passé en paramètre on assigne le nouveau type à la case à dessiner
     */
    public void makeH1V0(VueCase firstcase, VueCase drawcase, VueCase lastcase) {

        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)) {
            drawcase.getCase().setType(CaseType.h1v0);
        }


        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.h0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        }


        drawcase.update();
    }

    /**
     * @param firstcase: [VueCase] Case avant la case à dessiner
     * @param drawcase:  [VueCase] Case à dessiner
     * @param lastcase:  [VueCase] Case après la case à dessiner
     * @author Evan SEDENO
     * @brief Création du tournant (Sud-Est)
     * @details - On fonction de la position et du type des 3 cases passé en paramètre on assigne le nouveau type à la case à dessiner
     */
    public void makeH1V1(VueCase firstcase, VueCase drawcase, VueCase lastcase) {

        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)) {
            drawcase.getCase().setType(CaseType.h1v1);
        }


        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0v0
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        }
        drawcase.update();
    }

    /**
     * @return true/false: [boolean] Vrai si le chemin est bon et faux si non
     * @author Evan SEDENO
     * @brief Vérifie si le chemin est valide
     * @details - On regarde si toutes les cases du chemin se suivent avec leur position
     * @details - On renvoie vrai si le chemin est bon et faux si non
     */
    public boolean checkGoodChemin() {
        //On vérifie si la taille du chemin est supérieur à 2 cases et que le départ soit valide
        if (this.getStart() != null && this.getWay().size() > 2) {
            //On parcourt chaque case du chemin
            for (int i = 0; i < this.getWay().size() - 1; ++i) {
                //On vérifie que toutes les cases du chemin se suivent
                if (!(this.getWay().get(i).getCase().getX() == (this.getWay().get(i + 1).getCase().getX() + 1))
                        && !(this.getWay().get(i).getCase().getX() == (this.getWay().get(i + 1).getCase().getX() - 1))
                        && !(this.getWay().get(i).getCase().getY() == (this.getWay().get(i + 1).getCase().getY() + 1))
                        && !(this.getWay().get(i).getCase().getY() == (this.getWay().get(i + 1).getCase().getY() - 1))) {
                    return false;
                }
            }
        }
        //On renvoie vraie si toutes les cases se suivent
        return true;
    }

    /**
     * @param chemins: [List<chemins>] Liste des chemins
     * @author Evan SEDENO
     * @brief Supprime le chemin
     * @details - On parcourt l'ensemble des cases du chemin
     * @details - On vérifie si le type de la case est un croisement sinon on remet le type à vide
     * @details - On parcourt l'ensemble des cases des chemins passé en paramètre et on vérifie si le type est un croisement et que les deux cases parcourent sont les mêmes
     * @details - On replace le croisement par l'ancien type de la case de l'autre chemin
     * @details - On redessine la case
     */
    public void deleteChemin(List<Chemin> chemins) {
        //On parcourt chaque case du chemin
        int index = this.getStop() == null ? 0 : 1;
        for (int i = 1; i < this.getWay().size() - index; ++i) {
            //On vérifie si la case est un croisement
            if (this.getWay().get(i).getCase().getType() == CaseType.cross) {
                //On parcourt chaque chemin de la liste en paramètre
                for (int j = 0; j < chemins.size(); ++j) {
                    //On vérifie que le chemin est bien terminé
                    if (chemins.get(j) != this && chemins.get(j).getStop() != null) {
                        //On parcourt chaque case du chemin parcourt
                        for (int k = 1; k < chemins.get(j).getWay().size() - 1; ++k) {
                            //On vérifie si la case parcourut est commune à au chemin actuel
                            if (chemins.get(j).getWay().get(k).getCase().getType() == CaseType.cross
                                    && chemins.get(j).getWay().get(k) == this.getWay().get(i)) {
                                chemins.get(j).getWay().get(k).setStarter(chemins.get(j).getStart());
                                //On remplace le type de la case par l'ancien type du chemin parcourt
                                if (chemins.get(j).getStart() == chemins.get(j).getWay().get(k).getOtherStarter()) {
                                    chemins.get(j).getWay().get(k).getCase().setType(chemins.get(j).getWay().get(k).getLastType());
                                } else {
                                    chemins.get(j).getWay().get(k).getCase().setType(chemins.get(j).getWay().get(k).getFirstType());
                                }
                            }
                        }
                    }
                }
            } else {
                //On remplace le type de la case par le type vide
                this.getWay().get(i).getCase().setType(CaseType.empty);
            }
            //On redessine la case
            this.getWay().get(i).update();
        }
    }
}

