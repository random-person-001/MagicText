package SourceryText.LayerEditor.UI;

import SourceryText.ColoredTextMatrix;
import SourceryText.SpecialText;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by Jared on 1/30/2017.
 */
public class SpecialTextMaker extends JFrame {
    private Container c = getContentPane();

    public SpecialTextMaker (){
        setBackground(new Color(180, 180, 173));

        setVisible(true);

        setBounds(400, 400, 540, 600);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sourcery Text Layer Editor");
        setResizable(false);

        addColorSlider("R:", c, new JSlider(), new Color(255, 200, 200));
        addColorSlider("H:", c, new JSlider(), new Color(255, 200, 255));
        addColorSlider("G:", c, new JSlider(), new Color(200, 255, 200));
        addColorSlider("S:", c, new JSlider(), new Color(255, 255, 200));
        addColorSlider("B:", c, new JSlider(), new Color(200, 200, 255));
        addColorSlider("B:", c, new JSlider(), new Color(255, 255, 255));


        c.setLayout(new FlowLayout());

        c.validate();
    }
    
    private void addColorSlider (String sliderName, Container c, JSlider toAdd, Color bkgHue){
        toAdd.setMinimum(0);
        toAdd.setMaximum(255);
        toAdd.setValue(255);

        toAdd.setMinorTickSpacing(5);
        toAdd.setMajorTickSpacing(50);
        toAdd.setPaintTicks(true);
        toAdd.setPaintLabels(true);

        JTextField toAddLabel = new JTextField(sliderName);
        toAddLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
        toAddLabel.setBackground(bkgHue);
        toAddLabel.setEditable(false);

        JTextField manualEnter = new JTextField(String.valueOf(toAdd.getValue()),2);

        c.add(toAddLabel);
        c.add(toAdd);
        c.add(manualEnter);
    }
}
