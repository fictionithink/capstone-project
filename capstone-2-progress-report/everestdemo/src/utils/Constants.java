package utils;

import main.Game;

public class Constants {

    public static class EnemyConstants{
        public static final int WORKER = 0;

        public static final  int IDLE =0;
        public static final  int RUNNING =1;
        public static final  int HURT =2;
        public static final  int DEAD =3;
        public static final  int ATTACK =4;

        public static final  int WORKER_WIDTH_DEFAULT=48;
        public static final  int WORKER_HEIGHT_DEFAULT=48;

        public static final int WORKER_WIDTH = (int)(WORKER_WIDTH_DEFAULT * Game.SCALE);
        public static final int WORKER_HEIGHT = (int)(WORKER_HEIGHT_DEFAULT * Game.SCALE);

        public static final int WORKER_DRAWOFFSET_X = (int)(48 * Game.SCALE);
        public static final int WORKER_DRAWOFFSET_Y = (int)(48 * Game.SCALE);


        public static int GetSpriteAmount(int enemy_type,int enemy_state){

            switch(enemy_type){
                case WORKER:
                    switch (enemy_state){
                        case IDLE:
                                return 3;
                        case RUNNING:
                        case DEAD:
                        case ATTACK:
                            return 5;
                        case HURT:
                            return 1;
                    }
            }

            return 0;
        }
    }

    public static class UI {
        public static class Buttons{
            public static final int B_WIDTH_DEFAULT = 96;
            public static final int B_HEIGHT_DEFAULT = 32;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 16;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons {
            public static final int URM_DEFAULT_SIZE = 16;
            public static final int URM_SIZE = (int)(URM_DEFAULT_SIZE * Game.SCALE);
        }

        public static class VolumeButtons {
            public static final int VOLUME_DEFAULT_WIDTH = 28;
            public static final int VOLUME_DEFAULT_HEIGHT = 43;
            public static final int SLIDER_DEFAULT_WIDTH = 215;

            public static final int VOLUME_WIDTH = (int)(VOLUME_DEFAULT_WIDTH * Game.SCALE/2);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_DEFAULT_HEIGHT * Game.SCALE/2);
            public static final int SLIDER_WIDTH = (int)(SLIDER_DEFAULT_WIDTH * Game.SCALE/2);

        }
    }

    public static class Directions {
        public static final int LEFT = 0;
        public static final int RIGHT = 1;
        public static final int UP = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMPING = 2;
        public static final int HURT = 3;
        public static final int DEATH = 4;
        public static final int PUNCHING = 5;
        public static final int KICKING = 6;
        public static final int RUNNING_ATTACK = 7;
        public static final int HEAVY_ATTACK_1 = 8;
        public static final int HEAVY_ATTACK_2 = 9;

        public static int GetSpriteAmount(int player_action){
            switch (player_action) {
                case HURT:
                    return 2;
                case IDLE:
                case JUMPING:
                    return 4;
                case PUNCHING:
                    return 5;
                case RUNNING:
                case DEATH:
                case KICKING:
                case RUNNING_ATTACK:
                    return 6;
                case HEAVY_ATTACK_1:
                case HEAVY_ATTACK_2:
                    return 8;
                default:
                    return 1;
            }
        }
    }

}
