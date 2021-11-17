import java.awt.*;
import java.util.HashMap;

public class ModelGrille {

    private final VueCase[][] tabCV;
    private final HashMap<VueCase, Point> hashmap;
    private final int size;

    public HashMap<VueCase, Point> getHashMap(){
        return this.hashmap;
    }

    public ModelGrille(int size) {
        this.size = size;
        this.tabCV = new VueCase[size][size];
        this.hashmap = new HashMap<VueCase, Point>();
    }

    public int getSize() {
        return this.size;
    }

    public VueCase[][] getVueCases(){
        return this.tabCV;
    }

    public VueCase getVueCase(int x, int y) {
        return this.tabCV[x][y];
    }

    public void generateGrille() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, false);
                hashmap.put(tabCV[i][j], new Point(j, i));
            }
        }
    }

    public void generateEmptyGrille() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                tabCV[i][j] = new VueCase(i, j, true);
                hashmap.put(tabCV[i][j], new Point(j, i));
            }
        }

    }
}