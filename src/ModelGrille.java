import java.io.*;

public class ModelGrille {

    private final VueCase[][] tabCV;
    private final int size;
    private final int level;

    public ModelGrille(int size, int level) {
        this.size = size;
        this.tabCV = new VueCase[size][size];
        this.level = level;
    }

    public int getSize() {
        return this.size;
    }

    public VueCase getVueCase(int x, int y) {
        return this.tabCV[y][x];
    }

    public void generateGrille() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, this.getTypeInFile(this.level, i, j));
            }
        }
    }

    public void generateEmptyGrille() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, CaseType.empty);
            }
        }
    }

    public boolean validateGrille() {
        for (int i = 0; i < this.getSize(); ++i) {
            for (int j = 0; j < this.getSize(); ++j) {
                if (this.getVueCase(i, j).getCase().getType() == CaseType.empty) {
                    return false;
                }
            }
        }
        return true;
    }

    public CaseType getTypeInFile(int level, int x, int y){

        CaseType myCase;
        String[][] listCases = new String[size][size];

        File file = new File("levels/" + this.size + "/" + level + ".txt");
        FileReader fileR = null;
        try {
            fileR = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fileR != null;
        BufferedReader buffer = new BufferedReader(fileR);
        String s = "";
        String[] w;
        int i = 0;
        while (true) {
            try {
                if ((s = buffer.readLine()) == null) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            int j = 0;
            w = s.split(" ");
            for (String wrd : w) {
                listCases[i][j] = wrd;
                ++j;
            }
            ++i;
        }
        myCase = CaseType.valueOf(listCases[y][x]);
        return myCase;
    }
}