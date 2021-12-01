import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.event.*;
import java.util.List;

public class VueControleurGrille extends JFrame {

    private static final int PIXEL_PER_SQUARE = 60;
    private JComponent currentComponent;
    private final ModelGrille grille;
    private final JPanel contentPane;
    private final JPanel buttonPane;
    private final JPanel allPane;
    private int level;
    private final int size;
    private final User user;
    private final List<Chemin> ways = new ArrayList<Chemin>();
    private boolean clicked = false;
    private final boolean iscreation;

    public VueControleurGrille(int size, int _level, User user) {

        this.level = _level;
        this.size = size;
        this.user = user;

        //On créer la vue
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * PIXEL_PER_SQUARE * 2, size * PIXEL_PER_SQUARE * 2 + ((this.level == 0) ? 50 : 0));
        setResizable(false);
        GridLayout gridLayout = new GridLayout(size, size);
        this.contentPane = new JPanel(gridLayout);
        this.buttonPane = new JPanel();
        this.allPane = new JPanel();

        //On ajoute génère la grille (vide ou pleine)
        this.grille = new ModelGrille(size, this.level);
        if (level == 0) {
            this.iscreation = true;
            Files file = new Files();
            this.setNewLevel(file.getNextLevel(size));
            this.displayValidateButton(file);
            grille.generateEmptyGrille();
        } else {
            this.iscreation = false;
            grille.generateGrille();
        }

        //On ajoute la grille au pannel
        this.addGrilleOnVue();
        this.addGrilleListeners();

        //On ajoute les pannels à la vue
        this.addPaneOnVue();
        this.allPane.setLayout(new BoxLayout(this.allPane, BoxLayout.Y_AXIS));
        setContentPane(this.allPane);
    }

    public void addPaneOnVue() {
        if (this.iscreation) {
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
                        if (iscreation) {
                            ((VueCase) e.getSource()).getCase().setNextNumber();
                        } else if (((VueCase) e.getSource()).getCase().isLocked()) {
                            Chemin way = new Chemin(((VueCase) e.getSource()));
                            way.getStart().setStarter(way.getStart());
                            ways.add(way);
                            clicked = true;
                        }

                        ((VueCase) e.getSource()).update();
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (((VueCase) e.getSource()).getCase().isWay()) {
                            for (Chemin way : ways) {
                                for (VueCase cases : way.getWay()) {
                                    if (cases.getCase() == ((VueCase) e.getSource()).getCase()) {
                                        deleteChemin(way);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        currentComponent = (JComponent) e.getSource();

                        if (((VueCase) currentComponent).getCase().isWay() && clicked) {
                            deleteChemin(ways.get(ways.size() - 1));
                            clicked = false;
                        }

                        if (ways.size() > 0
                                && ways.get(ways.size() - 1).getStart() != null
                                && ways.get(ways.size() - 1).getStop() == null
                                && ((VueCase) currentComponent).getCase().getType() == CaseType.empty
                                && clicked) {
                            ways.get(ways.size() - 1).addCase((VueCase) currentComponent);
                            checkGoodChemin(ways.get(ways.size() - 1));
                            ((VueCase) currentComponent).setStarter(ways.get(ways.size() - 1).getStart());
                            drawCases(ways.get(ways.size() - 1));
                        }
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (((VueCase) currentComponent).getCase().isLocked()
                                && ((VueCase) currentComponent).getCase().getType() == ways.get(ways.size() - 1).getStart().getCase().getType()
                                && clicked) {
                            if (ways.get(ways.size() - 1).setStop((VueCase) currentComponent)) {
                                clicked = false;
                                drawCases(ways.get(ways.size() - 1));
                                repaint();

                                if (grille.validateGrille()) {
                                    endGame();
                                }
                            }
                        } else if (ways.size() > 0 && ways.get(ways.size() - 1).getStop() == null) {
                            deleteChemin(ways.get(ways.size() - 1));
                            clicked = false;
                        }
                        if (ways.size() > 0) checkGoodChemin(ways.get(ways.size() - 1));

                    }
                });
            }
        }
    }

    public void checkGoodChemin(Chemin chemin) {

        int maxi = 2;
        if (chemin.getStop() != null) maxi = 1;

        if (chemin.getStart() != null) {
            for (int i = 1; i < chemin.getWay().size() - maxi; ++i) {

                boolean cond1 = chemin.getWay().get(i).getCase().getX() == (chemin.getWay().get(i + 1).getCase().getX() + 1);
                boolean cond2 = chemin.getWay().get(i).getCase().getX() == (chemin.getWay().get(i + 1).getCase().getX() - 1);
                boolean cond3 = chemin.getWay().get(i).getCase().getY() == (chemin.getWay().get(i + 1).getCase().getY() + 1);
                boolean cond4 = chemin.getWay().get(i).getCase().getY() == (chemin.getWay().get(i + 1).getCase().getY() - 1);

                if ((!chemin.getWay().get(i).getCase().isWay()) || (!cond1 && !cond2 && !cond3 & !cond4)) {
                    deleteChemin(chemin);
                    break;
                }
            }
        }
    }

    public void drawCases(Chemin chemin) {
        ways.get(ways.size() - 1).assignType();
        repaint();
    }

    public void deleteChemin(Chemin chemin) {
        for (int i = 1; i < chemin.getWay().size(); ++i) {
            if (chemin.getStop() != chemin.getWay().get(i)) {
                chemin.getStart().setStarter(null);
                chemin.getWay().get(i).getCase().setType(CaseType.empty);
                repaint();
            }
        }
    }

    public void endGame() {
        JPanel gameEnd = new JPanel(new GridLayout(3,1));
        gameEnd.setBackground(Color.darkGray);
        gameEnd.setPreferredSize(new Dimension(getWidth()-100, getHeight()/2));
        JLabel text1 = new JLabel("Bravo " + this.user.getUsername() + " !");
        JLabel text2 = new JLabel("Vous avez réussi le niveau !");
        text1.setForeground(Color.white);
        text1.setHorizontalAlignment(JLabel.CENTER);
        text2.setForeground(Color.white);
        text2.setHorizontalAlignment(JLabel.CENTER);
        this.user.addPoints(this.size);
        Files file = new Files();
        ArrayList<User> users = file.getAllUsers();
        int userssizebefore = users.size();
        for (int i = 0; i < users.size(); ++i) {
            if (users.get(i).getId() == this.user.getId()) {
                users.set(i, this.user);
            }
        }
        if (userssizebefore == users.size()) {
            users.add(this.user);
        }
        file.actualizeUsers(users);

        text1.setVerticalAlignment(SwingConstants.CENTER);
        text1.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setVerticalAlignment(SwingConstants.CENTER);
        text2.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("myfont", Font.BOLD, 15);
        text1.setFont(font);
        text2.setFont(font);
        gameEnd.add(text1);
        gameEnd.add(text2);

        JButton back = new JButton("RETOUR");
        back.setPreferredSize(new Dimension(30, 30));
        back.setBackground(Color.white);
        back.setBorderPainted(false);
        back.setFont(new Font("buttonfont", Font.BOLD, 20));

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VueControleurMenu menu = new VueControleurMenu();
                menu.setUsername(user);
                menu.drawMainMenu();
                setVisible(false);
                dispose();
            }
        });

        gameEnd.add(back);

        setContentPane(gameEnd);
        repaint();
        revalidate();
    }

    public void setNewLevel(int _level) {
        this.level = _level;
    }

    public void displayCreationFinished() {


        JPanel gameReady = new JPanel();
        gameReady.setBackground(Color.darkGray);
        JLabel text = new JLabel("Le niveau a bien été enregisté !");
        text.setForeground(Color.white);
        text.setVerticalAlignment(SwingConstants.CENTER);
        text.setHorizontalAlignment(SwingConstants.CENTER);

        Font font = new Font("myfont", Font.BOLD, 20);
        text.setFont(font);
        gameReady.add(text);


        JButton button = new JButton("RETOUR");
        button.setBackground(Color.white);
        button.setPreferredSize(new Dimension(getWidth() / 2, 50));

        button.addActionListener(e -> {
            new VueControleurMenu();
            removeAll();
            setVisible(false);
            dispose();
        });

        gameReady.add(button);
        setContentPane(gameReady);
        repaint();
        revalidate();
    }

    public void displayValidateButton(Files file) {
        JButton button = new JButton("VALIDER");
        button.setBackground(Color.white);
        button.setPreferredSize(new Dimension(getWidth() / 2, 50));

        button.addActionListener(e -> {
            file.writeLevelFile(this.size, this.level, this.grille);
            displayCreationFinished();
        });

        this.buttonPane.add(button);
        this.buttonPane.setMinimumSize(new Dimension(PIXEL_PER_SQUARE * this.size, 50));
        this.buttonPane.setMaximumSize(new Dimension(PIXEL_PER_SQUARE * this.size, 50));
    }
}