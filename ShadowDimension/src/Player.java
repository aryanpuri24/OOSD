import bagel.*;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Player extends MovingObject {
    private final static String FAE_LEFT = "res/fae/faeLeft.png";
    private final static String FAE_RIGHT = "res/fae/faeRight.png";
    private final static String FAE_ATTACK_LEFT = "res/fae/faeAttackLeft.png";
    private final static String FAE_ATTACK_RIGHT = "res/fae/faeAttackRight.png";
    private final static int MAX_HEALTH_POINTS = 100;
    private final static double MOVE_SIZE = 2;
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;
    private final static double HEALTH_X = 20;
    private final static double HEALTH_Y = 25;
    private final static int FONT_SIZE = 30;
    private final static int DAMAGE_POINTS = 20;
    private double attackstatecounter;
    private boolean cooldownstate;
    private double cooldowncounter;
    private final static double REFRESH_RATE = 0.06; //units are HZ per millisecond

    public Player(int startX, int startY){

        super(startX,startY,MAX_HEALTH_POINTS,HEALTH_X,HEALTH_Y,FONT_SIZE,DAMAGE_POINTS,FAE_RIGHT);
        this.cooldownstate = false;
    }
    public void update(Input input, Level currentlevel){

        if(input.wasPressed(Keys.A)&&(!isAttackstate())&&(!cooldownstate)){
            setAttackstate(true);
            attackstatecounter = 0;
        }
        if (input.isDown(Keys.UP)){
            setPrevPosition();
            move(0, -MOVE_SIZE);
        } else if (input.isDown(Keys.DOWN)){
            setPrevPosition();
            move(0, MOVE_SIZE);
        } else if (input.isDown(Keys.LEFT)){
            setPrevPosition();
            move(-MOVE_SIZE,0);
            if (isFacingright()) {
                setCurrentImage(new Image(FAE_LEFT));
                setFacingright(!isFacingright());
            }
        } else if (input.isDown(Keys.RIGHT)){
            setPrevPosition();
            move(MOVE_SIZE,0);
            if (!isFacingright()) {
                setCurrentImage(new Image(FAE_RIGHT));
                setFacingright(!isFacingright());
            }
        }
        if(isAttackstate()&&isFacingright()){
            setCurrentImage(new Image(FAE_ATTACK_RIGHT));
        }
        if(isAttackstate()&&(!isFacingright())){
            setCurrentImage(new Image(FAE_ATTACK_LEFT));
        }
        getCurrentImage().drawFromTopLeft(getPosition().x, getPosition().y);
        checkcollisions(this,currentlevel);
        getHealth().renderHealthPoints();
        checkOutOfBounds(this,currentlevel);
        if(isAttackstate()){
            attackstatecounter+= 1;
            faeAttack(currentlevel);
        }
        //checks when the attackstate is over and switches back
        if(attackstatecounter/REFRESH_RATE ==1000){
            setAttackstate(false);
            attackstatecounter += 1;
            if(isFacingright()){
                setCurrentImage(new Image(FAE_RIGHT));
            }else{
                setCurrentImage(new Image(FAE_LEFT));
            }
            cooldownstate = true;
            cooldowncounter = 0;
        }
        if(cooldownstate){
            cooldowncounter+= 1;
        }
        //checks when the cooldownstate is over and switches back
        if(cooldowncounter/REFRESH_RATE == 2000){
            cooldownstate = false;
            cooldowncounter += 1;
        }
        if(isInvinciblestate()){
            setInvinciblestatecounter(getInvinciblestatecounter()+1);
        }
        //checks when the invincible state is over and switches back
        if((getInvinciblestatecounter()/REFRESH_RATE==3000)){
            setInvinciblestate(false);
            setInvinciblestatecounter(1+getInvinciblestatecounter());
        }
    }

    /**
     * Method that performs an attack by fae on the other moving objects
     */

    public void faeAttack(Level currentlevel){
        //only performs the attack in Level 1 in this case, as Level 0 has no objects to attack
        if(currentlevel instanceof Level1){
            Rectangle faebox = getBoundingBox();
            //checks if the demons are attackable
            for(MovingObject current: currentlevel.getMovingobjects()){
                Rectangle objectbox = current.getBoundingBox();
                if((faebox.intersects(objectbox))&&(!current.isInvinciblestate())){
                    //within range and can attack, therfore decreases all health points as required
                    //also sets the object that has been attacked into an invincible state
                    current.getHealth().setHealthpoints(Math.max(current.getHealth().getHealthpoints()
                            - getDAMAGE_POINTS(), 0));
                    System.out.println("Fae inflicts " + getDAMAGE_POINTS() + " damage points on Demon. " +
                            "Demon's current health: " + current.getHealth().getHealthpoints() + "/" +
                            current.getHealth().getMAX_HEALTH_POINTS());
                    if(current.getHealth().isDead()){
                        current.setActive(false);
                    }
                    current.setInvinciblestate(true);
                    current.setInvinciblestatecounter(0);
                }
            }
            //performs the same but for the NAVEC
            Rectangle objectbox = currentlevel.getNavec().getBoundingBox();
            if((faebox.intersects(objectbox))&&(!currentlevel.getNavec().isInvinciblestate())){
                //an attack needs to take place now
                currentlevel.getNavec().getHealth().setHealthpoints(Math.max(
                        currentlevel.getNavec().getHealth().getHealthpoints() - getDAMAGE_POINTS(), 0));
                System.out.println("Fae inflicts " + getDAMAGE_POINTS() + " damage points on Navec. " +
                        "Navec's current health: " + currentlevel.getNavec().getHealth().getHealthpoints() + "/" +
                        currentlevel.getNavec().getHealth().getMAX_HEALTH_POINTS());
                if(currentlevel.getNavec().getHealth().isDead()){
                    //this means that the game is won
                    currentlevel.setLevelcomplete(true);
                }
                currentlevel.getNavec().setInvinciblestate(true);
                currentlevel.getNavec().setInvinciblestatecounter(0);
            }
        }
    }


    public void checkcollisions(MovingObject object, Level currentlevel) {
        Rectangle objectbox = object.getBoundingBox();
        for (Stationairyobject current : currentlevel.getStationairyobjects()) {
            if (current instanceof Wall) {
                Rectangle wallBox = current.getBoundingBox();
                if (objectbox.intersects(wallBox)) {
                    object.moveBack();
                }
            } else if (current instanceof Tree) {
                Rectangle wallBox = current.getBoundingBox();
                if (objectbox.intersects(wallBox)) {
                    object.moveBack();
                }
            } else {
                Rectangle holeBox = current.getBoundingBox();
                if (current.isActive() && objectbox.intersects(holeBox)) {
                    object.getHealth().setHealthpoints(Math.max(object.getHealth().getHealthpoints() -
                            current.getDAMAGE_POINTS(), 0));
                    object.moveBack();
                    current.setActive(false);
                    System.out.println("Sinkhole inflicts " + current.getDAMAGE_POINTS() + " damage points on Fae. " +
                            "Fae's current health: " + object.getHealth().getHealthpoints() + "/" +
                            object.getHealth().getMAX_HEALTH_POINTS());
                }
            }
        }
    }

    public void moveBack(){
        setPosition(getPrevposition());
    }
    public void checkOutOfBounds(MovingObject object,Level currentlevel){
        Point currentPosition = object.getPosition();
        if ((currentPosition.y > currentlevel.getBottomRight().y) || (currentPosition.y < currentlevel.getTopLeft().y) ||
                (currentPosition.x < currentlevel.getTopLeft().x) || (currentPosition.x > currentlevel.getBottomRight().x)){
            object.moveBack();
        }
    }

    /**
     * Method that checks if level0 has been completed
     */
    public boolean reachedGate(){
        return (this.getPosition().x >= WIN_X) && (this.getPosition().y >= WIN_Y);
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(getPosition(), getCurrentImage().getWidth(), getCurrentImage().getHeight());
    }
}