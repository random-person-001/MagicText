package SourceryText.LayerEditor.UI;

import SourceryText.SpecialText;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

/**
 * Created by Jared on 1/30/2017.
 */
public class SpecialTextMaker extends JFrame implements ChangeListener, ActionListener{
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

    private JButton setForegroundButton = new JButton();
    private JButton setBackgroundButton = new JButton();
    private boolean settingForeground = true;

    private SpecialText finalChar = new SpecialText("", Color.WHITE, Color.BLACK);

    public SpecialTextMaker (){
        setBackground(new Color(180, 180, 173));

        setVisible(true);

        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setBounds(400, 400, 565, 260);

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SpecialText Creator");
        //setResizable(false);

        redSlider.setName("red");
        blueSlider.setName("blue");
        greenSlider.setName("green");
        hueSlider.setName("hue");
        satSlider.setName("sat");
        briSlider.setName("bright");

        c.setLayout(new FlowLayout());
        JPanel group = new JPanel(new FlowLayout());

        addColorSlider("R:", c, redSlider, new Color(255, 200, 200), redLabel, "m_red", 255);
        addColorSlider("H:", c, hueSlider, new Color(255, 200, 255), hueLabel, "m_hue", 360);
        addColorSlider("G:", c, greenSlider, new Color(200, 255, 200), greenLabel, "m_green", 255);
        addColorSlider("S:", c, satSlider, new Color(204, 204, 204), satLabel, "m_saturation", 100);
        addColorSlider("B:", c, blueSlider, new Color(200, 200, 255), blueLabel, "m_blue", 255);
        addColorSlider("B:", c, briSlider, new Color(255, 255, 255), briLabel, "m_brightness", 100);

        setSpecTxt.setEditable(true);
        setSpecTxt.setFont(new Font("Monospaced", Font.PLAIN, 30));
        setSpecTxt.setHorizontalAlignment(JTextField.CENTER);
        setSpecTxt.setPreferredSize(new Dimension(30, 30));
        group.add(setSpecTxt);

        setForegroundButton = new JButton("Fg");
        setForegroundButton.setPreferredSize(new Dimension(80, 30));
        setForegroundButton.setBackground(Color.WHITE);
        setForegroundButton.setActionCommand("foreground");
        setForegroundButton.addActionListener(this);
        setForegroundButton.setOpaque(true);
        setForegroundButton.setEnabled(false);

        setBackgroundButton = new JButton("Bg");
        setBackgroundButton.setPreferredSize(new Dimension(80, 30));
        setBackgroundButton.setBackground(Color.WHITE);
        setBackgroundButton.setActionCommand("background");
        setBackgroundButton.addActionListener(this);
        setBackgroundButton.setOpaque(true);
        setBackgroundButton.setEnabled(true);

        group.add(setForegroundButton);
        group.add(setBackgroundButton);

        JButton finishButton = new JButton("Finish");
        finishButton.setActionCommand("finish");

        group.add(finishButton);

        c.add(group);
        c.validate();

        Timer textTimer = new Timer();
        textTimer.scheduleAtFixedRate(new TextUpdate(), 10, 50);
    }
    
    private void addColorSlider (String sliderName, Container c, JSlider toAdd, Color bkgHue, JTextField manualEnter, String manualName, int maxValue){
        //JSlider stuff

        toAdd.setMinimum(0);
        toAdd.setMaximum(maxValue); //Setting min and max for the JSlider
        toAdd.setValue(maxValue);

        int majorSpacing = (maxValue / 5) - ((maxValue / 5) % 5);
        toAdd.setMinorTickSpacing((maxValue / 50)); //Spacing math
        toAdd.setMajorTickSpacing(majorSpacing);
        toAdd.setPaintTicks(true);
        toAdd.setPaintLabels(true);

        //JSlider label stuff (not the editable one)

        JTextField toAddLabel = new JTextField(sliderName);
        toAddLabel.setFont(new Font("Monospaced", Font.PLAIN, 15));
        toAddLabel.setBackground(bkgHue);
        toAddLabel.setEditable(false);

        //JSlider manual enter TextField

        manualEnter.setText(String.valueOf(toAdd.getValue()));
        manualEnter.setColumns(2);
        manualEnter.setName(manualName);

        toAdd.addChangeListener(this);

        //Putting it all together

        JPanel group = new JPanel(new FlowLayout());
        group.add(toAddLabel);
        group.add(toAdd);
        group.add(manualEnter);
        c.add(group);
    }

    private boolean changeTextBoxes = true;
    private boolean evaluateColor = true;

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        if (changeTextBoxes) { //This here is to prevent the text cursor to stop moving around when editing TextFields
            switch (source.getName()) {
                case "red": //Writes JSlider value onto its respective TextField
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
                    break;
                case "sat":
                    satLabel.setText(String.valueOf(source.getValue()));
                    break;
                case "bright":
                    briLabel.setText(String.valueOf(source.getValue()));
                    break;
            }
        }
        changeTextBoxes = true;
        if (evaluateColor) { //Makes sure the color isn't being evaluated while switching between foreground and background
            if (source.getName().equals("hue") || source.getName().equals("sat") || source.getName().equals("bright"))
                updateRGB();
            if (settingForeground) {
                finalChar.setForeground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue())); //Saves new color value
                setForegroundButton.setBackground(finalChar.getForegroundColor()); //Color stuff for display
                setSpecTxt.setForeground(finalChar.getForegroundColor());
            } else {
                finalChar.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
                setBackgroundButton.setBackground(finalChar.getBackgroundColor());
                setSpecTxt.setBackground(finalChar.getBackgroundColor());
            }
        }
    }

    /**
     * Updates the RGB values according to what the HSB values say
     */
    private void updateRGB(){
        Color newRGB = new Color(Color.HSBtoRGB(hueSlider.getValue() / 360f,satSlider.getValue() / 100f,briSlider.getValue()/ 100f));
        redSlider.setValue(newRGB.getRed());
        greenSlider.setValue(newRGB.getGreen());
        blueSlider.setValue(newRGB.getBlue());
    }

    private void updateHSB(){
        float[] hsbVals =  new float[3];
        Color.RGBtoHSB(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue(), hsbVals);
        hueSlider.setValue((int)(hsbVals[0] * 360));
        satSlider.setValue((int)(hsbVals[1] * 100));
        briSlider.setValue((int)(hsbVals[2] * 100));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("foreground".equals(e.getActionCommand())){
            evaluateColor = false;
            setForegroundButton.setEnabled(false);
            setBackgroundButton.setEnabled(true);
            finalChar.setBackground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
            settingForeground = true;
            redSlider.setValue(finalChar.getForegroundColor().getRed());
            greenSlider.setValue(finalChar.getForegroundColor().getGreen());
            blueSlider.setValue(finalChar.getForegroundColor().getBlue());
            updateHSB();
            evaluateColor = true;
        } else if ("background".equals(e.getActionCommand())){
            evaluateColor = false;
            setForegroundButton.setEnabled(true);
            setBackgroundButton.setEnabled(false);
            finalChar.setForeground(new Color(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
            settingForeground = false;
            redSlider.setValue(finalChar.getBackgroundColor().getRed());
            greenSlider.setValue(finalChar.getBackgroundColor().getGreen());
            blueSlider.setValue(finalChar.getBackgroundColor().getBlue());
            updateHSB();
            evaluateColor = true;
        } else
            System.out.println(e.getActionCommand());
    }

    private class TextUpdate extends TimerTask {
        String[] previousVals = {"", "", "", "", "", ""};

        @Override
        public void run() {
            updateTextBox(redLabel, redSlider, 0);
            updateTextBox(blueLabel, blueSlider, 1);
            updateTextBox(greenLabel, greenSlider, 2);
            updateTextBox(hueLabel, hueSlider, 3);
            updateTextBox(satLabel, satSlider, 4);
            updateTextBox(briLabel, briSlider, 5);
            System.out.println("STM Alive");
        }

        private void updateTextBox(JTextField label, JSlider slider, int pos){
            if (!label.getText().equals(previousVals[pos]) && label.getText().length() > 0 && Integer.valueOf(label.getText()) <= 255 && Integer.valueOf(label.getText()) > 0){
                slider.setValue(Integer.valueOf(label.getText()));
                changeTextBoxes = false;
            }
            previousVals[pos] = label.getText();
        }
    }
}
