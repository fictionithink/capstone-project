package entities;


import main.Game;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public class Worker extends Enemy {

    public Worker(float x, float y) {
        super(x, y, WORKER_WIDTH, WORKER_HEIGHT, WORKER);

        // Align hitbox size and offset
        initHitbox(x+30, y + (int)(14 * Game.SCALE), (int)(30 * Game.SCALE), (int)(28.5 * Game.SCALE));
    }

    public void update(int[][] lvlData, Player player) {
        updateMove(lvlData,player);
        updateAnimationTick();
    }

    private void updateMove(int[][] lvlData, Player player) {
        if (firstUpdate)
            firstUpdateCheck(lvlData);

        if (inAir) {
            updateInAir(lvlData);
        } else {
            // Movement logic
            switch (enemyState) {
                case IDLE:
                    newState(RUNNING);
                    break;

                case RUNNING:
                        if(canSeePlayer(lvlData,player))
                            turnTowardsPlayer(player);
                        if(isPlayerCloseForAttack(player))
                            newState(ATTACK);
                        move(lvlData);
                    break;
            }
        }
    }
}

