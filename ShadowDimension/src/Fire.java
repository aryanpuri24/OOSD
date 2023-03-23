import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;


public class Fire {
    private final Image FIRE;
    private Point position;
    private DrawOptions ROTATION = new DrawOptions();
    private final static double TOPRIGHTROTATION = (Math.PI)/2;
    private final static double BOTTOMRIGHTROTATION = Math.PI;
    private final static double BOTTOMLEFTROTATION = (3*Math.PI)/2;
    public Fire(String imagename){
        this.FIRE = new Image(imagename);
    }


    /**
     * Method that performs a stateupdate
     */
    public void update(Player player,MovingObject enemy){
        if(enemy.isActive()){
            //finds the centre of the player and the enemy to calculate where to draw the fire from
            double playercoordinatex = player.getPosition().x+(player.getCurrentImage().getWidth()/2);
            double playercoordinatey = player.getPosition().y+(player.getCurrentImage().getHeight()/2);
            double enemycoordinatex = enemy.getPosition().x +(enemy.getCurrentImage().getWidth()/2);
            double enemycoordinatey = enemy.getPosition().y + (enemy.getCurrentImage().getHeight()/2);
            if((playercoordinatex<=enemycoordinatex)&&(playercoordinatey<=enemycoordinatey)){
                //drawn from the top left
                this.setPosition(new Point(enemy.getPosition().x-this.FIRE.getWidth(),enemy.getPosition().y-this.FIRE.getHeight()));
                this.FIRE.drawFromTopLeft(getPosition().x, getPosition().y);
            } else if ((playercoordinatex<=enemycoordinatex)&&(playercoordinatey>enemycoordinatey)) {
                //drawn from the bottomleft
                this.setPosition(new Point(enemy.getPosition().x-this.FIRE.getWidth(),enemy.getPosition().y+enemy.getCurrentImage().getHeight()));
                this.setROTATION(getROTATION().setRotation(BOTTOMLEFTROTATION));
                this.FIRE.drawFromTopLeft(getPosition().x, getPosition().y,getROTATION());
            } else if ((playercoordinatex>enemycoordinatex)&&(playercoordinatey<=enemycoordinatey)) {
                //drawn from the topright;
                this.setPosition(new Point(enemy.getPosition().x+enemy.getCurrentImage().getWidth(),enemy.getPosition().y-this.FIRE.getHeight()));
                this.setROTATION(getROTATION().setRotation(TOPRIGHTROTATION));
                this.FIRE.drawFromTopLeft(getPosition().x, getPosition().y,getROTATION());
            }else{
                //drawn from the bottomright;
                this.setPosition(new Point(enemy.getPosition().x+enemy.getCurrentImage().getWidth(),
                        enemy.getPosition().y+enemy.getCurrentImage().getHeight()));
                this.setROTATION(getROTATION().setRotation(BOTTOMRIGHTROTATION));
                this.FIRE.drawFromTopLeft(getPosition().x, getPosition().y,getROTATION());
            }
        }
    }

    /**
     * Method that checks collision of player with a demon's fire or Navec's fire(moving objects fire)
     */
    public void checkcollission(Player player, MovingObject fireobject){
        Rectangle playerbox = player.getBoundingBox();
        Rectangle firebox = fireobject.getFire().getBoundingBox();
        if(fireobject instanceof Demon){
            if(fireobject.isActive()&&(playerbox.intersects(firebox))&&(!player.isInvinciblestate())){
                //damage occurs
                player.getHealth().setHealthpoints(Math.max(player.getHealth().getHealthpoints() - fireobject.getDAMAGE_POINTS(), 0));
                player.setInvinciblestate(true);
                player.setInvinciblestatecounter(0);
                System.out.println("Demon inflicts " + fireobject.getDAMAGE_POINTS() + " damage points on Fae. " +
                        "Fae's current health: " + player.getHealth().getHealthpoints() + "/" +
                        player.getHealth().getMAX_HEALTH_POINTS());
                }
        }else{
            if(fireobject.isActive()&&(playerbox.intersects(firebox))&&(!player.isInvinciblestate())){
                //damage occurs
                player.getHealth().setHealthpoints(Math.max(player.getHealth().getHealthpoints() - fireobject.getDAMAGE_POINTS(), 0));
                player.setInvinciblestate(true);
                player.setInvinciblestatecounter(0);
                System.out.println("Navec inflicts " + fireobject.getDAMAGE_POINTS() + " damage points on Fae. " +
                        "Fae's current health: " + player.getHealth().getHealthpoints() + "/" +
                        player.getHealth().getMAX_HEALTH_POINTS());
                }
        }
    }


    /**
     * Method that returns a bounding box for the fire
     */
    public Rectangle getBoundingBox(){
        return new Rectangle(position, FIRE.getWidth(),FIRE.getHeight());
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public void setROTATION(DrawOptions ROTATION) {
        this.ROTATION = ROTATION;
    }
    public DrawOptions getROTATION() {
        return ROTATION;
    }
}
