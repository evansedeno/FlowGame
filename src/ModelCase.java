import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

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

    public CaseType getTypeInFile() {

        int size = 0;
        try (Stream<String> lines = Files.lines(Path.of("cases.txt"), Charset.defaultCharset())) {
            size = (int) lines.count();
        } catch (IOException e) {
            e.printStackTrace();
        }

        CaseType myCase;
        String[][] listCases = new String[size][size];

        File file = new File("cases.txt");
        FileReader fileR = null;
        try {
            fileR = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        assert fileR != null;
        BufferedReader buffer = new BufferedReader(fileR);
        String s = "";
        String[] w = null;
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
        return CaseType.valueOf(listCases[this.y][this.x]);
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

    public void setNextWay() {
        switch (this.type) {
            case empty:
                this.type = CaseType.h0v0;
                break;
            case h0v0:
                this.type = CaseType.h0h1;
                break;
            case h0h1:
                this.type = CaseType.v0v1;
                break;
            case v0v1:
                this.type = CaseType.h0v1;
                break;
            case h0v1:
                this.type = CaseType.h1v0;
                break;
            case h1v0:
                this.type = CaseType.h1v1;
                break;
            case h1v1:
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
                || this.type == CaseType.h1v1;
    }

    public boolean isWay() {
        return this.type == CaseType.h0v0
                || this.type == CaseType.h0h1
                || this.type == CaseType.h1v0
                || this.type == CaseType.h1v1
                || this.type == CaseType.h0v1
                || this.type == CaseType.v0v1;
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