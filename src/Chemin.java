import java.util.ArrayList;
import java.util.List;

public class Chemin {

    private VueCase start = null;
    private VueCase stop = null;
    private final List<VueCase> way = new ArrayList<>();

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

    public void setStop(VueCase stop) {
        this.stop = stop;
        this.addCase(stop);
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

    public List<VueCase> getWayWithoutStartAndStop() {
        List<VueCase> chemin = new ArrayList<>(this.way);
        chemin.remove(0);
        if (this.getStop() != null) chemin.remove(chemin.size() - 1);
        return chemin;
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.v0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() + 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() - 1)
                && drawcase.getCase().isNotTurn()) {
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.h1v0
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        } else if (firstcase.getCase().getType() == CaseType.h1v0
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h0v1);
        }
        drawcase.update();
    }

    public void makeH1V0(VueCase firstcase, VueCase drawcase, VueCase lastcase) {

        if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.v0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h1v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().getX() == firstcase.getCase().getX()
                && drawcase.getCase().getY() == lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().getX() == (firstcase.getCase().getX() - 1)
                && drawcase.getCase().getY() == (lastcase.getCase().getY() + 1)
                && drawcase.getCase().isNotTurn()) {
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v0);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().getType() == CaseType.h0v1
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().isLocked()
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() < lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0h1
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() > lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.h0v0
                && lastcase.getCase().isLocked()
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
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
                && drawcase.getCase().isNotTurn()) {
            drawcase.getCase().setType(CaseType.h1v1);
        } else if (firstcase.getCase().getType() == CaseType.v0v1
                && lastcase.getCase().getType() == CaseType.h0h1
                && firstcase.getCase().getX() < lastcase.getCase().getX()
                && firstcase.getCase().getY() > lastcase.getCase().getY()
                && drawcase.getCase().isNotTurn()) {
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

    public void assignType(List<Chemin> chemins) {
        this.makeDirections();
        this.makeTurns();
        this.makeCross(chemins);
    }

    public void deleteChemin(List<Chemin> chemins) {
        //On supprime chaque case du chemin
        int index = this.getStop() == null ? 0 : 1;
        for (int i = 1; i < this.getWay().size() - index; ++i) {
            if (this.getWay().get(i).getCase().getType() == CaseType.cross) {
                for (int j = 0; j < chemins.size(); ++j) {
                    if (chemins.get(j) != this && chemins.get(j).getStop() != null) {
                        for (int k = 1; k < chemins.get(j).getWay().size() - 1; ++k) {
                            if (chemins.get(j).getWay().get(k).getCase().getType() == CaseType.cross
                                    && chemins.get(j).getWay().get(k) == this.getWay().get(i)) {
                                chemins.get(j).getWay().get(k).setStarter(chemins.get(j).getStart());
                                if (chemins.get(j).getStart() == chemins.get(j).getWay().get(k).getOtherStarter()) {
                                    chemins.get(j).getWay().get(k).getCase().setType(chemins.get(j).getWay().get(k).getLastType());
                                } else {
                                    chemins.get(j).getWay().get(k).getCase().setType(chemins.get(j).getWay().get(k).getFirstType());
                                }
                            }
                        }
                    }
                }
            } else {
                this.getWay().get(i).getCase().setType(CaseType.empty);
            }
            this.getWay().get(i).update();
        }
    }

    public void makeCross(List<Chemin> chemins) {
        chemins.remove(this);
        //On vérifie qu'il y ait plus de 1 chemin
        if (chemins.size() > 0) {
            for (Chemin way : chemins) {
                //On vérifie que les chemins sont bien terminés
                for (VueCase commoncase : way.getWay()) {
                    if (this.getWay().contains(commoncase) && !commoncase.isLocked()) {
                        commoncase.sendCrossToDraw(way);
                    }
                }
            }
        }
        chemins.add(this);
    }

    public boolean checkGoodChemin() {
        if (this.getStart() != null && this.getWay().size() > 2) {
            for (int i = 0; i < this.getWay().size() - 1; ++i) {

                //On vérifie que toutes les cases du chemin se suivent
                if (!(this.getWay().get(i).getCase().getX() == (this.getWay().get(i + 1).getCase().getX() + 1))
                        && !(this.getWay().get(i).getCase().getX() == (this.getWay().get(i + 1).getCase().getX() - 1))
                        && !(this.getWay().get(i).getCase().getY() == (this.getWay().get(i + 1).getCase().getY() + 1))
                        && !(this.getWay().get(i).getCase().getY() == (this.getWay().get(i + 1).getCase().getY() - 1))) {
                    return false;
                }
            }
        }
        return true;
    }

}

