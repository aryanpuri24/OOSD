import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2022
 * Please enter your name below
 * Aryan Puri - 1260676
 * Credit and acknowledge 2021 Project 1 Solutions by Tharun Dharmawickrema
 *
 */
public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static String END_MESSAGE = "GAME OVER!";
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static double REFRESH_RATE = 0.06; //units are HZ per millisecond
    private boolean introscreen;
    private boolean level0;
    private boolean level1;
    private boolean gameover;
    private boolean gamewon;
    private boolean level1introscreen;
    private final static int TITLE_FONT_SIZE = 75;
    private final Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    private final Level0 firstlevel;
    private final Level1 secondlevel;
    public ShadowDimension(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        this.introscreen = true;
        this.level0 = false;
        this.level1 = false;
        this.gameover = false;
        this.gamewon = false;
        this.level1introscreen = false;
        this.firstlevel = new Level0();
        firstlevel.readCSV();
        this.secondlevel = new Level1();
        secondlevel.readCSV();

    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        if(introscreen){
            firstlevel.drawStartScreen();
            if (input.wasPressed(Keys.SPACE)){
                introscreen = false;
                level0 = true;
            }
        }
        if (gameover){
            drawMessage(END_MESSAGE);
        }
        if(level0){
            firstlevel.update(input,this);
        }
        if(((firstlevel.getEndmessagecounter())/REFRESH_RATE)==3000){
            level0 = false;
            level1introscreen = true;
            firstlevel.setEndmessagecounter(0);
        }
        if(level1introscreen){
            secondlevel.drawStartScreen();
            if(input.wasPressed(Keys.SPACE)){
                level1introscreen = false;
                level1 = true;
            }
        }
        if(level1){
            secondlevel.update(input,this);
        }
        if(gamewon){
            drawMessage(WIN_MESSAGE);
        }
    }
    public void setLevel0(boolean level0) {
        this.level0 = level0;
    }
    public void setLevel1(boolean level1){
        this.level1 = level1;
    }
    public void setGameover(boolean gameover) {
        this.gameover = gameover;
    }
    public void setGamewon(boolean gamewon) {
        this.gamewon = gamewon;
    }

    /**
     * Method that draws a message - used for win and loss transitions
     */
    public void drawMessage(String message){
        TITLE_FONT.drawString(message, (Window.getWidth()/2.0 - (TITLE_FONT.getWidth(message)/2.0)),
                (Window.getHeight()/2.0 + (TITLE_FONT_SIZE/2.0)));
    }

}

