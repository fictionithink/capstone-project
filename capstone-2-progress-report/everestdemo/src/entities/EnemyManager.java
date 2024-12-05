package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] workerArr;
    private ArrayList<Worker> workers = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
        addEnemies();
    }

    public void update(int[][] lvlData,Player player) {
        for (Worker w : workers) {
            w.update(lvlData,player);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawWorker(g, xLevelOffset);
    }

    private void drawWorker(Graphics g, int xLevelOffset) {
        for (Worker w : workers) {
            int verticalOffset = (int)(17 * Game.SCALE);

            g.drawImage(workerArr[w.getEnemyState()][w.getAniIndex()],
                    (int) w.getHitbox().x - xLevelOffset,
                    (int) w.getHitbox().y - verticalOffset,
                    WORKER_WIDTH,
                    WORKER_HEIGHT,
                    null);

            // Draw the worker's hitbox for debugging
            w.drawHitBox(g, xLevelOffset);
        }
    }


    private void loadEnemyImgs() {
        workerArr = new BufferedImage[5][5];


        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.ENEMY_WORKER_RIGHT);

        for (int j = 0; j < workerArr.length; j++) {
            for (int k = 0; k < workerArr[j].length; k++) {
                workerArr[j][k] = temp.getSubimage(
                        k * WORKER_WIDTH_DEFAULT,
                        j * WORKER_HEIGHT_DEFAULT,
                        WORKER_WIDTH_DEFAULT,
                        WORKER_HEIGHT_DEFAULT
                );
            }
        }
    }

    private void addEnemies() {
        workers = LoadSave.GetWorkers();
        System.out.println("size of enemies: " + workers.size());
    }
}
