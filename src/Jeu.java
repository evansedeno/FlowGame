import java.io.*;

public class Jeu {

    private VueControleurGrille jeu;
    private final int size;
    private final int level;
    private final User user;

    public Jeu(int size, int level, User user) {
        this.size = size;
        this.level = level;
        this.user = user;

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

    public void createGame() throws IOException {
        this.jeu = new VueControleurGrille(this.size, 0, this.user);
        this.jeu.setVisible(true);
    }

    public void startGame() {
        this.jeu = new VueControleurGrille(this.size, this.level, this.user);
        this.jeu.setVisible(true);
    }
}