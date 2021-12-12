package utilitaires;

import model.ModelGrille;
import model.User;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Evan SEDENO
 * @date 07/12/2021
 * @file utilitaires.Files.java
 * @brief Class utilitaire pour la gestion avec les fichiers externes
 * @details Contient toutes les méthodes pour la gestion des fichiers externes et la manipulation de ceux-ci
 */
public class Files {

    /**
     * @param size: [int] Taille du niveau
     * @return level: [int] Identifiant du prochain niveau créable
     * @author Evan SEDENO
     * @brief Récupère l'identifiant du prochain niveau créable
     * @details - On récupère la liste des fichiers dans le dossier de la taille choisie
     * @details - On vérifie si un niveau existe déjà dans la taille choisie
     * @details - On renvoie l'identifiant du prochain niveau
     */
    public int getNextLevel(int size) {
        //On récupère la liste des niveaux
        File folder = new File("levels/" + size);
        File[] listOfFiles = folder.listFiles();

        //On vérifie s'il y a plus d'un niveau
        int level = 0;
        if (listOfFiles != null) {
            if (listOfFiles.length == 0) {
                level = 1;
            } else {
                level = listOfFiles.length + 1;
            }
        }
        return level;
    }

    /**
     * @param grille: [model.ModelGrille] Grille du niveau
     * @author Evan SEDENO
     * @brief Remplis le fichier text du nouveau niveau
     * @details - On récupère le type de chaque case et on l'ajoute à la String casesType
     * @details - On créer un nouveau fichier au bon emplacement par rapport à la taille et à l'identifiant du niveau
     * @details - On écrit dans le fichier la String remplis plus haut
     */
    public void writeLevelFile(ModelGrille grille) {

        //On récupère chaque type de utilitaires.CaseType de la grille
        StringBuilder casesType = new StringBuilder();
        for (int i = 0; i < grille.getSize(); ++i) {
            for (int j = 0; j < grille.getSize(); ++j) {
                casesType.append(grille.getVueCase(i, j).getCase().getType()).append(" ");
            }
            casesType.append("\r\n");
        }

        //On écrit un fichier text avec toutes les cases
        try {
            File cases = new File("levels/" + grille.getSize() + "/" + grille.getLevel() + ".txt");
            cases.createNewFile();
            FileWriter writeCases = new FileWriter("levels/" + grille.getSize() + "/" + grille.getLevel() + ".txt");
            writeCases.write(casesType.toString());
            writeCases.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * @return levels: [int[]] Liste des tailles
     * @author Evan SEDENO
     * @brief Récupère la liste des tailles disponibles
     * @details - On ouvre le dossier "levels"
     * @details - On récupère la liste des dossiers correspondant aux tailles
     * @details - On récupère pour chaque dossier son nom sous forme d'entier
     * @details - On renvoie la liste des tailles
     */
    public int[] getAllSizes() {

        File file = new File("levels");
        File[] fileList = file.listFiles();
        assert fileList != null;
        int[] levels = new int[fileList.length];

        for (int i = 0; i < fileList.length; ++i) {
            levels[i] = Integer.parseInt(fileList[i].getName());
        }
        return levels;
    }

    /**
     * @param size: [int] Taille des niveaux
     * @return levels: [int[]] Liste des niveaux
     * @author Evan SEDENO
     * @brief Récupère la liste des niveaux disponibles
     * @details - On ouvre le dossier de la taille passé en paramètre
     * @details - On récupère la liste des fichiers correspondant aux niveaux
     * @details - On récupère pour chaque fichier son nom sous forme d'entier
     * @details - On renvoie la liste des niveaux
     */
    public int[] getAllLevels(int size) {

        File file = new File("levels/" + size);
        File[] fileList = file.listFiles();
        assert fileList != null;
        int[] levels = new int[fileList.length];

        for (int i = 0; i < fileList.length; ++i) {
            String str = fileList[i].getName();
            levels[i] = Integer.parseInt(str.substring(0, str.lastIndexOf('.')));
        }
        return levels;
    }

    /**
     * @param size:  [int] Taille du niveau
     * @param level: [int] Identifiant du niveau
     * @param x:     [int] Position x de la case
     * @param y:     [int] Position y de la case
     * @return mycase: [utilitaires.CaseType] Objet utilitaires.CaseType de type enum de la case correspondante à une certaine position dans un niveau
     * @author Evan SEDENO
     * @brief Récupère le type d'une case en fonction d'un niveau et d'une position
     * @details - On lit le fichier text du niveau correspondant
     * @details - On parcourt l'ensemble des mots et les stocks dans une liste de String
     * @details - On renvoie la valeur utilitaires.CaseType du mot voulu à la position donnée en paramètre
     */
    public CaseType getTypeInFile(int size, int level, int x, int y) {
        CaseType mycase;
        String[][] listCases = new String[size][size];

        //On récupère le niveau en fonction de la taille et de l'identifiant du niveau
        File file = new File("levels/" + size + "/" + level + ".txt");
        FileReader fileR;
        try {
            fileR = new FileReader(file);

            //On lit le fichier du niveau
            BufferedReader buffer = new BufferedReader(fileR);
            String s;
            String[] w;
            int i = 0;
            //On parcourt l'ensemble des lignes du niveau
            while ((s = buffer.readLine()) != null) {
                int j = 0;

                //On récupère chaque type de case dans l'ensemble du niveau
                w = s.split(" ");
                for (String wrd : w) {
                    listCases[i][j] = wrd;
                    ++j;
                }
                ++i;
            }
            //On récupère le type de la position demandée
            mycase = CaseType.valueOf(listCases[y][x]);
            return mycase;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return levels: [ArrayList<model.User>] Liste des utilisateurs
     * @author Evan SEDENO
     * @brief Récupère la liste des joueurs
     * @details - On ouvre le fichier users.json
     * @details - Pour chaque utilisateur (séparé par "{" et "}") on récupère son identifiant, nom et points
     * @details - On renvoie la liste des utilisateurs
     */
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try {
            //On récupère chaque ligne du fichier "users.json"
            List<String> content = java.nio.file.Files.readAllLines(Path.of("users.json"), StandardCharsets.UTF_8);

            //On supprime chaque espace
            for (int i = 0; i < content.size(); ++i) {
                content.set(i, content.get(i).replaceAll("\\s+", ""));
            }

            //On récupère chaque information de chaque utilisateur et on instancie la classe model.User
            for (int i = 0; i < content.size(); ++i) {
                if (content.get(i).equals("{")) {
                    int idline = Integer.parseInt(content.get(i + 1).replaceAll("\"id\":", "").replaceAll(",", ""));
                    String usernameline = content.get(i + 2).replaceAll("\"username\":", "").replaceAll(",", "").replaceAll("\"", "");
                    int pointsline = Integer.parseInt(content.get(i + 3).replaceAll("\"points\":", "").replaceAll(",", ""));
                    users.add(new User(idline, usernameline, pointsline));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * @param username: [String] Nom de l'utilisateur à rechercher
     * @return user: [model.User] Utilisateur récupéré par son nom
     * @author Evan SEDENO
     * @brief Récupère un utilisateur par son nom
     * @details - On récupère la liste des utilisateurs
     * @details - Pour chaque utilisateur on vérifie son nom, si ça correspond on le renvoie sinon on en renvoie un nouveau
     */
    public User getUserByUsername(String username) {
        //On récupère la liste des utilisateurs
        ArrayList<User> users = this.getAllUsers();

        //On regarde si un des utilisateurs existant a le même nom que celui passé en paramètre
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return new User(this.getMaxId() + 1, username, 0);
    }

    /**
     * @return idmax: [int] Identifiant du dernier joueur
     * @author Evan SEDENO
     * @brief Récupère l'identifiant du dernier utilisateur créé
     * @details - On récupère la liste de tous les utilisateurs
     * @details - On vérifie lequel est le plus élevé
     * @details - On renvoie le dernier identifiant
     */
    public int getMaxId() {
        //On récupère tous les utilisateurs
        ArrayList<User> users = this.getAllUsers();
        int idmax = 0;

        //On regarde lequel est le plus grand
        for (User user : users) {
            if (user.getId() > idmax) idmax = user.getId();
        }
        return idmax;
    }

    /**
     * @param users: [ArrayList<users>] Liste des utilisateurs
     * @author Evan SEDENO
     * @brief Remplace le contenu du fichier "users.json" par les joueurs actuels
     * @details - On créer une liste de String dans laquelle on va mettre chaque utilisateur
     * @details - Pour chaque utilisateur de la liste passé en paramètre, on ajoute à la liste de String chaque ligne pour créer un nouvel objet JSON
     * @details - On remplace le contenu du fichier "users.json" par chaque ligne de la liste de String
     */
    public void actualizeUsers(ArrayList<User> users) {
        //On créer une liste de String
        ArrayList<String> json = new ArrayList<>();
        json.add("[");

        //On ajoute pour chaque utilisateur son objet en JSON dans la liste de String
        for (int i = 0; i < users.size(); ++i) {
            json.add("  {");
            json.add("    \"id\": " + users.get(i).getId() + ",");
            json.add("    \"username\": \"" + users.get(i).getUsername() + "\",");
            json.add("    \"points\": " + users.get(i).getPoints());

            //Si c'est le dernier utilisateur alors on ne met pas de virgule à la fin de son objet
            if (i == users.size() - 1) {
                json.add("  }");
            } else {
                json.add("  },");
            }
        }
        json.add("]");

        //On remplace le contenu du fichier "users.json" par chaque ligne de la liste de String
        try {
            FileWriter writer = new FileWriter("users.json");
            for (String line : json) {
                writer.write(line + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}