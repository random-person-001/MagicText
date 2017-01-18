/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceryText.Rooms.TheSource;

import SourceryText.Art;
import SourceryText.GameObjects.*;
import SourceryText.GameObjects.TheSource.Bandit;
import SourceryText.Layer;
import SourceryText.Rooms.Room;
import SourceryText.SpecialText;

import java.awt.*;

/**
 * A hidden bunker!  How exciting!  So many rooms hidden, and locked doors.
 *
 * @author Jared
 *         <p>
 *         So Far:
 *         > You've done most of the rooms in this zone
 *         <p>
 *         What Generally Happens Here:
 *         > You get a lot of practice using the inspect key to open doors
 *         > You get a couple of valauble items, and some evil spells
 *         > You reveal rooms that where hidden before your eyes with inspect
 *         > You pick amongst lots of loot to find the couple of keys to unlock doors to the rest of the level
 *         > There's some one way doors and a water pool
 */


public class HiddenBunker extends Room {

    public HiddenBunker(Player player) {
        super(player);
        strRoomName = "HiddenBunker";
    }

    @Override
    protected String playerLoop(Player play) {
        while (exitCode.equals("")) {
            try {
                Thread.sleep(20);
                if (play.getY() == 36) {
                    setNewRoom("SourceCaves", play, 2, 190);
                }
                if (play.getY() == 0) {
                    setNewRoom("BanditFortress", play, 64, 134);
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
        Item potion = new Item("MagicTaterChip", "A singular potato chip made\n with magic potatoes.\n\nEating this chip will\n restore hp over time\n (4hp/s + 10 overheal!)", "item");
        potion.setSpellType("potion");
        potion.setDuration(30 * 1000);
        DroppedItem dPotion = new DroppedItem(this, "You found a potato chip!", potion, 53, 11);
        addObject(dPotion);

        Item item1 = new Item("InitiateTome", "A book full of dark rituals\n\nNewcomers to dark magic are\n handed this book to study.\n\nYes, it's a textbook.", "equipment");
        item1.setEquipvals(0, 0, 0, 0, 0, 0, 3, "weapon");
        DroppedItem dBook = new DroppedItem(this, "You found an item: Initiate Tome!", item1, 110, 4);
        addObject(dBook);

        DroppedItem dPotion2 = new DroppedItem(this, "You found a potato chip!", potion, 113, 30);
        addObject(dPotion2);

        addMagicPotato(108, 2);

        Item item6 = new Item("Evil Powers", "Dark Spell;\n\nYou're not sure what it is\n exactly, but it has the\n word 'power' in its name,\n so it must be good,\n right?", "EvlPw", "spell", true);
        item6.dmgSpellDefine(1, 8, 2, 0.02f, "dark", new SpecialText("*", new Color(155, 55, 155)), new SpecialText("*", new Color(255, 55, 255)), false, 0);
        DroppedItem dSpell = new DroppedItem(this, "You found a spell: Evil Powers!", item6, 55, 9);
        addObject(dSpell);

        int[][] locs = {{63, 30}, {66, 32}, {37, 11}, {40, 7}, {43, 2}, {84, 9}, {82, 18}, {87, 16}, {132, 12}, {135, 13}, {119, 14}, {136, 19}};
        for (int ii = 0; ii < locs.length; ii++) {
            Bandit enemy = new Bandit(org, this, locs[ii][0], locs[ii][1]);
            addMortal(enemy);
        }
    }

    boolean[] keysTaken = {false, false, false, false, false};

    @Override
    protected void specialInspect(int x, int y, Player inspector) {
        if (x == 24 && y == 29) {
            org.removeLayer("Cloak1");
            org.editLayer("", "HiddenBunkerLayer", 28, 24);
            removeFromBaseHitMesh(24, 28);
        }
        if (x == 63 && y == 10) {
            org.removeLayer("Cloak2");
            org.editLayer("", "HiddenBunkerLayer", 10, 62);
            removeFromBaseHitMesh(62, 10);
        }
        if ((x == 89 || x == 91) && y == 13) {
            org.removeLayer("Cloak3");
            org.removeLayer("Cloak5");
            org.editLayer("", "HiddenBunkerLayer", 13, 90);
            removeFromBaseHitMesh(90, 13);
        }
        if (x == 95 && y == 13) {
            org.removeLayer("Cloak3");
            org.removeLayer("Cloak5");
            org.editLayer("", "HiddenBunkerLayer", 13, 94);
            removeFromBaseHitMesh(94, 13);
        }
        if (x == 114 && y == 13) {
            org.removeLayer("Cloak4");
            org.removeLayer("Cloak5");
            org.editLayer("", "HiddenBunkerLayer", 13, 113);
            removeFromBaseHitMesh(113, 13);
        }
        if (x == 102 && y == 6) {
            org.editLayer("", "HiddenBunkerLayer", 5, 102);
            removeFromBaseHitMesh(102, 5);
        }
        if (x == 95 && y == 26) {
            org.removeLayer("Cloak6");
            org.editLayer("", "HiddenBunkerLayer", 27, 95);
            removeFromBaseHitMesh(95, 27);
        }
        if (x == 108 && y == 30) {
            org.editLayer("", "HiddenBunkerLayer", 30, 109);
            removeFromBaseHitMesh(109, 30);
        }
        if (x == 119 && y == 26) {
            org.removeLayer("Cloak7");
            org.editLayer("", "HiddenBunkerLayer", 26, 120);
            removeFromBaseHitMesh(120, 26);
        }
        if (x == 129 && y == 25) {
            org.editLayer("", "HiddenBunkerLayer", 25, 130);
            removeFromBaseHitMesh(130, 25);
        }
        if (x == 128 && y == 20) {
            org.removeLayer("Cloak8");
            org.editLayer("", "HiddenBunkerLayer", 21, 128);
            removeFromBaseHitMesh(128, 21);
        }
        if (x == 130 && y == 12) {
            org.removeLayer("Cloak9");
            org.editLayer("", "HiddenBunkerLayer", 11, 130);
            removeFromBaseHitMesh(130, 11);
        }
        if (Math.abs(x - 24) + Math.abs(y - 24) <= 1 && !keysTaken[0]) {
            Item key = new Item("Bunker Key 1", "A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Need more plunder,\n hide key\'", "item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!", ""), inspector);
            keysTaken[0] = true;
        }

        if (Math.abs(x - 69) + Math.abs(y - 30) <= 1 && !keysTaken[1]) {
            Item key = new Item("Bunker Key 2", "A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Mike need to shower\'", "item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!", ""), inspector);
            keysTaken[1] = true;
        }
        if (Math.abs(x - 54) + Math.abs(y - 4) <= 1 && !keysTaken[2]) {
            Item key = new Item("Bunker Key 3", "A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Fondant\n Fondant\n Fondant\'", "item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!", ""), inspector);
            keysTaken[2] = true;
        }
        if (Math.abs(x - 125) + Math.abs(y - 25) <= 1 && !keysTaken[3]) {
            Item key = new Item("Bunker Key 4", "A key to a door in\n the hidden bunker found\n in the mountains.\n\nThere is an inscription\n on the key:\n\n\'Down with Carlson\'", "item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!", ""), inspector);
            keysTaken[3] = true;
        }
        if (Math.abs(x - 134) + Math.abs(y - 24) <= 1 && !keysTaken[4]) {
            Item key = new Item("Tunnel Key", "A key to a secret tunnel\n deep in the mountains.\n\nThere is an inscription\n on the key:\n\n\'DO NOT LOSE\'", "item");
            inspector.addItem(key);
            queueMessage(new FlavorText("You found a key!", ""), inspector);
            keysTaken[4] = true;
        }
    }

    @Override
    public void startup() {

        String[] signWords = {"ATTENTION NEW RECRUITS\n in order to ward off robbers\n from taking our stuff,",
                "Keys and various goods are\n kept behind hidden doors.\nWithout those keys,",
                "You can't get to our\n valuable stuff!", "Note: to open a door,\n simply rest your hand on\n the wall to move it out of the way"};
        plantText(new FlavorText(signWords, "A Floor Plaque"), 8, 30);

        Art arty = new Art();
        String[][] base = Art.strToArray(arty.hiddenBunker);

        Layer lay1 = new Layer(base, "HiddenBunkerLayer");
        highlightFlavorText(lay1);

        //lay1.findAndReplace(new SpecialText("#"), new SpecialText("#", new Color(175, 175, 175), new Color(25, 25, 25)));
        lay1.findAndReplace(new SpecialText("#"), arty.poundWall);
        //lay1.findAndReplace(new SpecialText("%"), new SpecialText("%", new Color(175, 175, 175), new Color(20, 20, 20)));
        lay1.findAndReplace(new SpecialText("%"), new SpecialText("%", new Color(40*3, 38*3, 36*3), new Color(50/2, 48/2, 46/2)));

        lay1.findAndReplace(new SpecialText("$"), new SpecialText("$", new Color(225, 175, 75)));

        lay1.findAndReplace(new SpecialText("C"), new SpecialText(" ", null, new Color(43, 38, 33)));

        Color goldColor = new Color(200, 175, 75);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("O", goldColor), 20);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("%", goldColor), 20);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("#", goldColor), 20);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("&", goldColor), 20);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("*", goldColor), 20);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("G", goldColor), 20);
        lay1.findAndReplace(new SpecialText("8"), new SpecialText("8", goldColor));

        org.addLayer(lay1);

        initHitMeshes(lay1);

        String[] solids = {"#", "|", "-", "%", "8", "$", "C"};
        addToBaseHitMesh(base, solids);

        int[][] layerInfo = {{5, 10, 23, 21}, {7, 15, 8, 47}, {3, 3, 12, 91},
                {10, 18, 6, 95}, {3, 1, 12, 94}, {7, 17, 28, 92},
                {5, 6, 23, 121}, {6, 3, 22, 127}, {11, 11, 0, 125}};
        for (int ii = 0; ii < 9; ii++) {
            Layer newCloak = new Layer(new String[layerInfo[ii][0]][layerInfo[ii][1]], ("Cloak" + (ii + 1)), layerInfo[ii][3], layerInfo[ii][2], true, true, true);
            newCloak.clear();
            org.addLayer(newCloak);
        }

        OneWayDoor secretDoor = new OneWayDoor(true, 92, 13, this, org);
        addObject(secretDoor);
        OneWayDoor normalDoor = new OneWayDoor(false, 133, 31, this, org);
        addObject(normalDoor);

        int[][] lockedDoorLocs = {{32, 30}, {51, 27}, {77, 11}, {106, 2}};
        for (int ii = 0; ii < lockedDoorLocs.length; ii++) {
            LockedDoor door = new LockedDoor("Bunker Key " + (ii + 1), true, lockedDoorLocs[ii][0], lockedDoorLocs[ii][1], this, org);
            addObject(door);
        }
        LockedDoor tunnelDoor = new LockedDoor("Tunnel Key", false, 133, 9, this, org);
        addObject(tunnelDoor);

        WaterPool testingPool = new WaterPool(this, new Layer(Art.strToArray(arty.hiddenBunkerWater), "Water!", true, false), 80, 10);
        addObject(testingPool);

        addItems();

        genericRoomInitialize();
    }
}
