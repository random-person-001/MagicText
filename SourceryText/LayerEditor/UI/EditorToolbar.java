package SourceryText.LayerEditor.UI;

import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jared on 1/29/2017.
 */
public class EditorToolbar extends JPanel {
    int cornerX;
    int cornerY;
    int toolbarWidth;

    SpecialText[] toolBarContents = new SpecialText[17];
    int toolBarCursor = 0;

    public EditorToolbar(int setX, int setY, int setWidth){
        cornerX = setX;
        cornerY = setY;
        toolbarWidth = setWidth;
        String[] defaultChars = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q"};

        for (int ii = 0; ii < toolBarContents.length; ii++){
            toolBarContents[ii] = new SpecialText(defaultChars[ii]);
        }
    }

    protected void paintComponent(Graphics g){
        g.setColor(new Color(150, 150, 150));
        g.drawLine(cornerX, cornerY, cornerX + toolbarWidth, cornerY);
        g.drawLine(cornerX + toolbarWidth, cornerY, cornerX + toolbarWidth, getHeight());

        setFont(new Font("Monospaced", Font.PLAIN, toolbarWidth / toolBarContents.length));

        for (int id = 0; id < toolBarContents.length; id++){
            int length = toolbarWidth / toolBarContents.length;
            int boxCornerX = length * id;
            g.setColor(new Color(150, 150, 150));
            g.drawRect(boxCornerX, cornerY, length, length);
            g.setColor(toolBarContents[id].getForegroundColor());
            g.drawString(toolBarContents[id].getStr(), boxCornerX + 2, cornerY + length - 6);
        }
    }
}