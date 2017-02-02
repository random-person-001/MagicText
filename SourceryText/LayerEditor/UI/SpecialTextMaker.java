package SourceryText.LayerEditor.UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private Color fgColor = Color.WHITE;
    private Color bgColor = Color.BLACK;

    public SpecialTextMaker (){
        setBackground(new Color(180, 180, 173));

        setVisible(true);

        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

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

        /*
        JTextField setSpecTxtLabel = new JTextField("Char:",3);
        setSpecTxtLabel.setEditable(false);
        c.add(setSpecTxtLabel);
        */

        setSpecTxt.setEditable(true);
        setSpecTxt.setFont(new Font("Monospaced", Font.PLAIN, 30));
        setSpecTxt.setHorizontalAlignment(JTextField.CENTER);
        setSpecTxt.setPreferredSize(new Dimension(30, 30));
        c.add(setSpecTxt);

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

        c.add(setForegroundButton);
        c.add(setBackgroundButton);

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
        if (settingForeground) {
            fgColor = new Color(Integer.valueOf(redLabel.getText()), Integer.valueOf(greenLabel.getText()), Integer.valueOf(blueLabel.getText()));
            setForegroundButton.setBackground(fgColor);
            setSpecTxt.setForeground(fgColor);
        } else {
            bgColor = new Color(Integer.valueOf(redLabel.getText()), Integer.valueOf(greenLabel.getText()), Integer.valueOf(blueLabel.getText()));
            setBackgroundButton.setBackground(bgColor);
            setSpecTxt.setBackground(bgColor);
        }
    }

    private void updateRGB(){
        Color newRGB = new Color(Color.HSBtoRGB(hueSlider.getValue() / 255f,satSlider.getValue() / 255f,briSlider.getValue()/ 255f));
        redSlider.setValue(newRGB.getRed());
        greenSlider.setValue(newRGB.getGreen());
        blueSlider.setValue(newRGB.getBlue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("foreground".equals(e.getActionCommand())){
            settingForeground = true;
            setForegroundButton.setEnabled(false);
            setBackgroundButton.setEnabled(true);
        } else if ("background".equals(e.getActionCommand())){
            settingForeground = false;
            setForegroundButton.setEnabled(true);
            setBackgroundButton.setEnabled(false);
        } else
            System.out.println(e.getActionCommand());
    }
}
