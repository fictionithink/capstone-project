package inputs;

import gamestates.Gamestate;
import main.Game;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utils.Constants.Directions.*;

// this class' purpose is to listen to the user's KEYBOARD inputs and act accordingly to their functions inside the methods.

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            default:
                break;
        }
    }


}
