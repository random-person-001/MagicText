package SourceryText.LayerEditor.UI;

import SourceryText.Layer;
import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Jared on 2/7/2017.
 */
public class LayerViewWindow extends JComponent implements MouseListener, MouseMotionListener{

    Layer image;

    int camX = 0;
    int camY = 0;

    public LayerViewWindow (){
        setPreferredSize(new Dimension(650, 570));

        setFont(new Font("Monospaced", Font.PLAIN, 20));

        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);

        Timer dragListener = new Timer();
        dragListener.scheduleAtFixedRate(new DisplayUpdateTimer(), 50, 25);
    }

    public void setImage(Layer setLayer){
        image = setLayer;

        System.out.println(image);
    }

    int mouseX = 0;
    int mouseY = 0;
    boolean displayCursorBox = true;

    @Override
    public void paintComponent(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        int char_xspacing = 14;
        int char_yspacing = 20;
        if (image != null) {
            for (int col = 0; col < image.getColumns(); col++) {
                for (int row = 0; row < image.getRows(); row++) {
                    SpecialText get = image.getSpecTxt(row, col);
                    if (get != null){
                        g.setColor((get.getBackgroundColor()));
                        g.fillRect((row * char_xspacing) - camX, ((col -1 ) * char_yspacing) - camY, char_xspacing, char_yspacing);
                        g.setColor(get.getForegroundColor());
                        g.drawString(get.getStr(), (row * char_xspacing) - camX, (col * char_yspacing) - camY);
                    }
                }
            }
        }
        g.setColor(Color.WHITE);
        if (displayCursorBox)
            g.drawRect((mouseX - 7) - ((mouseX - 7) % 14) - (camX % 14), (mouseY - 10) - ((mouseY - 10) % 20) - (camY % 20), 14, 20);
    }

    private int mousePrevPosX = 0;
    private int mousePrevPosY = 0;


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            displayCursorBox = false;
            mousePrevPosX = e.getX();
            mousePrevPosY = e.getY();
            System.out.println("Drag start");
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            displayCursorBox = true;
            System.out.println("Drag end");
            System.out.println(camX);
            System.out.println(camY);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        camX -= e.getX() - mousePrevPosX;
        camY -= e.getY() - mousePrevPosY;
        mousePrevPosX = e.getX();
        mousePrevPosY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    private class DisplayUpdateTimer extends TimerTask {

        @Override
        public void run() {
            repaint();
        }
    }
}
