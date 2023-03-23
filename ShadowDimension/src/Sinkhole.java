import bagel.Image;
import bagel.util.Rectangle;

public class Sinkhole extends Stationairyobject {
    private final Image SINKHOLE = new Image("res/sinkhole.png");
    private final static int DAMAGE_POINTS = 30;

    public Sinkhole(int startX, int startY){
        super(startX,startY,DAMAGE_POINTS);
    }
    public void update() {
        if (isActive()){
            SINKHOLE.drawFromTopLeft(getPosition().x,getPosition().y);
        }
    }
    public Rectangle getBoundingBox(){
        return new Rectangle(getPosition(), SINKHOLE.getWidth(), SINKHOLE.getHeight());
    }

}