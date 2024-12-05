package gamestates;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static main.Game.*;

public class Menu extends State implements Statemethods{

    private MenuButton[] buttons = new MenuButton[4];
    private BufferedImage backgroundImg, backgroundTitle;
    private int menuX, menuY, menuWidth, menuHeight;

    public boolean isInitialized() {
        return isInitialized;
    }

    private boolean isInitialized = false;

    public Menu(Game game) {
        super(game);
        System.out.println("Menu constructor called.");
        loadButtons();
        loadBackground();
        isInitialized = true;
    }

    private void loadBackground() {
        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
        backgroundTitle = LoadSave.getSpriteAtlas(LoadSave.MENU_TITLE);

        menuWidth = (int)(backgroundTitle.getWidth() * Game.SCALE);
        menuHeight = (int)(backgroundTitle.getHeight() * Game.SCALE);
        menuX = GAME_WIDTH / 2 - menuWidth / 2;
        menuY = (int) (-120 * SCALE);

    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.GAME_WIDTH/2,(int)(190*Game.SCALE),0,Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.GAME_WIDTH/2,(int)(230*Game.SCALE),1,Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.GAME_WIDTH/2,(int)(270*Game.SCALE),2,Gamestate.ABOUT);
        buttons[3] = new MenuButton(Game.GAME_WIDTH/2,(int)(310*Game.SCALE),3,Gamestate.EXIT);
    }

    @Override
    public void update() {
        if (isInitialized) {
            for (MenuButton mb : buttons)
                mb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        if (isInitialized) {
            g.drawImage(backgroundImg, 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
            g.drawImage(backgroundTitle, 0, menuY, GAME_WIDTH, GAME_HEIGHT, null);

            for (MenuButton mb : buttons)
                mb.draw(g);
        } else {
            System.err.println("Menu is not fully initialized yet.");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for(MenuButton mb : buttons) {
            if(isIn(e,mb)){
                if(mb.isMousePressed())
                    mb.applyGameState();
                break;
            }
        }
        resetButtons();
    }

    private void resetButtons() {
        for(MenuButton mb : buttons)
            mb.resetBools();

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for(MenuButton mb : buttons) {
            mb.setMouseOver(false);
        }

        // Check all buttons for hover
        for(MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
            Gamestate.state = Gamestate.PLAYING;

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }
}
