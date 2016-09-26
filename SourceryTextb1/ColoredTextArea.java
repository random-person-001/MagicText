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

    final int HOR_SEPARATION = 9;
    final int VER_SEPARATION = 16;
    final int CHAR_WIDTH     = 10;
    final int CHAR_HEIGHT    = 15;

    public ColoredTextArea (){
        setOpaque(true);
        setFont(new Font("Monospaced", Font.PLAIN, 15));
        setFocusable(true);

        Timer timing = new Timer();
        timing.scheduleAtFixedRate(new frameResetTimer(), 25, 25);
    }

    @Override
    public void paintComponent (Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());

        //System.out.println(text[0][0].getStr());
        for (int col = 0; col < text.length; col++){ //Draws the highlighting / backgrounds first
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(get.getBackgroundColor());
                    g.fillRect((col * HOR_SEPARATION), (row * VER_SEPARATION) + 1, CHAR_WIDTH, CHAR_HEIGHT);
                }
            }
        }
        for (int col = 0; col < text.length; col++){ //Afterwards drawing the text on top
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(get.makeInfluencedForegroundColor(overallForeGround));
                    g.drawString(get.getStr(), col * HOR_SEPARATION, (row * VER_SEPARATION) + 15);
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

