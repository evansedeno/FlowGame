import java.util.ArrayList;
import java.util.List;

public class Chemin {

    private VueCase start = null;
    private VueCase stop = null;
    private final List<VueCase> way = new ArrayList<VueCase>();

    public VueCase getStart() {
        return this.start;
    }

    public Chemin(VueCase start) {
        this.setStart(start);
        this.addCase(this.start);
    }

    public VueCase getStop() {
        return this.stop;
    }

    public boolean setStop(VueCase stop) {
        this.stop = stop;
        this.addCase(stop);
        return this.stop.isLocked() && this.start.isLocked();
    }

    public void setStart(VueCase start) {
        this.start = start;
        start.setStarter(start);
    }

    public void addCase(VueCase newcase) {
        this.way.add(newcase);
    }

    public List<VueCase> getWay() {
        return this.way;
    }

    public void makeDirections() {
        for (int i = 1; i < this.getWay().size(); ++i) {

            VueCase firstcase = this.getWay().get(i - 1);
            VueCase drawcase = this.getWay().get(i);

            if (drawcase != this.getStop()) {
                if (firstcase.getCase().getX() > drawcase.getCase().getX()
                        && firstcase.getCase().getY() == drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.h0h1);
                } else if (firstcase.getCase().getX() < drawcase.getCase().getX()
                        && firstcase.getCase().getY() == drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.h0h1);
                } else if (firstcase.getCase().getX() == drawcase.getCase().getX()
                        && firstcase.getCase().getY() > drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.v0v1);
                } else if (firstcase.getCase().getX() == drawcase.getCase().getX()
                        && firstcase.getCase().getY() < drawcase.getCase().getY()) {
                    drawcase.getCase().setType(CaseType.v0v1);
                }
            }
            drawcase.update();
        }
    }

    public void makeH0V0(VueCase firstcase, VueCase drawcase, VueCase lastcase) {
        //Turns on locked
        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);

        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)) {
            drawcase.getCase().setType(CaseType.h0v0);
        }

        //Turns on normals
        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v0);
        }
        drawcase.update();
    }

    public void makeH0V1(VueCase firstcase, VueCase drawcase, VueCase lastcase) {
        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)) {
            drawcase.getCase().setType(CaseType.h0v1);
        }

        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h1v0
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        }
        drawcase.update();
    }

    public void makeH1V0(VueCase firstcase, VueCase drawcase, VueCase lastcase) {

        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)) {
            drawcase.getCase().setType(CaseType.h1v0);
        }


        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.h0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v0);
        }


        drawcase.update();
    }

    public void makeH1V1(VueCase firstcase, VueCase drawcase, VueCase lastcase) {

        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)) {
            drawcase.getCase().setType(CaseType.h1v1);
        }


        if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && !drawcase.getCase().isTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0v0
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()) {
            drawcase.getCase().setType(CaseType.h1v1);
        }
        drawcase.update();
    }

    public void makeTurns() {
        for (int i = 2; i < this.getWay().size(); ++i) {
            VueCase firstcase = this.getWay().get(i - 2);
            VueCase drawcase = this.getWay().get(i - 1);
            VueCase lastcase = this.getWay().get(i);
            this.makeH0V0(firstcase, drawcase, lastcase);
            this.makeH0V1(firstcase, drawcase, lastcase);
            this.makeH1V0(firstcase, drawcase, lastcase);
            this.makeH1V1(firstcase, drawcase, lastcase);
            drawcase.update();
        }
    }

    public void assignType() {
        this.makeDirections();
        this.makeTurns();
    }
}

