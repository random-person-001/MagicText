package SourceryText.LayerEditor.UI;

import SourceryText.Layer;
import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.TimerTask;
import java.util.Timer;

/**
 * Created by Jared on 2/7/2017.
 */
public class LayerViewWindow extends JComponent implements MouseListener, MouseMotionListener, KeyListener{

    Layer image;
    EditorFrame owner;

    int camX = 0;
    int camY = 0;

    public LayerViewWindow (EditorFrame creator){
        setPreferredSize(new Dimension(650, 570));

        setFont(new Font("Monospaced", Font.PLAIN, 20));

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setFocusable(true);

        owner = creator;

        Timer dragListener = new Timer();
        dragListener.scheduleAtFixedRate(new DisplayUpdateTimer(), 50, 25);
    }

    void setImage(Layer setLayer){
        image = setLayer;
        System.out.println(image);
    }

    private int mouseX = 0;
    private int mouseY = 0;
    private boolean cameraMoving = false;

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
                        g.fillRect((row * char_xspacing) + camX, ((col - 1) * char_yspacing) + camY + 2, char_xspacing, char_yspacing);
                    }
                }
            }
        }
        if (image != null) {
            for (int col = 0; col < image.getColumns(); col++) {
                for (int row = 0; row < image.getRows(); row++) {
                    SpecialText get = image.getSpecTxt(row, col);
                    if (get != null && !get.getStr().equals("ñ")){
                        g.setColor(get.getForegroundColor());
                        g.drawString(get.getStr(), (row * char_xspacing) + camX, (col * char_yspacing) + camY);
                    }
                }
            }
        }
        g.setColor(Color.WHITE);
        if (!cameraMoving) {
            g.drawRect(calculateSnappedMousePos(mouseX, camX, 14) + camX, calculateSnappedMousePos(mouseY, camY, 20) + camY + 3, 14, 20);
        }
        g.setColor(new Color(102, 102, 0));
        g.drawRect(camX, camY - 18, image.getRows() * 14, image.getColumns() * 20);
    }

    private int calculateSnappedMousePos(int mouseVal, int cameraVal, int dim){
        int relativeVal = mouseVal - cameraVal;
        return relativeVal - (relativeVal % dim);
    }

    public SpecialText getSelectedSpecTxt(){
        return image.getSpecTxt(calculateSnappedMousePos(mouseX, camX, 14) / 14, (calculateSnappedMousePos(mouseY, camY, 20) / 20)+1);
    }

    private int mousePrevPosX = 0;
    private int mousePrevPosY = 0;

    private boolean drawing = false;
    private boolean clearing = false;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            cameraMoving = true;
            mousePrevPosX = e.getX();
            mousePrevPosY = e.getY();
            System.out.println("Drag start");
        } else if (e.getButton() == MouseEvent.BUTTON1){
            drawing = true;
        } else if (e.getButton() == MouseEvent.BUTTON2){
            clearing = true;
        }
    }

    @SuppressWarnings("SuspiciousNameCombination")
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            cameraMoving = false;
            System.out.println("Drag end");
            System.out.println(camX);
            System.out.println(camY);
        } else if (e.getButton() == MouseEvent.BUTTON1){
            drawing = false;
        } else if (e.getButton() == MouseEvent.BUTTON2){
            clearing = false;
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
        if (cameraMoving) {
            camX += e.getX() - mousePrevPosX;
            camY += e.getY() - mousePrevPosY;
            mousePrevPosX = e.getX();
            mousePrevPosY = e.getY();
        } else {
            mouseX = e.getX();
            mouseY = e.getY();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        System.out.println("Key pressed  " + keyEvent.getKeyChar());
        if (keyEvent.getKeyChar() == 'c'){
            System.out.println("Setting bar value this");
            owner.toolbar.setSelectedSpecTxt(getSelectedSpecTxt());
            owner.toolbar.repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        keyPressed(keyEvent);
    }

    private class DisplayUpdateTimer extends TimerTask {
        @Override
        public void run() {
            if (drawing){
                image.setSpecTxt(calculateSnappedMousePos(mouseX, camX, 14) / 14, (calculateSnappedMousePos(mouseY, camY, 20) / 20) + 1, owner.toolbar.getSpecTxt());
            } else if (clearing){
                image.setSpecTxt(calculateSnappedMousePos(mouseX, camX, 14) / 14, (calculateSnappedMousePos(mouseY, camY, 20) / 20) + 1, new SpecialText(" "));
            }
            repaint();
        }
    }
}
