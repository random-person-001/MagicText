/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.PotOfPetunias;
import SourceryTextb1.GameObjects.Spike;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 *
 * @author 119184
 */
public class TutorialBasement extends Room {

    private ImageOrg org;
    private int maxH;
    private int maxW;

    private void loop(){
        int exitCode = 0;
        int count = 0;
        while (exitCode == 0){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                playo.update();
                playo.addTime(20);
                if (count == 0){
                    compactTextBox(org, "You've woken up in a basement somewhere.\nWoah, there's now lots of text everywhere!", "", true);
                    compactTextBox(org, "You should explore the basement!\nUse the arrow keys to navigate the place.", "", false);
                    count++;
                }
                if (count == 1 && getPlayer().getX() == 16 && getPlayer().getY() == 10){
                    compactTextBox(org, "The world is now filled with dangers!\n Use the 'A' key to lock your aim\n Use the 'S' key to fire a spell!", "", false);
                    compactTextBox(org, "The bar on the top-right is your mana bar.\n Casting spells costs mana.\nLuckily, your mana refills after a bit.", "", false);
                    count++;
                }
                if (count == 2 && getPlayer().getX() == 24 && getPlayer().getY() == 3){
                    compactTextBox(org, "One such grave danger awaits you nearby.\n None other than a Pot of Petunias is ahead.\n You have but a book to kill it with.","",false);
                    compactTextBox(org, "Use the 'A' key to lock your aim\n Use the 'S' key to fire!", "", false);
                    count++;
                }
                if (count == 3 && getCountOf("PotOfPetunias") == 0){
                    compactTextBox(org, "You managed to destroy the flowers!\n Now let's move on.", "", false);
                    compactTextBox(org, "Oh no, not again.", "Petunias", false);
                    count++;
                }
                if (count == 4 && ((getPlayer().getX() == 39 && getPlayer().getY() == 8) || (getPlayer().getX() == 38 && getPlayer().getY() == 7))){
                    compactTextBox(org, "You may ask what that ^ is over yonder.\n I fear that is a Spike.","Narrator",false);
                    count++;
                }
                playo.reportPos();
                org.compileImage();

            } catch (InterruptedException ignored) {}
        }
    }

    public void startup(){
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        playo.goTo(7,20);
        playo.castingLayer = spells;
        playo.setupForNewRoom();


        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        emptyHitMesh();
        art arty = new art();
        String[][] base = art.strToArray(arty.tutForest);
        String[] solids = {"|","-","0"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
//        for (int i=0; i<lay1.self.length; i++){
//            for (int j=0; j<lay1.self[0].length; j++){
//                if (lay1.self[i][j].equals("#")){
//                    lay1.self[i][j] = " ";
//                }
//            }
//        }
        org.addLayer(lay1);

        Layer playerLayer = org.getLayer(org.getPosLayer(playo.getLayerName()));
        org.addLayer(playerLayer);
        addMortal(playo);

        addHUD(org);

        PotOfPetunias flowers = new PotOfPetunias(org, this, 27, 3);
        addMortal(flowers);

        Spike spike = new Spike(org, this, 46, 8);
        spike.setMoveFrq(10);
        addMortal(spike);
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(){
        org.compileImage();
        playo.frozen = false;
        loop();
        playo.frozen = true;
        super.cleanLayersForExit(org);
    }

    public TutorialBasement(ImageOrg orgo, Player player){
        playo = player;
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }

}
