import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

public class Health {
    private final int MAX_HEALTH_POINTS;
    private double HEALTH_X;
    private double HEALTH_Y;
    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final Font FONT;
    private final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);
    private int healthpoints;
    public Health(int MAX_HEALTH_POINTS, double HEALTH_X,double HEALTH_Y,int FONT_SIZE) {
        this.MAX_HEALTH_POINTS = MAX_HEALTH_POINTS;
        this.HEALTH_X = HEALTH_X;
        this.HEALTH_Y = HEALTH_Y;
        this.FONT = new Font("res/frostbite.ttf", FONT_SIZE);
        this.healthpoints = MAX_HEALTH_POINTS;
        COLOUR.setBlendColour(GREEN);
    }

    /**
     * Method used to check if the health falls below 0, therefore object being dead
     */
    public boolean isDead() {
        return healthpoints <= 0;
    }

    /**
     * Method that is used to render healthpoints on the screen, in provided coordinates and provided colour
     */

    public void renderHealthPoints(){
        double percentageHP = ((double) healthpoints/MAX_HEALTH_POINTS) * 100;
        if (percentageHP <= RED_BOUNDARY){
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY){
            COLOUR.setBlendColour(ORANGE);
        }
        FONT.drawString(Math.round(percentageHP) + "%", HEALTH_X, HEALTH_Y, COLOUR);
        COLOUR.setBlendColour(GREEN);

    }

    /**
     * Method that moves health to newcoordinates, used for moving objects with health
     */
    public void movehealth(double newx,double newy){
        this.HEALTH_X = newx;
        this.HEALTH_Y = newy;
    }
    public int getHealthpoints() {
        return healthpoints;
    }
    public void setHealthpoints(int healthpoints) {
        this.healthpoints = healthpoints;
    }
    public int getMAX_HEALTH_POINTS() {
        return MAX_HEALTH_POINTS;
    }

}




