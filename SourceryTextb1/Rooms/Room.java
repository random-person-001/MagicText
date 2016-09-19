/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package SourceryTextb1.Rooms;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

import SourceryTextb1.*;
import SourceryTextb1.GameObjects.*;
import SourceryTextb1.Window;

import static java.awt.Color.*;

/**
 * @author Jared
 */
public class Room implements java.io.Serializable{
    public ImageOrg org;
    protected Art arty = new Art();
    public List<GameObject> objs = new ArrayList<>();
    private List<GameObject> addList = new ArrayList<>();
    private List<GameObject> removeList = new ArrayList<>();
    public List<Mortal> enemies = new ArrayList<>();
    public HashMap storedStuff = new HashMap<String, Integer>();
    private boolean[][] objHitMesh;
    private boolean[][] baseHitMesh;

    private List<FlavorText> flavorTexts = new ArrayList<>();
    private List<FlavorText> messageQueue = new ArrayList<>();

    public Player playo;


    public int foodEaten = 0;
    public int roomWidth;
    public int roomHeight;
    protected int index;
    public String exitCode = "";


    /**
     * OVERRIDE THIS
     * Run a loop, doing things, until the player should go to a new room.
     * @return the room name to go to next
     */
    protected String loop (){
        return "You were supposed to override this, dummy.";
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public String enter(){
        playo.frozen = false;
        String exit = loop();
        playo.frozen = true;
        System.out.println(exit);
        System.out.println(exitCode);
        if (!exit.equals("die")) {
            removeAllObjectsAndLayersButPlayer();
        }
        else { // in a moment, pause everything so the player sees their grave mistake
            timedCancelAfter(3000);
        }
        return exit;
    }

    /**
     * After a time, all objects' timers will cancel.
     * @param msDelay delay (ms) after which the room will generally stop
     */
    private void timedCancelAfter(int msDelay) {
        System.out.println("Hi.  I'm a timer");
        new Timer().schedule(new TimerTask() {
            public void run() {
                System.out.println("\n\nCANCELLING STUFFFFFF\n\n");
                for (GameObject o : objs){
                    removeObject(o);
                }
            }
        }, msDelay);
    }

    /**
     * Set room variables and stuff.
     * @param player you know what
     */
    protected void constructor(Player player){
        playo = player;
        org = player.orgo;
        roomHeight = org.getWindow().maxH();
        roomWidth = org.getWindow().maxW();
        index = 1;
    }


    protected void setNewRoom(String newID, int playerX, int playerY){
        if (exitCode.equals("")) {
            exitCode = newID;
            playo.goTo(playerX, playerY);
        }
    }

    /**
     * Try to subtractHealth a mortal at a specified location
     *
     * @param x           the X coord of the possible mortal
     * @param y           the Y coord of the possible mortal
     * @param damage      how much to subtractHealth any mortals if they're there
     * @param killMessage if you are hurting a player, what do you want to be said upon their death?
     * @return whether there was an mortals there, and thus whether they got subtractHealth
     */
    public boolean hurtSomethingAt(int x, int y, int damage, String killMessage) {
        for (Mortal e : enemies) {
            if (e.getX() == x && e.getY() == y) {
                e.subtractHealth(damage, killMessage);
                return true;
            }
        }
        return false;
    }

    /**
     * A convenience method for discovering the number of a class are left
     *
     * @param className the String by which the class identifies (GameObject.strClass)
     * @return how many there are in the room's list
     */
    public int getCountOf(String className) {
        int count = 0;
        for (GameObject o : objs) {
            if (o.strClass.equals(className)) {
                count++;
            }
        }
        return count;
    }

    /**
     * General work to be done at the end of a level.  Cleans layers for exit, cancels the timers of everything but the
     * Player, and clears the list of objects.
     */
    private void removeAllObjectsAndLayersButPlayer(){
        cleanLayersForExit(org);
        for (GameObject o : objs){
            if (!o.strClass.equals("Player")) {
                o.cancelTimer();
                o.selfCleanup();
            }
        }
        objs.clear();
    }

    /**
     * Flush the buffers of things to be added and removed from the list of objects in the world.
     * Defaults to trying 20 times before giving up.  (Once when Riley was running it, it recursed 1019 times, causing
     * a StackOverflowError)
     */
    private void flushObjListChanges() {
        flushObjListChanges(20);
    }
    private void flushObjListChanges(int triesLeftBeforeGivingUp){
        if (triesLeftBeforeGivingUp <= 0){
            return;
        }
        else{
            triesLeftBeforeGivingUp--;
        }
        try {
            objs.addAll(addList);
            for (GameObject obj : removeList) {
                objs.remove(obj);
            }
            addList.clear();
            removeList.clear();
        } catch (ConcurrentModificationException ignore) {
            flushObjListChanges(triesLeftBeforeGivingUp); // Try again
            System.out.println("ConcurrentModExc in Room.java:flushObjListChanges() ; trying again");
        }
    }

    /**
     * Pause or unpause every object in the room
     *
     * @param set whether to pause (true) or unpause (false)
     */
    public void setObjsPause(boolean set) {
        flushObjListChanges();
        try {
            for (GameObject obj : objs) {
                try {
                    obj.setPause(set);
                    //objManifest += obj.strClass + ", ";
                } catch (NullPointerException e) {
                    System.out.println("[Room.java: setObjsPause(): caught nullpointer!  Probably Not Good!");
                    e.printStackTrace();
                }
                //System.out.println("OBJS PAUSED: " + objManifest + "\n");
            }
        } catch (ConcurrentModificationException ignore) { // Happens normally when an object is removed or added to the room
            System.out.println("Whoops, something weird! [Room.java: setObjsPuase(): caught a ConcurrentModificationException]");

        }
    }


    public void addObject(GameObject theObj) {
        addList.add(theObj);
    }

    /**
     * Don't want all that gunk of layering to clog up the next level!
     *
     * @param org imageOrg
     */
    private void cleanLayersForExit(ImageOrg org) {
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        org.compileImage();
        org.getWindow().clearImage();
    }

    public void removeObject(GameObject obj) {
        removeList.add(obj);
        obj.cancelTimer();
        obj.selfCleanup();
    }

    /**
     * Make a HUD and its layer and plop them on.  You probably want to look in to using
     * <code>genericRoomInitialize()</code> instead.
     *
     * @param org the ImageOrg
     */
    private void addHUD(ImageOrg org) {  //Fixes redundancy;
        Layer HUDd = new Layer(new String[1][70], "HUD", false, true);
        HUDd.setImportance(true);
        org.addLayer(HUDd);
        HUD hud = new HUD(org, this, HUDd);
        addObject(hud);
    }

    /**
     * Adds a dropped item to the room, but only if it hasn't been taken previously.
     */
    public String ownID = "";

    public void addItem(DroppedItem toAdd){
        addObject(toAdd);
        //System.out.println(playo.tracker.alreadyTaken(toAdd.getX(),toAdd.getY(),ownID));
        if (playo.tracker.alreadyTaken(toAdd.getX(),toAdd.getY(),ownID)) {
            removeObject(toAdd);
        }
        //playo.tracker.addLoc(toAdd.getX(),toAdd.getY(),ownID);
        //System.out.printf("[TRACKER] Item a success @ (%1$d,%2$d,%3$s)\n", toAdd.getX(),toAdd.getY(),ownID);
    }

    /**
     * Add a new mortal to the room, and to the list of mortals.  As a bonus, it'll change the objectsHitMesh to
     * reflect that.
     *
     * @param newMortal a new Mortal
     */
    public void addMortal(Mortal newMortal) {
        addObject(newMortal);
        enemies.add(newMortal);
        addToObjHitMesh(newMortal.getX(), newMortal.getY());
    }


    /**
     * Remove an Mortal from the world and enemy list, and make it so they no longer obstruct your way
     *
     * @param m the Mortal to be removed
     */
    public void removeMortal(Mortal m) {
        removeObject(m);
        enemies.remove(m);
        removeFromObjHitMesh(m.getX(), m.getY());
    }

    /**
     * Makes a unique name for an object to use when making a new Layer.
     *
     * Uses both time and random numbers to create a string that is very likely to be unique.
     * (0.000001% collision chance per object)
     */

    public String makeUniqueLayerName(String strClass){
        String output = strClass;
        output += ":";
        output += String.valueOf(Math.round(Math.random() * 1000));
        output += "-";
        output += String.valueOf(System.nanoTime() % 1000);
        return output;
    }

    /**
     * Is it a wall I walk into?  Find out now!
     *
     * @param x a specified X coord
     * @param y a particular Y coord
     * @return whether either an object is sitting there OR a wall is there; ie if that place is solid
     */
    public boolean isPlaceSolid(int x, int y) {
        if ((x >= 0 && x <= objHitMesh[0].length - 1) && (y >= 0 && y <= objHitMesh.length - 1)) { // Buffer of 1 for room walls
            return objHitMesh[y][x] || baseHitMesh[y][x];
        } else { // Outside wall
            return true;
        }
    }

    /**
     * Tell the room that somewhere in the hit mesh of objects, there is a solid place.  Useful for making enemies not
     * able to walk through later
     *
     * @param x x coord in room
     * @param y y coord in room
     */
    public void addToObjHitMesh(int x, int y) {
        if ((x > 0 && x < objHitMesh[0].length - 1) && (y > 0 && y < objHitMesh.length - 1)) { // Buffer of 1 for room walls
            objHitMesh[y][x] = true;
        }
    }

    private void addToObjHitMesh(String[][] picture, String solidChar, int x, int y) {
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[0].length; j++) {
                if (picture[i][j].equals(solidChar)) {
                    addToObjHitMesh(j + x, i + y);
                }
            }
        }
    }

    protected void addToObjHitMesh(String[][] picture, String[] solidChars, int x, int y) {
        for (String solid : solidChars) {
            addToObjHitMesh(picture, solid, x, y);
        }
    }

    protected void clearObjHitMesh() {
        for (int i = 0; i < objHitMesh.length; i++) {
            for (int ii = 0; ii < objHitMesh[0].length; ii++) {
                objHitMesh[i][ii] = false;
            }
        }
    }

    /**
     * Unset a location in the hit mesh of objects.  Useful for making Player able to walk through where an enemy hath
     * tread.
     *
     * @param x x coord in room
     * @param y y coord in room
     */
    public void removeFromObjHitMesh(int x, int y) {
        if ((x > 0 && x < objHitMesh[0].length - 1) && (y > 0 && y < objHitMesh.length - 1)) { // Buffer of 1 for room walls
            objHitMesh[y][x] = false;
        }
    }

    protected void addToBaseHitMesh(String[][] picture, String[] solidChars) {
        for (String solid : solidChars) {
            addToBaseHitMesh(picture, solid, 0, 0);
        }
    }

    protected void addToBaseHitMesh(String[][] picture, String[] solidChars, int x, int y) {
        for (String solid : solidChars) {
            addToBaseHitMesh(picture, solid, x, y);
        }
    }

    protected void removeFromBaseHitMesh(int x, int y) {
        baseHitMesh[y][x] = false;
    }

    protected void addToBaseHitMesh(int x, int y) {
        baseHitMesh[y][x] = true;
    }

    void addToBaseHitMesh(String[][] picture, String solidChar) {
        addToBaseHitMesh(picture, solidChar, 0, 0);
    }

    private void addToBaseHitMesh(String[][] picture, String solidChar, int x, int y) {
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[0].length; j++) {
                try {
                    if (picture[i][j].equals(solidChar)) {
                        addToBaseHitMesh(j + x, i + y);
                    }
                }
                catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {}
            }
        }
    }

    private void emptyAllHitMeshes() {
        for (int i = 0; i < baseHitMesh.length; i++) {
            for (int j = 0; j < baseHitMesh[0].length; j++) {
                baseHitMesh[i][j] = false;
            }
        }
        for (int i = 0; i < objHitMesh.length; i++) {
            for (int j = 0; j < objHitMesh[0].length; j++) {
                objHitMesh[i][j] = false;
            }
        }
    }

    /**
     * Initialize the hit meshes of the room.  Generally call this at the beginning of setting up a room, lest objects
     * try to define their location as solid before the arrays for solid things are set up (which is what this does).
     */
    protected void ititHitMeshes() {
        baseHitMesh = new boolean[roomHeight][roomWidth];
        objHitMesh = new boolean[roomHeight][roomWidth];
        emptyAllHitMeshes();
    }

    /**
     * Initialize some universal layers and stuff.  Generally call this at the end of setting up a room, lest the
     * spells, player, and HUD layers be placed below others.
     */
    protected void genericRoomInitialize() {
        Layer spells = new Layer(new String[roomHeight][roomWidth], "Spellz", true);
        org.addLayer(spells);

        playo.castingLayer = spells;
        playo.setupForNewRoom();

        addMortal(playo);

        addHUD(org);
    }

    public Player getPlayer() {
        return playo;
    }

    /**
     * Puts a message on the queue for display
     */
    protected void queueMessage(FlavorText message){
        messageQueue.add(message);
        System.out.println("MESSAGE STACK SIZE: " + messageQueue.size());
        if (messageQueue.size() == 1){
            messageQueue.get(0).output();
        }
    }

    /**
     * 'Plants' a message at a location, specified by the message given.
     * @param thing
     */
    protected void plantText(FlavorText thing){ flavorTexts.add(thing); }

    /**
     * Mainly used by the player to check for nearby planted text
     */
    public void queryForText(int testX, int testY){
        for (FlavorText text : flavorTexts){
            text.textIfCorrectSpot(testX, testY);
        }
    }

    /**
     * Draw a smallish text box at the bottom of the screen, waiting for enter to be pressed to dismiss it.
     *
     * @param org     image organizer
     * @param text    a string, with appropriate newlines, to show
     * @param speaker Who said it?  Do tell!
     * @param helpful whether the box ought to give instructions on its own dismissal
     */
    public void compactTextBox(ImageOrg org, String text, String speaker, boolean helpful) {
        Art artsedo = new Art();
        Layer txtBox;
        if (helpful) {
            txtBox = new Layer(Art.strToArray(artsedo.textBoxHelpful), "Dialog", 13, 0, false, true);
        } else {
            txtBox = new Layer(Art.strToArray(artsedo.textBox), "Dialog", 13, 0, false, true);
        }

        for (int ii = 0; ii < speaker.length(); ii++) {
            txtBox.setStr(0, ii + 2, String.valueOf(speaker.charAt(ii)));
        }

        if (speaker.length() != 0) {
            txtBox.setStr(0, speaker.length() + 2, ":");
        }

        int line = 1;
        int newLineAdjust = 0;
        for (int ii = 0; ii < text.length(); ii++) {
            if (text.charAt(ii) == '\n') {
                line++;
                newLineAdjust = ii + 1;
                System.out.println("Found newline.");
            } else {
                txtBox.setStr(line, ii + 1 - newLineAdjust, String.valueOf(text.charAt(ii)));
            }
        }

        setObjsPause(true);
        org.addLayer(txtBox);

        Window window = org.getWindow();
        Dismissal keyListener = new Dismissal();
        keyListener.resume = false;
        window.txtArea.addKeyListener(keyListener); // Add key listeners.

        //System.out.println(text);

        Timer listenTick = new Timer();
        TextBoxListener listen = new TextBoxListener(keyListener, window);
        listenTick.scheduleAtFixedRate(listen, 100, 100);

        /**/
    }

    /**
     *
     */

    public void splashMessage(String message, String speaker){
        queueMessage(new FlavorText(message, speaker));
    }

    public void startup() {
    }

    public class FlavorText implements java.io.Serializable {

        String[] messages = {""};
        String speaker =  "";
        int x;
        int y;

        boolean isHelpful = false;

        public FlavorText(int xLoc, int yLoc, String[] theMessage, String theSpeaker){
            x = xLoc;
            y = yLoc;
            messages = theMessage;
            speaker = theSpeaker;
        }

        public FlavorText(int xLoc, int yLoc, String theMessage, String theSpeaker){
            x = xLoc;
            y = yLoc;
            String[] messageArray = new String[1];
            messageArray[0] = theMessage;
            messages = messageArray;
            speaker = theSpeaker;
        }

        public FlavorText(String theMessage, String theSpeaker, boolean helpful){
            x = 0;
            y = 0;
            String[] messageArray = new String[1];
            messageArray[0] = theMessage;
            messages = messageArray;
            speaker = theSpeaker;
            isHelpful = helpful;
        }

        public FlavorText(String theMessage, String theSpeaker){
            x = 0;
            y = 0;
            String[] messageArray = new String[1];
            messageArray[0] = theMessage;
            messages = messageArray;
            speaker = theSpeaker;
        }

        public int getX(){
            return x;
        }

        public int getY(){
            return y;
        }

        void textIfCorrectSpot(int testX, int testY){
            if (x == testX && y == testY){
                doMessage();
            }
        }

        void doMessage(){
            for (String message : messages) {
                //System.out.println("STACKING FOLLOWING MESSAGE:\n " + message);
                FlavorText panel = new FlavorText(x, y, message, speaker);
                queueMessage(panel);
            }
        }

        void output(){
            compactTextBox(org, messages[0], speaker, isHelpful);
        }

    }

    private class TextBoxListener extends TimerTask {

        Dismissal listener;
        Window window;

        TextBoxListener(Dismissal dismiss, Window windouw){
            listener = dismiss;
            window = windouw;
        }

        public void run(){
            if (listener.resume){
                setObjsPause(false);
                org.removeLayer("Dialog");
                window.txtArea.removeKeyListener(listener);
                cancel();
                if (messageQueue.size() > 0){
                    messageQueue.remove(messageQueue.get(0));
                }
                if (messageQueue.size() >= 1){
                    messageQueue.get(0).output();
                }
            }
        }
    }


}


/**
 * A selector thing for listening to and selecting one of the few options on the Options menu of the oldish pause screen
 */
class OptionsSelector extends KeyAdapter implements java.io.Serializable {
    private ImageOrg org;
    boolean resume = false;
    private int ii = 0;

    OptionsSelector(ImageOrg o) {
        org = o;
    }


    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (ch == 'i') {
            System.out.println("I was pressed.");
            int i = Font.PLAIN;
            ii++;
            if (ii > 2) {
                ii = 0;
            }
            if (ii == 0) {
                i = Font.PLAIN;
            } else if (ii == 1) {
                i = Font.ITALIC;
            } else if (ii == 2) {
                i = Font.BOLD;
            }
            String font = "Monospaced";
            Font f = new Font(font, i, 15);
            org.getWindow().txtArea.setFont(f);
        }
        if (ch == 'c') {
            System.out.println("c was pressed.");
            Color c = org.getWindow().txtArea.getBackground();

            if (c.equals(BLACK)) {
                org.getWindow().txtArea.setBackground(DARK_GRAY);

            } else if (c.equals(DARK_GRAY)) {
                org.getWindow().txtArea.setBackground(LIGHT_GRAY);

            } else if (c.equals(LIGHT_GRAY)) {
                org.getWindow().txtArea.setBackground(GREEN);

            } else if (c.equals(GREEN)) {
                org.getWindow().txtArea.setBackground(CYAN);

            } else if (c.equals(CYAN)) {
                org.getWindow().txtArea.setBackground(BLACK);
            }
        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}

/**
 * A little key listener that will tell you when you press ESCAPE or ENTER, by setting its <code>pause</code> boolean
 * to true
 */
class Dismissal extends KeyAdapter implements java.io.Serializable {
    boolean resume = false;

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}
