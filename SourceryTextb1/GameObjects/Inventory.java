package SourceryTextb1.GameObjects;

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Window;
import SourceryTextb1.art;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A class to select something in your inventory
 * Created by riley on 12-Jun-2016.
 */
class Inventory {
    private HashMap<String, Integer> inventory;
    private ArrayList<Item> spells = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> equip = new ArrayList<>();
    private ArrayList<Item> equipped = new ArrayList<>();
    /*"equip" and "equipped" are different. "equip" is possible equipment that you have stockpiled up.
        In other words, all the gloves, staffs, and armor that you picked up along your journey.
      "equipped" contains the armor you have equipped, and also the spells you have equipped too.
        the first two elements in "equipped" are the two spells, and the third and fourth elements are weapon and armor respectively
    */

    private Player player;
    private ImageOrg org;
    private Layer invBkgdLayer;
    private Layer itemsLayer;
    private Layer selectedSpellsLayer;
    private art arty = new art();
    private String layerName = "WindowConfig";
    private int selectX = 1;
    private int selectY = 1;
    private int newSelectY = 2;
    private int scrollTimer = 0;

    private final int TOP = 1;
    private final int SPELLS = 2;
    private final int ITEMS = 3;
    private final int EQUIP = 4;
    private final int EXIT = 5;
    private final int QUIT = 6;

    public Inventory(ImageOrg orgo, Player p) {
        org = orgo;
        player = p;
        inventory = new HashMap<>(); // I have no idea why this can't go at a class-level with the declaration.
        selectedSpellsLayer = new Layer(new String[22][47], "selectedSpells", false);

        inventory.put("WoodStaff", 0);
        inventory.put("Book", 1);
        inventory.put("Flame", 1);
        inventory.put("Spark", 0);
        inventory.put("Wanderer", 1);
        inventory.put("SmallHealth", 1);
        inventory.put("HugeHealth", 1);

        spells.add(new Item("Spark","Arcane Spell;\nFires a spark of energy.\n\n\"All great fires start\n with small sparks\"", "Spark", player));
        spells.get(0).setDmgRngCost (3, 8, 2);
        spells.get(0).setAnim("+","x");
        spells.get(0).setDescMode("arcane");
        spells.add(new Item("Fireball","Fire Spell;\nUse your imagination", "FrBll", player));
        spells.get(1).setDmgRngCost (5, 6, 3);
        spells.get(1).setAnim("6","9");
        spells.get(1).setDescMode("fire");
        spells.add(new Item("Frostbite","Ice Spell;\nFreezes an enemy right\n in front of you.", "FrstB", player));
        spells.get(2).setDmgRngCost (4, 2, 5);
        spells.get(2).setAnim("X","x");
        spells.get(2).setDescMode("ice");
        spells.add(new Item("Shadow Knife","Dark Spell;\nThrows a blade made of\n forbidden magic.\n\nThe dart seems to have a \n soul that refuses to die," +
                "\n and thus travels very far.", "ShKnf", player));
        spells.get(3).setDmgRngCost (2, 15, 4);
        spells.get(3).setAnim("/","\\");
        spells.get(3).setDescMode("dark");

        for (int spam = 0; spam < 20; spam++){
            items.add(new Item("Postcard","Has not utility, but still\n costs $10\n\nDirectly sourced from\n The Mines of Mementos", player));
        }

        for (int spam = 0; spam < 35; spam++){
            items.add(new Item("Key Chain","A new chain to put your\n other pointless gifts on!\n\nLinking key chains together\n may generate lackluster" +
                    "\n results\n\nDirectly Sourced from\n The Mines of Mementos", player));
        }

        equip.add(new Item("Pyro Glove","A glove that's on fire!\n\nPyromancers are quite the\n adventurous people, and so\n these gloves became very\n commonplace" +
                "\n\n+2 Fire Spell Damage", player));
        equip.add(new Item("Broken Staff","A staff crafted by a\n dirt-poor student of\n The Magic Academy.\n\nMade of spare wood\n" +
                " and frayed ropes, it's\n no surprise that it\n already snapped in two\n\n+1 (All) Spell Damage", player));

        //putPrimary(arty.oldBook);  // Wouldn't work, not sure why
    }

    /**
     * Add another of an item in the inventory
     *
     * @param itemName which one?
     */
    public void addItem(String itemName) {
        if (inventory.containsKey(itemName)) {
            int n = inventory.get(itemName);
            inventory.put(itemName, n + 1);
        } else {
            inventory.put(itemName, 1);
        }
    }

    /**
     * Make there be one less item in the player's inventory
     *
     * @param itemName which to have less of
     */
    public void subtractItem(String itemName) {
        if (inventory.containsKey(itemName)) {
            int n = inventory.get(itemName);
            inventory.put(itemName, n - 1);
        } else {
            inventory.put(itemName, 0);
        }
    }

    /**
     * How many of <X> do I have in my inventory?  Find out!
     *
     * @param itemName which item
     * @return how many of itemName s there are
     */
    public int getItem(String itemName) {
        if (inventory.containsKey(itemName)) {
            return inventory.get(itemName);
        } else {
            inventory.put(itemName, 0);
            return 0;
        }
    }

    /**
     * Do I have <X> in my inventory?  Find out!
     *
     * @param itemName which item
     * @return whether there are more than 0
     */
    public boolean hasItem(String itemName) {
        return getItem(itemName) > 0;
    }


    /**
     * @return the item name of what is selected
     */
    private String getSelectedName() {
        return getSelected(1);
    }

    /**
     * @return A shortish description of what is selected
     */
    private String getSelectedDescription() {
        return getSelected(2);
    }

//    For reference.  This actually doesn't do anything here
//
//                    ///.........  I N V E N T O R Y  ..........\\\
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .........................................    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .........................................    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .........................................    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    .      ..      ..      ..      ..      ..    .
//                    ..............................................
//                    .      ..      ..      ...|..EQUIPPED SPELLS..
//                    .      ..      ..      ...|....1st......2nd...
//                    .      ..      ..      ...|...      ..      ..
//                    ..........................|...      ..      ..
//                    ..........................|...      ..      ..
//                    \\\........................................///


    /**
     * Press a key?  Call this to do stuff!
     *
     * @param c which character you have pressed on the board
     */
    void keyPressed(char c) {
        System.out.println(c);
        switch (c) {
            case '©': // Up
                if (selectY > 1)
                    selectY--;
                newSelectY --;
                scrollTimer = 0;
                break;
            case '®': // Down
                if (selectY < 8)
                    selectY++;
                newSelectY ++;
                scrollTimer = 0;
                break;
            case '1': // Set primary
                putPrimary(getSelectedName());
                player.setPrimarySpell(getSelectedName());
                break;
            case '2': // Set secondary
                putSecondary(getSelectedName());
                player.setSecondarySpell(getSelectedName());
                break;
            default:
                break;
        }
    }

    /**
     * Bring up the inventory for the player to do stuff with.
     */
    public void show() {
        player.frozen = true;
        invBkgdLayer = new Layer(art.strToArray(new art().inventoryBkgd), layerName, 0, 0, false, true);
        org.addLayer(invBkgdLayer); // Background grid
        itemsLayer = populateItems();
        org.addLayer(itemsLayer);
        org.addLayer(selectedSpellsLayer);
        Layer selector = new Layer(new String[22][46], "selector", false, false);
        org.addLayer(selector); // Foreground stuff

        Window window = org.getWindow();
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume) {
            try {
                updateSelector();
                org.compileImage();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
        org.removeLayer("selector");
        org.removeLayer(selectedSpellsLayer.name);
        org.removeLayer("invItems");
        org.removeLayer(layerName);
        window.txtArea.removeKeyListener(keyListener);
        player.frozen = false;
    }

    //BECAUSE SCOPE
    private int menuID = 0;
    public boolean pressedA = false;
    public boolean pressedS = false;
    public boolean pressedD = false;

    private Layer topMenuLayer = new Layer(art.strToArray(new art().topMenu), "top", 1, 27, false, true);
    private Layer quitMenuLayer = new Layer(art.strToArray(new art().quitMenu), "quit", 1, 27, false, true);
    private Layer itemsMenuLayer = new Layer(art.strToArray(new art().itemsMenu), "items", 1, 0, false, true);
    private Layer spellsMenuLayer = new Layer(art.strToArray(new art().spellsMenu), "spells", 1, 0, false, true);
    private Layer equipMenuLayer = new Layer(art.strToArray(new art().equipMenu), "equip", 1, 0, false, true);
    private Layer selectorLayer = new Layer(new String[22][46], "selector", 0, 0, false, false);

    public void newShow() {
        player.frozen = true;
        menuID = TOP;
        newSelectY = 2;

        org.addLayer(topMenuLayer);
        org.addLayer(selectorLayer);

        Window window = org.getWindow();
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (menuID != EXIT) {
            try {
                newUpdateSelector(menuID);
                org.compileImage();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }

        org.removeLayer("selector");
        org.removeLayer("top");
        window.txtArea.removeKeyListener(keyListener);
        player.frozen = false;
    }

    private Layer populateItems() {
        Layer itemsLayer = new Layer(new String[22][47], "invItems", false, false);
        if (hasItem("Book")) {
            putItem(itemsLayer, "Book", 1);
        }
        if (hasItem("Spark")) {
            putItem(itemsLayer, "Spark", 2);
        }
        if (hasItem("Flame")) {
            putItem(itemsLayer, "Flame", 3);
        }
        if (hasItem("Wanderer")) {
            putItem(itemsLayer, "Wanderer", 4);
        }
        if (hasItem("SmallHealth")) {
            putItem(itemsLayer, "Small Health", 5);
        }
        if (hasItem("HugeHealth")) {
            putItem(itemsLayer, "Huge Health", 6);
        }
        return itemsLayer;
    }

    private void putItem(Layer lay, String name, int indexY) {
        int yCoord = 2 * indexY;
        int xCoord = 4;
        char[] arrThing = name.toCharArray(); // Add name
        for (int i = 0; i < arrThing.length; i++) {
            lay.setStr(yCoord, i + xCoord, String.valueOf(arrThing[i]));
        }
        xCoord = 15;
        String desrciption = getInfo(2, indexY); // Add description
        arrThing = desrciption.toCharArray();
        for (int i = 0; i < arrThing.length; i++) {
            lay.setStr(yCoord, i + xCoord, String.valueOf(arrThing[i]));
        }
    }

    private void updateSelector() {
        System.out.println(scrollTimer);
        int indexY = 2 * selectY;
        int indexX = 2;
        System.out.println("X: " + selectX + "     Y:" + selectY);
        org.clearLayer("selector");
        org.editLayer("@", "selector", indexY, indexX);

        //Scroll description sideways
        int xCoord = 15;
        int yCoord = indexY;
        String desrciption = getInfo(2, selectY); // Add description
        char[] arrThing = (" "+desrciption).toCharArray();
        scrollTimer ++;
        if (scrollTimer > arrThing.length){
            scrollTimer = 0;
        }
        Layer lay = org.getLayer(org.getPosLayer("invItems"));
        for (int i = 0; i < arrThing.length; i++) {
            int x = i + xCoord - scrollTimer;
            if (x >= 15) {
                lay.setStr(yCoord, x, String.valueOf(arrThing[i]));
            }
        }

        org.compileImage();
    }

    int indexX = 0;
    private void newUpdateSelector(int menuType) {
        org.clearLayer("selector");

        switch(menuType) {
            case TOP:
                operateTopMenu();
                indexX = 28;
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
        }

        pressedA = false;
        pressedS = false;
        pressedD = false;

        int indexY = newSelectY;
        org.editLayer(">", "selector", indexY, indexX);

        org.compileImage();
    }

    private void operateTopMenu(){
        if (newSelectY <= 1) {
            newSelectY = 6;
        }
        if (newSelectY >= 7) {
            newSelectY = 2;
        }
        if (pressedA) {
            switch (newSelectY){
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
                    org.removeLayer("top");
                    org.removeLayer("selector");
                    menuID = EXIT;
                    break;
            }
        }
    }

    private int page = 1;

    private void jumpToNewMenu(Layer goTo, int newID, String from){
        org.removeLayer(from);
        org.removeLayer("selector");
        org.addLayer(goTo);
        org.addLayer(selectorLayer);
        menuID = newID;
    }

    private void operateSpellsMenu(){
        if (newSelectY <= 2) {
            newSelectY = 21;
        }
        if (newSelectY >= 22) {
            newSelectY = 3;
        }

        genericItemListing(spells);

        char[] chars = player.spell1.getName().toCharArray();
        for (int ii = 0; ii < chars.length ; ii ++){
            selectorLayer.setStr(16, 15 + ii, String.valueOf(chars[ii]));
        }
        chars = player.spell2.getName().toCharArray();
        for (int ii = 0; ii < chars.length ; ii ++){
            selectorLayer.setStr(17, 15 + ii, String.valueOf(chars[ii]));
        }

        indexX = 31;
        if (pressedA) {
            if (newSelectY == 21){
                jumpToNewMenu(topMenuLayer, TOP, "spells");
                indexX = 28;
                newSelectY = 2;
            }
            if (newSelectY == 20){
                page++;
            }
        }
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

    private void operateItemsMenu(){
        if (newSelectY <= 2) {
            newSelectY = 21;
        }
        if (newSelectY >= 22) {
            newSelectY = 3;
        }

        genericItemListing(items);

        indexX = 31;
        if (pressedA) {
            if (newSelectY == 21){
                jumpToNewMenu(topMenuLayer, TOP, "items");
                indexX = 28;
                newSelectY = 2;
            }
            if (newSelectY == 20){
                page++;
            }
        }
    }

    private void operateEquipMenu(){
        if (newSelectY <= 2) {
            newSelectY = 21;
        }
        if (newSelectY >= 22) {
            newSelectY = 3;
        }

        genericItemListing(equip);

        indexX = 31;
        if (pressedA) {
            if (newSelectY == 21){
                jumpToNewMenu(topMenuLayer, TOP, "equip");
                indexX = 28;
                newSelectY = 2;
            }
            if (newSelectY == 20){
                page++;
            }
        }
    }

    private void operateQuitMenu(){
        if (newSelectY <= 3) {
            newSelectY = 5;
        }
        if (newSelectY >= 6) {
            newSelectY = 4;
        }

        indexX = 28;
        if (pressedA) {
            if (newSelectY == 5){
                jumpToNewMenu(topMenuLayer, TOP, "quit");
                indexX = 28;
                newSelectY = 2;
            }
            if (newSelectY == 4){
                System.exit(0);
            }
        }
    }

    private void genericItemListing(ArrayList<Item> items){
        double pageReq = Math.ceil((double)items.size() / 16);

        if (pageReq == 0){
            pageReq = 1;
        }

        if (page > pageReq){
            page = 1;
        }

        org.editLayer(String.valueOf((int)pageReq), "selector", 2, 41);
        org.editLayer(String.valueOf(page), "selector", 2, 39);

        fillItemNames(items, 33, 3, page);

        int index = newSelectY - 3 + ((page - 1) * 16);
        if (index < items.size() && newSelectY < 19){
            fillInfoText(items.get(index).getDesc(), 1, 1);
        }
    }

    private void fillInfoText(String text, int startX, int startY){
        int line = 1;
        int newLineAdjust = 0;
        for (int ii = 0; ii < text.length(); ii++){
            if (text.charAt(ii) == '\n'){
                line++;
                newLineAdjust = ii + 1;
            } else {
                selectorLayer.setStr(startY + line, startX + ii - newLineAdjust, String.valueOf(text.charAt(ii)));
            }
        }
    }

    private void fillItemNames(ArrayList<Item> items, int startX, int startY, int page) {
        int pageOffset = (page - 1) * 16;
        int pageSize = items.size() - pageOffset;
        if (pageSize > 16) { pageSize = 16; }

        for (int ii = 0; ii < pageSize; ii++) {
            String itemName = items.get(ii + pageOffset).getName();
            for (int iii = 0; iii < itemName.length(); iii++) {
                selectorLayer.setStr(startY + ii, startX + iii, String.valueOf(itemName.charAt(iii)));
            }
        }
    }

    private String getSelected(int what){
        return getInfo(what, selectY);
    }
    /**
     * Fetch-all method to get random stuff from what's selected currently.  Consider using the more intuitively
     * named getSelectedArt(), getSelectedName(), or getSelectedDescription().
     *
     * @param what a magical integer that specifies which kind of thing to fetch
     * @return a String thing corresponding to what you asked for.
     */
    private String getInfo(int what, int yIndex) {
        if (yIndex == 1 && hasItem("Book")) {
            if (what == 1) {
                return "Book";
            } else if (what == 2) {
                return "This is an old book you found in your pocket.  You can't read it, so it's probably worth more as a weapon.";
            }
            return arty.oldBook;
        } else if (yIndex == 1 && hasItem("Spark")) {
            if (what == 1) {
                return "Spark";
            } else if (what == 2) {
                return "A small spell you found after killing a pot of petunias";
            }
            return arty.spark;
        } else if (yIndex == 3 && hasItem("Flame")) {
            if (what == 1) {
                return "Flame";
            } else if (what == 2) {
                return "A little spell that helps along the spontaneous reaction of an enemy with oxygen to form CO2 and H2O.";
            }
            return arty.flame;
        } else if (yIndex == 4 && hasItem("Wanderer")) {
            if (what == 1) {
                return "Wanderer";
            } else if (what == 2) {
                return "A respectable spell that wanders around and inflicts decent damage.  Not aimable, may hit you.";
            }
            return arty.wanderer;
        } else if (yIndex == 5 && hasItem("SmallHealth")) {
            if (what == 1) {
                return "SmallHealth";
            } else if (what == 2) {
                return "You can regenerate a little health with this.";
            }
            return arty.smallHealth;
        } else if (yIndex == 6 && hasItem("HugeHealth")) {
            if (what == 1) {
                return "HugeHealth";
            } else if (what == 2) {
                return "A huge boost to your health at a huge mana cost";
            }
            return arty.hugeHealth;
        } else {
            if (what == 1) {
                return "None";
            } else if (what == 2) {
                return "";
            }
            return arty.emptyItem;
        }
    }


    /**
     * Put a new item art in the Primary Spell place
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
     * Put a new item art in the Secondary Spell place
     *
     * @param thing a String (not String[][], this does that!).
     */
    private void putSecondary(String thing) {
        int y = 19;
        int x = 38;
        char[] arrThing = thing.toCharArray();
        for (int i = 0; i < arrThing.length; i++) {
            org.editLayer(String.valueOf(arrThing[i]), selectedSpellsLayer.name, y, i + x);
        }
    }


    /// OLD INVENTORY


    /*
    /**
     * Bring up the inventory for the player to do stuff with.
     *
    public void show() {
        player.frozen = true;
        invBkgdLayer = new Layer(art.strToArray(new art().inventoryBkgd), layerName, false, true);
        org.addLayer(invBkgdLayer); // Background grid
        itemsLayer = populateItems();
        org.addLayer(itemsLayer);
        org.addLayer(selectedSpellsLayer);
        Layer selector = new Layer(new String[22][46], "selector", false, false);
        org.addLayer(selector); // Foreground stuff

        Window window = org.getWindow();
        Navigator keyListener = new Navigator(this);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                updateSelector();
                org.compileImage();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
        org.removeLayer("selector");
        org.removeLayer(selectedSpellsLayer.name);
        org.removeLayer("invItems");
        org.removeLayer(layerName);
        window.txtArea.removeKeyListener(keyListener);
        player.frozen = false;
    }

    private Layer populateItems(){
        Layer itemsLayer = new Layer(new String[22][47], "invItems", false, false);
        if (hasItem("Book")){
            putItem(itemsLayer, arty.oldBook, 1, 1);
        }
        if (hasItem("Spark")){
            putItem(itemsLayer, arty.spark, 2, 1);
        }
        if (hasItem("Flame")){
            putItem(itemsLayer, arty.flame, 3, 1);
        }
        if (hasItem("Wanderer")){
            putItem(itemsLayer, arty.wanderer, 4, 1);
        }
        if (hasItem("SmallHealth")){
            putItem(itemsLayer, arty.smallHealth, 5, 1);
        }
        if (hasItem("HugeHealth")){
            putItem(itemsLayer, arty.hugeHealth, 1, 2);
        }
        return itemsLayer;
    }

    private void updateSelector(){
        int indexY = 4*(selectY-1) + 1;
        int indexX = 8*(selectX-1);
        System.out.println("X: " + selectX + "     Y:" + selectY);
        org.clearLayer("selector");
        org.editLayer("|", "selector", indexY, indexX);
        org.editLayer("|", "selector", indexY+1, indexX);
        org.editLayer("|", "selector", indexY+2, indexX);
        org.editLayer("|", "selector", indexY, indexX+7);
        org.editLayer("|", "selector", indexY+1, indexX+7);
        org.editLayer("|", "selector", indexY+2, indexX+7);
        String discription = getSelectedDescription(); // TODO Put item descriptions somewhere
        System.out.println(discription);
        org.compileImage();
    }

    /**
     * @return the art (String, not String[][]) of the item currently selected
     *
    private String getSelectedArt(){
        return getSelected(3);
    }

    /** Fetch-all method to get random stuff from what's selected currently.  Consider using the more intuitively
     * named getSelectedArt(), getSelectedName(), or getSelectedDescription().
     * @param what a magical integer that specifies which kind of thing to fetch
     * @return a String thing corresponding to what you asked for.
     *
    private String getSelected(int what) {
        if (selectX == 1 && selectY == 1 && hasItem("Book")) {
            if (what == 1) {
                return "Book";
            }else if (what == 2){
                return "This is an old book you found in your pocket.  You can't read it, so it's probably worth more as a weapon.";
            }
            return arty.oldBook;
        } else if (selectX == 2 && selectY == 1 && hasItem("Spark")) {
            if (what == 1) {
                return "Spark";
            }else if (what == 2){
                return "A small spell you found after killing a pot of petunias";
            }
            return arty.spark;
        } else if (selectX == 3 && selectY == 1 && hasItem("Flame")) {
            if (what == 1) {
                return "Flame";
            }else if (what == 2){
                return "A little spell that helps along the spontaneous reaction of an enemy with oxygen to form CO2 and H2O.";
            }
            return arty.flame;
        } else if (selectX == 4 && selectY == 1 && hasItem("Wanderer")) {
            if (what == 1) {
                return "Wanderer";
            }else if (what == 2){
                return "A respectable spell that wanders around and inflicts decent damage.  Not aimable, may hit you.";
            }
            return arty.wanderer;
        } else if (selectX == 5 && selectY == 1 && hasItem("SmallHealth")) {
            if (what == 1) {
                return "SmallHealth";
            }else if (what == 2){
                return "You can regenerate a little health with this.";
            }
            return arty.smallHealth;
        } else if (selectX == 1 && selectY == 2 && hasItem("HugeHealth")) {
            if (what == 1) {
                return "HugeHealth";
            }else if (what == 2){
                return "A huge boost to your health at a huge mana cost";
            }
            return arty.hugeHealth;
        } else {
            if (what == 1) {
                return "None";
            }else if (what == 2){
                return "";
            }
            return arty.emptyItem;
        }
    }

    private void putItem (Layer lay, String thing, int indexX, int indexY){
        indexY = 4*(indexY-1) + 1;
        indexX = 8*(indexX-1) + 1;
        String[][] arrThing = art.strToArray(thing);
        System.out.println(Arrays.deepToString(arrThing));
        for (int i = 0; i<arrThing.length; i++){
            for (int j = 0; j<arrThing[0].length; j++){
                lay.setStr(i+indexY, j+indexX, arrThing[i][j]);
            }
        }
    }

    /**
     * Put a new item art in the Primary Spell place
     * @param thing a String (not String[][], this does that!).
     *
    private void putPrimary (String thing){
        int y = 19;
        int x = 30;
        String[][] arrThing = art.strToArray(thing);
        for (int i = 0; i<arrThing.length; i++){
            for (int j = 0; j<arrThing[0].length; j++){
                org.editLayer(arrThing[i][j], selectedSpellsLayer.name, i+y, j+x);
            }
        }
    }
    /**
     * Put a new item art in the Secondary Spell place
     * @param thing a String (not String[][], this does that!).
     *
    private void putSecondary (String thing){
        int y = 19;
        int x = 38;
        String[][] arrThing = art.strToArray(thing);
        for (int i = 0; i<arrThing.length; i++){
            for (int j = 0; j<arrThing[0].length; j++){
                org.editLayer(arrThing[i][j], selectedSpellsLayer.name, i+y, j+x);
            }
        }
    }*/

}


class Navigator extends KeyAdapter {
    private Inventory inv;
    boolean resume = false;

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
        if (key == '1') {
            inv.keyPressed('1');
        }
        if (key == '2') {
            inv.keyPressed('2');
        }
        if (key == 'A') {
            inv.pressedA = true;
        }
        if (key == 'S') {
            inv.pressedS = true;
        }
        if (key == 'D') {
            inv.pressedD = true;
        }
        if (key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER || event.getKeyChar() == 'w') {
            resume = true;
        }
    }
}

