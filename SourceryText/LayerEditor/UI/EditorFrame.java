package SourceryText.LayerEditor.UI;

import SourceryText.Layer;
import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jared on 1/29/2017.
 */
public class EditorFrame extends JFrame{

    public LayerViewWindow viewWindow;
    EditorToolbar toolbar;
    EditorSidebar sidebar;

    SpecialTextMaker maker;

    private Container c = getContentPane();

    Color backgroundColor = new Color(180, 180, 173);

    public EditorFrame (){
        Layer testDisplay = new Layer(new String[46][28]);
        for (int col = 0; col < testDisplay.getColumns(); col++){
            for (int row = 0; row < testDisplay.getRows(); row++) {
                testDisplay.setSpecTxt(row, col, new SpecialText(String.valueOf(col % 10), new Color(255, 255, 255 - (row+col)), new Color((row+col),(row+col),(row+col))));
            }
        }

        setBackground(backgroundColor);

        viewWindow = new LayerViewWindow();
        viewWindow.setImage(testDisplay);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sourcery Text Layer Editor");
        setResizable(true); // false

        c.setLayout(new BorderLayout());
        c.add(viewWindow, BorderLayout.CENTER);

        toolbar = new EditorToolbar();
        toolbar.owner = this;
        //toolbar.setMinimumSize(new Dimension(510, 60));

        c.add(toolbar, BorderLayout.SOUTH);

        sidebar = new EditorSidebar();
        //sidebar.setMinimumSize(new Dimension(100, 640));

        c.add(sidebar, BorderLayout.EAST);

        pack();
        c.validate();

        setVisible(true);

        maker = new SpecialTextMaker();
        maker.owner = this;
    }

    protected void repaintComponents(){
        toolbar.repaint();
        viewWindow.repaint();
        repaint();
        pack();
    }
}
