/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryTextb1.Rooms.TheSource;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Spider;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * The initial view of the Source Pit.
 *
 * @author 119184
 *         <p>
 *         So Far:
 *         > You have been introduced to the backstory
 *         > You've mastered the Tutorial Basement
 *         <p>
 *         What Generally Happens Here:
 *         > The owner of the house is sitting at the edge of the hole. He convinces you to put the world back in order.
 *         > Then you move on to the next level, where a cloned witch has an identity crisis!
 */


public class WitchHut extends Room {

    public WitchHut(Player player) {
        super(player);
        strRoomName = "SourcePit";
    }

    @Override
    protected String loop(Player play) {
        int count = 0;

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
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

    }

    @Override
    public void startup() {
        Art arty = new Art();

        String[][] base = Art.strToArray(arty.witchHut);
        Layer lay1 = new Layer(base, "base");
        highlightFlavorText(lay1);

        lay1.findAndReplace(new SpecialText("+"), new SpecialText("+", new Color(255, 225, 175), new Color(128, 54, 32)));
        lay1.findAndReplace(new SpecialText("~"), new SpecialText("~", new Color(255, 225, 175), new Color(128, 54, 32)));
        lay1.findAndReplace(new SpecialText(":"), new SpecialText(":", new Color(255, 225, 175), new Color(128, 54, 32)));
        lay1.findAndReplace(new SpecialText("c"), new SpecialText(" ", null, new Color(128, 55, 38)));

        lay1.findAndReplace(new SpecialText("_"), new SpecialText("_", new Color(64, 32, 10), new Color(51, 27, 8)));

        lay1.findAndReplace(new SpecialText("S"), new SpecialText("~", new Color(50, 125, 0), new Color(25, 75, 0)),35);
        lay1.findAndReplace(new SpecialText("S"), new SpecialText(" ", new Color(50, 125, 0), new Color(25, 75, 0)));

        lay1.findAndReplace(new SpecialText("0"), new SpecialText("0", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("|"), new SpecialText("|", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("-"), new SpecialText("-", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("#"), new SpecialText("#", new Color (110, 110, 100), new Color(65, 65, 65)));

        lay1.findAndReplace(new SpecialText(">"), new SpecialText(">", new Color(77, 52, 34), new Color(36, 28, 21)));

        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(60, 150, 0)),35);
        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(0, 100, 220)),35);
        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(150, 40, 40)));

        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"0", "-", "|", "#", "m", "f"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }
}
