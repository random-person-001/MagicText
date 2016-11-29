/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1;

import java.awt.*;
import javax.swing.*;


/**
 * @author 119184
 */
public class Window extends JFrame {
    public ColoredTextMatrix txtArea;
    private Container c = getContentPane();
    public int serial = (int) (Math.random()*1000);

    public Window() {
        setVisible(true);
        txtArea = new ColoredTextMatrix();
        setBounds(100, 100, 412, 412);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sourcery Text  -  an alphanumeric misadventure");
        setResizable(true);

        c.add(txtArea);
        c.validate();
        setBackground(Color.CYAN);

        txtArea.repaint();
    }

    /**
     * @return the maximum allowable height in the game, an arbitrary large number that should be greater or equal to
     * the size of a room.  Used for hitmesh sizes and used to be used for the size of a unified spellcasting layer, but
     * now each spell has their own layer so it's only used for hitmeshes.  I suppose we could make this dynamic and
     * depend on the size of the background art if we really wanted to, but it isn't a high priority I think.
     */
    public int maxH() {
        return 80;
    }

    /**
     * @return the maximum allowable width in the game, an arbitrary large number
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
     * Place the temporary idea of what should be on the screen (fullImage) onto the actual display, the txtArea that
     * is a ColoredTextMatrix.
     * @param fullImage the 28x46 Layer that is the final result of rendering and should be placed onto the screen
     */
    void build(Layer fullImage) {
        for (int row = 0; row < 28; row++) { // Used to be 20
            for (int col = 0; col < 46; col++) { // Used to be 50
                SpecialText s  = fullImage.getSpecTxt(row, col);
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
}
