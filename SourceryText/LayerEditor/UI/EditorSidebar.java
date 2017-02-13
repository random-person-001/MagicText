package SourceryText.LayerEditor.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jared on 06-Feb-17.
 */
public class EditorSidebar extends JPanel {

    EditorSidebar() {
        setPreferredSize(new Dimension(200, 540));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(170, 170, 170));

        g.fill3DRect(0, 0, getWidth(), getHeight(), false);
        /*
        g.setColor(new Color(200, 200, 200));
        g.fill3DRect(5, getHeight() - 35, 30, 30, true);
        g.fill3DRect(5, getHeight() - 70, 30, 30, true);
        g.fill3DRect(5, getHeight() - 105, 30, 30, false);
        */
    }
}
