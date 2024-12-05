package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import main.GamePanel;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods{

    private int xLvlOffset;
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private GamePanel gamePanel;

    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    private int xLevelOffset;
    private int leftBorder = (int) (0.4 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.6 * Game.GAME_WIDTH);
    private int levelTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;


    public Playing(Game game, GamePanel gamePanel) {
        super(game);
        this.gamePanel = gamePanel; // Set GamePanel reference
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);

        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (64 * Game.SCALE), game);
        player.LoadLvlData(levelManager.getCurrentLevel().getLvlData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        if (!paused) {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLvlData(),player);
            checkCloseToBorder();
        } else
            pauseOverlay.update();
    }

    private void checkCloseToBorder() {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLevelOffset;

        if (diff > rightBorder)
            xLevelOffset += diff - rightBorder;
        else if (diff < leftBorder)
            xLevelOffset += diff - leftBorder;

        if (xLevelOffset > maxLevelOffsetX)
            xLevelOffset = maxLevelOffsetX;
        else if (xLevelOffset < 0)
            xLevelOffset = 0;

    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);
        enemyManager.draw(g,xLvlOffset);

        if (paused) {
            g.setColor(new Color(0,0,0, 150));
            g.fillRect(0,0,Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){                        // reads inputs
            case KeyEvent.VK_A:                         //VK_( A ) key
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:                         //VK_( D ) key
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:                         //VK_( Space bar ) key
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){                        // reads inputs
            case KeyEvent.VK_A:                         //VK_( A ) key
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:                         //VK_( D ) key
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:                         //VK_( Space bar ) key
                player.setJump(false);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1)
            player.setAttacking(true); // Ensure this is called
    }


    public void mouseDragged(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseDragged(e);

    }

    public void mousePressed(MouseEvent e) {
        if (paused)
            pauseOverlay.mousePressed(e);

        int button = e.getButton();
        if (button == MouseEvent.BUTTON1) { // Left-click
            player.setAttacking(true);
        } else if (button == MouseEvent.BUTTON3) { // Right-click
            getPlayer().shootLaser();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused)
            pauseOverlay.mouseReleased(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(paused)
            pauseOverlay.mouseMoved(e);
        else
            gamePanel.setMouseCoordinates(e.getX(), e.getY());

    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public void unpauseGame() {
        paused = false;
    }

    public int getXLevelOffset() {
        return xLevelOffset;
    }

}
