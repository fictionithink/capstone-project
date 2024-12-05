package main;

// this class holds the entirety of the game

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;
import inputs.MouseInputs;

import java.awt.*;

public class Game implements Runnable{

// Fields
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    private Playing playing;
    private Menu menu;

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 2.0f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int  TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    private boolean initialized = false;
    private boolean isFirstRender = true; // Flag to track the first render cycle

// Constructor (Game)
    public Game() {
        System.out.println("Initializing GamePanel...");
        gamePanel = new GamePanel(this);
        System.out.println("GamePanel reference in Game: " + gamePanel);

        gamePanel.addMouseListener(new MouseInputs(gamePanel));  // Creating MouseInputs instance

        System.out.println("Initializing GameWindow...");
        gameWindow = new GameWindow(gamePanel);

        System.out.println("Initializing Classes...");
        initClasses();  // Initialize menu and other components

        initialized = true;  // Set this flag after everything is initialized

        System.out.println("Game Initialized");
        Gamestate.state = Gamestate.MENU;  // Set state to MENU after initialization

        gamePanel.requestFocus();

        gamePanel.enableMouseInput();  // Enable mouse input after the first render

//        mouseInputs = new MouseInputs(gamePanel);  // Initialize MouseInputs
//        gamePanel.addMouseMotionListener(mouseInputs);  // Add mouse motion listener
//        mouseInputs.setGameInitialized(true);  // Now that everything is initialized, set this flag

        // Start the game loop after initialization
        startGameLoop();
    }

    private void initClasses() {
        System.out.println("Initializing Menu...");
        menu = new Menu(this);
        System.out.println("Menu initialized: " + (menu != null));

        playing = new Playing(this, gamePanel);
        System.out.println("Playing initialized: " + (playing != null));
    }


    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update() {
        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTIONS:
            case ABOUT:
            case EXIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void render(Graphics g) {
        if (isFirstRender) {
            if (menu == null || !menu.isInitialized()) {
                return;
            }
            isFirstRender = false;

            // this statement above is to stop sys_err messages na unecessary, because once ma fully loaded ang game nothing happens

            gamePanel.enableMouseInputAfterRender();
        }

        switch (Gamestate.state) {
            case MENU:
                if (menu != null) {
                    menu.draw(g);
                } else {
                    System.err.println("Error: 'menu' is null in Game.render()");
                }
                break;
            case PLAYING:
                if (playing != null) {
                    playing.draw(g);
                } else {
                    System.err.println("Error: 'playing' is null in Game.render()");
                }
                break;
            default:
                System.err.println("Error: Unknown Gamestate: " + Gamestate.state);
                break;
        }
    }

    @Override
    public void run() {

        double timePerFrame = 1000000000.0/FPS_SET;
        double timePerUpdate = 1000000000.0/UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while(true){
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1){
                gamePanel.repaint();
                frames++;
                deltaF--;
            }


            if(System.currentTimeMillis() - lastCheck >= 1000){
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames +  " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }

        }


    }

    public void windowFocusLost() {
        if(Gamestate.state == Gamestate.PLAYING){
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu(){
        return menu;
    }

    public Playing getPlaying(){
        return playing;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }


}
