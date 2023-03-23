import bagel.Image;
import bagel.util.Rectangle;

public class Tree extends Stationairyobject{
    private final Image TREE = new Image("res/tree.png");
    private final static int DAMAGE_POINTS = 0;
    public Tree(int startX, int startY){
        super(startX,startY,DAMAGE_POINTS);
    }
    public void update(){TREE.drawFromTopLeft(getPosition().x, getPosition().y);}
    public Rectangle getBoundingBox(){
        return new Rectangle(getPosition(),TREE.getWidth(),TREE.getHeight());
    }
}