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
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

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


public class InnerMountains extends Room {
    private ImageOrg org;

    @Override
    protected String loop(){
        while (exitCode.equals("")){
            try {
                Thread.sleep(20);
                if (getPlayer().getY() == 46){
                    setNewRoom("Cliffside", 137, 1);
                }
                if (getPlayer().getX() == 65){
                    setNewRoom("SnowyPeak", 1, 15);
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
            Item carrot = new Item("Carrot", "For some reason,\n they only grow\n in the mountains.\n\nNobody really know why.", playo, "item");
            carrot.healItemDefine(6, 3);
            DroppedItem gCarrot = new DroppedItem(this, org, "You picked a carrot!", carrot, coord[0], coord[1]);
            addObject(gCarrot);
        }

        int[][] wolfLocs = {{39,31},{44,29},{23,21},{27,16}};
        for (int[] coord : wolfLocs){
            Wolf puppy = new Wolf(org, this, coord[0], coord[1]);
            addMortal(puppy);
        }
    }

    @Override
    public void startup(){
        ititHitMeshes();

        String[] signWords = {"Because carrots are a healthy snack,\n all carrots growing here are\n available to the general public",
                "Eating food can restore health\n beyond your maximum health\nThis is called \"Overhealing\"",
                "The Mountain Range Committee considers\n those who don't eat food regularly\n to be malnourished."};
        plantText(new FlavorText(8, 38, signWords,"A Sign"));

        plantText(new FlavorText(39, 18, "<- Bandit Fortress\n   Snowy Peak      ->","A Sign"));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.innerMountains);
        String[] solids = {";",":","^","O","o","S","R"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        Art coloring = new Art();
        lay1.influenceAll(coloring.mountainPallette1);
        lay1.findAndReplace(new SpecialText("R",coloring.mountainPallette1), new SpecialText(":", coloring.mtnPeakPallette1));
        lay1.findAndReplace(new SpecialText("o",coloring.mountainPallette1), new SpecialText("o", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("O",coloring.mountainPallette1), new SpecialText("O", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("0",coloring.mountainPallette1), new SpecialText("0", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText(".",coloring.mountainPallette1), new SpecialText(".", coloring.mountainPallette2));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        addItems();

        genericRoomInitialize();
    }

    public InnerMountains(Player player){
        constructor(player);
        org = player.orgo;
        super.index = 1;
    }
}
