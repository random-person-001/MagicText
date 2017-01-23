/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.SavePoint;
import SourceryText.GameObjects.TheSource.Wolf;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * The inner mountains area, filled with carrots and the fork in the road between the snowy peak and the bandit fortress
 *
 * @author Jared
 *         <p>
 *         So Far:
 *         > You have been introduced to the backstory
 *         > You've mastered the Tutorial Basement
 *         <p>
 *         What Generally Happens Here:
 *         > You eat a bunch of carrots and fight some monsters.  Nothing too interesting.
 *         > You decide to go either to Snowy Peak or the Bandit Fortress (Snowy Peak winds up being a dead-end)
 */


public class InnerMountains extends Room {

    public InnerMountains(Player player) {
        super(player);
        strRoomName = "InnerMountains";
    }

    @Override
    protected String playerLoop(Player play) {
        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (play.getY() == 46) {
                    setNewRoom("Cliffside", play, 1, 137);
                }
                if (play.getX() == 65) {
                    setNewRoom("SnowyPeak", play, 15, 1);
                }
                if (play.getY() == 0) {
                    setNewRoom("BanditFortress", play, 103, 67);
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
        int[][] carrotLocs = {{18, 32}, {44, 28}, {21, 15}, {22, 22}, {48, 14}};
        for (int[] coord : carrotLocs) {
            Item carrot = new Item("Carrot", "For some reason,\n they only grow\n in the mountains.\n\nNobody really know why.", "item");
            carrot.healItemDefine(6, 3);
            DroppedItem gCarrot = new DroppedItem(this, "You picked a carrot!", carrot, coord[0], coord[1]);
            addObject(gCarrot);
        }

        int[][] wolfLocs = {{39, 31}, {44, 29}, {23, 21}, {27, 16}};
        for (int[] coord : wolfLocs) {
            Wolf puppy = new Wolf(org, this, coord[0], coord[1]);
            addMortal(puppy);
        }

        addSavePoint(12, 33);
    }

    @Override
    public void startup() {


        String[] signWords = {"Because carrots are a healthy snack,\n all carrots growing here are\n available to the general public",
                "Eating food can restore health\n beyond your maximum health\nThis is called \"Overhealing\"",
                "The Mountain Range Committee considers\n those who don't eat food regularly\n to be malnourished."};
        plantText(new FlavorText(signWords, "A Sign"), 8, 38);

        plantText(new FlavorText("<- Bandit Fortress\n   Snowy Peak      ->", "A Sign"), 39, 18);

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.innerMountains);
        Layer lay1 = new Layer(base, "Test");
        Art coloring = new Art();

        lay1.findAndReplace(new SpecialText(":"), new SpecialText(":", null, new Color(51, 43, 38)));
        lay1.findAndReplace(new SpecialText(";"), new SpecialText(";", null, new Color(51, 43, 38)));
        lay1.findAndReplace(new SpecialText("^"), new SpecialText("^", null, new Color(51, 43, 38)));

        lay1.setAllFg(coloring.mountainPallette1);
        lay1.findAndReplace(new SpecialText("R", coloring.mountainPallette1), new SpecialText(":", coloring.mtnPeakPallette1));
        lay1.findAndReplace(new SpecialText("o", coloring.mountainPallette1), new SpecialText("o", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("O", coloring.mountainPallette1), new SpecialText("O", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("0", coloring.mountainPallette1), new SpecialText("0", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText(".", coloring.mountainPallette1), new SpecialText(".", coloring.mountainPallette2));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {";", ":", "^", "O", "o", "S", "R"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }
}
