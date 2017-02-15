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
    private JTextField nameBox = new JTextField("Layer Name Here", 12);

    EditorSidebar(Layer editable, EditorFrame editorFrame){
        this.editorFrame = editorFrame;
        editLayer = editable;
        setVisible(false);
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        setBorder(BorderFactory.createEtchedBorder());

        opaqueBox.setActionCommand("Opaque");
        opaqueBox.addActionListener(this);
        opaqueBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        importantBox.setActionCommand("Important");
        importantBox.addActionListener(this);
        opaqueBox.setAlignmentX(0.0f);
        cameraBox.setActionCommand("Camera");
        cameraBox.addActionListener(this);
        opaqueBox.setAlignmentX(0.0f);
        nameBox.setActionCommand("Name");
        nameBox.addActionListener(this);
        opaqueBox.setAlignmentX(0.0f);

        JButton saveLayerButton = new JButton("Pretend Save");
        saveLayerButton.setActionCommand("finish");
        saveLayerButton.addActionListener(this);

        JButton newLayerButton = new JButton("Pretend New Layer");
        saveLayerButton.setActionCommand("new");
        saveLayerButton.addActionListener(this);

        JButton openLayerButton = new JButton("Pretend Open");
        saveLayerButton.setActionCommand("open");
        saveLayerButton.addActionListener(this);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        nameBox.setMaximumSize(new Dimension(300, 25));
        add(nameBox);
        add(opaqueBox);
        add(importantBox);
        add(cameraBox);
        add(new FindAndReplaceTool(editorFrame));
        add(new DrawToolsPanel(editorFrame));

        //This "serializationPanel" is NOT a separate class because it would be a little redundant.
        //The other JPanels need to have special code written for it, but this one doesn't.
        JPanel serializationPanel = new JPanel();
        serializationPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "File"));

        serializationPanel.add(newLayerButton);
        serializationPanel.add(saveLayerButton);
        serializationPanel.add(openLayerButton);

        serializationPanel.setAlignmentX(0f);
        serializationPanel.setLayout(new BoxLayout(serializationPanel, BoxLayout.PAGE_AXIS));

        add(serializationPanel);

        setBounds(900, 200, 165, 260);
        setPreferredSize(new Dimension(180, 400));
        setVisible(true);
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
