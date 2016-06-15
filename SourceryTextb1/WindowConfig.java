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

    private int windowHeight = 408;
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

        setSize(windowWidth, windowHeight);

        art arty = new art();
        String[][] base = art.strToArray(arty.configBox);
        Layer lay = new Layer(base, "helpful");
        org.addLayer(lay);

        org.compileImage();

        while (!keyListener.resume){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }

        org.removeLayer("helpful");
        window.txtArea.removeKeyListener(keyListener);
    }


    void keyPressed(char c){
        System.out.println(c);
        switch (c){
            case '©': // Up
                windowHeight -= 21;
                setSize(windowWidth, windowHeight);
                break;
            case '®': // Down
                windowHeight += 21;
                setSize(windowWidth, windowHeight);
                break;
            case 'µ': // Left
                windowWidth -= 9;
                setSize(windowWidth, windowHeight);
               break;
            case 'æ': // Right
                windowWidth += 9;
                setSize(windowWidth, windowHeight);
                break;
            case '1':
                setSize(421, 513);
                break;
            case '2':
                setSize(412, 412);
            default:
                break;
        }
    }

    private void setSize(int width, int height){
        windowWidth = width;
        windowHeight = height;
        window.setSize(width, height);
    }

}


class Navigator extends KeyAdapter {
    private WindowConfig cnfg;
    boolean resume = false;

    Navigator(WindowConfig inventory) {
        cnfg = inventory;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        char ch = event.getKeyChar();
        cnfg.keyPressed(ch);
        if (key == KeyEvent.VK_UP) {
            cnfg.keyPressed('©');
        }
        if (key == KeyEvent.VK_DOWN) {
            cnfg.keyPressed('®');
        }
        if (key == KeyEvent.VK_LEFT) {
            cnfg.keyPressed('µ');
        }
        if (key == KeyEvent.VK_RIGHT) {
            cnfg.keyPressed('æ');
        }
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}