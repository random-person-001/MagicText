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
            g.drawRect(calculateSnappedMouseXPos() + camX, calculateSnappedMouseYPos() + camY + 3, 14, 20);
        }
        g.setColor(new Color(102, 102, 0));
        g.drawRect(camX, camY - 18, image.getRows() * 14, image.getColumns() * 20);
    }

    private int calculateSnappedMouseXPos(){
        int relativeVal = mouseX - camX;
        if (relativeVal < 0)
            return 0;
        if (relativeVal > image.getRows() * 14)
            return (image.getRows() - 1) * 14;
        return relativeVal - (relativeVal % 14);
    }

    private int calculateSnappedMouseYPos(){
        int relativeVal = mouseY - camY;
        if (relativeVal < 0)
            return -20;
        if (relativeVal > image.getColumns() * 20)
            return (image.getColumns() - 2) * 20;
        return relativeVal - (relativeVal % 20);
    }

    public SpecialText getSelectedSpecTxt(){
        return image.getSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20)+1);
    }

    private int mousePrevPosX = 0;
    private int mousePrevPosY = 0;

    private boolean drawing = false;
    private boolean clearing = false;

    private int getToolID(){
        if (owner.sidebar != null)
            return owner.sidebar.getToolID();
        else
            return 0;
    }

    private void fillTool(int row, int col){
        spreadText(row, col);
    }

    private void spreadText(int row, int col){
        placeSpreadingText(row - 1, col);
        placeSpreadingText(row + 1, col);
        placeSpreadingText(row, col - 1);
        placeSpreadingText(row, col + 1);
    }

    private void placeSpreadingText(int row, int col){
        if (row >= 0 && col >= 0 && row < image.getRows() && col < image.getColumns() && image.getSpecTxt(row, col).equals(new SpecialText(" "))){
            image.setSpecTxt(row, col, owner.toolbar.getSpecTxt());
            spreadText(row, col);
        }
    }

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
            if (getToolID() == 1){ //Pick tool
                owner.toolbar.receiveSpecialText(image.getSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1));
            } else if (getToolID() == 2){ //Fill tool
                fillTool(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1);
            }
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
            if (getToolID() == 0) { //Pencil Tool
                if (drawing) {
                    image.setSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1, owner.toolbar.getSpecTxt());
                } else if (clearing) {
                    image.setSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1, new SpecialText(" "));
                }
            }
            repaint();
        }
    }
}
