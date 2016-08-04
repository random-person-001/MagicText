package SourceryTextb1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A class to select something in your inventory
 * Created by riley on 12-Jun-2016.
 */
class WindowConfig {

    private Window window;
    public ImageOrg org;

    public boolean doContinue = false;

    private int windowHeight = 408;
    private int windowWidth =  412;
    private String OS = System.getProperty("os.name").toLowerCase();

    WindowConfig(Window win, ImageOrg orgo){
         window = win;
         org = orgo;
         System.getProperties().list(System.out);  // Aw, cool!
    }

    /**
     * Bring up the inventory for the player to do stuff with.
     */
    void config() {
        System.out.println("WinCnfg: Start of config");

        setSize(windowWidth, windowHeight);

        art arty = new art();
        String[][] base = art.strToArray(arty.configBox);
        Layer lay = new Layer(base, "helpful");
        org.addLayer(lay);

        System.out.println("WinCnfg: layer added");

        if (OS.contains("nix") || OS.contains("nux")){
            keyPressed('2');
        } else if (OS.contains("win")){
            keyPressed('1');
        } else if (OS.contains("mac")){
            System.out.println("Hello Mac user.");
        }

        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.

        System.out.println("WinCnfg: keyListener created");

        Timer update = new Timer();
        update.schedule(new StopListener(keyListener), 0, 100);

        System.out.println("WinCnfg: End of config");
    }


    void keyPressed(char c){
        //System.out.println(c);
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
                setSize(417, 412);
                break;
            case '3':
                setSize(1312, 786);
                break;
            default:
                //System.out.println(windowHeight + " h  x  w " + windowWidth);
                break;
        }
    }

    private void setSize(int width, int height){
        windowWidth = width;
        windowHeight = height;
        window.setSize(width, height);
    }

    private class StopListener extends TimerTask {
        Navigator listen;

        private StopListener(Navigator navi){
            listen = navi;
        }

        public void run(){
            if (listen.resume){

                org.removeLayer("helpful");
                window.txtArea.removeKeyListener(listen);

                doContinue = true;
            }
        }
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