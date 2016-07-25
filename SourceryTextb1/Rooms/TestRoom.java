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
            if (playo.getHealth() <= 0){
                exitCode = 1;
                System.out.println("Leaving TestRoom");
            }
        }
    }
    
    public void startup(){
        ititHitMeshes();

        super.playo.goTo(20,29);

        art arty = new art();
        String[][] base = makeABox(50, 50);
        String[] solids = {"|","-","0","/",",","#","%","$","'"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test", 0, 0, true, false, false);
        org.addLayer(lay1);

        genericRoomInitialize();
    }
    
    /** 
     * Enter the room. IE, start loops and stuff now.
     */
    public void enter(Player player){
        org.compileImage();
        player.frozen = false;
        loop(player);
        //player.frozen = true;
        super.cleanLayersForExit(org);
    }
    
    public TestRoom(ImageOrg orgo, Player player){
            super.playo = player;
            super.org = orgo;
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
