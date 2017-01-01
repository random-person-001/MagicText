package SourceryText.Rooms.ForestOfFondant;

import SourceryText.Art;
import SourceryText.GameObjects.DroppedItem;
import SourceryText.GameObjects.ForestOfFondant.Alligator;
import SourceryText.GameObjects.ForestOfFondant.FlammableTree;
import SourceryText.GameObjects.Item;
import SourceryText.GameObjects.Player;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.GameObjects.TheSource.SnowPatch;
import SourceryText.GameObjects.WaterPool;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * The landing Room for zone 2
 * You find Sven here, and a fondant grove.  Also, there's a river and a dock.
 * Created by riley on 30-Nov-2016.
 */
public class FondantVillage extends Room {
    public FondantVillage(Player player) {
        super(player);
        strRoomName = "FondantVillage";
    }

    @Override
    protected String loop(Player play) {
        while (exitCode.equals("")) {
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
        dillChip.setSpellType("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky!\n You found a dill potato chip!", dillChip, 121, 100));

        Item fondantChunk = new Item("fondantChunk", "It puts the world in a\n sugar rush!\n Feels like the day after\n Halloween ", "item");
        fondantChunk.setDuration(30 * 1000);
        fondantChunk.setSpellType("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky! You found a chunk of fondant!\n But -   BEWARE:\n eating it will make you super hyper.", fondantChunk, 203, 24));

        Item perriwinkle = new Item("wrinkleInTime", "Distorts spacetime in a \n bubble around you, so \n nearby entities slow down \n in your perception of time", "item");
        perriwinkle.setDuration(60 * 1000);
        perriwinkle.setSpellType("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky! You found a potion that locally \n distorts spacetime.", perriwinkle, 135, 57));

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

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.fondantVillage);
        Layer forestBackground = new Layer(base, "Base");

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

        forestBackground.findAndReplace(new SpecialText("T"), new SpecialText("T", new Color(38, 77, 54), new Color(27, 51, 35)));

        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText(".", otherGrass, dirt), 15);
        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText("_", normalGrass, dirt), 15);
        forestBackground.findAndReplace(new SpecialText(" "), new SpecialText(" ", null, dirt));

        Color roadBkg = new Color(60, 40, 25);
        Color roadFg  = new Color(77, 58, 47);
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText(".", roadFg, roadBkg), 25);
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText("o", roadFg, roadBkg), 10);
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText(" ", roadFg, roadBkg));

        org.addLayer(forestBackground);
        initHitMeshes(forestBackground);
        String[] solids = {"r", "t", "h","T"};
        addToBaseHitMesh(base, solids);

        FlammableTree ft1 = new FlammableTree(this, forestBackground, trees);
        addObject(ft1);

        org.roomBackground = dirt;

        //addItems();

        genericRoomInitialize();
    }
}
