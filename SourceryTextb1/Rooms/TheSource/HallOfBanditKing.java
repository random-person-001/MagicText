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
 *
 * @author Jared
 *         <p>
 *         So Far:
 *         > You have ventured far into the mountains, ready for your ultimate confrontation with the bandits
 *         > Collected some loot; usually ready to take on fortress
 *         <p>
 *         What Generally Happens Here:
 *         > You fight your way through the bandits and walk up to the hall of The King of the Bandits
 */


public class HallOfBanditKing extends Room {

    public HallOfBanditKing(Player player) {
        super(player);
        strRoomName = "HallOfBanditKing";
    }

    @Override
    protected String loop(Player play) {
        while (exitCode.equals("")) {
            try {
                Thread.sleep(200);
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
    }

    @Override
    public void startup() {

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.banditKingHall);
        Layer lay1 = new Layer(base, "RoomLayer");
        Art coloring = new Art();
        lay1.influenceAll(coloring.mountainPallette1);
        lay1.findAndReplace(new SpecialText("#", coloring.mountainPallette1), new SpecialText("#", new Color(175, 175, 175), new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("o", coloring.mountainPallette1), new SpecialText("o", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("O", coloring.mountainPallette1), new SpecialText("O", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText("0", coloring.mountainPallette1), new SpecialText("0", coloring.mountainPallette2));
        lay1.findAndReplace(new SpecialText(".", coloring.mountainPallette1), new SpecialText(".", coloring.mountainPallette2));
        highlightFlavorText(lay1);
        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {";", ":", "^", "O", "o", "O", "#","_","\\","/","|"};
        addToBaseHitMesh(base, solids);

        //addItems();

        genericRoomInitialize();
    }
}
