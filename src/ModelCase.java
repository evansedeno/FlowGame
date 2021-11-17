import java.util.Objects;
import java.util.Random;

public class ModelCase {

    private static final Random rnd = new Random();
    private final int x, y;
    private CaseType type;

    public ModelCase(int x, int y, boolean empty) {
        this.x = x;
        this.y = y;

        if (empty) {
            this.type = CaseType.empty;
        } else {
            this.type = this.randomType();
        }
    }

    public CaseType randomType() {
        return CaseType.values()[rnd.nextInt(CaseType.values().length)];
    }

    public void setRandomType() {
        this.type = this.randomType();
    }

    public void setType(CaseType ct) {
        this.type = ct;
    }

    public void setNextNumber(){
        switch (this.type){
            case empty:
                this.type = CaseType.S1;
                break;
            case S1:
                this.type = CaseType.S2;
                break;
            case S2:
                this.type = CaseType.S3;
                break;
            case S3:
                this.type = CaseType.S4;
                break;
            case S4:
                this.type = CaseType.S5;
                break;
            case S5:
                this.type = CaseType.empty;
                break;
        }
    }

    public CaseType getType() {
        return this.type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return this.x + ", " + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelCase modelCase = (ModelCase) o;
        return x == modelCase.x && y == modelCase.y && type == modelCase.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, type);
    }

}