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

    public SnowyPeak(Player player){
        super(player);
        strRoomName = "SnowyPeak";
    }

    @Override
    protected String loop(Player play){
        while (exitCode.equals("")){
            try {
                Thread.sleep(50);

                if (play.getX() <= 0) {
                    setNewRoom("InnerMountains", play, 14, 64);
                }
                if (play.getX() == 179 && play.getY() == 6){
                    setNewRoom("IceCaves", play, 36, 39);
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
        Random randy = new Random();

        for (int flakes = 0; flakes < 300 ; flakes++) {
            addObject(new Snowflake(org, this, randy.nextInt(roomWidth), randy.nextInt(roomHeight)));
        }

        int[][] wolfLocs = {{32,10},{45,14},{58,8}};
        for (int[] coord : wolfLocs){
            Wolf puppy = new Wolf(org, this, coord[0], coord[1]);
            addMortal(puppy);
        }

        PolarBear daBear = new PolarBear(org, this, 139, 10, true);
        addMortal(daBear);

        LockedDoor witchHutDoor = new LockedDoor("Witch Hut Key", 100, 111, 7, this, org);
        addObject(witchHutDoor);
    }

    @Override
    public void startup(){
        roomWidth = 250;
        ititHitMeshes();

        String[] signWords = {"WARNING:\n Polar bears ahead.\n RUN AWAY if one attacks you!","However, they are known for stealing\n keys and whatnot.\nGetting them back is not recommended though"};
        plantText(new FlavorText(114, 9, signWords,"A Sign"));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.snowyPeak);
        String[] solids = {"0","o","O","W","#","\\","-","S"};
        addToBaseHitMesh(base, solids);
        Layer lay1 = new Layer(base, "Test");
        Art coloring = new Art();
        lay1.influenceAll(coloring.mtnPeakPallette1);
        lay1.findAndReplace(new SpecialText("W",coloring.mtnPeakPallette1), new SpecialText("W", coloring.mtnPeakPallette2));
        lay1.findAndReplace(new SpecialText("-",coloring.mtnPeakPallette1), new SpecialText("-"));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        addItems();

        genericRoomInitialize();
    }
}
