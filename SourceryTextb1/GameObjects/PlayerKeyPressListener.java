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

        if (event.getKeyCode() == KeyEvent.VK_UP) {
            player.moveKeyPressed("up", true);
        }
        else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
            player.moveKeyPressed("down", true);
        }
        else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
            player.moveKeyPressed("left", true);
        }
        else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.moveKeyPressed("right", true);
        }
        else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
            player.moveKeyPressed("space", true);
        }
        else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            player.keyPressed('\'');
        } else {
            player.keyPressed(event.getKeyChar());
        }

        //System.out.println(event.paramString());
        System.out.println(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event) {

        if (!player.frozen && !player.dead) {
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                player.moveKeyPressed("up", false);
            }
            else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                player.moveKeyPressed("down", false);
            }
            else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                player.moveKeyPressed("left", false);
            }
            else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                player.moveKeyPressed("right", false);
            }
            else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                player.moveKeyPressed("space", false);
            }
        }

        System.out.println(event.getKeyCode());
    }
}
