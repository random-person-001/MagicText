package SourceryText.LayerEditor.UI;

import SourceryText.ColoredTextMatrix;
import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jared on 1/29/2017.
 */
public class EditorFrame extends JFrame{

    public ColoredTextMatrix viewMatrix;
    EditorToolbar toolbar;

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

        setBounds(100, 100, 700, 640);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sourcery Text Layer Editor");
        setResizable(false);

        c.add(viewMatrix);

        viewMatrix.text = testDisplay;

        toolbar = new EditorToolbar(0, 572, 510);
        toolbar.owner = this;

        c.add(toolbar);

        c.validate();

        setVisible(true);

        repaintComponents();

        maker = new SpecialTextMaker();
        maker.owner = this;
    }

    protected void repaintComponents(){
        toolbar.repaint();
        viewMatrix.repaint();
    }
}
