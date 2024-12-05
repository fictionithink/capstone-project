package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;
// this class' purpose is to listen to the user's MOUSE inputs and act accordingly to their functions inside the methods.

public class MouseInputs implements MouseListener, MouseMotionListener {

    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    //MOUSE LISTENER METHODS vvv
    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gamePanel.enableMouseInput()) return; // Ignore if not ready

        gamePanel.getGame().getPlaying().mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gamePanel.enableMouseInput()) return; // Ignore if not ready

        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mousePressed(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mousePressed(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!gamePanel.enableMouseInput()) return; // Ignore if not ready

        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseReleased(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseReleased(e);
                break;
            default:
                break;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // MOUSE MOTION LISTENER METHODS vvv

    @Override
    public void mouseDragged(MouseEvent e) {
        switch (Gamestate.state) {
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseDragged(e);
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!gamePanel.enableMouseInput()) return; // Ignore if not ready

        switch (Gamestate.state){
            case PLAYING:
                gamePanel.getGame().getPlaying().mouseMoved(e);
                break;
            case MENU:
                gamePanel.getGame().getMenu().mouseMoved(e);
                break;
            default:
                break;
        }
    }
}
