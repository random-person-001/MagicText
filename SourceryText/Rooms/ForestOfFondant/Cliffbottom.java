package SourceryText.Rooms.ForestOfFondant;

import SourceryText.Art;
import SourceryText.GameObjects.*;
import SourceryText.GameObjects.ForestOfFondant.Alligator;
import SourceryText.GameObjects.ForestOfFondant.FlammableTree;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.SpecialText;
import SourceryText.Rooms.Room;
import SourceryText.Layer;

import java.awt.*;

/**
 * The landing Room for zone 2
 * You find Sven here, and a colorful fondant grove.  Also, there's a river and a dock.
 * Created by riley on 30-Nov-2016.
 */
public class Cliffbottom extends Room {
    public Cliffbottom(Player player) {
        super(player);
        strRoomName = "Cliffbottom";
    }

    @Override
    protected String playerLoop(Player play) {
        if (play.isAt(0,0)){
            play.goTo(137, 113);
        }

        while (exitCode.equals("")) {
            if (play.getY() < 1){
                setNewRoom("FondantVillage", play, 48, play.getX());
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
            Alligator ally = new Alligator(this, coord[0] - 69, coord[1]);
            addMortal(ally);
        }

        Item berries = new Item("Suspicious Berries", "You found these red\n berries while exploring \n a forest, but they\n look, well, suspicous. \n You can try to eat \n them if you want.", "item");
        berries.healItemDefine(-10, 0); // It hurts you
        DroppedItem dBerries = new DroppedItem(this, "You found some berries!", berries, 61, 108);
        addObject(dBerries);

        Bandit loner = new Bandit(org, this, 60, 109);
        addMortal(loner);


        Item dillChip = new Item("dillTaterChip", "It slows down your \n perception of time \n so you can meditate \n on your decisions!", "item");
        dillChip.setDuration(60 * 1000);
        dillChip.setSpellType("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky!\n You found a dill potato chip!", dillChip, 52, 100));

        Item fondantChunk = new Item("fondantChunk", "It puts the world in a\n sugar rush!\n Feels like the day after\n Halloween ", "item");
        fondantChunk.setDuration(30 * 1000);
        fondantChunk.setSpellType("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky! You found a chunk of fondant!\n But -   BEWARE:\n eating it will make you super hyper.", fondantChunk, 90, 10));

        Item perriwinkle = new Item("wrinkleInTime", "Distorts spacetime in a \n bubble around you, so \n nearby entities slow down \n in your perception of time", "item");
        perriwinkle.setDuration(60 * 1000);
        perriwinkle.setSpellType("potion");
        super.addObject(new DroppedItem(this, "Oooh, lucky! You found a potion that locally \n distorts spacetime.", perriwinkle, 64, 57));

    }

    @Override
    protected void specialInspect(int x, int y, Player inspector) {
        if (x == 114 && y == 49) {
            org.removeLayer("veil");
            removeFromBaseHitMesh(183, 50);
        }
        if (x == 142 && y == 47) {
            // talk to Sven
        }
    }

    @Override
    public void startup() {
        boundedCamera = true;

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

        /*
        Layer forestRiver = new Layer(Art.strToArray(arty.forestRiver), "river");
        forestRiver.setY(54);
        forestRiver.setX(0);
        */

        Layer kiosk = new Layer(Art.strToArray(arty.forestKiosk), "kiosk");
        kiosk.setY(57);
        kiosk.setX(72);
        
        Layer forestVeil = new Layer(Art.strToArray(arty.forestVeil), "veil");
        forestVeil.setY(46);
        forestVeil.setX(63);

        Color fondantBudding1 = new Color(250, 58, 58);
        Color fondant1 = new Color(230, 128, 128);
        Color fondantDark1 = new Color(190, 98, 98);
        Color fondant2 = new Color(130, 128, 228);
        Color fondantDark2 = new Color(90, 98, 198);
        Color fondantBudding3 = new Color(240, 58, 248);
        Color fondant3 = new Color(230, 128, 228);
        Color fondantDark3 = new Color(190, 98, 198);
        Color fondant4 = new Color(230, 228, 68);
        Color fondantDark4 = new Color(190, 198, 1);
        Color fondantBudding5 = new Color(60, 250, 248);
        Color fondant5 = new Color(130, 228, 228);
        Color fondantDark5 = new Color(90, 198, 198);

        SpecialText[] trees = { new SpecialText("o", normalGrass, lightTreeGreen),  // Cuz the burnable forest wants to know them
                new SpecialText("o", otherGrass, lightTreeGreen), new SpecialText("O", otherGrass, lightTreeGreen),
                new SpecialText("0", otherGrass, lightTreeGreen), new SpecialText(" ", lightTreeGreen, lightTreeGreen),
                new SpecialText(" ", shadowTreeGreen, shadowTreeGreen),
                // Fondant stuff (cuz you can burn it)
                new SpecialText(" ", null, fondant1), new SpecialText(" ", null, fondant2),
                new SpecialText(" ", null, fondant3), new SpecialText(" ", null, fondant4),
                new SpecialText(" ", null, fondant5), new SpecialText(" ", null, fondantDark1),
                new SpecialText(" ", null, fondantDark2), new SpecialText(" ", null, fondantDark3),
                new SpecialText(" ", null, fondantDark4), new SpecialText(" ", null, fondantDark5),
                new SpecialText("*", fondantBudding1, shadowTreeGreen), new SpecialText("*", fondantBudding3, shadowTreeGreen),
                new SpecialText("*", fondantBudding5, shadowTreeGreen)};
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

        forestBackground.findAndReplace(new SpecialText("T"), new SpecialText("T", new Color(38, 77, 54), new Color(45, 64, 51)));

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

        Color riverRockFg = new Color(115, 128, 128);
        Color riverRockBkg = new Color(61, 87, 102);

        forestBackground.findAndReplace(new SpecialText("w"), new SpecialText("o", riverRockFg, riverRockBkg));

        forestBackground.findAndReplace(new SpecialText("l"), new SpecialText(" ", null, fondant1));
        forestBackground.findAndReplace(new SpecialText("m"), new SpecialText(" ", null, fondant2));
        forestBackground.findAndReplace(new SpecialText("n"), new SpecialText(" ", null, fondant3));
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText(" ", null, fondant4));
        forestBackground.findAndReplace(new SpecialText("p"), new SpecialText(" ", null, fondant5));
        forestBackground.findAndReplace(new SpecialText("q"), new SpecialText(" ", null, fondant3));
        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText(" ", null, fondant2));

        forestBackground.findAndReplace(new SpecialText("L"), new SpecialText(" ", null, fondantDark1));
        forestBackground.findAndReplace(new SpecialText("M"), new SpecialText(" ", null, fondantDark2));
        forestBackground.findAndReplace(new SpecialText("N"), new SpecialText(" ", null, fondantDark3));
        forestBackground.findAndReplace(new SpecialText("O"), new SpecialText(" ", null, fondantDark4));
        forestBackground.findAndReplace(new SpecialText("P"), new SpecialText(" ", null, fondantDark5));
        forestBackground.findAndReplace(new SpecialText("Q"), new SpecialText(" ", null, fondantDark3));
        forestBackground.findAndReplace(new SpecialText("R"), new SpecialText(" ", null, fondantDark2));

        forestBackground.findAndReplace(new SpecialText("x"), new SpecialText("*", fondantBudding3, shadowTreeGreen));
        forestBackground.findAndReplace(new SpecialText("y"), new SpecialText("*", fondantBudding5, shadowTreeGreen));
        forestBackground.findAndReplace(new SpecialText("z"), new SpecialText("*", fondantBudding1, shadowTreeGreen));

        org.addLayer(forestBackground);
        //org.addLayer(forestRiver);
        org.addLayer(kiosk);
        initHitMeshes(forestBackground);
        String[] solids = {"r", "t", "h","T","w","l","m","n","o","p","L","M","N","O","P","x","y","z"};
        addToBaseHitMesh(base, solids);
        addToBaseHitMesh(114, 50); // Hiding the kiosk area

        org.addLayer(forestVeil);

        FlammableTree ft2 = new FlammableTree(this, forestVeil, trees);
        addObject(ft2);
        FlammableTree ft1 = new FlammableTree(this, forestBackground, trees);
        addObject(ft1);

        WaterPool wp1 = new WaterPool(this, forestBackground, "1", 1);
        WaterPool wp2 = new WaterPool(this, forestBackground, "2", 2);
        WaterPool wp3 = new WaterPool(this, forestBackground, "3", 3);
        WaterPool wp4 = new WaterPool(this, forestBackground, "4", 4);
        addObject(wp1);
        addObject(wp2);
        addObject(wp3);
        addObject(wp4);

        org.roomBackground = dirt;

        addItems();

        genericRoomInitialize();
    }
}
