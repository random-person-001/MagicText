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

    private char keyChar = ' ';
    private String ipString = "";

    private boolean finished = false;
    private boolean inMultiplayerMenu = false;

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

        Art artida = new Art();
        Layer menuLayer = new Layer(Art.strToArray(artida.mainMenu,true), "MAIN_MENU");
        org.addLayer(menuLayer);
    }

    protected void mainLoop() {
        if (!finished) {
            org.editLayer(" ", "MAIN_MENU", cursorY, 24);
            window.txtArea.setOverallForeGround(Color.WHITE);

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
                        System.out.println("\n\nNEW GAME!!!\n\n");
                        starter.newGame(1);
                    }
                    if (cursorY == 10) {
                        org.editLayer(" ", "MAIN_MENU", cursorY, 24);
                        org.removeLayer("MAIN_MENU");
                        Layer multiplayerLayer = new Layer(Art.strToArray(new Art().multiplayerMenu,true), "MULTIPLAYER_MENU");
                        org.addLayer(multiplayerLayer);
                        window.txtArea.addKeyListener(keyInputter); // Cuz we removed it before
                        inMultiplayerMenu = true;
                        cursorY = 9;
                    }
                    if (cursorY == 11 && loadGame()) {
                        finished = true;
                    }
                    if (cursorY == 12) {
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
                    if (cursorY == 13) {
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
                cursorY = 13;
            }
            if (cursorY >= 14) {
                cursorY = 8;
            }

            org.editLayer("*", "MAIN_MENU", cursorY, 24);
        }
    }

    /**
     * Multiplayer options screen
     */
    private boolean settingIP = false;
    private int ipSetXPos = 11;

    private void clearIPAddress(){
        ipString = "";
        for (int ii = 0 ; ii < 13; ii++){
            org.editLayer("_", "IP_ENTER_MENU", 0, 11+ii);
        }
        ipSetXPos = 11;
    }

    private void multiplayerLoop(){
        if (!finished) {
            int cursorX = 24;
            if (settingIP) cursorX = 10;
            org.editLayer(" ", "MULTIPLAYER_MENU", cursorY, cursorX);
            switch (keyCode) {
                case UP:
                    cursorY--;
                    break;
                case DOWN:
                    cursorY++;
                    break;
                case ENTER:
                    if (cursorY == 5){
                        org.removeLayer("MULTIPLAYER_MENU");
                        window.txtArea.removeKeyListener(keyInputter);
                        System.out.println("New multiplayer game made!");
                        System.out.println("In the HUD, enter 'lan' to start a server.");
                        starter.newGame(2);
                    }
                    if (cursorY == 6){ // For Riley
                        Layer ipSetLayer = new Layer(Art.strToArray(new Art().ipSetMenu,true), "IP_ENTER_MENU", 10, 10);
                        org.addLayer(ipSetLayer);
                        settingIP = true;
                        cursorY = 10;
                        cursorX = 10;
                    }
                    if (cursorY == 7){
                        org.removeLayer("MULTIPLAYER_MENU");
                        System.out.println("Returning to main menu");
                        inMultiplayerMenu = false;
                        org.addLayer(new Layer(Art.strToArray(new Art().mainMenu,true), "MAIN_MENU"));
                    }
                    if (cursorY == 11){
                        clearIPAddress();
                    }
                    if (cursorY == 12){
                        System.out.println("Requesting connection to Sourcery Text server at \"192.168.0.250\"");
                        starter.doNetworkClient("192.168.0.250");
                    }
                    if (cursorY == 13){
                        clearIPAddress();
                        org.removeLayer("IP_ENTER_MENU");
                        settingIP = false;
                        cursorY = 5;
                        cursorX = 24;
                    }
            }
            if (!settingIP) {
                if (cursorY <= 4) { //Looping
                    cursorY = 7;
                }
                if (cursorY >= 8) {
                    cursorY = 5;
                }
            } else {
                if (cursorY <= 9) { //Looping
                    cursorY = 13;
                }
                if (cursorY >= 14) {
                    cursorY = 10;
                }
                if (cursorY == 10 && (keyChar == '.' || Character.isDigit(keyChar)) && ipSetXPos < 24){
                    ipString += keyChar;
                    org.editLayer(String.valueOf(keyChar), "IP_ENTER_MENU", 0, ipSetXPos);
                    ipSetXPos++;
                    System.out.printf("IP Appended! (Now %1$s)\n", ipString);
                }

            }
            org.editLayer("*", "MULTIPLAYER_MENU", cursorY, cursorX);
            keyCode = 0;
            keyChar = ' ';
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
            if (!inMultiplayerMenu){
                mainLoop();
            }else {
                multiplayerLoop();
            }
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
            keyChar = e.getKeyChar();
            if (Character.isDigit(keyChar)){
                System.out.println("Digit: " + keyChar);
            }
        }
    }
}
