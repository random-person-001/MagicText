/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import SourceryTextb1.GameObjects.GameObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An intermediate between the countless Layers and the Window, to organise and keep all the Layers in line.
 * There is probably only one of these.
 * @author 119184
 */
public class ImageOrg {
    private Window window;
    public ArrayList<Layer> layers = new ArrayList<>();
    private ArrayList<Layer> toAdd = new ArrayList<>();
    private ArrayList<Layer> toRemove = new ArrayList<>();
    private int camX = 0;
    private int camY = 0;
    private boolean debugGame = false;

    private boolean somethingChanged = true;
    
    public ImageOrg(Window game){
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new frameTimer(), 0, 50); //20 fps lock
        window = game;
    }

    /** Add a layer onto the top, or somewhere near it if there's more important things
     * @param lay a Layer to be known about by everything
     */
    public void addLayer(Layer lay){
        toAdd.add(lay);
        System.out.println(String.format("Layer Given: %1$s (%2$b)", lay.getName(), somethingChanged));
        somethingChanged = true;
    }

    public void addTheLayers () {
        for (Layer lay : toAdd){
            layers.add(lay);
            System.out.println("Adding: " + lay.getName());
        }
        toAdd.clear();
    }

    public void removeTheLayers () {
        for (Layer lay : toRemove){
            layers.remove(lay);
        }
        toRemove.clear();
    }

    public boolean getDebug(){
        return debugGame;
    }

    /**
     * Reorder the list of layers to observe each one's <>importance</>  Probably doesn't work yet!
     */
    private void updateOrder() {
        //throw new OperationNotSupportedException
        for (Layer get : layers) {
            if (get.getImportance()){
                //System.out.println("Moving: " + get.getName() + " (from " + layers.indexOf(get) + " of " + layers.size() + ")");
                removeLayer(get.getName());
                addLayer(get);
            }
        }
    }

    /** Example bubble sorter, for writing updateOrder() off of
     * @param x array to sort
     */
    public void sortArray(int[] x) {
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for(int i=1; i<x.length; i++) {
                int temp=0;
                if(x[i-1] > x[i]) {
                    temp = x[i-1];
                    x[i-1] = x[i];
                    x[i] = temp;
                    swapped = true;
                }
            }
        }
    }

    /** Return the Layer specified by its index
     * @param go the Layer's index
     * @return that Layer
     */
    public Layer getLayer(int go){
        if (go == -1){
            return new Layer(new String[1][1]);
        } else {
            return layers.get(go);
        }
    }

    /** Remove a layer by its String name.
     * @param layerName what the Layer's name is
     */
    public void removeLayer(String layerName){
        for (int id = 0; id < layers.size() ; id++){
            Layer get = layers.get(id);
            if (get.nameMatches(layerName)){
                toRemove.add(get);
            }
        }
        somethingChanged = true;
    }

    /** Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.
     * @param input what it should be now
     * @param layerName what Layer you want to edit calls itself
     * @param y the Y coordinate which you want to change something at
     * @param x the X coordinate which you want to change something at
     */
    public void editLayer (String input, String layerName, int y, int x) {
        int loc = getPosLayer(layerName);
        if (loc != -1) {
            editLayer(input, loc, y, x);
            somethingChanged = true;
        }
        else{
            System.out.println("No layer with the name " + layerName);
        }
    }

    /** Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.  Note that you can instead
     * specify the Layer's string name instead of loc here, saving some trouble.
     * @param input what it should be now
     * @param loc index of the Layer you want to edit
     * @param r the row (Y coordinate) which you want to change something at
     * @param c the column (X coordinate) which you want to change something at
     */
    public void editLayer (String input, int loc, int r, int c){
        try {
            Layer get = layers.get(loc);
            if (!(r > get.getRows() || r < 0 || c > get.getColumns() || c < 0)){
                get.setStr(r, c, input);
                somethingChanged = true;
            } else {
                partialRender(get, input, loc, r, c);
            }
        } catch (ArrayIndexOutOfBoundsException e){System.out.println("No such layer (" + loc + ")! " + e);}
    }


    /**
     * smartImageMod was an attempt to make the game more efficient.
     * What it does is instead of re-building the whole game image, it only updates the specific spots that are edited.
     *
     * We might want to get back to this if we want the program to be even more lightweight.
     */

    private void partialRender (Layer lay, String input, int loc, int r, int c){
        //System.out.println("Partial rendering...");
        if (notEmpty(input)){
            int x = r + lay.getX();
            int y = c + lay.getY();
            boolean spaceOpen = true;
            for (int ii = loc; ii < layers.size(); ii++){
                Layer get = getLayer(ii);
                if (!notEmpty(get.getStr(x - get.getX(), y - get.getY()))){
                    //spaceOpen = false;
                    break;
                }
            }
            if (spaceOpen){
                //System.out.println("Part. render SUCCESS");
                window.getFullImage().setStr(x + camX, y + camY, input);
            }
        }
        window.build();
    }

    private boolean notEmpty (String input){
        return !("".equals(input) || " ".equals(input) || input == null);
    }

    /** Change the camera's relative position
     * @param x units along x axis
     * @param y units along y axis
     */
    public void moveCam(int x, int y){
        camX += y;
        camY += x;
        somethingChanged = true;
    }

    /** Change the camera's absolute position
     * @param x units, x axis
     * @param y units, y axis
     */
    public void setCam(int x, int y){
        camX = y; //This is totally intentional.  We really ought to do something about that.
        camY = x;
        somethingChanged = true;
    }
    
    public int getCamX(){
        return camY;
    }
    
    public int getCamY(){
        return camX;
    }


    /**
     * Print the camera's x and y coordinates to the console
     */
    private void reportCam(){
        System.out.println("camX: " + camY);
        System.out.println("camY: " + camX);
    }

    /** Specifying a Layer name (String), return the index of that layer
     * @param layerName string that the Layer calls its own
     * @return index of the Layer you're looking for
     */
    public int getPosLayer(String layerName){
        int result = -1;
        for (int id = 0; id < layers.size() ; id++){
            Layer get = layers.get(id);
            if (get.nameMatches(layerName)){
                result = id;
            }
        }
        return result;
    }

    /**
     * Remove all layers from existence except for the one named "playerLayer"
     */
    public void removeAllButPlayer(){
        //String isPlayerList = "";
        //String remainingList = "";
        //String beginList = "";
        /*

        //EVERYTHING DISPLAY RELATED HAS BEEN COMMENTED OUT.

        System.out.println("STARTING NEW CLEANUP....");
        int initSize = layers.size();
        for (int id = 0; id < layers.size() ; id++){ //Gets all current layers before operating
            Layer get = layers.get(id);
            beginList += (get.getName() + ", ");
        }
        System.out.println("Layers Before Clear: " + beginList + "(" + initSize + ")\n");
        */
        int count = 0;
        ArrayList<Layer> remove = new ArrayList<>();
        for (int id = 0; id < layers.size() ; id++){ //Main block of logic. Does the operation and outputs as it does so.
            Layer get = layers.get(id);
            count++;
            //System.out.println(get.getName() + " : playerLayer (" + !get.nameMatches("playerLayer") + ", " + count + " of " + layers.size() + ")");
            if (!get.nameMatches("playerLayer")){
                remove.add(get);
            } else {
                //isPlayerList += (get.getName() + ", ");
            }
        }
        for (Layer get : remove){
            layers.remove(get);
        }
        somethingChanged = true;
        /*
        System.out.println("\nLayers Marked as playerLayer: " + isPlayerList + "(Amount processed: " + count + ")");

        for (int id = 0; id < layers.size() ; id++){ //Gets all layers after operating
            Layer get = layers.get(id);
            remainingList += (get.getName() + ", ");
        }


        System.out.println("Layers Remaining: " + remainingList);
        */
    }

    /**
     * Assemble all the layers together, and place it on the screen.
     */


    public void compileImage(){
        /*
        This should do nothing.
        It's just here to make the game compile.
        That is all.
        */
    }

    public int sendImage(){
        long nanoTime = System.nanoTime();
        addTheLayers();
        removeTheLayers();
        window.clearImage();
        for (Layer get : layers) {
            if (get.getCamOb()) {
                window.placeLayer(get, camX, camY);
            } else {
                window.setLayer(get);
            }
        }
        window.build();
        int elapsedMs = (int)((System.nanoTime() - nanoTime) / 1000000);
        System.out.println("Update time: " + elapsedMs + "ms");
        return elapsedMs;
    }

    public void newSendImage(){
        if (somethingChanged) {
            //System.out.println("New Frame...");
            long nanoTime = System.nanoTime();
            addTheLayers();
            removeTheLayers();
            window.clearImage();
            window.topDownBuild(layers, camX, camY);
            window.build();
            int elapsedMs = (int) ((System.nanoTime() - nanoTime) / 1000000);
            //System.out.println("Update time: " + elapsedMs + "ms");
        }
        somethingChanged = false;
    }

    /**
     * For the Player and stuff to be able to attach its key bindings (and change colors).
     * @return the Window.
     */
    public Window getWindow(){
        return window;
    }

    /** Wipe a layer clean
     * @param layName string of layer
     */
    public void clearLayer(String layName) {
        int loc = getPosLayer(layName);
        getLayer(loc).clear();
    }

    public class frameTimer extends TimerTask {

        long lastRunNano = 0;

        public frameTimer(){
            lastRunNano = System.nanoTime();
        }

        public void run(){
            newSendImage();
        }
    }
}
