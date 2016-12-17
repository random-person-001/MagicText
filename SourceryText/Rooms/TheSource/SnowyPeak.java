/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.LockedDoor;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.PolarBear;
import SourceryText.GameObjects.TheSource.Snowflake;
import SourceryText.GameObjects.TheSource.Wolf;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.util.Random;

/**
 * A Snowy Mountain Peak, where there's an entrance to a witch hut and a dangerous Polar Bear in the way of the rather pretty Ice Caves
 *
 * @author Jared
 *         <p>
 *         So Far:
 *         > You done a fair amount of zone 1
 *         > You came through the InnerMountains
 *         <p>
 *         What Generally Happens Here:
 *         > You battle a dangerous Polar Bear!
 *         > Upon its defeat, you get the key to the Witch Hut, which you can now get to to solve a puzzle.
 */
public class SnowyPeak extends Room {

    public SnowyPeak(Player player) {
        super(player);
        strRoomName = "SnowyPeak";
    }

    @Override
    protected String loop(Player play) {
        while (exitCode.equals("")) {
            try {
                Thread.sleep(50);

                if (play.getX() <= 0) {
                    setNewRoom("InnerMountains", play, 14, 64);
                }
                if (play.getX() == 179 && play.getY() == 6) {
                    setNewRoom("IceCaves", play, 36, 39);
                }
                if (play.getX() == 114 && play.getY() == 6){
                    setNewRoom("WitchHut", play, 12, 6);
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    /**
     * Everything that self-updates and can be paused (and acts nonexistent during paused) should go here.
     */
    @Override
    public void addItems() {
        Random randy = new Random();

        for (int flakes = 0; flakes < 300; flakes++) {
            addObject(new Snowflake(org, this, randy.nextInt(roomWidth), randy.nextInt(roomHeight)));
        }

        int[][] wolfLocs = {{32, 10}, {45, 14}, {58, 8}};
        for (int[] coord : wolfLocs) {
            Wolf puppy = new Wolf(org, this, coord[0], coord[1]);
            addMortal(puppy);
        }

        PolarBear daBear = new PolarBear(org, this, 139, 10, true);
        addMortal(daBear);

        LockedDoor witchHutDoor = new LockedDoor("Witch Hut Key", 100, 111, 7, this, org);
        addObject(witchHutDoor);
    }

    @Override
    public void startup() {
        String[] signWords = {"WARNING:\n Polar bears ahead.\n RUN AWAY if one attacks you!", "However, they are known for stealing\n keys and whatnot.\nGetting them back is not recommended though"};
        plantText(new FlavorText(114, 9, signWords, "A Sign"));

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.snowyPeak);

        Layer lay1 = new Layer(base, "Test");
        Art coloring = new Art();
        lay1.influenceAll(coloring.mtnPeakPallette1);
        lay1.findAndReplace(new SpecialText("W", coloring.mtnPeakPallette1), new SpecialText("W", coloring.mtnPeakPallette2));
        lay1.findAndReplace(new SpecialText("-", coloring.mtnPeakPallette1), new SpecialText("-"));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"0", "o", "O", "W", "#", "\\", "-", "S"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }
}
