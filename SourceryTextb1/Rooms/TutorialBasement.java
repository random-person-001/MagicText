/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.Food;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.Spark;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 *
 * @author 119184
 */
public class TutorialBasement extends Room {

    ImageOrg org;
    int maxH;
    int maxW;

    private void loop(Player play){
        int exitCode = 0;
        int count = 0;
        while (exitCode == 0){
            try {
                Thread.sleep(20);
                //System.out.println("I'm not dead yet! " + ii);
                updateObjs(20);
                play.addTime(20);
                play.update();//Update player
                if (count == 0){
                    compactTextBox(org, "You've woken up in a basement somewhere.\nWoah, there's now lots of text everywhere!", "", true);
                    compactTextBox(org, "You should explore the basement!\nUse the arrow keys to navigate the place.", "", false);
                    count++;
                }
                if (count == 1 && getPlayer().x == 16 && getPlayer().y == 10){
                    compactTextBox(org, "The world is now filled with dangers!\n Use the 'A' key to lock your aim\n Use the 'S' key to fire a spell!", "", false);
                    compactTextBox(org, "The bar on the top-right is your mana bar.\n Casting spells costs mana.\nLuckily, your mana refills after a bit.", "", false);
                    count++;
                }
                //play.reportPos();
                org.compileImage();

            } catch (InterruptedException ex) {}
        }
    }

    public void startup(ImageOrg org, Player player){
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        super.playo = player;
        player.x = 7;
        player.y = 20;
        player.centerCamera();
        player.castingLayer = spells;

        playo.inventory.put("WoodStaff", 1);
        player.setupForNewRoom();

        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        emptyHitMesh();
        art arty = new art();
        String[][] base = art.strToArray(arty.tutForest);
        String[] solids = {"|","-","0"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        for (int i=0; i<lay1.self.length; i++){
            for (int j=0; j<lay1.self[0].length; j++){
                if (lay1.self[i][j].equals("#")){
                    lay1.self[i][j] = " ";
                }
            }
        }
        org.addLayer(lay1);

        super.playo = player;
        Layer playerLayer = org.getLayer(org.getPosLayer(player.layerName));
        org.addLayer(playerLayer);

        Spark sparky = new Spark(org, this, spells, 15, 15, 0);
        addObject(sparky);

        addHUD(org);
    }

    /**
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(Player player){
        org.compileImage();
        player.frozen = false;
        loop(player);
        player.frozen = true;
        super.cleanLayersForExit(org);
    }

    public TutorialBasement(ImageOrg orgo){
        org = orgo;
        maxH = org.getWindow().maxH();
        maxW = org.getWindow().maxW();
        super.roomHeight = maxH;
        super.roomWidth = maxW;
        super.index = 1;
    }
    
    private String[][] makeABox(int width, int height){
        String[][] output = new String[height][width];
        for(int ii = 0; ii < height; ii++){
            for (int iii = 0 ; iii < width; iii++){
                if (ii == 0 || ii == height - 1){
                    if (iii == 0 || iii == width - 1){
                        output[ii][iii] = "O";
                    } else {
                        output[ii][iii] = "-";
                    }
                } else {
                    if (iii == 0 || iii == width - 1){
                        output[ii][iii] = "|";
                    } else if (ii % 3 == 0 && iii % 6 == 0){
                        output[ii][iii] = ".";
                    } else {
                        output[ii][iii] = " ";
                    }
                }
            }
        }
        return output;
    }

}
