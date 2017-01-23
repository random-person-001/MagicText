package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.*;
import SourceryText.GameObjects.ForestOfFondant.FlammableTree;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.GameObjects.TheSource.RangedBandit;
import SourceryText.GameObjects.TheSource.WeakTower;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * A Fortress!  Exciting!
 * Created by riley on 01-Sep-2016.
 */
public class BanditFortress extends Room {

    public BanditFortress(Player player) {
        super(player);
        strRoomName = "BanditFortress";
    }

    @Override
    protected String playerLoop(Player play) {

        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (play.getY() >= 104) {
                    setNewRoom("InnerMountains", play, 1, 24);
                }
                if (play.getX() >= 135) {
                    setNewRoom("HiddenBunker", play, 1, 128);
                }
                if (play.getX() >= 87 && play.getY() <= 7) {
                    setNewRoom("HallOfBanditKing", play, 8, 50);
                }
                if (play.getX() <= 87 && play.getY() <= 3) {
                    setNewRoom("HallOfBanditKing", play, 72, 33);
                }
            } catch (InterruptedException ignored) {
            }
        }
        return exitCode;
    }

    @Override
    public void addItems() {

        int[][] banditStations = {{35, 74}, {33, 75}, {38, 75}, {36, 76}, {61, 87}, {73, 87}, {67, 94}, {19, 46},
                {27, 46}, {42, 56}, {48, 61}, {56, 56}, {60, 63}, {74, 32}, {86, 32}, {51, 25}, {59, 24}, {62, 19}, {39, 15},
                {65,41}, {53,36}, {113,65}, {54,84}, {50,84}, {64,72}, {73, 77}, {92,84}};
        for (int[] station : banditStations) {
            Bandit roughBill = new Bandit(org, this, station[0], station[1]);
            addMortal(roughBill);
        }

        int[][] towerLocs = {{76, 15}, {84, 17}, {76, 32}, {84, 32}, {78, 43}, {67, 46}, {23, 41}, {23, 50}, {63, 94},
                {71, 94}, {71, 97}, {63, 97}};
        for (int[] towerLoc : towerLocs) {
            WeakTower t = new WeakTower(org, this, towerLoc[0], towerLoc[1]);
            addMortal(t);
        }

        int[][] rangedLocs = {{58, 52}, {80, 58}, {27, 61}, {69, 65}};
        for (int[] rangedLoc : rangedLocs) {
            RangedBandit ranger = new RangedBandit(this, rangedLoc[0], rangedLoc[1]);
            addMortal(ranger);
        }

        Item magicTater = new Item("Magic Potato", "How lucky! This eccentric\n potato can permanently\n " +
                "increase either your\n Max HP or Max Mana.\n\nNOTE: it's permanent.\nYou got this illicitly.", "item");
        DroppedItem gTater = new DroppedItem(this, "You found a magic potato.  Cheater.", magicTater, 51, 19);
        super.addObject(gTater);

        OneWayDoor bunkerEntrance = new OneWayDoor(true, 94, 60, this, org);
        addObject(bunkerEntrance);

        addSavePoint(129, 63);
        addSavePoint(65, 8);
    }

    @Override
    public void startup() {

        String[] rockText1 = {"You've passed safely through the walls of \n the fortress!  Unfortunately, many more\n bandits lie inside.  Be careful."};
        plantText(new Room.FlavorText(rockText1, ""), 59, 82);

        String[] rockText2 = {"The immBanense, strong walls of the bandit   \n fortress tower before you.  This may be\n a long shot, whatever you're to do here."};
        plantText(new Room.FlavorText(rockText2, ""), 67, 94);

        String[] byFountain = {"Phew!  So many bandits!  Thankfully, it  \n only gets worse from here on out.  The \n Bandit King lies ahead, ugly and brutal.",
                "Meanwhile, you can enjoy the beautiful\n fountain.  There's an inaccessible item\n in the middle of it.  Pretty, huh?"};
        plantText(new Room.FlavorText(byFountain, "Talking Gargoyle"), 51, 25);

        String[] bedrooms = {"1) No bouncing on the beds! (remember the\n Jordan incident, you don't want to end\n up like him)","2) Bedtime at 01:00, we all need our\n beauty sleep"};
        plantText(new Room.FlavorText(bedrooms, "\"BEDROOM RULES\""), 90, 66);

        String[] kitchen = {"Kitchen staff: currently, more bandits have\n died from food sickness than battle. \n Shape up or see your head on a post."};
        plantText(new Room.FlavorText(kitchen, "Dirty Post-It note on floor"), 82, 49);

        String[] stables = {"Remember to let the horses eat sometimes.  \n If you want to starve things, go to  \n the torture room."};
        plantText(new Room.FlavorText(stables, "Horse Rules"), 113, 63);

        Color lightTreeGreen = new Color(20, 60, 10);
        Color shadowTreeGreen = new Color(29, 50, 19);

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.banditFortress);

        Layer lay1 = new Layer(base, "Base");
        highlightFlavorText(lay1);
        SpecialText[] flammableTreeChars = {new SpecialText(" ", null, lightTreeGreen), new SpecialText(" ", null, shadowTreeGreen)};
        lay1.findAndReplace(new SpecialText("t"), flammableTreeChars[0]);
        lay1.findAndReplace(new SpecialText("h"), flammableTreeChars[1]);
        Color grassbkgd = new Color(0, 28, 6);
        Color grassfgd = new Color(20, 40, 30);
        lay1.findAndReplace(new SpecialText("g"), new SpecialText(".", grassfgd, grassbkgd), 15);
        lay1.findAndReplace(new SpecialText("g"), new SpecialText(",", grassfgd, grassbkgd), 15);
        lay1.findAndReplace(new SpecialText("g"), new SpecialText(" ", grassfgd, grassbkgd));
        lay1.findAndReplace(new SpecialText("C"), new SpecialText(" ", null, new Color(43, 38, 33)));

        Color closeRock = new Color(65,55,33);
        Color rock = new Color(65/2,55/2,33/3);
        Color farRock = new Color(65/4,55/4,33/4);

        lay1.findAndReplace(new SpecialText("r"), new SpecialText(":", closeRock, rock), 40);
        lay1.findAndReplace(new SpecialText("r"), new SpecialText(";", closeRock, rock), 50);
        lay1.findAndReplace(new SpecialText("r"), new SpecialText("^", closeRock, rock), 70);
        lay1.findAndReplace(new SpecialText("r"), new SpecialText("*", rock, rock));

        lay1.findAndReplace(new SpecialText("s"), new SpecialText(":", rock, farRock),40);
        lay1.findAndReplace(new SpecialText("s"), new SpecialText(";", rock, farRock),50);
        lay1.findAndReplace(new SpecialText("s"), new SpecialText("^", rock, farRock),70);
        lay1.findAndReplace(new SpecialText("s"), new SpecialText("*", farRock, farRock));

        lay1.findAndReplace(new SpecialText("m"), new SpecialText("m", new Color(40*2, 38*2, 36*2), new Color(60/2, 58/2, 56/2)));
        lay1.findAndReplace(new SpecialText(":"), new SpecialText("#", new Color(40*2, 38*2, 36*2), new Color(60/2, 58/2, 56/2)));
        lay1.findAndReplace(new SpecialText("#"), new SpecialText("M", new Color(110, 90, 80), new Color(50/2, 50/2, 30/2)));
        lay1.findAndReplace(new SpecialText("w"), new SpecialText("W", new Color(80, 58, 56), new Color(60/2, 40/2, 48/2)));
        lay1.findAndReplace(new SpecialText("o"), new SpecialText("X", new Color(120, 131, 90)));
        lay1.findAndReplace(new SpecialText("b"), new SpecialText("b", new Color(30/2,10,100/2), new Color(70/2, 13/2, 120/2)));
        lay1.findAndReplace(new SpecialText("/"), new SpecialText("/", new Color(80, 58, 56)));
        lay1.findAndReplace(new SpecialText("-"), new SpecialText("-", new Color(80/2, 58/2, 56/2)));

        org.addLayer(lay1);

        initHitMeshes(lay1);

        addItems();
        String[] solids = {":", "w", "m", "#", "/", "C", "o", "r", "s", "t", "h"};
        addToBaseHitMesh(base, solids);

        addObject(new WaterPool(this, lay1,"1", 1));
        addObject(new WaterPool(this, lay1,"2", 2));
        addObject(new FlammableTree(this, lay1,flammableTreeChars));

        genericRoomInitialize();
    }
}
