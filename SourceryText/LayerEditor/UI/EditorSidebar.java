package SourceryText.LayerEditor.UI;

import SourceryText.Layer;
import SourceryText.SpecialText;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jared on 06-Feb-17.
 */
public class EditorSidebar extends JPanel implements ActionListener {
    private Layer editLayer;
    private EditorFrame editorFrame;
    private JCheckBox opaqueBox = new JCheckBox("Opaque");
    private JCheckBox importantBox = new JCheckBox("Important");
    private JCheckBox cameraBox = new JCheckBox("Camera Obedient");
    private JTextField nameBox = new JTextField("name", 12);

    EditorSidebar(Layer editable, EditorFrame editorFrame){
        this.editorFrame = editorFrame;
        editLayer = editable;
        setVisible(false);
        setBackground(new Color(180, 180, 173));
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        opaqueBox.setActionCommand("Opaque");
        opaqueBox.addActionListener(this);
        importantBox.setActionCommand("Important");
        importantBox.addActionListener(this);
        cameraBox.setActionCommand("Camera");
        cameraBox.addActionListener(this);
        nameBox.setActionCommand("Name");
        nameBox.addActionListener(this);

        JButton replaceButton= new JButton("Find & Replace");
        replaceButton.setActionCommand("Replace");
        replaceButton.addActionListener(this);

        JButton finishButton = new JButton("Pretend Save");
        finishButton.setActionCommand("finish");
        finishButton.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        nameBox.setMaximumSize(new Dimension(300, 25));
        add(nameBox);
        add(opaqueBox);
        add(importantBox);
        add(cameraBox);
        add(replaceButton);
        add(finishButton);

        setBounds(900, 200, 165, 260);
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
        if ("Replace".equals(e.getActionCommand())) {
            SpecialText find = editorFrame.toolbar.getSpecTxt();
            SpecialText replace = editorFrame.maker.getFinalChar();
            //SpecialText find = editorFrame.viewWindow.getSelectedSpecTxt(); //would work, but the selected rect is always by the right panel.
            System.out.println("Replacing '" + find.toString() + "' with '" + replace.toString() + "'");
            editLayer.findAndReplace(find, replace);
            System.out.println("Replace tool ran.");
        }
        if ("finish".equals(e.getActionCommand())) {
            System.out.println("Name: " + nameBox.getText());
            editLayer.name = nameBox.getText();
            System.out.println("Pretend Saving... Done.  That was fast.");
            //setVisible(false);
        }
    }
}
