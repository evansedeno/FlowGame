import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Files {


    public int getNextLevel(int size) {
        File folder = new File("levels/" + size);
        File[] listOfFiles = folder.listFiles();
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

    public void writeLevelFile(int size, int level, ModelGrille grille) {

        //On récupère chaque type de CaseType de la grille
        StringBuilder casesType = new StringBuilder();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                casesType.append(grille.getVueCase(i, j).getCase().getType()).append(" ");
            }
            casesType.append("\r\n");
        }

        //On écrit un fichier text avec toutes les cases
        File cases = new File("levels/" + size + "/" + level + ".txt");
        try {
            cases.createNewFile();
            FileWriter writeCases = new FileWriter("levels/" + size + "/" + level + ".txt");
            writeCases.write(casesType.toString());
            writeCases.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

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

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            List<String> content = java.nio.file.Files.readAllLines(Path.of("users.json"), StandardCharsets.UTF_8);

            for (int i = 0; i < content.size(); ++i) {
                content.set(i, content.get(i).replaceAll("\\s+", ""));
            }

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

    public User getUserById(int id) {
        User user = new User(id, "", 0);
        try {
            List<String> content = java.nio.file.Files.readAllLines(Path.of("users.json"), StandardCharsets.UTF_8);

            for (int i = 0; i < content.size(); ++i) {
                content.set(i, content.get(i).replaceAll("\\s+", ""));
            }

            for (int i = 0; i < content.size(); ++i) {
                if (content.get(i).equals("{")) {
                    int idline = Integer.parseInt(content.get(i + 1).replaceAll("\"id\":", "").replaceAll(",", ""));
                    String usernameline = content.get(i + 2).replaceAll("\"username\":", "").replaceAll(",", "").replaceAll("\"", "");
                    int pointsline = Integer.parseInt(content.get(i + 3).replaceAll("\"points\":", "").replaceAll(",", ""));

                    if (idline == id) {
                        user.setId(idline);
                        user.setUsername(usernameline);
                        user.setPoints(pointsline);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user.getUsername().equals("")) {
            user.setId(this.getMaxId() + 1);
            user.setUsername("Invite" + user.getId());
            user.setPoints(0);
        }
        return user;
    }

    public User getUserByUsername(String username) {
        User user = new User(-1, username, 0);
        try {
            List<String> content = java.nio.file.Files.readAllLines(Path.of("users.json"), StandardCharsets.UTF_8);

            for (int i = 0; i < content.size(); ++i) {
                content.set(i, content.get(i).replaceAll("\\s+", ""));
            }

            for (int i = 0; i < content.size(); ++i) {

                if (content.get(i).equals("{")) {
                    int idline = Integer.parseInt(content.get(i + 1).replaceAll("\"id\":", "").replaceAll(",", ""));
                    String usernameline = content.get(i + 2).replaceAll("\"username\":", "").replaceAll(",", "").replaceAll("\"", "");
                    int pointsline = Integer.parseInt(content.get(i + 3).replaceAll("\"points\":", "").replaceAll(",", ""));

                    if (usernameline.equals(username)) {
                        user.setId(idline);
                        user.setUsername(usernameline);
                        user.setPoints(pointsline);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (user.getId() == -1) {
            user.setId(this.getMaxId() + 1);
            user.setPoints(0);
        }
        return user;
    }

    public int getMaxId() {
        ArrayList<User> users = this.getAllUsers();
        int idmax = 0;
        for (User user : users) {
            if (user.getId() > idmax) idmax = user.getId();
        }
        return idmax;
    }

    public void actualizeUsers(ArrayList<User> users) {
        ArrayList<String> json = new ArrayList<String>();
        json.add("[");
        for (int i = 0; i < users.size(); ++i) {
            json.add("  {");
            json.add("    \"id\": " + users.get(i).getId() + ",");
            json.add("    \"username\": \"" + users.get(i).getUsername() + "\",");
            json.add("    \"points\": " + users.get(i).getPoints());

            if (i == users.size() - 1) {
                json.add("  }");
            } else {
                json.add("  },");
            }
        }
        json.add("]");

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
