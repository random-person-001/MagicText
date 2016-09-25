/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;


/**
 * @author 119184
 */
public class Window extends JFrame {
    private static final String OPAQUE_SPACE = "ñ";
    public JTextPane txtArea = new JTextPane();
    public ColoredTextArea newTxtArea = new ColoredTextArea();
    private Container c = getContentPane();
    private String foregroundColor = "#ffffff";

    private int dbgSavedSerial = 0;
    public int dbgCounter = 0;

    private Layer fullImage = new Layer(new String[screenH()][screenW()]);

    /**
     * @param newColor a string of hexcode (like "#c3c3c3") for the default foreground to be
     */
    public void setForegroundColor(String newColor) {
        foregroundColor = newColor;
    }

    public void giveSerial(int number) {
        if (number != dbgSavedSerial) {
            System.out.println(String.format("OH NO! There is another org!!! (%1$d vs. %2$d)", dbgSavedSerial, number));
        }
        dbgSavedSerial = number;
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
        return 23;
    }

    private int screenW() {
        return 46;
    }

    /**
     * Directly set the textArea contents to just an empty string
     */
    public void clearText() {
        txtArea.setText("");
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

    void topDownBuild(List<Layer> layers, int camX, int camY) {
        int camYtoBe = camX;
        camX = camY; // Riley moved the wire-crossing to here (from the Layer.getX() and .getY()) on 21 Sept
        camY = camYtoBe;
        int maxH = screenH(); //Equals 23
        int maxW = screenW(); //Equals 46
        for (int row = 0; row < maxH; row++) {
            for (int col = 0; col < maxW; col++) {
                for (int ii = layers.size(); ii > 0; ii--) {
                    Layer layer = layers.get(ii - 1);
                    int xPos = row - layer.getX();
                    int yPos = col - layer.getY();
                    if (layer.getCamOb()) {
                        xPos += camX;
                        yPos += camY;
                    }
                    String input = layer.getStr(xPos, yPos);
                    if (notEmpty(input)) {
                        if ("ñ".equals(input)) {
                            fullImage.setStr(row, col, " ");
                        } else {
                            fullImage.setStr(row, col, input);
                        }
                        ii = 0;
                    }
                }
            }
        }
        //System.out.println("Drawn bounding box:\n X: " + (camY) + " to " + (camY + maxH - 1) + "\n Y: " + (camX) + " to " + (camX + maxW - 1));
    }

    /**
     * Place the temporary idea of what should be on the screen (fullImage) onto the actual display
     * Usually takes 40-70ms. (at least, with an 80x150 fullImage size)
     */
    void build() {
        /*
        int maxH = screenH();
        int maxW = screenW();
        String build = "";
        for (int row = 0; row < maxH; row++) {// Used to be 20
            for (int col = 0; col < maxW; col++) { // Used to be 50
                String s = fullImage.getStr(row, col);
                if (s == null || s.equals("")) {
                    build += " ";
                } else {
                    build += s;
                }
            }
            build += "<br>";
        }
        //clearText();
        build = "<pre><span color='" + foregroundColor + "'>" + build + "</span></pre>"; // style=font-family:'monospace'
        Timer buffer = new Timer();
        buffer.schedule(new FrameBuffer(build), 25);
        //txtArea.setText(build);
        */
        /*
        for (int row = 0; row < 23; row++) {// Used to be 20
            for (int col = 0; col < 46; col++) { // Used to be 50
                String s = fullImage.getStr(row, col);
                if (s == null || s.equals("")) {
                    newTxtArea.text[col][row] = new SpecialText(" ");
                } else {
                    newTxtArea.text[col][row] = new SpecialText(s);
                }
            }
        }
        */
    }

    public Window() {
        setBounds(100, 100, 412, 412);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sourcery Text  -  an alphanumeric misadventure");
        setResizable(true);

        /*
        //NEW WAY (NOT COMPLETE)
        add(newTxtArea);
        clearImage();
        build();
        */

        /*
        //OLD WAY (FLICKERY)
        clearImage();
        build();
        txtArea.setBackground(Color.BLACK);
        //txtArea.setFont(new Font("Consolas", Font.PLAIN, 15));
        txtArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        txtArea.setEditable(false);

        c.add(txtArea);
        c.validate();

        setVisible(true);
        txtArea.requestFocusInWindow();

        System.out.println(txtArea.getContentType());
        txtArea.setContentType("text/html");
        */

        setVisible(true);
    }

    private class FrameBuffer extends TimerTask {
        String toBuild = "";

        FrameBuffer(String theScreen) {
            toBuild = theScreen;
        }

        public void run() throws NullPointerException {
            //System.out.println(txtArea.getText());
            txtArea.setText(toBuild);
        }
    }
}
