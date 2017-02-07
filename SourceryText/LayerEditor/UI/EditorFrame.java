package SourceryText.LayerEditor.UI;

import SourceryText.ColoredTextMatrix;
import SourceryText.SpecialText;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jared on 1/29/2017.
 */
public class EditorFrame extends JFrame{

    public ColoredTextMatrix viewMatrix;
    EditorToolbar toolbar;
    EditorSidebar sidebar;

    SpecialTextMaker maker;

    private Container c = getContentPane();

    Color backgroundColor = new Color(180, 180, 173);

    public EditorFrame (){
        SpecialText[][] testDisplay = new SpecialText[46][28];
        for (int ii = 0; ii < testDisplay.length; ii++){
            testDisplay[ii][0] = new SpecialText(String.valueOf(ii % 10));
        }
        for (int ii = 0; ii < testDisplay[0].length; ii++){
            testDisplay[0][ii] = new SpecialText(String.valueOf(ii % 10));
        }

        setBackground(backgroundColor);

        viewMatrix = new ColoredTextMatrix(510, 570);
        viewMatrix.setDisplay(testDisplay);

        //setMinimumSize(new Dimension(700, 640));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sourcery Text Layer Editor");
        setResizable(true); // false

        c.setLayout(new BorderLayout());
        c.add(viewMatrix, BorderLayout.CENTER);

        toolbar = new EditorToolbar(0, 572, 510);
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
        viewMatrix.repaint();
        repaint();
        pack();
    }
}
