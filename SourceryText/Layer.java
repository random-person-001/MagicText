/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText;

import java.awt.*;
import java.util.Random;

/**
 * A class that is essentially a convenience method for working with two dimensional SpecialText arrays.
 *
 * @author 119184
 */
public class Layer implements java.io.Serializable {
    private SpecialText[][] self;
    public String name = "";
    private boolean opaque = false;
    private boolean cameraObedient = true;
    private int yPos = 0;
    private int xPos = 0;
    private boolean importance = false;
    private String owningPlayerUsername = null;

    public String imageOrgOperation;


    // Layers should be any size

    public SpecialText[][] convertFromStrArray(String[][] original) {
        SpecialText[][] output = new SpecialText[original.length][original[0].length];
        for (int col = 0; col < original.length; col++) {
            for (int row = 0; row < original[0].length; row++) {
                output[col][row] = new SpecialText(original[col][row]);
            }
        }
        return output;
    }

    public String getRelaventPlayerUsername() {
        return owningPlayerUsername;
    }

    public Layer setOwningPlayerUsername(String newOwningPlayerUsername) { // It returns this Layer because of compactness in the createDuplicate method.
        owningPlayerUsername = newOwningPlayerUsername;
        //System.out.println("Layer " + name + " relevant username changed to " + newOwningPlayerUsername);
        return this;
    }

    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     */
    public Layer(String[][] assign) {
        self = convertFromStrArray(assign);
    }

    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey  a String name for the Layer to call itself
     */
    public Layer(String[][] assign, String inkey) {
        this(assign);
        name = inkey;
    }

    /**
     * @param assign an initial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey  a String name for the Layer to call itself
     * @param xSet   a X starting point
     * @param ySet   a Y starting point
     */
    public Layer(String[][] assign, String inkey, int xSet, int ySet) {
        this(assign, inkey);
        yPos = ySet;
        xPos = xSet;
    }

    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey  a String name for the Layer to call itself
     * @param camOb  whether the layer should be camera-obedient (default for others is false)
     */
    public Layer(String[][] assign, String inkey, boolean camOb) {
        this(assign, inkey);
        cameraObedient = camOb;
    }

    /**
     * @param assign  an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey   a String name for the Layer to call itself
     * @param camOb   whether the layer should be camera-obedient (default for others is false)
     * @param opacity whether the layer should wirite over layers even when writing blank spaces (default for others is false)
     *                NOTE: opacity unfinished / not implemented!
     */
    public Layer(String[][] assign, String inkey, boolean camOb, boolean opacity) {
        this(assign, inkey);
        cameraObedient = camOb;
        opaque = opacity;
        //System.out.printf("Layer Created! I am %1s, whose opacity is %2$b\n", name, opaque);
    }

    public Layer(String[][] assign, String inkey, int xSet, int ySet, boolean camOb, boolean opacity) {
        this(assign, inkey, camOb, opacity);

        yPos = ySet;
        xPos = xSet;
        //System.out.printf("Layer Created! I am %1s, whose opacity is %2$b\n", name, opaque);
    }

    public Layer(String[][] assign, String inkey, int xSet, int ySet, boolean camOb, boolean opacity, boolean important) {
        this(assign, inkey, xSet, ySet, camOb, opacity);
        importance = important;

        //System.out.printf("Layer Created! I am %1s, whose opacity is %2$b\n", name, opaque);
    }

    public Layer(SpecialText[][] assign, String inkey, int xSet, int ySet, boolean camOb, boolean opacity, boolean important) {
        self = assign;
        name = inkey;
        cameraObedient = camOb;
        opaque = opacity;
        importance = important;

        yPos = ySet;
        xPos = xSet;
        //System.out.printf("Layer Created! I am %1s, whose opacity is %2$b\n", name, opaque);
    }

    public void setCamOb(boolean set) {  // JARED, I'M WORKING ON THIS
        cameraObedient = set;
    }

    /**
     * @return whether the Layer is obedient to the camera or not
     */
    boolean getCamOb() {
        return cameraObedient;
    }

    private boolean getOpacity() {
        return opaque;
    }

    /**
     * @return Or, shall we say, the number of rows
     */
    public int getRows() {
        return self.length;
    }

    /**
     * @return Or, shall we say, the number of columns
     */
    public int getColumns() {
        return self[0].length;
    }

    public int getY() {
        return yPos;
    }

    public int getX() {
        return xPos;
    }

    /**
     * Set the absolute position of the layer
     *
     * @param newX a new X coordinate to start at
     * @param newY a new Y coordinate to start at
     */
    public void setPos(int newX, int newY) {
        yPos = newY;
        xPos = newX;
    }

    /**
     * @return what this Layer calls itself
     */
    public String getName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    /**
     * Makes a SpecialText based upon a given string at a location in the layer
     */
    public void setStr(int r, int c, String str) {
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())) {
            self[r][c] = new SpecialText(str);
        }
    }

    /**
     * Makes a SpecialText based upon a given string at a location in the layer
     */
    public void setSpecTxt(int r, int c, SpecialText toSet) {
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())) {
            self[r][c] = toSet;
        }
    }

    /**
     * @param r   row
     * @param c   column
     * @param str the string to place there if there's currently a space/null/empty there.
     */
    void placeStr(int r, int c, String str) {
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns()) &&
                !("".equals(self[r][c]) || " ".equals(self[r][c]) || (self[r][c]) != null)) {
            self[r][c] = new SpecialText(str);
        }
    }

    /**
     * Returns a SpecialText from a requested (row,col) coordinate in layer.
     *
     * @param r row of desired SpecialText
     * @param c column of desired SpecialText
     * @return
     */
    public SpecialText getSpecTxt(int r, int c) {
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())) {
            if ((getOpacity()) && ("".equals(self[r][c].toString()) || " ".equals(self[r][c].toString()) || self[r][c] == null)) {
                //System.out.println("Opacity applied!");
                return new SpecialText("ñ");
            } else {
                return self[r][c];
            }
        } else {
            return new SpecialText("");
        }
    }

    /**
     * @param r a row that you want to know about
     * @param c a column you want to know about
     * @return the String at the specified coordinates.  Will be ñ if opaque space, else the legit char there.
     */
    public String getStr(int r, int c) {
        return getSpecTxt(r, c).getStr();
    }

    /**
     * @param input a string that may be the name of the layer
     * @return true iff the Strings are the same and keyExists (whatever that is)
     */
    boolean nameMatches(String input) {
        return name != null && input.equals(getName());
    }

    /**
     * Pertinent to ordering the layers.
     * Higher importance will mean more on top
     * -1 is no preference to layer order,  100 is the Player's layer, 101 is the HUD, and anything higher than that
     * will display above those
     *
     * @return what it is for this Layer
     */
    boolean getImportance() {
        return importance;
    }

    /**
     * Pertinent to ordering the layers.
     * Higher importance will mean more on top
     * -1 is no preference to layer order,  100 is the Player's layer, 101 is the HUD, and anything higher than that
     * will display above those
     *
     * @param newImportance what it should be set to now
     */
    public void setImportance(boolean newImportance) {
        importance = newImportance;
    }

    /**
     * Make the layer completely empty strings
     */

    public void clear() {
        clear(Color.BLACK);
    }

    public void clear(Color usedBackgrnd) {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                SpecialText empty = new SpecialText(" ", null, usedBackgrnd);
                setSpecTxt(row, col, empty);
            }
        }
    }

    /**
     * Finds all SpecialTexts in layer and replaces it with another.
     *
     * @param find        SpecialText to find
     * @param replacement SpecialText to replace with
     */
    public void findAndReplace(SpecialText find, SpecialText replacement) {
        for (int c = 0; c < getColumns(); c++) {
            for (int r = 0; r < getRows(); r++) {
                if (getSpecTxt(r, c).equals(find)) {
                    setSpecTxt(r, c, replacement);
                }
            }
        }
    }

    /**
     * Finds all SpecialTexts in layer and replaces it with another.
     *
     * @param find        SpecialText to find
     * @param replacement SpecialText to replace with
     * @param chance      the chance (in 100) of each tile to be swapped
     */
    public void findAndReplace(SpecialText find, SpecialText replacement, int chance) {
        Random rand = new Random();
        for (int c = 0; c < getColumns(); c++) {
            for (int r = 0; r < getRows(); r++) {
                if (getSpecTxt(r, c).equals(find) && rand.nextInt(100) <= chance) {
                    setSpecTxt(r, c, replacement);
                }
            }
        }
    }

    /**
     * Sets (not influences) the background color of all SpecialText in the layer
     */
    public void setAllBg(Color flavor) {
        for (int c = 0; c < getColumns(); c++) {
            for (int r = 0; r < getRows(); r++) {
                SpecialText get = getSpecTxt(r, c);
                get.backgroundColor = (flavor);
                self[r][c] = get;
            }
        }
    }

    /**
     * Sets (not influences) the background color of all SpecialText in the layer
     */
    public void setAllFg(Color flavor) {
        for (int c = 0; c < getColumns(); c++) {
            for (int r = 0; r < getRows(); r++) {
                SpecialText get = getSpecTxt(r, c);
                get.foregroundColor = (flavor);
                self[r][c] = get;
            }
        }
    }

    /**
     * Influences the foreground color of all SpecialText in the layer
     */
    public void influenceAll(Color flavor) {
        for (int c = 0; c < getColumns(); c++) {
            for (int r = 0; r < getRows(); r++) {
                SpecialText get = getSpecTxt(r, c);
                get.setInfluencedForegroundColor(flavor);
                self[r][c] = get;
            }
        }
    }

    /**
     * Returns a duplicate layer
     */

    public Layer createDuplicate() {
        return new Layer(self, name, xPos, yPos, cameraObedient, opaque, importance).setOwningPlayerUsername(owningPlayerUsername);
    }

    public String getNameAndOperation() {
        String output = name;
        output += " (" + imageOrgOperation + ")";
        return output;
    }

    public boolean equals(Object other) {
        return name == ((Layer) other).getName();
    }

    /**
     * @return what the Layer looks like (without background)
     */
    public String getStr() {
        String toReturn = "";
        for (SpecialText[] row : self) {
            for (SpecialText txt : row) {
                toReturn += txt.getStr();
            }
            toReturn += "\n";
        }
        return toReturn;
    }

    public void setY(int y) {
        yPos = y;
    }

    public void setX(int x) {
        xPos = x;
    }
}
