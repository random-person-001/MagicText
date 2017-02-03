package SourceryText.UserScreens;

import SourceryText.Art;
import SourceryText.GameInstance;
import SourceryText.GameSettings.KeyMap;
import SourceryText.GameSettings.MetaSettings;
import SourceryText.GameSettings.Settings;
import SourceryText.GameSettings.SettingsIO;
import SourceryText.ImageOrg;
import SourceryText.Layer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.security.Key;


/**
 * Created by Jared on 25-Aug-16.
 * It's the place you first come to in the game, giving options to do the demo, start a new game, and maybe even load one.
 */

class MainMenu {
    private ImageOrg org;
    private SourceryText.Window window;
    private StartMethods starter;
    private KeyInput keyInputter;
    private int keyCode = 0;
    private final int UP = 1;
    private final int DOWN = 2;
    private final int ENTER = 3;
    private final int TOP_MENU = 0;
    private final int MULTIPLAYER_MENU = 1;
    private final int IP_ENTERING_MENU = 2;
    private final int SETTINGS_MENU = 3;
    private final int CONTROLS_MENU = 4;
    private final int EXIT_MENU = 5;

    private int menuID = TOP_MENU;
    private char keyChar = ' ';
    private String ipString = "192.168.0.";
    private int cursorY = 9;
    private int cursorX = 2;
    private int ipSetXPos = 11 + 10;
    private boolean mapping = false;
    private int namingI = -1;
    private String newName = "";
    private String[] presets;
    private int presetI = 0;
    private KeyMap keymap = new KeyMap();
    private MetaSettings metasettings = new MetaSettings();
    private SettingsIO saveORload = new SettingsIO();

    MainMenu(ImageOrg orgo) {
        org = orgo;
        window = orgo.getWindow();
        starter = new StartMethods(org);

        keyInputter = new KeyInput();
        window.formalAddKeyListener(keyInputter);

        Art artida = new Art();
        Layer menuLayer = new Layer(Art.strToArray(artida.mainMenu, true), "MAIN_MENU");
        org.addLayer(menuLayer);

        metasettings = (MetaSettings) saveORload.loadSettings(metasettings, metasettings.FILE_NAME);
        presets = saveORload.getFileNames(keymap);
        for (int i=0;i<presets.length;i++) {
            if (presets[i] == metasettings.DEFAULT_KEYMAP) {
                presetI = i;
                break;
            }
        }
        for (int i=0;i<presets.length;i++) {
            if (presets[i].equals(metasettings.USER_KEYMAP)) {
                presetI = i;
                break;
            }
        }
        keymap = (KeyMap) saveORload.loadSettings(keymap, presets[presetI]);
        waitAMomentAndUpdateCursor();
    }

    private void onEnterPressedDuringTop() {
        org.removeLayer("MAIN_MENU");

        window.txtArea.removeKeyListener(keyInputter); // Same as below  \/
        menuID = EXIT_MENU; // In most cases.  If going to multiplayer menu, we'll change it again.

        if (cursorY == 8) {
            starter.doIntro();
        }
        else if (cursorY == 9) {
            System.out.println("\n\nNEW GAME!!!\n\n");
            starter.newGame(1, keymap);
        }
        else if (cursorY == 10) {
            Layer multiplayerLayer = new Layer(Art.strToArray(new Art().multiplayerMenu), "MULTIPLAYER_MENU");
            org.addLayer(multiplayerLayer);
            window.txtArea.addKeyListener(keyInputter); // Cuz we removed it before

            org.editLayer(" ", "MULTIPLAYER_MENU", cursorY, 24);
            Layer ipSetLayer = new Layer(Art.strToArray(new Art().ipSetMenu, true), "IP_ENTER_MENU", 10, 10);
            for (int ii = 0; ii < ipString.length(); ii++) {
                ipSetLayer.setStr(0, ii + 11, String.valueOf(ipString.charAt(ii)));
            }
            org.addLayer(ipSetLayer);
            menuID = IP_ENTERING_MENU;
            cursorY = 10;
            onPressArrow(); // For graphic update
            waitAMomentAndUpdateCursor();
        }
        else if (cursorY == 11) {
            if (loadGame()) {
                System.out.println("success!");
            } else {
                org.addLayer(new Layer(Art.strToArray(new Art().mainMenu), "MAIN_MENU"));
                window.txtArea.addKeyListener(keyInputter);
                menuID = TOP_MENU;
                waitAMomentAndUpdateCursor();
            }
        }
        else if (cursorY == 12) {
            Layer settingsLayer = new Layer(Art.strToArray(new Art().settingsMenu), "SETTINGS_MENU");
            org.addLayer(settingsLayer);
            window.txtArea.addKeyListener(keyInputter); // Cuz we removed it before
            menuID = SETTINGS_MENU;
            cursorY = 5;
            waitAMomentAndUpdateCursor();
        }
        else if (cursorY == 13) {
            System.exit(0);
        }
    }


    private void onPressArrow() {
        int oldCursorY = cursorY;
        int oldCursorX = cursorX;
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
            case IP_ENTERING_MENU:
                org.editLayer(" ", "MULTIPLAYER_MENU", oldCursorY, 10);
                loopAtMenuEnd(10, 13);
                org.editLayer("*", "MULTIPLAYER_MENU", cursorY, 10);
                break;
            case SETTINGS_MENU:
                org.editLayer(" ", "SETTINGS_MENU", oldCursorY, 24);
                loopAtMenuEnd(5, 6);
                org.editLayer("*", "SETTINGS_MENU", cursorY, 24);
                break;
            case CONTROLS_MENU:
                if(cursorY==2 || cursorY==19)       {cursorY+=cursorY-oldCursorY;}
                org.editLayer(" ", "CONTROLS_MENU", oldCursorY, oldCursorX);
                loopAtMenuEnd(1, 20);
                if(cursorY==1 || cursorY==20)       {cursorX=2;}
                else if(cursorX==2)                 {cursorX=30;}
                org.editLayer("*", "CONTROLS_MENU", cursorY, cursorX);
                writeDescription();
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
        if (cursorY == 11) { //Clears the ip address that you wrote
            clearIPAddress();
        }
        if (cursorY == 12 || cursorY == 10) { //Runs client connection based on entered ip address
            System.out.println("Requesting connection to Sourcery Text server at \"" + ipString + "\"");
            starter.doNetworkClient(ipString, keymap);
        }
        if (cursorY == 13) {
            //Go back to top menu.
            org.editLayer(" ", "MULTIPLAYER_MENU", cursorY, 10);
            org.removeLayer("IP_ENTER_MENU");
            org.removeLayer("MULTIPLAYER_MENU");
            System.out.println("Returning to main menu");
            menuID = TOP_MENU;
            org.addLayer(new Layer(Art.strToArray(new Art().mainMenu, true), "MAIN_MENU"));
            cursorY = 10;
            waitAMomentAndUpdateCursor();
        }
    }

    /**
     * Generally, when a key is pressed during the IP entering submenu, call this.
     *
     * @param event the KeyEvent generated
     */
    private void onKeyPressedDuringIP(KeyEvent event) {
        keyChar = event.getKeyChar();
        if (cursorY == 10 && (keyChar == '.' || Character.isDigit(keyChar)) && ipSetXPos < 24) {
            ipString += keyChar;
            org.editLayer(String.valueOf(keyChar), "IP_ENTER_MENU", 0, ipSetXPos);
            ipSetXPos++;
            System.out.printf("IP Appended! (Now %1$s)\n", ipString);
        } else if (cursorY == 10 && event.getKeyCode() == KeyEvent.VK_BACK_SPACE && ipSetXPos <= 24){
            ipSetXPos--;
            org.editLayer("_", "IP_ENTER_MENU", 0, ipSetXPos);
            ipString = ipString.substring(0, ipString.length()-1);
            System.out.printf("IP Lessened! (Now %1$s)\n", ipString);
        }
    }

    /**
     * Generally, when a key is pressed during the control mapping menu, call this.
     *
     * @param event the KeyEvent generated
     */
    private void onKeyPressedDuringControls(KeyEvent event) {
        if(cursorY==20){return;}
        int keyCode = event.getKeyCode();
        if(mapping) {
            org.editLayer(new String(new char[6]).replace("\0", " "), "CONTROLS_MENU", cursorY, cursorX+1);
            org.editLayer(keymap.getKeyText(keyCode), "CONTROLS_MENU", cursorY, cursorX+1);
            int ordinal = cursorX==30 ? 1 : 2;
            keymap.setMap(cursorY-3, ordinal, keyCode);
            mapping = false;
            return;
        }
        else if(keyCode == KeyEvent.VK_DELETE) {
            org.editLayer(new String(new char[6]).replace("\0", " "), "CONTROLS_MENU", cursorY, cursorX+1);
            int ordinal = cursorX==30 ? 1 : 2;
            keymap.setMap(cursorY-3, ordinal, -1);
            return;
        }
        switch(keyCode) {
            case KeyEvent.VK_LEFT:
                if(cursorY==1) {
                    org.editLayer(new String(new char[32]).replace("\0", " "), "CONTROLS_MENU", 1, 13);
                    presetI = presetI==0 ? presets.length-1 : presetI-1;
                    keymap = (KeyMap) saveORload.loadSettings(keymap, presets[presetI]);
                    org.editLayer(presets[presetI], "CONTROLS_MENU", 1, 13);
                    for(int action=0; action<=15;action++){
                        for(int ordinal=1; ordinal<=2; ordinal++) {
                            int k = keymap.getMap(action, ordinal);
                            org.editLayer(new String(new char[6]).replace("\0", " "), "CONTROLS_MENU", action+3, 31+(ordinal-1)*8);
                            org.editLayer(k!=-1 ? keymap.getKeyText(k) : "", "CONTROLS_MENU", action+3, 31+(ordinal-1)*8);
                        }
                    }
                }
                else {
                    cursorX=30;
                    org.editLayer(" ", "CONTROLS_MENU", cursorY, 38);
                    org.editLayer("*", "CONTROLS_MENU", cursorY, 30);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(cursorY==1) {
                    org.editLayer(new String(new char[32]).replace("\0", " "), "CONTROLS_MENU", 1, 13);
                    presetI = presetI==presets.length-1 ? 0 : presetI+1;
                    keymap = (KeyMap) saveORload.loadSettings(keymap, presets[presetI]);
                    org.editLayer(presets[presetI], "CONTROLS_MENU", 1, 13);
                    for(int action=0; action<=15;action++){
                        for(int ordinal=1; ordinal<=2; ordinal++) {
                            int k = keymap.getMap(action, ordinal);
                            org.editLayer(new String(new char[6]).replace("\0", " "), "CONTROLS_MENU", action+3, 31+(ordinal-1)*8);
                            org.editLayer(k!=-1 ? keymap.getKeyText(k) : "", "CONTROLS_MENU", action+3, 31+(ordinal-1)*8);
                        }
                    }
                }
                else {
                    cursorX=38;
                    org.editLayer(" ", "CONTROLS_MENU", cursorY, 30);
                    org.editLayer("*", "CONTROLS_MENU", cursorY, 38);
                }
                break;
            case KeyEvent.VK_BACK_SPACE:
                if(cursorY==1 && namingI>-1) {
                    org.editLayer(" ", "CONTROLS_MENU", 1, 13+namingI);
                    namingI--;
                    newName = newName.substring(0,newName.length()-1);
                    if(namingI==-1) {
                        newName = "";
                        org.editLayer(presets[presetI],"CONTROLS_MENU", 1, 13);
                    }
                }
            default:
                if(cursorY==1 && event.getKeyText(keyCode).length()==1) {
                    if(namingI==-1)     {org.editLayer(new String(new char[32]).replace("\0", " "), "CONTROLS_MENU", 1, 13);}
                    namingI++;
                    org.editLayer(event.getKeyText(keyCode), "CONTROLS_MENU", 1, 13+namingI);
                    newName+=event.getKeyText(keyCode);
                }
        }
    }

    //writes descriptions of menu items in the box at the bottom of the controls menu
    private void writeDescription() {
        org.editLayer(new String(new char[38]).replace("\0", " "), "CONTROLS_MENU", 23, 4);
        org.editLayer(new String(new char[38]).replace("\0", " "), "CONTROLS_MENU", 24, 4);
        org.editLayer(new String(new char[38]).replace("\0", " "), "CONTROLS_MENU", 25, 4);
        if(cursorY==1){
            org.editLayer("Press left or right to change presets", "CONTROLS_MENU", 23, 4);
            org.editLayer("Type name to create a new profile", "CONTROLS_MENU", 24, 4);
            org.editLayer("Press enter to save", "CONTROLS_MENU", 25, 4);
        }
        else if(cursorY==20) {
            org.editLayer("Press enter to exit to settings", "CONTROLS_MENU", 23, 4);
            org.editLayer("(Note: this doesn't save your", "CONTROLS_MENU", 24, 4);
            org.editLayer("changes!)", "CONTROLS_MENU", 25, 4);
        }
        else{
            org.editLayer(keymap.getActionDescription(cursorY - 3)[0], "CONTROLS_MENU", 23, 4);
            org.editLayer(keymap.getActionDescription(cursorY - 3)[1], "CONTROLS_MENU", 24, 4);
            org.editLayer(keymap.getActionDescription(cursorY - 3)[2], "CONTROLS_MENU", 25, 4);
        }
    }

    /**
     * Flow control and code to execute when, during the Settings menu, the enter key is pressed
     */
    private void onEnterPressedDuringSettings() {
        if (cursorY == 5) { //to control menu
            org.editLayer(" ", "SETTINGS_MENU", cursorY, 24);
            org.removeLayer("SETTINGS_MENU");
            System.out.println("Entering controls menu");
            menuID = CONTROLS_MENU;
            org.addLayer(new Layer(Art.strToArray(new Art().controlsMenu, true), "CONTROLS_MENU"));
            cursorY = 1;
            waitAMomentAndUpdateCursor();
            org.editLayer(presets[presetI], "CONTROLS_MENU", 1, 13);
            for(int action=0; action<=15;action++){
                for(int ordinal=1; ordinal<=2; ordinal++) {
                    int k = keymap.getMap(action, ordinal);
                    org.editLayer(k!=-1 ? keymap.getKeyText(k) : "", "CONTROLS_MENU", action+3, 31+(ordinal-1)*8);
                }
            }
        }
        else if (cursorY == 6) { //back to main menu
            saveORload.saveSettings(metasettings, metasettings.FILE_NAME);
            org.editLayer(" ", "SETTINGS_MENU", cursorY, 24);
            org.removeLayer("SETTINGS_MENU");
            System.out.println("Returning to main menu");
            menuID = TOP_MENU;
            org.addLayer(new Layer(Art.strToArray(new Art().mainMenu, true), "MAIN_MENU"));
            cursorY = 12;
            waitAMomentAndUpdateCursor();
        }
    }

    /**
     * Flow control and code to execute when, during the Settings menu, the enter key is pressed
     */
    private void onEnterPressedDuringControls() {
        if (cursorY == 1) {
            String name = newName!="" ? newName : presets[presetI] ;
            for (int i=0;i<metasettings.OVERITE_PROTECTED_KEYMAPS.length;i++) {
                if(name.equals(metasettings.OVERITE_PROTECTED_KEYMAPS[i]))        {return;}
            }
            saveORload.saveSettings(keymap, newName!="" ? newName : presets[presetI]);
            newName = "";
            namingI = 0;
            presetI = -1;
            presets = saveORload.getFileNames(keymap);
            for (int i=0;i<presets.length;i++) {
                if (presets[i].equals(name)) {
                    presetI = i;
                    break;
                }
            }
        }
        else if (cursorY == 20) { //back to settings menu
            metasettings.USER_KEYMAP = presets[presetI];
            org.editLayer(" ", "CONTROLS_MENU", cursorY, 24);
            org.removeLayer("CONTROLS_MENU");
            System.out.println("Returning to settings menu");
            menuID = SETTINGS_MENU;
            org.addLayer(new Layer(Art.strToArray(new Art().settingsMenu, true), "SETTINGS_MENU"));
            cursorY = 5;
            waitAMomentAndUpdateCursor();
        }
        else{
            mapping = true;
        }
    }
    /**
     * Generic case for pressing enter; splits flow off into the different methods according to whether in multiplayer
     * menu or not.
     */
    private void onEnterPressed() {
        if (menuID == TOP_MENU) {
            onEnterPressedDuringTop();
        } else if(menuID == MULTIPLAYER_MENU || menuID == IP_ENTERING_MENU) {
            onEnterPressedDuringMultiplayer();
        } else if(menuID == SETTINGS_MENU) {
            onEnterPressedDuringSettings();
        } else if(menuID == CONTROLS_MENU){
            onEnterPressedDuringControls();
        }
    }

    /**
     * Call when a non-arrow, non-enter key is pressed.
     *
     * @param e which key
     */
    private void onGenericKeyPressed(KeyEvent e) {
        if (menuID == IP_ENTERING_MENU) {
            onKeyPressedDuringIP(e);
        } else if (menuID == CONTROLS_MENU){
            onKeyPressedDuringControls(e);
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

    private boolean loadGame() {
        File save;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sourcery Text Saves", "sav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(new Component() {
        });
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
            save = chooser.getSelectedFile();
        } else return false;

        try {
            System.out.println("Attempting to open");
            FileInputStream fileIn = new FileInputStream(save);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            GameInstance instance = (GameInstance) in.readObject();
            System.out.println("Game instance read");
            starter.buildGame(instance);
            System.out.println("Game built");
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException | java.lang.Error e) {
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
            if(mapping)     {onGenericKeyPressed(e);    return;}
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
            System.out.println(e.getKeyChar());
        }
    }
}
