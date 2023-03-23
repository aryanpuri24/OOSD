import bagel.*;
import bagel.util.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level0 extends Level {
        private final static String GAME_TITLE = "SHADOW DIMENSION";
        private final static String FILE_LEVEL_0 = "res/level0.csv";
        private final Image BACKGROUND_IMAGE = new Image("res/background0.png");
        private final static int TITLE_FONT_SIZE = 75;
        private final static int INSTRUCTION_FONT_SIZE = 40;
        private final static int TITLE_X = 260;
        private final static int TITLE_Y = 250;
        private final static int INS_X_OFFSET = 90;
        private final static int INS_Y_OFFSET = 190;
        private final static int SECOND_X_OFFSET = -50;
        private final static int SECOND_Y_OFFSET = 45;
        private final Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
        private final Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
        private final static String INSTRUCTION_MESSAGE_LINE_2 = "USE ARROW KEYS TO FIND GATE";
        private final static String INSTRUCTION_MESSAGE_LINE_1 = "PRESS SPACE TO START";
        private final static String LEVEL_COMPLETE_MESSAGE = "LEVEL COMPLETE!";
        private double endmessagecounter;
        private Player player;
        public Level0(){
            super();
        }
        public void readCSV(){
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_LEVEL_0))){
                String line;
                while((line = reader.readLine()) != null){
                    String[] sections = line.split(",");
                    switch (sections[0]) {
                        case "Player":
                            player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                            break;
                        case "Wall":
                            getStationairyobjects().add(new Wall(Integer.parseInt(sections[1]),Integer.parseInt(sections[2])));
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
        public void update(Input input, ShadowDimension gameObject) {
            if(!(isLevelcomplete())){
                BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
                for(Stationairyobject current: getStationairyobjects()){
                    current.update();
                }
                player.update(input,this);
                if (player.getHealth().isDead()){
                    gameObject.setGameover(true);
                    gameObject.setLevel0(false);
                }
                if (player.reachedGate()){
                    setLevelcomplete(true);
                    endmessagecounter = 1;
                }
            }
            else{
                endmessagecounter += 1;
                drawMessage(LEVEL_COMPLETE_MESSAGE);
            }
        }
        public void drawStartScreen(){
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE_1,TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
            INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE_LINE_2,TITLE_X+INS_X_OFFSET+SECOND_X_OFFSET,
                    TITLE_Y+INS_Y_OFFSET+SECOND_Y_OFFSET);
        }
        public void drawMessage(String message){
            TITLE_FONT.drawString(message, (Window.getWidth()/2.0 - (TITLE_FONT.getWidth(message)/2.0)),
                    (Window.getHeight()/2.0 + (TITLE_FONT_SIZE/2.0)));
        }
        public double getEndmessagecounter() {
            return endmessagecounter;
        }
        public void setEndmessagecounter(double endmessagecounter) {
            this.endmessagecounter = endmessagecounter;
        }

}

