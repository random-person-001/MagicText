/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

/**
 * An intermediate between the countless Layers and the Window, to organise and keep all the Layers in line.
 * There is probably only one of these.
 * @author 119184
 */
public class ImageOrg {
    private Window window;
    private ArrayList<Layer> layers = new ArrayList<>();
    private int camX = 0;
    private int camY = 0;
    
    public ImageOrg(Window game){
        window = game;
    }

    /** Add a layer onto the top, or somewhere near it if there's more important things
     * @param lay a Layer to be known about by everything
     */
    public void addLayer(Layer lay){
        layers.add(lay);
        //updateOrder();
    }

    /**
     * Reorder the list of layers to observe each one's <>importance</>
     */
    private void updateOrder() {
        //throw new OperationNotSupportedException
        boolean swapped = true;
        while (swapped) {
            swapped = false;
            for (int i = 0; i < layers.size(); i++) {
                if (layers.get(i-1).getImportance() > layers.get(i).getImportance()){
                    Layer temp = layers.get(i-1);
                    layers.remove(i-1);
                    layers.add(i, temp);
                }

            }
        }
    }

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
        return layers.get(go);
    }

    /** Remove a layer by its String name.
     * @param layerName what the Layer's name is
     */
    public void removeLayer(String layerName){
        for (int id = 0; id < layers.size() ; id++){
            Layer get = layers.get(id);
            if (get.nameMatches(layerName)){
                layers.remove(id);
            }
        }
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
            }
        }catch (ArrayIndexOutOfBoundsException e){System.out.println("No such layer!" + e);}
    }

    /** Change the camera's relative position
     * @param x units along x axis
     * @param y units along y axis
     */
    public void moveCam(int x, int y){
        camX += y;
        camY += x;
        //reportCam();
    }

    /** Change the camera's absolute position
     * @param x units, x axis
     * @param y units, y axis
     */
    public void setCam(int x, int y){
        camX = y; //This is totally intentional.  We really ought to do something about that.
        camY = x;
        //reportCam();
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
        for (int id = 0; id < layers.size() ; id++){
            Layer get = layers.get(id);
            if (!get.nameMatches("playerLayer")){
                layers.remove(id);
            }
        }
    }

    /**
     * Assemble all the layers together, and place it on the screen.
     */
    public void compileImage(){
        window.clearImage();
        for (Layer get : layers) {
            if (get.getCamOb()) {
                window.placeLayer(get, camX, camY);
            } else {
                window.setLayer(get);
            }
        }
        window.build(camX, camY);
    }

    /**
     * Clear out a rectangle in the compiled layers to be spacey quotes.  Note that this will be overwritten/ignored
     * next time org.compileImage() is called
     * @param rStart starting row
     * @param rEnd ending row
     * @param cStart starting column
     * @param cEnd ending column
     */
    public void clearArea(int rStart, int rEnd, int cStart, int cEnd){
        window.clearArea(rStart, rEnd, cStart, cEnd);
    }
    
    /**
     * For the Player and stuff to be able to attach its key bindings (and change colors).
     * @return the Window.
     */
    public Window getWindow(){
        return window;
    }
}
