import bagel.*;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Level1 extends Level{
    private final static String FILE_LEVEL_1 = "res/level1.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background1.png");
    private final static int INSTRUCTION_FONT_SIZE = 40;
    private final Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    private final static int INS_INSTRUCTION_X = 350;
    private final static int INS_INSTRUCTION_Y = 350;
    private final static int X_OFFSET_LINE2 = 30;
    private final static int Y_OFFSET_LINE2 = 45;
    private final static int X_OFFSET_LINE3 = 15;
    private final static int Y_OFFSET_LINE3 = 90;
    private final static String INSTRUCTION_LINE_1 = "PRESS SPACE TO START";
    private final static String INSTRUCTION_LINE_2 = "PRESS A TO ATTACK";
    private final static String INSTRUCTION_LINE_3 = "DEFEAT NAVEC TO WIN";
    private final static double LOWERBOUND = 0.2;
    private final static double UPPERBOUND = 0.7;
    private final static int STATE_BOUND = 2;
    private final static int DIRECTION_BOUND = 4;
    private final Random random = new Random();
    private int timescale;
    private Player player;
    //also sets the timescale to 0, no timescale in level 1 as no moving objects
    public Level1(){
        super();
        this.timescale = 0;
    }
    public void update(Input input, ShadowDimension gameObject){
        if(!(isLevelcomplete())) {
            BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
            for(Stationairyobject current: getStationairyobjects()){
                current.update();
            }
            for(MovingObject current: getMovingobjects()){
                current.update(input,this);
                if(current.inRadius(player, current.getATTACK_RADIUS())){
                    //we draw the fire here
                    //current.getFire().update(player);
                    current.getFire().update(player,current);
                    current.getFire().checkcollission(player,current);
                }
            }
            getNavec().update(input,this);
            if(getNavec().inRadius(player, getNavec().getATTACK_RADIUS())){
                //draw the fire here?
                getNavec().getFire().update(player,getNavec());
                //check if it coliders here??
                getNavec().getFire().checkcollission(player,getNavec());
            }
            player.update(input, this);
            changespeed(input);
            if (player.getHealth().isDead()){
                gameObject.setGameover(true);
                gameObject.setLevel1(false);
            }
        }
        else{
            gameObject.setGamewon(true);
            gameObject.setLevel1(false);
        }
    }

    /**
     * Method that performs a speed change to all moving objects excluding the player in the given level
     */
    public void changespeed(Input input){
        if(input.wasPressed(Keys.L)){
            //the speed is being increased
            if(timescale<=2){
                timescale+= 1;
                System.out.println("Sped up , Speed: " + timescale);
                for(MovingObject current: getMovingobjects()){
                    current.setSpeed(current.getSpeed()+current.getSpeedchangefactor());
                }
                if(getNavec().isActive()){
                    getNavec().setSpeed(getNavec().getSpeed()+getNavec().getSpeedchangefactor());
                }
            }
        }
        if(input.wasPressed(Keys.K)){
            //the speed is being decreased
            if(timescale>=(-2)){
                timescale -= 1;
                System.out.println("Slowed down Speed: " +timescale);
                for(MovingObject current: getMovingobjects()){
                    current.setSpeed(current.getSpeed()-current.getSpeedchangefactor());
                }
                if(getNavec().isActive()){
                    getNavec().setSpeed(getNavec().getSpeed()-getNavec().getSpeedchangefactor());
                }
            }
        }
    }

    public void readCSV(){
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_LEVEL_1))){
            String line;
            while((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Player":
                        player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Navec":
                        //randoises the speed, and direction of the Navec
                        double navecspeed = random.nextDouble();
                        while(!((LOWERBOUND<=navecspeed) && (navecspeed<=UPPERBOUND))){
                            navecspeed = random.nextDouble();
                        }
                        int navecdirection = random.nextInt(DIRECTION_BOUND);
                        setNavec(new Navec(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]),navecspeed,navecdirection));
                        break;
                    case"Tree":
                        getStationairyobjects().add(new Tree(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2])));
                        break;
                    case "Demon":
                        //randomises the speed, direction and state of the Demon
                        int state = random.nextInt(STATE_BOUND);
                        double speed = random.nextDouble();
                        while(!((LOWERBOUND<=speed) && (speed<=UPPERBOUND))){
                            speed = random.nextDouble();
                        }
                        int direction = random.nextInt(DIRECTION_BOUND);
                        getMovingobjects().add(new Demon(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]),state,speed,direction));
                        break;
                    case "Sinkhole":
                        getStationairyobjects().add(new Sinkhole(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2])));
                        break;
                    case "TopLeft":
                        setTopLeft(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                    case "BottomRight":
                        setBottomRight(new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2])));
                        break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
    public void drawStartScreen(){
        INSTRUCTION_FONT.drawString(INSTRUCTION_LINE_1,INS_INSTRUCTION_X,INS_INSTRUCTION_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_LINE_2,INS_INSTRUCTION_X+X_OFFSET_LINE2,
                INS_INSTRUCTION_Y+Y_OFFSET_LINE2);
        INSTRUCTION_FONT.drawString(INSTRUCTION_LINE_3,INS_INSTRUCTION_X+X_OFFSET_LINE3,
                INS_INSTRUCTION_Y+Y_OFFSET_LINE3);
    }
    public void drawMessage(String message){
    }

}
