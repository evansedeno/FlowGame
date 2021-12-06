import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class VueControleurMenu extends JFrame {

    private static final int PIXEL_PER_SQUARE = 60;
    private static final int SIZE_WINDOWS = 5;
    private final JPanel menuPane = new JPanel();
    private final Font buttonfont = new Font("buttonfont", Font.BOLD, 20);
    private String level;
    private final ArrayList<User> users = new ArrayList<>();
    private User user;


    public VueControleurMenu() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(SIZE_WINDOWS * PIXEL_PER_SQUARE * 2, SIZE_WINDOWS * PIXEL_PER_SQUARE * 2);
        setResizable(false);

        this.addUsers();            //On récupère tout les utilisateurs
        this.drawUsernameMenu();    //On affiche le menu pour demander le nom d'utilisateur
        repaint();

        setContentPane(this.menuPane);
        setVisible(true);
    }

    public void setUsername(User user) {
        this.user = user;
    }

    public void drawTitle() {
        this.menuPane.add(Box.createRigidArea(new Dimension(0, 20)));
        JLabel title = new JLabel("FLOWGAME");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Font titlefont = new Font("titlefont", Font.BOLD, 40);

        title.setForeground(Color.white);
        title.setFont(titlefont);
        this.menuPane.add(title);
    }

    public void drawText(String text, int size, JPanel pane) {
        JLabel str = new JLabel(text);
        str.setHorizontalAlignment(SwingConstants.CENTER);
        Font textfont = new Font("textfont", Font.BOLD, size);
        str.setForeground(Color.white);
        str.setFont(textfont);
        pane.add(str);
    }

    public void setMenuPaneConf() {
        this.menuPane.removeAll();
        this.menuPane.add(Box.createRigidArea(new Dimension(0, 10)));
        this.drawTitle();
        this.menuPane.add(Box.createRigidArea(new Dimension(0, getHeight() / 4)));
        this.menuPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.menuPane.setBackground(Color.darkGray);
        this.menuPane.setPreferredSize(new Dimension(getWidth() - 30, getHeight() - 30));
    }

    public void drawMainMenu() {

        this.setMenuPaneConf();

        JPanel game = new JPanel();
        game.setBackground(Color.darkGray);
        game.setPreferredSize(new Dimension(getWidth() - 80, 100));
        game.setLayout(new GridLayout(1, 2, 50, 50));
        game.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton creategame = new JButton("CREER");
        JButton playgame = new JButton("JOUER");
        JButton seepoints = new JButton("VOIR LES POINTS");

        playgame.setPreferredSize(new Dimension(getWidth() / 2, 50));
        creategame.setPreferredSize(new Dimension(getWidth() / 2, 50));
        seepoints.setPreferredSize(new Dimension(getWidth() - 80, 50));

        playgame.setBackground(Color.white);
        creategame.setBackground(Color.white);
        seepoints.setBackground(Color.white);

        playgame.setFont(this.buttonfont);
        creategame.setFont(this.buttonfont);
        seepoints.setFont(this.buttonfont);

        creategame.addActionListener(e -> {
            drawSizesMenu(true);
            repaint();
        });

        playgame.addActionListener(e -> {
            drawSizesMenu(false);
            repaint();
        });

        seepoints.addActionListener(e -> {
            drawPointsMenu();
            repaint();
        });

        game.add(creategame);
        game.add(playgame);

        JPanel points = new JPanel();
        points.setBackground(Color.darkGray);
        points.add(seepoints);

        this.menuPane.add(game);
        this.menuPane.add(points);
        setContentPane(this.menuPane);
        repaint();
    }

    public void drawSizesMenu(boolean iscreation) {

        this.setMenuPaneConf();

        JPanel sizePane = new JPanel(new GridLayout(1, 1, 30, 30));
        sizePane.setPreferredSize(new Dimension(getWidth() - 100, 50));
        sizePane.setBackground(Color.darkGray);

        Files file = new Files();
        int[] sizes = file.getAllSizes();
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 0)));
        this.drawText("Choisisez la taille du niveau:", 20, this.menuPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 10)));
        for (int i : sizes) {
            JButton button = new JButton(String.valueOf(i));

            button.setPreferredSize(new Dimension(30, 10));
            button.setBackground(Color.white);
            button.setBorderPainted(false);
            button.setFont(this.buttonfont);

            button.addActionListener(e -> {
                if (iscreation) {
                    drawCreateMenu(i);
                    repaint();
                } else {
                    drawLevelsMenu(i);
                    repaint();
                }
            });

            sizePane.add(button);
        }


        JButton back = this.drawReturnButton();

        back.addActionListener(e -> drawMainMenu());

        this.menuPane.add(sizePane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 50)));
        this.menuPane.add(back);
        repaint();
        setContentPane(this.menuPane);
    }

    public void drawLevelsMenu(int size) {

        this.setMenuPaneConf();

        JPanel levelPane = new JPanel(new GridLayout(1, 1, 30, 30));
        levelPane.setPreferredSize(new Dimension(getWidth() - 100, 50));
        levelPane.setBackground(Color.darkGray);

        Files file = new Files();
        int[] levels = file.getAllLevels(size);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 0)));
        this.drawText("Choisisez votre niveau:", 20, this.menuPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 10)));
        for (int i : levels) {
            JButton button = new JButton(String.valueOf(i));

            button.setPreferredSize(new Dimension(30, 10));
            button.setBackground(Color.white);
            button.setBorderPainted(false);
            button.setFont(this.buttonfont);

            button.addActionListener(e -> {
                level = "levels/" + size + "/" + i;
                menuPane.removeAll();
                new Jeu(size, i, user);
                setVisible(false);
                dispose();
            });

            levelPane.add(button);
        }

        JButton back = this.drawReturnButton();

        back.addActionListener(e -> drawSizesMenu(false));

        this.menuPane.add(levelPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 50)));
        this.menuPane.add(back);

        repaint();
        setContentPane(this.menuPane);
    }

    public void drawCreateMenu(int size) {
        menuPane.removeAll();
        new Jeu(size, 0, user);
        setVisible(false);
        dispose();
    }

    public void addUsers() {
        Files files = new Files();
        ArrayList<User> _users = files.getAllUsers();
        this.users.addAll(_users);
        Collections.sort(this.users);
        Collections.reverse(this.users);
    }

    public void drawPointsMenu() {

        this.setMenuPaneConf();

        Files files = new Files();
        int nbuser = files.getMaxId();

        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 0)));
        this.drawText("Points des joueurs:", 20, this.menuPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 10)));

        JPanel pointsPane = new JPanel(new GridLayout(nbuser / 4, 4, 30, 30));
        pointsPane.setPreferredSize(new Dimension(getWidth() - 100, 50));
        pointsPane.setBackground(Color.darkGray);
        pointsPane.setAlignmentX(Component.CENTER_ALIGNMENT);

        Font userfont = new Font("textfont", Font.PLAIN, 15);
        Font pointfont = new Font("textfont", Font.BOLD, 20);

        for (User value : this.users) {
            JPanel user = new JPanel(new GridLayout(2, 1));
            user.setBackground(Color.white);
            JLabel usernametext = new JLabel(value.getUsername());
            JLabel pointstext = new JLabel(String.valueOf(value.getPoints()));

            usernametext.setHorizontalAlignment(JLabel.CENTER);
            pointstext.setHorizontalAlignment(JLabel.CENTER);

            usernametext.setFont(userfont);
            pointstext.setFont(pointfont);

            user.add(usernametext);
            user.add(pointstext);
            pointsPane.add(user);
        }

        JButton back = this.drawReturnButton();

        back.addActionListener(e -> drawMainMenu());

        this.menuPane.add(pointsPane);
        this.menuPane.add(Box.createRigidArea(new Dimension(getWidth(), 50)));
        this.menuPane.add(back);

        setContentPane(this.menuPane);
        repaint();
    }

    public void drawUsernameMenu() {
        this.setMenuPaneConf();

        JPanel username = new JPanel();
        BoxLayout boxlayout = new BoxLayout(username, BoxLayout.Y_AXIS);
        username.setLayout(boxlayout);
        username.setBackground(Color.darkGray);

        Files files = new Files();

        this.menuPane.add(Box.createRigidArea(new Dimension(0, getHeight() / 4)));
        this.drawText("Entrez votre nom d'utilisteur:", 20, username);

        JTextField usernamefield = new JTextField();
        usernamefield.setFont(this.buttonfont);
        usernamefield.setPreferredSize(new Dimension(getWidth() / 2, 30));
        usernamefield.setBackground(Color.WHITE);

        username.add(Box.createRigidArea(new Dimension(0, 10)));
        username.add(usernamefield);
        username.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton validate = new JButton("VALIDER");
        validate.setBackground(Color.white);
        validate.setBorderPainted(false);
        validate.setFont(this.buttonfont);

        validate.addActionListener(e -> {
            String userstr = usernamefield.getText();
            user = files.getUserByUsername(userstr);
            if (user.getUsername().equals("")) user.setUsername(userstr);
            menuPane.removeAll();
            drawMainMenu();
        });

        username.add(validate);
        this.menuPane.add(username);
        repaint();
    }

    public JButton drawReturnButton() {
        JButton back = new JButton("RETOUR");
        back.setPreferredSize(new Dimension(getWidth() / 4, 30));
        back.setBackground(Color.white);
        back.setBorderPainted(false);
        back.setFont(this.buttonfont);
        return back;
    }
}
