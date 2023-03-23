import bagel.util.Point;
import bagel.*;
import bagel.util.Rectangle;

public abstract class MovingObject {
    private Point position;
    private Point prevposition;
    private boolean facingright;
    private double speed; //aka movesize
    private double speedchangefactor;
    //direction is to be stored as an int
    //0 = north, 1 = east, 2 = south and 3 = west
    private int direction;
    //passive to be stored as an int
    //0 = passive and 1 = aggressive
    private int passive;
    private Image currentImage;
    private final Health health;
    private boolean attackstate;
    private boolean invinciblestate;
    private final int DAMAGE_POINTS;
    private boolean active;
    private double invinciblestatecounter;
    private  double ATTACK_RADIUS;
    private Fire fire;

    //constructor that is used more for the attackingobjects (demon and navec)
    public MovingObject(int startX, int startY, int MAX_HEALTH_POINTS, double HEALTH_X, double HEALTH_Y,
                        int FONT_SIZE,int DAMAGE_POINTS,int STATE,double speed,int direction,double ATTACK_RADIUS,
                        String fire){
        this.position = new Point(startX,startY);
        this.health = new Health(MAX_HEALTH_POINTS,HEALTH_X,HEALTH_Y,FONT_SIZE);
        this.attackstate = false;
        this.invinciblestate = false;
        this.DAMAGE_POINTS = DAMAGE_POINTS;
        this.active = true;
        this.passive = STATE;
        this.speed = speed;
        this.speedchangefactor = 0.5*speed;
        this.direction = direction;
        this.ATTACK_RADIUS = ATTACK_RADIUS;
        this.fire = new Fire(fire);
    }
    //this constructor is used for creating a player
    public MovingObject(int startX,int startY,int MAX_HEALTH_POINTS,double HEALTH_X, double HEALTH_Y,
                        int FONT_SIZE,int DAMAGE_POINTS,String imagename){
        this.position = new Point(startX,startY);
        this.health = new Health(MAX_HEALTH_POINTS,HEALTH_X,HEALTH_Y,FONT_SIZE);
        this.attackstate = false;
        this.invinciblestate = false;
        this.DAMAGE_POINTS = DAMAGE_POINTS;
        this.active = true;
        this.currentImage = new Image(imagename);
        this.facingright = true;

    }

    /**
     *Method that performs a state update
     */
    public abstract void update(Input input, Level currentlevel);

    /**
     * Method that moves Object back to previous position
     */
    public abstract void moveBack();

    /**
     * Method that provides a rectangular bounding box for the provided image
     */
    public abstract Rectangle getBoundingBox();
    /**
     * Method that moves object given the direction
     */
    public void move(double xMove, double yMove){
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }

    /**
     * Method that checks if the object is out of the levels specific bounds
     */
    public abstract void checkOutOfBounds(MovingObject object, Level currentlevel);

    /**
     * Method that checks for collission with other objects in the provided level
     */
    public abstract void checkcollisions(MovingObject object, Level currentlevel);

    /**
     * Method that returns the centrepoint coordinate of an object
     */
    public Point centrepoint(){
        double centrex = position.x + currentImage.getWidth();
        double centrey = position.y + currentImage.getHeight();
        return new Point(centrex,centrey);
    }

    /**
     * Method that returns wether the player is inside an objects attackradius
     */
    public boolean inRadius(MovingObject player,double ATTACK_RADIUS){
        Point playercentre = player.centrepoint();
        Point objectcentre = centrepoint();
        double distance = Math.sqrt(power(playercentre.x-objectcentre.x)+power(playercentre.y-objectcentre.y));
        return(distance<=ATTACK_RADIUS);

    }
    /**
     * Method that returns the square of any provided number
     */
    public double power(double x){
        return x*x;
    }

    public Health getHealth(){return health;}

    public Point getPosition(){return position;}

    public Point getPrevposition(){
        return prevposition;
    }
    public int getDirection() {
        return direction;
    }
    public void setFacingright(boolean facingright) {
        this.facingright = facingright;
    }
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }
    public Image getCurrentImage(){return currentImage;}
    public boolean isFacingright() {
        return facingright;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }
    public void setPrevPosition(){
        this.prevposition = new Point(position.x, position.y);
    }
    public int getPassive() {
        return passive;
    }
    public void setPosition(Point position) {
        this.position = position;

    }
    public double getSpeed() {
        return speed;
    }
    public boolean isAttackstate() {
        return attackstate;
    }
    public void setAttackstate(boolean attackstate) {
        this.attackstate = attackstate;
    }
    public boolean isInvinciblestate() {
        return invinciblestate;
    }
    public void setInvinciblestate(boolean invinciblestate) {
        this.invinciblestate = invinciblestate;
    }
    public int getDAMAGE_POINTS() {
        return DAMAGE_POINTS;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public double getSpeedchangefactor() {
        return speedchangefactor;
    }
    public double getInvinciblestatecounter() {
        return invinciblestatecounter;
    }
    public void setInvinciblestatecounter(double invinciblestatecounter) {
        this.invinciblestatecounter = invinciblestatecounter;
    }
    public double getATTACK_RADIUS() {
        return ATTACK_RADIUS;
    }
    public Fire getFire() {
        return fire;
    }

}
