package SourceryTextb1.GameObjects;

import SourceryTextb1.Art;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Window;
import SourceryTextb1.Layer;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;


/**
 * A class to select something in your inventory
 * Created by riley on 12-Jun-2016, mostly redone by Jared around 5 July 2016
 */
class Inventory implements java.io.Serializable {
    private ArrayList<Item> spells = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> equip = new ArrayList<>();

    private Player player;
    private ImageOrg org;
    private Layer selectedSpellsLayer;
    private int newSelectY = 2;
    private int prevIndex = 0;
    private int potatoIndex = 0;

    private final int TOP = 1;
    private final int SPELLS = 2;
    private final int ITEMS = 3;
    private final int EQUIP = 4;
    private final int EXIT = 5;
    private final int QUIT = 6;
    private final int UPGRADE = 7;

    //BECAUSE SCOPE
    private int menuID = 0;
    boolean pressedA = false;
    boolean pressedS = false;
    boolean pressedD = false;
    private int page = 1;
    private int indexX = 0;

    private Layer topMenuLayer = new Layer(Art.strToArray(new Art().topMenu), "top", 1, 27, false, true);
    private Layer quitMenuLayer = new Layer(Art.strToArray(new Art().quitMenu), "quit", 1, 27, false, true);
    private Layer itemsMenuLayer = new Layer(Art.strToArray(new Art().itemsMenu), "items", 1, 0, false, true);
    private Layer spellsMenuLayer = new Layer(Art.strToArray(new Art().spellsMenu), "spells", 1, 0, false, true);
    private Layer equipMenuLayer = new Layer(Art.strToArray(new Art().equipMenu), "equip", 1, 0, false, true);
    private Layer taterMenuLayer = new Layer(Art.strToArray(new Art().taterMenu), "tater", 1, 27, false, true);
    private Layer selectorLayer = new Layer(new String[22][46], "selector", 0, 0, false, false);
    private Layer infoLayer = new Layer(new String[22][46], "invInfo", 0, 0, false, false);


    int getY() {
        return newSelectY;
    }

    Inventory(ImageOrg orgo, Player p) {
        org = orgo;
        player = p;
        selectedSpellsLayer = new Layer(new String[22][47], "selectedSpells", false);

        /*
        spells.add(new Item("Spark","Arcane Spell;\nFires a spark of energy.\n\n\"All great fires start\n with small sparks\"", "Spark", player, "spell"));
        spells.add(new Item("Fireball","Fire Spell;\nUse your imagination", "FrBll", player, "spell"));
        spells.add(new Item("Frostbite","Ice Spell;\nFreezes an enemy right\n in front of you.", "FrstB", player, "spell"));
        spells.add(new Item("Shadow Knife","Dark Spell;\nThrows a blade made of\n forbidden magic.\n\nThe dart seems to have a \n soul that refuses to die," +
                "\n and thus travels very far.", "ShKnf", player, "spell"));
        */
        equip.add(new Item("Dusty Robe", "Dust is baked into this\n old robe.\n\nThe newest students at\n The Magic Academy get\n only hand-me-downs. As a" +
                "\n result, they are usually\n really, really old.\n\n+2 Defense", player, "equip"));
        equip.get(0).setEquipvals(2, 0, 0, 0, 0, 0, 0, "armor");

        //items.add(new Item("Magic Potato","A magically enhanced potato\n\nCan be used to either\n permanently increase\n your max health or\n max mana by 5.", player, "item"));

        player.armor = equip.get(0);
        player.defineStats();
    }

    /**
     * Add another of an item in the inventory
     *
     * @param input the Item you are placing into the inventory. Doesn't matter which type.
     */
    void addItem(Item input) {
        switch (input.itemType) {
            case 1:
                spells.add(input);
                break;
            case 2:
                items.add(input);
                break;
            case 3:
                equip.add(input);
                break;
        }
    }

    /**
     * Press a key?  Call this to do stuff!
     *
     * @param c which character you have pressed on the board
     */
    void keyPressed(char c) {
        org.editLayer(" ", "selector", newSelectY, indexX);
        switch (c) {
            case '©': // Up
                newSelectY--;
                break;
            case '®': // Down
                newSelectY++;
                break;
            default:
                break;
        }
        if (Character.isDigit(c) && (menuID == SPELLS || menuID == ITEMS || menuID == EQUIP)){
            int number = Integer.valueOf(String.valueOf(c));
            if (number != 0){
                newSelectY = 2*(number-1) + 3;
            } else {
                newSelectY = 21;
            }
            System.out.println(c);
        }
        org.editLayer(">", "selector", newSelectY, indexX);
    }

    /**
     * Bring up the menus (meanwhile pausing everything else)
     */
    void newShow() {
        System.out.println("Bringing up menu...");
        player.frozen = true;
        org.getLayer(player.layerName).setImportance(false);
        player.room.setObjsPause(true);

        menuID = TOP;
        newSelectY = 2;

        selectorLayer.clear();
        org.addLayer(topMenuLayer);
        org.addLayer(selectorLayer);
        org.addLayer(infoLayer);

        Window window = org.getWindow();
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MenuTimer(window, keyListener), 10 , 99);
    }

    /**
     * Exits the entire inventory menu thing.
     * (Cleans up all the layers that may have been made, and then sets menuID to EXIT)
     */
    void exitAllMenus() {
        menuID = EXIT;
        infoLayer.clear();
        selectorLayer.clear();
        org.removeLayer("top");
        org.removeLayer("quit");
        org.removeLayer("items");
        org.removeLayer("equip");
        org.removeLayer("tater");
        org.removeLayer("spells");
        org.removeLayer("invInfo");
        org.removeLayer("selector");
    }

    /**
     * Update the selector position on the screen and operate the appropriate menu
     *
     * @param menuType magical number for current submenu: TOP, SPELLS, ITEMS, EQUIP, or QUIT
     */
    private void newUpdateSelector(int menuType) {
        //long beginNano = System.nanoTime();
        org.editLayer(" ", "selector", newSelectY, indexX);

        switch (menuType) {
            case TOP:
                operateTopMenu();
                break;
            case SPELLS:
                operateSpellsMenu();
                break;
            case ITEMS:
                operateItemsMenu();
                break;
            case EQUIP:
                operateEquipMenu();
                break;
            case QUIT:
                operateQuitMenu();
                break;
            case UPGRADE:
                operateUpgradeMenu();
                break;
        }
        pressedA = false;
        pressedS = false;
        pressedD = false;

        int indexY = newSelectY;
        org.editLayer(">", "selector", indexY, indexX);
        //System.out.println(String.format("Time to process menu: %1$dmcs",(System.nanoTime() - beginNano) / 1000));
    }

    /**
     * The small, top menu, when you first press 'w'.
     * Submenus: spells, items, equip, quit
     */
    private void operateTopMenu() {
        loopAtMenuEnd(2, 6);
        indexX = 28;
        if (pressedA) {
            switch (newSelectY) {
                case 2:
                    jumpToNewMenu(spellsMenuLayer, SPELLS, "top");
                    newSelectY = 3;
                    break;
                case 3:
                    jumpToNewMenu(itemsMenuLayer, ITEMS, "top");
                    newSelectY = 3;
                    break;
                case 4:
                    jumpToNewMenu(equipMenuLayer, EQUIP, "top");
                    newSelectY = 3;
                    break;
                case 5:
                    jumpToNewMenu(quitMenuLayer, QUIT, "top");
                    newSelectY = 4;
                    break;
                case 6:
                    exitAllMenus();
                    break;
            }
        }
    }

    /**
     * Do some cleanup and prep to moving to a different menu
     *
     * @param goTo  a Layer which you'll be going to
     * @param newID the magical caps-lock variable that defines the new menu
     * @param from  the string name of the layer you came from (so it can be removed)
     */
    private void jumpToNewMenu(Layer goTo, int newID, String from) {
        //selectorLayer.clear();
        org.removeLayer(from);
        org.removeLayer("selector");
        org.removeLayer("invInfo");
        selectorLayer.clear();
        infoLayer.clear();
        org.addLayer(goTo);
        org.addLayer(selectorLayer);
        org.addLayer(infoLayer);
        menuID = newID;
    }

    /**
     * The submenu containing spells
     */
    private void operateSpellsMenu() {
        loopAtMenuEnd();
        genericItemListing(spells);
        indexX = 31;

        char[] chars = player.spell1.getName().toCharArray();
        for (int ii = 0; ii < chars.length; ii++) {
            selectorLayer.setStr(16, 15 + ii, String.valueOf(chars[ii]));
        }
        chars = player.spell2.getName().toCharArray();
        for (int ii = 0; ii < chars.length; ii++) {
            selectorLayer.setStr(17, 15 + ii, String.valueOf(chars[ii]));
        }

        if (pressedA && newSelectY == 21) {
            jumpToNewMenu(topMenuLayer, TOP, "spells");
        }
        checkNewPage();
        int index = newSelectY - 3 + ((page - 1) * 16);
        if (index < spells.size() && newSelectY < 19) {
            if (pressedS) {
                player.spell1 = spells.get(index);
                putPrimary(spells.get(index).getIcon());
                System.out.println("PRIMARY SET TO: " + spells.get(index).getName());
            }
            if (pressedD) {
                player.spell2 = spells.get(index);
                putSecondary(spells.get(index).getIcon());
                System.out.println("SECONDARY SET TO: " + spells.get(index).getName());
            }
        }
    }

    /**
     * The submenu containing boring (non-spell, non-equipment) items
     */
    private void operateItemsMenu() {
        loopAtMenuEnd();
        genericItemListing(items);

        indexX = 31;
        if (pressedA) {
            if (newSelectY == 21) {
                jumpToNewMenu(topMenuLayer, TOP, "items");
            }

        }
        checkNewPage();
        int index = newSelectY - 3 + ((page - 1) * 16);
        if (index < items.size() && newSelectY < 19) {
            if (pressedA) {
                Item thing = items.get(index);
                if (thing.getName().equals("Magic Potato")){
                    jumpToNewMenu(taterMenuLayer, UPGRADE, "items");
                    potatoIndex = index;
                }
            }
        }
    }

    /**
     * The submenu that deals with upgrades from Magic Potatoes
     */

    private void operateUpgradeMenu() {
        loopAtMenuEnd(4, 6);
        indexX = 28;

        if (pressedA) {
            switch (newSelectY){
                case 4:
                    player.maxHP += 5;
                    player.setHealth(player.maxHP);
                    items.remove(potatoIndex);
                    break;
                case 5:
                    player.maxMana += 5;
                    items.remove(potatoIndex);
                    break;
            }
            jumpToNewMenu(topMenuLayer, TOP, "tater");
        }
    }

    /**
     * Incrament page number if you pressed A and were over the right spot
     */
    private void checkNewPage() {
        if (newSelectY == 20 && pressedA) {
            page++;
        }
    }

    /**
     * Loop the selector in the menu, so that if you go off the bottom, you start back at the top and vice versa
     *
     * @param minAllowedYCoord minimum allowed Y coordinate before looping
     * @param maxAllowedYCoord maximum allowed Y coordinate before looping
     */
    private void loopAtMenuEnd(int minAllowedYCoord, int maxAllowedYCoord) {
        if (newSelectY <= minAllowedYCoord - 1) {
            newSelectY = maxAllowedYCoord;
        }
        if (newSelectY >= maxAllowedYCoord + 1) {
            newSelectY = minAllowedYCoord;
        }
    }

    /**
     * Loop the selector in the menu, so that if you go off the bottom, you start back at the top and vice versa.
     * Passed without parameters, this will default to the standard menu size, with maxima at 3 and 21.
     */
    private void loopAtMenuEnd() {
        loopAtMenuEnd(3, 21);
    }

    /**
     * The submenu with equipment
     */
    private void operateEquipMenu() {
        loopAtMenuEnd();
        genericItemListing(equip);
        indexX = 31;

        dispInt(player.defense, 12, 17, true);
        dispInt(player.allSpellBoost, 25, 18, true);
        dispInt(player.arcSpellBoost, 15, 19, false);
        dispInt(player.darkSpellBoost, 15, 20, false);
        dispInt(player.fireSpellBoost, 25, 19, false);
        dispInt(player.iceSpellBoost, 25, 20, false);

        putText(player.weapon.getName(), 9, 16);
        putText(player.armor.getName(), 9, 17);

        if (pressedA) {
            int index = newSelectY - 3 + ((page - 1) * 16);
            if (index < equip.size() && newSelectY < 19) {
                player.equip(equip.get(index));
            }
            if (newSelectY == 21) {
                jumpToNewMenu(topMenuLayer, TOP, "equip");
            }
        }
        checkNewPage();
    }

    /**
     * The submenu asking whether you really want to quit and save.  Hopefully ends up at the top MainMenu again.
     */
    private void operateQuitMenu() {
        loopAtMenuEnd(4, 5);
        indexX = 28;

        if (pressedA) {
            if (newSelectY == 5) {
                jumpToNewMenu(topMenuLayer, TOP, "quit");
            }
            if (newSelectY == 4) {
                player.saveGame();
                player.subtractHealth(2100000000, "For your convenience, you died.\n  Just press enter and carry on."); // cleanup
                exitAllMenus();
            }
        }
    }

    private void dispInt(int value, int x, int y, boolean includeZero) {
        String disp = String.valueOf(value);
        if (!disp.equals("0") || includeZero) {
            org.editLayer(String.valueOf(disp.charAt(0)), "equip", y, x);
        } else {
            org.editLayer(" ", "equip", y, x);
        }
        if (disp.length() > 1) {
            org.editLayer(String.valueOf(disp.charAt(1)), "equip", y, x + 1);
        }
    }

    /**
     * Show the listing for all the item-item-items (like, Item.java that aren't spells or equipment)
     * @param items dem array-list of you-know-what!
     */
    private void genericItemListing(ArrayList<Item> items) {
        double pageReq = Math.ceil((double) items.size() / 16);

        if (pageReq == 0) {
            pageReq = 1;
        }
        if (page > pageReq) {
            page = 1;
        }

        org.editLayer(String.valueOf((int) pageReq), "selector", 2, 41);
        org.editLayer(String.valueOf(page), "selector", 2, 39);
        fillItemNames(items, 33, 3, page);

        int index = newSelectY - 3 + ((page - 1) * 16);

        if (index != prevIndex){
            org.getLayer("invInfo").clear();
            System.out.println(String.format("Prev Index: %d vs. %d", prevIndex, index));
        }
        prevIndex = index;

        if (index < items.size() && newSelectY < 19) {
            fillInfoText(items.get(index).getDesc(), 1, 1);
        }
    }

    private void fillInfoText(String text, int startX, int startY) {
        int line = 1;
        int newLineAdjust = 0;
        for (int ii = 0; ii < text.length(); ii++) {
            if (text.charAt(ii) == '\n') {
                line++;
                newLineAdjust = ii + 1;
            } else {
                org.editLayer(String.valueOf(text.charAt(ii)), "invInfo", startY + line, startX + ii - newLineAdjust);
            }
        }
    }

    private void fillItemNames(ArrayList<Item> items, int startX, int startY, int page) {
        int pageOffset = (page - 1) * 16;
        int pageSize = items.size() - pageOffset;
        if (pageSize > 16) {
            pageSize = 16;
        }
        for (int ii = 0; ii < pageSize; ii++) {
            String itemName = items.get(ii + pageOffset).getName();
            putText(itemName, startX, startY + ii);
        }
    }

    /**
     * In the selector layer, incrementally place a text starting at an X,Y coordinate
     *  @param text a string to be displayed
     * @param xStart    starting X coordinate
     * @param yStart    Y coordinate
     */
    private void putText(String text, int xStart, int yStart) {
        for (int iii = 0; iii < text.length(); iii++) {
            org.editLayer(String.valueOf(text.charAt(iii)), "selector", yStart, xStart + iii);
        }
    }

    /**
     * Put a new item Art in the Primary Spell place
     *
     * @param thing a String (not String[][], this does that!).
     */
    private void putPrimary(String thing) {
        int y = 19;
        int x = 30;
        char[] arrThing = thing.toCharArray();
        for (int i = 0; i < arrThing.length; i++) {
            org.editLayer(String.valueOf(arrThing[i]), selectedSpellsLayer.name, y, i + x);
        }
    }

    /**
     * Put a new item Art in the Secondary Spell place
     *
     * @param thing a String (not String[], this does that!).
     */
    private void putSecondary(String thing) {
        int y = 19;
        int x = 38;
        char[] arrThing = thing.toCharArray();
        for (int i = 0; i < arrThing.length; i++) {
            org.editLayer(String.valueOf(arrThing[i]), selectedSpellsLayer.name, y, i + x);
        }
    }

    private class MenuTimer extends TimerTask{
        Window window;
        Navigator keyListener;

        MenuTimer(Window windouw, Navigator navi){
            window = windouw;
            keyListener = navi;
        }

        public void run(){
            if (menuID == EXIT){
                org.removeLayer("selector");
                org.removeLayer("top");
                window.txtArea.removeKeyListener(keyListener);
                player.frozen = false;
                org.getLayer(player.layerName).setImportance(true);
                player.room.setObjsPause(false);
            } else {
                newUpdateSelector(menuID);
            }
        }
    }
}


class Navigator extends KeyAdapter {
    private Inventory inv;

    Navigator(Inventory inventory) {
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
        if (Character.isDigit(event.getKeyChar())) {
            inv.keyPressed(event.getKeyChar());
        }
        if (key == 'A' || key == KeyEvent.VK_ENTER) {
            inv.pressedA = true;
        }
        if (key == 'S') {
            inv.pressedS = true;
        }
        if (key == 'D') {
            inv.pressedD = true;
        }
        if (key == '\\') {
            System.out.println(inv.getY());
        }
        if (key == KeyEvent.VK_ESCAPE || event.getKeyChar() == 'w') {
            inv.exitAllMenus();
        }
    }
}

