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
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import static java.lang.Math.abs;

/**
 * Player-controlled protagonist
 *
 * @author Riley
 */
public class Player extends GameObject {
    private MKeyListener playerKeyListener = new MKeyListener(this);
    private GameObject closestFood = null;
    private ImageOrg org;
    private Room room;
    public String layerName = "playerLayer";
    public int x = 10;
    public int y = 10;
    private int celeCount = 0;
    private boolean s1 = true; //toggler for celeb anim
    private String state1 = "+";
    private String state2 = "X";
    private boolean autonomous = false;
    public boolean shouldPause = false;
    public boolean frozen = true; //This gets changed by a room upon beginning the level

    //Convenience variables
    private final int UP = 0;
    private final int DOWN = 1;
    private final int LEFT = 2;
    private final int RIGHT = 3;

    private int orientation = UP;
    boolean orientationLocked = false;

    private Layer aimDispLayer;
    private String aimDispName = "aimDisp";

    public Layer castingLayer;

    private int superCheatProgress = 0;
    private Color restingBackground = Color.black;

    //STATS

    public int baseAttack = 3;
    public int baseDefend = 3;

    public int maxMana = 20;
    public int mana = maxMana;
    public int manaRegen = 10; //Mana per second
    private int manaRegenClock = 0;
    //private int manaWaitClock = 0;
    public int manaWaitStat = 2000; //Waiting before restoring mana
    public int manaWait = 0;

    //NO MORE STATS

    public boolean dead = false;
    public Map<String, Integer> inventory; // A dict-like thing for an inventory!
    private int technicolorIndex = 0;
    private boolean shouldInventory = false;
    private String primarySpell = "Book";
    private String secondarySpell = "None";

    /**Initialize a whole lotta variables.
     * @param theOrg the ImageOrg(anizer)
     */
    public Player(ImageOrg theOrg) {
        super.strClass = "Player";
        org = theOrg;
        Layer playerLayer = new Layer(new String[org.getWindow().maxH()][org.getWindow().maxW()], layerName);
        setupForNewRoom();
        org.addLayer(playerLayer);

        Window window = org.getWindow();
        window.txtArea.addKeyListener(playerKeyListener); // Add key listeners.

        inventory = new HashMap<String, Integer>(); // I have no idea why this can't go at a class-level with the declaration.
        inventory.put("spoon", 0);
        inventory.put("molotov", 0);
        inventory.put("rocketBoots", 0);
        inventory.put("longEnoughLever", 0);
        inventory.put("mashedPotatoes", 0);
        inventory.put("food", 0);
        inventory.put("WoodStaff", 0);
        inventory.put("Book", 1);
    }

    public void setPrimarySpell (String spell){
        primarySpell = spell;
    }
    public void setSecondarySpell (String spell){
        secondarySpell = spell;
    }

    public void setupForNewRoom(){
        aimDispLayer = new Layer(new String[org.getWindow().maxH()][org.getWindow().maxW()], aimDispName);
        org.setCam(x - 22, y - 8);
        org.addLayer(aimDispLayer);
    }

    public void centerCamera(){
        org.setCam(x - 22, y - 8);
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
    public void goTo(int newX, int newY) {
        org.editLayer(" ", layerName, y, x);
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
            room.pause(org);
            shouldPause = false;
        }else if (shouldInventory){
            //org.getWindow().removeKeyListener(playerKeyListener);
            Inventory inv = new Inventory(org, this);
            inv.show();
            //org.getWindow().addKeyListener(playerKeyListener);
            shouldInventory = false;
        }
        if (frozen) {
            try {
                org.editLayer(" ", layerName, y, x);
            } catch (IndexOutOfBoundsException ignored) {
            }
            return;
        }

        manaRegenClock += getTime();
            //manaWaitClock += getTime();

        if (manaWait > 0) {
            manaWait -= getTime();
            manaRegenClock = 0;
        } else if (manaRegenClock >= (1000 / manaRegen) && mana < maxMana) {
            mana++;
            manaRegenClock = 0;
        }

        //System.out.println(getTime());

        resetTime();

        if (celeCount > 0) { // Celebrate
            celeCount--;
            s1 = !s1;
            org.editLayer((s1) ? state1 : state2, layerName, y, x);
        } else {
            graphicUpdate();
            aimDispUpdate();
            //reportPos();
        }
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
        updateBackground();
    }

    private void aimDispUpdate(){
        int editAt = org.getPosLayer(aimDispName);
        if (editAt > -1) {  //Basically, if aimDispLayer != null
            org.getLayer(editAt).clear();
            if (orientationLocked) {
                switch (orientation) {
                    case UP:
                        org.editLayer("+", editAt, y - 1, x);
                        break;
                    case DOWN:
                        org.editLayer("+", editAt, y + 1, x);
                        break;
                    case LEFT:
                        org.editLayer("+", editAt, y, x - 1);
                        break;
                    case RIGHT:
                        org.editLayer("+", editAt, y, x + 1);
                        break;
                    default:
                        System.out.println("No valid orientation? IMPOSSIBLE");
                }
            }
        }
    }

    private void reoprtPos(){
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
     * Hurt the player.  This will make the game freeze a little, the player flicker, and the text on the screen
     * to flicker between red and white.
     */
    public void hurt() {
        for (int i = 0; i < 2; i++) {
            try {
                org.getWindow().txtArea.setForeground(Color.RED);
                org.editLayer(" ", layerName, y, x);
                org.compileImage();
                Thread.sleep(200);
                graphicUpdate();
                org.getWindow().txtArea.setForeground(Color.WHITE);
                Thread.sleep(200);
                org.compileImage();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * Start a celebration, now that a food has been eaten
     */
    public void celebrate() {
        inventory.put("food", 1 + inventory.get("food"));
        celeCount = 5;
    }

    private String smallChar = "x";
    private String largeChar = "@";
    private boolean big = false;

    /**
     * Update the Player symbol
     */
    public void graphicUpdate() {
        org.editLayer((big) ? smallChar : largeChar, layerName, y, x);

    }

    private void move(int direction) {
        try {
            org.editLayer(" ", layerName, y, x);
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
        org.setCam(x - 22, y - 8);
        graphicUpdate();
    }

    /** Handler for keypresses, and delegates appropriate actions based off them.  Note that this does not necessarily
     * align with the game clock, or Update() method.
     * @param key a character that was pressed on the leopard
     */
    void keyPressed(char key) {
        if (shouldPause) {
            System.out.println("No!");
            return;
        }
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
            case 'q':
                shiftCamLeft();// cam left
                break;
            case 'k': //Skip to next
            case 'e':
                shiftCamRight(); // cam right
                break;
            case 't':
                shiftCamUp();// cam left
                break;
            case 'g':
                shiftCamDown();// cam left
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
            case 's':
                castSpell(primarySpell);
                break;
            case 'd':
                castSpell(secondarySpell);
                break;
            case 'a':
                orientationLocked = !orientationLocked;
                break;
            case 'w':
                shouldInventory = true;
            default:
                System.out.print(key);
        }
        graphicUpdate();
        org.setCam(x - 22, y - 8);
        checkCheatProgress(key);
    }


    private void castSpell(String spellName){
        if (mana > 1) {
            if (spellName.equals("Book") && inventory.get("Book") > 0) {
                room.addObject(new Spell(org, room, castingLayer, x, y, orientation, "Book"));
                inventory.put("Book",0);
            }
            if (spellName.equals("Spark")) {
                room.addObject(new Spell(org, room, castingLayer, x, y, orientation, "Spark"));
                manaWait = manaWaitStat;
                mana -= 2;
            }
            if (spellName.equals("Flame")) {
                room.addObject(new Spell(org, room, castingLayer, x, y, orientation, "Flame"));
                manaWait = manaWaitStat;
                mana -= 3;
            }
            System.out.println("Unrecognised spell or you already threw your book from inventory");
        }
        else{
            System.out.println("Not enough mana or you don't have a staff yet!");
        }
    }
    /**
     * @param newColor a new Color for the player to perceive as the proper one for a background to be
     */
    public void setBackgroundColor(Color newColor){
        restingBackground = newColor;
    }

    private void updateBackground(){ // Max is about 15 or 16
        if (technicolorIndex > 0) {
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
            org.getWindow().txtArea.setBackground(new Color(r,g,b));
        }
        else {
            org.getWindow().txtArea.setBackground(restingBackground);
        }
    }
    /**
     * Tracker for Up up down down left right left right b a [whatever] cheat.
     * @param c character that was pressed
     */
    private void checkCheatProgress(char c){
        //System.out.println(superCheatProgress);
        if (superCheatProgress > 9){
            // Yay!
            room.foodEaten += 42;
            technicolorIndex = 16;
            superCheatProgress = 0;
        }
        switch (superCheatProgress){
            case 0:
                if (c == 'w'){ superCheatProgress++; return; }
                break;
            case 1:
                if (c == 'w'){ superCheatProgress++; return; }
                break;
            case 2:
                if (c == 's'){ superCheatProgress++; return; }
                break;
            case 3:
                if (c == 's'){ superCheatProgress++; return; }
                break;
            case 4:
                if (c == 'a'){ superCheatProgress++; return; }
                break;
            case 5:
                if (c == 'd'){ superCheatProgress++; return; }
                break;
            case 6:
                if (c == 'a'){ superCheatProgress++; return; }
                break;
            case 7:
                if (c == 'd'){ superCheatProgress++; return; }
                break;
            case 8:
                if (c == 'b'){ superCheatProgress++; return; }
                break;
            case 9:
                if (c == 'a'){ superCheatProgress++; return; }
                break;
        }
        superCheatProgress = 0;
    }

    public void reportPos() {
        System.out.println("X: " + x + "  Y: " + y);
    }

    private void shiftCamRight() {
        org.moveCam(1, 0);
    }

    private void shiftCamLeft() {
        org.moveCam(-1, 0);
    }

    private void shiftCamDown() {
        org.moveCam(0, 1);
    }

    private void shiftCamUp() {
        org.moveCam(0, -1);
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
        if (!player.frozen && !player.shouldPause) {
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
        }
    }
}
