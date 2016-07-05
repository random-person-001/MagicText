/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms;

import SourceryTextb1.GameObjects.*;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.art;

/**
 *
 * @author 119184
 */
public class TestRoom extends Room {
    
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
                count++;
                if (count == 50){
                    compactTextBox(org, "Welcome to the world of Sourcery Text!", "", true);
                    compactTextBox(org, "All things considered,\nLife ain't that bad, although\nThe new text's crazy", "Haiku", false);
                }
                //play.reportPos();
                org.compileImage();
                if (getCountOf("Food") == 0){
                   exitCode = 1;
                }
            } catch (InterruptedException ex) {}
        }
    }
    
    public void startup(ImageOrg org, Player player){
        Layer spells = new Layer(new String[maxH][maxW], "Spellz", true);
        org.addLayer(spells);

        super.playo = player;
        player.castingLayer = spells;
        super.baseHitMesh = new boolean[super.roomHeight][super.roomWidth];
        super.objHitMesh = new boolean[super.roomHeight][super.roomWidth];
        //emptyAllHitMeshes();
        art arty = new art();
        String[][] base = art.strToArray(arty.hand1);
        String[] solids = {"P", "8", "'", ",", "a","b","d","I","f","_","\"",".","`",")","Y","#"};
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
        
        String foodLayerName = "foooooooooood";
        Layer foodstuffs = new Layer(new String[maxH][maxW], foodLayerName);
        org.addLayer(foodstuffs);
        for (int i = 0 ; i < 10 ; i++){
            Food aFood = new Food(org, foodLayerName, this);
            addObject(aFood);
        }
        super.playo = player;
        Layer playerLayer = org.getLayer(org.getPosLayer(player.getLayerName()));
        org.addLayer(playerLayer);

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
    
    public TestRoom(ImageOrg orgo){
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
