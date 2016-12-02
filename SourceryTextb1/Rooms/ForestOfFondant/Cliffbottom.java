package SourceryTextb1.Rooms.ForestOfFondant;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.DroppedItem;
import SourceryTextb1.GameObjects.Item;
import SourceryTextb1.GameObjects.Player;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.Layer;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.SpecialText;

import java.awt.*;

/**
 * The landing Room for zone 2
 * Created by riley on 30-Nov-2016.
 */
public class Cliffbottom extends Room {
    public Cliffbottom(Player player) {
        super(player);
        strRoomName = "Cliffbottom";
    }

    @Override
    protected String loop(Player play) {
        play.goTo(206,63);

        while (exitCode.equals("")) {
            if (play.getY() >= 270) {
                setNewRoom("switch to zone 1", play, 0, 0);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    @Override
    public void addItems() {
        /*int[][] yetiLocs = {{37, 14}, {43, 12}}; // X and Y are switched.
        for (int[] station : yetiLocs) {
            CaveYeti yeti = new CaveYeti(org, this, station[0], station[1]); // X and Y switched.  I'm really sorry.
            addMortal(yeti);
        }
        */

        Item berries = new Item("Suspicious Berries", "You found these red\n berries while exploring \n a forest, but they\n look, well, suspicous. \n You can try to eat \n them if you want.", "item");
        berries.healItemDefine(-10, 0); // It hurts you
        DroppedItem dBerries = new DroppedItem(this, "You found some berries!", berries, 13, 36);
        addObject(dBerries);

        Bandit loner = new Bandit(org, this, 12, 37); // X and Y switched.  I'm really sorry.
        addMortal(loner);
    }

    @Override
    public void startup() {
        Color lightTreeGreen = new Color(27, 145, 17);
        Color shadowTreeGreen = new Color(29, 120, 19);
        Color normalGrass = new Color(72, 150, 69);
        Color otherGrass = new Color(66, 150, 58);
        //Color dirt = new Color(0, 0, 0);
        //Color dirt = new Color(44, 46, 23);
        Color dirt = new Color(29, 31, 15);
        Color deepWater4 = new Color(21, 47, 145);
        Color deepWater3 = new Color(24, 67, 145);
        Color deepWater2 = new Color(19, 91, 145);
        Color deepWater1 = new Color(19, 103, 145);

        Color sand3 = new Color(221, 192, 89);
        Color sand2 = new Color(158, 168, 72);
        Color sand1 = new Color(109, 122, 48);

        Color rock1 = new Color(89, 83, 77);
        Color rock2 = new Color(74, 69, 64);
        Color rock3 = new Color(87, 77, 72);



        Art arty = new Art();
        String[][] base = Art.strToArray(arty.forest);
        Layer lay1 = new Layer(base, "Base");

        lay1.findAndReplace(new SpecialText("t"), new SpecialText(".", otherGrass, lightTreeGreen), 20);
        lay1.findAndReplace(new SpecialText("t"), new SpecialText(" ", lightTreeGreen, lightTreeGreen));
        lay1.findAndReplace(new SpecialText("h"), new SpecialText(" ", shadowTreeGreen, shadowTreeGreen));
        lay1.findAndReplace(new SpecialText(" "), new SpecialText(".", otherGrass, dirt), 15);
        lay1.findAndReplace(new SpecialText(" "), new SpecialText("_", normalGrass, dirt), 15);
        lay1.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, dirt));

        lay1.findAndReplace(new SpecialText("r"), new SpecialText("o", rock1, rock2), 30);
        lay1.findAndReplace(new SpecialText("r"), new SpecialText("O", rock2, rock2), 30);
        lay1.findAndReplace(new SpecialText("r"), new SpecialText("O", rock3, rock2));

        lay1.findAndReplace(new SpecialText("w"), new SpecialText("~", deepWater2, deepWater1), 10);
        lay1.findAndReplace(new SpecialText("w"), new SpecialText(" ", deepWater1, deepWater1));

        lay1.findAndReplace(new SpecialText("4"), new SpecialText(" ", deepWater4, deepWater4));
        lay1.findAndReplace(new SpecialText("3"), new SpecialText(" ", deepWater3, deepWater3));
        lay1.findAndReplace(new SpecialText("2"), new SpecialText(" ", deepWater2, deepWater2));
        lay1.findAndReplace(new SpecialText("1"), new SpecialText(" ", deepWater1, deepWater1));

        lay1.findAndReplace(new SpecialText("d"), new SpecialText(":", sand2, sand3), 20);
        lay1.findAndReplace(new SpecialText("d"), new SpecialText(" ", sand3, sand3));
        lay1.findAndReplace(new SpecialText("f"), new SpecialText(".", sand3, sand2), 15);
        lay1.findAndReplace(new SpecialText("f"), new SpecialText(" ", sand2, sand2));
        lay1.findAndReplace(new SpecialText("g"), new SpecialText("~", dirt, sand1), 15);
        lay1.findAndReplace(new SpecialText("g"), new SpecialText(" ", sand1, sand1));


        /*
        A more text based theme, without backgrounds.
        lay1.findAndReplace(new SpecialText("t"), new SpecialText("O", lightTreeGreen));
        lay1.findAndReplace(new SpecialText("h"), new SpecialText("o", shadowTreeGreen));
        lay1.findAndReplace(new SpecialText(" "), new SpecialText(".", otherGrass), 2);
        lay1.findAndReplace(new SpecialText(" "), new SpecialText("_", dirt), 4);

        lay1.findAndReplace(new SpecialText("r"), new SpecialText(" ", mainRiverWater, mainRiverWater));
        lay1.findAndReplace(new SpecialText("s"), new SpecialText("~", fastRiverWater, mainRiverWater));

        lay1.findAndReplace(new SpecialText("w"), new SpecialText("~", deepWater2, deepWater1), 10);
        lay1.findAndReplace(new SpecialText("w"), new SpecialText(" ", deepWater1, deepWater1));

        lay1.findAndReplace(new SpecialText("4"), new SpecialText(" ", deepWater4, deepWater4));
        lay1.findAndReplace(new SpecialText("3"), new SpecialText(" ", deepWater3, deepWater3));
        lay1.findAndReplace(new SpecialText("2"), new SpecialText(" ", deepWater2, deepWater2));
        lay1.findAndReplace(new SpecialText("1"), new SpecialText(" ", deepWater1, deepWater1));

        lay1.findAndReplace(new SpecialText("d"), new SpecialText(":", sand2), 20);
        lay1.findAndReplace(new SpecialText("d"), new SpecialText(":", sand3));
        lay1.findAndReplace(new SpecialText("f"), new SpecialText(".", sand3), 15);
        lay1.findAndReplace(new SpecialText("f"), new SpecialText(".", sand2));
        lay1.findAndReplace(new SpecialText("g"), new SpecialText(".", dirt), 15);
        lay1.findAndReplace(new SpecialText("g"), new SpecialText(".", sand1));
         */

        org.addLayer(lay1);
        initHitMeshes(lay1);
        String[] solids = {"t","h"};
        addToBaseHitMesh(base, solids);

        org.roomBackground = rock2;

        addItems();

        genericRoomInitialize();
    }
}