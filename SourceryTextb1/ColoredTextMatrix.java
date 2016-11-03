package SourceryTextb1;

import SourceryTextb1.GameClock;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

import static java.lang.Math.abs;

/**
 * Created by Jared on 9/25/2016.
 */
public class ColoredTextMatrix extends JPanel{

    public SpecialText[][] text = new SpecialText[46][28];
    protected Color overallForeGround = Color.WHITE;

    //Fabulous colors!
    private Color[] fabulousColorWheel = {new Color(255, 100, 100), new Color(255, 100, 255), new Color(100, 100, 255), new Color(100, 255, 255),
                                          new Color( 80, 255, 120), new Color(255, 255, 100), new Color(255, 150,  75)};
    private int fabulousColorIndex = 0;
    private int fabulousLocIndex = 1;
    public boolean fabulousMode = false;

    int HOR_SEPARATION = 9;
    int VER_SEPARATION = 16;
    int CHAR_WIDTH     = 10;
    int CHAR_HEIGHT    = 16;
    int CHAR_SIZE      = 15;
    int HOR_MARGIN     = 0;

    public ColoredTextMatrix(){
        setOpaque(true);
        setFont(new Font("Monospaced", Font.PLAIN, CHAR_SIZE));
        setFocusable(true);

        GameClock.timer.scheduleAtFixedRate(new frameResetTimer(), 25, 25);
    }

    @Override
    public void paintComponent (Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        HOR_SEPARATION = getWidth() / 46; //Calculates horizontal and vertical separation of the letters
        VER_SEPARATION = (getHeight() / 28); //Intentionally wrong
        int adjustedVerSep = (int)((float)VER_SEPARATION * (9f/16)); //Adjusts horizontal separation if too big for vertical.
        if (HOR_SEPARATION > adjustedVerSep){
            HOR_SEPARATION = adjustedVerSep;
            HOR_MARGIN = (getWidth() - (HOR_SEPARATION*46)) / 2; //Sets a margin to center display in the screen
        } else {
            HOR_MARGIN = 0;
        }
        CHAR_SIZE = VER_SEPARATION; //Calculations based upon horizontal and vertical separation
        CHAR_WIDTH = HOR_SEPARATION;
        CHAR_HEIGHT = CHAR_SIZE + 1;

        setFont(new Font("Monospaced", Font.PLAIN, CHAR_SIZE));

        //System.out.println(text[0][0].getStr());
        if (HOR_MARGIN > 0){
            g.setColor(new Color(35, 35, 35));
            g.drawLine(HOR_MARGIN - 1, 0, HOR_MARGIN - 1, getHeight());
            int secondX = HOR_MARGIN + (HOR_SEPARATION*46);
            g.drawLine(secondX, 0, secondX, getHeight());
        }
        for (int col = 0; col < text.length; col++){ //Draws the highlighting / backgrounds first
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(get.getBackgroundColor());
                    g.fillRect((col * HOR_SEPARATION) + HOR_MARGIN, (row * VER_SEPARATION) + (CHAR_SIZE / 5), CHAR_WIDTH, CHAR_HEIGHT);
                }
            }
        }
        for (int col = 0; col < text.length; col++){ //Afterwards drawing the text on top
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(retrieveColor(get, row, col));
                    g.drawString(get.getStr(), (col * HOR_SEPARATION) + HOR_MARGIN, (row * VER_SEPARATION) + CHAR_SIZE);
                }
            }
        }
    }

    private Color retrieveColor(SpecialText input, int row, int col){
        if (fabulousMode && abs((row + col) - fabulousLocIndex) <= 7){
            return fabulousColorWheel[fabulousColorIndex];
        } else {
            return input.makeInfluencedForegroundColor(overallForeGround);
        }
    }

    public void setOverallForeGround (Color set){
        overallForeGround = set;
    }

    private class frameResetTimer extends TimerTask {
        public void run(){
            if (fabulousMode){
                fabulousLocIndex++;
                if (fabulousLocIndex > 75){
                    fabulousLocIndex = 1;
                    fabulousColorIndex++;
                    if (fabulousColorIndex >= 7) fabulousColorIndex = 0;
                }
            }
            repaint();
        }
    }
}

