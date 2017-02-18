/*
 * Copyleft 2016 Riley.
 *
 * Licensed under the Epoch Incense, Version 2.0; you may not use this
 * file except in compliance with the incense.  You may obtain a copy
 * of the incense at
 *
 *      http://www.epoch.org/incenses/INCENSE-2.0
 *
 * Software distributed under the incense is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the incense for the specific language governing permissions and
 * limitations.
 */
package SourceryText.GameObjects;

import SourceryText.*;
import SourceryText.GameSettings.KeyMap;
import SourceryText.Network.NetworkServerWorker;
import SourceryText.Rooms.Room;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * Player-controlled protagonist
 *
 * @author Riley
 */
public class Player extends Mortal implements java.io.Serializable {
    private GameInstance gameInstance; // Only used for saving.
    public String roomName = ""; // important for saving implementation.
    private int zoneNumber = 1; // Needed for saving
    private String username = System.getProperty("user.name");
    private Inventory inv;
    private HUD hud;
    private KeyMap keymap;
    private NetworkServerWorker networkServer; // Only used in multiplayer
    private boolean hasLocalWindow;
    public java.util.List<Room.FlavorText> messageQueue = new ArrayList<>();

    TextBox textBox;

    private boolean shouldNewInv = false;
    private String lastPainMessage = "None";
    public boolean dead = false;

    //Convenience variables
    private final int UP = 0;
    private final int LEFT = 1;
    private final int DOWN = 2;
    private final int RIGHT = 3;

    // CONSTANT STATS
    int baseMaxHP = 20;
    int maxMana = 20;
    private int manaRegenClock = 0;
    int manaWait = 0;
    private boolean manaHalfStep;
    int defense = 0;

    private int burnoutRecoveryClock = 0;
    private int burnoutWaitTime = 250;

    //Note for the future: Damage can't be reduced below 1 damage. Swords and explosions don't heal people.

    // CHANGABLE STATS
    int mana = maxMana;
    private float sprintVelocity = 1;
    private float sprintAcceleration = 0.9f;

    boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed = false;
    boolean swimming = false;
    int waterEntry = 0;
    private int movecount = 0;
    public boolean ludicrousSpeed = false;
    private int orientation = UP;
    private boolean orientationLocked = false;
    private boolean turnTypeHeld = false;
    private boolean aimDisplayLocked = false;
    private boolean aimDisplayedLastTick;
    private int lastOrientation = UP;
    private String aimDispName = "aimDisp";
    public boolean frozen = false;
    public boolean isGhost = false;

    int allSpellBoost = 0;
    int arcSpellBoost = 0;
    int fireSpellBoost = 0;
    int iceSpellBoost = 0;
    int darkSpellBoost = 0;
    private int healBoost, durBoost, rangeBoost, armorHealthBoost = 0;
    //NO MORE STATS

    Item spell1 = new Item("None", "");
    Item spell2 = new Item("None", "");
    Item weapon = new Item("None", "");
    Item armor = new Item("None", "");

    // Fabulous mode variables
    public boolean fabulousMode = false;
    public int fabulousLocIndex = 1;
    public int fabulousColorIndex = 0;
    int screenRedness = 0;
    int screenYellowness = 0;
    public Color foregroundColor = Color.WHITE;

    private int spellCasts = 0;
    private int snowflakes = 0; // Note: we'll probably want to improve the currency system later, like last few methods for this class
    public boolean braindead = false;


    /**
     * Initialize a whole lotta variables.
     *
     * @param theOrg the ImageOrg(anizer)
     */
    public Player(GameInstance gameInstance, ImageOrg theOrg, int playerNumber, KeyMap keymap) {
        this.gameInstance = gameInstance;
        hasLocalWindow = (playerNumber == 0);
        setHealth(baseMaxHP);
        makeGoodGuy(); // Set good-guy-ness to true.
        super.maxHealth = baseMaxHP + armorHealthBoost;
        super.strClass = "Player";
        System.out.println("\nNEW PLAYER\n");
        org = theOrg;
        username += playerNumber;

        layerName = "playerLayer-" + username;
        aimDispName += username;

        inv = new Inventory(org, this);

        hud = new HUD(this);
        hud.setOrg(org);
        setupTimer(20);
        //resumeFromSave();
        this.keymap = keymap;
    }

    /**
     * Attempt to cancel the sending of the display data over network. (stops an earlier instance of calling testSendOverNetwork())
     */
    void cancelSendOverNetwork() {
        networkServer.disconnect();
    }

    /**
     * Set things up that don't get carried between saves, ex timers
     */
    public void resumeFromSave() {
        //org.resetClock();
        hud.setupTimer();
    }

    public String getUsername() {
        return username;
    }

    public boolean getHasLocalWindow() {
        return hasLocalWindow;
    }

    public void setupForNewRoom() {
        graphicUpdate();
        /*
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
         */
    }

    /**
     * Change the Player's perception of which room it is in, and do various cleanup from earlier rooms.
     *
     * @param newRoom a Room that Player should consider itself in
     */
    public void setRoom(Room newRoom) {
        // Clean up from old room
        org.removeLayer(layerName);
        org.removeLayer(aimDispName);
        //org.setLayerImportance("HUD_of_" + username, true);
        //Set new room
        room = newRoom;
        //Update everybody's orgs
        org = room.org;
        hud.setOrg(org);
        // Add the player layers to this room
        addPlayerLayers();
    }

    public void restartTimer() {
        setupTimer(20);
    }

    /**
     * Add layers for the HUD and player into this room.
     */
    private void addPlayerLayers() {
        // Add hud layer
        System.out.println("Adding HUD for " + username);
        String hudLayerName = "HUD_OF_" + username;
        Layer HUDd = new Layer(new String[1][70], hudLayerName, 0, 0, false, true, true);
        HUDd.setOwningPlayerUsername(username);
        org.addLayer(HUDd);

        hud.setLayerName(HUDd.name);

        // Add player layer
        Layer playerLayer = new Layer(new String[3][3], layerName);
        org.setLayerImportance(layerName, true);
        org.addLayer(playerLayer);

        // Add aim display layer
        Layer aimDispLayer = new Layer(new String[1][1], aimDispName);
        aimDispLayer.setOwningPlayerUsername(username);
        org.addLayer(aimDispLayer);
    }

    /**
     * Set x and y coordinates of the player directly.
     *
     * @param newX the new x coord
     * @param newY the new y coord
     */
    @Override
    public void goTo(int newX, int newY) {
        x = newX;
        y = newY;
        graphicUpdate();
    }

    /**
     * Perform a general update of the player, calling all methods that ought to be called each tick.
     */
    @Override
    public void update() {
        updateFabulousness();
        if (frozen || dead || room == null) { // Should be first, so other things don't try to happen first
            //System.out.println("Not Updating " + username);
            try {
                if (dead) {
                    onDeath();
                } else if (room != null) {
                    org.editLayer(" ", layerName, y, x);
                }
            } catch (IndexOutOfBoundsException | NullPointerException ignored) {
            }
        } else {
            if (shouldNewInv) {
                System.out.println("Opening inventory");
                inv.newShow();
                shouldNewInv = false;
            }

            boolean blocked = false;
            for (GameObject object: room.objs) {
                if(object.getClass() == MagicSmoke.class) {
                    if(((MagicSmoke) object).isBlockManaRegen( x, y)) {
                        blocked = true;
                        break;
                    }
                }
            }
            if(!blocked) {
                manaRegenClock += getTime();
                if (manaWait > 0) {
                    manaWait -= getTime();
                    manaRegenClock = 0;
                } else if (manaRegenClock >= (500 / maxMana) && mana < maxMana) {
                    mana++;
                    manaRegenClock = 0;
                }
            }

            burnoutRecoveryClock += getTime();
            if (burnoutRecoveryClock > burnoutWaitTime){
                spell1.decrementBurnout();
                if (spell1.spellBurnout > 0)
                    System.out.printf("Player Spell 1 Burnout: %1$f\n", spell1.spellBurnout);
                spell2.decrementBurnout();
                burnoutRecoveryClock -= burnoutWaitTime;
            }

            resetTime(); // time is used for mana regen and spell burnout.
            graphicUpdate();
            aimDispUpdate();
            doMovement();
        }
    }

    /**
     * General method for handling player movement for this tick.
     */
    private void doMovement() {
        int movespeed = 5;
        boolean spendingManaToSprint = false;
        if (swimming) movespeed = 7;
        if (isGhost) movespeed = 1;
        if (ludicrousSpeed) movespeed = 0;
        if (spacePressed && mana > 0) {
            if (swimming) {
                movespeed -= 3;
            } else {
                movespeed -= (int) sprintVelocity;
            }
            spendingManaToSprint = true;
        } else {
            sprintVelocity = 1;
        }
        if (slowedTimer > 0) {
            movespeed *= 2;
        }
        if (movecount == 0) {
            if (waterEntry == 0) {
                if (upPressed) {
                    move(UP);
                }
                if (downPressed) {
                    move(DOWN);
                }
                if (leftPressed) {
                    move(LEFT);
                }
                if (rightPressed) {
                    move(RIGHT);
                }
                if ((upPressed || leftPressed || rightPressed || downPressed) && spendingManaToSprint) {
                    if (manaHalfStep) {
                        spendMana(1);
                        if (sprintVelocity < 4) {
                            sprintVelocity += sprintAcceleration;
                        }
                        manaHalfStep = false;
                    } else {
                        manaHalfStep = true;
                    }
                }
            } else {
                waterEntry--;
            }
        }
        if (!(upPressed || downPressed || leftPressed || rightPressed) || (movecount >= movespeed)) {
            movecount = 0;
        } else {
            movecount++;
        }
    }

    /**
     * Simply put, it resets the button press booleans so that the player doesn't confusingly drift around without input
     */
    public void resetMovement() {
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
    }

    private void aimDispUpdate() {
        if(aimDisplayedLastTick) {
            switch (lastOrientation) {
                case UP:
                    org.editLayer(" ", layerName, 0, 1);
                    break;
                case DOWN:
                    org.editLayer(" ", layerName, 2, 1);
                    break;
                case LEFT:
                    org.editLayer(" ", layerName, 1, 0);
                    break;
                case RIGHT:
                    org.editLayer(" ", layerName, 1, 2);
                    break;
            }
            aimDisplayedLastTick = false;
        } if(aimDisplayLocked || orientationLocked) {
            SpecialText txt = new SpecialText("+", new Color(255, orientationLocked?0:255, orientationLocked?0:255));
            switch (orientation) {
                case UP:
                    org.editLayer(txt, layerName, 0, 1);
                    break;
                case DOWN:
                    org.editLayer(txt, layerName, 2, 1);
                    break;
                case LEFT:
                    org.editLayer(txt, layerName, 1, 0);
                    break;
                case RIGHT:
                    org.editLayer(txt, layerName, 1, 2);
                    break;
            }
            aimDisplayedLastTick = true;
        }
        lastOrientation = orientation;
    }

    private void reportPos() {
        System.out.println("\nPlayer X: " + x + "\nPlayer Y: " + y + "\nPaused?: " + paused + "\n" + "Spell casts: " + spellCasts);
    }

    /**
     * @return the player's instance of Inventory
     */
    Inventory getInventory() {
        return inv;
    }

    public Item getItem(String itemName, String invSection){
        return inv.getItem(itemName,invSection);
    }

    /**
     * Writes a .sav file (of the serialized Player) to a user-defined directory
     *
     * @return whether the saving was successful
     */
    public boolean saveGame() {
        if (gameInstance == null) {
            System.out.println("Player.gameInstance is null; cannot save");
            room.splashMessage("Saving failed!", "Save Point", this);
            return false;
        }
        org.terminateClock();
        String path;
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(roomName + ".sav"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sourcery Text Saves", "sav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(new Component() {
        });
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
            if (!path.endsWith(".sav")) { // Add .sav to file if user didn't.
                path += ".sav";
            }
            System.out.println("You chose to save the file to: " + path);
        } else {
            org.resetClock();
            room.splashMessage("Saving failed!", "Save Point", this);
            return false;
        }

        try {
            System.out.println("Valid output file specified; attempting serialization of gameInstance...");
            room.setObjsPause(true);
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println("Writing, please wait...");
            out.writeObject(gameInstance);
            System.out.println("Written!");
            out.close();
            fileOut.close();
            System.out.printf("Serialized Player data is saved in " + path);
            room.setObjsPause(false);
            org.resetClock();
            room.splashMessage("Saving successful!", "Save Point", this);
            return true;
        } catch (IOException | ConcurrentModificationException e) {
            e.printStackTrace();
            org.resetClock();
            room.splashMessage("Saving failed!", "Save Point", this);
            return false;
        }
    }


    @Override
    public void onDeath() {
        //room.compactTextBox(lastPainMessage, "An ominous voice from above", false);
        hud.cancelTimer();
        inv.cancelTimer();
        dead = true;
        room.exitCode = "die";
    }

    /**
     * Increment variables relating to fabulous mode (colorful screen after eating some potions) if needed.
     */
    private void updateFabulousness() {
        if (fabulousMode) {
            fabulousLocIndex++;
            if (fabulousLocIndex > 75) {
                fabulousLocIndex = 1;
                fabulousColorIndex++;
                if (fabulousColorIndex >= 7) fabulousColorIndex = 0;
            }
        }
    }

    /**
     * Update the Player symbol and all the graphic things, like screen coloration for healing or hurting effects
     */
    private void graphicUpdate() {
        if (screenRedness > 0 || screenYellowness > 0) {
            if (screenRedness > 0) screenRedness--;
            //This means that higher levels of redness depletes faster
            if (screenRedness > 200) screenRedness--;
            if (screenRedness > 100) screenRedness--;
            if (screenYellowness > 0) screenYellowness--;
            int opposite = 255 - screenRedness;
            int yellowNumber = (int) (opposite * (1 - ((float) screenYellowness / 100)));
            //System.out.printf("Screen yellow factor: %1$d, (%2$d --> %3$d)\n", screenYellowness, opposite, yellowNumber);

            foregroundColor = new Color(255, opposite, yellowNumber);
        }

        if (room != null) {
            if (room.checkForWater(getX(), getY())) {
                if (!swimming) {
                    waterEntry = 2;
                    swimming = true;
                }
            } else {
                swimming = false;
            }
        }

        SpecialText playerIcon;
        if (braindead){
            playerIcon = new SpecialText("@", new Color(44, 59, 38));
        }
        else if (!swimming)
            playerIcon = new SpecialText("@", new Color(150, 255, 100));
        else {
            if (waterEntry == 2)
                playerIcon = new SpecialText("u", new Color(255, 255, 255));
            else if (waterEntry == 1) {
                playerIcon = new SpecialText("Y", new Color(255, 255, 255));
            } else
                playerIcon = new SpecialText("@", new Color(100, 150, 255));
        }
        org.editLayer(playerIcon, layerName, 1, 1);
        Layer iconLayer = org.getLayer(layerName);
        if (iconLayer != null) iconLayer.setPos(x - 1, y - 1);
    }

    /**
     * Used by ImageOrg's buildImage.
     *
     * @return where the camera ought to be
     */
    public int getCamX() {
        int centeredX = getX() - 23;
        if (room != null && room.boundedCamera){
            centeredX = Math.min(centeredX, room.roomWidth - org.getWindow().screenW());
            centeredX = Math.max(centeredX, 0);
        }
        return centeredX;
    }

    /**
     * Used by ImageOrg's buildImage.
     *
     * @return where the camera ought to be
     */
    public int getCamY() {
        int centeredY = getY() - 11;
        if (room != null && room.boundedCamera){
            centeredY = Math.min(centeredY, room.roomHeight - org.getWindow().screenH());
            centeredY = Math.max(centeredY, -1);
        }
        return centeredY;
    }

    /**
     * Attempt to move in the world
     * @param direction a magic int corresponding to up, down, left, or right
     */
    private void move(int direction) {
        if (!paused.get()) {
            try {
                //org.editLayer(" ", layerName, y, x);
                room.removeFromObjHitMesh(x, y);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
            if (slowedTimer > 0)
                slowedTimer--;
            switch (direction) {
                case UP:
                    if (!room.isPlaceSolid(x, y - 1) || isGhost)
                        y--;
                    if (!orientationLocked)
                        orientation = UP;
                    break;
                case DOWN:
                    if (!room.isPlaceSolid(x, y + 1) || isGhost)
                        y++;
                    if (!orientationLocked)
                        orientation = DOWN;
                    break;
                case LEFT:
                    if (!room.isPlaceSolid(x - 1, y) || isGhost)
                        x--;
                    if (!orientationLocked)
                        orientation = LEFT;
                    break;
                case RIGHT:
                    if (!room.isPlaceSolid(x + 1, y) || isGhost)
                        x++;
                    if (!orientationLocked)
                        orientation = RIGHT;
                    break;
                default:
                    System.out.println("Bro, you're using Player.move wrong.");
            }
            room.addToObjHitMesh(x, y);
            graphicUpdate();
        }
    }

    /**
     * Set the Player's weapon or armor, either one.  The new item will fill the player's only armor or weapon slot,
     * according to its type.
     *
     * @param toEquip an Item that should be equipped now
     */
    void equip(Item toEquip) {
        if (toEquip.getEquipType().toLowerCase().equals("weapon")) {
            weapon = toEquip;
            defineStats();
        } else if (toEquip.getEquipType().toLowerCase().equals("armor")) {
            armor = toEquip;
            defineStats();
        } else {
            System.out.println("You can't wear that!");
        }
    }

    //   Where the spell boost variables are used, maybe the corresponding expression should be used instead. --Riley
    void defineStats() {
        defense = armor.getEquipVals()[0] + weapon.getEquipVals()[0];
        armorHealthBoost = armor.getEquipVals()[1] + weapon.getEquipVals()[1];
        allSpellBoost = armor.getEquipVals()[2] + weapon.getEquipVals()[2];
        arcSpellBoost = armor.getEquipVals()[3] + weapon.getEquipVals()[3];
        fireSpellBoost = armor.getEquipVals()[4] + weapon.getEquipVals()[4];
        iceSpellBoost = armor.getEquipVals()[5] + weapon.getEquipVals()[5];
        darkSpellBoost = armor.getEquipVals()[6] + weapon.getEquipVals()[6];

        super.maxHealth = baseMaxHP + armorHealthBoost;

        System.out.println("DEF: " + defense + " HpB: " + armorHealthBoost + " AllB: " + allSpellBoost + "\nArcB: " + arcSpellBoost + " FireB: " + fireSpellBoost +
                " IceB: " + iceSpellBoost + " DarkB: " + darkSpellBoost);
    }

    /**
     * Handler for keypresses, and delegates appropriate actions based off them.  Note that this does not necessarily
     * align with the game clock, or Update() method.
     *
     * @param key a character that was pressed on the leopard
     */
    void keyPressed(int key) {
        if (!frozen) {
            if      (key == keymap.BACK_PRIMARY || keymap.BACK_SECONDARY == key)                            { reportPos(); }
            else if (key == keymap.TURN_TYPE_TOGGLE_PRIMARY || keymap.TURN_TYPE_TOGGLE_SECONDARY == key)    { orientationLocked = !orientationLocked; }
            else if (key == keymap.SPELL1_PRIMARY || keymap.SPELL1_SECONDARY == key)                        { newCastSpell(spell1); }
            else if (key == keymap.SPELL2_PRIMARY || keymap.SPELL2_SECONDARY == key)                        { newCastSpell(spell2); }
            else if (key == keymap.MENU_PRIMARY || keymap.MENU_SECONDARY == key)                            { shouldNewInv = true; }
            else if (key == keymap.INTERACT_PRIMARY || keymap.INTERACT_SECONDARY == key)                    { textBoxQuery(); }
            else if (key == keymap.CLOCKWISE_PRIMARY || keymap.CLOCKWISE_SECONDARY == key)                  { orientation = Math.floorMod(orientation-1, 4); }
            else if (key == keymap.ANTICLOCKWISE_PRIMARY || keymap.ANTICLOCKWISE_SECONDARY == key)          { orientation = Math.floorMod(orientation+1, 4); }
            else if (key == keymap.DISPLAY_DIRECTION_PRIMARY || keymap.DISPLAY_DIRECTION_SECONDARY == key)  { aimDisplayLocked = !aimDisplayLocked; }
            else                                                                                            { System.out.print(key); }
            graphicUpdate();
        }
    }

    /**
     * Call room.queryForText() in a + shape around you - effectively activating any peices of text planted around
     * the level next to you.
     */
    private void textBoxQuery() {
        room.inspectAt(x, y, this);
        room.inspectAt(x+1, y, this);
        room.inspectAt(x-1, y, this);
        room.inspectAt(x, y+1, this);
        room.inspectAt(x, y-1, this);
    }

    private void newCastSpell(Item spell) {
        boolean blocked = false;
        for (GameObject object: room.objs) {
            if(object.getClass() == MagicSmoke.class) {
                if(((MagicSmoke) object).isBlockSpell(spell, x, y)) {
                    blocked = true;
                    break;
                }
            }
        }
        if (!swimming && !blocked) { //Swimming AND casting spells at the same time?!?!?! That's too much, man!
            spellCasts++;
            if (spell.isDmgSpell) {
                int damage = spell.damage + allSpellBoost;
                switch (spell.getDescMode()) {
                    case "arcane":
                        damage += arcSpellBoost;
                        break;
                    case "fire":
                        damage += fireSpellBoost;
                        break;
                    case "ice":
                        damage += iceSpellBoost;
                        break;
                    case "dark":
                        damage += darkSpellBoost;
                        break;
                }
                damage = (int)Math.ceil((float)damage * (1.0f - spell.spellBurnout));
                looseCastDmgSpell(damage, spell);
            } else if (spell.getDescMode().equals("healing")){
                if (mana >= spell.cost) {
                    int amountHealing = (int)Math.ceil((float)spell.healing * (1.0f - spell.spellBurnout));
                    restoreHealth(amountHealing);
                    spendMana(spell);
                }
            }
        }
    }

    private void spendMana(int cost){
        mana -= cost;
        int wait = 2000 - (int) (1750 * ((float) mana / (float) maxMana));
        manaWait = wait;
    }

    private void spendMana(Item spellUsed) {
        spendMana(spellUsed.cost);
        spellUsed.spellBurnout += spellUsed.usageBurnout;
        if (spellUsed.spellBurnout > 0.99f) spellUsed.spellBurnout = 0.99f;
    }

    private void looseCastDmgSpell(int dmg, Item spell) {
        if (mana >= spell.cost) {
            Spell toFire = new Spell(room, x, y, orientation, dmg, spell);
            toFire.setType(spell.getDescMode());
            toFire.setHostility(false);
            toFire.setSplashRadius(spell.splashRadius);
            room.addObject(toFire);
            spendMana(spell);
            //System.out.println("The damage spell fired!");
        }
    }

    public String getPrimarySpell() {
        return spell1.getIcon();
    }

    public Item getWeapon() {
        return weapon;
    }

    public void addItem(Item input) {
        inv.addItem(input);
    }

    /**
     * Take away a random item of a certain type
     * @param itemType what the Item.type should be:
     *                  1: spell
     *                  2: normal item
     *                  3: equipment
     */
    public Item removeRandomItem(String itemType) {
        int type;
        switch (itemType.toLowerCase()){
            case "spell":
                type = 1;
                break;
            case "item":
                type = 2;
                break;
            case "equip":
                type = 3;
                break;
            case "any":
                type = -1;
                break;
            default:
                System.out.println("[Player.removeRandomItem] bad parameter: " + itemType + "; acceptable inputs are" +
                        "spell, item, equip, or any.");
                return null;
        }
        return inv.removeRandomItem(type);
    }

    public void removeItem(String itemName, String itemType){
        inv.removeItem(itemName, itemType);
    }

    String getSecondarySpell() {
        return spell2.getIcon();
    }

    /**
     * Adds a potato to the player's inventory. For debug console.
     */

    void addPotato(int amount) {
        for (int ii = 0; ii < amount; ii++) {
            inv.addItem(new Item("Magic Potato", "A magically enhanced potato\n\nCan be used to either" +
                    "\n permanently increase\n your max health or\n max mana by 5.", "item"));
        }
    }

    /**
     * This is the place where incoming key events go from faraway windows.  This method is called by the NetworkServer.
     * This method runs even when the player is paused or frozen, as the network stops for no-one.
     *
     * @param event a KeyEvent that occurred on the window pertaining to this player.
     */
    public void fireKeyEvent(KeyEvent event) {
        if (event.toString().contains("KEY_PRESSED")) {
            if (event.getKeyCode() == keymap.UP_PRIMARY || event.getKeyCode() == keymap.UP_SECONDARY) {
                upPressed = true;
            } if (event.getKeyCode() == keymap.DOWN_PRIMARY || event.getKeyCode() == keymap.DOWN_SECONDARY) {
                downPressed = true;
            } if (event.getKeyCode() == keymap.LEFT_PRIMARY || event.getKeyCode() == keymap.LEFT_SECONDARY) {
                leftPressed = true;
                if (textBox != null) textBox.receiveInput("change");
            } if (event.getKeyCode() == keymap.RIGHT_PRIMARY || event.getKeyCode() == keymap.RIGHT_SECONDARY) {
                rightPressed = true;
                if (textBox != null) textBox.receiveInput("change");
            } if (event.getKeyCode() == keymap.RUN_PRIMARY || event.getKeyCode() == keymap.RUN_SECONDARY) {
                spacePressed = true;
            } if((event.getKeyCode() == keymap.TURN_TYPE_HOLD_PRIMARY || event.getKeyCode() == keymap.TURN_TYPE_HOLD_SECONDARY) && !turnTypeHeld) {
                orientationLocked = !orientationLocked;
                turnTypeHeld = true;
            } if (event.getKeyCode() == keymap.CONFIRM_PRIMARY || event.getKeyCode() == keymap.CONFIRM_SECONDARY) {
                if (textBox != null) textBox.receiveInput("end");
            } if (event.getKeyCode() == keymap.BACK_PRIMARY || event.getKeyCode() == keymap.BACK_SECONDARY) {
                if (textBox != null) textBox.receiveInput("end");
            } else {
                keyPressed(event.getKeyCode());
            }
            if (room != null) { // Player is sometimes initialized for a bit before being placed into the room
                inv.fireKeyEvent(event, keymap);
                room.fireKeyEvent(event, keymap);
                hud.fireKeyEvent(event);
            }
        } else if (event.toString().contains("KEY_RELEASED")) {
            if (event.getKeyCode() == keymap.UP_PRIMARY || event.getKeyCode() == keymap.UP_SECONDARY) {
                upPressed = false;
            } if (event.getKeyCode() == keymap.DOWN_PRIMARY || event.getKeyCode() == keymap.DOWN_SECONDARY) {
                downPressed = false;
            } if (event.getKeyCode() == keymap.LEFT_PRIMARY || event.getKeyCode() == keymap.LEFT_SECONDARY) {
                leftPressed = false;
            } if (event.getKeyCode() == keymap.RIGHT_PRIMARY || event.getKeyCode() == keymap.RIGHT_SECONDARY) {
                rightPressed = false;
            } if (event.getKeyCode() == keymap.RUN_PRIMARY || event.getKeyCode() == keymap.RUN_SECONDARY) {
                spacePressed = false;
            }if(event.getKeyCode() == keymap.TURN_TYPE_HOLD_PRIMARY || event.getKeyCode() == keymap.TURN_TYPE_HOLD_SECONDARY) {
                orientationLocked = !orientationLocked;
                turnTypeHeld = false;
            }
        }
        if (frozen){
            resetMovement();
        }
    }

    /*public void testSpell1(){
        int dmg = 10;
        int rng = 5;
        FancySpell toFire = new FancySpell(room, x, y, orientation, dmg, rng,
                new SpecialText("|"), new SpecialText("-"), false, "fire");
        toFire.makeSpiral(2, 10);
        //toFire.makeSplash(5);
        toFire.setObjectToCenterOn(this);
        room.addObject(toFire);
    }*/

    /**
     * @return which zone this Player is in!
     */
    public int getZoneNumber() {
        return zoneNumber;
    }

    /**
     * Move on to a new Zone?  Call this in GameInstance.java so saving doesn't break!
     */
    public void setZoneNumber(int newZoneNumber) {
        zoneNumber = newZoneNumber;
    }

    public void setGameInstance(GameInstance gameInstance) {
        this.gameInstance = gameInstance;
    }

    public int getOrientation() {
        return orientation;
    }

    public void addCurrency(String currencyName, int quantity) {
        // Todo: implement better money!  Do we want to store lists?
        currencyName = currencyName.toLowerCase();
        if (currencyName.equals("snowflake")){
            snowflakes+=quantity;
        }
    }

    public boolean subtractCurrency(String currencyName, int quantity){
        return false;
    }

    public int getCurrency(String currencyName){
        currencyName = currencyName.toLowerCase();
        if (currencyName.equals("snowflake")){
            return snowflakes;
        }
        return 0;
    }

    public boolean getOrientationLocked() {
        return orientationLocked;
    }

    public GameInstance getGameInstance() {
        return gameInstance;
    }

    public void setKeyMap(KeyMap keyMap) {
        this.keymap = keyMap;
    }

    public boolean hasKeyMap() {
        return keymap != null;
    }

    public boolean rickroll(){
        return hud.rickroll();
    }
}
