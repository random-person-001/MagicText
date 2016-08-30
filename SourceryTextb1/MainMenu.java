package SourceryTextb1;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Jared on 25-Aug-16.
 * It's the place you first come to in the game, giving options to do the demo, start a new game, and maybe even load one.
 */

class MainMenu {
    private ImageOrg org;
    private Window window;
    private Start.StartCheck starter;
    private WindowConfig wincnfg;
    private KeyInput keyInputter;
    private int keyCode = 0;
    private final int UP = 1;
    private final int DOWN = 2;
    private final int ENTER = 3;

    private int clock = 0;
    private int cursorY = 9;

    MainMenu(ImageOrg orgo, Window theWindow, Start.StartCheck start) {
        org = orgo;
        window = theWindow;
        starter = start;
        wincnfg = new WindowConfig(orgo);
        Timer time = new Timer();
        time.scheduleAtFixedRate(new MenuTimer(), 0, 100);

        keyInputter = new KeyInput(this);
        window.txtArea.addKeyListener(keyInputter);

        art artida = new art();
        Layer menuLayer = new Layer(art.strToArray(artida.mainMenu), "MAIN_MENU");
        org.addLayer(menuLayer);
    }

    protected void loop() {
        org.editLayer(" ", "MAIN_MENU", cursorY, 24);

        switch (keyCode) {
            case UP:
                cursorY--;
                break;
            case DOWN:
                cursorY++;
                break;
            case ENTER:
                System.out.println(clock);
                window.txtArea.removeKeyListener(keyInputter);
                Layer orig = org.getLayer("MAIN_MENU"); // to add back later
                org.removeLayer("MAIN_MENU");

                if (cursorY == 8) {
                    starter.doIntro();
                }
                if (cursorY == 9) {
                    starter.startGame();
                }
                if (cursorY == 11) {
                    // Do window size adjustment, interactively, and wait until it is done
                    wincnfg.config(true);
                    while (!wincnfg.doContinue) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (cursorY == 12) {
                    System.exit(0);
                }
                org.addLayer(orig);
                window.txtArea.addKeyListener(keyInputter);
                keyCode = 0;
        }
        keyCode = 0;

        if (cursorY <= 7) {
            cursorY = 12;
        }
        if (cursorY >= 13) {
            cursorY = 8;
        }

        org.editLayer("*", "MAIN_MENU", cursorY, 24);
    }

    private class MenuTimer extends TimerTask {
        @Override
        public void run() {
            clock++;
            loop();
        }
    }

    private class KeyInput extends KeyAdapter {
        MainMenu owner;

        KeyInput(MainMenu creator) {
            owner = creator;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            //System.out.println("input");
            switch (input) {
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
