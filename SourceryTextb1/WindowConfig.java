package SourceryTextb1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A class to select something in your inventory
 * Created by riley on 12-Jun-2016.
 */
class WindowConfig {

    public Window window;
    public ImageOrg org;

    private int windowHeight = 412;
    private int windowWidth =  412;

    public WindowConfig(Window win, ImageOrg orgo){
         window = win;
         org = orgo;
    }

    /**
     * Bring up the inventory for the player to do stuff with.
     */
    public void config() {
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.

        art arty = new art();
        String[][] base = art.strToArray(arty.configBox);
        Layer lay = new Layer(base, "Test");
        org.addLayer(lay);

        org.compileImage();

        while (!keyListener.resume){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }

        //org.removeLayer("helpful");
        window.txtArea.removeKeyListener(keyListener);
    }


    void keyPressed(char c){
        System.out.println(c);
        switch (c){
            case '©': // Up
                windowHeight -= 15;
                window.setBounds(100, 100, windowWidth, windowHeight);
                break;
            case '®': // Down
                windowHeight += 15;
                window.setBounds(100, 100, windowWidth, windowHeight);
                break;
            case 'µ': // Left
                windowWidth -= 9;
                window.setBounds(100, 100, windowWidth, windowHeight);
               break;
            case 'æ': // Right
                windowWidth += 9;
                window.setBounds(100, 100, windowWidth, windowHeight);
                break;
            default:
                break;
        }
    }

}


class Navigator extends KeyAdapter {
    private WindowConfig inv;
    boolean resume = false;

    Navigator(WindowConfig inventory) {
        inv = inventory;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        if (key == KeyEvent.VK_UP) {
            inv.keyPressed('©');
        }
        if (key == KeyEvent.VK_DOWN) {
            inv.keyPressed('®');
        }
        if (key == KeyEvent.VK_LEFT) {
            inv.keyPressed('µ');
        }
        if (key == KeyEvent.VK_RIGHT) {
            inv.keyPressed('æ');
        }
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}