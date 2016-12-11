package SourceryTextb1.GameObjects;

import SourceryTextb1.Art;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.SpecialText;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


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
    private Timer timer = new Timer("InventoryTimer");
    //Cursor stuff
    private int cursorY = 2;
    private int cursorX = 28;

    private int pageChange = 0;

    private int prevIndex = 0;
    private int potatoIndex = 0;

    private boolean shouldRedraw = true;

    public boolean inCmdLine = false;

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

    private Color healingColor = new Color(225, 255, 125);
    private Color potionColor = new Color(255, 185, 100);

    int getY() {
        return cursorY;
    }

    Inventory(ImageOrg orgo, Player p) {
        org = orgo;
        player = p;
        spellsMenuLayer = new Layer(Art.strToArray(new Art().spellsMenu), "spells" + player.getUsername(), 0, 1, false, true, true);
        spellsMenuLayer.setOwningPlayerUsername(p.getUsername());
        spellsMenuLayer.findAndReplace(new SpecialText(","), new SpecialText("_", new Color(15, 15, 17), new Color(20, 20, 23)));
        selectedSpellsLayer = new Layer(new String[22][47], "selectedSpells" + player.getUsername(), 0, 0, false, false, true);
        selectedSpellsLayer.setOwningPlayerUsername(p.getUsername());
        topMenuLayer = new Layer(Art.strToArray(new Art().topMenu), "top" + player.getUsername(), 27, 1, false, true, true);
        topMenuLayer.setOwningPlayerUsername(p.getUsername());
        quitMenuLayer = new Layer(Art.strToArray(new Art().quitMenu), "quit" + player.getUsername(), 27, 1, false, true, true);
        quitMenuLayer.setOwningPlayerUsername(p.getUsername());
        itemsMenuLayer = new Layer(Art.strToArray(new Art().itemsMenu), "items" + player.getUsername(), 0, 1, false, true, true);
        itemsMenuLayer.setOwningPlayerUsername(p.getUsername());
        itemsMenuLayer.findAndReplace(new SpecialText(","), new SpecialText("_", new Color(15, 15, 15), new Color(20, 20, 20)));
        equipMenuLayer = new Layer(Art.strToArray(new Art().equipMenu), "equip" + player.getUsername(), 0, 1, false, true, true);
        equipMenuLayer.setOwningPlayerUsername(p.getUsername());
        equipMenuLayer.findAndReplace(new SpecialText(","), new SpecialText("_", new Color(15, 17, 15), new Color(20, 22, 20)));
        taterMenuLayer = new Layer(Art.strToArray(new Art().taterMenu), "tater" + player.getUsername(), 27, 1, false, true, true);
        taterMenuLayer.setOwningPlayerUsername(p.getUsername());
        selectorLayer = new Layer(new String[1][1], "selector" + player.getUsername(), 0, 0, false, false, true);
        selectorLayer.setOwningPlayerUsername(p.getUsername());
        infoLayer = new Layer(new String[22][46], "invInfo" + player.getUsername(), 0, 0, false, false, true);
        infoLayer.setOwningPlayerUsername(p.getUsername());

        submenuColoring(spellsMenuLayer);
        submenuColoring(itemsMenuLayer);
        submenuColoring(equipMenuLayer);

        /*
        spells.add(new Item("Spark","Arcane Spell;\nFires a spark of energy.\n\n\"All great fires start\n with small sparks\"", "Spark", player, "spell"));
        spells.add(new Item("Fireball","Fire Spell;\nUse your imagination", "FrBll", player, "spell"));
        spells.add(new Item("Frostbite","Ice Spell;\nFreezes an enemy right\n in front of you.", "FrstB", player, "spell"));
        spells.add(new Item("Shadow Knife","Dark Spell;\nThrows a blade made of\n forbidden magic.\n\nThe dart seems to have a \n soul that refuses to die," +
                "\n and thus travels very far.", "ShKnf", player, "spell"));
        */
        equip.add(new Item("Dusty Robe", "Dust is baked into this\n old robe.\n\nThe newest students at\n The Magic Academy get\n only hand-me-downs. As a" +
                "\n result, they are usually\n really, really old.", "equip"));
        equip.get(0).setEquipvals(2, 0, 0, 0, 0, 0, 0, "armor");

        //items.add(new Item("Magic Potato","A magically enhanced potato\n\nCan be used to either\n permanently increase\n your max health or\n max mana by 5.", player, "item"));

        player.armor = equip.get(0);
        player.defineStats();
    }

    private void submenuColoring(Layer toColor) {
        for (int ii = 9; ii >= 0; ii--)
            toColor.findAndReplace(new SpecialText(String.valueOf(ii)), new SpecialText(String.valueOf(ii), new Color(30, 30, 30)));
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

    private Random rand = new Random();

    protected int r(int max) {
        return r(max, 0);
    }

    protected int r(int max, int min) {
        return rand.nextInt((max - min) + 1) + min;
    }

    /**
     * Take away a random item of a certain type
     * @param itemType what the Item.type should be:
     *                  1: spell
     *                  2: normal item
     *                  3: equipment
     * @return the item that was removed
     */
    Item removeRandomItem(int itemType) {
        Item item = null;
        int index;
        switch (itemType) {
            case 1:
                if (spells.size() > 0) {
                    index = r(spells.size()-1);
                    item = spells.get(index);
                    player.room.splashMessage(String.format("Your %1$s spell has just been stolen!", item.getName()), "", player);
                    spells.remove(index);
                }
                break;
            case 2:
                if (items.size() > 0) {
                    index = r(items.size() - 1);
                    item = items.get(index);
                    player.room.splashMessage(String.format("Your %1$s has just been stolen!", item.getName()), "", player);
                    items.remove(index);
                }
                break;
            case 3:
                if (equip.size() > 0) {
                    index = (equip.size() - 1);
                    item = equip.get(index);
                    player.room.splashMessage(String.format("Your %1$s has just been stolen!", item.getName()), "", player);
                    equip.remove(index);
                }
                break;
            case -1: // any type
                item = removeRandomItem(r(1,3)); // will only recurse once
                break;
            default:
                System.out.println("[Inventory.removeRandomItem] bad input: " + itemType + "; should be -1, 1, 2, or 3");
        }
        return item;
    }

    /**
     * Returns an item based upon the name of what you're looking for.
     *
     * @param invSection use "items", "spells", "equip" (It isn't case-sensitive, which should mitigate some human error)
     */
    Item getItem(String name, String invSection) {
        ArrayList<Item> list;
        switch (invSection.toLowerCase()) {
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
        for (Item it : list) {
            if (it.getName().equals(name)) {
                return it;
            }
        }
        return null;
    }

    /**
     * Gives player a bunch of items to probably be added later
     */

    void testKit() {
        Item item1 = new Item("InitiateTome", "A book full of dark rituals\n\nNewcomers to dark magic are\n handed this book to study.\n\nYes, it's a textbook.", "equipment");
        item1.setEquipvals(0, 0, 0, 0, 0, 0, 3, "weapon");
        equip.add(item1);
        Item item2 = new Item("Wool Scarf", "A fluffy scarf.\n\nWhat's not to love in\n a scarf?\n\nThey're magical, comfy, and\n fashionable!\nAwesome, right?", "equipment");
        item2.setEquipvals(2, 0, 0, 0, 0, 1, 0, "weapon");
        equip.add(item2);
        Item item3 = new Item("Stat Bomb", "It does everything!\nBut don't actually use this\n in the game.", "equipment");
        item3.setEquipvals(1, 1, 1, 1, 1, 1, 1, "weapon");
        equip.add(item3);
        Item item5 = new Item("NeodymNeedle", "Arcane spell;\n\nA neodymium needle.\n\nIt is so magnetic it will\n fly around walls to hit\n enemies; no aim required.", "NeoNd", "spell", true);
        item5.dmgSpellDefine(6, 20, 7, "arcane", new SpecialText("\\"), new SpecialText("/"), true, 0);
        spells.add(item5);
        Item item5AndAHalf = new Item("Fire Bomb", "Fire spell;\n\nHighly explosive throwable\n\nBoom! There goes another\n ship! So poetic!\n Deals splash damage on impact", "FireB", "spell", true);
        item5AndAHalf.dmgSpellDefine(7, 11, 9, "fire", new SpecialText("O", new Color(191, 8, 38)), new SpecialText("o", new Color(255,156,0)), false, 5);
        spells.add(item5AndAHalf);
        Item item6 = new Item("Evil Powers", "Dark Spell;\n\nYou're not sure what it is\n exactly, but it has the\n word 'power' in its name,\n so it must be good,\n right?", "EvlPw", "spell", true);
        item6.dmgSpellDefine(1, 8, 2, "dark", new SpecialText("*", new Color(155, 55, 155)), new SpecialText("*", new Color(255, 55, 255)), false, 0);
        spells.add(item6);
        Item item7 = new Item("Witch Scarf", "A scarf imbued with witch\n magic, granting the user\n increased dark and ice\n magic power\n\nIt smells like flowers.", "equipment");
        item7.setEquipvals(1, 3, 0, 0, 0, 2, 2, "weapon");
        equip.add(item7);
        Item potion = new Item("MagicTaterChip", "A singular potato chip made\n with magic potatoes.\n\nEating this chip will\n restore hp over time\n (4hp/s + 10 overheal!)", "item");
        potion.setSpellType("potion");
        potion.setDuration(30 * 1000);
        items.add(potion);
        for (int ii = 0; ii < 24; ii++) {
            Item item4 = new Item("Carrot", "For some reason,\n they only grow\n in the mountains.\n\nNobody really know why.", "item");
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
        switch (c) {
            case '↑': // Up
                cursorY--;
                break;
            case '↓': // Down
                cursorY++;
                break;
            case '←':
                pageChange--;
                break;
            case '→':
                pageChange++;
                break;
            default:
                break;
        }
        if (Character.isDigit(c) && (menuID == SPELLS || menuID == ITEMS || menuID == EQUIP)) {
            int number = Integer.valueOf(String.valueOf(c));
            if (number != 0) {
                cursorY = (2 * (number - 1) + 2);
            } else {
                cursorY = 20;
            }
            System.out.println(c);
        }
    }

    /**
     * Bring up the menus (meanwhile pausing everything else)
     */
    void newShow() {
        System.out.println("Bringing up menu...");
        org = player.org; // Update the org (cuz it changes based on room now)
        player.frozen = true;

        for (Layer lay : org.getLayers()) {
            if (lay.getName().contains("playerLayer"))
                org.setLayerImportance(lay.getName(), false);
        }
        //player.room.setObjsPause(true);

        menuID = TOP;
        cursorY = 2;
        cursorX = 28;

        pressedA = false;

        selectorLayer.setStr(0, 0, ">");
        selectorLayer.setPos(cursorX, cursorY);
        org.addLayer(topMenuLayer);
        org.addLayer(selectorLayer);
        org.addLayer(infoLayer);

        timer.scheduleAtFixedRate(new MenuTimer(), 10, 99);
    }

    /**
     * Exits the entire inventory menu thing.
     * (Cleans up all the layers that may have been made, and then sets menuID to EXIT)
     */
    private void exitAllMenus() {
        menuID = EXIT;
        infoLayer.clear();
        org.removeLayer("top" + player.getUsername());
        org.removeLayer("quit" + player.getUsername());
        org.removeLayer("items" + player.getUsername());
        org.removeLayer("equip" + player.getUsername());
        org.removeLayer("tater" + player.getUsername());
        org.removeLayer("spells" + player.getUsername());
        org.removeLayer("invInfo" + player.getUsername());
        org.removeLayer("selector" + player.getUsername());
        player.resetMovement();
        player.frozen = false;
        player.room.setObjsPause(false);
    }

    void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
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
                pageChange = 0;
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
                pageChange = 0;
                break;
            case UPGRADE:
                operateUpgradeMenu();
                pageChange = 0;
                break;
        }
        pressedA = false;
        pressedS = false;
        pressedD = false;

        Layer cursorLayer = org.getLayer(selectorLayer.getName());
        if (cursorLayer != null) cursorLayer.setPos(cursorX, cursorY);
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
                    jumpToNewMenu(spellsMenuLayer, SPELLS, topMenuLayer, 30);
                    cursorY = 2;
                    break;
                case 3:
                    jumpToNewMenu(itemsMenuLayer, ITEMS, topMenuLayer, 30);
                    cursorY = 2;
                    break;
                case 4:
                    jumpToNewMenu(equipMenuLayer, EQUIP, topMenuLayer, 30);
                    cursorY = 2;
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
        System.out.println("Going to menu " + goTo.getName());
        org.removeLayer(from);
        infoLayer.clear();

        org.addLayer(goTo);

        org.removeLayer(selectorLayer.getName());
        org.removeLayer(infoLayer.getName());
        org.addLayer(selectorLayer);
        org.addLayer(infoLayer);
        menuID = newID;
        cursorX = newXIndex;
    }

    /**
     * The submenu containing spells
     */
    private void operateSpellsMenu() {
        loopAtMenuEnd();
        genericItemListing(spells);
        //cursorX = 31;
        int index = calcIndex();
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
        if (pressedA && cursorY == 20) {
            infoLayer.clear();
            jumpToNewMenu(topMenuLayer, TOP, spellsMenuLayer, 28);
        }
        checkNewPage(spells);
    }

    /**
     * The submenu containing boring (non-spell, non-equipment) items
     */
    private void operateItemsMenu() {
        loopAtMenuEnd();
        genericItemListing(items);

        //cursorX = 31;
        if (pressedA) {
            if (cursorY == 20) {
                jumpToNewMenu(topMenuLayer, TOP, itemsMenuLayer, 28);
            }

        }
        int index = calcIndex();
        if (index < items.size() && cursorY < 19) {
            if (pressedA) {
                Item thing = items.get(index);
                if (thing.getName().equals("Magic Potato")) {
                    jumpToNewMenu(taterMenuLayer, UPGRADE, itemsMenuLayer, 28);
                    potatoIndex = index;
                }
                if (thing.getDescMode().equals("potion")) {
                    new Potion(player.room, player, thing.getName(), thing.getDuration());
                    items.remove(thing);
                    shouldRedraw = true;
                }
                if (thing.getDescMode().equals("healitem")) {
                    player.restoreHealth(thing.healing, thing.overheal);
                    items.remove(index);
                    shouldRedraw = true;
                }
            }
        }
        checkNewPage(items);
    }

    /**
     * The submenu that deals with upgrades from Magic Potatoes
     */

    private void operateUpgradeMenu() {
        loopAtMenuEnd(4, 6);
        //cursorX = 28;

        if (pressedA) {
            switch (cursorY) {
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
     * Increment page number if you pressed A and were over the right spot
     */
    private void checkNewPage(ArrayList<Item> itemList) {
        if (cursorY == 19 && pressedA) {
            page++;
            shouldRedraw = true;
        }
        if (pageChange != 0) {
            page += pageChange;
            pageChange = 0;
            shouldRedraw = true;
        }
        double maxPage = Math.ceil((double) itemList.size() / 16);
        if (maxPage == 0) {
            maxPage = 1;
        }
        if (page > maxPage) {
            page = 1;
            shouldRedraw = true;
        }
        if (page < 1) {
            page = (int) maxPage;
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
        if (cursorY <= minAllowedYCoord - 1) {
            cursorY = maxAllowedYCoord;
            System.out.println("Below the min allowed coord, buddy.");
        }
        if (cursorY >= maxAllowedYCoord + 1) {
            cursorY = minAllowedYCoord;
            System.out.println("Over the max allowed coord, buddy.");
        }
    }

    /**
     * Loop the selector in the menu, so that if you go off the bottom, you start back at the top and vice versa.
     * Passed without parameters, this will default to the standard menu size, with maxima at 3 and 21.
     */
    private void loopAtMenuEnd() {
        loopAtMenuEnd(2, 20);
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
            int index = calcIndex();
            if (index < equip.size() && cursorY < 18) {
                int prevMaxHP = player.maxHealth;
                player.equip(equip.get(index));
                player.addHealth(player.maxHealth - prevMaxHP);
            }
            if (cursorY == 20) {
                jumpToNewMenu(topMenuLayer, TOP, equipMenuLayer, 28);
            }
        }
        checkNewPage(equip);
    }

    /**
     * The submenu asking whether you really want to quit and save.  Hopefully ends up at the top MainMenu again.
     */
    private void operateQuitMenu() {
        loopAtMenuEnd(2, 5);
        //cursorX = 28;

        if (pressedA) {
            if (cursorY == 2) {
                jumpToNewMenu(topMenuLayer, TOP, quitMenuLayer, 28);
            }
            if (cursorY == 3) {
                player.saveGame();
                jumpToNewMenu(topMenuLayer, TOP, quitMenuLayer, 28);
            }
            if (cursorY == 4) {
                player.saveGame();
                player.subtractHealth(2100000000, "IT SEEMS THAT SOME ALIEN\n FORCE HAS INSTANTLY KILLED YOU.", "arcane"); // cleanup, with style
                exitAllMenus();
            }
            if (cursorY == 5) {
                player.subtractHealth(2100000000, "IT SEEMS THAT SOME UNFORGIVING\n FORCE HAS INSTANTLY KILLED YOU.", "arcane"); // cleanup, with style
                exitAllMenus();
            }
        }
    }

    //private void dispInt(int value, int x, int y, boolean includeZero){ dispInt(value, x, y, includeZero, Color.WHITE);}

    private void dispInt(int value, int x, int y, boolean includeZero, Color txtColor) {
        String disp = String.valueOf(value);
        if (!disp.equals("0") || includeZero) {
            org.editLayer(new SpecialText(String.valueOf(disp.charAt(0)), txtColor), equipMenuLayer.getName(), y, x);
        } else {
            org.editLayer(" ", equipMenuLayer.getName(), y, x);
        }
        if (disp.length() > 1) {
            org.editLayer(new SpecialText(String.valueOf(disp.charAt(1)), txtColor), equipMenuLayer.getName(), y, x + 1);
        }
    }

    /**
     * Show the listing for all the item-item-items (like, Item.java that aren't spells or equipment)
     *
     * @param items dem array-list of you-know-what!
     */
    private void genericItemListing(ArrayList<Item> items) {
        genericItemListing(items, false);
    }

    private void genericItemListing(ArrayList<Item> items, boolean highlightEquip) {

        if (shouldRedraw) {
            infoLayer.clear();
            //clearItemInfo();
            shouldRedraw = false;
        }
        double pageReq = Math.ceil((double) items.size() / 16);
        if (pageReq == 0) {
            pageReq = 1;
        }
        org.editLayer(String.valueOf((int) pageReq), infoLayer.getName(), 1, 44);
        org.editLayer(String.valueOf(page), infoLayer.getName(), 1, 42);

        fillItemNames(items, 32, 2, page, highlightEquip);

        int index = calcIndex();

        if (index != prevIndex) {
            clearItemInfo();
            System.out.println(String.format("Prev Index: %d vs. %d", prevIndex, index));
        }
        if (pressedS || pressedD) {
            infoLayer.clear();
            System.out.println("Clearing infoLayer...");
        }
        prevIndex = index;

        if (index < items.size() && cursorY < 19) {
            fillInfoText(items.get(index).getDesc(player), 1, 1);
        }
    }

    private void clearItemInfo() {
        for (int ix = 0; ix < 29; ix++) {
            for (int iy = 0; iy < 13; iy++) {
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
            if (highilghtEquip) {
                if (toDraw.equals(player.armor)) {
                    drawWith = armorColor;
                } else if
                        (toDraw.equals(player.weapon)) {
                    drawWith = weaponColor;
                }
            }
            if (toDraw.getDescMode().equals("healitem")) {
                drawWith = healingColor;
            }

            if (toDraw.getDescMode().equals("potion")) {
                drawWith = potionColor;
            }
            String itemName = toDraw.getName();
            putText(itemName, startX, startY + ii, drawWith);
        }
    }

    /**
     * In the selector layer, incrementally place a text starting at an X,Y coordinate
     *
     * @param text   a string to be displayed
     * @param xStart starting X coordinate
     * @param yStart Y coordinate
     */
    private void putText(String text, int xStart, int yStart, Color toDrawWith) {
        putText(text, xStart, yStart, toDrawWith, 12);
    }

    private void putText(String text, int xStart, int yStart, Color toDrawWith, int maxLength) {
        int iter = 0;
        for (int iii = 0; iii < text.length(); iii++) {
            SpecialText toPut = new SpecialText(String.valueOf(text.charAt(iii)), toDrawWith);
            org.editLayer(toPut, infoLayer.getName(), yStart, xStart + iii);
            iter++;
        }
        for (int iif = iter; iif < maxLength; iif++) { //Fixes string by concatenating blank spaces until maxLength
            org.editLayer(" ", infoLayer.getName(), yStart, xStart + iif);
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

    private int calcIndex() {
        return cursorY - 2 + ((page - 1) * 16);
    }


    void fireKeyEvent(KeyEvent event) {
        if (!inCmdLine) {
            int key = event.getKeyCode();
            if (key == KeyEvent.VK_UP) {
                keyPressed('↑');
            }
            if (key == KeyEvent.VK_DOWN) {
                keyPressed('↓');
            }
            if (key == KeyEvent.VK_LEFT) {
                keyPressed('←');
            }
            if (key == KeyEvent.VK_RIGHT) {
                keyPressed('→');
            }
            if (Character.isDigit(event.getKeyChar())) {
                keyPressed(event.getKeyChar());
            }
            if (key == 'A' || key == KeyEvent.VK_ENTER) {
                pressedA = true;
            }
            if (key == 'S') {
                pressedS = true;
            }
            if (key == 'D') {
                pressedD = true;
            }
            if (key == '\\') {
                System.out.println(getY());
            }
            if (key == KeyEvent.VK_ESCAPE || event.getKeyChar() == 'w') {
                exitAllMenus();
            }
        }
    }

    private class MenuTimer extends TimerTask {
        public void run() {
            if (menuID == EXIT) {
                org.removeLayer("selector");
                org.removeLayer("invInfo");
                System.out.println("Exiting menu through top menu....");
                org.getLayers().stream().filter(lay -> lay.name.contains("playerLayer")).forEach(lay -> org.setLayerImportance(lay.getName(), true));
                exitAllMenus();
                cancel();
            } else {
                newUpdateSelector(menuID);
            }
        }
    }
}