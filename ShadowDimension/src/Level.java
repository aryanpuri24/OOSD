import bagel.Input;
import bagel.util.Point;
import java.util.ArrayList;
public abstract class Level {
    private boolean levelcomplete;
    private Point topLeft;
    private Point bottomRight;
    private Navec navec;
    private final ArrayList<Stationairyobject> stationairyobjects = new ArrayList<>();
    private final ArrayList<MovingObject> movingobjects = new ArrayList<>();
    public Level(){
        this.levelcomplete = false;
    }

    /**
     *Method that performs a state update
     */
    public abstract void update(Input input, ShadowDimension gameObject);

    /**
     * Method that reads a given file into the level
     */
    public abstract void readCSV();

    /**
     * Method used to draw the start screen
     */
    public abstract void drawStartScreen();

    /**
     * Method used to draw a provided string on the screen
     */
    public abstract void drawMessage(String message);
    public boolean isLevelcomplete() {
        return levelcomplete;
    }
    public void setLevelcomplete(boolean levelcomplete) {
        this.levelcomplete = levelcomplete;
    }
    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }
    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }
    public Point getTopLeft() {
        return topLeft;
    }
    public Point getBottomRight() {
        return bottomRight;
    }
    public ArrayList<Stationairyobject> getStationairyobjects() {
        return stationairyobjects;
    }
    public ArrayList<MovingObject> getMovingobjects(){
        return movingobjects;
    }
    public Navec getNavec() {
        return navec;
    }
    public void setNavec(Navec navec) {
        this.navec = navec;
    }
}
