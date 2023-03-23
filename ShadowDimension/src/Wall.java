import bagel.Image;
import bagel.util.Rectangle;
public class Wall extends Stationairyobject  {
    private final Image WALL = new Image("res/wall.png");
    private final static int DAMAGE_POINTS = 0;
    public Wall(int startX, int startY){
        super(startX,startY,DAMAGE_POINTS);
    }
    public void update() {
        WALL.drawFromTopLeft(getPosition().x,getPosition().y);
    }
    public Rectangle getBoundingBox(){
        return new Rectangle(getPosition(), WALL.getWidth(), WALL.getHeight());
    }
}