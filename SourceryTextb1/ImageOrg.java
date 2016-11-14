/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import com.sun.org.apache.xpath.internal.SourceTree;

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

    private ArrayList<Layer> operationList = new ArrayList<>();
    private ArrayList<Layer> operationBuffer = new ArrayList<>();
    private boolean layerOpLock = false;
    private int layerOpWait = 0; //Integer representing the amount of non-doLayerOperations actions are being currently executed

    private int camX = 0;
    private int camY = 0;
    private boolean debugGame = false;

    public Color roomBackground = Color.BLACK;

    /**
     * Only for Player 1
     */
    public Color imageForeground = null;

    //FrameTimer frameTimerInstance = new FrameTimer();
    private Timer drawTimer = new Timer();

    final int orgSerial = (int) (Math.random() * 10000);
    private String owningPlayerUsername = null;

    public ImageOrg(Window game) {
        //drawTimer.scheduleAtFixedRate(frameTimerInstance, 0, 50); //20 fps lock
        resetClock();
        window = game;
    }

    public void setOwningPlayerUsername(String newOwningPlayerUsername){
        owningPlayerUsername = newOwningPlayerUsername;
    }

    public ArrayList<Layer> getLayers(){
        return layers;
    }

    public void resetClock() {
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
        lay.imageOrgOperation = "add";
        operationList.add(lay);
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

    /**
     * Remove a layer by its String name.
     *
     * @param layerName what the Layer's name is
     */
    public void removeLayer(String layerName) {
        ArrayList<Layer> opStack = operationList;
        if (layerOpLock)
            opStack = operationBuffer;
        boolean layerFound = false;
        
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer get = layers.get(i);
            if (get.nameMatches(layerName)) {
                get.imageOrgOperation = "remove";
                opStack.add(get);
                layerFound = true;
            }
        }
        for (int i = importantLayers.size() - 1; i >= 0; i--) {
            Layer get = importantLayers.get(i);
            if (get.nameMatches(layerName)) {
                get.imageOrgOperation = "remove";
                opStack.add(get);
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
        ArrayList<Layer> opStack = operationList;
        if (layerOpLock)
            opStack = operationBuffer;
        boolean layerFound = false;
        for (int i = layers.size() - 1; i >= 0; i--) {
            Layer get = layers.get(i);
            if (get.nameMatches(layerName)) {
                get.imageOrgOperation = "importance";
                get.setImportance(set);
                opStack.add(get);
                layerFound = true;
            }
        }
        for (int i = importantLayers.size() - 1; i >= 0; i--) {
            Layer get = importantLayers.get(i);
            if (get.nameMatches(layerName)) {
                get.imageOrgOperation = "importance";
                get.setImportance(set);
                opStack.add(get);
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
        boolean doOutput = operationList.size() > 0;  //Stuff for the extremely useful output of layer changes
        String opManifest = String.format("- Org -\n[%1$d] Layer Op's", layerChangeInstance);
        for (Layer op : operationList){ //Now for the actual layer operations; works like a stack
            switch(op.imageOrgOperation){
                case "add": //If the layer in question should be added
                    if (op.getImportance()) importantLayers.add(op);
                    else layers.add(op);
                    if (doOutput) opManifest += (String.format(": + %1$s ", op.getName()));
                    break;
                case "remove": //If the layer in question shouuld be removed
                    layers.remove(op);
                    importantLayers.remove(op);
                    if (doOutput) opManifest += (String.format(": - %1$s ", op.getName()));
                    break;
                case "importance":
                    if (op.getImportance()){
                        layers.remove(op);
                        importantLayers.add(op);
                        if (doOutput) opManifest += (String.format(": ↑ %1$s ", op.getName()));
                    } else {
                        layers.add(op);
                        importantLayers.remove(op);
                        if (doOutput) opManifest += (String.format(": ↓ %1$s ", op.getName()));
                    }
                    break;
                default: //Should never happen, but it's here in case it does.
                    System.out.printf("A null layer operation! (\"%1$s\")", op.getName());
                    break;
            }
        }
        layerOpLock = false;
        operationList.clear();
        operationList.addAll(operationBuffer);
        operationBuffer.clear();
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
            System.out.printf("Null Layer called: \"%1$s\"\n", layerName);
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

    public int getCamX() {
        return camX;
    }

    public int getCamY() {
        return camY;
    }

    /**
     * Specifying a Layer name (String), return the whether the layer exists
     *
     * @param layerName string that the Layer calls its own
     * @return boolean of the layer's existence
     */
    public boolean layerExists(String layerName) {
        for (int id = 0; id < layers.size(); id++) {
            Layer get = layers.get(id);
            if (get != null && get.nameMatches(layerName)) {
                return true;
            }
        }
        for (int id = 0; id < importantLayers.size(); id++) {
            Layer get = importantLayers.get(id);
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
            layers.remove(get);
        }
    }

    private int layerChangeInstance = 1;

    private void newSendImage() {
        try {
            window.build(topDownBuild(camX, camY, owningPlayerUsername, imageForeground));
            //if (imageForeground != null)
                //System.out.println(imageForeground.toString());
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

    public Layer topDownBuild(int camX, int camY, String owningPlayerUsername, Color foregroundColor) {
        //Update layer order to minimize nonexistant layers
        doLayerOperations();
        //printLayers();
        //System.out.println(layerOpWait);

        ArrayList<Layer> allLayers = layers;
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
                                fullImage.setSpecTxt(row, col, found);
                            }
                            ii = 0; //Ends search at the coordinate and moves onto the next coordinate
                        }
                    }
                }
            }
        }
        if (foregroundColor != null && foregroundColor != Color.WHITE){
            fullImage.influenceAll(foregroundColor);
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
            } else {
                System.out.print("\n");
            }
        }
    }

    public String getOwningPlayerUsername() {
        return owningPlayerUsername;
    }

    private class FrameTimer extends TimerTask {

        long lastRunNano = 0;

        FrameTimer() {
            lastRunNano = System.nanoTime();
        }

        public void run() {
            newSendImage();
        }
    }
}
