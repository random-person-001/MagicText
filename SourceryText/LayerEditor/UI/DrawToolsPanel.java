package SourceryText.LayerEditor.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Jared on 15-Feb-17.
 */
public class DrawToolsPanel extends JPanel implements ActionListener, ItemListener{
    private EditorFrame editorFrame;

    private JButton pencilBtn;
    private JButton pickBtn;
    private JButton fillBtn;
    private JButton lineBtn;
    private JButton rectBtn;
    private JButton selectBtn;

    private JButton[] btns = {pencilBtn, pickBtn, fillBtn, lineBtn, rectBtn, selectBtn};

    private JButton copyBtn;
    private JButton cutBtn;

    JPanel clipboardBtnsPanel;

    int currentTool = 0;

    private JCheckBox rectFilledBox;
    private boolean fillRectangle;

    public DrawToolsPanel (EditorFrame editFrame){
        editorFrame = editFrame;

        setLayout(new GridLayout(4, 2));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Tools"));
        setAlignmentX(0.0f);

        setMaximumSize(new Dimension(140, 200));

        pencilBtn = createButton("Pencil", "pencil");
        pickBtn = createButton("Pick", "pick");
        fillBtn = createButton("Fill", "fill");
        lineBtn = createButton("Line", "line");
        rectBtn = createButton("Rect", "rect");
        selectBtn = createButton("Select", "select");

        btns = new JButton[]{pencilBtn, pickBtn, fillBtn, lineBtn, rectBtn, selectBtn};

        for (JButton btn : btns){
            add(btn);
        }

        rectFilledBox = new JCheckBox("Filled");
        rectFilledBox.setEnabled(false);
        add(rectFilledBox);

        rectFilledBox.addItemListener(this);

        clipboardBtnsPanel = new JPanel(new GridLayout(1, 3));

        copyBtn = new JButton("C");
        copyBtn.setMargin(new Insets(5, 5, 5, 5));
        copyBtn.setEnabled(false);
        cutBtn = new JButton("X");
        cutBtn.setMargin(new Insets(5, 5, 5, 5));
        cutBtn.setEnabled(false);

        clipboardBtnsPanel.add(copyBtn);
        clipboardBtnsPanel.add(cutBtn);

        add(clipboardBtnsPanel);

        validate();
    }

    private JButton createButton(String buttonName, String buttonAction){
        JButton base = new JButton(buttonName);
        base.setActionCommand(buttonAction);
        base.setMargin(new Insets(5, 5, 5, 5));
        base.addActionListener(this);
        return base;
    }

    public boolean getRectToolFilled(){
        return fillRectangle;
    }

    private void resetButtons(){
        for (JButton btn : btns){
            btn.setEnabled(true);
        }
        rectFilledBox.setEnabled(false);
        copyBtn.setEnabled(false);
        cutBtn.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean wasAButton = false;
        for (JButton btn : btns){ //Why use long if statements and tedious switch statements, when you can just use for loops?
            if (btn != null)
                wasAButton |= e.getActionCommand().equals(btn.getActionCommand());
            else
                System.out.println("Null btn!");
        }
        if (wasAButton){
            resetButtons();
            for (int ii = 0; ii < btns.length ; ii++){ //The amount of convenience to this is astounding
                JButton btn = btns[ii];
                if (e.getActionCommand().equals(btn.getActionCommand())) {
                    btn.setEnabled(false);
                    if (btn.equals(rectBtn)){
                        rectFilledBox.setEnabled(true);
                    } else if (btn.equals(selectBtn)) {
                        copyBtn.setEnabled(true);
                        cutBtn.setEnabled(true);
                    }
                    currentTool = ii;
                    editorFrame.viewWindow.toolReset();
                    return; //Hopefully two buttons don't have the same action command
                }
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        if (source == rectFilledBox){
            fillRectangle = !fillRectangle;
            System.out.println(fillRectangle);
        }
    }
}
