package SourceryTextb1;

import javax.swing.plaf.basic.BasicTextAreaUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Jared on 9/25/2016.
 */
public class ColoredTextArea extends JTextComponent {

    public SpecialText[][] text = new SpecialText[46][23];
    protected Color overallForeGround = Color.WHITE;

    public ColoredTextArea (){
        setOpaque(true);
        setUI(new BasicTextAreaUI());
        setFont(new Font("Monospaced", Font.PLAIN, 15));

        text[0][0] = new SpecialText("t");

        Timer timing = new Timer();
        timing.scheduleAtFixedRate(new frameResetTimer(), 25, 25);
    }

    @Override
    public void paintComponent (Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0, getWidth(), getHeight());

        //System.out.println(text[0][0].getStr());
        for (int col = 0; col < text.length; col++){
            for (int row = 0; row < text[0].length; row++){
                SpecialText get = text[col][row];
                if (get != null){
                    g.setColor(get.getBackgroundColor());
                    g.fillRect((col * 10), (row * 15) + 1, 10, 15);
                    g.setColor(get.makeInfluencedForegroundColor(overallForeGround));
                    g.drawString(get.getStr(), col * 10, (row * 15) + 15);
                }
            }
        }
    }

    private class frameResetTimer extends TimerTask {
        int iter = 0;
        int number = 0;
        public void run(){
            number++;
            if (iter < 46 && number > 10){
                text[iter][0] = new SpecialText(String.valueOf(iter % 10));
                iter++;
                number = 0;
            }
            repaint();
        }
    }
}

