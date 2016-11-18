/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
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

    @Override
    protected void specialInspect(int x, int y, Player inspector){
        if (x == 24 && y == 29){
            org.removeLayer("Cloak1");
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
        String[] solids = {"#","|","-","%","$","8"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        lay1.influenceAll(new Color(175, 175, 175));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        Layer cloak1 = new Layer(new String[10][10], "Cloak1", 21, 23, true, true);
        cloak1.clear();
        org.addLayer(cloak1);

        //addItems();

        genericRoomInitialize();
    }
}
