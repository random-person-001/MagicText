package SourceryText.LayerEditor.UI;

import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jared on 1/29/2017.
 */
public class EditorToolbar extends JComponent implements MouseListener{
    private SpecialText[] toolBarContents = new SpecialText[17];
    private int toolBarCursor = 4;

    EditorFrame owner;

    public EditorToolbar(){
        String[] defaultChars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q"};

        for (int ii = 0; ii < toolBarContents.length; ii++){
            toolBarContents[ii] = new SpecialText(defaultChars[ii]);
        }

        int width = 710;

        // this won't work! getWidth() and getHeight() return 0 until you pack() or validate()
        //setPreferredSize(new Dimension(getWidth(), getHeight()));
        setPreferredSize(new Dimension(width, 60));
        setMinimumSize(new Dimension(width, 60));

        setFont(new Font("Monospaced", Font.PLAIN, width / toolBarContents.length));

        addMouseListener(this);
    }

    protected void paintComponent(Graphics g){
        int bottom = getHeight();
        g.setColor(new Color(125, 125, 125));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawLine(0, 0, getWidth(), 0);

        int xLength = (getWidth() / toolBarContents.length) - 4;
        int yLength = (int)(xLength * 1.1);
        for (int id = 0; id < toolBarContents.length; id++){
            boolean isSelected = (id == toolBarCursor);
            int boxCornerX = ((xLength + 4) * id);
            int boxCornerY = (isSelected) ? 1 : 2;

            g.setColor(new Color(180, 180, 173));
            if (isSelected) {
                g.fillRect(boxCornerX - 4, boxCornerY, xLength + 9, bottom);
            }
            g.draw3DRect(boxCornerX, boxCornerY + 1, xLength, yLength + 1, true);

            g.setColor(toolBarContents[id].getBackgroundColor());
            g.fillRect(boxCornerX + 1, boxCornerY + 2, xLength - 1, yLength);

            g.setColor(toolBarContents[id].getForegroundColor());
            g.drawString(toolBarContents[id].getStr(), boxCornerX + (xLength / 4), boxCornerY + (int)(yLength / 1.35f));
        }
    }

    public void receiveSpecialText(SpecialText present){
        toolBarContents[toolBarCursor] = present;
        repaint();
    }

    public SpecialText getSpecTxt(){
        return toolBarContents[toolBarCursor];
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > 0 && e.getX() < getWidth() && e.getY() > 0){
            toolBarCursor = (e.getX()) / (getWidth() / toolBarContents.length);
            repaint();
            owner.repaintComponents();
            if (e.getButton() == MouseEvent.BUTTON3){
                owner.maker.setVisible(true);
                owner.maker.setFinalChar(toolBarContents[toolBarCursor]);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setSelectedSpecTxt(SpecialText selectedSpecTxt) {
        this.toolBarContents[toolBarCursor] = selectedSpecTxt;
    }
}