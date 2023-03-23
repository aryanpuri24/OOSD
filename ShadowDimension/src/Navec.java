import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Navec extends MovingObject{
    private final static String NAVEC_LEFT = "res/navec/navecLeft.png";
    private final static String NAVEC_RIGHT = "res/navec/navecRight.png";
    private final static String NAVEC_INVC_LEFT = "res/navec/navecInvincibleLeft.png";
    private final static String NAVEC_INVC_RIGHT = "res/navec/navecInvincibleRight.png";
    private final static String NAVEC_FIRE = "res/navec/navecFire.png";
    private final static int MOVING_NORTH = 0;
    private final static int MOVING_EAST = 1;
    private final static int MOVING_SOUTH = 2;
    private final static int MOVING_WEST = 3;
    private final static int MAX_HEALTH_POINTS = 80;
    private final static int FONT_SIZE = 15;
    private final static int HEALTH_Y_OFFSET = -6;
    private final static int STATE = 1;
    private final static int DAMAGE_POINTS = 20;
    private final static double REFRESH_RATE = 0.06; //units are HZ per millisecond
    private final static double ATTACK_RADIUS = 200;
    public Navec(int startX, int startY,double speed,int direction){
        super(startX,startY,MAX_HEALTH_POINTS,startX,startY+HEALTH_Y_OFFSET,FONT_SIZE,DAMAGE_POINTS,
                STATE,speed,direction,ATTACK_RADIUS,NAVEC_FIRE);
        //if the direction is west, sets the image facing left, otherwise right
        if(direction==3){
            setCurrentImage(new Image(NAVEC_LEFT));
            setFacingright(false);
        }else{
            setCurrentImage(new Image(NAVEC_RIGHT));
            setFacingright(true);
        }
    }
    public void update(Input input, Level currentlevel){

            if(getDirection()==MOVING_NORTH){
                setPrevPosition();
                move(0,-getSpeed());
                getHealth().movehealth(getPosition().x,getPosition().y);
            } else if (getDirection()==MOVING_SOUTH) {
                setPrevPosition();
                move(0,getSpeed());
                getHealth().movehealth(getPosition().x,getPosition().y);
            } else if (getDirection()==MOVING_EAST) {
                setPrevPosition();
                move(getSpeed(),0);
                getHealth().movehealth(getPosition().x,getPosition().y);
            }else{
                setPrevPosition();
                move(-getSpeed(),0);
                getHealth().movehealth(getPosition().x,getPosition().y);
            }
            getHealth().renderHealthPoints();
            if(isInvinciblestate()){
                if(isFacingright()){
                    setCurrentImage(new Image(NAVEC_INVC_RIGHT));
                }
                else{
                    setCurrentImage(new Image(NAVEC_INVC_LEFT));
                }
            setInvinciblestatecounter(1+getInvinciblestatecounter());
            }
            getCurrentImage().drawFromTopLeft(getPosition().x,getPosition().y);
            checkOutOfBounds(this,currentlevel);
            checkcollisions(this,currentlevel);

            if(getInvinciblestatecounter()/REFRESH_RATE==3000){
                setInvinciblestate(false);
                setInvinciblestatecounter(1+getInvinciblestatecounter());
                if(isFacingright()){
                    setCurrentImage(new Image(NAVEC_RIGHT));
                }else{
                    setCurrentImage(new Image(NAVEC_LEFT));
                }
            }
    }

    public void checkcollisions(MovingObject object, Level currentlevel){
        Rectangle objectbox = object.getBoundingBox();

        for(Stationairyobject current: currentlevel.getStationairyobjects()){
            Rectangle wallbox = current.getBoundingBox();
            if(objectbox.intersects(wallbox)){
                object.moveBack();
            }
        }
    }
    public void checkOutOfBounds(MovingObject object,Level currentlevel){
        Point currentPosition = object.getPosition();
        if ((currentPosition.y > currentlevel.getBottomRight().y) || (currentPosition.y < currentlevel.getTopLeft().y) ||
                (currentPosition.x < currentlevel.getTopLeft().x) || (currentPosition.x > currentlevel.getBottomRight().x)){
            object.moveBack();
        }
    }
    //moveback function is used here to swtich directions
    public void moveBack(){
        if(getDirection()==MOVING_NORTH){
            setDirection(MOVING_SOUTH);
        } else if (getDirection()==MOVING_SOUTH) {
            setDirection(MOVING_NORTH);
        }else if(getDirection()==MOVING_EAST){
            //we also need to change the image,
            setCurrentImage(new Image(NAVEC_LEFT));
            setFacingright(false);
            setDirection(MOVING_WEST);
        }else{
            setCurrentImage(new Image(NAVEC_RIGHT));
            setFacingright(true);
            setDirection(MOVING_EAST);
        }
    }
    public Rectangle getBoundingBox(){
        return new Rectangle(getPosition(), getCurrentImage().getWidth(), getCurrentImage().getHeight());
    }
}
