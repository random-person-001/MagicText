package SourceryText.LayerEditor.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jared on 15-Feb-17.
 */
public class DrawToolsPanel extends JPanel implements ActionListener{
    private EditorFrame editorFrame;

    private JButton pencilBtn;
    private JButton pickBtn;
    private JButton fillBtn;
    private JButton lineBtn;
    private JButton rectBtn;
    private JButton randomBtn;

    private JButton[] btns = {pencilBtn, pickBtn, fillBtn, lineBtn, rectBtn, randomBtn};

    int currentTool = 0;

    private JCheckBox rectFilled;

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
        randomBtn = createButton("Move", "move");

        btns = new JButton[]{pencilBtn, pickBtn, fillBtn, lineBtn, rectBtn, randomBtn};

        for (JButton btn : btns){
            add(btn);
        }

        rectFilled = new JCheckBox("Filled");
        rectFilled.setEnabled(false);
        add(rectFilled);

        validate();
    }

    private JButton createButton(String buttonName, String buttonAction){
        JButton base = new JButton(buttonName);
        base.setActionCommand(buttonAction);
        base.setMargin(new Insets(5, 5, 5, 5));
        base.addActionListener(this);
        return base;
    }

    private void resetButtons(){
        for (JButton btn : btns){
            btn.setEnabled(true);
        }
        rectFilled.setEnabled(false);
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
                        rectFilled.setEnabled(true);
                    }
                    currentTool = ii;
                    System.out.println(ii);
                    return; //Hopefully two buttons don't have the same action command
                }
            }
        }
    }
}
