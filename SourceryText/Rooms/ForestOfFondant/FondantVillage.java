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
import java.util.Objects;

/**
 * A quaint little village.  You can go many places from here.
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
                if (play.getY() > 49){
                    setNewRoom("Cliffbottom", play, 1, play.getX());
                }
                if (play.getX() == 56 && play.getY() == 35) {
                    setNewRoom("VillageInterior", play, 5, 14);
                }
                if (play.getX() == 85 && play.getY() == 31) {
                    setNewRoom("VillageInterior", play, 5, 63);
                }
                if ((play.getX() == 119 || play.getX() == 120) && play.getY() == 34) {
                    setNewRoom("VillageInterior", play, 5, play.getX() - 20);
                }
                Thread.sleep(20);
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    @Override
    public void addItems() {
    }

    @Override
    protected void specialInspect(int x, int y, Player inspector) {
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

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.fondantVillage);
        Layer forestBackground = new Layer(base, "Base");

        forestBackground.findAndReplace(new SpecialText("%"), new SpecialText("\\"));

        //Nature Art

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

        //Road Art

        Color roadBkg = new Color(60, 40, 25);
        Color roadFg  = new Color(77, 58, 47);
        Color roughRoadBkg = new Color((60+29)/2, (40+31)/2, (25+15)/2);
        Color roughRoadFg  = new Color(53, 40, 32);
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText(".", roadFg, roadBkg), 25);
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText("o", roadFg, roadBkg), 10);
        forestBackground.findAndReplace(new SpecialText("o"), new SpecialText(" ", roadFg, roadBkg));

        forestBackground.findAndReplace(new SpecialText("e"), new SpecialText(".", roughRoadFg, roughRoadBkg), 25);
        forestBackground.findAndReplace(new SpecialText("e"), new SpecialText("o", roughRoadFg, roughRoadBkg), 10);
        forestBackground.findAndReplace(new SpecialText("e"), new SpecialText(" ", roughRoadFg, roughRoadBkg));


        //House Art

        Color bricksFg =  new Color(102, 102, 102);
        Color bricksBkg = new Color(38, 38, 38);
        Color woodFg =    new Color(87, 51, 26);
        Color woodBkg =   new Color(51, 33, 20);
        Color roofFg =    new Color(150, 150, 250);
        Color interiorColor = new Color(26, 17, 10);

        forestBackground.findAndReplace(new SpecialText("#"), new SpecialText("#", bricksFg, bricksBkg));
        forestBackground.findAndReplace(new SpecialText("="), new SpecialText("=", woodFg, woodBkg));
        forestBackground.findAndReplace(new SpecialText("b"), new SpecialText("|", woodFg, woodBkg));
        forestBackground.findAndReplace(new SpecialText("d"), new SpecialText(" ", null, interiorColor));

        forestBackground.findAndReplace(new SpecialText("/"), new SpecialText("/", roofFg, dirt));
        forestBackground.findAndReplace(new SpecialText("_"), new SpecialText("_", roofFg, dirt));
        forestBackground.findAndReplace(new SpecialText("\\"), new SpecialText("\\", roofFg, dirt));

        forestBackground.findAndReplace(new SpecialText("r"), new SpecialText("/", roofFg, woodBkg));
        forestBackground.findAndReplace(new SpecialText("l"), new SpecialText("\\", roofFg, woodBkg));
        forestBackground.findAndReplace(new SpecialText("+"), new SpecialText("+", roofFg, interiorColor));

        // River Art

        Color bridgeFg = new Color(128, 95, 64);
        Color bridgeBkg = new Color(89, 60, 30);
        Color sand = new Color(221, 192, 89);
        Color riverRockFg = new Color(115, 128, 128);
        Color riverRockBkg = new Color(61, 87, 102);

        forestBackground.findAndReplace(new SpecialText("w"), new SpecialText("o", riverRockFg, riverRockBkg));
        forestBackground.findAndReplace(new SpecialText("R"), new SpecialText("-", bridgeFg, bridgeBkg));
        forestBackground.findAndReplace(new SpecialText("B"), new SpecialText(":", woodFg, woodBkg));
        forestBackground.findAndReplace(new SpecialText("s"), new SpecialText(" ", null, sand));

        org.addLayer(forestBackground);
        initHitMeshes(forestBackground);
        String[] solids = {"r", "t", "h","T","#","=","l","+","b","R","w"};
        addToBaseHitMesh(base, solids);

        FlammableTree ft1 = new FlammableTree(this, forestBackground, trees);
        addObject(ft1);

        org.roomBackground = dirt;

        //addItems();

        WaterPool oceanShallow          = new WaterPool (this, forestBackground, "1", 1);
        WaterPool oceanDeeper           = new WaterPool (this, forestBackground, "2", 2);
        WaterPool oceanItGetsEvenDeeper = new WaterPool (this, forestBackground, "3", 3);
        addObject(oceanShallow);
        addObject(oceanDeeper);
        addObject(oceanItGetsEvenDeeper);

        genericRoomInitialize();
    }
}
