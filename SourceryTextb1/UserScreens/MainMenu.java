package SourceryTextb1.UserScreens;

import SourceryTextb1.*;
import SourceryTextb1.GameObjects.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Jared on 25-Aug-16.
 * It's the place you first come to in the game, giving options to do the demo, start a new game, and maybe even load one.
 */

class MainMenu {
    private ImageOrg org;
    private SourceryTextb1.Window window;
    private Start.StartCheck starter;
    private WindowConfig wincnfg;
    private KeyInput keyInputter;
    private int keyCode = 0;
    private final int UP = 1;
    private final int DOWN = 2;
    private final int ENTER = 3;

    private boolean finished = false;

    private int clock = 0;
    private int cursorY = 9;

    MainMenu(ImageOrg orgo, SourceryTextb1.Window theWindow, Start.StartCheck start) {
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
        if (!finished) {
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
                        starter.newGame();
                        //finished = true;
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ignore) {}
                        org.removeAllButPlayer();
                        org.removeLayer("playerLayer");
                        window.txtArea.setForeground(Color.WHITE);
                        org.setCam(0,0);
                    }
                    if (cursorY == 10 && loadGame()) {
                        finished = true;
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
                    if (!finished) {
                        org.addLayer(orig);
                        window.txtArea.addKeyListener(keyInputter);
                    }
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
    }

    private boolean loadGame(){
        File save;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sourcery Text Saves", "sav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(new Component(){});
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            save = chooser.getSelectedFile();
        } else return false;

        try {
            System.out.println("Attempting to open");
            FileInputStream fileIn = new FileInputStream(save);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Player loadedPlayer = (Player)in.readObject();
            System.out.println("Player read");
            starter.buildGame(loadedPlayer);
            System.out.println("Game built");
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException | java.lang.Error e){
            System.out.println("Something went wrong :(");
            e.printStackTrace();
            return false;
        }
        return true;

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
