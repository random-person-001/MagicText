/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

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
    private ArrayList<Layer> toAdd = new ArrayList<>();
    private ArrayList<Layer> toRemove = new ArrayList<>();
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
    public void setLayers(ArrayList<Layer> setLayers){
        layers = setLayers;
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
        toAdd.add(lay);
        //System.out.println(String.format("Layer Given: %1$s (%2$b)", lay.getName(), somethingChanged));
    }

    private String addTheLayers() {
        return addTheLayers(true);
    }

    private String addTheLayers(boolean reorderImportant) {
        String addList = "";
        for (Layer lay : toAdd) {
            if (!lay.getImportance())
                layers.add(lay);
            else
                importantLayers.add(lay);
            addList += lay.getName() + " ";
            //System.out.println("Adding: " + lay.getName());
        }
        //if (reorderImportant && !toAdd.isEmpty()) {
        //    moveImportantLayersUp();
        //}
        toAdd.clear();
        return addList;
    }

    private String removeTheLayers() {
        String output = String.format("\n[%1$d] Layers Removed: ", layerChangeInstance);
        for (Layer lay : toRemove) {
            layers.remove(lay);
            output += lay.getName() + " ";
        }
        toRemove.clear();
        return output;
    }

    public boolean getDebug() {
        return debugGame;
    }

    /**
     * Reorder the list of layers to observe each one's <>importance</>  Probably doesn't work yet!
     */
    private void updateOrder() {
        //throw new OperationNotSupportedException
        for (Layer get : layers) {
            if (get.getImportance()) {
                //System.out.println("Moving: " + get.getName() + " (from " + layers.indexOf(get) + " of " + layers.size() + ")");
                removeLayer(get.getName());
                addLayer(get);
            }
        }
    }

    /**
     * Example bubble sorter, for writing updateOrder() off of
     *
     * @param x array to sort
     */
    public void sortArray(int[] x) {
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 1; i < x.length; i++) {
                int temp = 0;
                if (x[i - 1] > x[i]) {
                    temp = x[i - 1];
                    x[i - 1] = x[i];
                    x[i] = temp;
                    swapped = true;
                }
            }
        }
    }

    /**
     * Return the Layer specified by its name
     *
     * @param layerName the Layer's name
     * @return that Layer
     */
    public Layer getLayer(String layerName) {
        for (Layer lay1 : layers){
            if (lay1 != null && lay1.getName().equals(layerName))
                return lay1;
        }
        for (Layer lay2 : importantLayers){
            if (lay2 != null && lay2.getName().equals(layerName))
                return lay2;
        }
        return null;
    }

    /**
     * Remove a layer by its String name.
     *
     * @param layerName what the Layer's name is
     */
    public void removeLayer(String layerName) {
        for (Layer get : layers) {
            if (get.nameMatches(layerName)) {
                toRemove.add(get);
            }
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
        int loc = getPosLayer(layerName);
        if (loc != -1) {
            editLayer(input, layers.get(loc), y, x);
        }
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
            editLayer(input, get, y, x);
        else
            System.out.printf("Null Layer called: \"%1$s\"", layerName);
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
     * Specifying a Layer name (String), return the index of that layer
     *
     * @param layerName string that the Layer calls its own
     * @return index of the Layer you're looking for
     */
    public int getPosLayer(String layerName) {
        int result = -1;
        for (int id = 0; id < layers.size(); id++) {
            Layer get = layers.get(id);
            if (get != null && get.nameMatches(layerName)) {
                result = id;
            }
        }
        return result;
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
        boolean doOutput = toAdd.size() > 0 || toRemove.size() > 0;
        String changeList = String.format("- Org -\n[%1$d] Layers Added:   ", layerChangeInstance);
        changeList += addTheLayers();
        changeList += removeTheLayers();
        if (doOutput) {
            System.out.println(changeList + "\n");
            layerChangeInstance++;
        }
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
        System.out.print("Current layers: ");
        for (int ii = 0; ii < layers.size(); ii++) {
            System.out.print(layers.get(ii).getName());
            if (ii != layers.size() - 1) {
                System.out.print(", ");
            } else {
                System.out.print("\n");
            }
        }
    }

    public void removeLayer(Layer layer) {
        layers.remove(layer);
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
