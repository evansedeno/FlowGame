import java.util.Scanner;

public class Jeu {

    private VueControleurGrille creation;
    private VueControleurGrille jeu;
    private int size;
    private CaseType[][] tabType;
    private final Scanner scanner = new Scanner(System.in);

    public Jeu() {
        this.setSize();
        this.initialize();
        this.start();
    }

    public void setSize(){
        System.out.println("Entrer la taille de la fenêtre: ");
        int taille = scanner.nextInt();
        while (taille < 0){
            System.out.println("La valeur entrée est mauvaise, veuillez réessayer: ");
            taille = scanner.nextInt();
        }
        this.size = taille;
    }

    public void initialize() {
        this.creation = new VueControleurGrille(this.size, true);
        this.creation.setVisible(true);
    }

    public void start() {

    }

    public static void main(String[] args) {
        Jeu jeu = new Jeu();
    }

}
