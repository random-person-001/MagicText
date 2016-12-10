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

    int fireplaceFireState = 0;

    @Override
    protected String loop(Player play) {
        int count = 0;

        while (exitCode.equals("")) {
            try {
                Thread.sleep(200);
                Layer roomLayer = org.getLayer("roomLayer");
                if (roomLayer != null) {
                    Color bubbleColor = new Color(50, 125, 0);
                    Color stewColor = new Color(25, 75, 0);
                    roomLayer.findAndReplace(new SpecialText("O", bubbleColor, stewColor), new SpecialText(" ", bubbleColor, stewColor));
                    roomLayer.findAndReplace(new SpecialText("o", bubbleColor, stewColor), new SpecialText("O", bubbleColor, stewColor));
                    roomLayer.findAndReplace(new SpecialText(".", bubbleColor, stewColor), new SpecialText("o", bubbleColor, stewColor));
                    roomLayer.findAndReplace(new SpecialText(" ", bubbleColor, stewColor), new SpecialText(".", bubbleColor, stewColor), 15);

                    Color fireBkg = new Color(120, 25, 0);
                    switch (fireplaceFireState) {
                        case 0:
                            roomLayer.findAndReplace(new SpecialText("w", new Color(150, 40, 0), fireBkg), new SpecialText("w", new Color(165, 70, 0), fireBkg));
                            break;
                        case 1:
                            roomLayer.findAndReplace(new SpecialText("w", new Color(165, 70, 0), fireBkg), new SpecialText("W", new Color(180, 100, 0), fireBkg));
                            break;
                        case 2:
                            roomLayer.findAndReplace(new SpecialText("W", new Color(180, 100, 0), fireBkg), new SpecialText("W", new Color(195, 130, 0), fireBkg));
                            break;
                        case 3:
                            roomLayer.findAndReplace(new SpecialText("W", new Color(195, 130, 0), fireBkg), new SpecialText("W", new Color(180, 100, 0), fireBkg));
                            break;
                        case 4:
                            roomLayer.findAndReplace(new SpecialText("W", new Color(180, 100, 0), fireBkg), new SpecialText("w", new Color(165, 70, 0), fireBkg));
                            break;
                        case 5:
                            roomLayer.findAndReplace(new SpecialText("w", new Color(165, 70, 0), fireBkg), new SpecialText("w", new Color(150, 40, 0), fireBkg));
                            break;
                    }
                    fireplaceFireState++;
                    if (fireplaceFireState > 5) fireplaceFireState = 0;
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
        Item item2 = new Item("Wool Scarf", "A fluffy scarf.\n\nWhat's not to love in\n a scarf?\n\nThey're magical, comfy, and\n fashionable!\nAwesome, right?", "equipment");
        item2.setEquipvals(2, 0, 0, 0, 0, 1, 0, "weapon");
        DroppedItem forgottenScarf = new DroppedItem(this, "You found a wool scarf!", item2, 29, 5);
        addObject(forgottenScarf);
    }

    @Override
    public void startup() {
        Art arty = new Art();

        plantText(new FlavorText(31, 4, "...Of all magic, Arcane is the king;\n The world is simply not complex\n enough to support Fire, Ice, and Dark...", "Book"));
        plantText(new FlavorText(32, 4, "...Arcane is primal; it is magic itself.\n Fire, Ice, and Dark are all false copies\n of it; they are not fit to be magic...", "Book"));
        plantText(new FlavorText(33, 4, "...For decades the debate raged onward;\n Not even the grand council of\n the Magic Academy could end it...", "Book"));

        plantText(new FlavorText(36, 4, "...The discovery of The Source is almost as\n influential as its potential misuse...", "Book"));
        plantText(new FlavorText(37, 4, "...Amidst the endless argument\n over the fate of magic, many took action\n while others continued to argue...", "Book"));
        plantText(new FlavorText(38, 4, "...However, exiting the universe proved\n difficult, until someone found two ropes\n strong enough to survive...", "Book"));

        plantText(new FlavorText(41, 4, "...Unfortunately, the person who discovered\n the ropes strongly opposed its use...", "Book"));
        plantText(new FlavorText(42, 4, "...With his own magical hands he built\n two giant labyrinths to stop all\n intruders, each guarding a single rope...", "Book"));
        plantText(new FlavorText(43, 4, "...Countless breakthroughs were attempted\nAll but one had ultimately failed.\nThat one rope has yet to be found...", "Book"));

        String[][] base = Art.strToArray(arty.witchHut);
        Layer lay1 = new Layer(base, "roomLayer");

        Color rugStringColor = new Color (255, 225, 175);
        Color rugOuterColor = new Color (102, 42, 26);
        Color rugInnerColor = new Color (102, 44, 31);
        lay1.findAndReplace(new SpecialText("+"), new SpecialText("+", rugStringColor, rugOuterColor));
        lay1.findAndReplace(new SpecialText("~"), new SpecialText("~", rugStringColor, rugOuterColor));
        lay1.findAndReplace(new SpecialText(":"), new SpecialText(":", rugStringColor, rugOuterColor));
        lay1.findAndReplace(new SpecialText("c"), new SpecialText(" ", null, rugInnerColor));

        lay1.findAndReplace(new SpecialText("_"), new SpecialText("_", new Color(33, 18, 5), new Color(38, 19, 6)));

        lay1.findAndReplace(new SpecialText("S"), new SpecialText(" ", new Color(50, 125, 0), new Color(25, 75, 0)));

        lay1.findAndReplace(new SpecialText("0"), new SpecialText("0", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("|"), new SpecialText("|", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("-"), new SpecialText("-", null, new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("#"), new SpecialText("#", new Color (120, 110, 100), new Color(55, 55, 55)));

        lay1.findAndReplace(new SpecialText(">"), new SpecialText(">", new Color(77, 52, 34), new Color(36, 28, 21)));

        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(60, 150, 0)),33);
        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(0, 100, 220)),50);
        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(150, 40, 40)));

        lay1.findAndReplace(new SpecialText("f"), new SpecialText("w", new Color(150, 40, 0), new Color(120, 25, 0)));

        org.addLayer(lay1);

        initHitMeshes(lay1);
        String[] solids = {"0", "-", "|", "#", "m", "f", "W"};
        addToBaseHitMesh(base, solids);

        addItems();

        genericRoomInitialize();
    }
}
