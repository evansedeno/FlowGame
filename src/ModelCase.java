import java.util.Objects;

public class ModelCase {

    private final int x, y;
    private CaseType type;
    private boolean locked;

    public ModelCase(int x, int y, CaseType type) {
        this.x = x;
        this.y = y;

        if (type == CaseType.empty) {
            this.type = type;
            this.locked = false;
        } else {
            this.type = type;
            this.checkLockCase();
        }
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void checkLockCase() {
        if (this.type != CaseType.empty) this.locked = true;
    }

    public void setType(CaseType ct) {
        this.type = ct;
    }

    public void setNextNumber() {
        switch (this.type) {
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

    public boolean isTurn() {
        return this.type == CaseType.h0v0
                || this.type == CaseType.h0v1
                || this.type == CaseType.h1v0
                || this.type == CaseType.h1v1
                || this.type == CaseType.cross;
    }

    public boolean isNotTurn() {
        return this.type != CaseType.h0v0
                && this.type != CaseType.h0v1
                && this.type != CaseType.h1v0
                && this.type != CaseType.h1v1
                && this.type != CaseType.cross;
    }

    public boolean isWay() {
        return this.type == CaseType.h0v0
                || this.type == CaseType.h0h1
                || this.type == CaseType.h1v0
                || this.type == CaseType.h1v1
                || this.type == CaseType.h0v1
                || this.type == CaseType.v0v1
                || this.type == CaseType.cross;
    }

    public boolean isSameDirection(CaseType type) {
        if (type == CaseType.cross && this.type == CaseType.v0v1) {
            return true;
        } else if (type == CaseType.cross && this.type == CaseType.h0h1) {
            return true;
        } else {
            return false;
        }
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String toString() {
        return "X: " + this.x + ", Y: " + this.y;
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