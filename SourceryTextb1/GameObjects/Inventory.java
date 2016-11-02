package SourceryTextb1.GameObjects;

import SourceryTextb1.*;
import SourceryTextb1.Window;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;


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
    //Cursor stuff
    private int cursorY = 2;
    private int cursorX = 28;

    private int selectYChange = 0;
    private int selectXChange = 0;
    private boolean updateCursor = false;

    private int prevIndex = 0;
    private int potatoIndex = 0;

    private boolean shouldRedraw = true;

    private final int NEUTRAL = -1;
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

    private Layer topMenuLayer;
    private Layer quitMenuLayer;
    private Layer itemsMenuLayer;
    private Layer spellsMenuLayer;
    private Layer equipMenuLayer;
    private Layer taterMenuLayer;
    private Layer selectorLayer;
    private Layer infoLayer;
    private Layer selectedSpellsLayer;

    private Color weaponColor = new Color(255, 210, 200);
    private Color armorColor = new Color(200, 255, 255);

    int getY() {
        return cursorY;
    }

    Inventory(ImageOrg orgo, Player p) {
        org = orgo;
        player = p;
        spellsMenuLayer = new Layer(Art.strToArray(new Art().spellsMenu), "spells" + player.getUsername(), 1, 0, false, true);
        spellsMenuLayer.setOwningPlayerUsername(p.getUsername());
        selectedSpellsLayer = new Layer(new String[22][47], "selectedSpells" + player.getUsername(), false);
        selectedSpellsLayer.setOwningPlayerUsername(p.getUsername());
        topMenuLayer = new Layer(Art.strToArray(new Art().topMenu), "top" + player.getUsername(), 1, 27, false, true);
        topMenuLayer.setOwningPlayerUsername(p.getUsername());
        quitMenuLayer = new Layer(Art.strToArray(new Art().quitMenu), "quit" + player.getUsername(), 1, 27, false, true);
        quitMenuLayer.setOwningPlayerUsername(p.getUsername());
        itemsMenuLayer = new Layer(Art.strToArray(new Art().itemsMenu), "items" + player.getUsername(), 1, 0, false, true);
        itemsMenuLayer.setOwningPlayerUsername(p.getUsername());
        equipMenuLayer = new Layer(Art.strToArray(new Art().equipMenu), "equip" + player.getUsername(), 1, 0, false, true);
        equipMenuLayer.setOwningPlayerUsername(p.getUsername());
        taterMenuLayer = new Layer(Art.strToArray(new Art().taterMenu), "tater" + player.getUsername(), 1, 27, false, true);
        taterMenuLayer.setOwningPlayerUsername(p.getUsername());
        selectorLayer = new Layer(new String[22][46], "selector" + player.getUsername(), 0, 0, false, false);
        selectorLayer.setOwningPlayerUsername(p.getUsername());
        infoLayer = new Layer(new String[22][46], "invInfo" + player.getUsername(), 0, 0, false, false);
        infoLayer.setOwningPlayerUsername(p.getUsername());

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
     * Returns an item based upon the name of what you're looking for.
     * @param invSection use "items", "spells", "equip" (It isn't case-sensitive, which should mitigate some human error)
     */
    Item getItem(String name, String invSection){
        ArrayList<Item> list;
        switch (invSection.toLowerCase()){
            case "items":
                list = items;
                break;
            case "spells":
                list = spells;
                break;
            case "equip":
                list = equip;
                break;
            default:
                return null;
        }
        for (Item it : list){
            if (it.getName().equals(name)){
                return it;
            }
        }
        return null;
    }

    /**
     * Gives player a bunch of items to probably be added later
     */

    void testKit(){
        Item item1 = new Item("InitiateTome", "A book full of dark rituals\n\nNewcomers to dark magic are\n handed this book to study.\n\nYes, it's a textbook.\n\n+3 Dark Spell Damage", player, "equip");
        item1.setEquipvals(0, 0, 0, 0, 0, 0, 3, "weapon");
        equip.add(item1);
        Item item2 = new Item("Wool Scarf", "A fluffy scarf.\n\nWhat's not to love in\n a scarf?\n\nThey're magical, comfy, and\n fashionable!\nAwesome, right?\n\n+1 Ice Spell Damage\n+1 Defense", player, "equip");
        item2.setEquipvals(1, 0, 0, 0, 0, 1, 0, "weapon");
        equip.add(item2);
        Item item3 = new Item("Stat Bomb", "It does everything!\n\nDon't actually use this\n in the game.\n\n+1 To all equipment stats.", player, "equip");
        item3.setEquipvals(1, 1, 1, 1, 1, 1, 1, "weapon");
        equip.add(item3);
        Item item5 = new Item("NeodymNeedle", "Arcane spell;\n\nA neodymium needle.\n\nIt is so magnetic it will\n fly around walls to hit\n enemies; no aim required.", "NeoNd", player, "spell", true);
        item5.dmgSpellDefine(6, 20, 7, "arcane", new SpecialText("\\"), new SpecialText("/"), true);
        spells.add(item5);
        Item item6 = new Item("Evil Powers", "Dark Spell;\n\nYou're not sure what it is\n exactly, but it has the\n word 'power' in its name,\n so it must be good,\n right?", "EvlPw", player, "spell", true);
        item6.dmgSpellDefine(1, 8, 2, "dark", new SpecialText("*", new Color(155, 55, 155)), new SpecialText("*", new Color(255, 55, 255)), true);
        spells.add(item6);
        Item item7 = new Item("Witch Scarf", "A scarf imbued with witch\n magic, granting the user\n increased dark and ice\n magic power\n\nIt smells like flowers.\n\n+1 Ice Spell Damage\n+1 Dark Spell Damage\n+1 Defense\n+3 Max Health", player, "equip");
        item7.setEquipvals(1, 3, 0, 0, 0, 2, 3, "weapon");
        equip.add(item7);
        for (int ii = 0; ii < 4; ii++) {
            Item item4 = new Item("Carrot", "For some reason,\n they only grow\n in the mountains.\n\nNobody really know why.", player, "items");
            item4.healItemDefine(6, 3);
            items.add(item4);
        }
    }

    /**
     * Press a key?  Call this to do stuff!
     *
     * @param c which character you have pressed on the board
     */
    void keyPressed(char c) {
        //org.editLayer(" ", selectorLayer, cursorY, cursorX);
        switch (c) {
            case '©': // Up
                selectYChange--;
                break;
            case '®': // Down
                selectYChange++;
                break;
            default:
                break;
        }
        if (Character.isDigit(c) && (menuID == SPELLS || menuID == ITEMS || menuID == EQUIP)){
            int number = Integer.valueOf(String.valueOf(c));
            if (number != 0){
                selectYChange = (2*(number-1) + 3) - cursorY;
            } else {
                selectYChange = 21 - cursorY;
            }
            System.out.println(c);
        }
        //org.editLayer(">", selectorLayer, cursorY, cursorX);
    }

    /**
     * Bring up the menus (meanwhile pausing everything else)
     */
    void newShow() {
        System.out.println("Bringing up menu...");
        org = player.orgo; // Update the org (cuz it changes based on room now)
        player.frozen = true;
        org.getLayers().stream().filter(lay -> lay.name.contains("playerLayer")).forEach(lay -> lay.setImportance(false));
        player.room.setObjsPause(true);

        updateCursor = true;

        menuID = TOP;
        cursorY = 2;
        cursorX = 28;

        selectorLayer.clear();
        selectorLayer.setStr(cursorY,cursorX, ">");
        org.addLayer(topMenuLayer);
        org.addLayer(selectorLayer);
        org.addLayer(infoLayer);

        Window window = player.getRealOrg().getWindow();
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
        org.removeLayer(topMenuLayer);
        org.removeLayer(quitMenuLayer);
        org.removeLayer(itemsMenuLayer);
        org.removeLayer(equipMenuLayer);
        org.removeLayer(taterMenuLayer);
        org.removeLayer(spellsMenuLayer);
        org.removeLayer(infoLayer);
        org.removeLayer(selectorLayer);
        player.resetMovement();
    }

    /**
     * Update the selector position on the screen and operate the appropriate menu
     *
     * @param menuType magical number for current submenu: TOP, SPELLS, ITEMS, EQUIP, or QUIT
     */
    private void newUpdateSelector(int menuType) {
        //long beginNano = System.nanoTime();

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

        if (selectYChange != 0 || selectXChange != 0 || updateCursor) {
            org.editLayer(" ", selectorLayer, cursorY, cursorX);
            cursorY += selectYChange;
            cursorX += selectXChange;
            org.editLayer(">", selectorLayer, cursorY, cursorX);
            selectYChange = 0;
            selectXChange = 0;
            if (updateCursor){
                System.out.println("Cursor update!");
            }
            updateCursor = false;
        }
        //System.out.println(String.format("Time to process menu: %1$dmcs",(System.nanoTime() - beginNano) / 1000));
    }

    /**
     * The small, top menu, when you first press 'w'.
     * Submenus: spells, items, equip, quit
     */
    private void operateTopMenu() {
        loopAtMenuEnd(2, 6);
        //cursorX = 28;
        if (pressedA) {
            switch (cursorY) {
                case 2:
                    jumpToNewMenu(spellsMenuLayer, SPELLS, topMenuLayer, 31);
                    cursorY = 3;
                    break;
                case 3:
                    jumpToNewMenu(itemsMenuLayer, ITEMS, topMenuLayer, 31);
                    cursorY = 3;
                    break;
                case 4:
                    jumpToNewMenu(equipMenuLayer, EQUIP, topMenuLayer, 31);
                    cursorY = 3;
                    break;
                case 5:
                    jumpToNewMenu(quitMenuLayer, QUIT, topMenuLayer, 28);
                    cursorY = 4;
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
    private void jumpToNewMenu(Layer goTo, int newID, Layer from, int newXIndex) {
        //selectorLayer.clear();

        org.removeLayer(from);
        selectorLayer.clear();
        infoLayer.clear();
        org.removeLayer(selectorLayer);
        org.removeLayer(infoLayer);
        org.addLayer(goTo);
        org.addLayer(selectorLayer);
        org.addLayer(infoLayer);
        menuID = newID;
        updateCursor = true;
        selectXChange = newXIndex - cursorX;
        //org.editLayer(" ", selectorLayer, cursorY, cursorX);
        //System.out.println(selectXChange);
    }

    /**
     * The submenu containing spells
     */
    private void operateSpellsMenu() {
        loopAtMenuEnd();
        genericItemListing(spells);
        //cursorX = 31;

        checkNewPage();
        int index = cursorY - 3 + ((page - 1) * 16);
        if (index < spells.size() && cursorY < 19) {
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

        char[] chars = player.spell1.getName().toCharArray();
        for (int ii = 0; ii < chars.length; ii++) {
            org.editLayer(String.valueOf(chars[ii]), infoLayer, 16, 15 + ii);
        }
        chars = player.spell2.getName().toCharArray();
        for (int ii = 0; ii < chars.length; ii++) {
            org.editLayer(String.valueOf(chars[ii]), infoLayer, 17, 15 + ii);
        }
        if (pressedA && cursorY == 21) {
            infoLayer.clear();
            jumpToNewMenu(topMenuLayer, TOP, spellsMenuLayer, 28);
        }
    }

    /**
     * The submenu containing boring (non-spell, non-equipment) items
     */
    private void operateItemsMenu() {
        loopAtMenuEnd();
        genericItemListing(items);

        //cursorX = 31;
        if (pressedA) {
            if (cursorY == 21) {
                jumpToNewMenu(topMenuLayer, TOP, itemsMenuLayer, 28);
            }

        }
        checkNewPage();
        int index = cursorY - 3 + ((page - 1) * 16);
        if (index < items.size() && cursorY < 19) {
            if (pressedA) {
                Item thing = items.get(index);
                if (thing.getName().equals("Magic Potato")){
                    jumpToNewMenu(taterMenuLayer, UPGRADE, itemsMenuLayer, 28);
                    potatoIndex = index;
                }
                if (thing.getDescMode().equals("healitem")){
                    player.restoreHealth(thing.healing, thing.overheal);
                    items.remove(index);
                    shouldRedraw = true;
                }
            }
        }
    }

    /**
     * The submenu that deals with upgrades from Magic Potatoes
     */

    private void operateUpgradeMenu() {
        loopAtMenuEnd(4, 6);
        //cursorX = 28;

        if (pressedA) {
            switch (cursorY){
                case 4:
                    player.baseMaxHP += 5;
                    player.setHealth(player.baseMaxHP);
                    player.defineStats();
                    items.remove(potatoIndex);
                    break;
                case 5:
                    player.maxMana += 5;
                    items.remove(potatoIndex);
                    break;
            }
            jumpToNewMenu(topMenuLayer, TOP, taterMenuLayer, 28);
        }
    }

    /**
     * Incrament page number if you pressed A and were over the right spot
     */
    private void checkNewPage() {
        if (cursorY == 20 && pressedA) {
            page++;
            shouldRedraw = true;
        }
    }

    /**
     * Loop the selector in the menu, so that if you go off the bottom, you start back at the top and vice versa
     *
     * @param minAllowedYCoord minimum allowed Y coordinate before looping
     * @param maxAllowedYCoord maximum allowed Y coordinate before looping
     */
    private void loopAtMenuEnd(int minAllowedYCoord, int maxAllowedYCoord) {
        if (cursorY + selectYChange <= minAllowedYCoord - 1) {
            selectYChange = maxAllowedYCoord - cursorY;
            System.out.println("Below the min allowed coord, buddy.");
        }
        if (cursorY + selectYChange>= maxAllowedYCoord + 1) {
            selectYChange = minAllowedYCoord - cursorY;
            System.out.println("Over the max allowed coord, buddy.");
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
        genericItemListing(equip, true);
        //cursorX = 31;

        dispInt(player.defense, 15, 17, true, new Color(150, 200, 200));
        dispInt(player.maxHealth, 25, 17, true, new Color(175, 255, 175));
        dispInt(player.allSpellBoost, 25, 18, true, new Color(255, 175, 175));
        dispInt(player.arcSpellBoost, 15, 19, false, new Color(100, 100, 255));
        dispInt(player.darkSpellBoost, 15, 20, false, new Color(125, 25, 155));
        dispInt(player.fireSpellBoost, 25, 19, false, new Color(225, 150, 0));
        dispInt(player.iceSpellBoost, 25, 20, false, new Color(200, 225, 255));

        putText(player.weapon.getName(), 9, 16, weaponColor);
        putText(player.armor.getName(), 9, 17, armorColor);

        if (pressedA) {
            int index = cursorY - 3 + ((page - 1) * 16);
            if (index < equip.size() && cursorY < 19) {
                int prevMaxHP = player.maxHealth;
                player.equip(equip.get(index));
                player.addHealth(player.maxHealth - prevMaxHP);
            }
            if (cursorY == 21) {
                jumpToNewMenu(topMenuLayer, TOP, equipMenuLayer, 28);
            }
        }
        checkNewPage();
    }

    /**
     * The submenu asking whether you really want to quit and save.  Hopefully ends up at the top MainMenu again.
     */
    private void operateQuitMenu() {
        loopAtMenuEnd(2, 5);
        //cursorX = 28;

        if (pressedA) {
            if (cursorY == 2) {
                jumpToNewMenu(topMenuLayer, TOP, quitMenuLayer,28);
            }
            if (cursorY == 3){
                player.saveGame();
                jumpToNewMenu(topMenuLayer, TOP, quitMenuLayer, 28);
            }
            if (cursorY == 4) {
                player.saveGame();
                player.subtractHealth(2100000000, "IT SEEMS THAT SOME ALIEN\n FORCE HAS INSTANTLY KILLED YOU."); // cleanup, with style
                exitAllMenus();
            }
            if (cursorY == 5) {
                player.subtractHealth(2100000000, "IT SEEMS THAT SOME UNFORGIVING\n FORCE HAS INSTANTLY KILLED YOU."); // cleanup, with style
                exitAllMenus();
            }
        }
    }

    //private void dispInt(int value, int x, int y, boolean includeZero){ dispInt(value, x, y, includeZero, Color.WHITE);}

    private void dispInt(int value, int x, int y, boolean includeZero, Color txtColor) {
        String disp = String.valueOf(value);
        if (!disp.equals("0") || includeZero) {
            org.editLayer(new SpecialText(String.valueOf(disp.charAt(0)), txtColor), equipMenuLayer, y, x);
        } else {
            org.editLayer(" ", equipMenuLayer, y, x);
        }
        if (disp.length() > 1) {
            org.editLayer(new SpecialText(String.valueOf(disp.charAt(1)), txtColor), equipMenuLayer, y, x + 1);
        }
    }

    /**
     * Show the listing for all the item-item-items (like, Item.java that aren't spells or equipment)
     * @param items dem array-list of you-know-what!
     */
    private void genericItemListing(ArrayList<Item> items) {
        genericItemListing(items, false);
    }

    private void genericItemListing(ArrayList<Item> items, boolean highlightEquip) {
        double pageReq = Math.ceil((double) items.size() / 16);

        if (shouldRedraw){
            //org.getLayer(selectorLayer).clear();
            infoLayer.clear();
            //clearItemInfo();
            shouldRedraw = false;
        }


        if (pageReq == 0) {
            pageReq = 1;
        }
        if (page > pageReq) {
            page = 1;
        }

        org.editLayer(String.valueOf((int) pageReq), selectorLayer, 2, 41);
        org.editLayer(String.valueOf(page), selectorLayer, 2, 39);
        fillItemNames(items, 33, 3, page, highlightEquip);

        int index = cursorY - 3 + ((page - 1) * 16);

        if (index != prevIndex){
            clearItemInfo();
            System.out.println(String.format("Prev Index: %d vs. %d", prevIndex, index));
        }
        if (pressedS || pressedD){
            infoLayer.clear();
            System.out.println("Clearing infoLayer...");
        }
        prevIndex = index;

        if (index < items.size() && cursorY + selectYChange < 19) {
            fillInfoText(items.get(index).getDesc(), 1, 1);
        }
    }

    private void clearItemInfo(){
        for (int ix = 0; ix < 29; ix++){
            for(int iy = 0; iy < 13; iy++){
                org.editLayer(" ", infoLayer, iy, ix);
            }
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
                org.editLayer(String.valueOf(text.charAt(ii)), infoLayer, startY + line, startX + ii - newLineAdjust);
            }
        }
    }

    private void fillItemNames(ArrayList<Item> items, int startX, int startY, int page, boolean highilghtEquip) {
        int pageOffset = (page - 1) * 16;
        int pageSize = items.size() - pageOffset; //The amount of elements on page shown
        if (pageSize > 16) {
            pageSize = 16;
        }
        for (int ii = 0; ii < pageSize; ii++) {
            Item toDraw = items.get(ii + pageOffset);
            Color drawWith = Color.WHITE;
            if (highilghtEquip){
                if (toDraw.equals(player.armor)){
                    drawWith = armorColor;
                } else if
                    (toDraw.equals(player.weapon)){
                    drawWith = weaponColor;
                }
            }
            String itemName = toDraw.getName();
            putText(itemName, startX, startY + ii, drawWith);
        }
    }

    /**
     * In the selector layer, incrementally place a text starting at an X,Y coordinate
     *  @param text a string to be displayed
     * @param xStart    starting X coordinate
     * @param yStart    Y coordinate
     */
    private void putText(String text, int xStart, int yStart, Color toDrawWith){
        putText(text, xStart, yStart, toDrawWith, 12);
    }

    private void putText(String text, int xStart, int yStart, Color toDrawWith, int maxLength) {
        int iter = 0;
        for (int iii = 0; iii < text.length(); iii++) {
            SpecialText toPut = new SpecialText(String.valueOf(text.charAt(iii)), toDrawWith);
            org.editLayer(toPut, infoLayer, yStart, xStart + iii);
            iter++;
        }
        for (int iif = iter; iif < maxLength; iif++){ //Fixes string by concatenating blank spaces until maxLength
            org.editLayer(" ", infoLayer, yStart, xStart + iif);
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
                org.removeLayer(selectorLayer);
                org.removeLayer(topMenuLayer);
                window.txtArea.removeKeyListener(keyListener);
                player.frozen = false;
                player.resetMovement();
                System.out.println("Exiting menu through top menu....");
                org.getLayers().stream().filter(lay -> lay.name.contains("playerLayer")).forEach(lay -> lay.setImportance(true));
                player.room.setObjsPause(false);
                menuID = NEUTRAL;
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

