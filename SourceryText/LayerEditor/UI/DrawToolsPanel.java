package SourceryText.LayerEditor.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jared on 15-Feb-17.
 */
public class DrawToolsPanel extends JPanel {
    private EditorFrame editorFrame;

    public DrawToolsPanel (EditorFrame editFrame){
        editorFrame = editFrame;

        setLayout(new GridLayout(3, 2));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tools"));
        setAlignmentX(0.0f);

        setMaximumSize(new Dimension(150, 200));

        add(new JButton("Pencil"));
        add(new JButton("Pick"));
        add(new JButton("Line"));
        add(new JButton("Rectangle"));
        add(new JButton("Fill"));
        add(new JButton("Ellipse"));
    }
}
