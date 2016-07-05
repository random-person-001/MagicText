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

import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.Window;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.TimerTask;


import static java.lang.Math.abs;

/**
 * Player-controlled protagonist
 *
 * @author Riley
 */
public class Player extends Mortal{
    private MKeyListener playerKeyListener = new MKeyListener(this);
    private GameObject closestFood = null;
    private Inventory inv;
    private int celeCount = 0;
    private boolean s1 = false; //toggler for celeb anim
    private String state1 = "+";
    private String state2 = "X";
    private boolean autonomous = false;
    public boolean shouldPause = false;
    private boolean shouldInventory = false;
    private boolean shouldNewInv = false;
    public boolean frozen = true; //This gets changed by a room upon beginning the level

    //Convenience variables
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    private int orientation = UP;
    boolean orientationLocked = false;

    private String aimDispName = "aimDisp";

    public Layer castingLayer;

    private int superCheatProgress = 0;
    private Color restingBackground = Color.black;

    //STATS
    public int maxHP = 20;

    public int maxMana = 20;
    public int mana = maxMana;
    public int manaRegen = 30; //Mana per second
    private int manaRegenClock = 0;
    //private int manaWaitClock = 0;
    public int manaWaitStat = 2000; //Waiting before restoring mana
    public int manaWait = 0;

    public int defense = 0;
    //Note for the future: Damage can't be reduced below 1 damage. Swords and explosions don't heal people.

    public int allSpellBoost = 0;
    public int arcSpellBoost = 0;
    public int fireSpellBoost = 0;
    public int iceSpellBoost = 0;
    public int darkSpellBoost = 0;
    public int healBoost, durBoost, rangeBoost, armorHealthBoost = 0;

    //NO MORE STATS

    public boolean dead = false;
    private int technicolorIndex = 0;
    private String primarySpell = "Book";
    private String secondarySpell = "None";

    public Item spell1 = new Item ("None", "", this);
    public Item spell2 = new Item ("None", "", this);

    public Item weapon = new Item ("None", "", this);
    public Item armor  = new Item ("None", "", this);

    private int hurtColor = 0;

    /**Initialize a whole lotta variables.
     * @param theOrg the ImageOrg(anizer)
     */
    public Player(ImageOrg theOrg) {
        setHealth(maxHP);
        makeGoodGuy(); // Set good-guy-ness to true.
        super.strClass = "Player";
        System.out.println("\nNEW PLAYER\n");

        orgo = theOrg;
        layerName = "playerLayer";

        Layer playerLayer = new Layer(new String[orgo.getWindow().maxH()][orgo.getWindow().maxW()], layerName);
        orgo.addLayer(playerLayer);
        setupForNewRoom();

        super.maxHealth = maxHP + armorHealthBoost;

        Window window = orgo.getWindow();
        window.txtArea.addKeyListener(playerKeyListener); // Add key listeners.

        inv = new Inventory(orgo, this);

        /*
        Timer tima = new Timer("tima");

        tima.scheduleAtFixedRate(new PlayerTimerTask(), 20, 20);
        */
        setupTimer(20);
    }

    public void setPrimarySpell (String spell){
        primarySpell = spell;
    }
    public void setSecondarySpell (String spell){
        secondarySpell = spell;
    }

    public void setupForNewRoom(){
        Layer aimDispLayer = new Layer(new String[orgo.getWindow().maxH()][orgo.getWindow().maxW()], aimDispName);
        orgo.setCam(x - 22, y - 8);
        orgo.addLayer(aimDispLayer);
    }

    public void centerCamera(){
        orgo.setCam(x - 22, y - 8);
    }

    /**Change the Player's perception of which room it is in.  As a bonus, celebrate a bit.
     * @param newRoom a Room that Player should consider itself in
     */
    public void setRoom(Room newRoom) {
        celeCount = 12;
        room = newRoom;
    }

    /**Set x and y coordinates directly.
     * @param newX
     * @param newY
     */
    @Override
    public void goTo(int newX, int newY) {
        orgo.editLayer(" ", layerName, y, x);
        x = newX;
        y = newY;
        centerCamera();
    }

    /**
     * Perform a general update of the player.
     */
    @Override
    public void update(){

        if (shouldPause) {
            System.out.println("Pausing.");
            room.pause(orgo);
            shouldPause = false;
        } else if (shouldInventory){
            //orgo.getWindow().removeKeyListener(playerKeyListener);
            inv.show();
            //orgo.getWindow().addKeyListener(playerKeyListener);
            shouldInventory = false;
        } else if (shouldNewInv){
            System.out.println("GAME PAUSED\n " + inv.pressedA);
            inv.newShow();
            shouldNewInv = false;
            System.out.println("GAME UNPAUSED");
        }
        if (frozen) {
            try {
                orgo.editLayer(" ", layerName, y, x);
            } catch (IndexOutOfBoundsException ignored) {
            }
            return;
        }

        manaRegenClock += getTime();

        if (manaWait > 0) {
            manaWait -= getTime();
            //System.out.println("Mana Wait Clock: " + manaWait);
            manaRegenClock = 0;
        } else if (manaRegenClock >= (500 / maxMana) && mana < maxMana) {
            mana++;
            manaRegenClock = 0;
        }

        //System.out.println(getTime());

        resetTime();

        graphicUpdate();
        aimDispUpdate();
        //reportPos();

        if (autonomous) {
            closestFood = getClosestVisibleFood();
            if (closestFood != null) {
                if (x > closestFood.x) {
                    move(LEFT);
                } else if (x < closestFood.x) {
                    move(RIGHT);
                } else if (y > closestFood.y) {
                    move(UP);
                } else if (y < closestFood.y) {
                    move(DOWN);
                } else {
                    room.objs.remove(closestFood);
                    closestFood = null;
                    move(r(3));
                }
            } else { // Just finished a level / no visible food left
                //move(r(3));  //Move randomly
            }
        } else { // Unset
            closestFood = null;
        }
        if (dead){
            System.out.println("Apparently you died.");
            System.exit(0);
        }
        updateBackground();
    }

    private void aimDispUpdate(){
        int editAt = orgo.getPosLayer(aimDispName);
        if (editAt > -1) {  //Basically, if aimDispLayer != null
            orgo.getLayer(editAt).clear();
            if (orientationLocked) {
                switch (orientation) {
                    case UP:
                        orgo.editLayer("+", editAt, y - 1, x);
                        break;
                    case DOWN:
                        orgo.editLayer("+", editAt, y + 1, x);
                        break;
                    case LEFT:
                        orgo.editLayer("+", editAt, y, x - 1);
                        break;
                    case RIGHT:
                        orgo.editLayer("+", editAt, y, x + 1);
                        break;
                    default:
                        System.out.println("No valid orientation? IMPOSSIBLE");
                }
            }
        }
    }

    public void reportPos(){
        System.out.println("Player X: " + x + "\nPlayer Y: " + y + "\n");
    }


    /**Generate a random int between 0 and max, inclusive.
     * @param max the largest number that amy be returned
     * @return a random int
     */
    private int r(int max) {
        return r(max, 0);
    }
    private int r(int max, int min) {
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
    }

    private GameObject getClosestVisibleFood() {
        int minDist = 500000;
        if (!room.objs.contains(closestFood)) {
            for (GameObject o : room.objs) {
                if (o.strClass.contains("Food")) { //Found a food!
                    // check x
                    boolean obscured = false;
                    if (x < o.x) {
                        for (int i = x; i < o.x; i++) {
                            obscured |= room.isPlaceSolid(i, y);
                        }
                    } else {
                        for (int i = x; i > o.x; i--) {
                            obscured |= room.isPlaceSolid(i, y);
                        }
                    }// check y
                    if (y < o.y) {
                        for (int i = y; i < o.y; i++) {
                            obscured |= room.isPlaceSolid(o.x, i);
                        }
                    } else {
                        for (int i = y; i > o.y; i--) {
                            obscured |= room.isPlaceSolid(o.x, i);
                        }
                    }
                    //System.out.println(obscured);
                    if (!obscured) {
                        System.out.println("Found a visible food. dx: " + (x - o.x) + "  dy: " + (y - o.y));
                        int dist = abs(o.y - y) + abs(o.x - x);
                        if (dist < minDist) {
                            minDist = dist;
                            closestFood = o;
                        }
                    }
                }
            }
        }
        return closestFood;
    }

    /**
     * Hurt the player by a specified amount of health.  This will make the game freeze a little, the player flicker,
     * and the text on the screen to flicker between red and white.
     * @param damage how much health to take away
     */
    public void hurt(int damage, String deathMessage){
        subtractHealth(damage, deathMessage);
        hurtColor += damage/3;
        //hurt(); subtractHealth does that
    }

    /**
     * Hurt the player without damage.  This will make the game freeze a little, the player flicker, and the text on
     * the screen to flicker between red and white.
     */
    public void hurt(String deathMessage) {
        orgo.editLayer(" ", layerName, y, x);
        orgo.compileImage();
        if (checkDeath()){
            orgo.getWindow().txtArea.setForeground(Color.RED);
            room.compactTextBox(orgo, deathMessage, "An ominous voice from above", false);
            dead = true;
        }
        hurtColor += 3;
    }

    /**
     * Start a celebration, now that a food has been eaten
     */
    public void celebrate() {
        celeCount = 5;
    }

    private String smallChar = "x";
    private String largeChar = "@";
    private boolean big = false;

    /**
     * Update the Player symbol
     */
    public void graphicUpdate() {
        orgo.editLayer((big) ? smallChar : largeChar, layerName, y, x);
        centerCamera();
    }

    private void move(int direction) {
        try {
            orgo.editLayer(" ", layerName, y, x);
            room.removeFromObjHitMesh(x,y);
        } catch (IndexOutOfBoundsException e) {
            return;
        }
        switch (direction) {
            case UP:
                if (!room.isPlaceSolid(x, y - 1))
                    y--;
                if (!orientationLocked)
                    orientation = UP;
                break;
            case DOWN:
                if (!room.isPlaceSolid(x, y + 1))
                    y++;
                if (!orientationLocked)
                    orientation = DOWN;
                break;
            case LEFT:
                if (!room.isPlaceSolid(x - 1, y))
                    x--;
                if (!orientationLocked)
                    orientation = LEFT;
                break;
            case RIGHT:
                if (!room.isPlaceSolid(x + 1, y))
                    x++;
                if (!orientationLocked)
                    orientation = RIGHT;
                break;
            default:
                System.out.println("Bro, you're using Player.move wrong.");
        }
        room.addToObjHitMesh(x,y);
        graphicUpdate();
    }

    public void equip(Item toEquip){
        if (toEquip.getEquipType().toLowerCase().equals("weapon")){
            weapon = toEquip;
            defineStats();
        } else if (toEquip.getEquipType().toLowerCase().equals("armor")){
            armor = toEquip;
            defineStats();
        } else {
            System.out.println("You can't wear that!");
        }
    }

    public void defineStats(){
        defense = armor.getEquipVals()[0];
        armorHealthBoost = armor.getEquipVals()[1];
        allSpellBoost = weapon.getEquipVals()[2];
        arcSpellBoost = weapon.getEquipVals()[3];
        fireSpellBoost = weapon.getEquipVals()[4];
        iceSpellBoost = weapon.getEquipVals()[5];
        darkSpellBoost = weapon.getEquipVals()[6];

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
        switch (key) {
            case '©':
                move(UP);
                break;
            case 'µ':
                move(LEFT);
                break;
            case '®':
                move(DOWN);
                break;
            case 'æ':
                move(RIGHT);
                break;
            case ' ':
                big = !big;
                break;
            case 'b':
                autonomous = !autonomous;
                break;
            case '\'':
                shouldPause = true;
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
            default:
                System.out.print(key);
        }
        graphicUpdate();
        checkCheatProgress(key);
    }


    private void castSpell(String spellName){
        if (mana >= 1) {
            if (mana >= 1 && spellName.equals("Book") && inv.hasItem("Book")) {
                room.addObject(new Spell(orgo, room, castingLayer, x, y, orientation, "Book"));
                inv.subtractItem("Book");
            }
            if (mana >= 2 && spellName.equals("Spark") && inv.hasItem("Spark")) {
                room.addObject(new Spell(orgo, room, castingLayer, x, y, orientation, "Spark"));
                manaWait = manaWaitStat;
                mana -= 2;
            }
            if (mana >= 3 && spellName.equals("Flame") && inv.hasItem("Flame")) {
                room.addObject(new Spell(orgo, room, castingLayer, x, y, orientation, "Flame"));
                manaWait = manaWaitStat;
                mana -= 3;
            }
            if (mana >= 3 && spellName.equals("SmallHealth") && inv.hasItem("SmallHealth")) {
                setHealth(getHealth()+10);
                manaWait = manaWaitStat;
                mana -= 3;
            }
            if (mana >= 20 && spellName.equals("HugeHealth") && inv.hasItem("HugeHealth")) {
                setHealth(getHealth()+40);
                manaWait = manaWaitStat;
                mana -= 20;
            }
            if (mana >= 2 && spellName.equals("Wanderer") && inv.hasItem("Wanderer")) {
                room.addObject(new Spell(orgo, room, castingLayer, x, y, orientation, "Wanderer"));
                manaWait = manaWaitStat;
                mana -= 2;
            }
            if (spellName.equals("None")) {
                System.out.println("No spell equipped.");
            }
            System.out.println("Unrecognised spell ("+spellName+") or it isn't in your inventory or not enough mana.");
        }
        else{
            System.out.println("You don't have any mana!");
        }
    }

    private void newCastSpell(Item spell){
        if (spell.isDmgSpell){
            int damage = spell.damage + allSpellBoost;
            switch(spell.getDescMode()){
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
            looseCastDmgSpell(damage, spell.range, spell.cost, spell.animation1, spell.animation2, spell.getAlting());
            //System.out.println("Pew! I just fired " + spell.getName());
        } else {
            switch (spell.getName()){
                case "Heal":
                    if (mana >= spell.cost) {
                        restoreHealth(8);
                        spendMana(spell.cost);
                    }
                    break;
            }
        }
    }

    private void spendMana(int cost){
        mana -= cost;
        int wait = 2000 - (int)(1750 * ((float)mana / (float)maxMana));
        //System.out.println("Waiting before mana refresh (ms): " + wait + " (" + ((float)mana / (float)maxMana) + ")");
        manaWait = wait;
    }

    private void looseCastDmgSpell(int dmg, int rng, int cost, String anim1, String anim2, boolean alt){
        if (mana >= cost){
            room.addObject(new Spell(orgo, room, castingLayer, x, y, orientation, dmg, rng, anim1, anim2, alt));
            spendMana(cost);
            //System.out.println("The damage spell fired!");
        }
    }
    /**
     * @param newColor a new Color for the player to perceive as the proper one for a background to be
     */
    public void setBackgroundColor(Color newColor){
        restingBackground = newColor;
    }

    private void updateBackground(){ // Max is about 15 or 16
        if (technicolorIndex > 0) { // Update the background color, if you did the supercheat.
            float r,g,b;
            r = 0; b = 0; g = 0;
            // RGB:  001 010 011 100 101 110 111
            //         1   2   3   4   5   6   7
            if ((technicolorIndex/4) % 2 > 0.){
                r = .5f;
            }
            if ((technicolorIndex/2) % 2 > 0){ // Uh
                g = .5f;
            }
            if (technicolorIndex % 2 > 0){ // On odds
                b = .5f;
            }
            technicolorIndex--;
            orgo.getWindow().txtArea.setBackground(new Color(r,g,b));
        }else if (hurtColor > 1){  // update the redness of the screen; more red = more recently hurt more
            int top = 5;
            if (hurtColor > top){
                hurtColor = top;
            }
            float eh = (float)(hurtColor-1)/top;
            Color c = new Color(1f - eh/2, 1f - eh, 1f - eh);
            orgo.getWindow().txtArea.setForeground(c);
            hurtColor--;
        }
        else {
            orgo.getWindow().txtArea.setBackground(restingBackground);
        }
    }
    /**
     * Tracker for Up up down down left right left right b a [whatever] cheat.
     * @param c character that was pressed
     */
    private void checkCheatProgress(char c){
        //System.out.println(superCheatProgress);
        if (superCheatProgress > 9) {
            // Yay!
            room.foodEaten += 42;
            technicolorIndex = 16;
            superCheatProgress = 0;
        }
        //System.out.println(c);
        switch (superCheatProgress){
            case 0:
                if (c == '©'){ superCheatProgress++; return; }
                break;
            case 1:
                if (c == '©'){ superCheatProgress++; return; }
                break;
            case 2:
                if (c == '®'){ superCheatProgress++; return; }
                break;
            case 3:
                if (c == '®'){ superCheatProgress++; return; }
                break;
            case 4:
                if (c == 'µ'){ superCheatProgress++; return; }
                break;
            case 5:
                if (c == 'æ'){ superCheatProgress++; return; }
                break;
            case 6:
                if (c == 'µ'){ superCheatProgress++; return; }
                break;
            case 7:
                if (c == 'æ'){ superCheatProgress++; return; }
                break;
            case 8:
                if (c == 'b'){ superCheatProgress++; return; }
                break;
            case 9:
                if (c == 'a'){ superCheatProgress++; return; }
                break;
        }
        //System.out.println("No!  You messed up.");
        superCheatProgress = 0;
    }

    private void shiftCamRight() {
        orgo.moveCam(1, 0);
    }

    private void shiftCamLeft() {
        orgo.moveCam(-1, 0);
    }

    private void shiftCamDown() {
        orgo.moveCam(0, 1);
    }

    private void shiftCamUp() {
        orgo.moveCam(0, -1);
    }

    public String getPrimarySpell() {
        return spell1.getIcon();
    }

    public void addItem(Item input) {
       inv.addItem(input);
    }

    public String getSecondarySpell() {
        return spell2.getIcon();
    }

    class PlayerTimerTask extends TimerTask{

        @Override
        public void run() {
            update();
        }
    }
}

/**
 * A listener class for keypresses, tailored to the Player.
 */
class MKeyListener extends KeyAdapter {
    private Player player;

    MKeyListener(Player p) {
        player = p;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (!player.frozen && !player.shouldPause && !player.dead) {
            char ch = event.getKeyChar();
            player.keyPressed(ch);
            if (event.getKeyCode() == KeyEvent.VK_UP) {
                //System.out.println("UP! Key codes: " + event.getKeyCode());
                player.keyPressed('©');
            }
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                //System.out.println("DOWN! Key codes: " + event.getKeyCode());
                player.keyPressed('®');
            }
            if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                //System.out.println("LEFT! Key codes: " + event.getKeyCode());
                player.keyPressed('µ');
            }
            if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                //System.out.println("RIGHT! Key codes: " + event.getKeyCode());
                player.keyPressed('æ');
            }
            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                //System.out.println("RIGHT! Key codes: " + event.getKeyCode());
                player.keyPressed('\'');

            }
        }if (player.dead){
            System.out.println("No, stop.  You're dead.");
        }
    }
}


