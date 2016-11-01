/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.LockedDoor;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.PolarBear;
import SourceryTextb1.GameObjects.TheSource.Snowflake;
import SourceryTextb1.GameObjects.TheSource.Wolf;
import SourceryTextb1.ImageOrg;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.util.Random;

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


public class SnowyPeak extends Room {
    private ImageOrg org;

    @Override
    protected String loop(){
        Random randy = new Random();
        while (exitCode.equals("")){
            try {
                //System.out.println("It's chilly here in SnowyPeak!");
                Thread.sleep(50);

                if (!isPaused)
                    addObject(new Snowflake(org, this, randy.nextInt(220), 0));

                if (getPlayer().getX() == 0){
                    setNewRoom("InnerMountains", 64, 14);
                }
            } catch (InterruptedException ignored) {}
        }
        //System.out.println("WOW, it was cold!");
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems(){
        int[][] wolfLocs = {{32,10},{45,14},{58,8}};
        for (int[] coord : wolfLocs){
            Wolf puppy = new Wolf(org, this, coord[0], coord[1]);
            addMortal(puppy);
        }

        PolarBear daBear = new PolarBear(org, this, 139, 10, true);
        addMortal(daBear);

        LockedDoor witchHutDoor = new LockedDoor ("Witch Hut Key", 100, 111, 7, this, org);
        addObject(witchHutDoor);
    }

    @Override
    public void startup(){
        roomWidth = 250;
        ititHitMeshes();

        /*
        String[] signWords = {"Because carrots are a healthy snack,\n all carrots growing here are\n available to the general public",
                "Eating food can restore health\n beyond your maximum health\nThis is called \"Overhealing\"",
                "The Mountain Range Committee considers\n those who don't eat food regularly\n to be malnourished."};
        plantText(new FlavorText(8, 38, signWords,"A Sign"));
        */

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.snowyPeak);
        String[] solids = {"0","o","O","W","#","\\","-"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        Art coloring = new Art();
        lay1.influenceAll(coloring.mtnPeakPallette1);
        lay1.findAndReplace(new SpecialText("W",coloring.mtnPeakPallette1), new SpecialText("W", coloring.mtnPeakPallette2));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        addItems();

        genericRoomInitialize();
    }

    public SnowyPeak(Player player){
        constructor(player);
        org = player.orgo;
        super.index = 1;
    }
}
