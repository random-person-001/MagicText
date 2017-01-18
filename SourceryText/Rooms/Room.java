/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package SourceryText.Rooms;

import SourceryText.Art;
import SourceryText.GameObjects.*;
import SourceryText.ImageOrg;
import SourceryText.Layer;
import SourceryText.SpecialText;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

import static java.lang.StrictMath.abs;

/**
 * @author Jared
 */
public class Room implements java.io.Serializable {
    public ImageOrg org;
    protected Art arty = new Art();
    public List<GameObject> objs = new ArrayList<>();
    private List<GameObject> addList = new ArrayList<>();
    private List<GameObject> removeList = new ArrayList<>();
    public List<Mortal> enemies = new ArrayList<>();
    public List<Player> players = new ArrayList<>();
    private boolean[][] objHitMesh;
    private boolean[][] baseHitMesh;
    public String strRoomName = "not set";
    private List<FlavorText> flavorTexts = new ArrayList<>();
    private List<FlavorText> messageQueue = new ArrayList<>();
    private ArrayList<GameObject> inspectables = new ArrayList<>(); // gameObjects that may move but have something to say
    private ArrayList<InteractableEnvironment> interactableEnvironmentObjects = new ArrayList<>(); // things that should be notified on a spell going somewhere

    public Player playo = null;

    public int roomWidth;
    public int roomHeight;
    public String exitCode = "";

    public boolean boundedCamera = false;

    protected boolean isPaused = false;
    private boolean resume = true;
    private boolean changingAnswer = false;
    private boolean[][] waterMesh;


    /**
     * Set room variables and stuff.
     *
     * @param player you know what
     */
    protected Room(Player player) {
        playo = player;
        // Make a new imageOrg for this level, looking at the same Window as the last one did
        org = new ImageOrg(player.org.getWindow());
        org.setDefaultPlayer(player.org.getDefaultPlayer());
        // So it doesn't draw over other things.  It'll be restarted as needed later.
        org.terminateClock();

        org.roomBackground = Color.BLACK;

        // Boring stuff
        roomHeight = 50;
        roomWidth = 50;

        Layer spells = new Layer(new String[roomHeight][roomWidth], "Spellz", true);
        org.addLayer(spells);
    }


    /**
     * @param username a String by which the desired Player identifies
     * @return the Player in the room if there is one with the username, else null
     */
    public Player getPlayer(String username) {
        for (Player p : players) {
            if (p.getUsername().equals(username)) {
                return p;
            }
        }
        return null;
    }

    /**
     * OVERRIDE THIS
     * Run a playerLoop, doing things, until the player should go to a new room.
     *
     * @param play
     * @return the room name to go to next
     */
    protected String playerLoop(Player play) {
        return "You were supposed to override this, dummy.";
    }

    /**
     * OVERRIDE THIS
     * Initialize hit meshes, set background layer, do generic room init here
     */
    public void startup() {
    }

    /**
     * OVERRIDE THIS
     * Add all of the items and enemies
     */
    public void addItems() {
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(Player player) {
        org.resetClock();
        setObjsPause(false);
        addMortal(player);
        players.add(player);
        player.restartTimer(); // I'm really not sure why, but the player didn't move until I reset its timer after switching levels.
        player.setRoom(this);
        player.frozen = false;
        player.setupForNewRoom();

        String exit = playerLoop(player);

        takeCareOfPlayerLeavingRoom(player, exit);
    }

    /**
     * Thing to be called at the end of enter() after things are done.  Needs work for multiplayer.
     *
     * @param player a Player that left
     * @param exit   the String of what room they're trying to go to now.
     */
    private void takeCareOfPlayerLeavingRoom(Player player, String exit) {
        exitCode = ""; // For next time!
        player.roomName = exit;
        removeMortal(player);
        players.remove(player);
        player.frozen = true;
        System.out.println(exit);
        System.out.println(exitCode);

        if (players.size() == 0) {
            System.out.println("No more players in " + strRoomName);
            org.terminateClock();
            setObjsPause(true);
        }
    }

    public void setNewRoom(String newID, Player player, int playerY, int playerX) {
        if (exitCode.equals("")) {
            removeFromObjHitMesh(player.getX(), player.getY());
            player.goTo(playerX, playerY);
            exitCode = newID;
        }
    }

    /**
     * Try to subtractHealth a mortal at a specified location
     *
     * @param x           the X coord of the possible mortal
     * @param y           the Y coord of the possible mortal
     * @param damage      how much to subtractHealth any mortals if they're there
     * @param killMessage if you are hurting a player, what do you want to be said upon their death?
     * @param attackName  name of the attack
     * @return whether there was an mortals there, and thus whether they got subtractHealth
     */
    public boolean hurtSomethingAt(int x, int y, int damage, String killMessage, boolean fromPlayer, String attackName) {
        for (Mortal e : enemies) {
            if (!fromPlayer) {
                if (e.getX() == x && e.getY() == y) {
                    e.subtractHealth(damage, killMessage, attackName);
                    return true;
                }
            } else if (e.getX() == x && e.getY() == y && !e.strClass.equals("Player")) {
                e.subtractHealth(damage, killMessage, attackName);
                return true;
            }
        }
        return false;
    }

    public boolean hurtSomethingAt(int x, int y, int damage, String killMessage, String damageType) {
        return hurtSomethingAt(x, y, damage, killMessage, false, damageType);
    }

    public boolean hurtSomethingAt(int x, int y, int damage, String killMessage){
        return hurtSomethingAt(x, y, damage, killMessage, "generic");
    }

    public boolean hurtSomethingAt(int x, int y, int damage){
        return hurtSomethingAt(x, y, damage, "You died!", "generic");
    }

    /**
     * A convenience method for discovering the number of a class are left
     *
     * @param className the String by which the class identifies (GameObject.strClass)
     * @return how many there are in the room's list
     */
    public int getCountOf(String className) {
        int count = 0;
        String finalCount = "[Room] Object Search for " + className +":";
        for (GameObject o : objs) {
            if (o.strClass.equals(className)) {
                count++;
            }
            finalCount += " \'" + o.strClass + "\'";
        }
        for (Mortal o : enemies) {
            if (o.strClass.equals(className)) {
                count++;
            }
            finalCount += " \'" + o.strClass + "\'";
        }
        //System.out.println(finalCount);
        return count;
    }

    /**
     * Are you a potion?  Do you want to speed up or slow down the game?  Call this weird method now!
     * @param intervalMultiplier a coefficient for targetUpdateInterval (the delay in the timer between update() calls)
     */
    public void changeTimerSpeedsBy(float intervalMultiplier){
        for (GameObject object : objs){
            object.setTimerToWeirdFrequency(intervalMultiplier);
        }
        for (Mortal morry : enemies){
            morry.setTimerToWeirdFrequency(intervalMultiplier);
        }
    }

    /**
     * A convenience method for discovering the number of a class are left
     *
     * @param className the String by which the class identifies (GameObject.strClass)
     * @return how many there are in the room's list
     */
    public int getMortalCountOf(String className) {
        int count = 0;
        for (Mortal o : enemies) {
            if (o.strClass.equals(className)) {
                count++;
            }
        }
        return count;
    }

    /**
     * A convenience method for getting all mortals of a specific name
     *
     * @param className the String by which the class identifies (GameObject.strClass)
     * @return how many there are in the room's list
     */
    public Mortal[] getMortalListOf(String className) {
        ArrayList<Mortal> output = new ArrayList<>();
        for (Mortal o : enemies) {
            if (o.strClass.equals(className)) {
                output.add(o);
            }
        }
        Mortal[] mortalOutput = new Mortal[output.size()];
        for (int ii = 0; ii < output.size(); ii++){
            mortalOutput[ii] = output.get(ii);
        }
        return mortalOutput;
    }

    /**
     * Colors all SpecialTexts in a layer that corresponds to FlavorText in room an bright aqua blue
     */

    protected void highlightFlavorText(Layer roomBase) {
        for (FlavorText txt : flavorTexts) {
            SpecialText toEdit = roomBase.getSpecTxt(txt.getY() + roomBase.getX(), txt.getX() + roomBase.getY());
            toEdit = new SpecialText(toEdit.getStr(), new Color(175, 235, 255), toEdit.getBackgroundColor());
            roomBase.setSpecTxt(txt.getY() + roomBase.getX(), txt.getX() + roomBase.getY(), toEdit);
        }
    }


    /**
     * Flush the buffers of things to be added and removed from the list of objects in the world.
     * Defaults to trying 20 times before giving up.  (Once when Riley was running it, it recursed 1019 times, causing
     * a StackOverflowError)
     */
    private void flushObjListChanges() {
        flushObjListChanges(20);
    }

    private void flushObjListChanges(int triesLeftBeforeGivingUp) {
        if (triesLeftBeforeGivingUp <= 0) {
            return;
        } else {
            triesLeftBeforeGivingUp--;
        }
        try {
            objs.addAll(addList);
            addList.clear();
            objs.removeAll(removeList);
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
        System.out.println("Changing room pause setting");
        flushObjListChanges();
        isPaused = set;
        try {
            for (GameObject obj : objs) {
                try {
                    if (set) obj.cancelTimer();
                    else obj.setupTimer();
                } catch (NullPointerException e) {
                    System.out.println("[Room.java: setObjsPause(): caught nullpointer!  Probably Not Good!");
                    e.printStackTrace();
                }
            }
            for (Mortal m : enemies) {
                try {
                    if (set) m.cancelTimer();
                    else m.setupTimer();
                } catch (NullPointerException e) {
                    System.out.println("[Room.java: setObjsPause(): caught nullpointer!  Probably Not Good!");
                    e.printStackTrace();
                }
            }
        } catch (ConcurrentModificationException ignore) { // Happens normally when an object is removed or added to the room
            System.out.println("Whoops, something weird! [Room.java: setObjsPuase(): caught a ConcurrentModificationException]");
        }
    }


    public void addObject(GameObject theObj) {
        addList.add(theObj);
        flushObjListChanges();
    }

    public void removeObject(GameObject obj) {
        removeList.add(obj);
        obj.cancelTimer();
        obj.selfCleanup();
    }

    /**
     * Add a new mortal to the room, and to the list of mortals.  As a bonus, it'll change the objectsHitMesh to
     * reflect that.
     *
     * @param newMortal a new Mortal
     */
    public void addMortal(Mortal newMortal) {
        enemies.add(newMortal);
        addToObjHitMesh(newMortal.getX(), newMortal.getY());
    }


    /**
     * Remove an Mortal from the world and enemy list, and make it so they no longer obstruct your way
     *
     * @param m the Mortal to be removed
     */
    public void removeMortal(Mortal m) {
        m.cancelTimer();
        m.selfCleanup();
        enemies.remove(m);
        removeFromObjHitMesh(m.getX(), m.getY());
    }

    /**
     * Makes a unique name for an object to use when making a new Layer.
     * <p>
     * Uses both time and random numbers to create a string that is very likely to be unique.
     * (0.000001% collision chance per object)
     */

    public String makeUniqueLayerName(String strClass) {
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
            return false;
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

    public void removeFromBaseHitMesh(int x, int y) {
        if (x<baseHitMesh[0].length && x >= 0 && y < baseHitMesh.length && y >= 0) {
            baseHitMesh[y][x] = false;
        } else {
            System.out.println("[Room] can't remove point " + x + " " + y + " from base hit mesh.  Size is " +
                    baseHitMesh[0].length + " by " + baseHitMesh.length);
        }
    }

    public void addToBaseHitMesh(int x, int y) {
        baseHitMesh[y][x] = true;
    }

    private void addToBaseHitMesh(String[][] picture, String solidChar, int x, int y) {
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[0].length; j++) {
                try {
                    if (picture[i][j].equals(solidChar)) {
                        addToBaseHitMesh(j + x, i + y);
                    }
                } catch (NullPointerException | ArrayIndexOutOfBoundsException ignored) {
                }
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
        for (int i = 0; i < waterMesh.length; i++) {
            for (int j = 0; j < waterMesh[0].length; j++) {
                waterMesh[i][j] = false;
            }
        }
    }

    /**
     * Initialize the hit meshes of the room.  Generally call this at the beginning of setting up a room, lest objects
     * try to define their location as solid before the arrays for solid things are set up (which is what this does).
     *
     * @param baseLayer The Layer whose size and dimension defines the room's boundaries
     */
    protected void initHitMeshes(Layer baseLayer) {
        waterMesh =  new boolean[baseLayer.getColumns()][baseLayer.getRows()]; // Flipped around, it looks easier
        baseHitMesh = new boolean[baseLayer.getRows()][baseLayer.getColumns()];
        objHitMesh = new boolean[baseLayer.getRows()][baseLayer.getColumns()];
        roomHeight = baseLayer.getRows();
        roomWidth = baseLayer.getColumns();
        emptyAllHitMeshes();
    }

    public void addWaterAt(int x, int y){
        if (x<waterMesh.length && x >= 0 && y < waterMesh[0].length && y >= 0) {
            waterMesh[x][y] = true;
        }
        else {
            System.out.println("[Room adding water] cannot add water at " + x + ", " + y +
                    "; maxes are " + waterMesh.length + " and " + waterMesh[0].length);
        }
    }

    public boolean checkForWater(int x, int y) {
        if (x<waterMesh.length && x >= 0 && y < waterMesh[0].length && y >= 0) {
            return waterMesh[x][y];
        }
        else {
            return false;
        }
    }

    protected void addMagicPotato(int x, int y) {
        Item magicTater = new Item("Magic Potato", "How lucky! This eccentric\n potato can permanently\n increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent!", "item");
        DroppedItem gTater = new DroppedItem(this, "You found a hidden magic potato!", magicTater, x, y);
        addObject(gTater);
    }

    public void onSpellAt(int x, int y, String type){
        for (InteractableEnvironment object : interactableEnvironmentObjects) {
            object.onSpellOver(x,y,type);
        }
    }

    public void addAnInteractableEnvironment(InteractableEnvironment ie){
        interactableEnvironmentObjects.add(ie);
    }

    /**
     * Initialize some universal layers and stuff.  Generally call this at the end of setting up a room, lest the
     * spells, player, and HUD layers be placed below others.
     */
    protected void genericRoomInitialize() {
    }

    /**
     * @param newPlayers a list of Players that the Room's 'players' variable should be set to
     */
    public void setPlayers(List<Player> newPlayers) {
        players = newPlayers;
    }

    /**
     * @return the first Player in the list of Players in the level
     */
    public Player getPlayer() {
        return players.get(0);
    }

    /**
     * Puts a message on the queue for display
     */
    public void queueMessage(FlavorText message, Player viewer) {
        viewer.messageQueue.add(message.setViewer(viewer));
        System.out.println("MESSAGE STACK SIZE: " + viewer.messageQueue.size());
        if (viewer.messageQueue.size() == 1) {
            viewer.messageQueue.get(0).output();
        }
    }

    /**
     * 'Plants' a message at a location, specified by the message given.
     *
     * @param thing
     */
    protected void plantText(FlavorText thing, int setX, int setY) {
        thing.x = setX;
        thing.y = setY;
        flavorTexts.add(thing);
    }

    /**
     * Clears out all 'planted' text
     */
    protected void clearPlantedText() {
        flavorTexts.clear();
    }

    /**
     * Mainly used by the player to check for nearby planted text
     */
    private void queryForText(int testX, int testY, Player user) {
        for (FlavorText text : flavorTexts) {
            text.textIfCorrectSpot(testX, testY, user);
        }
    }

    public void inspectAt(int testX, int testY, Player observer) {
        specialInspect(testX, testY, observer);
        queryForText(testX, testY, observer);
        for (GameObject object : inspectables){
            if (object.getX() == testX && object.getY() == testY){
                object.onInspect(observer);
            }
        }
    }

    /**
     * Override for special interactions within a room
     *
     * @param x         x loc of inspection
     * @param y         y loc of inspection
     * @param inspector Player who is inspecting
     */
    protected void specialInspect(int x, int y, Player inspector) {
    }

    /**
     * Draw a smallish text box at the bottom of the screen, waiting for enter to be pressed to dismiss it.
     *
     * @param text             a string, with appropriate newlines, to show
     * @param speaker          Who said it?  Do tell!
     * @param helpful          whether the box ought to give instructions on its own dismissal
     * @param user             if not null, show this to only the Player with that username.
     */
    private void compactTextBox(String text, String speaker, boolean helpful, Player user) {
        TextBox box = new TextBox(this, user, text, speaker);
        box.showTextBox(helpful);
    }

    private void questionTextBox(String text, String speaker, Player user, int qID) {
        TextBox question = new TextBox(this, user, text, speaker);
        question.showQuestionBox(qID);
    }

    /**
     * Override with custom code for each room that poses a question
     */
    public void respondToQuestion(int qID, Player respondTo) {
    }

    public void splashMessage(String message, String speaker, Player viewer) {
        queueMessage(new FlavorText(message, speaker), viewer);
    }

    public void splashQuestion(String message, Player viewer, int questionID) {
        queueMessage(new FlavorText(message, true, questionID), viewer);
    }

    public Player getClosestPlayerTo(int x, int y){
        int smallestDistance = 500;
        Player toReturn = null;
        for (Player play : players){
            int dist = Math.abs(play.getX() - x) + Math.abs(play.getY() - y);
            if (dist < smallestDistance){
                toReturn = play;
            }
            smallestDistance = dist;
        }
        return toReturn;
    }

    protected void genericKeyEvent (KeyEvent event){
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_RIGHT) {
            changingAnswer = true;
        }
    }

    public void fireKeyEvent(KeyEvent event) {
        genericKeyEvent(event);
    }

    /**
     * @param x x coord of query
     * @param y y coord of query
     * @return all registered GameObjects at a coordinate
     */

    public List<GameObject> getObjectsAt(int x, int y) {
        List<GameObject> toReturn = new ArrayList<>();
        for (GameObject object : objs){
            if (object.getX() == x && object.getY() == y){
                toReturn.add(object);
            }
        }
        for (Mortal object : enemies){
            if (object.getX() == x && object.getY() == y){
                toReturn.add(object);
            }
        }
        return toReturn;
    }

    /**
     * If you move around but still want to be inspectable by the player, call this method so we can index you!
     * @param gameObject
     */
    public void addInspectable(GameObject gameObject) {
        if (!inspectables.contains(gameObject)) {
            System.out.println("Adding a " + gameObject.strClass + " to list of inspectables.");
            inspectables.add(gameObject);
        }
    }

    public class FlavorText implements java.io.Serializable {
        Player player;
        String[] messages = {""};
        String speaker = "";
        int x;
        int y;

        boolean isHelpful = false;

        boolean isQuestion = false;
        int questionID = 0;

        public FlavorText(String[] theMessage, String theSpeaker) {
            messages = theMessage;
            speaker = theSpeaker;
        }

        public FlavorText(String theMessage, String theSpeaker) {
            String[] messageArray = new String[1];
            messageArray[0] = theMessage;
            messages = messageArray;
            speaker = theSpeaker;
        }

        public FlavorText(String theMessage, String theSpeaker, boolean helpful) {
            x = 0;
            y = 0;
            String[] messageArray = new String[1];
            messageArray[0] = theMessage;
            messages = messageArray;
            speaker = theSpeaker;
            isHelpful = helpful;
        }

        public FlavorText(String theMessage, boolean setQuestion, int qID) {
            x = 0;
            y = 0;
            String[] messageArray = new String[1];
            messageArray[0] = theMessage;
            messages = messageArray;
            isQuestion = setQuestion;
            questionID = qID;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        void textIfCorrectSpot(int testX, int testY, Player user) {
            if (x == testX && y == testY) {
                doMessage(user);
            }
        }

        void doMessage(Player user) {
            for (String message : messages) {
                //System.out.println("STACKING FOLLOWING MESSAGE:\n " + message);
                FlavorText panel = new FlavorText(message, speaker);
                panel.player = user;
                panel.isQuestion = isQuestion;
                panel.questionID = questionID;
                queueMessage(panel, user);
            }
        }

        public void output() {
            if (isQuestion)
                questionTextBox(messages[0], speaker, player, questionID);
            else
                compactTextBox(messages[0], speaker, isHelpful, player);
        }

        public FlavorText setViewer (Player viewer){
            player = viewer;
            return this;
        }
    }

}
