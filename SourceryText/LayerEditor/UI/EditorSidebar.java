package SourceryText.LayerEditor.UI;

import SourceryText.Layer;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jared on 06-Feb-17.
 */
public class EditorSidebar extends JPanel implements ActionListener {
    EditorSidebar(Layer editable){
        editLayer = editable;
        setBackground(new Color(180, 180, 173));
        setVisible(false);
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setBounds(900, 200, 165, 260);

        opaqueBox.setActionCommand("Opaque");
        opaqueBox.addActionListener(this);
        importantBox.setActionCommand("Important");
        importantBox.addActionListener(this);
        cameraBox.setActionCommand("Camera");
        cameraBox.addActionListener(this);
        nameBox.setActionCommand("Name");
        nameBox.addActionListener(this);

        JButton finishButton = new JButton("Finish");
        finishButton.setActionCommand("finish");
        finishButton.addActionListener(this);

        this.add(new JPanel().add(opaqueBox));
        this.add(new JPanel().add(importantBox));
        this.add(new JPanel().add(cameraBox));
        this.add(new JPanel().add(nameBox));
        this.add(new JPanel().add(finishButton));

        setPreferredSize(new Dimension(180, 400));
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(170, 170, 170));

        g.fill3DRect(0, 0, getWidth(), getHeight(), false);
        /*
        g.setColor(new Color(200, 200, 200));
        g.fill3DRect(5, getHeight() - 35, 30, 30, true);
        g.fill3DRect(5, getHeight() - 70, 30, 30, true);
        g.fill3DRect(5, getHeight() - 105, 30, 30, false);
        */
    }

    private Layer editLayer;
    private JCheckBox opaqueBox = new JCheckBox("Opaque");
    private JCheckBox importantBox = new JCheckBox("Important");
    private JCheckBox cameraBox = new JCheckBox("Camera Obedient");
    private JTextField nameBox = new JTextField("name");

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("Opaque".equals(e.getActionCommand())) {
            System.out.println("Opaque: " + opaqueBox.isSelected());
            editLayer.setOpaque(opaqueBox.isSelected());
        }
        if ("Important".equals(e.getActionCommand())) {
            System.out.println("Important: " + importantBox.isSelected());
            editLayer.setImportance(importantBox.isSelected());
        }
        if ("Camera".equals(e.getActionCommand())) {
            System.out.println("Camera Obedient: " + cameraBox.isSelected());
            editLayer.setImportance(cameraBox.isSelected());
        }
        if ("Name".equals(e.getActionCommand())) {
            System.out.println("Name: " + nameBox.getText());
            editLayer.name = nameBox.getText();
        }
        if ("finish".equals(e.getActionCommand())) {
            System.out.println("Name: " + nameBox.getText());
            editLayer.name = nameBox.getText();
            System.out.println("Done.");
            //setVisible(false);
        }
    }
}
