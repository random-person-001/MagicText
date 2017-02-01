package SourceryText.LayerEditor.UI;

import SourceryText.ColoredTextMatrix;
import SourceryText.SpecialText;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by Jared on 1/30/2017.
 */
public class SpecialTextMaker extends JFrame implements ChangeListener{
    private Container c = getContentPane();

    private JSlider redSlider = new JSlider();
    private JSlider blueSlider = new JSlider();
    private JSlider greenSlider = new JSlider();
    private JSlider hueSlider = new JSlider();
    private JSlider satSlider = new JSlider();
    private JSlider briSlider = new JSlider();

    private JTextField redLabel = new JTextField();
    private JTextField blueLabel = new JTextField();
    private JTextField greenLabel = new JTextField();
    private JTextField hueLabel = new JTextField();
    private JTextField satLabel = new JTextField();
    private JTextField briLabel = new JTextField();

    private JTextField setSpecTxt = new JTextField();

    public SpecialTextMaker (){
        setBackground(new Color(180, 180, 173));

        setVisible(true);

        setBounds(400, 400, 540, 260);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SpecialText Creator");
        setResizable(false);

        redSlider.setName("red");
        blueSlider.setName("blue");
        greenSlider.setName("green");
        hueSlider.setName("hue");
        satSlider.setName("sat");
        briSlider.setName("bright");

        c.setLayout(new FlowLayout());

        addColorSlider("R:", c, redSlider, new Color(255, 200, 200), redLabel);
        addColorSlider("H:", c, hueSlider, new Color(255, 200, 255), hueLabel);
        addColorSlider("G:", c, greenSlider, new Color(200, 255, 200), greenLabel);
        addColorSlider("S:", c, satSlider, new Color(204, 204, 204), satLabel);
        addColorSlider("B:", c, blueSlider, new Color(200, 200, 255), blueLabel);
        addColorSlider("B:", c, briSlider, new Color(255, 255, 255), briLabel);

        JTextField setSpecTxtLabel = new JTextField("Char:",3);
        setSpecTxtLabel.setEditable(false);
        c.add(setSpecTxtLabel);

        setSpecTxt.setEditable(true);
        setSpecTxt.setColumns(1);
        c.add(setSpecTxt);

        c.validate();
    }
    
    private void addColorSlider (String sliderName, Container c, JSlider toAdd, Color bkgHue, JTextField manualEnter){
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

        manualEnter.setText(String.valueOf(toAdd.getValue()));
        manualEnter.setColumns(2);

        toAdd.addChangeListener(this);

        c.add(toAddLabel);
        c.add(toAdd);
        c.add(manualEnter);
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        switch(source.getName()){
            case "red":
                redLabel.setText(String.valueOf(source.getValue()));
                break;
            case "blue":
                blueLabel.setText(String.valueOf(source.getValue()));
                break;
            case "green":
                greenLabel.setText(String.valueOf(source.getValue()));
                break;
            case "hue":
                hueLabel.setText(String.valueOf(source.getValue()));
                updateRGB();
                break;
            case "sat":
                satLabel.setText(String.valueOf(source.getValue()));
                updateRGB();
                break;
            case "bright":
                briLabel.setText(String.valueOf(source.getValue()));
                updateRGB();
                break;
        }
    }

    private void updateRGB(){
        Color newRGB = new Color(Color.HSBtoRGB(hueSlider.getValue() / 255f,satSlider.getValue() / 255f,briSlider.getValue()/ 255f));
        redSlider.setValue(newRGB.getRed());
        greenSlider.setValue(newRGB.getGreen());
        blueSlider.setValue(newRGB.getBlue());
    }
}
