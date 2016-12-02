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
        play.goTo(206,103);

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
        DroppedItem dBerries = new DroppedItem(this, "You found some berries!", berries, 130, 98);
        addObject(dBerries);

        Bandit loner = new Bandit(org, this, 129, 99);
        addMortal(loner);
    }

    @Override
    protected void specialInspect(int x, int y, Player inspector) {
        if (x == 196 && y == 39) {
            org.removeLayer("veil");
            removeFromBaseHitMesh(196, 38);
        }
        if (x == 201 && y == 47) {
            // talk to Sven
        }
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
        Layer forestBackground = new Layer(base, "Base");
        
        Layer kiosk = new Layer(Art.strToArray(arty.forestKiosk), "kiosk");
        kiosk.setX(47);
        kiosk.setY(141);
        
        Layer forestVeil = new Layer(Art.strToArray(arty.forestVeil), "veil");
        forestVeil.setX(37);
        forestVeil.setY(132);


        forestVeil.findAndReplace(new SpecialText("t"), new SpecialText("o", normalGrass, lightTreeGreen), 17);
        forestVeil.findAndReplace(new SpecialText("t"), new SpecialText("o", otherGrass, lightTreeGreen), 17);
        forestVeil.findAndReplace(new SpecialText("t"), new SpecialText("O", otherGrass, lightTreeGreen), 17);
        forestVeil.findAndReplace(new SpecialText("t"), new SpecialText("0", otherGrass, lightTreeGreen), 17);
        forestVeil.findAndReplace(new SpecialText("t"), new SpecialText(" ", lightTreeGreen, lightTreeGreen));
        forestVeil.findAndReplace(new SpecialText("h"), new SpecialText(" ", shadowTreeGreen, shadowTreeGreen));

        forestBackground.findAndReplace(new SpecialText("t"), new SpecialText("o", normalGrass, lightTreeGreen), 17);
        forestBackground.findAndReplace(new SpecialText("t"), new SpecialText("o", otherGrass, lightTreeGreen), 17);
        forestBackground.findAndReplace(new SpecialText("t"), new SpecialText("O", otherGrass, lightTreeGreen), 17);
        forestBackground.findAndReplace(new SpecialText("t"), new SpecialText("0", otherGrass, lightTreeGreen), 17);
        forestBackground.findAndReplace(new SpecialText("t"), new SpecialText(" ", lightTreeGreen, lightTreeGreen));
        forestBackground.findAndReplace(new SpecialText("h"), new SpecialText(" ", shadowTreeGreen, shadowTreeGreen));

        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText(".", otherGrass, dirt), 15);
        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText("_", normalGrass, dirt), 15);
        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, dirt));

        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("o", rock1, rock2), 30);
        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("O", rock2, rock2), 30);
        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("O", rock3, rock2));

        forestBackground.findAndReplace(new SpecialText("w"), new SpecialText("~", deepWater2, deepWater1), 10);
        forestBackground.findAndReplace(new SpecialText("w"), new SpecialText(" ", deepWater1, deepWater1));

        forestBackground.findAndReplace(new SpecialText("4"), new SpecialText(" ", deepWater4, deepWater4));
        forestBackground.findAndReplace(new SpecialText("3"), new SpecialText(" ", deepWater3, deepWater3));
        forestBackground.findAndReplace(new SpecialText("2"), new SpecialText(" ", deepWater2, deepWater2));
        forestBackground.findAndReplace(new SpecialText("1"), new SpecialText(" ", deepWater1, deepWater1));

        forestBackground.findAndReplace(new SpecialText("d"), new SpecialText(":", sand2, sand3), 20);
        forestBackground.findAndReplace(new SpecialText("d"), new SpecialText(" ", sand3, sand3));
        forestBackground.findAndReplace(new SpecialText("f"), new SpecialText(".", sand3, sand2), 15);
        forestBackground.findAndReplace(new SpecialText("f"), new SpecialText(" ", sand2, sand2));
        forestBackground.findAndReplace(new SpecialText("g"), new SpecialText("~", dirt, sand1), 15);
        forestBackground.findAndReplace(new SpecialText("g"), new SpecialText(" ", sand1, sand1));


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

        org.addLayer(kiosk);
        org.addLayer(forestBackground);
        initHitMeshes(forestBackground);
        String[] solids = {"t","h"};
        addToBaseHitMesh(base, solids);
        addToBaseHitMesh(196, 38); // Hiding the kiosk area
        org.addLayer(forestVeil);

        org.roomBackground = dirt;

        addItems();

        genericRoomInitialize();
    }
}
