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
    private int cornerX;
    private int cornerY;
    private int toolbarWidth;

    private SpecialText[] toolBarContents = new SpecialText[17];
    private int toolBarCursor = 4;

    EditorFrame owner;

    public EditorToolbar(int setX, int setY, int setWidth){
        cornerX = setX;
        cornerY = setY;
        toolbarWidth = setWidth;
        String[] defaultChars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q"};

        for (int ii = 0; ii < toolBarContents.length; ii++){
            toolBarContents[ii] = new SpecialText(defaultChars[ii]);
        }

        setFont(new Font("Monospaced", Font.PLAIN, toolbarWidth / toolBarContents.length));

        Container c = new Container();

        JSlider redSlider = new JSlider(0, 255, 0);
        c.add(redSlider);

        addMouseListener(this);
    }

    protected void paintComponent(Graphics g){
        int bottom = getHeight() - cornerY;
        g.setColor(new Color(125, 125, 125));
        g.fillRect(cornerX, cornerY, toolbarWidth, bottom);
        g.drawLine(cornerX + toolbarWidth, cornerY, cornerX + toolbarWidth, getHeight());

        int length = (toolbarWidth / toolBarContents.length) - 4;
        for (int id = 0; id < toolBarContents.length; id++){
            int boxCornerX = (length + 4) * id;

            boolean isSelected = (id == toolBarCursor);
            if (!isSelected) {
                g.draw3DRect(boxCornerX, cornerY + 1, length, length + 1, true);
            } else {
                g.setColor(new Color(180, 180, 173));
                g.fillRect(boxCornerX - 3, cornerY, length + 7, bottom);
                g.draw3DRect(boxCornerX, cornerY + 1, length, length + 1, true);
            }

            g.setColor(toolBarContents[id].getBackgroundColor());
            g.fillRect(boxCornerX + 1, cornerY + 2, length - 1, length);

            g.setColor(toolBarContents[id].getForegroundColor());
            if (toolBarContents[id].getStr().equals("_"))
                g.drawString(toolBarContents[id].getStr(), boxCornerX + 2, cornerY + length - 6);
            else
                g.drawString(toolBarContents[id].getStr(), boxCornerX + 2, cornerY + length - 4);
        }
    }

    public void receiveSpecialText(SpecialText present){
        toolBarContents[toolBarCursor] = present;
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > cornerX && e.getX() < toolbarWidth + cornerX && e.getY() > cornerY){
            toolBarCursor = (e.getX() - cornerX) / (toolbarWidth / toolBarContents.length);
            repaint();
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
}