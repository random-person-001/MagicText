/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package MagicTextb2.Rooms;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import MagicTextb2.GameObjects.GameObject;
import MagicTextb2.GameObjects.HUD;
import MagicTextb2.GameObjects.Player;
import MagicTextb2.ImageOrg;
import MagicTextb2.Layer;
import MagicTextb2.Window;
import MagicTextb2.art;

import java.util.ConcurrentModificationException;

import static java.awt.Color.*;

/**
 * @author Jared  CHECK YOUR EMAIL, JARED
 */
public class Room {

    public List<GameObject> objs = new ArrayList<GameObject>();
    public HashMap storedStuff = new HashMap<String, Integer>();
    public boolean[][] objHitMesh;
    public boolean[][] baseHitMesh;

    public Player playo;
    public Layer playerLayer;

    public int foodEaten = 0;
    public int totalFood = 0;

    public int roomWidth;
    public int roomHeight;
    public int index;

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
        Layer bkgd = new Layer(art.strToArray(arty.usefulTipBkgd), "tip", org.getCamY()+7, org.getCamX()+5, true, false);
        char[] tipArr = usefulTip.toCharArray();
        String[] strArr = new String[tipArr.length];
        for (int i=0; i<tipArr.length; i++){
            strArr[i] = String.valueOf(tipArr[i]);
            strArr[i] = (strArr[i].equals(" ")) ? " " : strArr[i]; // Replace spaces with something else for layering
        }
        int n = 5;
        for (int i=0; i<strArr.length; i++){
            bkgd.self[n][i%28+5] = strArr[i];
            if (i%28 == 27){
                n++;
            }
        }
        org.addLayer(bkgd);
        org.compileImage();
        ///org.clearArea(1, 78, 1, 50); /// Hopeless
        ///org.getWindow().build(org.getCamX(), org.getCamY());

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

    public void pause(ImageOrg org) {
        art arty = new art();
        int camStartX = org.getCamX();
        Layer bkgd = new Layer(art.strToArray(arty.getPauseBkgdDot), "pause", false);
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
        Layer bkgd = new Layer(art.strToArray(arty.optionsbkgd), "opts", false);
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
    ImageOrg org;
    int x = 14;
    int y = 14;
    public boolean resume = false;
    public boolean options = false;

    public PauseSelector(ImageOrg o) {
        org = o;
        org.editLayer("@", "pause", y, x);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();
        if (event.getKeyCode() == KeyEvent.VK_UP || ch == 'w') {
            org.editLayer(".", "pause", y, x);
            y = (y>14) ? y-2 : y;
            org.editLayer("@", "pause", y, x);
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN || ch == 's') {
            org.editLayer(".", "pause", y, x);
            y = (y<18) ? y+2 : y;
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
    void select(){
        switch (y){
            case 14: // Exit
                System.exit(0);
            case 16: // Resume
                resume = true;
                break;
            case 18: // Options
                options = true;
                break;
            default:
                break;
        }
    }
}

class OptionsSelector extends KeyAdapter {
    ImageOrg org;
    public boolean resume = false;
    String font = "Monospaced";
    int ii = 0;

    public OptionsSelector(ImageOrg o) {
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
    public boolean resume = false;

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
