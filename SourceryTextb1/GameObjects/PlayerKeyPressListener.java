package SourceryTextb1.GameObjects;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A listener class for keypresses, tailored to the Player.
 */
public class PlayerKeyPressListener extends KeyAdapter {
    public Player player;

    public PlayerKeyPressListener(Player p) {
        player = p;
    }

    public String getPlayerUsername(){
        return player.getUsername();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        player.fireKeyEvent(event);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        player.fireKeyEvent(event);
    }
}
