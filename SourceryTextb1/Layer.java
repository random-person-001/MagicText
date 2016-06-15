/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

/**
 *
 * @author 119184
 */
public class Layer {
    public String[][] self;
    
    public String name = "";
    private boolean keyExists = false;
    private boolean opaque = false;
    private boolean cameraObedient = true;
    private int xPos = 0;
    private int yPos = 0;
    /**
     * Pertinent to ordering the layers.
     * Higher is more important/will mean more on top
     * -1 is no preference to layer order,  100 is the Player's layer, 101 is the HUD, and anything higher than that
     * will display above those
     */
    private int importance = -1;
    
    /*
    Note to future self:
    In a given layer, rows = layer[0].length
                      cols = layer.length
    Each array in the array represent a column, and entries in each array repesent a row
    therefore:
    layer[col][row]    // Wait, what?  Everywhere here it's the other way around!?!?!?!!! -Riley

    */
    
    // Layers should be any size

    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     */
    public Layer(String[][] assign){
        self = assign;
    }
    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey a String name for the Layer to call itself
     */
    public Layer(String[][] assign, String inkey){
        self = assign;
        name = inkey;
        keyExists = true;
    }

    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey a String name for the Layer to call itself
     * @param xSet a X starting point
     * @param ySet a Y starting point
     */
    public Layer(String[][] assign, String inkey, int xSet, int ySet){
        self = assign;
        name = inkey;
        keyExists = true;
        
        xPos = xSet;
        yPos = ySet;
    }
    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey a String name for the Layer to call itself
     * @param camOb whether the layer should be camera-obedient (default for others is false)
     */
    public Layer(String[][] assign, String inkey, boolean camOb){
        self = assign;
        name = inkey;
        keyExists = true;
        cameraObedient = camOb;
    }

    /**
     * @param assign an ititial String[][] for the layer to have (can be later edited, element at a time)
     * @param inkey a String name for the Layer to call itself
     * @param camOb whether the layer should be camera-obedient (default for others is false)
     * @param opacity whether the layer should wirite over layers even when writing blank spaces (default for others is false)
     * NOTE: opacity unfinished / not implemented!
     */
    public Layer(String[][] assign, String inkey, boolean camOb, boolean opacity){
        self = assign;
        name = inkey;
        keyExists = true;
        cameraObedient = camOb;
        opaque = opacity;
    }

    public Layer(String[][] assign, String inkey, int xSet, int ySet, boolean camOb, boolean opacity){
        self = assign;
        name = inkey;
        keyExists = true;
        cameraObedient = camOb;
        opaque = opacity;

        xPos = xSet;
        yPos = ySet;
    }
    
    public void setCamOb(boolean set){  // JARED, I'M WORKING ON THIS
        cameraObedient = set;
    }

    /**
     * @return whether the Layer is obedient to the camera or not
     */
    public boolean getCamOb(){
        return cameraObedient;
    }

    public boolean getOpacity(){
        return opaque;
    }

    /**
     * @return Or, shall we say, the number of rows
     */
    public int getRows(){
        return self.length;
    }

    /**
     * @return Or, shall we say, the number of columns
     */
    public int getColumns(){
        return self[0].length;
    }
    
    public int getX(){
        return xPos;
    }
    
    public int getY(){
        return yPos;
    }

    /** Set the absolute position of the layer
     * @param newX a new X coordinate to start at
     * @param newY a new Y coordinate to start at
     */
    public void setPos(int newX, int newY){
        xPos = newX;
        yPos = newY;
    }

    /**
     * @return what this Layer calls itself
     */
    public String getName(){
        return name;
    }
    
    public void setStr(int r, int c, String str){
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())){
            self[r][c] = str;
        }
    }

    /** Hey Jared!  I think this is where to put in the opaque spaces.
     * @param r
     * @param c
     * @param str
     */
    public void placeStr(int r, int c, String str){
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns()) &&
         !("".equals(self[r][c]) || " ".equals(self[r][c]) || (self[r][c]) != null)){
            setStr(r,c,str);
        }
    }

    /**
     * @param r a row that you want to know about
     * @param c a column you want to know about
     * @return the String at the specified coordinates
     */
    public String getStr(int r, int c){
        if (!(r < 0 || r >= getRows() || c < 0 || c >= getColumns())){
            if ((getOpacity()) && ("".equals(self[r][c]) || " ".equals(self[r][c]) || self[r][c] == null)){
                return "ñ";
            } else {
                return self[r][c];
            }
        } else {
            return "";
        }
    }

    /**
     * @param input a string that may be the name of the layer
     * @return true iff the Strings are the same and keyExists (whatever that is)
     */
    public boolean nameMatches (String input) {
        return keyExists && input.equals(name);
    }

    /**
     * Pertinent to ordering the layers.
     * Higher importance will mean more on top
     * -1 is no preference to layer order,  100 is the Player's layer, 101 is the HUD, and anything higher than that
     * will display above those
     * @return what it is for this Layer
     */
    public int getImportance(){
        return importance;
    }
    /**
     * Pertinent to ordering the layers.
     * Higher importance will mean more on top
     * -1 is no preference to layer order,  100 is the Player's layer, 101 is the HUD, and anything higher than that
     * will display above those
     * @param newImportance what it should be set to now
     */
    public void setImportance(int newImportance){
        importance = newImportance;
    }

    /**
     * Make the layer completely empty strings
     */
    public void clear(){
        for(int row = 0 ; row < getRows(); row++){
            for(int col = 0 ; col < getColumns(); col++){
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
