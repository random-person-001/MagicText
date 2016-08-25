package SourceryTextb1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;



/**
 * Created by Jared on 25-Aug-16.
 *
 */

public class MainMenu {
    private ImageOrg org;
    private Window window;
    private Start.StartCheck starter;
    protected int keyCode = 0;
    final int UP = 1;
    final int DOWN = 2;
    final int ENTER = 3;

    protected int clock = 0;
    protected int cursorY = 9;

    public MainMenu(ImageOrg orgo, Window theWindow, Start.StartCheck start){
        org = orgo;
        window = theWindow;
        starter = start;
        Timer time = new Timer();
        time.scheduleAtFixedRate(new MenuTimer(), 0, 100);

        KeyInput check = new KeyInput(this);
        window.txtArea.addKeyListener(check);

        art artida = new art();
        Layer menuLayer = new Layer(artida.strToArray(artida.mainMenu),"MAIN_MENU");
        org.addLayer(menuLayer);
    }

    protected void loop() {
        org.editLayer(" ","MAIN_MENU",cursorY,24);

        switch (keyCode){
            case UP:
                cursorY--;
                break;
            case DOWN:
                cursorY++;
                break;
            case ENTER:
                if (cursorY == 9){
                    starter.startGame();
                }
        }
        keyCode = 0;

        if (cursorY == 8){
            cursorY = 11;
        }
        if (cursorY == 12){
            cursorY = 9;
        }

        org.editLayer("*","MAIN_MENU",cursorY,24);
    }

    class MenuTimer extends TimerTask{
        @Override
        public void run(){
            clock++;
            loop();
        }
    }

    class KeyInput extends KeyAdapter {
        MainMenu owner;

        protected KeyInput(MainMenu creator){
            owner = creator;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            //System.out.println("input");
            switch (input){
                case KeyEvent.VK_UP:
                    owner.keyCode = UP;
                    break;
                case KeyEvent.VK_DOWN:
                    owner.keyCode = DOWN;
                    break;
                case KeyEvent.VK_ENTER:
                    owner.keyCode = ENTER;
                    break;
            }
        }
    }
}
