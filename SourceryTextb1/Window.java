/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import java.awt.*;
import java.util.List;
import java.util.TimerTask;
import javax.swing.*;


/**
 * @author 119184
 */
public class Window extends JFrame {
    private static final String OPAQUE_SPACE = "ñ";
    //public JTextPane txtArea = new JTextPane();
    public ColoredTextMatrix txtArea = new ColoredTextMatrix();
    private Container c = getContentPane();
    private String foregroundColor = "#ffffff";

    private Layer fullImage = new Layer(new String[screenH()][screenW()]);

    /**
     * @param newColor a string of hexcode (like "#c3c3c3") for the default foreground to be
     */
    public void setForegroundColor(String newColor) {
        foregroundColor = newColor;
    }

    /**
     * @return the maximum allowable height in the game
     */
    public int maxH() {
        return 80;
    } //Note that this must be >= any layer sizes, but other than that it's arbitrary

    /**
     * @return the maximum allowable width in the game
     */
    public int maxW() {
        return 150;
    }

    private int screenH() {
        return 28;
    }

    private int screenW() {
        return 46;
    }

    /**
     * Directly set the textArea contents to just an empty string
     */
    public void clearText() {
        //txtArea.setText("");
    }


    /**
     * Clear out the compiled layers to be spacey quotes.  Note that this will be overwritten/ignored next
     * time org.compileImage() is called
     */
    public void clearImage() {
        fullImage.clear();
    }


    /**
     * Much like placeLayer, but ignores camera.
     *
     * @param layer a layer to place on top of fullImage
     */
    public void setLayer(Layer layer) { //Much like placeLayer, but ignores camera
        for (int row = 0; row < fullImage.getRows(); row++) {
            for (int col = 0; col < fullImage.getColumns(); col++) { //This stuff is complicated!!!!
                if (!" ".equals(layer.getStr(row, col))) {
                    if ("".equals(layer.getStr(row, col)) || layer.getStr(row, col) == null) {
                        fullImage.placeStr(row + layer.getX(), col + layer.getY(), " ");
                    } else if (layer.getStr(row, col).equals(OPAQUE_SPACE)) {
                        fullImage.setStr(row + layer.getX(), col + layer.getY(), " ");
                    } else {
                        fullImage.setStr(row + layer.getX(), col + layer.getY(), layer.getStr(row, col));
                    }
                }
            }
        }
    }

    private boolean notEmpty(String input) {
        return !("".equals(input) || " ".equals(input) || input == null);
    }

    private boolean notSignificant(SpecialText test) {return test.isSignificant();}

    void topDownBuild(List<Layer> layers, int camX, int camY) {
        int camYtoBe = camX;
        camX = camY; // Riley moved the wire-crossing to here (from the Layer.getX() and .getY()) on 21 Sept
        camY = camYtoBe;
        int maxH = screenH(); //Equals 23
        int maxW = screenW(); //Equals 46
        for (int row = 0; row < maxH; row++) { //Iterates over every coordinate of the screen
            for (int col = 0; col < maxW; col++) {
                Color finalBackground = null;
                for (int ii = layers.size(); ii > 0; ii--) { //At each coordinate, goes through all layers until an opaque space is found
                    Layer layer = layers.get(ii - 1);
                    int xPos = row - layer.getX();
                    int yPos = col - layer.getY(); //Math done to find out what portion of the layer corresponds with the screen coordinate
                    if (layer.getCamOb()) {
                        xPos += camX;
                        yPos += camY;
                    }
                    SpecialText found = layer.getSpecTxt(xPos, yPos); //Gets SpecialText from derived layer coordinate
                    String input = found.getStr(); //Gets string from SpecialText to make code easier to read
                    if (notSignificant(found)) { //If the SpecialText isn't blank
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

    /**
     * Place the temporary idea of what should be on the screen (fullImage) onto the actual display
     * Usually takes 40-70ms. (at least, with an 48x27 fullImage size)
     */
    void build() {
        for (int row = 0; row < 28; row++) { // Used to be 20
            for (int col = 0; col < 46; col++) { // Used to be 50
                SpecialText s  = fullImage.getSpecTxt(row, col);
                if (s == null || s.toString().equals("")) {
                    txtArea.text[col][row] = new SpecialText(" ");
                } else {
                    txtArea.text[col][row] = s;
                }
            }
        }

    }

    public Window() {
        setBounds(100, 100, 412, 412);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sourcery Text  -  an alphanumeric misadventure");
        setResizable(true);

        c.add(txtArea);
        c.validate();
        clearImage();
        build();

        setVisible(true);
    }

    private class FrameBuffer extends TimerTask {
        String toBuild = "";

        FrameBuffer(String theScreen) {
            toBuild = theScreen;
        }

        public void run() throws NullPointerException {
            //System.out.println(txtArea.getText());
            //txtArea.setText(toBuild);
        }
    }
}
