import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.awt.event.*;

public class VueControleurGrille extends JFrame {

    private static final int PIXEL_PER_SQUARE = 60;
    private JComponent currentComponent;
    private final ModelGrille grille;
    private final JPanel contentPane;
    private final JPanel buttonPane;
    private final JPanel allPane;


    public VueControleurGrille(int size, boolean empty) {
        //On créer la vue
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE, size * PIXEL_PER_SQUARE + (empty ? 50 : 0));
        this.contentPane = new JPanel(new GridLayout(size, size));
        this.buttonPane = new JPanel();
        this.allPane = new JPanel();

        //On ajoute génère la grille (vide ou pleine)
        this.grille = new ModelGrille(size);
        if (empty) {
            grille.generateEmptyGrille();
            JButton button = new JButton("Valider");
            boolean isButtonPressed = false;
            button.addActionListener(e -> {

                //On récupère tout les types de toutes les cases
                StringBuilder casesType = new StringBuilder();
                for (int i = 0; i < size; ++i){
                    for (int j = 0; j < size; ++j){
                        casesType.append(grille.getVueCase(i, j).getCase().getType()).append(" ");
                    }
                    casesType.append("\r\n");
                }

                //On écrit un fichier text avec toutes les cases
                File cases = new File("cases.txt");
                try {
                    cases.createNewFile();
                    FileWriter writeCases = new FileWriter("cases.txt");
                    writeCases.write(casesType.toString());
                    writeCases.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });
            this.buttonPane.add(button);
            this.buttonPane.setMinimumSize(new Dimension(PIXEL_PER_SQUARE * size, 50));
            this.buttonPane.setMaximumSize(new Dimension(PIXEL_PER_SQUARE * size, 50));
            grille.generateEmptyGrille();
        } else {
            grille.generateGrille();
        }

        //On ajoute la grille au pannel
        this.addGrilleOnVue();
        this.addGrilleListeners();

        //On ajoute les pannels à la vue
        this.addPaneOnVue(empty);
        this.allPane.setLayout(new BoxLayout(this.allPane, BoxLayout.Y_AXIS));
        setContentPane(this.allPane);
    }

    public void addPaneOnVue(boolean empty) {
        if (empty) {
            this.allPane.add(this.contentPane);
            this.allPane.add(this.buttonPane);
        } else {
            this.allPane.add(this.contentPane);
        }
    }

    public ModelGrille getGrille() {
        return this.grille;
    }

    public void addGrilleOnVue() {
        for (int i = 0; i < grille.getSize(); i++) {
            for (int j = 0; j < grille.getSize(); j++) {
                this.contentPane.add(grille.getVueCase(i, j));
            }
        }
    }

    public void addGrilleListeners() {
        for (int i = 0; i < grille.getSize(); i++) {
            for (int j = 0; j < grille.getSize(); j++) {
                HashMap<VueCase, Point> hashMap = this.grille.getHashMap();
                this.grille.getVueCase(i, j).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {

                        //Point p = hashMap.get(e.getSource()); // (*) permet de récupérer les coordonnées d'une caseVue

                        ((VueCase) e.getSource()).getCase().setNextNumber();
                        ((VueCase) e.getSource()).update();
                        System.out.println("mousePressed : " + ((VueCase) e.getSource()).getCase().toString());
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        currentComponent = (JComponent) e.getSource();
                        System.out.println("mouseEntered : " + ((VueCase) e.getSource()).getCase().toString());
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        // (**) - voir commentaire currentComponent
                        System.out.println("mouseReleased : " + ((VueCase) currentComponent).getCase().toString());
                    }
                });
            }
        }
    }

}