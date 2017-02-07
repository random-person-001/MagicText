package SourceryText.LayerEditor.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jared on 06-Feb-17.
 */
public class EditorSidebar extends JPanel {

    public EditorSidebar() {
        setPreferredSize(new Dimension(100, 540));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(125, 125, 125));

        //g.fill3DRect(getX(), getY(), getWidth(), getHeight(), false);

        // this won't work because Java draws text with the Y at the baseline, which is the line on which text rests,
        // and above where letters like 'y' and 'g' descend downward. By using getY(), you are saying the baseline is
        // the top of the draw area, so most of the text is drawn off the screen! You need to add the font height.
        //g.drawString("Testing JPanel whatnots", getX(), getY());
        g.drawString("Testing JPanel whatnots", getX(), getY() + 18);
    }
}
