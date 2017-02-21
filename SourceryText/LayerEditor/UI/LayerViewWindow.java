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

        //Drawing of layer

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

        //End of drawing layer

        //Tool drawing

        g.setColor(Color.WHITE);
        if (!cameraMoving && selectionStage == 0) { //Draw cursor rectangle
            g.drawRect(calculateSnappedMouseXPos() + camX, calculateSnappedMouseYPos() + camY + 3, 14, 20);
        } else if (!cameraMoving) { //Don't draw any tool art while camera is being moved
            if ((getToolID() == 4 || getToolID() == 5)) { //Art for Rect / Select tool
                if (selectionStage == 1 && calculateSnappedMouseXPos() >= selectedX && calculateSnappedMouseYPos() >= selectedY) { //Makes sure the rectangle is not a negative width/height
                    g.setColor(new Color(255, 175, 0));
                    g.drawRect(selectedX, selectedY + 3, calculateSnappedMouseXPos() - selectedX + camX + 14, calculateSnappedMouseYPos() - selectedY + camY + 20);
                } else if (selectionStage == 2 && selectedWidth > 0 && selectedHeight > 0) { //Only used for Select tool; draws 'frozen' rectangle after selecting
                    g.setColor(new Color(255, 175, 0));
                    g.drawRect(selectedX, selectedY + 3, selectedWidth, selectedHeight);
                }
            } else if (getToolID() == 3) { //Art for line tool
                g.setColor(new Color(50, 175, 255)); //Draws blue line from start to end point
                g.drawLine(selectedX + 7, selectedY + 10, calculateSnappedMouseXPos() + camX + 7, calculateSnappedMouseYPos() + camY + 10);
            }
        }
        g.setColor(new Color(102, 102, 0));
        g.drawRect(camX, camY - 18, image.getRows() * 14, image.getColumns() * 20); //Layer border
    }

    /**
     * Calculates the by-pixel x coordinate relative to the corner of the layer (which is also the camera coordinate)
     * From there you can
     *   * + camX | to get draw value
     *   * / dimX (=14) | get layer x coordinate
     *
     * @return the calculated x value
     */
    private int calculateSnappedMouseXPos(){
        int relativeVal = mouseX - camX;
        if (relativeVal < 0)
            return 0;
        if (relativeVal > image.getRows() * 14)
            return (image.getRows() - 1) * 14;
        return relativeVal - (relativeVal % 14);
    }

    /**
     * Calculates the by-pixel y coordinate relative to the corner of the layer (which is also the camera coordinate)
     * From there you can
     *   * + camY | to get draw value
     *   * / dimY (=20) | get layer y coordinate
     *
     * @return the calculated x value
     */
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

    private int selectedX = 0;
    private int selectedY = 0;
    private int selectionStage = 0;
    private int selectedWidth = 0;
    private int selectedHeight = 0;

    private boolean drawing = false;
    private boolean clearing = false;

    private int getToolID(){
        if (owner.sidebar != null)
            return owner.sidebar.getToolID();
        else
            return 0;
    }

    private void fillTool(int row, int col){ //Outside of recursion
        placeSpreadingText(row, col); //Starts recursion
    }

    private void spreadText(int row, int col){
        placeSpreadingText(row - 1, col); //Does the placeSpreadingText operation on adjacent tiles
        placeSpreadingText(row + 1, col);
        placeSpreadingText(row, col - 1);
        placeSpreadingText(row, col + 1);
    }

    private void placeSpreadingText(int row, int col){ // below: Makes sure the text is spreading into the correct area and the space it's spreading into is empty
        if (row >= 0 && col >= 0 && row < image.getRows() && col < image.getColumns() && image.getSpecTxt(row, col).getStr().equals(" ")){
            image.setSpecTxt(row, col, owner.toolbar.getSpecTxt()); //Sets down the text
            spreadText(row, col); //Then recurses on to the new point to continue spreading the text
        }
    }

    /**
     * Draws a rectangle on the layer. Not trig-based or otherwise math-heavy
     * @param filled Whether the rectangle should be filled
     */
    private void drawRectangle(boolean filled){
        int layerX = (selectedX - camX) / 14;
        int layerY = (selectedY - camY) / 20 + 1;
        int boxEndX = (calculateSnappedMouseXPos() / 14);
        int boxEndY = (calculateSnappedMouseYPos() / 20) + 1;
        System.out.printf("Rectangle:\n cornerX: %1$d\n cornerY: %2$d\n endX: %3$d\n endY: %4$d\n", layerX, layerY, boxEndX, boxEndY);
        for (int row = layerX; row <= boxEndX; row++){
            for (int col = layerY; col <= boxEndY; col++){
                if (filled || row == layerX || row == boxEndX || col == layerY || col == boxEndY){
                    image.setSpecTxt(row, col, owner.toolbar.getSpecTxt());
                }
            }
        }
    }

    /**
     * Trig-based algorithm,
     *   1) Calculates angle and distance using atan2() and distance formula respectively
     *   2) Iterates for the distance of the line, increasing the radius each iteration then placing text using sin() and cos()
     */
    private void drawLine(){
        int x1 = (selectedX - camX) / 14;
        int y1 = (selectedY - camY) / 20 + 1;
        int x2 = (calculateSnappedMouseXPos() / 14);
        int y2 = (calculateSnappedMouseYPos() / 20) + 1;
        double angle = Math.atan2(y2 - y1, x2 - x1); //Start of trig stuff
        int dist = (int)Math.round(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2))); //Distance formula to get length of line
        System.out.printf("Line:\n x1: %1$d\n y1: %2$d\n x2: %3$d\n y2: %4$d\n slope: %5$f\n", x1, y1, x2, y2, angle);
        for (int ix = 0; ix <= dist; ix++){ //Moves along line at dr = 1 each iteration and places down text at coordinates nearest to the line
            image.setSpecTxt((int)Math.round(ix * Math.cos(angle)) + x1, (int)Math.round(ix * Math.sin(angle)) + y1, owner.toolbar.getSpecTxt());
        }
    }

    private SpecialText[][] clipBoard;

    private void copySelection(){
        /*
        int layerX = (selectedX - camX) / 14;
        int layerY = (selectedY - camY) / 20 + 1;
        int boxEndX = (calculateSnappedMouseXPos() / 14);
        int boxEndY = (calculateSnappedMouseYPos() / 20) + 1;
        System.out.printf("Rectangle:\n cornerX: %1$d\n cornerY: %2$d\n endX: %3$d\n endY: %4$d\n", layerX, layerY, boxEndX, boxEndY);
        for (int row = layerX; row <= boxEndX; row++){
            for (int col = layerY; col <= boxEndY; col++){
                image.setSpecTxt(row, col, owner.toolbar.getSpecTxt());
            }
        }
        */
        System.out.println("I copy!");
    }

    public void toolReset(){
        selectionStage = 0;
        drawing = false;
        clearing = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) { //Camera panning
            cameraMoving = true;
            mousePrevPosX = e.getX();
            mousePrevPosY = e.getY();
            System.out.println("Drag start");
        } else if (e.getButton() == MouseEvent.BUTTON1){ //Use tool
            drawing = true;
            if (getToolID() == 1){ //Pick tool
                owner.toolbar.receiveSpecialText(image.getSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1));
            } else if (getToolID() == 2){ //Fill tool
                fillTool(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1);
            } else if (getToolID() >= 3 || getToolID() <= 5){ //Move/Rect Tool area selection and Line tool
                if (selectionStage == 0) { //Sets the x and y values of when you first click w/ the tool
                    selectedX = calculateSnappedMouseXPos() + camX;
                    selectedY = calculateSnappedMouseYPos() + camY;
                    selectionStage++;
                }
            }
        } else if (e.getButton() == MouseEvent.BUTTON2){ //Delete button
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
            if (getToolID() == 3){
                drawLine();
                selectionStage = 0;
            } else if (getToolID() == 4){
                drawRectangle(owner.sidebar.getIsRectFilled());
                selectionStage = 0;
            } else if (getToolID() == 5){
                if (selectionStage == 1){
                    selectedWidth = calculateSnappedMouseXPos() - selectedX + camX + 14;
                    selectedHeight = calculateSnappedMouseYPos() - selectedY + camY + 20;
                    selectionStage++;
                } else {
                    //moveStuff()
                    selectionStage = 0;
                }
            }
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

    private boolean ctrlHeld = false;

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        ctrlHeld = keyEvent.getKeyCode() == KeyEvent.VK_CONTROL;
        if (ctrlHeld) {
            copySelection();
        }
        System.out.println(ctrlHeld);
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        ctrlHeld = false;
        System.out.println("No ctrl anymore");
    }

    private class DisplayUpdateTimer extends TimerTask {
        @Override
        public void run() {
            if (getToolID() == 0) { //Pencil Tool
                if (drawing) {
                    image.setSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1, owner.toolbar.getSpecTxt());
                }
            }
            if (clearing) {
                image.setSpecTxt(calculateSnappedMouseXPos() / 14, (calculateSnappedMouseYPos() / 20) + 1, new SpecialText(" "));
            }
            repaint();
        }
    }
}
