package SourceryTextb1.UserScreens;

import SourceryTextb1.*;
import SourceryTextb1.GameObjects.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;


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
    private final int TOP_MENU = 0;
    private final int MULTIPLAYER_MENU = 1;
    private final int IP_ENTERING_MENU = 2;
    private final int EXIT_MENU = 4;

    private int menuID = TOP_MENU;
    private char keyChar = ' ';
    private String ipString = "192.168.0.";
    private int cursorY = 9;
    private int ipSetXPos = 11 + 10;

    private boolean finished = false;

    MainMenu(ImageOrg orgo, SourceryTextb1.Window theWindow, Start.StartCheck start) {
        org = orgo;
        window = theWindow;
        starter = start;
        wincnfg = new WindowConfig(orgo);

        keyInputter = new KeyInput();
        window.txtArea.addKeyListener(keyInputter);

        Art artida = new Art();
        Layer menuLayer = new Layer(Art.strToArray(artida.mainMenu, true), "MAIN_MENU");
        org.addLayer(menuLayer);
        waitAMomentAndUpdateCursor();
    }

    private void onEnterPressedDuringTop() {
        org.removeLayer("MAIN_MENU");

        window.txtArea.removeKeyListener(keyInputter); // Same as below  \/
        menuID = EXIT_MENU; // In most cases.  If going to multiplayer menu, we'll change it again.

        if (cursorY == 8) {
            starter.doIntro();
        }
        if (cursorY == 9) {
            System.out.println("\n\nNEW GAME!!!\n\n");
            starter.newGame(1);
        }
        if (cursorY == 10) {
            Layer multiplayerLayer = new Layer(Art.strToArray(new Art().multiplayerMenu), "MULTIPLAYER_MENU");
            org.addLayer(multiplayerLayer);
            window.txtArea.addKeyListener(keyInputter); // Cuz we removed it before
            menuID = MULTIPLAYER_MENU;
            cursorY = 9;
            waitAMomentAndUpdateCursor();
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
    }


    private void onPressArrow() {
        int oldCursorY = cursorY;
        switch (keyCode) {
            case UP:
                cursorY--;
                break;
            case DOWN:
                cursorY++;
                break;
            default:
                System.out.println("Variable for keyCode was not UP or DOWN");
        }
        switch (menuID) {
            case TOP_MENU:
                org.editLayer(" ", "MAIN_MENU", oldCursorY, 24);
                loopAtMenuEnd(8, 13);
                org.editLayer("*", "MAIN_MENU", cursorY, 24);
                break;
            case MULTIPLAYER_MENU:
                org.editLayer(" ", "MULTIPLAYER_MENU", oldCursorY, 24);
                loopAtMenuEnd(5, 7);
                org.editLayer("*", "MULTIPLAYER_MENU", cursorY, 24);
                break;
            case IP_ENTERING_MENU:
                org.editLayer(" ", "MULTIPLAYER_MENU", oldCursorY, 10);
                loopAtMenuEnd(10, 13);
                org.editLayer("*", "MULTIPLAYER_MENU", cursorY, 10);
                break;
            case EXIT_MENU:
                System.out.println("You have pressed a button while exiting.");
                break;
            default:
                System.out.println("Unrecognised menu id: " + menuID);
        }
    }

    /**
     * Loop the selector in the menu, so that if you go off the bottom, you start back at the top and vice versa
     *
     * @param start lowest allowed y coord
     * @param end   highest allowed y coord
     */
    private void loopAtMenuEnd(int start, int end) {
        if (cursorY > end) {
            cursorY = start;
        }
        if (cursorY < start) {
            cursorY = end;
        }
    }

    /**
     * Clear the IP address that is being entered in the IP entering submenu (in multiplayer menu)
     */
    private void clearIPAddress() {
        ipString = "";
        for (int ii = 0; ii < 13; ii++) {
            org.editLayer("_", "IP_ENTER_MENU", 0, 11 + ii);
        }
        ipSetXPos = 11;
    }

    /**
     * Flow control and code to execute when, during the multiplayer menu, the enter key is pressed
     */
    private void onEnterPressedDuringMultiplayer() {
        if (cursorY == 5) { //Starts new game "as a server"
            org.removeLayer("MULTIPLAYER_MENU");
            window.txtArea.removeKeyListener(keyInputter);
            System.out.println("New multiplayer game made!");
            starter.newGame(2);
        }
        if (cursorY == 6) { //Jumps to ip address submenu
            org.editLayer(" ", "MULTIPLAYER_MENU", cursorY, 24);
            Layer ipSetLayer = new Layer(Art.strToArray(new Art().ipSetMenu, true), "IP_ENTER_MENU", 10, 10);
            for (int ii = 0; ii < ipString.length(); ii++) {
                ipSetLayer.setStr(0, ii + 11, String.valueOf(ipString.charAt(ii)));
            }
            org.addLayer(ipSetLayer);
            menuID = IP_ENTERING_MENU;
            cursorY = 10;
            onPressArrow(); // For graphic update
        }
        if (cursorY == 7) { //Go back to main menu
            org.editLayer(" ", "MULTIPLAYER_MENU", cursorY, 24);
            org.removeLayer("MULTIPLAYER_MENU");
            System.out.println("Returning to main menu");
            menuID = TOP_MENU;
            org.addLayer(new Layer(Art.strToArray(new Art().mainMenu, true), "MAIN_MENU"));
            cursorY = 10;
            waitAMomentAndUpdateCursor();
        }
        if (cursorY == 11) { //Clears the ip address that you wrote
            clearIPAddress();
        }
        if (cursorY == 12) { //Runs client connection based on entered ip address
            System.out.println("Requesting connection to Sourcery Text server at \"" + ipString + "\"");
            starter.doNetworkClient(ipString);
        }
        if (cursorY == 13) { //Go back to multiplayer top menu.
            org.editLayer(" ", "MULTIPLAYER_MENU", cursorY, 10);
            org.removeLayer("IP_ENTER_MENU");
            menuID = MULTIPLAYER_MENU;
            cursorY = 5;
            onPressArrow();
        }
    }

    /**
     * Generally, when a key is pressed during the IP entering submenu, call this.
     * @param event the KeyEvent generated
     */
    private void onKeyPressedDuringIP(KeyEvent event){
        keyChar = event.getKeyChar();
        if (cursorY == 10 && (keyChar == '.' || Character.isDigit(keyChar)) && ipSetXPos < 24) {
            ipString += keyChar;
            org.editLayer(String.valueOf(keyChar), "IP_ENTER_MENU", 0, ipSetXPos);
            ipSetXPos++;
            System.out.printf("IP Appended! (Now %1$s)\n", ipString);
        }
    }

    /**
     * Generic case for pressing enter; splits flow off into the different methods according to whether in multiplayer
     * menu or not.
     */
    private void onEnterPressed(){
        if (!finished){
            if (menuID == TOP_MENU){
                onEnterPressedDuringTop();
            } else {
                onEnterPressedDuringMultiplayer();
            }
        }
    }

    /**
     * Call when a non-arrow, non-enter key is pressed.
     * @param e which key
     */
    private void onGenericKeyPressed(KeyEvent e) {
        if (menuID == IP_ENTERING_MENU){
            onKeyPressedDuringIP(e);
        }
    }

    /**
     * Sometimes ImageOrg takes a bit to add your layer, so you can't edit it right away.  This waits a duration
     * of time just over the tick rate of the ImageOrg and then updates the cursor position.
     */
    private void waitAMomentAndUpdateCursor() {
        try {
            Thread.sleep(52); // Wait a moment after adding a layer before editing it
        } catch (InterruptedException ignore) {
        }
        onPressArrow(); // This is the graphic update stuff for the cursor
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
            GameInstance instance= (GameInstance)in.readObject();
            System.out.println("Game instance read");
            starter.buildGame(instance);
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

    private class KeyInput extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int input = e.getKeyCode();
            switch (input) {
                case KeyEvent.VK_UP:
                    keyCode = UP;
                    onPressArrow();
                    break;
                case KeyEvent.VK_DOWN:
                    keyCode = DOWN;
                    onPressArrow();
                    break;
                case KeyEvent.VK_ENTER:
                    keyCode = ENTER;
                    onEnterPressed();
                    break;
                default:
                    onGenericKeyPressed(e);
            }
            keyChar = e.getKeyChar();
        }
    }
}
