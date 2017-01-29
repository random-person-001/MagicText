package SourceryText.LayerEditor.UI;

import SourceryText.ColoredTextMatrix;
import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jared on 1/29/2017.
 */
public class EditorFrame extends JFrame {

    public ColoredTextMatrix viewMatrix;
    private Container c = getContentPane();

    public EditorFrame (){
        SpecialText[][] testDisplay = new SpecialText[46][28];
        for (int ii = 0; ii < testDisplay.length; ii++){
            testDisplay[ii][0] = new SpecialText(String.valueOf(ii % 10));
        }
        for (int ii = 0; ii < testDisplay[0].length; ii++){
            testDisplay[0][ii] = new SpecialText(String.valueOf(ii % 10));
        }

        viewMatrix = new ColoredTextMatrix(510, 570);

        setVisible(true);

        setBounds(100, 100, 700, 800);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sourcery Text Layer Editor");
        setResizable(false);

        c.add(viewMatrix);
        c.validate();

        viewMatrix.text = testDisplay;

        viewMatrix.repaint();
    }
}
