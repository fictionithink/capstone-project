package utils;

import entities.Worker;
import main.Game;

import static utils.Constants.EnemyConstants.WORKER;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadSave {

    public static final String PLAYER_ATLAS = "Cyborg_Spritesheet.png";
    public static final String LEVEL_ATLAS = "IndustrialTile_0.png";
//    public static final String LEVEL_1_DATA = "level_1_data.png";
    public static final String LEVEL_1_DATA = "level_1_data_long.png";
    public static final String CYBER_ARM = "cyber_arm.png";
    public static final String MENU_BUTTONS = "menu_atlas.png";
    public static final String MENU_BACKGROUND = "citiciti.jpg";
    public static final String ENEMY_WORKER_LEFT = "man_left_spritesheet.png";
    public static final String ENEMY_WORKER_RIGHT = "man_right_spritesheet.png";
    public static final String MENU_TITLE = "pathfinder.png";
    public static final String LASER_BEAM_SPRITE = "laser_beam.png";
    public static final String PAUSE_BACKGROUND = "pause menu.png";
    public static final String SOUND_BUTTONS = "music atlas.png";
    public static final String URM_BUTTONS = "pause atlas.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String STATUS_BAR_FULL = "full_health_bar.png";

    public static final String STATUS_BAR_EMPTY = "empty_health_bar.png";


    public static BufferedImage getSpriteAtlas(String fileName) {
        BufferedImage img;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try{
           img = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("image not found?! what the sigma???");
            throw new RuntimeException(e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                System.err.println("InputStream *is* failed to close!");
            }
        }
        return img;
    }

    public static ArrayList<Worker> GetWorkers(){
        BufferedImage img = getSpriteAtlas(LEVEL_1_DATA);
        ArrayList<Worker> list =    new ArrayList<>();

        for(int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if(value == WORKER)
                    list.add(new Worker(i* Game.TILES_SIZE, j* Game.TILES_SIZE));

            }
        }
        return list;
    }

    public static int[][] GetLevelData(){
        BufferedImage img = getSpriteAtlas(LEVEL_1_DATA);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

        for(int j = 0; j < img.getHeight(); j++){
            for(int i = 0; i < img.getWidth(); i++){
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if(value >= 12){
                    value = 0;
                }
                lvlData[j][i] = value;
            }
        }
        return lvlData;
    }

    public static BufferedImage getArmSprite() {
        return getSpriteAtlas(CYBER_ARM);
    }

}
