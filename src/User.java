/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file User.java
 * @brief Class User qui définit un utilisateur
 * @details Contient toutes les méthodes et attributs pour la gestion d'un utilisateur
 */
public class User implements Comparable<User> {

    private final int id;
    private final String username;
    private int points;

    /**
     * @param id:       [int] Identifiant de l'utilisateur
     * @param username: [String] Nom de l'utilisateur
     * @param points:   [int] Points de l'utilisateur
     * @author Evan SEDENO
     * @brief Constructeur de la class User
     * @details - On définit les valeurs des trois paramètres aux trois attributs
     */
    public User(int id, String username, int points) {
        this.id = id;
        this.username = username;
        this.points = points;
    }

    /**
     * @return this.id: [int] Identifiant de l'utilisateur
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut id
     * @details - On renvoie l'attribut id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return this.username: [String] Nom de l'utilisateur
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut username
     * @details - On renvoie l'attribut username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * @param points: [int] Nombre de points à ajouter à l'utilisateur
     * @author Evan SEDENO
     * @brief Ajoute un certains nombres de points à l'utilisateur
     * @details - On définit la valeur des points de l'utilisateur par son nombre de points actuel + le nombre de points en paramètre
     */
    public void addPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * @return this.points: [int] Points de l'utilisateur
     * @author Evan SEDENO
     * @brief Accesseur de l'attribut points
     * @details - On renvoie l'attribut points
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * @param user: [User] Utilisateur à comparer
     * @return (this.points - user.points) [int] Difference de points de l'utilisateur actuel et celui passé en paramètre
     * @author Evan SEDENO
     * @brief Réécriture de la méthode compareTo de l'interface Comparable
     * @details - On retourne la difference de points de l'utilisateur actuel et celui passé en paramètre
     * @see "https://docs.oracle.com/javase/7/docs/api/java/lang/Comparable.html"
     */
    @Override
    public int compareTo(User user) {
        return (this.points - user.points);
    }
}
