package SourceryTextb1.UserScreens;

import SourceryTextb1.*;
import SourceryTextb1.Window;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Set the size of the viewing window, automatically or manually
 * Created by riley on 12-Jun-2016.
 */
class WindowConfig {
    private Navigator keyListener;
    private Window window;
    public ImageOrg org;

    boolean doContinue = false;
    boolean resume = false;

    private int windowHeight = 500;
    private int windowWidth =  500;

    WindowConfig(ImageOrg orgo){
         window = orgo.getWindow();
         org = orgo;
    }

    /**
     * Bring up the inventory for the player to do stuff with.
     * @param interactable whether to wait for the user to press buttons or just do what we think is best and quit
     */
    void config(boolean interactable) {
        System.out.println("WinCnfg: Start of config");

        resume = false;
        doContinue = false;
        setSize(windowWidth, windowHeight);

        if (interactable) {
            Art arty = new Art();
            String[][] base = Art.strToArray(arty.configBox);
            Layer lay = new Layer(base, "helpful");
            org.addLayer(lay);

            keyListener = new Navigator(this);
            window.txtArea.addKeyListener(keyListener); // Add key listeners.
        }
        else{
            exit();
        }

        System.out.println("WinCnfg: End of config\n");
    }


    void keyPressed(char c){
        //System.out.println(c);
        switch (c){
            case '©': // Up
                windowHeight -= 1;//21;
                setSize(windowWidth, windowHeight);
                break;
            case '®': // Down
                windowHeight += 1;//21;
                setSize(windowWidth, windowHeight);
                break;
            case 'µ': // Left
                windowWidth -= 1;//9;
                setSize(windowWidth, windowHeight);
               break;
            case 'æ': // Right
                windowWidth += 1;//9;
                setSize(windowWidth, windowHeight);
                break;
            case '1':
                setSize(430, 490);
                break;
            case '2':
                setSize(416, 478);
                break;
            case '3':
                setSize(1312, 786);
                break;
            default:
                System.out.println(windowHeight + " h  x  w " + windowWidth);
                break;
        }
    }

    private void setSize(int width, int height){
        windowWidth = width;
        windowHeight = height;
        window.setSize(width, height);
    }

    void exit(){
        window.txtArea.removeKeyListener(keyListener);
        org.removeLayer("helpful");
        doContinue = true;
        resume = true;
    }
}


class Navigator extends KeyAdapter {
    private WindowConfig cnfg;

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
            cnfg.exit();
        }
    }
}