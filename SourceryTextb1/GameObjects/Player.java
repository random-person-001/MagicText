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
package SourceryTextb1.GameObjects;

import SourceryTextb1.*;
import SourceryTextb1.Rooms.Room;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ConcurrentModificationException;

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
    private NetworkServer networkServer; // Only used in multiplayer
    private boolean hasLocalWindow;

    private boolean shouldNewInv = false;
    private String lastPainMessage = "None";
    public boolean dead = false;

    //Convenience variables
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    // CONSTANT STATS
    int baseMaxHP = 20;
    int maxMana = 20;
    private int manaRegenClock = 0;
    int manaWait = 0;
    private boolean manaHalfStep;
    int defense = 0;
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


    /**
     * Initialize a whole lotta variables.
     *
     * @param theOrg the ImageOrg(anizer)
     */
    public Player(GameInstance gameInstance, ImageOrg theOrg, int playerNumber) {
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
    }

    /**
     * Initialize a new NetworkServer and start its doTimerSend() method, which sends display information to clients.
     */
    void testSendOverNetwork() {
        try {
            networkServer = new NetworkServer(this);
            networkServer.doTimerSend();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempt to cancel the sending of the display data over network. (stops an earlier instance of calling testSendOverNetwork())
     */
    void cancelSendOverNetwork() {
        try {
            networkServer.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Set things up that don't get carried between saves, ex timers
     */
    public void resumeFromSave() {
        //org.getWindow().txtArea.addKeyListener(new PlayerKeyPressListener(this)); // Add key listeners.
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
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
    }

    /**
     * Change the Player's perception of which room it is in, and do various cleenup from before.
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
     * Set x and y coordinates directly.
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
     * Perform a general update of the player.
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

            manaRegenClock += getTime();
            if (manaWait > 0) {
                manaWait -= getTime();
                manaRegenClock = 0;
            } else if (manaRegenClock >= (500 / maxMana) && mana < maxMana) {
                mana++;
                manaRegenClock = 0;
            }

            resetTime(); // time is used for mana regen.
            graphicUpdate();
            aimDispUpdate();
            doMovement();
        }
    }

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

    private boolean hadLocked = false;

    private void aimDispUpdate() {
        if (orientationLocked && !hadLocked) {
            switch (orientation) {
                case UP:
                    org.editLayer("+", layerName, 0, 1);
                    break;
                case DOWN:
                    org.editLayer("+", layerName, 2, 1);
                    break;
                case LEFT:
                    org.editLayer("+", layerName, 1, 0);
                    break;
                case RIGHT:
                    org.editLayer("+", layerName, 1, 2);
                    break;
            }
            hadLocked = true;
        } else if (!orientationLocked && hadLocked) {
            org.editLayer(" ", layerName, 1, 0);
            org.editLayer(" ", layerName, 0, 1);
            org.editLayer(" ", layerName, 1, 2);
            org.editLayer(" ", layerName, 2, 1);
            hadLocked = false;
        }
    }

    private void reportPos() {
        System.out.println("\nPlayer X: " + x + "\nPlayer Y: " + y + "\nPaused?: " + paused + "\n");
    }

    /**
     * @return the player's instance of Inventory
     */
    Inventory getInventory() {
        return inv;
    }

    /**
     * Writes a .sav file (of the serialized Player) to a user-defined directory
     *
     * @return whether the saving was successful
     */
    boolean saveGame() {
        if (gameInstance == null) {
            System.out.println("Player.gameInstance is null; cannot save");
            return false;
        }
        org.terminateClock();
        System.out.println("Running serialization test...");
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
            return false;
        }

        try {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println("Writing, please wait...");
            out.writeObject(gameInstance);
            out.close();
            fileOut.close();
            System.out.printf("Serialized Player data is saved in " + path);
            org.resetClock();
            return true;
        } catch (IOException | ConcurrentModificationException e) {
            e.printStackTrace();
            org.resetClock();
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
     * Increment variables relating to fab mode if needed.
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
     * Update the Player symbol and all the graphic things
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
        if (!swimming)
            playerIcon = new SpecialText("@", new Color(150, 255, 100));
        else {
            if (waterEntry == 2)
                playerIcon = new SpecialText("u", new Color(255, 255, 255), new Color(70, 70, 200));
            else if (waterEntry == 1) {
                playerIcon = new SpecialText("Y", new Color(255, 255, 255), new Color(70, 70, 200));
            } else
                playerIcon = new SpecialText("@", new Color(100, 150, 255), new Color(65, 65, 200));
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
        return getX() - 22;
    }

    /**
     * Used by ImageOrg's buildImage.
     *
     * @return where the camera ought to be
     */
    public int getCamY() {
        return getY() - 11;
    }

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

    //   Where the spell boost variables are used, the corresponding expression should be used instead. --Riley
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
    void keyPressed(char key) {
        if (!frozen) {
            switch (Character.toLowerCase(key)) {
                case '\'': // ESC right now, subject to change
                    reportPos();
                    break;
                case 'a':
                    orientationLocked = !orientationLocked;
                    break;
                case 's':
                    newCastSpell(spell1);
                    break;
                case 'd':
                    newCastSpell(spell2);
                    break;
                case 'w':
                    shouldNewInv = true;
                    break;
                case 'q':
                    reportPos();
                    break;
                case 'f':
                    textBoxQuery();
                    break;
                case 'h':
                    System.out.println(room.getCountOf("HUD"));
                default:
                    System.out.print(key);
            }
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
        if (!swimming) { //Swimming AND casting spells at the same time?!?!?! That's too much, man!
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
                looseCastDmgSpell(damage, spell);
            } else {
                switch (spell.getName()) {
                    case "Heal":
                        if (mana >= spell.cost) {
                            restoreHealth(spell.healing);
                            spendMana(spell.cost);
                        }
                        break;
                }
            }
        }
    }


    private void spendMana(int cost) {
        mana -= cost;
        int wait = 2000 - (int) (1750 * ((float) mana / (float) maxMana));
        manaWait = wait;
    }

    private void looseCastDmgSpell(int damage, Item spell) {
        looseCastDmgSpell(damage, spell.range, spell.cost, spell.animation1, spell.animation2, spell.getAlting(), spell.getPathfinding(), spell.getDescMode());
    }

    private void looseCastDmgSpell(int dmg, int rng, int cost, SpecialText anim1, SpecialText anim2, boolean alt, boolean tracking, String spellType) {
        if (mana >= cost) {
            Spell toFire = new Spell(room, x, y, orientation, dmg, rng, anim1, anim2, alt, tracking);
            toFire.setType(spellType);
            toFire.setHostility(false);
            room.addObject(toFire);
            spendMana(cost);
            //System.out.println("The damage spell fired!");
        }
    }

    String getPrimarySpell() {
        return spell1.getIcon();
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
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = true;
            } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = true;
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = true;
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = true;
            } else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                spacePressed = true;
            } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                keyPressed('\'');
            } else {
                keyPressed(event.getKeyChar());
            }
            if (room != null) { // Player is sometimes initialized for a bit before being placed into the room
                inv.fireKeyEvent(event);
                room.fireKeyEvent(event, getUsername());
                hud.fireKeyEvent(event);
            }
        } else if (event.toString().contains("KEY_RELEASED")) {
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                upPressed = false;
            } else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                downPressed = false;
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                leftPressed = false;
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                rightPressed = false;
            } else if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                spacePressed = false;
            }
        }
    }

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
}
