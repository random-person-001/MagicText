package SourceryTextb1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * Created by Jared on 9/25/2016.
 */
public class ColoredTextMatrix extends JPanel {
    public SpecialText[][] text = new SpecialText[46][28];
    private int marginDisplayTimer = 0;

    private int HOR_SEPARATION = 9;
    private int VER_SEPARATION = 16;
    private int CHAR_WIDTH = 10;
    private int CHAR_HEIGHT = 16;
    private int CHAR_SIZE = 15;
    private int HOR_MARGIN = 0;

    ColoredTextMatrix() {
        setOpaque(true);
        setFocusable(true);
        recalculate();
        setFont(new Font("Monospaced", Font.PLAIN, CHAR_SIZE));
        addComponentListener(new ComponentResizeListener());
    }

    private class ComponentResizeListener implements ComponentListener {
        /**
         * // Now is a great time to recalculate display variables!
         *
         * @param e something I don't care about
         */
        @Override
        public void componentResized(ComponentEvent e) {
            //System.out.println("Window resized!");
            recalculate();
            //String message = "w=" + getWidth() + " h=" + getHeight();
            //System.out.println(message);
        }

        // These aren't important; it refused to run by throwing errors until I implemented these.
        @Override
        public void componentMoved(ComponentEvent componentEvent) {
        }

        @Override
        public void componentShown(ComponentEvent componentEvent) {
        }

        @Override
        public void componentHidden(ComponentEvent componentEvent) {
        }
    }

    /**
     * Recalculate all of the display stuff, to call during window resizing (when it changes)
     */
    private void recalculate() {
        HOR_SEPARATION = getWidth() / 46; //Calculates horizontal and vertical separation of the letters
        VER_SEPARATION = (getHeight() / 28); //Intentionally wrong
        int adjustedVerSep = (int) ((float) VER_SEPARATION * (9f / 15)); //Adjusts horizontal separation if too big for vertical.
        if (HOR_SEPARATION > adjustedVerSep) {
            HOR_SEPARATION = adjustedVerSep;
            HOR_MARGIN = (getWidth() - (HOR_SEPARATION * 46)) / 2; //Sets a margin to center display in the screen
        } else {
            HOR_MARGIN = 0;
        }
        CHAR_SIZE = VER_SEPARATION; //Calculations based upon horizontal and vertical separation
        CHAR_WIDTH = HOR_SEPARATION;
        CHAR_HEIGHT = CHAR_SIZE + 1;

        setFont(new Font("Monospaced", Font.PLAIN, CHAR_SIZE));
        marginDisplayTimer = 120; // Used to redraw grey lines at edges of the useful area of the window
    }

    @Override
    public void paintComponent(Graphics g) {
        //float startTime = System.currentTimeMillis();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        //recalculate(); // See the difference it makes by uncommenting - my unscientific methods measured around a thirdish difference

        for (int col = 0; col < text.length; col++) { //Draws the highlighting / backgrounds first, so that it does not draw over any characters whatsoever
            for (int row = 0; row < text[0].length; row++) {
                SpecialText get = text[col][row];
                if (get != null) {
                    g.setColor(get.backgroundColor);
                    g.fillRect((col * HOR_SEPARATION) + HOR_MARGIN, (row * VER_SEPARATION) + (CHAR_SIZE / 4), CHAR_WIDTH, CHAR_HEIGHT);
                }
            }
        }

        for (int col = 0; col < text.length; col++) { //Draws the text after doing the background
            for (int row = 0; row < text[0].length; row++) {
                SpecialText get = text[col][row];
                if (get != null) {
                    g.setColor(get.foregroundColor);
                    g.drawString(get.getStr(), (col * HOR_SEPARATION) + HOR_MARGIN, (row * VER_SEPARATION) + CHAR_SIZE);
                }
            }
        }

        if (marginDisplayTimer > 0 && HOR_MARGIN > 0) {
            int secondX = HOR_MARGIN + (HOR_SEPARATION * 46);
            int marginColor = (marginDisplayTimer < 15) ? marginDisplayTimer * 3 : 45;
            g.setColor(new Color(marginColor, marginColor, marginColor));
            g.drawLine(HOR_MARGIN - 1, 0, HOR_MARGIN - 1, getHeight());
            g.drawLine(secondX, 0, secondX, getHeight());
            marginDisplayTimer--;
        }
    }
}

