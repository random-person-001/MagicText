/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.OneWayDoor;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Wolf;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * The inner mountains area, filled with carrots and the fork in the road between the snowy peak and the bandit fortress
 * @author Jared
 *
 * So Far:
 *  > You have been introduced to the backstory
 *  > You've mastered the Tutorial Basement
 *
 * What Generally Happens Here:
 *  > The owner of the house is sitting at the edge of the hole. He convinces you to put the world back in order.
 *  > Then you move on to the next level, where a cloned witch has an identity crisis!
 */


public class HiddenBunker extends Room {

    public HiddenBunker(Player player){
        super(player);
        strRoomName = "InnerMountains";
    }

    @Override
    protected String loop(Player play){
        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (play.getX() == 0){
                    setNewRoom("SourceCaves", play, 15, 1);
                }
            } catch (InterruptedException ignored) {}
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems(){
        int[][] carrotLocs = {{18,32},{44,28},{21,15},{22,22},{48,14}};
        for (int[] coord : carrotLocs){
            Item carrot = new Item("Carrot", "For some reason,\n they only grow\n in the mountains.\n\nNobody really know why.", "item");
            carrot.healItemDefine(6, 3);
            DroppedItem gCarrot = new DroppedItem(this, "You picked a carrot!", carrot, coord[0], coord[1]);
            addObject(gCarrot);
        }

        int[][] wolfLocs = {{39,31},{44,29},{23,21},{27,16}};
        for (int[] coord : wolfLocs){
            Wolf puppy = new Wolf(org, this, coord[0], coord[1]);
            addMortal(puppy);
        }
    }

    boolean[] keysTaken = {false, false, false, false};

    @Override
    protected void specialInspect(int x, int y, Player inspector){
        if (x == 24 && y == 29){
            org.removeLayer("Cloak1");
            org.editLayer("", "HiddenBunkerLayer", 28, 24);
            removeFromBaseHitMesh(24, 28);
        }
        if (x == 63 && y == 10){
            org.removeLayer("Cloak2");
            org.editLayer("", "HiddenBunkerLayer", 10, 62);
            removeFromBaseHitMesh(62, 10);
        }
        if ((x == 89 || x == 91) && y == 13){
            org.removeLayer("Cloak3");
            org.removeLayer("Cloak5");
            org.editLayer("", "HiddenBunkerLayer", 13, 90);
            removeFromBaseHitMesh(90, 13);
        }
        if (x == 95 && y == 13){
            org.removeLayer("Cloak3");
            org.removeLayer("Cloak5");
            org.editLayer("", "HiddenBunkerLayer", 13, 94);
            removeFromBaseHitMesh(94, 13);
        }
        if (x == 114 && y == 13){
            org.removeLayer("Cloak4");
            org.removeLayer("Cloak5");
            org.editLayer("", "HiddenBunkerLayer", 13, 113);
            removeFromBaseHitMesh(113, 13);
        }
        if (x == 102 && y == 6){
            org.editLayer("", "HiddenBunkerLayer", 5, 102);
            removeFromBaseHitMesh(102, 5);
        }
        if (x == 95 && y == 26){
            org.removeLayer("Cloak6");
            org.editLayer("", "HiddenBunkerLayer", 27, 95);
            removeFromBaseHitMesh(95, 27);
        }
        if (x == 108 && y == 30){
            org.editLayer("", "HiddenBunkerLayer", 30, 109);
            removeFromBaseHitMesh(109, 30);
        }
        if (x == 119 && y == 26){
            org.removeLayer("Cloak7");
            org.editLayer("", "HiddenBunkerLayer", 26, 120);
            removeFromBaseHitMesh(120, 26);
        }
        if (x == 129 && y == 25){
            org.editLayer("", "HiddenBunkerLayer", 25, 130);
            removeFromBaseHitMesh(130, 25);
        }
        if (x == 128 && y == 20){
            org.removeLayer("Cloak8");
            org.editLayer("", "HiddenBunkerLayer", 21, 128);
            removeFromBaseHitMesh(128, 21);
        }
        if (x == 130 && y == 12){
            org.removeLayer("Cloak9");
            org.editLayer("", "HiddenBunkerLayer", 11, 130);
            removeFromBaseHitMesh(130, 11);
        }
        if (Math.abs(x - 24) + Math.abs(y - 24) <= 1 && !keysTaken[0]){
            Item key = new Item("Bunker Key 1","A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Need more plunder,\n hide key\'","item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!","").setViewerUsername(inspector.getUsername()));
            keysTaken[0] = true;
        }

        if (Math.abs(x - 69) + Math.abs(y - 30) <= 1 && !keysTaken[1]){
            Item key = new Item("Bunker Key 2","A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Mike need to shower\'","item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!","").setViewerUsername(inspector.getUsername()));
            keysTaken[1] = true;
        }
        if (Math.abs(x - 54) + Math.abs(y - 4) <= 1 && !keysTaken[2]){
            Item key = new Item("Bunker Key 3","A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Fondant\n Fondant\n Fondant\'","item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!","").setViewerUsername(inspector.getUsername()));
            keysTaken[2] = true;
        }
    }

    @Override
    public void startup(){
        ititHitMeshes();

        String[] signWords = {"Because carrots are a healthy snack,\n all carrots growing here are\n available to the general public",
                "Eating food can restore health\n beyond your maximum health\nThis is called \"Overhealing\"",
                "The Mountain Range Committee considers\n those who don't eat food regularly\n to be malnourished."};
        //plantText(new FlavorText(8, 38, signWords,"A Sign"));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.hiddenBunker);
        String[] solids = {"#","|","-","%","8"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "HiddenBunkerLayer");
        lay1.influenceAll(new Color(175, 175, 175));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        int[][] layerInfo ={{5, 10, 23, 21}, {7, 15, 8, 47}, {3, 3, 12, 91},
                            {10, 18, 6, 95}, {3, 1, 12, 94}, {7, 17, 28, 92},
                            {5, 6, 23, 121}, {6, 3, 22, 127}, {11, 11, 0, 125}};
        for (int ii = 0; ii < 9; ii++){
            Layer newCloak = new Layer(new String[layerInfo[ii][0]][layerInfo[ii][1]], ("Cloak" + (ii+1)), layerInfo[ii][2], layerInfo[ii][3], true, true, true);
            newCloak.clear();
            org.addLayer(newCloak);
        }
        /*
        Layer cloak1 = new Layer(new String[5][10], "Cloak1", 23, 21, true, true, true);
        cloak1.clear();
        org.addLayer(cloak1);

        Layer cloak2 = new Layer(new String[7][15], "Cloak2", 8, 47, true, true, true);
        cloak2.clear();
        org.addLayer(cloak2);

        Layer cloak3 = new Layer(new String[3][3], "Cloak3", 12, 91, true, true, true);
        cloak3.clear();
        org.addLayer(cloak3);

        Layer cloak4 = new Layer(new String[10][18], "Cloak4", 6, 95, true, true, true);
        cloak4.clear();
        org.addLayer(cloak4);

        Layer cloak5 = new Layer(new String[3][1], "Cloak5", 12, 94, true, true, true);
        cloak5.clear();
        org.addLayer(cloak5);

        Layer cloak6 = new Layer(new String[7][17], "Cloak6", 28, 92, true, true, true);
        cloak6.clear();
        org.addLayer(cloak6);

        Layer cloak7 = new Layer(new String[5][6], "Cloak7", 23, 121, true, true, true);
        cloak7.clear();
        org.addLayer(cloak7);

        Layer cloak8 = new Layer(new String[6][3], "Cloak8", 22, 127, true, true, true);
        cloak8.clear();
        org.addLayer(cloak8);

        Layer cloak9 = new Layer(new String[11][11], "Cloak9", 0, 125, true, true, true);
        cloak9.clear();
        org.addLayer(cloak9);
        */

        OneWayDoor secretDoor = new OneWayDoor(true, 92, 13, this, org);
        addObject(secretDoor);
        OneWayDoor normalDoor = new OneWayDoor(false, 133, 31, this, org);
        addObject(normalDoor);



        //addItems();

        genericRoomInitialize();
    }
}
