import bagel.util.Point;
import bagel.util.Rectangle;



public abstract class Stationairyobject {
    private final Point position;
    private boolean active;
    private final int DAMAGE_POINTS;
    public Stationairyobject(int startX, int startY, int DAMAGE_POINTS){
        this.position = new Point(startX,startY);
        this.active = true;
        this.DAMAGE_POINTS = DAMAGE_POINTS;
    }

    /**
     * Method that performs a state update
     */
    public abstract void update();

    /**
     * Method that returns a rectangle bounding box for the provided image
     */
    //Function used in collission checking
    public abstract Rectangle getBoundingBox();

    public Point getPosition() {
        return position;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public int getDAMAGE_POINTS() {
        return DAMAGE_POINTS;
    }
}
