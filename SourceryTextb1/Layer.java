/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

/**
 * A class that is essentially a convenience method for working with two dimensional String arrays.
 *
 * @author 119184
 */
public class Layer implements java.io.Serializable {
    private SpecialText[][] self;
    public String name = "";
    private boolean opaque = false;
    private boolean cameraObedient = true;
    private int xPos = 0;
    private int yPos = 0;
    private boolean importance = false;


    // Layers should be any size

    public SpecialText[][] convertFromStrArray (String[][] original){
        SpecialText[][] output = new SpecialText[original.length][original[0].length];
        for (int col = 0 ; col < original.length; col++){
            for (int row = 0; row < original[0].length; row++){
                output[col][row] = new SpecialText(original[col][row]);
            }
        }
        return output;
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
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey  a String name for the Layer to call itself
     * @param xSet   a X starting point
     * @param ySet   a Y starting point
     */
    public Layer(String[][] assign, String inkey, int xSet, int ySet) {
        this(assign, inkey);
        xPos = xSet;
        yPos = ySet;
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
        if (opacity){
            System.out.println("An opaque Layer has been created!");
        }
    }

    public Layer(String[][] assign, String inkey, int xSet, int ySet, boolean camOb, boolean opacity) {
        this(assign, inkey);
        cameraObedient = camOb;
        opaque = opacity;

        xPos = xSet;
        yPos = ySet;
    }

    public Layer(String[][] assign, String inkey, int xSet, int ySet, boolean camOb, boolean opacity, boolean important) {
        this(assign, inkey);
        cameraObedient = camOb;
        opaque = opacity;
        importance = important;

        xPos = xSet;
        yPos = ySet;
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

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    /**
     * Set the absolute position of the layer
     *
     * @param newX a new X coordinate to start at
     * @param newY a new Y coordinate to start at
     */
    public void setPos(int newX, int newY) {
        xPos = newX;
        yPos = newY;
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

    public void setStr(int r, int c, String str) {
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())) {
            self[r][c] = new SpecialText(str);
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
     * @param r a row that you want to know about
     * @param c a column you want to know about
     * @return the String at the specified coordinates.  Will be ñ if opaque space, else the legit char there.
     */
    public String getStr(int r, int c) {
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())) {
            if ((getOpacity()) && ("".equals(self[r][c]) || " ".equals(self[r][c]) || self[r][c] == null)) {
                return "ñ";
            } else {
                return self[r][c].getStr();
            }
        } else {
            return "";
        }
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
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                setStr(row, col, "");
            }
        }
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
    }
}
