/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;


/**
 * @author 119184
 */
public class Window extends JFrame {
    public ColoredTextMatrix txtArea;
    private Container c = getContentPane();
    public int serial = (int) (Math.random() * 1000);

    public Window() {
        setVisible(true);
        txtArea = new ColoredTextMatrix();

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth() / 4;
        int height = gd.getDisplayMode().getHeight() / 2;

        setBounds(100, 100, width, height);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sourcery Text  -  an alphanumeric misadventure");
        setResizable(true);

        c.add(txtArea);
        c.validate();

        txtArea.repaint();
    }

    public int screenH() {
        return 28;
    }

    public int screenW() {
        return 46;
    }

    /**
     * Place the temporary idea of what should be on the screen (fullImage) onto the actual display, the txtArea that
     * is a ColoredTextMatrix.
     *
     * @param fullImage the 28x46 Layer that is the final result of rendering and should be placed onto the screen
     */
    public void build(Layer fullImage) {
        for (int row = 0; row < 28; row++) { // Used to be 20
            for (int col = 0; col < 46; col++) { // Used to be 50
                SpecialText s = fullImage.getSpecTxt(row, col);
                if (s != null && s.toString() != null) {
                    if (!s.toString().equals("")) {
                        txtArea.text[col][row] = s;
                    }
                } else {
                    txtArea.text[col][row] = new SpecialText(" ");
                }
            }
        }
        //System.out.println(fullImage.getStr());
        txtArea.repaint();
    }

    /**
     * "Formally" adds a keyListener with a request of focus in the window to ensure the keyListener can hear
     */
    public void formalAddKeyListener(KeyListener toAdd){
        txtArea.requestFocusInWindow();
        txtArea.addKeyListener(toAdd);
    }
}
