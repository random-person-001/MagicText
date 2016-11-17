/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import SourceryTextb1.GameObjects.Player;

import java.awt.*;
import java.util.*;

/**
 * An intermediate between the countless Layers and the Window, to organise and keep all the Layers in line.
 * There is probably only one of these.
 *
 * @author 119184
 */
public class ImageOrg implements java.io.Serializable {
    private Window window;
    private ArrayList<Layer> layers = new ArrayList<>();
    private ArrayList<Layer> importantLayers = new ArrayList<>();

    private ArrayList<Layer> operationList1 = new ArrayList<>();
    private ArrayList<Layer> operationList2 = new ArrayList<>();
    private boolean layerOpLock = false;
    private boolean useOpList2 = false;

    private int camX = 0;
    private int camY = 0;
    private boolean debugGame = false;

    public Color roomBackground = Color.BLACK;

    //Fabulous colors!
    private Color[] fabulousColorWheel = {new Color(255, 100, 100), new Color(255, 100, 255), new Color(100, 100, 255), new Color(100, 255, 255),
            new Color( 80, 255, 120), new Color(255, 255, 100), new Color(255, 150,  75)};

    //FrameTimer frameTimerInstance = new FrameTimer();
    private Timer drawTimer = new Timer();

    final int orgSerial = (int) (Math.random() * 10000);
    private String owningPlayerUsername = null;

    private Player defaultPlayer = null; // Will be changed when enter

    public ImageOrg(Window game) {
        //drawTimer.scheduleAtFixedRate(frameTimerInstance, 0, 50); //20 fps lock
        resetClock();
        window = game;
    }

    /**
     * Set the default player, ie the player that the ImageOrg will build for in its timer
     * @param player
     */
    public void setDefaultPlayer(Player player){
        defaultPlayer = player;
    }

    public void setOwningPlayerUsername(String newOwningPlayerUsername){
        owningPlayerUsername = newOwningPlayerUsername;
    }

    public ArrayList<Layer> getLayers(){
        ArrayList<Layer> result = (ArrayList<Layer>)layers.clone();
        result.addAll(importantLayers);
        return result;
    }

    public void resetClock() {
        System.out.println("[ImageOrg] Timer restarted, layer op's: (1) " + operationList1 + " (2) " + operationList2);
        doLayerOperations();
        printLayers();
        try {
            drawTimer.cancel();
        } catch (NullPointerException ignore) {
        }
        drawTimer = new Timer();
        drawTimer.scheduleAtFixedRate(new FrameTimer(), 0, 50); //20 fps lock
    }

    public void terminateClock() {
        if (drawTimer != null) {
            drawTimer.cancel();
            drawTimer.purge();
            drawTimer = null;
        }
    }

    /**
     * Add a layer onto the top, or somewhere near it if there's more important things
     *
     * @param lay a Layer to be known about by everything
     */
    public void addLayer(Layer lay) {
        while(layerOpLock){
            try{
                Thread.sleep(5);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        addOp(lay, "add");
    }

    public boolean getDebug() {
        return debugGame;
    }

    /**
     * Return the Layer specified by its name
     *
     * @param layerName the Layer's name
     * @return that Layer
     */
    public Layer getLayer(String layerName) {
        while(layerOpLock){
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer lay1 = layers.get(i);
            if (lay1 != null && lay1.getName().equals(layerName)) {
                return lay1;
            }
        }
        for (int i = importantLayers.size() - 1; i >= 0; i--) {
            Layer lay2 = importantLayers.get(i);
            if (lay2 != null && lay2.getName().equals(layerName)) {
                
                return lay2;
            }
        }
        return null;
    }

    private void addOp(Layer op, String type) {
        //System.out.println("Adding layer " + op.getName());
        op.imageOrgOperation = type;
        if (!useOpList2){
            operationList1.add(op);
        } else {
            operationList2.add(op);
        }
    }

    /**
     * Remove a layer by its String name.
     *
     * @param layerName what the Layer's name is
     */
    public void removeLayer(String layerName) {
        boolean layerFound = false;
        
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer get = layers.get(i);
            if (get.nameMatches(layerName)) {
                addOp(get, "remove");
                layerFound = true;
            }
        }
        for (int i = importantLayers.size() - 1; i >= 0; i--) {
            Layer get = importantLayers.get(i);
            if (get.nameMatches(layerName)) {
                get.imageOrgOperation = "remove";
                addOp(get, "remove");
                layerFound = true;
            }
        }
        
        if (!layerFound)
            System.out.printf("Layer not found for remove: \"%1$s\"\n", layerName);
    }

    public void removeLayer(Layer layer) {
        removeLayer(layer.getName());
    }

    /**
     * Change the importance of a layer by its String name.
     *
     * @param layerName what the Layer's name is
     */
    public void setLayerImportance(String layerName, boolean set) {
        boolean layerFound = false;
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer get = layers.get(i);
            if (get.nameMatches(layerName)) {
                get.setImportance(set);
                addOp(get, "importance");
                layerFound = true;
            }
        }
        for (int i = importantLayers.size() - 1; i >= 0; i--) {
            Layer get = importantLayers.get(i);
            if (get.nameMatches(layerName)) {
                get.setImportance(set);
                addOp(get, "importance");
                layerFound = true;
            }
        }
        
        if (!layerFound)
            System.out.printf("Layer not found for importance change: \"%1$s\"\n", layerName);
    }

    /**
     * Adds and removes all layers requested in a stack-like fashion.
     */
    private void doLayerOperations(){
        layerOpLock = true;
        ArrayList<Layer> operationList;
        if (!useOpList2){
            operationList = operationList1;
        } else {
            operationList = operationList2;
        }

        useOpList2 = !useOpList2;

        boolean doOutput = operationList.size() > 0;  //Stuff for the extremely useful output of layer changes
        String opManifest = String.format("- Org -\n[%1$d] Layer Op's", layerChangeInstance);

        for (Layer op : operationList){ //Now for the actual layer operations; works like a stack
            switch(op.imageOrgOperation){
                case "add": //If the layer in question should be added
                    if (op.getImportance() && !importantLayerExists(op.getName())) {
                        System.out.println("SIR, we have a new refined guest, called " + op.getName());
                        importantLayers.add(op);
                    }
                    else if (!op.getImportance() && !unimportantLayerExists(op.getName())) {
                        layers.add(op);
                    }
                    opManifest += (String.format(": + %1$s ", op.getName()));
                    break;
                case "remove": //If the layer in question shouuld be removed
                    layers.remove(op);
                    importantLayers.remove(op);
                    opManifest += (String.format(": - %1$s ", op.getName()));
                    break;
                case "importance":
                    if (op.getImportance()){
                        layers.remove(op);
                        if (!importantLayers.contains(op)) {
                            importantLayers.add(op);
                        }
                        opManifest += (String.format(": ↑ %1$s ", op.getName()));
                    } else {
                        if (!layers.contains(op)) {
                            layers.add(op);
                        }
                        importantLayers.remove(op);
                        opManifest += (String.format(": ↓ %1$s ", op.getName()));
                    }
                    break;
                default: //Should never happen, but it's here in case it does.
                    System.out.printf("A null layer operation! (\"%1$s\")", op.getName());
                    break;
            }
        }
        layerOpLock = false;
        operationList.clear();
        if (doOutput) {
            System.out.println(opManifest + "\n");
            layerChangeInstance++;
        }
    }

    /**
     * Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.
     *
     * @param input     what it should be now
     * @param layerName what Layer you want to edit calls itself
     * @param y         the Y coordinate which you want to change something at
     * @param x         the X coordinate which you want to change something at
     */
    public void editLayer(String input, String layerName, int y, int x) {
        SpecialText translate = new SpecialText(input);
        editLayer(translate, layerName, y, x);
    }

    /**
     * Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.
     *
     * @param input     what it should be now
     * @param lay       the actual Layer you want changed
     * @param y         the Y coordinate which you want to change something at
     * @param x         the X coordinate which you want to change something at
     */
    public void editLayer(String input, Layer lay, int y, int x) {
        if (!(y > lay.getRows() || y < 0 || x > lay.getColumns() || x < 0)) {
            lay.setStr(y, x, input);
        }
    }

    /**
     * Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.
     *
     * @param input what it should be now, a SpecialText!
     * @param layerName The name of the layer you want to edit
     * @param y     the row (Y coordinate) which you want to change something at
     * @param x     the column (X coordinate) which you want to change something at
     */
    public void editLayer(SpecialText input, String layerName, int y, int x) {
        Layer get = getLayer(layerName);
        if (get != null)
            get.setSpecTxt(y, x, input);
        else
            System.out.printf("Null Layer called (editLayer): \"%1$s\"\n", layerName);
    }

    /**
     * Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.
     *
     * @param input what it should be now, a SpecialText!
     * @param lay   the actual Layer you want to edit
     * @param y     the row (Y coordinate) which you want to change something at
     * @param x     the column (X coordinate) which you want to change something at
     */
    public void editLayer(SpecialText input, Layer lay, int y, int x) {
        if (!(y > lay.getRows() || y < 0 || x > lay.getColumns() || x < 0)) {
            lay.setSpecTxt(y, x, input);
        }
    }

    /**
     * Change the camera's relative position
     *
     * @param x units along x axis
     * @param y units along y axis
     */
    public void moveCam(int x, int y) {
        camY += y;
        camX += x;
    }

    /**
     * Change the camera's absolute position
     *
     * @param x units, x axis
     * @param y units, y axis
     */
    public void setCam(int x, int y) {
        camY = y;
        camX = x;
    }

    /**
     * Specifying a Layer name (String), return the whether the layer exists
     *
     * @param layerName string that the Layer calls its own
     * @return boolean of the layer's existence
     */
    public boolean layerExists(String layerName) {
        while (layerOpLock){
            try{
                Thread.sleep(5);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        return importantLayerExists(layerName) || unimportantLayerExists(layerName);
    }

    public boolean importantLayerExists(String layerName) {
        for (int id = 0; id < importantLayers.size(); id++) {
            Layer get = importantLayers.get(id);
            if (get != null && get.nameMatches(layerName)) {
                return true;
            }
        }
        return false;
    }

    public boolean unimportantLayerExists(String layerName) {
        for (int id = 0; id < layers.size(); id++) {
            Layer get = layers.get(id);
            if (get != null && get.nameMatches(layerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all layers from existence except for the one named "playerLayer"
     */
    public void removeAllButPlayer() {
        ArrayList<Layer> remove = new ArrayList<>();
        for (Layer get : layers) { //Main block of logic. Does the operation and outputs as it does so.
            if (!get.name.contains("playerLayer")) {
                remove.add(get);
            }
        }
        for (Layer get : remove) {
            removeLayer(get.getName());
        }
    }

    private int layerChangeInstance = 1;

    private void newSendImage() {
        Layer fullImage;
        try {
            if (defaultPlayer == null){
                fullImage = topDownBuild();
            } else {
                fullImage = topDownBuild(defaultPlayer);
            }
            window.build(fullImage);
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            System.out.println("[ImageOrg] Concurrent Modification error while building image!");
        }// Cuz it'll be fixed next time probs.
    }

    private int screenH() {
        return 28;
    }
    private int screenW() {
        return 46;
    }

    /**
     * Build a layer with default, normal parameters, like starting at (0,0) with no owningPlayerUsername, a white foreground, and no fabulous mode on.
     * @return what should go on the screen
     */
    private Layer topDownBuild() {
        return topDownBuild(camX, camY, null, Color.WHITE, false, 0, 0); // All the default things
    }
    /**
     * Build a layer with display parameters read from a player
     * @return what should go on the screen
     */
    Layer topDownBuild(Player focusOn) {
        return topDownBuild(focusOn.getCamX(), focusOn.getCamY(), focusOn.getUsername(), focusOn.foregroundColor, focusOn.fabulousMode, focusOn.fabulousLocIndex, focusOn.fabulousColorIndex);
    }
    /**
     * Build a layer with parameters specified, for other methods to override
     * @return what should go on the screen
     */
    private Layer topDownBuild(int camX, int camY, String owningPlayerUsername, Color foregroundColor, boolean fabulousMode, int fabulousLocIndex, int fabulousColorIndex) {
        //Update layer order to minimize nonexistant layers
        doLayerOperations();
        //System.out.println(layerOpWait);

        ArrayList<Layer> allLayers = (ArrayList<Layer>)layers.clone();
        allLayers.addAll(importantLayers);
        //Actually render image
        Layer fullImage = new Layer(new String[screenH()][screenW()]);
        fullImage.clear(roomBackground);
        int camYtoBe = camX;
        camX = camY;
        camY = camYtoBe;
        int maxH = screenH(); //Equals 23
        int maxW = screenW(); //Equals 46
        for (int row = 0; row < maxH; row++) { //Iterates over every coordinate of the screen
            for (int col = 0; col < maxW; col++) {
                for (int ii = allLayers.size(); ii > 0; ii--) { //At each coordinate, goes through all layers until an opaque space is found
                    Layer layer = allLayers.get(ii - 1);
                    if (layer != null) {
                        int xPos = row - layer.getX();
                        int yPos = col - layer.getY(); //Math done to find out what portion of the layer corresponds with the screen coordinate
                        if (layer.getCamOb()) {
                            xPos += camX;
                            yPos += camY;
                        }
                        SpecialText found = layer.getSpecTxt(xPos, yPos); //Gets SpecialText from derived layer coordinate
                        String input = found.getStr(); //Gets string from SpecialText to make code easier to read
                        if (found.isSignificant() && (layer.getRelaventPlayerUsername() == null || layer.getRelaventPlayerUsername().equals(owningPlayerUsername))) { //If the SpecialText isn't blank
                            if ("ñ".equals(input)) { //If space found was opaque
                                fullImage.setSpecTxt(row, col, new SpecialText(" "));
                            } else { //Otherwise, place found SpecialText
                                SpecialText toPlace = new SpecialText(found.getStr(), found.foregroundColor, found.backgroundColor);
                                if (foregroundColor != null && foregroundColor != Color.WHITE){
                                    toPlace.setInfluencedForegroundColor(foregroundColor);
                                }
                                if (fabulousMode && Math.abs((row + col) - fabulousLocIndex) <= 7) { // Fabulous mode accommodations
                                    toPlace.setInfluencedForegroundColor(fabulousColorWheel[fabulousColorIndex]);
                                }
                                fullImage.setSpecTxt(row, col, toPlace);
                            }
                            ii = 0; //Ends search at the coordinate and moves onto the next coordinate
                        }
                    }
                }
            }
        }
        return fullImage;
    }


    /**
     * For the Player and stuff to be able to attach its key bindings (and change colors).
     *
     * @return the Window.
     */
    public Window getWindow() {
        return window;
    }

    /**
     * For resuming saves, set the Window to a legit, existent one.
     */
    public void setWindow(Window newWindow) {
        window = newWindow;
    }

    /**
     * Wipe a layer clean
     * @param layName string of layer
     */
    public void clearLayer(String layName) {
        Layer toClear = getLayer(layName);
        if (toClear != null) {
            toClear.clear();
            System.out.println("Clearing " + toClear.getName());
        } else {
            System.out.printf("Layer '%1$s' could not be found to be cleared!\n", layName);
        }
    }

    public void printLayers() {
        System.out.printf("Current layers (Size: %1$d) ", layers.size());
        for (int ii = 0; ii < layers.size(); ii++) {
            System.out.print(layers.get(ii).getName());
            if (ii != layers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print(" | ");
        for (int ii = 0; ii < importantLayers.size(); ii++) {
            System.out.print(importantLayers.get(ii).getName());
            if (ii != importantLayers.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("\n");
    }

    public String getOwningPlayerUsername() {
        return owningPlayerUsername;
    }

    private class FrameTimer extends TimerTask {

        long lastRunMs = 0;

        FrameTimer() {
            lastRunMs = System.nanoTime();
        }

        public void run() {
            //printLayers();
            lastRunMs = System.currentTimeMillis();
            newSendImage();
            if (System.currentTimeMillis() - lastRunMs > 3)
                System.out.printf("Time to compile image (notably long): %1$dms\n", System.currentTimeMillis() - lastRunMs);
        }
    }
}
