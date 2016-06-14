/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package SourceryTextb1.Rooms;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Window;
import SourceryTextb1.art;

import java.util.ConcurrentModificationException;

import static java.awt.Color.*;

/**
 * @author Jared  CHECK YOUR EMAIL, JARED
 */
public class Room {

    public List<GameObject> objs = new ArrayList<>();
    private List<Mortal> enemies = new ArrayList<>();
    public HashMap storedStuff = new HashMap<String, Integer>();
    public boolean[][] objHitMesh;
    public boolean[][] baseHitMesh;

    public Player playo;



    public int foodEaten = 0;
    public int totalFood = 0;

    public int roomWidth;
    public int roomHeight;
    public int index;

    /** Try to hurt a mortal at a specified location
     * @param x the X coord of the possible mortal
     * @param y the Y coord of the possible mortal
     * @param damage how much to hurt any mortals if they're there
     * @return whether there was an mortals there, and thus whether they got hurt
     */
    public boolean hurtSomethingAt(int x, int y, int damage) {
        for (Mortal e : enemies){
            if (e.getX() == x && e.getY() == y) {
                e.subtractHealth(damage);
                return true;
            }
        }
        return false;
    }

    public int getCountOf (String className){
        int count = 0;
        for (GameObject o : objs) {
            if (o.strClass.equals(className)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Loop through all objects that are in the room and tell them to update. (call obj.update() on each)
     */
    public void updateObjs(int timeElapsed) {
        try {
            for (GameObject obj : objs) {
                obj.update();
                obj.addTime(timeElapsed);
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Whoops, something weird! [Room.java: updateObjs(): caught a ConcurrentModificationException]");
        } catch (NullPointerException ex) {
            System.out.println("Well, by then if you want to nullpoint me. [Room.java: updateObjs(): caught nullpointer");
        }
    }

    public void addObject(GameObject theObj) {
        objs.add(theObj);
    }

    public void deleteAll() {
        for (GameObject obj : objs) {
            objs.remove(obj);
        }
    }

    public void cleanLayersForExit(ImageOrg org) {
        org.removeAllButPlayer(); //Cleanup, happens when loop is done.
        org.compileImage();
        org.getWindow().clearImage();
    }

    public void removeObject(GameObject obj) {
        objs.remove(obj);
    }

    /*public boolean isPlaceSolid(int x, int y){ //Useful when defining walls of rooms
        if (x < -1 || x > hitMesh[0].length || y < -1 || y > hitMesh.length){
            return hitMesh[y][x] || baseHitMesh[y][x];
        } else {
            return false;
        }
    }*/
    public void addHUD(ImageOrg org){  //Fixes redundancy;
        Layer HUDd = new Layer(new String[1][70], "HUD", false, true);
        org.addLayer(HUDd);
        HUD hud = new HUD(org, (Room)this, HUDd);
        addObject(hud);
    }


    /**
     * Add a new mortal to the room, and to the list of mortals.  As a bonus, it'll change the objectsHitMesh to
     * reflect that.
     * @param newMortal a new Mortal
     */
    public void addMortal(Mortal newMortal) {
        addObject(newMortal);
        enemies.add(newMortal);
        objHitMesh[newMortal.getY()][newMortal.getX()] = true;
    }


    /** Remove an Mortal from the world and enemy list, and make it so they no longer obstruct your way
     * @param m the Mortal to be removed
     */
    public void removeMortal(Mortal m) {
        removeObject(m);
        enemies.remove(m);
        objHitMesh[m.getY()][m.getX()] = false;
    }

    public boolean isPlaceSolid(int x, int y) { //Useful when defining walls of rooms
        if ((x > 0 && x < objHitMesh[0].length - 1) && (y > 0 && y < objHitMesh.length - 1)) { // Buffer of 1 for room walls
            return objHitMesh[y][x] || baseHitMesh[y][x];
            //return false;
        } else { // Outside wall
            return true;
        }
    }

    public void addToBaseHitMesh(String[][] picture, String[] solidChars) {
        for (String solid : solidChars){
            addToBaseHitMesh(picture, solid);
        }
    }
    public void addToBaseHitMesh(String[][] picture, String solidChar) {
        for (int i = 0; i < picture.length; i++) {
            for (int j = 0; j < picture[0].length; j++) {
                if (picture[i][j].equals(solidChar)) {
                    baseHitMesh[i][j] = true;
                }
            }
        }
    }

    public void emptyHitMesh() {
        for (int i = 0; i < baseHitMesh.length; i++) {
            for (int j = 0; j < baseHitMesh[0].length; j++) {
                baseHitMesh[i][j] = false;
            }
        }
        for (int i = 0; i < objHitMesh.length; i++) {
            for (int j = 0; j < objHitMesh[0].length; j++) {
                objHitMesh[i][j] = false;
            }
        }
    }

    public void enterANewRoom(){

    }

    public Player getPlayer() {
        return playo;
    }

    private String[][] makeABox(int width, int height) {  //Makes a box that looks like the one in the test room
        String[][] output = new String[height][width];
        for (int ii = 0; ii < height; ii++) {
            for (int iii = 0; iii < width; iii++) {
                if (ii == 0 || ii == height - 1) {
                    if (iii == 0 || iii == width - 1) {
                        output[ii][iii] = "O";
                    } else {
                        output[ii][iii] = "-";
                    }
                } else {
                    if (iii == 0 || iii == width - 1) {
                        output[ii][iii] = "|";
                    } else if (ii % 3 == 0 && iii % 6 == 0) {
                        output[ii][iii] = ".";
                    } else {
                        output[ii][iii] = " ";
                    }
                }
            }
        }
        return output;
    }

    int getFoodCount() {
        int count = 0;
        for (GameObject o : objs) {
            if (o.strClass.equals("Food")) {
                count++;
            }
        }
        return count;
    }

    public void infoMessage(ImageOrg org, String usefulTip) {
        art arty = new art();
        Layer bkgd = new Layer(art.strToArray(arty.usefulTipBkgd), "tip", 5, 5, false, true);
        char[] tipArr = usefulTip.toCharArray();
        String[] strArr = new String[tipArr.length];
        for (int i=0; i<tipArr.length; i++){
            strArr[i] = String.valueOf(tipArr[i]);
        }
        int n = 3;
        for (int i=0; i<strArr.length; i++){
            bkgd.self[n][i%28+5] = strArr[i];
            if (i%28 == 27){
                n++;
            }
        }
        org.addLayer(bkgd);
        org.compileImage();

        Window window = org.getWindow();
        Dismissal keyListener = new Dismissal();
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }
        org.removeLayer("tip");
        window.txtArea.removeKeyListener(keyListener);
    }

    public void compactTextBox(ImageOrg org, String text, String speaker, boolean helpful){
        art artsedo = new art();
        Layer txtBox;
        if (helpful) {
            txtBox = new Layer(artsedo.strToArray(artsedo.textBoxHelpful), "Dialog", 13, 0, false, true);
        } else {
            txtBox = new Layer(artsedo.strToArray(artsedo.textBox), "Dialog", 13, 0, false, true);
        }

        for (int ii = 0; ii < speaker.length(); ii++) {
            txtBox.setStr(0, ii + 2, String.valueOf(speaker.charAt(ii)));
        }

        if (speaker.length() != 0){ txtBox.setStr(0, speaker.length() + 2, ":"); }

        int line = 1;
        int newLineAdjust = 0;
        for (int ii = 0; ii < text.length(); ii++){
            if (text.charAt(ii) == '\n'){
                line++;
                newLineAdjust = ii + 1;
            } else {
                txtBox.setStr(line, ii + 1 - newLineAdjust, String.valueOf(text.charAt(ii)));
            }
        }

        org.addLayer(txtBox);
        org.compileImage();

        Window window = org.getWindow();
        Dismissal keyListener = new Dismissal();
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {}
        }
        org.removeLayer("Dialog");
        window.txtArea.removeKeyListener(keyListener);
    }

    public void pause(ImageOrg org) {
        art arty = new art();
        int camStartX = org.getCamX();
        Layer bkgd = new Layer(art.strToArray(arty.pauseBkgd), "pause", false, true);
        org.addLayer(bkgd);
        String[][] textArr = art.strToArray(arty.pausedText);
        Layer texty = new Layer(textArr, "texty", org.getCamY()+1, org.getCamX());
        org.addLayer(texty);

        Window window = org.getWindow();
        PauseSelector keyListener = new PauseSelector(org);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                int newX;
                if (org.getCamX() < -1*textArr[0].length - 6){
                    newX = textArr[0].length;
                }else{
                    newX = org.getCamX()-1;
                }
                org.setCam(newX, org.getCamY());
                org.compileImage();
                if (keyListener.options){
                    window.txtArea.removeKeyListener(keyListener);
                    options(org);
                    window.txtArea.addKeyListener(keyListener); // Add key listeners.
                    keyListener.options = false;
                }
                Thread.sleep(70);
            } catch (InterruptedException e) {}
        }
        org.setCam(camStartX, org.getCamY());
        org.removeLayer("pause");
        org.removeLayer("texty");
        window.txtArea.removeKeyListener(keyListener);
    }

    public void options(ImageOrg org) {
        art arty = new art();
        int camStartX = org.getCamX();
        Layer bkgd = new Layer(art.strToArray(arty.optionsbkgd), "opts", false, true);
        org.addLayer(bkgd);
        String[][] textArry = art.strToArray(arty.optionsText);
        Layer texty = new Layer(textArry, "texty", org.getCamY()+1, org.getCamX());
        org.addLayer(texty);

        Window window = org.getWindow();
        OptionsSelector keyListener = new OptionsSelector(org);
        window.txtArea.addKeyListener(keyListener); // Add key listeners.
        while (!keyListener.resume){
            try {
                int newX;
                if (org.getCamX() < -1*textArry[0].length - 6){
                    newX = textArry[0].length;
                }else{
                    newX = org.getCamX()-1;
                }
                org.setCam(newX, org.getCamY());
                org.compileImage();
                Thread.sleep(70);
            } catch (InterruptedException e) {}
        }
        try{ playo.setBackgroundColor(window.txtArea.getBackground()); }
        catch (NullPointerException e){ System.out.println(e); }
        org.setCam(camStartX, org.getCamY());
        org.removeLayer("opts");
        org.removeLayer("texty");
        window.txtArea.removeKeyListener(keyListener);
    }



}


class PauseSelector extends KeyAdapter {
    private ImageOrg org;
    private int x = 14;
    private int y = 15;
    boolean resume = false;
    boolean options = false;

    PauseSelector(ImageOrg o) {
        org = o;
        org.editLayer("@", "pause", y, x);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (event.getKeyCode() == KeyEvent.VK_UP || ch == 'w') {
            org.editLayer(" ", "pause", y, x);
            y = (y>15) ? y-2 : y;
            org.editLayer("@", "pause", y, x);
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN || ch == 's') {
            org.editLayer(" ", "pause", y, x);
            y = (y<19) ? y+2 : y;
            org.editLayer("@", "pause", y, x);
        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            select();
        }
        System.out.println(y);
    }
    private void select(){
        switch (y){
            case 15: // Exit
                System.exit(0);
            case 17: // Resume
                resume = true;
                break;
            case 19: // Options
                options = true;
                break;
            default:
                break;
        }
    }
}

class OptionsSelector extends KeyAdapter {
    private ImageOrg org;
    boolean resume = false;
    private String font = "Monospaced";
    private int ii = 0;

    OptionsSelector(ImageOrg o) {
        org = o;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (ch == 'i') {
            System.out.println("I was pressed.");
            int i = Font.PLAIN;
            ii++;
            if (ii>2){
                ii=0;
            }
            if (ii == 0){
                i = Font.PLAIN;
            }
            else if (ii == 1){
                i = Font.ITALIC;
            }
            else if (ii == 2){
                i = Font.BOLD;
            }
            Font f = new Font(font, i, 15);
            org.getWindow().txtArea.setFont(f);
        }
        if (ch == 'c'){
            System.out.println("c was pressed.");
            Color c = org.getWindow().txtArea.getBackground();

            if (c.equals(BLACK)) {
                org.getWindow().txtArea.setBackground(DARK_GRAY);

            } else if (c.equals(DARK_GRAY)) {
                org.getWindow().txtArea.setBackground(LIGHT_GRAY);

            } else if (c.equals(LIGHT_GRAY)) {
                org.getWindow().txtArea.setBackground(GREEN);

            } else if (c.equals(GREEN)) {
                org.getWindow().txtArea.setBackground(CYAN);

            } else if (c.equals(CYAN)) {
                org.getWindow().txtArea.setBackground(BLACK);
            }

        }
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}

class Dismissal extends KeyAdapter {
    boolean resume = false;

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            resume = true;
        }
        if (event.getKeyCode() == KeyEvent.VK_ENTER) {
            resume = true;
        }
    }
}
