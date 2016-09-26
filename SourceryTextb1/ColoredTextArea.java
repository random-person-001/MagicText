package SourceryTextb1;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jared on 9/25/2016.
 */
public class ColoredTextArea extends JPanel{

    public SpecialText[][] text = new SpecialText[46][28];
    protected Color overallForeGround = Color.WHITE;

    int HOR_SEPARATION = 9;
    int VER_SEPARATION = 16;
    int CHAR_WIDTH     = 10;
    int CHAR_HEIGHT    = 16;
    int CHAR_SIZE      = 15;

    public ColoredTextArea (){
        setOpaque(true);
        setFont(new Font("Monospaced", Font.PLAIN, CHAR_SIZE));
        setFocusable(true);

        Timer timing = new Timer();
        timing.scheduleAtFixedRate(new frameResetTimer(), 25, 25);
    }

    @Override
    public void paintComponent (Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());
        HOR_SEPARATION = getWidth() / 46;
        VER_SEPARATION = getHeight() / 27;
        CHAR_SIZE = VER_SEPARATION - 1;
        CHAR_WIDTH = HOR_SEPARATION;
        CHAR_HEIGHT = CHAR_SIZE + 1;

        setFont(new Font("Monospaced", Font.PLAIN, CHAR_SIZE));

        //System.out.println(text[0][0].getStr());
        for (int col = 0; col < text.length; col++){ //Draws the highlighting / backgrounds first
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(get.getBackgroundColor());
                    g.fillRect((col * HOR_SEPARATION), (row * VER_SEPARATION) + (CHAR_SIZE / 5), CHAR_WIDTH, CHAR_HEIGHT);
                }
            }
        }
        for (int col = 0; col < text.length; col++){ //Afterwards drawing the text on top
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(get.makeInfluencedForegroundColor(overallForeGround));
                    g.drawString(get.getStr(), col * HOR_SEPARATION, (row * VER_SEPARATION) + CHAR_SIZE);
                }
            }
        }
    }

    public void setOverallForeGround (Color set){
        overallForeGround = set;
    }

    private class frameResetTimer extends TimerTask {
        public void run(){
            repaint();
        }
    }
}

