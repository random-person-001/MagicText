package SourceryText.GameObjects;

import SourceryText.Art;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * Created by Jared on 25-Dec-16.
 */
public class TextBox extends GameObject {
    
    private Player viewer;
    private boolean showing = false;

    private boolean isQuestion = false;
    private boolean choosingYes = false;
    private int questionID = 0;

    private GameObject specialRecipient;

    String layerName = "";
    
    private String text;
    private String speaker;

    public TextBox(Room theRoom, Player receiver, String theText, String theSpeaker){
        room = theRoom;
        viewer = receiver;
        text = theText;
        speaker = theSpeaker;

        org = room.org;
        
        layerName = room.makeUniqueLayerName("TextBox");
    }

    public void showTextBox (boolean helpful){
        Art artsedo = new Art();
        Layer txtBox;
        if (helpful) {
            txtBox = new Layer(Art.strToArray(artsedo.textBoxHelpful), layerName, 0, 13, false, true);
        } else {
            txtBox = new Layer(Art.strToArray(artsedo.textBox), layerName, 0, 13, false, true);
        }
        txtBox.setOwningPlayerUsername(viewer.getUsername());
        txtBox.setImportance(true);
        //System.out.println(viewer.getUsername());

        for (int ii = 0; ii < speaker.length(); ii++) {
            txtBox.setStr(0, ii + 2, String.valueOf(speaker.charAt(ii)));
        }

        if (speaker.length() != 0) {
            txtBox.setStr(0, speaker.length() + 2, ":");
        }

        int line = 1;
        int newLineAdjust = 0;
        for (int ii = 0; ii < text.length(); ii++) {
            if (text.charAt(ii) == '\n') {
                line++;
                newLineAdjust = ii + 1;
                System.out.println("Found newline.");
            } else if (String.valueOf(text.charAt(ii)).equals("ĩ")) {
                txtBox.setSpecTxt(line, ii + 1 - newLineAdjust, new SpecialText("#"));
                //, new Color(175, 215, 245), new Color(175, 215, 245)
            } else {
                txtBox.setStr(line, ii + 1 - newLineAdjust, String.valueOf(text.charAt(ii)));
            }
        }

        txtBox.setAllBg(new Color(0, 0, 15));

        org.addLayer(txtBox);

        viewer.frozen = true;
        viewer.textBox = this;
        showing = true;
    }

    public void showQuestionBox (int qID){
        Art artsedo = new Art();
        Layer txtBox;

        txtBox = new Layer(Art.strToArray(artsedo.textBoxQuestion), layerName, 0, 13, false, true);

        txtBox.setOwningPlayerUsername(viewer.getUsername());
        txtBox.setImportance(true);

        for (int ii = 0; ii < speaker.length(); ii++) {
            txtBox.setStr(0, ii + 2, String.valueOf(speaker.charAt(ii)));
        }

        if (speaker.length() != 0) {
            txtBox.setStr(0, speaker.length() + 2, ":");
        }

        int line = 1;
        int newLineAdjust = 0;
        for (int ii = 0; ii < text.length(); ii++) {
            if (text.charAt(ii) == '\n') {
                line++;
                newLineAdjust = ii + 1;
                System.out.println("Found newline.");
            } else if (String.valueOf(text.charAt(ii)).equals("ĩ")) {
                txtBox.setSpecTxt(line, ii + 1 - newLineAdjust, new SpecialText("#"));
                //, new Color(175, 215, 245), new Color(175, 215, 245)
            } else {
                txtBox.setStr(line, ii + 1 - newLineAdjust, String.valueOf(text.charAt(ii)));
            }
        }

        txtBox.setAllBg(new Color(0, 0, 15));

        org.addLayer(txtBox);

        viewer.frozen = true;
        viewer.textBox = this;
        showing = true;

        isQuestion = true;
        questionID = qID;
    }

    private void exit(){
        org.removeLayer(layerName);
        viewer.frozen = false;
        viewer.textBox = null;
        if (isQuestion){
            int answerID = questionID;
            if (!choosingYes){
                answerID *= -1;
            }
            room.respondToQuestion(answerID, viewer);
        }
        if (viewer.messageQueue.size() > 0) {
            viewer.messageQueue.remove(viewer.messageQueue.get(0));
        }
        if (viewer.messageQueue.size() >= 1) {
            viewer.messageQueue.get(0).output();
        }
        showing = false;
    }

    public void receiveInput(String inputStr){
        switch (inputStr){
            case "end":
                if (showing)
                    exit();
                break;
            case "change":
                if (isQuestion) {
                    if (!choosingYes) {
                        org.editLayer(" ", layerName, 4, 3);
                        org.editLayer(">", layerName, 4, 11);
                        choosingYes = true;
                    } else {
                        org.editLayer(" ", layerName, 4, 11);
                        org.editLayer(">", layerName, 4, 3);
                        choosingYes = false;
                    }
                }
                break;
        }
    }
}
