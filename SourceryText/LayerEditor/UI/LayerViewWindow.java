package SourceryText.LayerEditor.UI;

import SourceryText.Layer;
import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Jared on 2/7/2017.
 */
public class LayerViewWindow extends JComponent implements MouseListener{

    Layer image;

    int camX = 0;
    int camY = 0;

    public LayerViewWindow (){
        setPreferredSize(new Dimension(540, 480));

        setFont(new Font("Monospaced", Font.PLAIN, 20));

        addMouseListener(this);
        setFocusable(true);

        Timer dragListener = new Timer();
        dragListener.scheduleAtFixedRate(new ViewDragTracker(), 50, 50);
    }

    public void setImage(Layer setLayer){
        image = setLayer;

        System.out.println(image);
    }

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
    }

    boolean movingCamera = false;
    int mousePrevPosX = 0;
    int mousePrevPosY = 0;


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            movingCamera = true;
            mousePrevPosX = (int)MouseInfo.getPointerInfo().getLocation().getX();
            mousePrevPosY = (int)MouseInfo.getPointerInfo().getLocation().getY();
            System.out.println("Drag start");
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            movingCamera = false;
            System.out.println("Drag end");
            System.out.println(camX);
            System.out.println(camY);
            repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private class ViewDragTracker extends TimerTask {

        @Override
        public void run() {
            if (movingCamera){
                camX -= MouseInfo.getPointerInfo().getLocation().getX() - mousePrevPosX;
                camY -= MouseInfo.getPointerInfo().getLocation().getY() - mousePrevPosY;
                mousePrevPosX = (int)MouseInfo.getPointerInfo().getLocation().getX();
                mousePrevPosY = (int)MouseInfo.getPointerInfo().getLocation().getY();
                repaint();
            }
        }
    }
}
