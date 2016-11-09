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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Player-controlled protagonist
 *
 * @author Riley
 */
public class Player extends Mortal implements java.io.Serializable {
    private String username = System.getProperty("user.name");
    //private PlayerKeypressListener playerKeyListener;// = new PlayerKeypressListener(this);
    private Inventory inv;
    private ItemTracker tracker;
    private HUD hud;
    public String roomName = ""; //Extremely important when we implement saving.
    private ImageOrg realOrg; // For access to the real window for keybindings

    private boolean shouldNewInv = false;
    public boolean frozen = false; //This gets changed by a room upon beginning the level

    //Convenience variables
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed = false;
    boolean swimming = false;
    int waterEntry = 0;
    private int movecount = 0;
    boolean ludicrousSpeed = false;

    private int orientation = UP;
    private boolean orientationLocked = false;
    private String aimDispName = "aimDisp";

    private Color restingBackground = Color.black;
    boolean isGhost = false;
    private String lastPainMessage = "None";

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

    int allSpellBoost = 0;
    int arcSpellBoost = 0;
    int fireSpellBoost = 0;
    int iceSpellBoost = 0;
    int darkSpellBoost = 0;
    private int healBoost, durBoost, rangeBoost, armorHealthBoost = 0;
    //NO MORE STATS

    public boolean dead = false;
    private int technicolorIndex = 0;
    private int hurtColor = 0;

    Item spell1 = new Item("None", "", this);
    Item spell2 = new Item("None", "", this);
    Item weapon = new Item("None", "", this);
    Item armor = new Item("None", "", this);

    int screenRedness = 0;
    int screenYellowness = 0;
    boolean hasLocalWindow;
    private NetworkerServer networkerServer;

    /**
     * Initialize a whole lotta variables.
     *
     * @param theOrg the ImageOrg(anizer)
     */
    public Player(ImageOrg theOrg, int playerNumber) {
        hasLocalWindow = (playerNumber == 0);
        setHealth(baseMaxHP);
        makeGoodGuy(); // Set good-guy-ness to true.
        super.maxHealth = baseMaxHP + armorHealthBoost;
        super.strClass = "Player";
        System.out.println("\nNEW PLAYER\n");
        orgo = theOrg;
        username += playerNumber;

        layerName = "playerLayer-" + username;
        aimDispName += username;

        inv = new Inventory(orgo, this);
        tracker = new ItemTracker();

        hud = new HUD(this);
        hud.setOrgo(orgo);
        resumeFromSave();
    }

    /**
     * Initialize a new NetworkerServer and start its doTimerSend() method, which sends display information to clients.
     */
    void testSendOverNetwork() {
        try {
            networkerServer = new NetworkerServer();
            networkerServer.doTimerSend(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempt to cancel the sending of the display data over network. (stops an earlier instance of calling testSendOverNetwork())
     */
    void cancelSendOverNetwork(){
        try {
            networkerServer.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Intended for multiplayer, for instance adding key listeners, something that is now being moved over to network stuff
     * @return the ImageOrg for the actual physical window that corresponds to the player, something not viable with network.
     */
    @Deprecated // Only useful in obsolete implementations of multiplayer
    public ImageOrg getRealOrg(){
        if (realOrg == null){ // which is the same as hasLocalWindow
            return orgo;
        }
        return realOrg;
    }

    /**
     * Intended for multiplayer, for instance adding key listeners, something that is now being moved over to network stuff
     * Set the ImageOrg for the actual physical window that corresponds to the player, something not viable with network.
     */
    @Deprecated // Only useful in obsolete implementations of multiplayer
    public void setRealOrg(ImageOrg newRealOrg){
        realOrg = newRealOrg;
    }

    /**
     * Set things up that don't get carried between saves, ex timers and ?keylisteners?
     */
    public void resumeFromSave() {
        //orgo.getWindow().addKeyListener(playerKeyListener); // Add key listeners.
        setupForNewRoom();
        setupTimer(20);
        orgo.resetClock();
    }

    public String getUsername(){
        return username;
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
        orgo.removeLayer(layerName);
        orgo.removeLayer(aimDispName);
        orgo.removeLayer("HUD_of_" + username);
        //Set new room
        room = newRoom;
        //Update everybody's orgs
        orgo = room.org;
        hud.setOrgo(orgo);
        // Add the player layers to this room
        addPlayerLayers();
    }

    public void restartTimer(){
        setupTimer(20);
    }

    private void addPlayerLayers() {
        // Add hud layer
        System.out.println("Adding HUD for " + username);
        Layer HUDd = new Layer(new String[1][70], "HUD_of_" + username, false, true);
        HUDd.setImportance(true);
        HUDd.setOwningPlayerUsername(username);
        orgo.addLayer(HUDd);
        hud.setLayerName(HUDd.name);

        // Add player layer
        Layer playerLayer = new Layer(new String[3][3], layerName);
        playerLayer.setImportance(true);
        orgo.addLayer(playerLayer);

        // Add aim display layer
        Layer aimDispLayer = new Layer(new String[1][1], aimDispName);
        aimDispLayer.setOwningPlayerUsername(username);
        orgo.addLayer(aimDispLayer);
    }

    /**
     * Set x and y coordinates directly.
     *
     * @param newX the new x coord
     * @param newY the new y coord
     */
    @Override
    public void goTo(int newX, int newY) {
        //orgo.editLayer(" ", layerName, y, x);
        x = newX;
        y = newY;
        graphicUpdate();
    }

    /**
     * Perform a general update of the player.
     */
    @Override
    public void update() {
        if (hasLocalWindow){ // Build the image if this is the local player.  Otherwise, somewhere else will.
            //orgo.getWindow().build(orgo.topDownBuild(getX()-22, getY()-11, username));
        }
        if (frozen || dead) { // Should be first, so other things don't try to happen first
            System.out.println("Not Updating " + username);
            try {
                if (dead) {
                    onDeath();
                }
                orgo.editLayer(" ", layerName, y, x);
            } catch (IndexOutOfBoundsException ignored) {}
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

            if (screenRedness > 0 || screenYellowness > 0){
                if (screenRedness > 0) screenRedness --;
                //This means that higher levels of redness depletes faster
                if (screenRedness > 200) screenRedness--;
                if (screenRedness > 100) screenRedness--;
                if (screenYellowness > 0) screenYellowness--;
                //System.out.print("The screen is red! | ");
                int opposite = 255 - screenRedness;
                int yellowNumber = (int)(opposite * (1-((float)screenYellowness/100)));
                //System.out.printf("Screen yellow factor: %1$d, (%2$d --> %3$d)\n", screenYellowness, opposite, yellowNumber);

                // convert old Java color thing to Hex color
                Color c = new Color(255, opposite, yellowNumber);
                getRealOrg().getWindow().txtArea.setOverallForeGround(c);
                //orgo.getWindow().txtArea.setForeground(new Color(255, opposite, yellowNumber)); // old way
            }

            resetTime();
            graphicUpdate();
            aimDispUpdate();
            updateBackground();
            doMovement();
        }
    }

    private void doMovement(){
        int movespeed = 5;
        boolean spendingManaToSprint = false;
        if (swimming)       movespeed = 7;
        if (isGhost)        movespeed = 1;
        if (ludicrousSpeed) movespeed = 0;
        if (spacePressed && mana > 0){
            if (swimming) {
                movespeed -= 3;
            } else {
                movespeed -= (int)sprintVelocity;
            }
            spendingManaToSprint = true;
        } else {
            sprintVelocity = 1;
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
    public void resetMovement(){
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        spacePressed = false;
    }

    private boolean hadLocked = false;
    private void aimDispUpdate() {
        if (orientationLocked && !hadLocked){
            switch (orientation){
                case UP:
                    orgo.editLayer("+", layerName, 0, 1);
                    break;
                case DOWN:
                    orgo.editLayer("+", layerName, 2, 1);
                    break;
                case LEFT:
                    orgo.editLayer("+", layerName, 1, 0);
                    break;
                case RIGHT:
                    orgo.editLayer("+", layerName, 1, 2);
                    break;
            }
            hadLocked = true;
        } else if (!orientationLocked && hadLocked){
            orgo.editLayer(" ", layerName, 1, 0);
            orgo.editLayer(" ", layerName, 0, 1);
            orgo.editLayer(" ", layerName, 1, 2);
            orgo.editLayer(" ", layerName, 2, 1);
            hadLocked = false;
        }
    }

    private void reportPos() {
        System.out.println("\nPlayer X: " + x + "(" + orgo.getCamX() + ")" + "\nPlayer Y: " + y + "(" + orgo.getCamY() + ")" + "\nPaused?: " + paused + "\n");
    }

    /**
     * @return the player's instance of Inventory
     */
    Inventory getInventory() {
        return inv;
    }

    /**
     * Show the player was damaged.  This will make the player flicker and the text on
     * the screen to flicker between to be more red than white.  Probably only called after subtractHealth in Mortal.
     *
     * @param deathMessage a final string to show lest you have died
     */
    void showPain(String deathMessage) {
        //orgo.editLayer(" ", layerName, 0, 0);
        hurtColor += 3;
        lastPainMessage = deathMessage;
    }


    /**
     * Writes a .sav file (of the serialized Player) to a user-defined directory
     * @return whether the saving was successful
     */
    boolean saveGame(){
        orgo.terminateClock();
        System.out.println("Running serialization test...");
        String path;
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(roomName + ".sav"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Sourcery Text Saves", "sav");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showSaveDialog(new Component(){});
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getPath();
            if (!path.endsWith(".sav")){ // Add .sav to file if user didn't.
                path += ".sav";
            }
            System.out.println("You chose to save the file to: " + path);
        } else {
            orgo.resetClock();
            return false;
        }


        try
        {
            FileOutputStream fileOut =
                    new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(room.playo);
            out.close();
            fileOut.close();
            System.out.printf("Serialized Player data is saved in " + path);
            orgo.resetClock();
            return true;
        }catch(IOException i)
        {
            i.printStackTrace();
            orgo.resetClock();
            return false;
        }
    }


    @Override
    public void onDeath() {
        //orgo.getWindow().txtArea.removeKeyListener(playerKeyListener);
        room.compactTextBox(lastPainMessage, "An ominous voice from above", false);
        dead = true;
        room.exitCode = "die";
    }

    /**
     * Update the Player symbol
     */
    private void graphicUpdate() {
        //String color = (upPressed|downPressed|leftPressed|rightPressed) ? "66ff66" : "80ff80";
        //color = (spacePressed) ? "33ff33" : color;
        //color = (ludicrousSpeed) ? "00b300" : color;
        SpecialText playerIcon = new SpecialText(" ");
        if (!swimming)
            playerIcon = new SpecialText("@",new Color (150, 255, 100));
        else {
            if (waterEntry == 2)
                playerIcon = new SpecialText("u", new Color(255, 255, 255), new Color(70, 70, 200));
            else if (waterEntry == 1){
                playerIcon = new SpecialText("Y", new Color(255, 255, 255), new Color(70, 70, 200));
            }
            else
                playerIcon = new SpecialText("@", new Color(100, 150, 255), new Color(65, 65, 200));
        }
        orgo.editLayer(playerIcon, layerName, 1, 1);
        orgo.getLayer(layerName).setPos(y-1, x-1);
        getRealOrg().setCam(getX()-22, getY()-11);
    }

    private void move(int direction) {
        if (!paused.get()) {
            try {
                //orgo.editLayer(" ", layerName, y, x);
                room.removeFromObjHitMesh(x, y);
            } catch (IndexOutOfBoundsException e) {
                return;
            }
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
        defense =          armor.getEquipVals()[0] + weapon.getEquipVals()[0];
        armorHealthBoost = armor.getEquipVals()[1] + weapon.getEquipVals()[1];
        allSpellBoost =    armor.getEquipVals()[2] + weapon.getEquipVals()[2];
        arcSpellBoost =    armor.getEquipVals()[3] + weapon.getEquipVals()[3];
        fireSpellBoost =   armor.getEquipVals()[4] + weapon.getEquipVals()[4];
        iceSpellBoost =    armor.getEquipVals()[5] + weapon.getEquipVals()[5];
        darkSpellBoost =   armor.getEquipVals()[6] + weapon.getEquipVals()[6];

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
        room.queryForText(getX() - 1, getY(), username);
        room.queryForText(getX() + 1, getY(), username);
        room.queryForText(getX(), getY() - 1, username);
        room.queryForText(getX(), getY() + 1, username);
        room.queryForText(getX(), getY(), username);
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
                            restoreHealth(8);
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
        looseCastDmgSpell(damage, spell.range, spell.cost, spell.animation1, spell.animation2, spell.getAlting(), spell.getPathfinding());
    }

    private void looseCastDmgSpell(int dmg, int rng, int cost, SpecialText anim1, SpecialText anim2, boolean alt, boolean tracking) {
        if (mana >= cost) {
            Spell toFire = new Spell(room, x, y, orientation, dmg, rng, anim1, anim2, alt, tracking);
            toFire.setHostility(false);
            room.addObject(toFire);
            spendMana(cost);
            //System.out.println("The damage spell fired!");
        }
    }

    private void updateBackground() { // Max is about 15 or 16
        if (technicolorIndex > 0) { // Update the background color, if you did the supercheat.
            float r, g, b;
            r = 0;
            b = 0;
            g = 0;
            // RGB:  001 010 011 100 101 110 111
            //         1   2   3   4   5   6   7
            if ((technicolorIndex / 4) % 2 > 0.) {
                r = .5f;
            }
            if ((technicolorIndex / 2) % 2 > 0) { // Uh
                g = .5f;
            }
            if (technicolorIndex % 2 > 0) { // On odds
                b = .5f;
            }
            technicolorIndex--;
            orgo.getWindow().txtArea.setBackground(new Color(r, g, b));
        } else if (hurtColor >= 1) {  // update the redness of the screen; more red = more recently hurt more
            int top = 5;
            if (hurtColor > top) {
                hurtColor = top;
            }
            float eh = (float) (hurtColor - 1) / top;
            Color c = new Color(1f - eh / 2, 1f - eh, 1f - eh);
            orgo.getWindow().txtArea.setForeground(c);
            hurtColor--;
        } else {
            orgo.getWindow().txtArea.setBackground(restingBackground);
        }
    }

    String getPrimarySpell() {
        return spell1.getIcon();
    }

    void addItem(Item input) {
        inv.addItem(input);
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
                    "\n permanently increase\n your max health or\n max mana by 5.", this, "item"));
        }
    }

    /**
     * Receive an arrow (or space) keypress
     * @param keyName a String: up, down, left, right, or space
     * @param noActuallyItWasReleased for simplification.  Self explanitory.
     */
    void moveKeyPressed(String keyName, boolean noActuallyItWasReleased){
        if (frozen || dead){
            return;
        }
        switch (keyName){
            case "up":
                upPressed = noActuallyItWasReleased;
                break;
            case "down":
                downPressed = noActuallyItWasReleased;
                break;
            case "left":
                leftPressed = noActuallyItWasReleased;
                break;
            case "right":
                rightPressed = noActuallyItWasReleased;
                break;
            case "space":
                spacePressed = noActuallyItWasReleased;
                break;
            default:
                System.out.println("Someone called Player.moveKeyPressed() wrong! '" + keyName + "' not recognised");
        }
    }
}
