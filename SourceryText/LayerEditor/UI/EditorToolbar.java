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
    int toolBarCursor = 4;

    JSlider redSilder;
    JSlider greenSlider;
    JSlider blueSlider;

    public EditorToolbar(int setX, int setY, int setWidth){
        cornerX = setX;
        cornerY = setY;
        toolbarWidth = setWidth;
        String[] defaultChars = {"a","b","c","d","e","f","g","h","i","#","'","l","m","n","o","p","q"};

        for (int ii = 0; ii < toolBarContents.length; ii++){
            toolBarContents[ii] = new SpecialText(defaultChars[ii]);
        }

        setFont(new Font("Monospaced", Font.PLAIN, toolbarWidth / toolBarContents.length));

        Container c = new Container();

        JSlider redSlider = new JSlider(0, 255, 0);
        c.add(redSlider);
    }

    protected void paintComponent(Graphics g){
        g.setColor(new Color(150, 150, 150));
        g.drawLine(cornerX, cornerY, cornerX + toolbarWidth, cornerY);
        g.drawLine(cornerX + toolbarWidth, cornerY, cornerX + toolbarWidth, getHeight());

        //g.draw3DRect(cornerX + 50, cornerY + 50, 20, 20, true);

        for (int id = 0; id < toolBarContents.length; id++){
            int length = (toolbarWidth / toolBarContents.length) - 4;
            int boxCornerX = (length + 4) * id;

            boolean isSelected = (id == toolBarCursor);
            if (!isSelected) {
                g.setColor(new Color(150, 150, 150));
                g.draw3DRect(boxCornerX, cornerY + 1, length, length + 1, true);
            } else {
                g.setColor(Color.WHITE);
                g.drawRect(boxCornerX, cornerY + 1, length, length + 1);
                g.drawRect(boxCornerX - 1, cornerY, length + 2, length + 3);
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
}