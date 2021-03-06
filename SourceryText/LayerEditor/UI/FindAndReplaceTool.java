package SourceryText.LayerEditor.UI;

import SourceryText.SpecialText;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jared on 14-Feb-17.
 */
public class FindAndReplaceTool extends JPanel implements ActionListener{
    private EditorFrame editorFrame;

    private SpecialText find;
    private SpecialText replaceWith;

    private JButton findButton = new JButton();
    private JButton replaceWithButton = new JButton();

    public FindAndReplaceTool(EditorFrame source){
        editorFrame = source;

        setMaximumSize(new Dimension(135, 85));
        setAlignmentX(Component.LEFT_ALIGNMENT);

        setLayout(new BorderLayout(10, 5));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Find and Replace"));

        Font specTextFont = new Font("Monospaced", Font.BOLD, 20);

        findButton = new JButton("#");
        findButton.setActionCommand("ChangeFind");
        findButton.addActionListener(this);
        findButton.setFont(specTextFont);
        replaceWithButton = new JButton("#");
        replaceWithButton.setActionCommand("ChangeReplaceWith");
        replaceWithButton.addActionListener(this);
        replaceWithButton.setFont(specTextFont);

        JButton replaceButton = new JButton("Replace");
        replaceButton.setActionCommand("DoReplace");
        replaceButton.addActionListener(this);

        add(findButton, BorderLayout.WEST);
        add(replaceWithButton, BorderLayout.EAST);
        add(replaceButton, BorderLayout.SOUTH);
    }

    public void paintComponent(Graphics g){
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
        g.drawLine(55, 33, 75, 33);
        g.drawLine(75, 33, 65, 23);
        g.drawLine(75, 33, 65, 43);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch(e.getActionCommand()){
            case "ChangeFind":
                find = editorFrame.toolbar.getSpecTxt();
                findButton.setText(find.getStr());
                findButton.setBackground(find.getBackgroundColor());
                findButton.setForeground(find.getForegroundColor());
                break;
            case "ChangeReplaceWith":
                replaceWith = editorFrame.toolbar.getSpecTxt();
                replaceWithButton.setText(replaceWith.getStr());
                replaceWithButton.setBackground(replaceWith.getBackgroundColor());
                replaceWithButton.setForeground(replaceWith.getForegroundColor());
                break;
            case "DoReplace":
                editorFrame.viewWindow.image.findAndReplace(find, replaceWith);
                break;
        }
        repaint();
    }
}
