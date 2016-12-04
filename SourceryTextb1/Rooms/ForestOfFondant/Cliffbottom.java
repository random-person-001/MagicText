package SourceryTextb1.Rooms.ForestOfFondant;

import SourceryTextb1.Art;
import SourceryTextb1.GameObjects.*;
import SourceryTextb1.GameObjects.ForestOfFondant.Alligator;
import SourceryTextb1.GameObjects.ForestOfFondant.FlammableTree;
import SourceryTextb1.GameObjects.TheSource.Bandit;
import SourceryTextb1.SpecialText;
import SourceryTextb1.Rooms.Room;
import SourceryTextb1.Layer;

import java.awt.*;

/**
 * The landing Room for zone 2
 * You find Sven here, and a fondant grove.  Also, there's a river and a dock.
 * Created by riley on 30-Nov-2016.
 */
public class Cliffbottom extends Room {
    public Cliffbottom(Player player) {
        super(player);
        strRoomName = "Cliffbottom";
    }

    @Override
    protected String loop(Player play) {
        play.goTo(206,113);

        while (exitCode.equals("")) {
            if (play.getY() >= 270) {
                setNewRoom("switch to zone 1", play, 0, 0);
            }
            if (play.getY() <= 1) {
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
        //int[][] allyLocs = {{97, 100}, {97, 102}, {97, 104}, {97, 106}, {101, 99}, {101, 101}, {101, 103}, {101, 105}
        //        , {100, 98}, {100, 100}, {100, 102}, {100, 104}, {98, 111}};
        int[][] allyLocs2 = {{195, 100}, {216, 102}, {217, 114}, {141, 115}, {134, 102}, {131, 97}, {169, 98}, {184, 100}};
        for (int[] coord : allyLocs2) {
            Alligator ally = new Alligator(this, coord[0], coord[1]);
            addMortal(ally);
        }

        Item berries = new Item("Suspicious Berries", "You found these red\n berries while exploring \n a forest, but they\n look, well, suspicous. \n You can try to eat \n them if you want.", "item");
        berries.healItemDefine(-10, 0); // It hurts you
        DroppedItem dBerries = new DroppedItem(this, "You found some berries!", berries, 130, 108);
        addObject(dBerries);

        Bandit loner = new Bandit(org, this, 129, 109);
        addMortal(loner);


        Item dillChip = new Item("dillTaterChip", "It slows down your \n perception of time \n so you can meditate \n on your decisions!", "item");
        dillChip.setDuration(60 * 1000);
        dillChip.setDescMode("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky!\n You found a dill potato chip!", dillChip, 121, 100));

        Item fondantChunk = new Item("fondantChunk", "It puts the world in a\n sugar rush!\n Feels like the day after\n Halloween ", "item");
        fondantChunk.setDuration(30 * 1000);
        fondantChunk.setDescMode("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky!\n You found a chunk of fondant!\n But beware: eating it will make \n you super hyper.", fondantChunk, 203, 24));
    }

    @Override
    protected void specialInspect(int x, int y, Player inspector) {
        if (x == 183 && y == 49) {
            org.removeLayer("veil");
            removeFromBaseHitMesh(183, 50);
        }
        if (x == 211 && y == 47) {
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

        Color sand3 = new Color(221, 192, 89);
        Color sand2 = new Color(158, 168, 72);
        Color sand1 = new Color(109, 122, 48);

        Color rock1 = new Color(89, 83, 77);
        Color rock2 = new Color(74, 69, 64);
        Color rock3 = new Color(87, 77, 72);

        Color dock1 = new Color(67, 45, 23);
        Color dock2 = new Color(39, 26, 13);


        Art arty = new Art();
        String[][] base = Art.strToArray(arty.forest);
        Layer forestBackground = new Layer(base, "Base");

        Layer forestRiver = new Layer(Art.strToArray(arty.forestRiver), "river");
        forestRiver.setX(54);
        forestRiver.setY(0);

        Layer kiosk = new Layer(Art.strToArray(arty.forestKiosk), "kiosk");
        kiosk.setX(57);
        kiosk.setY(141);
        
        Layer forestVeil = new Layer(Art.strToArray(arty.forestVeil), "veil");
        forestVeil.setX(47);
        forestVeil.setY(132);

        SpecialText[] trees = { new SpecialText("o", normalGrass, lightTreeGreen),  // Cuz the burnable forest wants to know them
                new SpecialText("o", otherGrass, lightTreeGreen), new SpecialText("O", otherGrass, lightTreeGreen),
                new SpecialText("0", otherGrass, lightTreeGreen), new SpecialText(" ", lightTreeGreen, lightTreeGreen),
                new SpecialText(" ", shadowTreeGreen, shadowTreeGreen)};
        forestBackground.findAndReplace(new SpecialText("t"), trees[0], 17);
        forestBackground.findAndReplace(new SpecialText("t"), trees[1], 17);
        forestBackground.findAndReplace(new SpecialText("t"), trees[2], 17);
        forestBackground.findAndReplace(new SpecialText("t"), trees[3], 17);
        forestBackground.findAndReplace(new SpecialText("t"), trees[4]);
        forestBackground.findAndReplace(new SpecialText("h"), trees[5]);
        forestVeil.findAndReplace(new SpecialText("t"), trees[0], 17);
        forestVeil.findAndReplace(new SpecialText("t"), trees[1], 17);
        forestVeil.findAndReplace(new SpecialText("t"), trees[2], 17);
        forestVeil.findAndReplace(new SpecialText("t"), trees[3], 17);
        forestVeil.findAndReplace(new SpecialText("t"), trees[4]);
        forestVeil.findAndReplace(new SpecialText("h"), trees[5]);

        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText(".", otherGrass, dirt), 15);
        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText("_", normalGrass, dirt), 15);
        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, dirt));

        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("o", rock1, rock2), 30);
        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("O", rock2, rock2), 30);
        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("O", rock3, rock2));

        forestBackground.findAndReplace(new SpecialText("d"), new SpecialText(":", sand2, sand3), 20);
        forestBackground.findAndReplace(new SpecialText("d"), new SpecialText(" ", sand3, sand3));
        forestBackground.findAndReplace(new SpecialText("f"), new SpecialText(".", sand3, sand2), 15);
        forestBackground.findAndReplace(new SpecialText("f"), new SpecialText(" ", sand2, sand2));
        forestBackground.findAndReplace(new SpecialText("g"), new SpecialText("~", dirt, sand1), 15);
        forestBackground.findAndReplace(new SpecialText("g"), new SpecialText(" ", sand1, sand1));

        forestBackground.findAndReplace(new SpecialText("j"), new SpecialText("_", dock1, dock2));
        forestBackground.findAndReplace(new SpecialText("k"), new SpecialText(".", dock1, dock2), 30);
        forestBackground.findAndReplace(new SpecialText("k"), new SpecialText(" ", dock1, dock2));

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

        org.addLayer(forestBackground);
        org.addLayer(forestRiver);
        org.addLayer(kiosk);
        initHitMeshes(forestBackground);
        String[] solids = {"r"};
        addToBaseHitMesh(base, solids);
        addToBaseHitMesh(183, 50); // Hiding the kiosk area
        addToObjHitMesh(120, 97); // Dock
        addToObjHitMesh(120, 98);
        addToObjHitMesh(120, 99);
        addToObjHitMesh(120, 100);
        addToObjHitMesh(120, 101); // river
        org.addLayer(forestVeil);

        FlammableTree ft2 = new FlammableTree(this, forestVeil, trees);
        addObject(ft2);
        FlammableTree ft1 = new FlammableTree(this, forestBackground, trees);
        addObject(ft1);
        WaterPool wp1 = new WaterPool(this, forestRiver, "1", 1);
        WaterPool wp2 = new WaterPool(this, forestRiver, "2", 2);
        WaterPool wp3 = new WaterPool(this, forestRiver, "3", 3);
        WaterPool wp4 = new WaterPool(this, forestRiver, "4", 4);
        addObject(wp1);
        addObject(wp2);
        addObject(wp3);
        addObject(wp4);

        org.roomBackground = dirt;

        addItems();

        genericRoomInitialize();
    }
}
