/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * An intermediate between the countless Layers and the Window, to organise and keep all the Layers in line.
 * There is probably only one of these.
 *
 * @author 119184
 */
public class ImageOrg implements java.io.Serializable {
    private Window window;
    private ArrayList<Layer> layers = new ArrayList<>();
    private ArrayList<Layer> toAdd = new ArrayList<>();
    private ArrayList<Layer> toRemove = new ArrayList<>();
    private int camX = 0;
    private int camY = 0;
    private boolean debugGame = false;

    //FrameTimer frameTimerInstance = new FrameTimer();
    private Timer drawTimer = new Timer();

    final int orgSerial = (int) (Math.random() * 10000);

    public ImageOrg(Window game) {
        //drawTimer.scheduleAtFixedRate(frameTimerInstance, 0, 50); //20 fps lock
        resetClock();
        window = game;
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
        drawTimer.cancel();
        drawTimer.purge();
        drawTimer = null;
    }

    /**
     * Add a layer onto the top, or somewhere near it if there's more important things
     *
     * @param lay a Layer to be known about by everything
     */
    public void addLayer(Layer lay) {
        toAdd.add(lay);
        if (lay.getName().contains("One-wayDoor")) {
            System.out.println(lay.getStr(0, 0));
        }
        //System.out.println(String.format("Layer Given: %1$s (%2$b)", lay.getName(), somethingChanged));
    }

    private void moveImportantLayersUp() {
        ArrayList<Layer> importants = new ArrayList<>();
        for (Layer l : layers) {
            if (l.getImportance()) {
                importants.add(l);
            }
        }
        for (Layer l : importants) {
            layers.remove(l);
        }
        for (Layer l : importants) {
            layers.add(l);
        }
    }

    private String addTheLayers() {
        return addTheLayers(true);
    }

    private String addTheLayers(boolean reorderImportant) {
        String addList = "";
        for (Layer lay : toAdd) {
            layers.add(lay);
            addList += lay.getName() + " ";
            //System.out.println("Adding: " + lay.getName());
        }
        if (reorderImportant && !toAdd.isEmpty()) {
            moveImportantLayersUp();
        }
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
     * Return the Layer specified by its index
     *
     * @param go the Layer's index
     * @return that Layer
     */
    public Layer getLayer(int go) {
        if (go == -1) {
            return new Layer(new String[1][1]);
        } else {
            return layers.get(go);
        }
    }

    /**
     * Return the Layer specified by its name
     *
     * @param layerName the Layer's name
     * @return that Layer
     */
    public Layer getLayer(String layerName) {
        int go = getPosLayer(layerName);
        return getLayer(go);
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
            editLayer(input, loc, y, x);
        }
    }

    /**
     * Edit a single element of a Layer's array of strings.  Don't get X and Y mixed up.  Note that you can instead
     * specify the Layer's string name instead of loc here, saving some trouble.
     *
     * @param input what it should be now
     * @param loc   index of the Layer you want to edit
     * @param r     the row (Y coordinate) which you want to change something at
     * @param c     the column (X coordinate) which you want to change something at
     */
    public void editLayer(String input, int loc, int r, int c) {
        Layer get;
        try {
            get = layers.get(loc);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No such layer (" + loc + ")! " + e);
            return;
        }
        if (!(r > get.getRows() || r < 0 || c > get.getColumns() || c < 0)) {
            get.setStr(r, c, input);
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
        camY = y; //This is totally intentional.  We really ought to do something about that.
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
     * Remove all layers from existence except for the one named "playerLayer"
     */
    public void removeAllButPlayer() {
        ArrayList<Layer> remove = new ArrayList<>();
        for (Layer get : layers) { //Main block of logic. Does the operation and outputs as it does so.
            if (!get.nameMatches("playerLayer")) {
                remove.add(get);
            }
        }
        for (Layer get : remove) {
            layers.remove(get);
        }
    }

    /**
     * Assemble all the layers together, and place it on the screen.
     */


    public void compileImage() {
        /*
        This should do nothing.
        It's just here to make the game compile.
        That is all.
        */
    }

    private int layerChangeInstance = 1;

    private void newSendImage() {
        try {
            //if (somethingChanged) {
            //System.out.println("New Frame...");
            //long nanoTime = System.nanoTime();
            boolean doOutput = toAdd.size() > 0 || toRemove.size() > 0;
            String changeList = String.format("- Org -\n[%1$d] Layers Added:   ", layerChangeInstance);
            changeList += addTheLayers();
            changeList += removeTheLayers();
            if (doOutput) {
                System.out.println(changeList + "\n");
                layerChangeInstance++;
            }
            window.clearImage();
            window.topDownBuild(layers, camX, camY);
            window.build();
        } catch (ConcurrentModificationException ignore) {
        }// Cuz it'll be fixed next time probs.
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
     *
     * @param layName string of layer
     */
    public void clearLayer(String layName) {
        int loc = getPosLayer(layName);
        getLayer(loc).clear();
        System.out.println("Clearing " + getLayer(loc).getName());
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
