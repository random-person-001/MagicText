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
        g.setColor(new Color(160, 160, 160));

        g.fill3DRect(0, 0, getWidth(), getHeight(), false);
    }
}
